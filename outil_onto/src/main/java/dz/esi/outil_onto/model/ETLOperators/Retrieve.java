package dz.esi.outil_onto.model.ETLOperators;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;

import dz.esi.outil_onto.model.DataSources.DataSource;
import dz.esi.outil_onto.model.DataSources.ExtSource;
import dz.esi.outil_onto.model.DataSources.IntSource;
import dz.esi.outil_onto.model.DataSources.IntermSource;
import dz.esi.outil_onto.model.DataSources.TypeSource;
import dz.esi.outil_onto.model.OntologyManager.openOWL;

public class Retrieve extends ETLOperation implements iOperation {
	
	private static final TypeSource Internal = null;
	private static final TypeSource External = null;
	private static final TypeSource Intermediate = null;
	String ETLQuery="";
	
	
	//conctructors
	public Retrieve() {
		super();
	}
	public Retrieve(String uriName, String Resource, String Position) {
		super(uriName, Resource, Position);
	}
	
	//Setters and getters 
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
	public Model run(DataSource DS) {
		Model output = null;
		System.out.println("Je suis retrieve  :\n");
		if(DS.getType()==TypeSource.Internal){
			System.out.println("Je suis interne  : "+query()+"\n");
			output = ((IntSource) DS).run(query());
			//System.out.println("Le resultat est \n : "+ETL.run(query,ETL.run(query()))+"\n");
		}else{
			if(DS.getType()==TypeSource.External){
				output = DS.run(query(), ((ExtSource)DS).getResource());
				System.out.println("Je suis externe  "+query()+":\n");
			}else{
				if(DS.getType()==TypeSource.Intermediate){
					output = DS.run(query(), ((IntermSource)DS).getDataset());
					System.out.println("Je suis intermediaire  "+query()+":\n");
				}
			}
		}
		
		return output;
	}
	@Override
	public String query() {
		String query="";
		query= ETL.Retrieve(this.Resource);
		this.ETLQuery= query; 
		return query;
	}
	
	
}
