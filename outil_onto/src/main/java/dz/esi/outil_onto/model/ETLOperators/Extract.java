package dz.esi.outil_onto.model.ETLOperators;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

import dz.esi.outil_onto.model.DataSources.DataSource;
import dz.esi.outil_onto.model.DataSources.ExtSource;
import dz.esi.outil_onto.model.DataSources.IntermSource;
import dz.esi.outil_onto.model.DataSources.TypeSource;
import dz.esi.outil_onto.model.ETLOperators.typeETLOp;

public class Extract extends ETLOperation implements iOperation{
	
	String ETLQuery="";
	
	public Extract() {
		super();
	}
	
	public Extract(String uriName, String Resource, String Position) {
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

	

	// Methodes
		@Override
		public Model run(DataSource DS) {
			Model output = null;
			System.out.println("Je suis Extract  :\n");
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
		query= ETL.Extract(this.Resource, "http://purl.org/dc/elements/1.1/publisher");
		this.ETLQuery= query; 
		return query;
	}

}