package dz.esi.outil_onto.model.DataSources;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.*;

import dz.esi.outil_onto.controller.Controller;
import dz.esi.outil_onto.model.OntologyManager.openOWL;

public class ExtSource extends DataSource {

	String Resource="";
	String Format="";
	String ResourcePath="";
	
	Property hasFormat=Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#asFormat");
	Property ExternalClassOf=Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#ExternalClassOf");
	Property Path=Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#Path");
	
	public ExtSource(TypeSource type, String resource) {
		super(type);
		this.Resource = resource;
		Resource  res = Controller.ontology.getResource(resource);
		
		//Extraire le format de la ressource externe
		StmtIterator it = Controller.ontology.listStatements(res, hasFormat, (RDFNode) null);
        while ( it.hasNext()) {
        Statement st = (Statement) it.next(); 
    	    if (st.getObject().isLiteral()== false) {
    	    	//System.out.println("La ressource :"+st.getObject().toString()+" type = "+st.getPredicate().getLocalName());
            	 this.Format=st.getObject().toString();
    	    }
        }
        
      //Extraire le chemin vers la ressource externe
      		StmtIterator it1 = Controller.ontology.listStatements(res, Path, (RDFNode) null);
              while ( it1.hasNext()) {
              Statement st = (Statement) it1.next(); 
          	    if (st.getObject().isLiteral()) {
          	    	//System.out.println("La ressource :"+st.getObject().toString()+" type = "+st.getPredicate().getLocalName());
                  	 this.ResourcePath=st.getLiteral().getLexicalForm().toString();
          	    }
              }
	}

	
	public String getResource() {
		return Resource;
	}

	public void setResource(String resource) {
		Resource = resource;
	}

	public ExtSource(TypeSource type) {
		super(type);
	}
	
	// Function running a query on the internal ontology (Internal source)
		public Model run(String query, String resource){
			String res="\n";
			System.out.println("Coucou je suis externe : ");
			QueryExecution qe = QueryExecutionFactory.sparqlService("http://www.lotico.com/resource/","PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX wdt: <http://www.wikidata.org/prop/direct/> PREFIX wd: <http://www.wikidata.org/entity/> PREFIX geo:<http://www.w3.org/2003/01/geo/wgs84_pos#> PREFIX gn:<http://www.geonames.org/ontology#> PREFIX foaf:<http://xmlns.com/foaf/0.1/> PREFIX xsd:<http://www.w3.org/2001/XMLSchema#> PREFIX loticoowl:<http://www.lotico.com/ontology/>Select ?s where {?s rdf:type loticoowl:Member.}");
			Model results = qe.execConstruct();
			return results;
		}

}
