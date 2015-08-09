package com.tutorial.struts.service.apunte;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.csvreader.CsvReader;
import com.tutorial.domain.entidades.baseDatos.Apunte;
import com.tutorial.domain.entidades.baseDatos.Clase;
import com.tutorial.domain.entidades.baseDatos.Cuenta;
import com.tutorial.struts.forms.apunte.ApunteForm;
import com.tutorial.struts.forms.apunte.ApunteListForm;
import com.tutorial.struts.hibernate.util.HibernateUtil;
import com.tutorial.struts.service.ServiceUtils;
import com.tutorial.struts.utils.FechaUtil;
import com.tutorial.struts.utils.NumeroUtil;

public class ApunteServiceImpl implements IApunteService {
	
	
	private static String OBTENER_SALDO_ACTUAL_CUENTA_QUERY=
		" SELECT SUM(apunte.importe) FROM Apunte apunte WHERE apunte.cuenta.idCuenta=:idCuenta";
		
	/**
	 * Singleton Instance of ApunteServiceImpl
	 */
	private static ApunteServiceImpl apunteServiceImpl = new ApunteServiceImpl();
	
	/**
	 * Creates Instance of {@link ApunteServiceImpl}
	 */
	private ApunteServiceImpl(){		
	}
	
	/***
	 * Gets Instance of ApunteServiceImpl
	 * @return ApunteServiceImpl Implementation
	 */
	public static ApunteServiceImpl getInstance(){	
		return apunteServiceImpl;
	}
	/**
	 * Guarda el fichero pasado como parametro en la ruta indicada
	 */
	@Override
	public Boolean guardarFichero(String path, FormFile fichero) {

		File f=null;
		
		try {
			// se comprueba que la ruta exista
			f = new File(path);
			if (makeSureDirectoryExists(parent(f))) {
				// Se graba en la ruta del fichero;
				FileOutputStream out = new FileOutputStream(f);
				out.write(fichero.getFileData());
				out.flush();
				out.close();
			}
		}

		catch (Exception ex) {
			System.out.println ("Clase: "+getClass().getName()+" metodo: guardarFichero. Error: "+ex);
			return null ;
		}
		//Destruimos el archivo temporal
		fichero.destroy();
		System.out.print( "Subido el fichero" );
		//Retornamos y continuamos en
		
		return true;
	}
	
	/**
	 * Carga en la tabla apunte los movimientos existentes en el fichero del que se le pasa la ruta
	 * El fichero debe de ser un .txt separado por tabulaciones.
	 * @param path --> La ruta del fichero del que debe leer
	 * @return idCuenta --> La cuenta a la que pertenece el apunte
	 */
	@Override
	public Integer cargarMovimientos(String path, Integer idCuenta) {
		//Para crear nuestro objeto lector de csv
		File fichero = null;
		List<Apunte> lstApunte=new ArrayList<Apunte>();
		Cuenta cuenta=null;
		Clase  clase=null;
		
		try {
			cuenta=this.getCuenta(idCuenta);
			clase=this.getClase(null);
		
			fichero=new File(path);
		
			FileReader fileReader = new FileReader(fichero);
			CsvReader csvReader = new CsvReader(fileReader,'\t');
		
			//Leemos los encabezados
			 /*	parametro[0] = fecha 		parametro[1] = concepto	
			  * parametro[2] = fecha valor 	parametro[3] = importe	
			  *	parametro[4] = saldo
			  */
			
			//Leemos las cabeceras del fichero (primera fila).
			csvReader.readHeaders();
			
			//interar los valores de cada columna para cada fila.
			while(csvReader.readRecord()){ //Mientras se encuentren resultados
				Apunte apunte=new Apunte();
				apunte.setFecha(FechaUtil.formatearADate(csvReader.get("fecha")));
				apunte.setClase(clase);
				apunte.setCuenta(cuenta);
				apunte.setConcepto(csvReader.get("concepto"));
				apunte.setImporte(NumeroUtil.formatearImporteComaAPuntoBD(csvReader.get("importe")));
				apunte.setTraspaso("N");
								
				if (apunte.getImporte().compareTo(new BigDecimal("0"))>0)
					apunte.setTipoOperacion("INGRESO");
				else
					apunte.setTipoOperacion("GASTO");
				
				apunte.setNotas("Importado desde fichero");
				apunte.setBaja("N");
				lstApunte.add(apunte);
			}
		}
		catch (Exception ex) {
			System.out.println ("Clase: "+getClass().getName()+" metodo: cargarMovimientos. Error: "+ex);
			return 0;
		}
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;				
		//Guardar lista de apuntes
		try{
			transaction=session.beginTransaction();
			for (Apunte apunte:lstApunte){
				session.saveOrUpdate(apunte);	
			}
			
			session.flush();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			return 0;
		}
		finally{
			HibernateUtil.closeSession();	
		}		
		return lstApunte.size();
	}
	
