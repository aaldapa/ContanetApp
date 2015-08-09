package com.tutorial.struts.forms.apunte;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.LinkedHashMap;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class ConceptoApunteBean implements Serializable, JSONStreamAware {

	private Integer id;
	private String concepto;
	
	public ConceptoApunteBean() {
		super();
	}
	
	public ConceptoApunteBean(Integer id, String concepto) {
		super();
		this.id = id;
		this.concepto = concepto;
	}
	
	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	public String getConcepto() {return concepto;}
	public void setConcepto(String concepto) {this.concepto = concepto;}


	@Override
	public void writeJSONString(Writer out) throws IOException {
		 LinkedHashMap obj = new LinkedHashMap();
		  obj.put("value", new Integer(id));
		  obj.put("label", concepto);
         JSONValue.writeJSONString(obj, out);

	}

}
