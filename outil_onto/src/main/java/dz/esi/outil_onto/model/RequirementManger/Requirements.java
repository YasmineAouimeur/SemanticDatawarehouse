package dz.esi.outil_onto.model.RequirementManger;

import java.util.ArrayList;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.iterator.ExtendedIterator;

import dz.esi.outil_onto.controller.Controller;
import dz.esi.outil_onto.model.OntologyManager.Jena;
import dz.esi.outil_onto.model.ETLMappings.ETLOperations;

public class Requirements {

	

	public String requirementName;
	public ArrayList<ETLOperations> mappings = new ArrayList<ETLOperations>();
	public ArrayList<String> sourcesExt = new ArrayList<String>();
	public ArrayList<String> sourcesInt = new ArrayList<String>();
	public ArrayList<KPI> kpi = new ArrayList<KPI>();
	public ArrayList<String> results = new ArrayList<String>();
	public ArrayList<String> criterias = new ArrayList<String>();
	
	
	public static String hasRes="hasResult";
	public static String hasCrit="hasCriteria";
	public static String sqlQuery="sqlQuery";
	public static String description="description";
	
	//Crit�res 
	public int nbMappings=0;
	public int nbNodes=0;
	public int nbExt=0;
	public int nbInt=0;
	public int nbInserted=0;
	public int nbInferred=0;
	  
	
	public Requirements() {
		// TODO Auto-generated constructor stub
	}
	
	public void setNbExt(){
		for(int i=0; i<this.mappings.size(); i++){
			this.nbExt = this.nbExt + mappings.get(i).sourceExt.size();
		}
	}
	public void setNbInt(){
		for(int i=0; i<this.mappings.size(); i++){
			this.nbInt = this.nbInt + mappings.get(i).sourceInt.size();
		}
	}
	
	public int getNbMappings(){
		return mappings.size();
	}
	public int calculateNbInserted(){
		int nbInserted=0;
		for (int i=0; i < this.mappings.size(); i++){
			nbInserted = nbInserted + mappings.get(i).nbInserted;
		}
		return nbInserted;
	}
	public int calculateNbInferred(){
		int nbInferred=0;
		for (int i=0; i < this.mappings.size(); i++){
			nbInferred = nbInferred + mappings.get(i).nbInferred;
		}
		return nbInferred;
	}
	public int getNbInserted(){
		return this.nbInserted;
	}
	public int getNbInferred(){
		return this.nbInferred;
	}
	public void addNbInserted(int i){
		this.nbInserted = this.nbInserted+i;
	}
	public void addNbInferred(int i){
		this.nbInferred = this.nbInferred+i;
	}
	public int getNbInt(){
		return sourcesInt.size();
	}
	public int getNbExt(){
		return sourcesExt.size();
	}
	public void addNbExt(int i){
		this.nbExt = this.nbExt+i;
	}
	public void addNbInt(int i){
		this.nbInt = this.nbInt+i;
	}
	public void addNbNodes(int i){
		this.nbNodes = this.nbNodes+i;
	}
	public int getNbNodes()
	{
		return this.nbNodes;
	}
	
	public void addSourcesExt(ArrayList<String> List){
		for(int i=0; i<List.size(); i++){
			this.sourcesExt.add(List.get(i));
		}
	}
	public void addSourcesInt(ArrayList<String> List){
		for(int i=0; i<List.size(); i++){
			this.sourcesInt.add(List.get(i));
		}
	}
	public Requirements(String requirementName, ArrayList<String> results, ArrayList<String> criterias) {
		// TODO Auto-generated constructor stub
		this.requirementName=requirementName;
		for(int i = 0; i < results.size(); i++)
        {
    		this.results.add(results.get(i));
        }
		for(int i = 0; i < criterias.size(); i++)
        {
    		this.results.add(criterias.get(i));
        }
	}
	
	
	//retourne la liste des noms des besoins de l'ontologie
	public static ArrayList<String> requiremeentList(OntModel onto)
	{
		ArrayList<String> rep=new ArrayList<String>();
		OntClass req=onto.createClass(Jena.prefix+"Requirement");
		ExtendedIterator< ? extends OntResource> it =req.listInstances();
		while(it.hasNext()) {
			OntResource indiv=it.next();
			rep.add(indiv.getLocalName());
		}
		return rep;
	}
	