	/**
	 * Carga en la tabla apunte los movimientos existentes en el fichero del que se le pasa la ruta
	 * El fichero debe de ser un .txt separado por tabulaciones.
	 * @param path --> La ruta del fichero del que debe leer
	 * @param idCuenta --> La cuenta seleccionada
	 * @param idBanco --> El banco al que pertenece la cuenta seleccionada
	 * @return lstApunte.size() --> Numero de apuntes insertados
	 */
	@Override
	public Integer cargarMovimientosExcel(String fileName, Integer idCuenta, Integer idBanco) {
		/**
		* Create a new instance for cellDataList
		*/
		List cellDataList = new ArrayList();
		try{
			/**
			* Create a new instance for FileInputStream class
			*/
			FileInputStream fileInputStream = new FileInputStream(fileName);
			/**
			* Create a new instance for POIFSFileSystem class
			*/
			POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
			/*
			* Create a new instance for HSSFWorkBook Class
			*/
			HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem);
			HSSFSheet hssfSheet = workBook.getSheetAt(0);
			/**
			* Iterate the rows and cells of the spreadsheet
			* to get all the datas.
			*/
			Iterator rowIterator = hssfSheet.rowIterator();
			while (rowIterator.hasNext()){
				HSSFRow hssfRow = (HSSFRow) rowIterator.next();
				Iterator iterator = hssfRow.cellIterator();
				List cellTempList = new ArrayList();
				while (iterator.hasNext()){
					HSSFCell hssfCell = (HSSFCell) iterator.next();
					cellTempList.add(hssfCell);
				}
				cellDataList.add(cellTempList);
			}
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		/**
		* Call the printToConsole method to print the cell data in the
		* console.
		*/
		printToConsole(cellDataList);
		
		/**
		 * Guardo los apuntes en base de datos
		 * **/
		
		List<Apunte>lstApunte=new ArrayList<Apunte>();
		try {
			
			Cuenta cuenta=this.getCuenta(idCuenta);
			Clase clase=this.getClase(null);
		
			/*------------- Kutxabank --------------
				parametro[0] = fecha 		parametro[1] = concepto	
			 	parametro[2] = fecha valor 	parametro[3] = importe	
			 	parametro[4] = saldo
		 	*/
			if(idBanco.compareTo(new Integer(1))==0) {
				for (int i = 5; i < cellDataList.size(); i++){
					List cellTempList = (List) cellDataList.get(i);
					Apunte apunte=new Apunte();
					Boolean pintarlinea=true;
					for (int j = 0; j < cellTempList.size(); j++){
						HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
						String stringCellValue = hssfCell.toString();
						if(stringCellValue.isEmpty()){
							pintarlinea=false;
							break;
						}
					 
						else{
							
							if (j==0)
								apunte.setFecha(FechaUtil.formatearADate(stringCellValue));				
							else if (j==1)
								apunte.setConcepto(stringCellValue);
							else if (j==3){
								apunte.setImporte(new BigDecimal(stringCellValue));
								if (apunte.getImporte().compareTo(new BigDecimal("0"))>0)
									apunte.setTipoOperacion("INGRESO");
								else
									apunte.setTipoOperacion("GASTO");
							}
						}
					}
					apunte.setClase(clase);
					apunte.setCuenta(cuenta);
					apunte.setTraspaso("N");
					apunte.setNotas("Importado desde fichero");
					apunte.setBaja("N");
					if (pintarlinea)
						lstApunte.add(apunte);
				}
			}
			/*------------- ING Direct --------------
				parametro[0] = fecha ope	parametro[1] = fecha valor 	 
			 	parametro[2] = concepto		parametro[3] = importe	
			 	parametro[4] = saldo
			 */
			if(idBanco.compareTo(new Integer(2))==0) {
				for (int i = 4; i < cellDataList.size(); i++){
					List cellTempList = (List) cellDataList.get(i);
					Apunte apunte=new Apunte();
					Boolean pintarlinea=true;
					for (int j = 0; j < cellTempList.size(); j++){
						HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
						String stringCellValue = hssfCell.toString();
						if(stringCellValue.isEmpty()){
							pintarlinea=false;
							break;
						}
					 
						else{
							if (j==0)
								apunte.setFecha(FechaUtil.formatearADate(stringCellValue,"dd-MMM-yyyy"));				
							else if (j==2)
								apunte.setConcepto(stringCellValue);
							else if (j==3){
								apunte.setImporte(new BigDecimal(stringCellValue));
								if (apunte.getImporte().compareTo(new BigDecimal("0"))>0)
									apunte.setTipoOperacion("INGRESO");
								else
									apunte.setTipoOperacion("GASTO");
							}
						}
					}
					apunte.setClase(clase);
					apunte.setCuenta(cuenta);
					apunte.setTraspaso("N");
					apunte.setNotas("Importado desde fichero");
					apunte.setBaja("N");
					if (pintarlinea)
						lstApunte.add(apunte);
				}
			}
			
			/*------------- openbank --------------
				parametro[1] = fecha ope	parametro[3] = fecha valor 	 
			 	parametro[5] = concepto		parametro[7] = importe	
			 	parametro[9] = saldo
			 */
			if(idBanco.compareTo(new Integer(3))==0) {
				for (int i = 11; i < cellDataList.size(); i++){
					List cellTempList = (List) cellDataList.get(i);
					Apunte apunte=new Apunte();
					Boolean pintarlinea=true;
					
					for (int j = 1; j < cellTempList.size(); j=j+2){
						HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
						String stringCellValue = hssfCell.toString();
						
						if (j==1){
							if(stringCellValue.isEmpty()){
								pintarlinea=false;
								break;
							};
						}
							
						if (j==1)
							apunte.setFecha(FechaUtil.formatearADate(stringCellValue,"dd-MMM-yyyy"));				
						else if (j==5)
							apunte.setConcepto(stringCellValue);
						else if (j==7){
							apunte.setImporte(new BigDecimal(stringCellValue));
							if (apunte.getImporte().compareTo(new BigDecimal("0"))>0)
								apunte.setTipoOperacion("INGRESO");
							else
								apunte.setTipoOperacion("GASTO");
						}
					}
					apunte.setClase(clase);
					apunte.setCuenta(cuenta);
					apunte.setTraspaso("N");
					apunte.setNotas("Importado desde fichero");
					apunte.setBaja("N");
					if (pintarlinea)
						lstApunte.add(apunte);
				}
			}
		
		}
		catch (Exception ex) {
			System.out.println ("Clase: "+getClass().getName()+" metodo: cargarMovimientoskutxaExcel. Error: "+ex);
		}
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;				
		//Guardar lista de apuntes
		try{
			transaction=session.beginTransaction();
			for (Apunte apunte:lstApunte){
				session.saveOrUpdate(apunte);	
			}
			
			session.flush();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			return 0;
		}
		finally{
			HibernateUtil.closeSession();	
		}		
		return lstApunte.size();
		
	}
	
