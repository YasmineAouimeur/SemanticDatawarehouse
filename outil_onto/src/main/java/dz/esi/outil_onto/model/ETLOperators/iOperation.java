package dz.esi.outil_onto.model.ETLOperators;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

import dz.esi.outil_onto.model.DataSources.DataSource;

public interface iOperation {
	public Model run();
	public Model run(DataSource DS);
	public String query();
}
