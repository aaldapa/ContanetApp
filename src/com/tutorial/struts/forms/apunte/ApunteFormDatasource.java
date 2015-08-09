package com.tutorial.struts.forms.apunte;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ApunteFormDatasource implements JRDataSource {

	private int indiceApunteActual = -1;
	List<ApunteForm> lstApuntesForm=new ArrayList<ApunteForm>();
	
	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		Object valor = null;  

	    
	    if("fecha".equals(jrField.getName())) 
	    { 
	        valor = lstApuntesForm.get(indiceApunteActual).getFecha(); 
	    } 
	    else if("tipo".equals(jrField.getName())) 
	    { 
	        valor = lstApuntesForm.get(indiceApunteActual).getTipoApunte(); 
	    } 
	    else if("concepto".equals(jrField.getName())) 
	    { 
	        valor = lstApuntesForm.get(indiceApunteActual).getConcepto(); 
	    } 
	    else if("nombreGrupo".equals(jrField.getName())) 
	    { 
	        valor = lstApuntesForm.get(indiceApunteActual).getNombreGrupo(); 
	    }
	    else if("nombreClase".equals(jrField.getName())) 
	    { 
	        valor = lstApuntesForm.get(indiceApunteActual).getNombreClase(); 
	    }
	    else if("importe".equals(jrField.getName())) 
	    { 
	        if (lstApuntesForm.get(indiceApunteActual).getTipoApunte().equalsIgnoreCase("GASTO"))
	    	   	valor = new BigDecimal (new String("-").concat(lstApuntesForm.get(indiceApunteActual).getImporte().toString()));
	        else
	        	valor = lstApuntesForm.get(indiceApunteActual).getImporte();
	    }
	    else if("saldo".equals(jrField.getName())) 
	    { 
	        valor = lstApuntesForm.get(indiceApunteActual).getSaldo(); 
	    }
	 
	    return valor; 
	}

	@Override
	public boolean next() throws JRException {
		  return ++indiceApunteActual < lstApuntesForm.size();
	}

	public void addApunteForm(ApunteForm apunteForm){
        this.lstApuntesForm.add(apunteForm);
    }

	public int getIndiceApunteActual() {
		return indiceApunteActual;
	}

	public void setIndiceApunteActual(int indiceApunteActual) {
		this.indiceApunteActual = indiceApunteActual;
	}

	public List<ApunteForm> getLstApuntesForm() {
		return lstApuntesForm;
	}

	public void setLstApuntesForm(List<ApunteForm> lstApuntesForm) {
		this.lstApuntesForm = lstApuntesForm;
	}
}
