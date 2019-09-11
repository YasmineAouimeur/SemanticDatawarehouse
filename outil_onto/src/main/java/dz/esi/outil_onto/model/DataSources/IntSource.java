package dz.esi.outil_onto.model.DataSources;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.system.StreamRDFLib;

import dz.esi.outil_onto.controller.Controller;
import dz.esi.outil_onto.model.ETLOperators.ETL;
import dz.esi.outil_onto.model.OntologyManager.openOWL;

public class IntSource extends DataSource {
	OntModel dataset;
	
	
	public IntSource(TypeSource type, OntModel dataset) {
		super(type);
		this.dataset = dataset;
	}


	public Model getDataset() {
		return dataset;
	}


	public void setDataset(OntModel dataset) {
		this.dataset = dataset;
	}

	// Function running a query on the internal ontology (Internal source)
		public Model run(String query){
			String res="\n";
			System.out.println("Je commence l'éxécution de la requete dans la source interne : \n");
			QueryExecution qe = QueryExecutionFactory.create(query, openOWL.OpenConnectOWL());
			Model results = qe.execConstruct();
			//System.out.println("Voila le resultat : \n"+ETL.convertModelToString(results));
			return results;
		}
		
		public Model run(String query, String resource){
			String res="\n";
			String quer = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX wdt: <http://www.wikidata.org/prop/direct/> PREFIX wd: <http://www.wikidata.org/entity/> PREFIX geo:<http://www.w3.org/2003/01/geo/wgs84_pos#> PREFIX gn:<http://www.geonames.org/ontology#> PREFIX foaf:<http://xmlns.com/foaf/0.1/> PREFIX xsd:<http://www.w3.org/2001/XMLSchema#> PREFIX loticoowl:<http://www.lotico.com/ontology/>construct where {?object rdf:type loticoowl:Member.}"; 
			System.out.println("Coucou je suis externe : ");
			//Model mod = RDFDataMgr.loadModel("http://www.lotico.com/ontology/",Lang.RDFXML);
			//StreamRDFLib.graph(mod.getGraph());
			//QueryExecution qe = QueryExecutionFactory.create(quer, mod);
			QueryExecution qe = QueryExecutionFactory.sparqlService("http://www.lotico.com/ontology/",quer);
			Model results = qe.execConstruct();
			System.out.println("Voia le result : ");
			return results;
			
		}
	
	
}
