package com.tutorial.struts.utils;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfree.util.StringUtils;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.loom.converter.date.UtilDateAdapterFormat;

public class FechaUtil {

  	 private static String PATRON_DEFECTO = "dd/MM/yyyy";
     // Valida el patron por defecto de la aplicacion
	 private static final String PATRON_DEFECTOddmmyyyy = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
	 private static final String PATRON_DEFECTO_HORA_12HORAS = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
	 private static final String PATRON_DEFECTO_HORA_24HORAS = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	 
	 public static String PATRON_DEFECTO_HORA="HH:mm";
     public static String PATRON_HORAS_MINUTOS = "dd/MM/yyyy dd:mm";
     private Pattern patternFecha;
     private Pattern patternHora12Horas;
     private Pattern patternHora24Horas;
	 private Matcher matcher;
	
	 public FechaUtil() {   
		 patternFecha = Pattern.compile(PATRON_DEFECTOddmmyyyy);
		 patternHora12Horas = Pattern.compile(PATRON_DEFECTO_HORA_12HORAS);
		 patternHora24Horas = Pattern.compile(PATRON_DEFECTO_HORA_24HORAS);
	 }

     /* Entre nuestro Hosting en España y el Hosting en EEUU hay una diferencia de 
     * 9 Horas por tanto la hora se ha de obtener desde este Lugar */
     
     public static Date getDate(){
    	DateTime horaActual = new DateTime();
    	return horaActual.toDate();
     }
     
     /*
     * Añade a un String buffer y a una posicion determinada
     * la fecha en el formato por defecto
     * */
     public static StringBuffer formatear(Date fecha,StringBuffer anadirA, FieldPosition posicion) {
         
         UtilDateAdapterFormat dateFmt = new UtilDateAdapterFormat();
	     DateTimeFormatter formatter = DateTimeFormat.forPattern(PATRON_DEFECTO);
	     dateFmt.setFormatter(formatter);
	     return dateFmt.format(fecha,anadirA, posicion);
     }
     /*
     * Añade a un String buffer y a una posicion determinada
     * la fecha en el formato indicado
     * */
     public static StringBuffer formatear(Date fecha,StringBuffer anadirA, FieldPosition posicion,String patron) {
         
         UtilDateAdapterFormat dateFmt = new UtilDateAdapterFormat();
	     DateTimeFormatter formatter = DateTimeFormat.forPattern(patron);
	     dateFmt.setFormatter(formatter);
	     return dateFmt.format(fecha,anadirA, posicion);
     }
     
  public static String formatearAHora(Date fecha) {
	  	 
	  	 DateTime fechaParsear = new DateTime(fecha); 
	  	 String hora = ""+ fechaParsear.getHourOfDay();
	  	 String minutos = ""+ fechaParsear.getMinuteOfHour();
	  	 if (hora.length() == 1)
	  		hora= "0"+hora;
	  	if (minutos.length() == 1)
	  		minutos= "0"+minutos;
	  	 return hora+":"+minutos; 
     }
    /*
     * Devuelve la fecha actual a String
     * */ 
	public static StringBuffer getFechaActual() {
		
		java.util.Date unDate = FechaUtil.getDate();
		java.sql.Date fechaHora = new java.sql.Date(unDate.getTime());
		return formatear( fechaHora, new StringBuffer(),new FieldPosition(0) );
	}
	
	
	/*
	 * Añade a un String buffer y a una posicion determinada 
	 * la fecha en el formato indicado por defecto 
	 * 
	 * */
	
	public static java.util.Date formatearADate(String fecha) {
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern(PATRON_DEFECTO);  
        	return  formatter.parseDateTime(fecha).toDate(); 
	}
	/*
	 * Añade a un String buffer y a una posicion determinada 
	 * la fecha en el formato indicado por parametro 
	 * 
	 * */
	public static java.util.Date formatearADate(String fecha,String patron) {
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern(patron);  
        	return  formatter.parseDateTime(fecha).toDate(); 
	}
	