	/**
	* This method is used to print the cell data to the console.
	* @param cellDataList - List of the data's in the spreadsheet.
	*/
	private void printToConsole(List cellDataList){
		for (int i = 0; i < cellDataList.size(); i++){
			List cellTempList = (List) cellDataList.get(i);
			
			for (int j = 0; j < cellTempList.size(); j++){
				HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
				String stringCellValue = hssfCell.toString();
				System.out.print(stringCellValue + "\t");
			}
			
			System.out.println();
		}
	}
			
		
	
	/**
	 * En funcion de los parametros recibidos se confeciona una select que 
	 * devolvera un listado de apuntes
	 */
	/*@Override
	public List<Apunte> getLstApuntes(Integer idCuenta, Integer idGrupo,
		Integer idClase, String fechaInicioStr, String fechaFinStr) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Apunte> lstApuntes=null;
		
		//Tratamiento de fechas
		Date fechaFin=null;
				
		if (fechaFinStr==null || fechaFinStr.isEmpty())
			fechaFin=FechaUtil.getDate();
		else
			fechaFin=FechaUtil.formatearADate(fechaFinStr);
		
		try{
			transaction=session.beginTransaction();
		
			Query query=null;
			StringBuffer sql=new StringBuffer();
			
			sql.append(" from Apunte apunte where apunte.baja='N' ");
			
			if (fechaInicioStr!=null && !fechaInicioStr.isEmpty())
				sql.append(" and apunte.fecha between :fechaInicio and :fechaFin ");
				
					
			if (idCuenta!=null && idCuenta>0){
				sql.append(	" and apunte.cuenta.idCuenta=:idCuenta " +
							" and apunte.cuenta.baja='N' ");
			}
			
			if (idGrupo!=null && idGrupo>0){
				sql.append(	" and apunte.clase.idClase in " +
							" (select clase.idClase from Clase clase" +
							" where clase.grupo.idGrupo=:idGrupo " +
							" and clase.grupo.baja='N') ");
			}
			if (idClase!=null && idClase>0){
				sql.append(	" and apunte.clase.idClase=:idClase" +
				" and apunte.clase.baja='N' ");
			}
			
			if (idClase!=null && idClase>0){
				sql.append(	" and apunte.clase.idClase=:idClase" +
				" and apunte.clase.baja='N' ");
			}
									
			sql.append(" order by apunte.fecha ");
			
			//creamos la query
			query=session.createQuery(sql.toString());
			
			if (fechaInicioStr!=null && !fechaInicioStr.isEmpty()){
				query.setParameter("fechaInicio", FechaUtil.formatearADate(fechaInicioStr), Hibernate.DATE);
				query.setParameter("fechaFin", fechaFin, Hibernate.DATE);
			}
			
			if (idCuenta!=null && idCuenta>0){
				query.setParameter("idCuenta", idCuenta, Hibernate.INTEGER);
			}
			
			if (idGrupo!=null && idGrupo>0){
				query.setParameter("idGrupo", idGrupo, Hibernate.INTEGER);
			}
			if (idClase!=null && idClase>0){
				query.setParameter("idClase", idClase, Hibernate.INTEGER);
			}
			
			lstApuntes=(List<Apunte>)query.list();
			
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println("error sql:" + e);
		}
		finally {		 
			HibernateUtil.closeSession();
		 }
		
		return lstApuntes;
		
	}
*/
	@Override
	public List<Apunte> getLstApuntes(Integer idCuenta, Integer idGrupo,
		Integer idClase, String fechaInicioStr, String fechaFinStr, String concepto, String tipo) {
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Apunte> lstApuntes=null;
		
		//Tratamiento de fechas
		Date fechaFin=null;
				
		if (fechaFinStr==null || fechaFinStr.isEmpty())
			fechaFin=FechaUtil.getDate();
		else
			fechaFin=FechaUtil.formatearADate(fechaFinStr);
		
		try{
			transaction=session.beginTransaction();
		
			Query query=null;
			StringBuffer sql=new StringBuffer();
			
			sql.append(" from Apunte apunte where apunte.baja='N' ");
			
			if (fechaInicioStr!=null && !fechaInicioStr.isEmpty())
				sql.append(" and apunte.fecha between :fechaInicio and :fechaFin ");
				
					
			if (idCuenta!=null && idCuenta>0){
				sql.append(	" and apunte.cuenta.idCuenta=:idCuenta " +
							" and apunte.cuenta.baja='N' ");
			}
			
			if (idGrupo!=null && idGrupo>0){
				sql.append(	" and apunte.clase.idClase in " +
							" (select clase.idClase from Clase clase" +
							" where clase.grupo.idGrupo=:idGrupo " +
							" and clase.grupo.baja='N') ");
			}
			if (idClase!=null && idClase>0){
				sql.append(	" and apunte.clase.idClase=:idClase" +
				" and apunte.clase.baja='N' ");
			}
			
			if (idClase!=null && idClase>0){
				sql.append(	" and apunte.clase.idClase=:idClase" +
				" and apunte.clase.baja='N' ");
			}
			
			if (concepto!=null && !concepto.isEmpty()){
				sql.append(" and apunte.concepto like=:concepto ");
			}
			
			sql.append(" order by apunte.fecha ");
			
			//creamos la query
			query=session.createQuery(sql.toString());
			
			if (fechaInicioStr!=null && !fechaInicioStr.isEmpty()){
				query.setParameter("fechaInicio", FechaUtil.formatearADate(fechaInicioStr), Hibernate.DATE);
				query.setParameter("fechaFin", fechaFin, Hibernate.DATE);
			}
			
			if (idCuenta!=null && idCuenta>0){
				query.setParameter("idCuenta", idCuenta, Hibernate.INTEGER);
			}
			
			if (idGrupo!=null && idGrupo>0){
				query.setParameter("idGrupo", idGrupo, Hibernate.INTEGER);
			}
			if (idClase!=null && idClase>0){
				query.setParameter("idClase", idClase, Hibernate.INTEGER);
			}
			
			if (concepto!=null && !concepto.isEmpty()){
				query.setParameter("concepto", concepto, Hibernate.STRING);
			}
			
			
			lstApuntes=(List<Apunte>)query.list();
			
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println("error sql:" + e);
		}
		finally {		 
			HibernateUtil.closeSession();
		 }
		
		return lstApuntes;
		
	}
	
