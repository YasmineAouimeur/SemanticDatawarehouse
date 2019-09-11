package dz.esi.outil_onto.model.ETLOperators;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

public class Agregate extends ETLOperation implements iOperation{
	
	String ETLQuery="";
	AggFunction aggFunct;
	
	// Constructors
	public Agregate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Agregate(String uriName, String Resource, String Position) {
		super(uriName, Resource, Position);
	}
	
	//Getter and setters
	public typeETLOp getType() {
		return type;
	}

	public void setType(typeETLOp type) {
		this.type = type;
	}

	public String getETLQuery() {
		return ETLQuery;
	}

	public void setETLQuery(String eTLQuery) {
		ETLQuery = eTLQuery;
	}


	@Override
	public Model run() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
