package dz.esi.outil_onto.model.ETLMappings;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdf.model.impl.StmtIteratorImpl;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.iterator.ExtendedIterator;

import dz.esi.outil_onto.controller.Controller;
import dz.esi.outil_onto.model.DataSources.TypeSource;
import dz.esi.outil_onto.model.ETLMetadata.DSA;
import dz.esi.outil_onto.model.ETLMetadata.Data;
import dz.esi.outil_onto.model.ETLMetadata.Instance;
import dz.esi.outil_onto.model.ETLMetadata.Metadata;
import dz.esi.outil_onto.model.ETLOperators.ETL;
import dz.esi.outil_onto.model.ETLOperators.ETLOperation;
import dz.esi.outil_onto.model.ETLOperators.typeETLOp;
import dz.esi.outil_onto.model.OntologyManager.Jena;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ETLOperations {
	
	public String ETLName;
	public ArrayList<ETLOperation> Operations = new ArrayList<ETLOperation>();
	public String Target;
	
	// map details
	public ArrayList<String> internalClasses = new ArrayList<String>();
	public ArrayList<String> externalClasses = new ArrayList<String>();
	public ArrayList<String> formats = new ArrayList<String>();
	
	// Internal and external properties 
	public ArrayList<String> sourceExt = new ArrayList<String>();
	public ArrayList<String> sourceInt = new ArrayList<String>();
	
	// si le mapping contient une fonction load, on ne peut plus lui ajouter de fonction
	public boolean closed =false;
	public boolean inte=true;
	
	//ensemble des indicateurs qui seront ajoutÃ©s au besoin correspondant
		// nombre d'expression ETL dans le mapping
		public int nbPro=0;
		public int nbInt=0;
		public int nbExt=0;
		public long ETtime=0;
		public long Ltime=0;
		public int nbInserted=0;
		public int nbInferred=0;
		public int nbNodes=0;
	
		
		// Properties of external classes 
		Property hasFormat = Controller.ontology.getProperty("http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/hasFormat");
		Property externalClassOf = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#externalClassOf");
		Property Path = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#Path");
		
		
	public ETLOperations(){}
		
	public void setSourcesDetails(){
		this.externalClasses.clear();
		this.internalClasses.clear();
		this.formats.clear();
		
		for (ETLOperation ETLOp:this.Operations) {
			if(ETLOp.getTypeResource()==TypeSource.External){
				this.externalClasses.add(ETLOp.getResource());
				
				Resource externalClass = Controller.ontology.getIndividual(ETLOp.getResource());
				
				// Extraire le format de format de métadonnées de la classe externe
				StmtIterator stmtIterator1 = Controller.ontology.listStatements(externalClass, hasFormat, (RDFNode) null);
				while (stmtIterator1.hasNext()){
	        	    Statement s = stmtIterator1.nextStatement();
	        	    Resource formatR = (Resource) s.getObject();
	        	    ETLOp.setFormat(formatR.toString());
	        	    this.formats.add(formatR.getLocalName());
	        	}
				
				// Extraire le format le chemain vers la classe externe
				StmtIterator stmtIterator = Controller.ontology.listStatements(externalClass, Path, (RDFNode) null);
				while (stmtIterator.hasNext()){
	        	    Statement ss = stmtIterator.nextStatement();
					if (ss.getObject().isLiteral() ){
	                    ETLOp.setPath(ss.getLiteral().getLexicalForm().toString());
	                    System.out.println("le chemain "+ETLOp.getPath()+"\n");
					}
	        	}
			}
			else{
				if(ETLOp.getTypeResource()==TypeSource.Internal){
					this.internalClasses.add(ETLOp.getResource());
				}
			}
		}
	}
	
	
	
	
	
	public void setNbNodes(int i){
		this.nbNodes=i;
	}
	public void setNbExt (int i){
		this.nbExt=i;
	}
	public void setNbInt(int i){
		this.nbInt=i;
	}
	
	public ETLOperations(ArrayList<ETLOperation> ETLOperations, String target, String Name, int nbNodes) {
		// TODO Auto-generated constructor stub
		//this.ETLOperations = new ArrayList<ETLOperation>(ETLOperations);
		for(int i = 0; i < ETLOperations.size(); i++)
        {
    		Operations.add(ETLOperations.get(i));
        }
		Target = target;
		ETLName = Name;
		this.nbNodes= nbNodes;
	}

	
	public String getTarget(){
		return this.Target;
	}
	public String getETLName(){
		return this.ETLName;
	}
	

	public ArrayList<ETLOperation> getETLOperations(){
		return Operations;
	}
	
	public void setTarget( String target){
		this.Target = target;
	}
	public void setETLName( String name){
		this.ETLName = name;
	}
	
	public void setETLOperations(ArrayList<ETLOperation> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		this.Operations.add(List.get(i));
        }
	}
	
	public void setSourceExt(ArrayList<String> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		this.sourceExt.add(List.get(i));
        }
	}
	public void setSourceInt(ArrayList<String> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		this.sourceInt.add(List.get(i));
        }
	}
	
	
	public void setExternalClasses(ArrayList<String> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		this.externalClasses.add(List.get(i));
        }
	}
	public void setInternalClasses(ArrayList<String> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		this.internalClasses.add(List.get(i));
        }
	}
	
	public void setFormats(ArrayList<String> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		this.formats.add(List.get(i));
        }
	}
	
	public ArrayList<String> getExternalClasses() {
		return this.externalClasses;
	}
	
	public ArrayList<String> getInternalClasses() {
		return this.internalClasses;
	}
	
	public ArrayList<String> getFormats() {
		return this.formats;
	}
	
	
	public void runMapping(){
		
	} 
	
	public void dispETLOperations(){
		System.out.println("L'ETL choisit est :\n"+ this.ETLName + "\n");
		System.out.println("*********************************************************");
		System.out.println("La classe cible est :\n"+ this.Target + "\n");
		System.out.println("*********************************************************");
    	for(int i = 0; i < Operations.size(); i++)
        {
    		System.out.println("Nom : "  + this.Operations.get(i).getName());
    		System.out.println("Resource : " + this.Operations.get(i).getResource());
    		System.out.println("Position : " + this.Operations.get(i).getPosition());
    		System.out.println("*********************************************************");
        }
    	
    	
	}
	
	public String displayETLOperations(){
		String result="";
		result = result +"============================================================\n";
		//result = result +"Selected mapping : "+ this.ETLName + "\n";
		result = result +"- Target class : "+ this.Target + "\n";
		//result = result +"- The number of nodes in the selected mapping : "+ this.nbNodes + "\n";
		result = result +"============================================================\n";
		result = result +"Operations of the mapping '"+this.ETLName+ "' :\n\n";
    	for(int i = 0; i < this.Operations.size(); i++)
        {
    		result = result +"- ETL operation name : "  + this.Operations.get(i).getName() +"\n";
    		result = result +"- Resource class : " + this.Operations.get(i).getResource()+"\n";
    		result = result +"- Position of the ETL operation in the workflow : " + this.Operations.get(i).getPosition()+"\n\n";
        }
		return result;
	}
	
	public static ObservableList<String> initializeMappings(){
		
		// Initialiser les combobox avec les noms des mappings
       	String strg="ETL";
       	ObservableList<String> mappingList = FXCollections.observableArrayList();
    	OntClass mapping = Controller.ontology.getOntClass("http://www.co-ode.org/ontologies/ont.owl#ETL"); 
    	
    	for ( final ExtendedIterator<? extends OntResource> merlots = mapping.listInstances(); merlots.hasNext(); ) {
            String instance = merlots.next().getLocalName();
            if (instance.toLowerCase().contains(strg.toLowerCase())){
	            mappingList.add(instance);
            }
    	}
    	return mappingList;
	}

	
}
