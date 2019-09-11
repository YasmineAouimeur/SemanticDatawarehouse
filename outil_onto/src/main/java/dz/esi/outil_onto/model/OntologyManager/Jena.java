package dz.esi.outil_onto.model.OntologyManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDF;

import dz.esi.outil_onto.application.Main;
import javafx.scene.control.TextArea;

public class Jena {

	public Jena() {
		
	}
	// préfixe de l'ontologie, choisi par l'utilisateur au lancement du programme
	public static String prefix;
	
	// enregistrement de l'ontologie dans un fichier
	public static void save(OntModel onto, String path)
	{
		FileOutputStream fichierSortie = null; 
		try { fichierSortie = new FileOutputStream (new File (path)); }
		catch (FileNotFoundException ex){ Logger.getLogger(Main.class.getName()).log(Level.ALL, null, ex); } 
		onto.write(fichierSortie);
	}
	// pour la classe ETLProcess : ajout des caractéristiques des fonctions
	public static void addDataStatement (OntModel onto, Individual inst, String property, Object object)
	{
		DatatypeProperty prop =onto.createDatatypeProperty(prefix+property);
		Statement stat = onto.createLiteralStatement(inst,prop,object);
		onto.add(stat);
	}
	public static void addDataStatement (OntModel onto, String ind, String property, Object object)
	{
		Individual inst=onto.getIndividual(prefix+ind);
		addDataStatement(onto, inst, property, object);
	}
	
	// pour les fonctions de stockage de ETL (propriétés ou classe)
	public static boolean addProperty(OntModel onto, String instance,String prop, String instance2, String cla, String cla2)
	{
		boolean add=false;
		OntClass classe = onto.createClass(prefix+cla);
		Individual ind=onto.createIndividual(prefix+instance,classe);
		Property property = onto.createObjectProperty(prefix+prop);
		OntClass classe2 = onto.createClass(prefix+cla2);
		Individual indi=onto.createIndividual(prefix+instance2,classe2);
		Statement stat=onto.createStatement(ind, property, indi);
		// vérification que l'ontologie ne contient pas déjà l'élément
		if (!onto.contains(stat)) {
			onto.add(stat);
			add=true;
		}
		return add;
	}
	public static boolean addDataProperty(OntModel onto, String instance, String prop, String res, String cla)
	{
		boolean add=false;
		OntClass classe= onto.createClass(prefix+cla);
		Individual ind=onto.createIndividual(prefix+instance,classe);
		Property property = onto.createDatatypeProperty(prefix+prop);
		Statement stat =onto.createStatement(ind, property, res);
		// vérification que l'ontologie ne contient pas déjà l'élément
		if (!onto.contains(stat)) {
			onto.add(stat);
			add=true;
		}
		return add;
	}
	public static boolean addInstance(OntModel onto, String instance,String cla)
	{
		boolean add=false;
		// vérification que l'ontologie ne contient pas déjà l'élément
		Resource res=onto.createResource(prefix+instance);
		OntClass classe=onto.createClass(prefix+cla);
		if (!res.hasProperty(RDF.type, classe))
			{onto.createIndividual(prefix+instance,classe);add=true;}
		return add;
	}
	
	public void listClasses(OntModel ontology,TextArea afficher) {
		String text="";
		ExtendedIterator classes = ontology.listClasses();
        while (classes.hasNext())
        {
          OntClass thisClass = (OntClass) classes.next();
          text = text + "Found class: " + thisClass.toString() + "\n";
        }
        afficher.setText(text);
        System.out.println(text);
        
	}
	
	public void listInstances(OntClass className,TextArea afficher) {
		String text="";
		ExtendedIterator instances = className.listInstances();
        while (instances.hasNext())
        {
          Individual thisInstance = (Individual) instances.next();
          text= text+ "  Found instance: " + thisInstance.toString()+ "\n";
        }
        afficher.setText(text);
        System.out.println(text);
        
	}
	
	public static String[] statementSplit(String Statement){
		String[] splits1 = null;
		String[] splits2 = null;
		String[] result = null;
		String part1 ="";
		String part2 ="";
		//System.out.println(Statement);
		
		splits1 = Statement.split("\\[");
		part1 = splits1[1];
		
		splits2 = part1.split("\\]");
		part2 = splits2[0];
		
		result = part2.split(",");
		
		return result;
	}

	public static String getLocalName(String uri){
		String result = "";
		String[] splits = null;
		
		if(uri.contains("#")){
			splits = uri.split("#");
			result = splits[splits.length-1];
		}else{
			splits = uri.split("/");
			result = splits[splits.length-1];
		}
		return result;
	}
	
	public static String getValue(String uri){
		String value ="";
		String[] splits = null;
		
		if(uri.contains("http")){
			splits = uri.split("http");
			value = splits[0];
		}
		
		return value;
	}
	
	public static boolean checkValue(String uri){
		boolean value = false;
		
		if(uri.contains("^^")){
			value = true;
		}
		
		return value;
	}
	

	 public static boolean checkMetadata(String uri){
		 boolean metadata = false;
		 
		 if((uri.contains("#")==true)&&(uri.contains("type"))==false){
			 metadata = true;
		 }else{
			 if (uri.contains("isVersion")||
				uri.contains("modified")||
				uri.contains("foundingYear")||
				uri.contains("description")||
				uri.contains("comment")||
				uri.contains("title")||
				uri.contains("subject")||
				uri.contains("publisher")||
				uri.contains("created")||
				uri.contains("owners")||
				uri.contains("owner")||
				uri.contains("priorVersion")||
				uri.contains("Date")||
				uri.contains("date")||
				uri.contains("versionInfo")||
				uri.contains("title")||
				uri.contains("reviewer")||
				uri.contains("reviewDate")||
				uri.contains("reviewFor")||
				uri.contains("wasDerivedFrom")||
				uri.contains("created")||
				uri.contains("builder")||
				uri.contains("developer")||
				uri.contains("publisher")||
				uri.contains("wasDerivedFrom")||
				uri.contains("foudry")||
				uri.contains("manufacturer")||
				uri.contains("owners")||
				uri.contains("hasSource")||
				uri.contains("reviewer")||
				uri.contains("isDefinedBy")||
				uri.contains("owner")||
				uri.contains("modified")||
				uri.contains("date")||
				uri.contains("reviewDate")||
				uri.contains("foundingYear")||
				uri.contains("validFrom")||
				uri.contains("validTo")||
				uri.contains("label")||
				uri.contains("seeAlso")||
				uri.contains("title")||
				uri.contains("description")||
				uri.contains("subject")||
				uri.contains("comment")||
				uri.contains("isDefinedBy")||
				uri.contains("incompatibleWith")||
				uri.contains("backwardCompatibleWith")||
				uri.contains("distributor")||
				uri.contains("recordLabel")||
				uri.contains("versionInfo")||
				uri.contains("priorVersion")||
				uri.contains("isVersion")||
				uri.contains("hasMainRevision")||
				uri.contains("hasOldRevision")||
				uri.contains("uniqueContributorNb")||
				uri.contains("revPerYear2016")||
				uri.contains("revPerYear2015")||
				uri.contains("revPerYear2014")||
				uri.contains("revPerYear2013")||
				uri.contains("revPerYear2012")||
				uri.contains("revPerYear2010")||
				uri.contains("revPerYear2007")||
				uri.contains("revPerYear2006")||
				uri.contains("revPerYear2005")||
				uri.contains("revPerLastMonth1")||
				uri.contains("revPerLastMonth2")){
				 metadata =true;
			 }
		 }
			
		 return metadata;
	 } 
}
