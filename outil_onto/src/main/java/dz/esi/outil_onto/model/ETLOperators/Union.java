package dz.esi.outil_onto.model.ETLOperators;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

import dz.esi.outil_onto.model.DataSources.DataSource;
import dz.esi.outil_onto.model.DataSources.ExtSource;
import dz.esi.outil_onto.model.DataSources.IntSource;
import dz.esi.outil_onto.model.DataSources.IntermSource;
import dz.esi.outil_onto.model.DataSources.TypeSource;

public class Union extends ETLOperation implements iOperation {
	
	String ETLQuery="";
	
	//Constructors
	public Union() {
		super();
	}
	public Union(String uriName, String Resource, String Position) {
		super(uriName, Resource, Position);
	}
	
	//Setters and getters
	public String getETLQuery() {
		return ETLQuery;
	}
	public void setETLQuery(String eTLQuery) {
		ETLQuery = eTLQuery;
	}
	public typeETLOp getType() {
		return type;
	}
	public void setType(typeETLOp type) {
		this.type = type;
	}
	
	// Methodes
	@Override
	public Model run(DataSource DS) {
		Model output = null;
		System.out.println("Je suis Union  :\n");
		if(DS.getType()==TypeSource.Internal){
			System.out.println("Je suis interne VOICI la requete  : "+query()+"\n");
			//output = ((IntSource) DS).run(query());
			//System.out.println("Le resultat est \n : "+ETL.run(query,ETL.run(query()))+"\n");
		}else{
			if(DS.getType()==TypeSource.External){
				output = DS.run(query(), ((ExtSource)DS).getResource());
				System.out.println("Je suis externe  "+query()+":\n");
			}else{
				if(DS.getType()==TypeSource.Intermediate){
					System.out.println("Je suis intermediaire  "+query()+"\n");
					output = DS.run(query(), ((IntermSource)DS).getDataset());
					
				}
			}
		}
		
		return output;
	}
	@Override
	public String query() {
		String query="";
		query= ETL.Union(this.Resources);
		this.ETLQuery= query; 
		return query;
	}
	
	
}
