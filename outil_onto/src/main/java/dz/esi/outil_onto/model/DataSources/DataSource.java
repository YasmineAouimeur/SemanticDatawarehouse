package dz.esi.outil_onto.model.DataSources;

import org.apache.jena.rdf.model.Model;

public class DataSource {
	TypeSource type=null;
	Model dataset=null;

	public Model getDataset() {
		return dataset;
	}

	public void setDataset(Model data) {
		this.dataset = data;
	}

	public DataSource(TypeSource type) {
		this.type = type;
	}

	public TypeSource getType() {
		return type;
	}

	public void setType(TypeSource type) {
		this.type = type;
	}
	
	// run a query in an external resource
	public Model run(String query, String resource){
		return null;
	}
	// reun a query in an intermediate resource
	public Model run(String query, Model m){
		return null;
	}
	//run a query in an internal resource 
	public Model run(String query){
		return null;
	}
	
}
