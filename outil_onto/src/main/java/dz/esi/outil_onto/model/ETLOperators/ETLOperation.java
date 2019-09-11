package dz.esi.outil_onto.model.ETLOperators;

import java.util.ArrayList;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

import dz.esi.outil_onto.controller.Controller;
import dz.esi.outil_onto.model.DataSources.DataSource;
import dz.esi.outil_onto.model.DataSources.TypeSource;
import dz.esi.outil_onto.model.ETLOperators.ETL;

public class ETLOperation {
	
	String Name; 
	String Resource;
	ArrayList<String> Resources = new ArrayList<String>();
	String Position; 
	
	//Boolean Applied; // This variable contains true if the mappings was saved in onto, and false if it doesn't 
	typeETLOp type; // contains the type of the ETL operator
	TypeSource typeResource; // contains the type of the resource (External / Internal)
	String format;
	String path;
	
	// Specify the dataset going in the ETLOperation and dataset 
	DataSource input;
	Model output;
	
	public String getPath(){
		return this.path;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public DataSource getInput() {
		return input;
	}

	public void setInput(DataSource input) {
		this.input = input;
	}
	
	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Model getOutput() {
		return output;
	}

	public void setOutput(Model output) {
		this.output = output;
	}

	public ETLOperation() {
		// TODO Auto-generated constructor stub
	}
	
	public ETLOperation(String uriName,String Resource, String Position) {
		// TODO Auto-generated constructor stub
		this.Name = uriName;
		this.Resource = Resource;
		this.Position = Position;
	}
	
	public typeETLOp getType() {
		return type;
	}

	public void setType(typeETLOp type) {
		this.type = type;
	}
	
	public TypeSource getTypeResource() {
		return typeResource;
	}

	public void setTypeResource(TypeSource type) {
		this.typeResource = type;
	}



	public void setName(String Name) {
		this.Name = Name;
	}
	
	public void setResource(String Resource) {
		this.Resource = Resource;
	}
	
	public void setPosition(String Position) {
		this.Position = Position;
	}
	
	public String getName() {
		return this.Name;
	}
	public String getPosition() {
		return this.Position;
	}
	public String getResource() {
		return this.Resource;
	}
	public ArrayList<String> getResources(){
		return this.Resources;
	}
	public void setResources(ArrayList<String> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		this.Resources.add(List.get(i));
        }
	}
	public void addResource(String resource) {
    		this.Resources.add(resource);
	}
	//_ Getter  and setters 
	
	
	// Methodes 
	public Model run(){
		return null;
	}


	public Model run(DataSource DS) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
