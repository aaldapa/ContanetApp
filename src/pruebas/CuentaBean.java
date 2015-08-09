package pruebas;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.LinkedHashMap;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class CuentaBean implements Serializable, JSONStreamAware {
	
	private int id;
    private String label;
     
    public CuentaBean(int id, String label) {
		super();
		this.id = id;
		this.label = label;
	}
    
	@Override
	public void writeJSONString(Writer out) throws IOException {
		  LinkedHashMap obj = new LinkedHashMap();
		  obj.put("id", new Integer(id));
		  obj.put("label", label);
          JSONValue.writeJSONString(obj, out);

	}
	
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getLabel() {return label;}
	public void setLabel(String label) {this.label = label;}
}