	/* Devuelve el dia de la semana de la fecha indicada **/
	public static String obtenerDia(String fecha) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(PATRON_DEFECTO);  
		String obj =formatter.parseDateTime(fecha).dayOfWeek().getAsText(Locale.ENGLISH).toUpperCase();
        return  formatearDiaDeInglesabd(obj); 
	}
	
	/* Intervalo entre Horas **/
	public static List<Date> intervaloEntreHoras(java.util.Date horaInicio,
											  java.util.Date horaFin,
											  int horas,int minutos,int segundos,int milisegundos) {
		List<Date> horasIntervalo = new ArrayList<Date>();
		// Construyo un periodo que en este caso es hasta 
		// 0 horas xx minutos 0 segundos 0 milisegundos.
		Period periodo = new Period(horas, minutos, segundos, milisegundos);
		// Hora de Inicio
        DateTime horaInicioTime = new DateTime(horaInicio); 
        // Hora de Fin  
        DateTime horaFinTime = new DateTime(horaFin);
        // Añado la primera hora del intervalo
          	while(horaFinTime.isAfter(horaInicioTime) || horaFinTime.isEqual(horaInicioTime)){
          		horasIntervalo.add(horaInicioTime.toDate());
        		horaInicioTime = horaInicioTime.plus(periodo);
        	}
		 return horasIntervalo;
	}
	
	
	/** Devuelve una hora Target valida entre un intervalo de horas Intervalo entre Horas, 
	 * imaginemonos que tenemos un rango de horas que va desde las 10:00 hasta las 12:00 con un intervalo de 15 minutos,
	 * y nos entra una hora target de 11:10 el metodo nos devuelve la hora mas aproximada igual o superior dentro del rango 
	 * en este caso devolveria 11:15 si la hora no esta dentro del rango devolvera un null **/
	public static Date siguienteHoraValidaIntervaloEntreHoras(java.util.Date horaInicio,java.util.Date horaFin,
											  int horas,int minutos,int segundos,int milisegundos,java.util.Date horaTarget) {
	
		Date horasDevol = null;
		DateTime horaTargetTime = new DateTime(horaTarget); 
		List<Date> horasIntervalo =intervaloEntreHoras(horaInicio,horaFin,horas,minutos,segundos,milisegundos);
		for (Date horaDevol :horasIntervalo){
		     // Hora de Fin  
	        DateTime horaDevolTime = new DateTime(horaDevol);
	          if ( horaDevolTime.isAfter(horaTargetTime) || horaDevolTime.isEqual(horaTargetTime)){
	        	return horaDevolTime.toDate();
	        }
		}
		return horasDevol;
	}
	
	/** Devuelve una hora Target valida entre un intervalo de horas Intervalo entre Horas, 
	 * imaginemonos que tenemos un rango de horas que va desde las 10:00 hasta las 12:00 con un intervalo de 15 minutos,
	 * y nos entra una hora target de 11:10 el metodo nos devuelve la hora mas aproximada igual o superior dentro del rango 
	 * en este caso devolveria 11:15 si la hora no esta dentro del rango devolvera un null **/
	public static Date siguienteHoraValidaIntervaloEntreHoras(List<Date>  horasIntervalo,java.util.Date horaTarget) {
	
		Date horasDevol = null;
		DateTime horaTargetTime = new DateTime(horaTarget); 
		for (Date horaDevol :horasIntervalo){
		     // Hora de Fin  
	        DateTime horaDevolTime = new DateTime(horaDevol);
	          if ( horaDevolTime.isAfter(horaTargetTime) || horaDevolTime.isEqual(horaTargetTime)){
	        	return horaDevolTime.toDate();
	        }
		}
		return horasDevol;
	}
	
	/** Devuelve una hora Target valida entre un intervalo de horas Intervalo entre Horas, 
	 * imaginemonos que tenemos un rango de horas que va desde las 10:00 hasta las 12:00 con un intervalo de 15 minutos,
	 * y nos entra una hora target de 11:10 el metodo nos devuelve la hora mas aproximada igual o inferior dentro del rango 
	 * en este caso devolveria 11:05 si la hora no esta dentro del rango devolvera un null**/
	
	public static Date anteriorHoraValidaIntervaloEntreHoras(List<Date>  horasIntervalo,java.util.Date horaTarget) {
		
		Date horasDevol = null;
		DateTime horaTargetTime = new DateTime(horaTarget); 
		// Doy la vuelta a la lista 
		Collections.reverse(horasIntervalo);
		for (Date horaDevol :horasIntervalo){
		     // Hora de Fin  
	        DateTime horaDevolTime = new DateTime(horaDevol);
	          if ( horaDevolTime.isBefore(horaTargetTime) || horaDevolTime.isEqual(horaTargetTime)){
	        	 //
	        	 Collections.sort(horasIntervalo);
	        	return horaDevolTime.toDate();
	        }
		}
		Collections.sort(horasIntervalo);
		return horasDevol;
	} 
	
	public static Date establecerFecha1970AHoras(java.util.Date horas) {
		DateTime horasTime = new DateTime(horas);
		DateTime horasDevol = new DateTime(1970,1,1,
				    horasTime.getHourOfDay(), 
				    horasTime.getMinuteOfHour(),
				    horasTime.getSecondOfMinute(), 0);
		return horasDevol.toDate();
	}
	
	
	public static Date establecerFechaAHoras(java.util.Date fechaEstablecimiento,
			  								 java.util.Date horas) {
		
		DateTime fechaEstablecimientoTime = new DateTime(fechaEstablecimiento); 
		DateTime horasTime = new DateTime(horas);
		DateTime horasDevol = new DateTime(fechaEstablecimientoTime.getYear(), 
										    fechaEstablecimientoTime.getMonthOfYear(),
										    fechaEstablecimientoTime.getDayOfMonth(),
										    horasTime.getHourOfDay(), 
										    horasTime.getMinuteOfHour(),
										    horasTime.getSecondOfMinute(), 0);
		return horasDevol.toDate();
	}
	
	/* Dada una hora en formato String hh:mm:ss se tokeniza y se añade a la fecha especificada  **/
	public static Date formatearStringAHoraDeFecha(String hora, Date fechaEstablecimiento) {
	
		String[] horasActuales = hora.split(":");
	 DateTime fechaEstablecimientoTime = new DateTime(fechaEstablecimiento); 
		DateTime horasDevol = new DateTime(fechaEstablecimientoTime.getYear(), 
										    fechaEstablecimientoTime.getMonthOfYear(),
										    fechaEstablecimientoTime.getDayOfMonth(),
										    new Integer(horasActuales[0]).intValue(), 
										    new Integer(horasActuales[1]).intValue(),
										    new Integer(horasActuales[2]).intValue(), 0);
		return horasDevol.toDate();
  }
	
	/* Intervalo entre Horas  **/
	public static boolean existeEntreHoras(java.util.Date horaInicio,
										   java.util.Date horaFin,
										   java.util.Date horaTarget,
										   boolean ignorarIgualdad) {
		// Hora de Inicio
        DateTime horaInicioTime = new DateTime(horaInicio); 
        // Hora de Fin
        DateTime horaFinTime = new DateTime(horaFin);
        // Hora de Fin
        DateTime horaTargetTime = new DateTime(horaTarget);
        if ( !ignorarIgualdad ){
        	if ( horaTargetTime.isEqual(horaInicioTime) || horaTargetTime.isEqual(horaFinTime))
        		return true;
        }
        if ( horaTargetTime.isAfter(horaInicioTime) && horaTargetTime.isBefore(horaFinTime))
        	return true;
        
		 return false;
	}
	
	/* Devuelve un calculo de la hora Origen mas las horas minutos o segundos que se introduzcan **/
	public static Date sumarPeriodo(java.util.Date hora, int horas,int minutos,int segundos,int milisegundos) {
		// Construyo un periodo que en este caso es hasta 
		// 0 horas xx minutos 0 segundos 0 milisegundos.
		DateTime horaTime = new DateTime(hora);
		Period periodo = new Period(horas, minutos, segundos, milisegundos);
		return horaTime.plus(periodo).toDate();
	}
	
	/* Hora posterior a la actual comparo con la hora actual de la base de datos
	 * que siempre da 1970-01-01 y HH:MM de forma correcta**/
	public static boolean posteriorHoraActualDB(java.util.Date hora ) {
	    DateTime horaComparable = new DateTime(hora); 
	    DateTime horaActual = new DateTime( FechaUtil.getDate()); 
	    DateTime horaActual1970 = new DateTime(1970, 1, 1, horaActual.getHourOfDay(),horaActual.getMinuteOfHour(), horaActual.getSecondOfMinute(), 0);
        return horaComparable.isAfter(horaActual1970);
	}
	
	/* Hora posterior a la actual comparo con la hora actual 
	 * y fecha actual **/
	public static boolean posteriorHoraActualSystema(java.util.Date hora ) {
	    DateTime horaComparable = new DateTime(hora); 
	      return horaComparable.isAfterNow();
	}
	
	/* Hora posterior a la actual comparo con la hora actual  y fecha actual y le añado las horas preferidas por el usuario
	 * en este caso se ha decidido que van a ser 4 horas .**/
	public static boolean posteriorHoraActualSystemaExtranet(java.util.Date hora ) {
	    
		DateTime horaComparable = new DateTime(hora); 
		DateTime fechaNueva = new DateTime(FechaUtil.getDate());
		fechaNueva= fechaNueva.plusHours(2);
	    return horaComparable.isAfter(fechaNueva);
	    
	}
	
	/* Comparo fechas ignorando las horas **/
	public static boolean posteriorFechaActual(java.util.Date fecha ) {
		LocalDate fechaParam = new LocalDate(fecha);
		LocalDate hoy = new LocalDate(FechaUtil.getDate());
		
		LocalDate fechaParamComparable = new LocalDate(fechaParam.getYear(), fechaParam.getMonthOfYear(), fechaParam.getDayOfMonth());
		LocalDate fechaActual = new LocalDate(hoy.getYear(), hoy.getMonthOfYear(), hoy.getDayOfMonth());
		
	    return fechaParamComparable.isAfter(fechaActual);
	}
	/* Comparo fechas ignorando las horas **/
	public static boolean equalsFechaActual(java.util.Date fecha ) {
		LocalDate fechaParam = new LocalDate(fecha);
		LocalDate hoy = new LocalDate(FechaUtil.getDate());
		
		LocalDate fechaParamComparable = new LocalDate(fechaParam.getYear(), fechaParam.getMonthOfYear(), fechaParam.getDayOfMonth());
		LocalDate fechaActual = new LocalDate(hoy.getYear(), hoy.getMonthOfYear(), hoy.getDayOfMonth());
		
	    return fechaParamComparable.isEqual(fechaActual);
	}
	
	/* Comparo fechas si son iguales devuelvo un 0, si fecha1 es menor que fecha2 devuelvo un 1 y si no un -1  **/
	public static int compararFechas(java.util.Date fecha1 , java.util.Date fecha2) {
		LocalDate fecha1Param = new LocalDate(fecha1);
		LocalDate fecha2Param = new LocalDate(fecha2);
		
		LocalDate fecha1ParamComparable = new LocalDate(fecha1Param.getYear(), fecha1Param.getMonthOfYear(), fecha1Param.getDayOfMonth());
		LocalDate fecha2ParamComparable = new LocalDate(fecha2Param.getYear(), fecha2Param.getMonthOfYear(), fecha2Param.getDayOfMonth());
		if (fecha1ParamComparable.isEqual(fecha2ParamComparable))
			return 0;
		if (fecha1ParamComparable.isAfter(fecha2ParamComparable))
			return -1;
		
	    return 1;
	}
	
	/* Comparo horas y fechas si son iguales devuelvo un 0, si fecha1 es menor que fecha2 devuelvo un 1 y si no un -1  **/
	public static int compararFechaHoras(java.util.Date fecha1 , java.util.Date fecha2) {
		DateTime fecha1Param = new DateTime(fecha1);
		DateTime fecha2Param = new DateTime(fecha2);
		
		if (fecha1Param.isEqual(fecha2Param))
			return 0;
		if (fecha1Param.isAfter(fecha2Param))
			return -1;
		
	    return 1;
	}
	
	
	
	public static int restarFechas(java.util.Date fecha1 , java.util.Date fecha2) {
		DateMidnight jodafec1 = new DateMidnight(fecha1);
		DateMidnight jodafec2 = new DateMidnight(fecha2);
		Period periodo = new Period(jodafec2.getMillis()-jodafec1.getMillis());
	    return periodo.getMinutes();
	}
	
	 /*
     * Formatea el campo dia almacenado en la base de datos (L,M,X,J,V,S,F)
     * al nombre del día de la semana (lunes, martes, miercoles, jueves, viernes, sabado, domingo)
     * */
	
    public static String formatearDiaDeInglesabd(String diaIngles)
    {
   	 if (diaIngles.equalsIgnoreCase("Monday"))
   		 return("L");
   	 else if (diaIngles.equalsIgnoreCase("Tuesday"))
   		 return("M");
   	 else if (diaIngles.equalsIgnoreCase("Wednesday"))
   		 return("X");
   	 else if (diaIngles.equalsIgnoreCase("Thursday"))
   		 return("J");
   	 else if (diaIngles.equalsIgnoreCase("Friday"))
   		 return("V");
   	 else if (diaIngles.equalsIgnoreCase("Saturday"))
   		 return("S");
   	 else
   		 return("F");
    }
  
  
    
     /*
      * Formatea el campo dia almacenado en la base de datos (L,M,X,J,V,S,F)
      * al nombre del día de la semana (lunes, martes, miercoles, jueves, viernes, sabado, domingo)
      * */
     public static String formatearDia(String dia)
     {
    	 if (dia.equals("L"))
    		 return("dia.lunes");
    	 else if (dia.equals("M"))
    		 return("dia.martes");
    	 else if (dia.equals("X"))
    		 return("dia.miercoles");
    	 else if (dia.equals("J"))
    		 return("dia.jueves");
    	 else if (dia.equals("V"))
    		 return("dia.viernes");
    	 else if (dia.equals("S"))
    		 return("dia.sabado");
    	 else
    		 return("dia.domingo");
     }
     
     /*
      * Formatea el campo dia almacenado en la base de datos (L,M,X,J,V,S,F)
      * a un valor Integer para poder ordenarlo (1,2,3,4,5,6,7)
      * */
     public static Integer formatearDiaInteger(String dia)
     {
    	 if (dia.equals("L"))
    		 return new Integer(1);
    	 else if (dia.equals("M"))
    		 return new Integer(2);
    	 else if (dia.equals("X"))
    		 return new Integer(3);
    	 else if (dia.equals("J"))
    		 return new Integer(4);
    	 else if (dia.equals("V"))
    		 return new Integer(5);
    	 else if (dia.equals("S"))
    		 return new Integer(6);
    	 else
    		 return new Integer(7);
     }
     
     /*
      * Formatea el campo dia almacenado en la base de datos (L,M,X,J,V,S,F)
      * a un valor Integer para poder ordenarlo (1,2,3,4,5,6,7)
      * */
     public static String formatearDiaString(Integer dia)
     {
    	 if (dia.intValue() == 1)
    		 return "L";
    	 else if (dia.intValue() == 2)
    		 return "M";
    	 else if (dia.intValue() == 3)
    		 return "X";
    	 else if (dia.intValue() == 4)
    		 return "J";
    	 else if (dia.intValue() == 5)
    		 return "V";
    	 else if (dia.intValue() == 6)
    		 return "S";
    	 else
    		 return "F";
     }
     
   
     public static int edad(String fecha_nac) {     //fecha_nac debe tener el formato dd/MM/yyyy
    	   
    	    Date fechaActual = new Date();
    	    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    	    String hoy = formato.format(fechaActual);
    	    String[] dat1 = fecha_nac.split("/");
    	    String[] dat2 = hoy.split("/");
    	    int anos = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
    	    int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
    	    if (mes < 0) {
    	      anos = anos - 1;
    	    } else if (mes == 0) {
    	      int dia = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
    	      if (dia > 0) {
    	        anos = anos - 1;
    	      }
    	    }
    	    return anos;
    	  }
     /**
      * 
      * @param lstMeses: meses en formato entero (Enero-1, Febrero-2,...) 
      * @param lstDiasSemana : dias en formato entero (Lunes-1, Martes-2,...)
      * @param anio: año del cálculo
      * @return lista de fechas que cumplen estar en las dos listas
      */
     public static List<DateTime> diasEnAnio(Set<Integer> lstMeses, Set<Integer> lstDiasSemana,Integer anio)
     {
    	 List<DateTime> lstDias = new ArrayList<DateTime>();
    	 DateTime fecha = new DateTime(anio,1,1,0,0,0,0);
    	 for(int i=1;i<=365;i++){
    		 for(Integer month : lstMeses){
    			 if(fecha.getMonthOfYear()==month){
    				 for(Integer day : lstDiasSemana){
    					 if(fecha.getDayOfWeek()==day){
    						 lstDias.add(fecha);
    						 
    					 }
    				 }
    			 }
    		 }
    		 fecha = fecha.plusDays(1);
    		 
    	 }
    	 return lstDias;
     }
     
	  /**
	   * Valida la fecha con una expresion regular
	   * @param date 
	   * @return true valido dateformat, false invalid0 dateformat
	   * Son validos los siguientes formatos:
	   * 1) "1/1/2010" , "01/01/2020"
	   * 2) "31/1/2010", "31/01/2020"
	   * 3) "29/2/2008", "29/02/2008"
	   * 4) "28/2/2009", "28/02/2009"
	   * 5) "31/3/2010", "31/03/2010"
	   * 6) "30/4/2010", "30/04/2010"
	   * 7) "31/5/2010", "31/05/2010"
	   * 8) "30/6/2010", "30/06/2010"
	   * 9) "31/7/2010", "31/07/2010"
	   * 10) "31/8/2010", "31/08/2010"
	   * 11) "30/9/2010", "30/09/2010"
	   * 12) "31/10/2010", "31/10/2010"
	   * 13) "30/11/2010", "30/11/2010"
	   * 14) "31/12/2010", "31/12/2010"
	   * NO SON VALIDOS
	   * 1)"32/1/2010" ,"32/01/2020" – day is out of range [1-31]
	   * 2)"1/13/2010","01/01/1820" – month is out of range [1-12], year is out of range [1900-2999]
	   * 3)"29/2/2007","29/02/2007" – 2007 is not leap year, only has 28 days
	   * 4)"30/2/2008","31/02/2008" – leap year in February has 29 days only
	   * 5)"29/a/2008","a/02/2008" – month is invalie, day is invalid
	   * 6)"333/2/2008","29/02/200a” – day is invalid, year is invalid
	   * 7)"31/4/20100","31/04/2010" – April has 30 days only
	   * 8 )"31/6/2010","31/06/2010" -June has 30 days only
	   * 9)"31/9/2010","31/09/2010" – September has 30 days only
	   * 10)"31/11/2010" – November has 30 days only
	   */
     
	  public boolean validarFecha(final String date){
		
		  matcher = patternFecha.matcher(date);
		  if(matcher.matches()){
			  matcher.reset();
			  if(matcher.find()){
				  String day = matcher.group(1);
				  String month = matcher.group(2);
				  int year = Integer.parseInt(matcher.group(3));
				  if (day.equals("31") &&  (month.equals("4") || month .equals("6") || month.equals("9") || month.equals("11") ||
								  month.equals("04") || month .equals("06") || month.equals("09")))
				  {
					  return false; // only 1,3,5,7,8,10,12 has 31 days
				  } else if (month.equals("2") || month.equals("02")) {
					  if(year % 4==0){ //leap year
						  if(day.equals("30") || day.equals("31")){
							  return false;
						  }else{
							  return true;
						  }
					  }else{
						  if(day.equals("29")||day.equals("30")||day.equals("31")){
							  return false;
						  }else{
							  return true;
						  }
					  }
				  }else{
					return true;

				  }
			  }else{
				  return false;
			  }
		  }else{
			  return false;
		  }

	  }

	  /**
	   * Valida la hora (formato 12 horas am/pm) con una expresion regular
	   * @param date 
	   * @return true valido dateformat, false invalid dateformat
	   * Son validos los siguientes formatos:
	   * 1) "1:00am", "1:00 am","1:00 AM",
	   * 2) "1:00pm", "1:00 pm", "1:00 PM",
	   * 3) "12:50 pm"
	   * NO SON VALIDOS
	   * 1) "0:00 am" – hour is out of range [1-12]
	   * 2) "10:00 am" – only one white space is allow
	   * 3) '1:00' – must end with am or pm
	   * 4) "23:00 am" -24-hour format is not allow
	   * 5) "1:61 pm" – minute is out of range [0-59]
	   * 6) "13:00 pm" – hour is out of range [1-12]
	   * 7) "001:50 pm" – invalid hour format
	   * 8) "10:99 am" – minute is out of range [0-59]
	   * 9) "01:00 pm" – 24-hour format is not allow
	   * 10) "1:00 bm" – must end with am or pm
	   */
	  public boolean validarHora12Horas(final String time){		  
		  matcher = patternHora12Horas.matcher(time);
		  return matcher.matches();	    	    
	  }
	  
	  /**
	   * Valida la hora (formato 12 horas am/pm) con una expresion regular
	   * @param date 
	   * @return true valido dateformat, false invalid dateformat
	   * Son validos los siguientes formatos:
	   * 1) '01:00', '02:00', '13:00',
	   * 2) '1:00', '2:00', '13:01',
	   * 3) '23:59','15:00'
	   * 4) '00:00','0:00'
	   * NO SON VALIDOS
	   * 1) '24:00' – hour is out of range [0-23]
	   * 2) '12:60' – minute is out of range [00-59]
	   * 3) '0:0' – invalid format for minute, at least 2 digits
	   * 4) '13:1' – invalid format for minute, at least 2 digits
	   * 5) '101:00' – hour is out of range [0-23]
	   */
	  public boolean validarHora24Horas(final String time){		  
		  matcher = patternHora24Horas.matcher(time);
		  return matcher.matches();	    	    
	  }
}