	public static ArrayList<String> getRequirement (OntModel onto, String besoin,Property prop) 
	{
		ArrayList<String> result=new ArrayList<String>();
		Resource req = Controller.ontology.getIndividual("http://www.co-ode.org/ontologies/ont.owl#"+besoin);
		
		StmtIterator stmtIterator2 = Controller.ontology.listStatements(req, prop, (RDFNode) null);
    	while (stmtIterator2.hasNext()){
    	    Statement stmt = stmtIterator2.nextStatement();
    	    Resource operation = (Resource) stmt.getObject();
    	    result.add(operation.toString());
    	}
		
        return result;
    }
	
	// retourne les resources internes d'un besoin
	public static ArrayList<String> getRequirementResult (OntModel onto, String besoin) 
	{
		Property prop =onto.getProperty(Jena.prefix+hasRes);
		return getRequirement(onto,besoin,prop);
    }
	//retourne les resources externes d'un besoin
	public static ArrayList<String> getRequirementCriteria (OntModel onto, String besoin) 
	{
		Property prop =onto.getObjectProperty(Jena.prefix+hasCrit);
		return getRequirement(onto,besoin,prop);
    }
	
	// ajoute à l'ontologie un ensemble d'éléments, sous forme de ressources internes d'un besoin
	public static void addRequirementResult(OntModel onto,String besoin, ArrayList<String> content) {
		Property prop =onto.getObjectProperty(Jena.prefix+hasRes);
		OntClass req=onto.getOntClass(Jena.prefix+"Requirement");
		Individual indiv =onto.createIndividual(besoin,req);
		for (String str:content) {
			Resource obj=onto.createClass(str);
			Statement stat=onto.createStatement(indiv,prop,obj);
			onto.add(stat);
		}
	}
	// ajoute à l'ontologie un ensemble d'éléments, sous forme de ressources externes d'un besoin
	public static void addRequirementCriteria(OntModel onto,String besoin, ArrayList<String> content) {
		Property prop =onto.getObjectProperty(Jena.prefix+hasCrit);
		OntClass req=onto.getOntClass(Jena.prefix+"Requirement");
		Individual indiv =onto.createIndividual(besoin,req);
		for (String str:content) {
			OntClass exte=onto.getOntClass(Jena.prefix+"ExternalResource");
			OntClass obj=onto.createClass(str);
			exte.addSubClass(obj);
			Statement stat=onto.createStatement(indiv,prop,obj);
			onto.add(stat);
		}
	}
	
	// retire un ensemble d'éléments d'un besoin
	private static void rmvBesoin(OntModel onto, String besoin, ArrayList<String> content,Property prop) {
		Individual indiv =onto.getIndividual(besoin);
		for (String str:content) {
			Resource obj=onto.createResource(str);
			Statement stat=onto.createStatement(indiv,prop,obj);
			onto.remove(stat);
		}
	}
	public static void rmvBesoinInt(OntModel onto, String besoin, ArrayList<String> content) {
		Property prop =onto.getObjectProperty(Jena.prefix+hasRes);
		rmvBesoin(onto,besoin,content,prop);
	}
	public static void rmvBesoinExt(OntModel onto, String besoin, ArrayList<String> content) {
		Property prop =onto.getObjectProperty(Jena.prefix+hasCrit);
		rmvBesoin(onto,besoin,content,prop);
	}
	//Supprime un besoin de l'ontologie
	public static void rmvBesoin(OntModel onto, String besoin) {
		rmvBesoinInt(onto,besoin,getRequirementResult(onto,besoin));
		rmvBesoinExt(onto,besoin,getRequirementCriteria(onto,besoin));
		Individual indiv =onto.getIndividual(besoin);
		OntClass req=onto.getOntClass(Jena.prefix+"Requirement");
		indiv.removeOntClass(req);
	}
	
	
	
	// Setters
	public void setRequirementName(String name)
	{
		this.requirementName= name;
	}
	public void setResults(ArrayList<String> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		this.results.add(List.get(i));
        }
	}
	public void setCriteria(ArrayList<String> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		this.criterias.add(List.get(i));
        }
	}

}
