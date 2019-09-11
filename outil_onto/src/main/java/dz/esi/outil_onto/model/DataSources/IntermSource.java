package dz.esi.outil_onto.model.DataSources;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;

import dz.esi.outil_onto.model.OntologyManager.openOWL;

public class IntermSource extends DataSource {
	
	public IntermSource(TypeSource type) {
		super(type);
	}

	public IntermSource(TypeSource type, Model dataset) {
		super(type);
	}

	
	
	// Function Runing a query on an RDF model, this model is the result of the execution of the precedent ETL operation (Intermediate source)
	public Model run(String query, Model m){
		String res="\n";
		QueryExecution qe = QueryExecutionFactory.create(query, openOWL.OpenConnectOWL());
		Model results = qe.execConstruct();
		return results;
	}
	
}