	/**
	* Devuelve la ruta padre del subdirectorio actual
	* @param File --> El archivo del cual se quiere sacar su directorio o directorio padre
	* @return File --> Crea un archivo con la ruta del directorio padre
	*/
	private File parent(File f) {
		String dirname = f.getParent();
		if (dirname == null ) {
			return new File(File.separator);
		}
		return new File(dirname);
	}

	/**
	* Crear un subdirectorio si este no existe
	* @param dir --> El path del archivo (dirección + nombre)
	* @return True -> Existe o se ha creado False --> No existe y no se ha podido crear
	*/
	private boolean makeSureDirectoryExists(File dir) {

		if (!dir.exists()) {
			if (makeSureDirectoryExists(parent(dir)))
				dir.mkdir();
			else
				return false ;
		}
		return true ;
	}
	
	private Cuenta getCuenta(Integer idCuenta) throws Exception {
		Cuenta cuenta=new Cuenta();
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		transaction=session.beginTransaction();
		cuenta=(Cuenta)session.get(Cuenta.class, idCuenta);
		HibernateUtil.closeSession();	
		
		return cuenta;
		
	}
	
	private Clase getClase(Integer idClase) throws Exception {
		Clase clase=new Clase();
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		
		transaction=session.beginTransaction();
		if (idClase==null)
			//El codigo 1 pertenece a la clase:"SIN CLASE". Esta clase pertence a su vez al grupo:"SIN GRUPO" 
			idClase=1; 
		clase=(Clase)session.get(Clase.class, idClase);
		HibernateUtil.closeSession();	
		
		return clase;
		
	}
	
	@Override
	public ApunteForm setApunteNew(ApunteListForm formulario) {
		
		ApunteForm apunteForm=new ApunteForm();
				
		apunteForm.setFechaStr(formulario.getFechaFinStr());
		
		apunteForm.setIdContabilidad(formulario.getIdContabilidad());
		apunteForm.setIdCuenta(formulario.getIdCuenta());
		apunteForm.setIdGrupo(formulario.getIdGrupo());
		apunteForm.setIdClase(formulario.getIdClase());
		apunteForm.setTraspaso("N");
		return apunteForm;
	}

	@Override
	public void saveApunte(ApunteForm apunteForm) {
				
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		try{
			transaction=session.beginTransaction();
			
			Apunte apunte=new Apunte();
			Clase clase=(Clase)session.get(Clase.class, apunteForm.getIdClase());
			Cuenta cuenta=(Cuenta)session.get(Cuenta.class, apunteForm.getIdCuenta());
						
			apunte.setIdApunte(apunteForm.getId());
			apunte.setFecha(FechaUtil.formatearADate(apunteForm.getFechaStr()));
			apunte.setTipoOperacion(apunteForm.getTipoApunte());
			apunte.setCuenta(cuenta);
			apunte.setClase(clase);
			apunte.setConcepto(apunteForm.getConcepto());
			apunte.setTraspaso(StringUtils.equals("S",apunteForm.getTraspaso())?"S":"N");
			
			//gestion del importe negativo en caso de que el tipo de apunte sea gasto
			StringBuffer importeSbr=new StringBuffer();
			if (apunteForm.getTipoApunte().equalsIgnoreCase("GASTO"))
				importeSbr.append("-");
			importeSbr.append(apunteForm.getImporteStr());
			apunte.setImporte(NumeroUtil.formatearImporteComaAPuntoBD(importeSbr.toString()));
			
			//Gestion para restar el importe anterior para calcular el nuevo saldo
			//Query query=session.createQuery(OBTENER_SALDO_ACTUAL_CUENTA_QUERY);
			//query.setParameter("idCuenta", new Integer(apunteForm.getIdCuenta()), Hibernate.INTEGER);
						
			//Double saldoDbl=(Double)query.uniqueResult();
			BigDecimal importeAnterior=new BigDecimal("0");
			if (apunte.getIdApunte()!=null && apunte.getIdApunte()>0){
				Apunte apunteAntesModificar=(Apunte)session.get(Apunte.class, apunte.getIdApunte());
				importeAnterior=apunteAntesModificar.getImporte();
			}
		
			apunte.setNotas(apunteForm.getNotas());
			apunte.setBaja("N");
			
			session.merge(apunte);
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null){ 
				transaction.rollback();
			}
		}
		finally{
			HibernateUtil.closeSession();	
		}
		
	}

	@Override
	public ApunteForm geApunte(Integer id, String accion) {
		
		ApunteForm apunteForm=new ApunteForm();
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		try{
			transaction=session.beginTransaction();
			Apunte apunte=(Apunte)session.get(Apunte.class, id);
			
			if (!accion.equalsIgnoreCase("copiar"))
				apunteForm.setId(apunte.getIdApunte());
						
			apunteForm.setFechaStr(FechaUtil.formatear(apunte.getFecha(), new StringBuffer(), new FieldPosition(0)).toString());
			apunteForm.setTipoApunte(apunte.getTipoOperacion());
			apunteForm.setTraspaso(apunte.getTraspaso());
			apunteForm.setIdContabilidad(apunte.getCuenta().getContabilidad().getIdContabilidad());
			apunteForm.setIdCuenta(apunte.getCuenta().getIdCuenta());
			apunteForm.setIdGrupo(apunte.getClase().getGrupo().getIdGrupo());
			apunteForm.setNombreGrupo(apunte.getClase().getGrupo().getNombre());
			apunteForm.setIdClase(apunte.getClase().getIdClase());
			apunteForm.setNombreClase(apunte.getClase().getNombre());
			apunteForm.setConcepto(apunte.getConcepto());
			apunteForm.setImporteStr(apunte.getImporte().toString().replace("-", ""));
			apunteForm.setImporte(new BigDecimal(apunte.getImporte().toString().replace("-", "")));
			apunteForm.setNotas(apunte.getNotas());
			
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null){ 
				transaction.rollback();
			}
		}
		finally{
			HibernateUtil.closeSession();	
		}
		return apunteForm;
	}

	@Override
	public void deleteApunte(Integer id) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		try{
			transaction=session.beginTransaction();
			Apunte apunte=(Apunte)session.get(Apunte.class, id);
			apunte.setBaja("S");
			session.merge(apunte);
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null){ 
				transaction.rollback();
			}
		}
		finally{
			HibernateUtil.closeSession();	
		}
	}
	
	@Override
	public BigDecimal getTotalIngresos(List<ApunteForm> lstApuntesForm){
		
		BigDecimal totalIngresos=new BigDecimal(0);
		
		for (ApunteForm apunteForm:lstApuntesForm){
			if (apunteForm.getTipoApunte().equalsIgnoreCase("INGRESO"))
				totalIngresos=totalIngresos.add(apunteForm.getImporte());
		}
		
		return totalIngresos;
	}

	@Override
	public BigDecimal getTotalGastos(List<ApunteForm> lstApuntesForm){
		
		BigDecimal totalGastos=new BigDecimal(0);
		
		for (ApunteForm apunteForm:lstApuntesForm){
			if (apunteForm.getTipoApunte().equalsIgnoreCase("GASTO"))
				totalGastos=totalGastos.add(apunteForm.getImporte());
		}
		
		return totalGastos;
	}
	
	/**
	 * Devuelve una lista de apuntesForm con el saldo calculado al vuelvo para cada apunte
	 * @param idCuenta
	 * @param idGrupo
	 * @param idClase
	 * @param fechaInicioStr
	 * @param fechaFinStr
	 * @return
	 */
	@Override
	/*public List<ApunteForm>getLstApuntesForm(Integer idCuenta, Integer idGrupo,
			Integer idClase, String fechaInicioStr, String fechaFinStr){
		
		Cuenta cuenta=ServiceUtils.getICuentaService().cargarCuenta(idCuenta);
		List<Apunte>lstApuntes=this.getLstApuntesAll(idCuenta);
		List<ApunteForm> lstApuntesFormAll=new ArrayList<ApunteForm>();
				
		for (int i=0;lstApuntes.size()>i;i++){
			ApunteForm apunteForm=this.geApunte(lstApuntes.get(i).getIdApunte(), "");
			//Si es el primer registro, para calcular el saldo sumo al importe del apunte el saldo inicial de la cuenta
			if (i==0)
				apunteForm.setSaldo(lstApuntes.get(i).getImporte()+cuenta.getSaldoInicial());
			//Si no es el 1º registro, sumo importe del apunte actual con el importe del apunte anterior
			else
				apunteForm.setSaldo(lstApuntes.get(i).getImporte()+lstApuntesFormAll.get(i-1).getSaldo());
			
			lstApuntesFormAll.add(apunteForm);
		}
		
		//Filtro para añadir a la lista de apuntes tan solo los que se deben mostrar
		List<ApunteForm> lstApuntesFormReturn=new ArrayList<ApunteForm>();
		for (ApunteForm apunteForm:lstApuntesFormAll){
			//que la fecha del apunte este dentro del rango de fechas seleccionado por el usuario
			if(FechaUtil.compararFechas(
					FechaUtil.formatearADate(apunteForm.getFechaStr()),	FechaUtil.formatearADate(fechaInicioStr))<=0
				&& 	FechaUtil.compararFechas(FechaUtil.formatearADate(apunteForm.getFechaStr()),FechaUtil.formatearADate(fechaFinStr))>=0){			
				//si se ha seleccionado grupo, el apunte debera de estar dentro del grupo
				if(idGrupo>0){
					if (idGrupo.compareTo(apunteForm.getIdGrupo())==0){
						//si se ha seleccionado clase, el apunte debera estar dentro de la clase
						if (idClase>0){
							if (idClase.compareTo(apunteForm.getIdClase())==0){
								lstApuntesFormReturn.add(apunteForm);
							}
						}
						else{
							lstApuntesFormReturn.add(apunteForm);
						}
					}
				}
				else{
					lstApuntesFormReturn.add(apunteForm);
				}
				
				
				
			}
			
		}
		
		
		return lstApuntesFormReturn;
		
	}*/
	
	public List<ApunteForm>getLstApuntesForm(Integer idCuenta, Integer idGrupo,
			Integer idClase, String fechaInicioStr, String fechaFinStr, String concepto, String tipo){
		
		Cuenta cuenta=ServiceUtils.getICuentaService().cargarCuenta(idCuenta);
		
		List<Apunte>lstApuntes=this.getLstApuntesAll(idCuenta);
		//List<Apunte>lstApuntes=this.getLstApuntes(idCuenta, idGrupo, idClase, fechaInicioStr, fechaFinStr, concepto, tipo);
		List<ApunteForm> lstApuntesFormAll=new ArrayList<ApunteForm>();
				
		for (int i=0;lstApuntes.size()>i;i++){
			ApunteForm apunteForm=new ApunteForm();
			if(FechaUtil.compararFechas(
					lstApuntes.get(i).getFecha(),FechaUtil.formatearADate(fechaInicioStr))<=0
				&& 	FechaUtil.compararFechas(lstApuntes.get(i).getFecha(),FechaUtil.formatearADate(fechaFinStr))>=0){
				
				if(idClase!=null && idClase>0){
					if (idClase.compareTo(lstApuntes.get(i).getClase().getIdClase())==0){
						//apunteForm=this.geApunte(lstApuntes.get(i).getIdApunte(), "");
						apunteForm=this.parsearApunte(lstApuntes.get(i));
					}
				}
				else{
					//apunteForm=this.geApunte(lstApuntes.get(i).getIdApunte(), "");
					apunteForm=this.parsearApunte(lstApuntes.get(i));
				}
			}			
			
			//Si es el primer registro, para calcular el saldo sumo al importe del apunte el saldo inicial de la cuenta
			if (i==0)
				apunteForm.setSaldo(lstApuntes.get(i).getImporte().add(new BigDecimal(cuenta.getSaldoInicial())));
			//Si no es el 1º registro, sumo importe del apunte actual con el importe del apunte anterior
			else
				apunteForm.setSaldo(lstApuntes.get(i).getImporte().add(lstApuntesFormAll.get(i-1).getSaldo()));
			
			lstApuntesFormAll.add(apunteForm);
		}
		
		//Filtro para añadir a la lista de apuntes tan solo los que se deben mostrar
		List<ApunteForm> lstApuntesFormReturn=new ArrayList<ApunteForm>();
		for (ApunteForm apunteForm:lstApuntesFormAll){
			//que la fecha del apunte este dentro del rango de fechas seleccionado por el usuario
			if (apunteForm.getFechaStr()!=null){
			
				if(FechaUtil.compararFechas(
						FechaUtil.formatearADate(apunteForm.getFechaStr()),	FechaUtil.formatearADate(fechaInicioStr))<=0
					&& 	FechaUtil.compararFechas(FechaUtil.formatearADate(apunteForm.getFechaStr()),FechaUtil.formatearADate(fechaFinStr))>=0){			
					//si se ha seleccionado grupo, el apunte debera de estar dentro del grupo
					
					if(idGrupo!=null && idGrupo>0){
						if (idGrupo.compareTo(apunteForm.getIdGrupo())==0){
							//si se ha seleccionado clase, el apunte debera estar dentro de la clase
							if (idClase!=null && idClase>0){
								if (idClase.compareTo(apunteForm.getIdClase())==0){
									if (concepto!=null && !concepto.isEmpty()){									
										if(apunteForm.getConcepto().contains(concepto))
											lstApuntesFormReturn.add(apunteForm);
									}
									else	
										lstApuntesFormReturn.add(apunteForm);
								}
							}
							else{
								if (concepto!=null && !concepto.isEmpty()){									
									if(apunteForm.getConcepto().contains(concepto.toUpperCase())||apunteForm.getConcepto().contains(concepto.toLowerCase()))
										lstApuntesFormReturn.add(apunteForm);
								}
								else	
									lstApuntesFormReturn.add(apunteForm);
							}
						}
					}
					else{
						if (concepto!=null && !concepto.isEmpty()){									
							if(apunteForm.getConcepto().contains(concepto.toUpperCase())||apunteForm.getConcepto().contains(concepto.toLowerCase()))
								lstApuntesFormReturn.add(apunteForm);
						}
						else
							lstApuntesFormReturn.add(apunteForm);
					}

				}
			}
		}
	
		return lstApuntesFormReturn;
		
	}
	
	
	/**
	 * Devuelve la lista de todos los apuntes contables de una cuenta
	 * @param idCuenta
	 * @param fechaInicioStr
	 * @param fechaFinStr
	 * @return
	 */
	private List<Apunte> getLstApuntesAll(Integer idCuenta){
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Apunte> lstApuntes=null;
		
		try{
			transaction=session.beginTransaction();
		
			Query query=null;
			StringBuffer sql=new StringBuffer();
			
			sql.append(" from Apunte apunte " +
					" left join fetch apunte.clase as clase " +
					" left join fetch apunte.clase.grupo as grupo " +
					" left join fetch apunte.cuenta as cuenta " +
					" where apunte.baja='N' ");
			
			sql.append(" and apunte.cuenta.idCuenta=:idCuenta " +
					   " order by apunte.fecha, apunte.idApunte ");
			
			
			//creamos la query
			query=session.createQuery(sql.toString());
			query.setParameter("idCuenta", idCuenta, Hibernate.INTEGER);
			lstApuntes=(List<Apunte>)query.list();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println("error sql:" + e);
		}
		finally {		 
			HibernateUtil.closeSession();
		 }
		
		return lstApuntes;
	}

	@Override
	public void saveReorganizarApuntes(ApunteListForm apunteListForm) {
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		try{
			transaction=session.beginTransaction();
			for (int i=0;apunteListForm.getIds().length>i;i++){
				Apunte apunte=new Apunte();
				apunte=(Apunte)session.get(Apunte.class, new Integer(apunteListForm.getIds()[i]));
				
				Cuenta cuenta=(Cuenta)session.get(Cuenta.class, apunteListForm.getIdCuenta());
				Clase clase=(Clase)session.get(Clase.class, apunteListForm.getIdClase());
				
				apunte.setCuenta(cuenta);
				apunte.setClase(clase);
								
				
			}
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null){ 
				transaction.rollback();
			}
		}
		finally{
			HibernateUtil.closeSession();	
		}
		
	}

	/**
	 * Transforma un apunte a apunteform
	 * @param apunte
	 * @return
	 */
	private ApunteForm parsearApunte(Apunte apunte) {
			
		ApunteForm apunteForm=new ApunteForm();
		
		apunteForm.setId(apunte.getIdApunte());
						
		apunteForm.setFechaStr(FechaUtil.formatear(apunte.getFecha(), new StringBuffer(), new FieldPosition(0)).toString());
		apunteForm.setTipoApunte(apunte.getTipoOperacion());
		apunteForm.setIdContabilidad(apunte.getCuenta().getContabilidad().getIdContabilidad());
		apunteForm.setIdCuenta(apunte.getCuenta().getIdCuenta());
		apunteForm.setIdGrupo(apunte.getClase().getGrupo().getIdGrupo());
		apunteForm.setNombreGrupo(apunte.getClase().getGrupo().getNombre());
		apunteForm.setIdClase(apunte.getClase().getIdClase());
		apunteForm.setNombreClase(apunte.getClase().getNombre());
		apunteForm.setConcepto(apunte.getConcepto());
		apunteForm.setImporteStr(apunte.getImporte().toString().replace("-", ""));
		apunteForm.setImporte(new BigDecimal(apunte.getImporte().toString().replace("-", "")));
		apunteForm.setNotas(apunte.getNotas());
		apunteForm.setTraspaso(apunte.getTraspaso());
					
		return apunteForm;
	}

	@Override
	public List<Apunte> getLstApuntesAgrupadosConceptos(Integer idCuenta, Integer idGrupo,
			Integer idClase, String fechaInicioStr, String fechaFinStr, String concepto, String tipo){
		
		Session session=HibernateUtil.currentSession();
		Transaction transaction = null;
		List<Apunte> lstApuntes=null;
		
		//Tratamiento de fechas
		Date fechaFin=null;
				
		if (fechaFinStr==null || fechaFinStr.isEmpty())
			fechaFin=FechaUtil.getDate();
		else
			fechaFin=FechaUtil.formatearADate(fechaFinStr);
		
		try{
			transaction=session.beginTransaction();
		
			Query query=null;
			StringBuffer sql=new StringBuffer();
			
			sql.append(" from Apunte apunte where apunte.baja='N' ");
			
			if (fechaInicioStr!=null && !fechaInicioStr.isEmpty())
				sql.append(" and apunte.fecha between :fechaInicio and :fechaFin ");
				
					
			if (idCuenta!=null && idCuenta>0){
				sql.append(	" and apunte.cuenta.idCuenta=:idCuenta " +
							" and apunte.cuenta.baja='N' ");
			}
			
			if (idGrupo!=null && idGrupo>0){
				sql.append(	" and apunte.clase.idClase in " +
							" (select clase.idClase from Clase clase" +
							" where clase.grupo.idGrupo=:idGrupo " +
							" and clase.grupo.baja='N') ");
			}
			if (idClase!=null && idClase>0){
				sql.append(	" and apunte.clase.idClase=:idClase" +
				" and apunte.clase.baja='N' ");
			}
			
			if (idClase!=null && idClase>0){
				sql.append(	" and apunte.clase.idClase=:idClase" +
				" and apunte.clase.baja='N' ");
			}
			
			if (concepto!=null && !concepto.isEmpty()){
				sql.append(" and apunte.concepto like '%"+concepto+"%' ");
				
			}
			sql.append(" group by apunte.concepto ");
			sql.append(" order by apunte.fecha ");
			
			//creamos la query
			query=session.createQuery(sql.toString());
			
			if (fechaInicioStr!=null && !fechaInicioStr.isEmpty()){
				query.setParameter("fechaInicio", FechaUtil.formatearADate(fechaInicioStr), Hibernate.DATE);
				query.setParameter("fechaFin", fechaFin, Hibernate.DATE);
			}
			
			if (idCuenta!=null && idCuenta>0){
				query.setParameter("idCuenta", idCuenta, Hibernate.INTEGER);
			}
			
			if (idGrupo!=null && idGrupo>0){
				query.setParameter("idGrupo", idGrupo, Hibernate.INTEGER);
			}
			if (idClase!=null && idClase>0){
				query.setParameter("idClase", idClase, Hibernate.INTEGER);
			}
			
			lstApuntes=(List<Apunte>)query.list();
			
			session.getTransaction().commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			System.out.println("error sql:" + e);
		}
		finally {		 
			HibernateUtil.closeSession();
		 }
		
		return lstApuntes;
	}
}
