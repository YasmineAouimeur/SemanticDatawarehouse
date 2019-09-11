package dz.esi.outil_onto.controller;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.apache.jena.ontology.AnnotationProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import com.sun.glass.ui.View;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import dz.esi.outil_onto.model.*;
import dz.esi.outil_onto.model.ETLOperators.ETLOperation;
import dz.esi.outil_onto.model.OntologyManager.openOWL;
import dz.esi.outil_onto.model.RequirementManger.KPI;
import dz.esi.outil_onto.model.RequirementManger.Requirements;
import dz.esi.outil_onto.model.ETLMappings.ETLOperations;
import dz.esi.outil_onto.controller.Controller;

public class RequirementCont implements Initializable {

	public RequirementCont() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*System.out.println(Controller.ontology.toString());
		if (openOWL.path != "" ){
			//System.out.println("me voila"+Controller.ontology.toString());
			initializeRequirements();
			initializeKPI();
		}
		//initializeKPI();*/
		
	}
	
    
    // mapping properties
    Property hasinputSet = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#hasinputSet");
	Property hasoutputSet = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#hasoutputSet");
	Property Resource = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#Resource");
    Property position = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#position");
    
    //requirement properties
    Property hasCriteria = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#hasCriteria");
    Property hasResult = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#hasResult");
    
 // Requirement list
    public static ArrayList<Requirements> requirements =new ArrayList<Requirements>();
    
    
    /********************************************* Internal and external sources*************************************************/
    
    @FXML 
    private TextArea intDesc;
    @FXML 
    private TextArea extDesc;
    
    @FXML
	   public void initializeSources(){
    	// resultInt and resultExt are Strings used to view the list of found resources 
    	String resultInt ="";
    	String resultExt ="";
    	// Contains the number of extrenal and internal resources
    	int nbInt=0;
    	int nbExt=0;
    	// Contains the list of internal and external resources
    	ArrayList<String> resourceExt = new ArrayList<String>();
		ArrayList<String> resourceInt = new ArrayList<String>();
		// RequirementName contains the selected requirement name from the combobox
		String RequirementName = comboIntExt.getValue().toString();
		intDesc.clear();
		extDesc.clear();
		// Browse the list of requirements
    	for (int i=0; i < requirements.size();i++){
				String ResourceName="";
				String opName="";
				// Clear the internal and external lists 
				resourceExt.clear();
	    		resourceInt.clear();
	    		requirements.get(i).sourcesExt.clear();
	    		requirements.get(i).sourcesInt.clear();
	    		resultInt = resultInt +"=====================================================\n"+ "Requirement : " + requirements.get(i).requirementName + "\n";
				resultExt = resultExt +"=====================================================\n"+ "Requirement : " + requirements.get(i).requirementName + "\n";
				// for each requirement browse its mapping list
				for (int j=0; j < requirements.get(i).mappings.size();j++){
					resourceExt.clear();
		    		resourceInt.clear();
					String[] separate = requirements.get(i).mappings.get(j).getETLName().split("\\#");
					resultInt = resultInt +"\n"+ "- Internal resources of mapping : " + separate[1] + "\n";
					resultExt = resultExt +"\n"+ "- External resources of mapping : " + separate[1] + "\n";
				
				for (int k=0; k < requirements.get(i).mappings.get(j).Operations.size();k++){
					
					opName = requirements.get(i).mappings.get(j).Operations.get(k).getName();
					// L'opération "Load" contient toujours la classe d'entrepôt de données, nous l'omettons car nous ne la considérons pas comme une ressource
					if (opName.toLowerCase().contains("load") ==false){
						ResourceName = requirements.get(i).mappings.get(j).Operations.get(k).getResource();
						// check the internal prefixes 
						if ((ResourceName.toLowerCase().contains("http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/"))||(ResourceName.toLowerCase().contains("http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/"))){
							nbInt++;
							resourceInt.add(ResourceName);
							resultInt = resultInt + ResourceName +"\n";
       					 	// For each internal resource, check its annotation properties if they are internal or external
		                		OntClass resourceCl = Controller.ontology.getOntClass(ResourceName);
		                		// Pick one instance to check the annotation properties
		                		final ExtendedIterator<? extends OntResource> merlots = resourceCl.listInstances();
			                	OntResource in = merlots.next();
			                	// List statements
		                			 for ( final ExtendedIterator<Statement> merlot = in.listProperties(); merlot.hasNext(); ) {
		                				 String instance = merlot.next().toString();
		                				 // split the statement into three parts (Subject , Property, object) we need the property 
		                				 String[] separated = instance.split("\\,");
		                				 
		                					 if((instance.contains("country"))||(instance.contains("date"))||(instance.contains("publisher"))||(instance.contains("homepage")))
			                				 {
			                					 nbExt++;
					                			 resourceExt.add(separated[1]);
					                			 resultExt = resultExt + separated[1]+"\n";
			                				 }else {
			                					 boolean exist= false;
			                					 int p = 0;
			                					 while((exist == false)&&(p<resourceInt.size())){
			                						 //System.out.println("Contenue dans a liste : "+ resourceInt.get(p) +"  trouvé : "+separated[1]);
			                						 if((resourceInt.get(p).equals(separated[1])) == true){
			                							 exist=true;
			                						 }
			                						 p++;
			                					 }
			                					 //System.out.println(" C'est que je décide selon exist : "+exist + " la taille de la list :"+ p);
			                					 if (exist==false){
			                						 nbInt++;
				       		                		 resourceInt.add(separated[1]);
				       		                		//System.out.println("Je suis interne : "+ instance);
				       		                		resultInt = resultInt + separated[1]+ "\n";
			                					 }
			                				 }
		                			 }
		                		 
	                	 }
	                	 else {
	                		 if (ResourceName.toLowerCase().contains("http://www.co-ode.org/ontologies/ont.owl#")){
	                			 nbInt++;
		                		 resourceInt.add(ResourceName);
		                		 resultInt = resultInt + ResourceName+"\n";
	                		 }
	                		 else {
	                			 nbExt++;
		                		 resourceExt.add(ResourceName);
		                		 resultExt = resultExt + ResourceName+"\n";
	                		 }
	                	 }
					}
				}
				requirements.get(i).mappings.get(j).setNbExt(nbExt);
				requirements.get(i).mappings.get(j).setNbInt(nbInt);
				requirements.get(i).mappings.get(j).setSourceExt(resourceExt);
				requirements.get(i).mappings.get(j).setSourceInt(resourceInt);
				requirements.get(i).setNbExt();
				requirements.get(i).setNbInt();
    			requirements.get(i).addSourcesExt(resourceExt);
    			requirements.get(i).addSourcesInt(resourceInt);
    			//resultInt = resultInt +"\n - The number of inetrnal resources :"+ requirements.get(i).getNbInt() + "\n";
				//resultExt = resultExt +"\n - The number of external resources :"+ requirements.get(i).getNbExt()+ "\n";
    			resourceExt.clear();
    			resourceInt.clear();
			}
				// Display results in the textAreas 
					intDesc.setText(resultInt);
					extDesc.setText(resultExt);
		}
    }
		   
    
	
	/********************************************* Performance value*************************************************/
	//KPI properties
    Property levelPerformance = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#levelPerformance");
    Property conflict = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#conflict");
    
    // Liste des KPI 
    public static ArrayList<KPI> KPIs =new ArrayList<KPI>();
    
    @FXML
    private TextArea KPIdesc;
    
	@FXML
	   public void initializeKPI(){
		   
		   // Declarations
		   
		   int j=0;
		   
		   
		   // Liste contient les noms des instances KPI
		    ObservableList<String> KPIList = FXCollections.observableArrayList();
		   
		 
	   		
		 //Remplir une liste contenant les KPI 
	   	OntClass KPI = Controller.ontology.getOntClass("http://www.co-ode.org/ontologies/ont.owl#KPI"); 
	      	for ( final ExtendedIterator<? extends OntResource> merlots = KPI.listInstances(); merlots.hasNext(); ) {
	              String instance = merlots.next().getLocalName();
	              KPIList.add(instance);
	              System.out.println("The KPI Name"+instance);
	      	}
	      	
	      	for(int i = 0; i < KPIList.size(); i++)
	        {
	      		KPI kpi= new KPI();
	      //Extraire les individues
	   		Resource indiv = Controller.ontology.getIndividual("http://www.co-ode.org/ontologies/ont.owl#"+KPIList.get(i));
            kpi.Name = indiv.getLocalName();
      	    System.out.println("The indiv "+kpi.Name);
      	   
	        	
	        	// Extraire  la propriété "Conflict"
	        	  StmtIterator it = Controller.ontology.listStatements(indiv, conflict, (RDFNode) null);
	        	  
	                while (it.hasNext()){
	                	Statement stt = (Statement) it.next();
	                		if (stt.getObject().isLiteral()) {
	                			kpi.conflict = Boolean.parseBoolean(stt.getLiteral().getLexicalForm().toString());
	                			System.out.println("Conflict :"+stt.getLiteral().getLexicalForm().toString());
	     	                }
	     	                else { 
	     	                	System.out.println("Error");
	     	                }
	                }
	        	// Extraire  la propriété "Level Performance"
	       	    it = Controller.ontology.listStatements(indiv, levelPerformance, (RDFNode) null);
	             
	            	while(it.hasNext()){
	            		Statement st = (Statement) it.next();
	            		if (st.getObject().isLiteral()) {
	            			kpi.levelPerformance.add(Integer.parseInt(st.getLiteral().getLexicalForm().toString()));
	            			System.out.println("level Perform"+Integer.parseInt(st.getLiteral().getLexicalForm().toString()));
	 	                }
	 	                else { 
	 	                	System.out.println("Error");
	 	                }
	            	}
	            	//MappingCont.requirements.get(j).kpi.add(kpi);
	            	//j++;
	            	KPIs.add(kpi);
	        }
	      	System.out.println(displayKPI(KPIs));
	      	KPIdesc.setText(displayKPI(KPIs));
	   }
	
	// Renvoi une chaine de caractère qui contient tout les details d'un besoin
		public String displayKPI(ArrayList<KPI> KPIs){
			String result="";
			for(int i = 0; i < KPIs.size(); i++)
	        {
			result = result +"\n==============================================\n";
			result = result +"- KPI name : "+ KPIs.get(i).Name + "\n";
			result = result +"- Conflict : "+ KPIs.get(i).conflict + "\n";
			result = result +"- Performance value : ";
	    	for(int j = 0; j < KPIs.get(i).levelPerformance.size(); j++)
	        {
	    		result = result + KPIs.get(i).levelPerformance.get(j) +" , ";
	        }
	        }
	    	result = result +"\n==============================================\n";
			return result;
		}
	
		
/*********************************************Mapping/ETL application*************************************************/
		@FXML
	    public ComboBox comboMap1;
		@FXML
	    public ComboBox comboIntExt;
	    @FXML
	    public ComboBox comboRequirement;
	    @FXML
	    public ComboBox choixBesoin;
	    @FXML
	    private TextArea affichBesoin;
		
		private ETLOperations mapping;
		
		//Elements qui contienderont le contenue de chaque opertion EX: les propriétés de "Retrieve"
		String NameOp="";
		String ResourceOp="";
		String PositionOp="";
		

		// Contient la liste des mappings et requirements a mettre dans le combobox 
	    public ObservableList<String> mappingList = FXCollections.observableArrayList();
	    public ObservableList<String> requirementList = FXCollections.observableArrayList();
		
		
		// recuperation de la liste des besoins
	    @FXML
	    public void initializeRequirements(){
	    	Requirements requir = new Requirements();
	    	
	    	//Initialiser le combobox avec les noms des besoins
	    	OntClass requirement = Controller.ontology.getOntClass("http://www.co-ode.org/ontologies/ont.owl#Requirement"); 
	       	for ( final ExtendedIterator<? extends OntResource> merlots = requirement.listInstances(); merlots.hasNext(); ) {
	               String instance = merlots.next().getLocalName();
	               requirementList.add(instance);
	       	}
	       	comboIntExt.setValue("Choose a requirement");
	       	comboIntExt.setItems(requirementList);
	       	
	       	choixBesoin.setValue("Choose a requirement");
	       	choixBesoin.setItems(requirementList);
	       	
	       	comboRequirement.setValue("Requirement List");
	       	comboRequirement.setItems(requirementList);
	       	
	       	
	     // Initialiser les combobox avec les noms des mappings
	       	String strg="ETL";
	    	OntClass mapping = Controller.ontology.getOntClass("http://www.co-ode.org/ontologies/ont.owl#ETL"); 
	    	
	    	for ( final ExtendedIterator<? extends OntResource> merlots = mapping.listInstances(); merlots.hasNext(); ) {
	            String instance = merlots.next().getLocalName();
	            if (instance.toLowerCase().contains(strg.toLowerCase())){
		            mappingList.add(instance);
	            }
	    	}
	    	comboMap1.setValue("Mapping List");
	    	comboMap1.setItems(mappingList);
	    	
	       	for(int i = 0; i < requirementList.size(); i++)
	        {
	        	requirements.add(new Requirements(requirementList.get(i),Requirements.getRequirement(Controller.ontology,requirementList.get(i),hasResult),Requirements.getRequirement(Controller.ontology,requirementList.get(i),hasCriteria)));
	        	
	        }
	       	//for(int i = 0; i < requirements.size(); i++)
	        //{
	       		//System.out.print(displayRequirement(requirements.get(i)));
	        //}
	    }
	   
	   // appliquer un mapping sur un besoin et capturer les indicateurs 
	   public void execMappingInRequirement(){
		   
		   ETLOperations map= new ETLOperations();
		   ArrayList<String> sourceExt = new ArrayList<String>();
		   ArrayList<String> sourceInt = new ArrayList<String>();
		   String RequirementName = comboRequirement.getValue().toString();
		   String MappingName= comboMap1.getValue().toString();
		   String textContainer="";
		   int nbNodes=0;
		   int nbExt=0;
	   		int nbInt=0;
	   		String ResourceName="";
		   //Apply ETL mappings
		   
		   //Get the seected mapping
	   		Resource ETL = Controller.ontology.getIndividual("http://www.co-ode.org/ontologies/ont.owl#"+ MappingName);
	   		map.ETLName = ETL.toString();
	   		nbNodes++;
	   		//Extraire la classe cible (hasoutputSet)
	   		StmtIterator stmtIterator1 = Controller.ontology.listStatements(ETL, hasoutputSet, (RDFNode) null);
	   		while (stmtIterator1.hasNext()){
		    Statement s = stmtIterator1.nextStatement();
		    Resource targetClass = (Resource) s.getObject();
		    map.Target= targetClass.toString();
		    nbNodes++;
	   		}
		    // Extraire la liste des operations ETL du mapping séléctionné (hasinputSet)
		    StmtIterator stmtIterator2 = Controller.ontology.listStatements(ETL, hasinputSet, (RDFNode) null);
	    	while (stmtIterator2.hasNext()){
	    	    Statement stmt = stmtIterator2.nextStatement();
	    	    Resource operation = (Resource) stmt.getObject();
	    	    // Extraire le nom de l'operation
	    	    NameOp=operation.toString();
	    	    nbNodes++;
	    	    // Afficher la classe resource de l'operation 
	     	    StmtIterator it = Controller.ontology.listStatements(operation, Resource, (RDFNode) null);
	            
	            while (it.hasNext()){
	            	Statement st = (Statement) it.next(); 
		        	    if (st.getObject().isLiteral()== false) {
		                	 ResourceOp=st.getObject().toString();
		                	 ResourceName = ((org.apache.jena.rdf.model.Resource) st.getObject()).getLocalName();
		                	 //System.out.println("Je verifie: "+ ResourceOp);
		                	 
		                	 if ((ResourceOp.toLowerCase().contains("http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/"))||(ResourceOp.toLowerCase().contains("http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/"))){
		                		 
		                		 OntClass mapping = Controller.ontology.getOntClass(ResourceOp);
		                		 boolean ext = false;
		                		 
		                			 for ( final ExtendedIterator<? extends OntResource> merlots = mapping.listInstances(); merlots.hasNext(); ) {
			                			 OntResource in = merlots.next();
			                			 for ( final ExtendedIterator<Statement> merlot = in.listProperties(); merlot.hasNext(); ) {
			                				 String instance = merlot.next().toString();
			                				 //System.out.println("Voila annotation :"+instance+"\n");
			                				 
			                				 if((instance.contains("country"))||(instance.contains("date"))||(instance.contains("publisher")))
			                				 {
			                					 ext=true;
			                				 }
			                			 }
			                		 }
		                			 if (ext== false)
			                		 {
			                			 nbInt++;
	    		                		 sourceInt.add(ResourceOp);
	    		                		 //System.out.println("Je suis interne : "+ ResourceOp);
			                		 }
			                		 else {
			                			 nbExt++;
	    		                		 sourceExt.add(ResourceOp);
			                		 }
		                	 }
		                	 else {
		                		 if (ResourceOp.toLowerCase().contains("http://www.co-ode.org/ontologies/ont.owl#")){
		                			 nbInt++;
    		                		 sourceInt.add(ResourceOp);
		                		 }
		                		 else {
		                			 nbExt++;
			                		 sourceExt.add(ResourceOp);
			                		 //System.out.println("Je suis externe"+ ResourceOp);
		                		 }
		                	 }
		                	 nbNodes++;
		                }
		                else { 
		                	System.out.println("Error");
		                }}
		        //Afficher la position de l'operation dans le mapping
		        it = Controller.ontology.listStatements(operation, position, (RDFNode) null);
		        while (it.hasNext()){
		        Statement stm = (Statement) it.next(); 
		        	    if (stm.getObject().isLiteral()) {
		                    //System.out.println("La position :"+stm.getLiteral().getLexicalForm().toString()+" type = "+stm.getPredicate().getLocalName());
		                    PositionOp = stm.getLiteral().getLexicalForm().toString();
		                    nbNodes++;
		        	    }
		                else { 
		                	System.out.println("Error");
		                }
		        // Ajouter l'operation au mapping
		        map.Operations.add(new ETLOperation(NameOp,ResourceOp,PositionOp));
	             } }
	    	
	    	// Ajouter le mapping au besoin
	    	for(int i = 0; i < requirements.size(); i++)
	        {
	    		if (requirements.get(i).requirementName.equals(RequirementName))
	    		{
	    			map.setNbNodes(nbNodes);
	    			map.setNbExt(nbExt);
	    			map.setNbInt(nbInt);
	    			map.setSourceExt(sourceExt);
	    			map.setSourceInt(sourceInt);
	    			requirements.get(i).addSourcesExt(sourceExt);
	    			requirements.get(i).addSourcesInt(sourceInt);
	    			requirements.get(i).mappings.add(map);
	    			requirements.get(i).addNbNodes(nbNodes);
	    			requirements.get(i).addNbExt(nbExt);
	    			requirements.get(i).addNbInt(nbInt);
	    			//System.out.print("Description map :"+map.displayETLOperations());
	    			affichBesoin.setText(displayRequirement(requirements.get(i)));
	    		}
	        }
		}
	   
	   
	// Renvoi une chaine de caractère qui contient tout les details d'un besoin
		public String displayRequirement(Requirements R){
			String result="";
			result = result +"- Requirement name : "+ R.requirementName + "\n";
			result= result +"==============================================\n";
			result = result +"- The number of nodes :"+ R.getNbNodes() + "\n";
			result = result +"- "+R.getNbMappings()+" Mappings applied :\n";
	    	for(int i = 0; i < R.mappings.size(); i++)
	        {
	    		result = result +"Mapping : "  + R.mappings.get(i).getETLName() +"\n";
	        }
			
			result = result +"==============================================\n";
			
	    	
			return result;
		}
		
	/*************************************** Inferred and inserted Instances **************************************************/
		@FXML
	    private Button butInstances;
		
		@FXML
	    private TextArea instancesDesc;
		
		public int nbInserted=0;
		public int nbInferred=0;
		String result="";
		@FXML
		public void instancesAct() {
			InfModel inf = ModelFactory.createRDFSModel(Controller.ontology); 
			result="";
			for (int i=0; i < requirements.size();i++){
				nbInserted =0;
				nbInferred=0;
				requirements.get(i).nbInserted = 0;
				requirements.get(i).nbInferred = 0;
				for (int j=0; j < requirements.get(i).mappings.size();j++){
					nbInserted =0;
					nbInferred=0;
					for (int k=0; k < requirements.get(i).mappings.get(j).Operations.size();k++){
						
						if (requirements.get(i).mappings.get(j).Operations.get(k).getName().contains("load") ==false){
							OntClass Res = Controller.ontology.getOntClass(requirements.get(i).mappings.get(j).Operations.get(k).getResource());
							//Extraire les individues de la classe 
							for ( final ExtendedIterator<? extends OntResource> merlots = Res.listInstances(); merlots.hasNext(); ) {
					            nbInserted++;
								System.out.println("Inserted "+nbInserted+" :"+merlots.next().getLocalName());
					        }	
							
							String rules = "[rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]";
						    Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
						    reasoner.setDerivationLogging(true);
						    InfModel infer = ModelFactory.createInfModel(reasoner, Controller.ontology);
						    Resource Res1 = infer.getResource(requirements.get(i).mappings.get(j).Operations.get(k).getResource());
						    PrintWriter out = new PrintWriter(System.out);
						    
						    for (StmtIterator in = Res1.listProperties(); in.hasNext(); ) {
						        Statement s = in.nextStatement(); 
						        nbInferred++;
								System.out.println("Inferred "+ nbInferred +" :"+s);
						        for (Iterator id = infer.getDerivation(s); id.hasNext(); ) {
						            Derivation deriv = (Derivation) id.next();
						            System.out.print(deriv.toString());
						            deriv.printTrace(out, true); 
						            }     
						        }
						    out.flush(); 	
						} 
					
					requirements.get(i).mappings.get(j).nbInserted = nbInserted;
					requirements.get(i).mappings.get(j).nbInferred = nbInferred + nbInserted;
					}
				}
				requirements.get(i).addNbInferred(requirements.get(i).calculateNbInferred());
				requirements.get(i).addNbInserted(requirements.get(i).calculateNbInserted());
				result = result + displayRequirementInstances(requirements.get(i));
			}
			
			instancesDesc.setText(result);
			
		}
		
	
		// Renvoi une chaine de caractère qui contient tout les details d'un besoin
				public String displayRequirementInstances(Requirements R){
					String result="";
					result = result +"\n==========================================================\n";
					result = result +"- Requirement : "+ R.requirementName + "\n";
					result = result +"- Mappings applied :\n";
			    	for(int i = 0; i < R.mappings.size(); i++)
			        {
			    		result = result +"Mapping : "  + R.mappings.get(i).getETLName() +"\n";
			        }
			    	//sresult = result +"- There is "+ R.getNbExt() + " extern sources : \n"+  +;
			    	result = result +"- The number of inserted instances : "+ R.getNbInserted() + "\n";
			    	result = result +"- The number of inferred instances : "+ R.getNbInferred() + "\n";
			    	result = result +"\n==========================================================\n";
			    	
			    	
					return result;
				}
	/************************************** La page Gestion des besoins*******************************************************/
	
	
	@FXML
    private TextArea resultatBesoin;
	@FXML
    private TextArea requirementQuery;
	@FXML
    private TextArea requirementDescr;
	@FXML
	private TextArea requirementResultQuery;
	@FXML
    public Label errorRequirementLoad;

	
	@FXML
    void choixBesoinAct() {
		
		
		if ( choixBesoin.getValue().toString().equals("Requirement_1")) {
			Controller.ontology = openOWL.OpenConnectOWL();
			String queryString = "prefix bsbm: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/> "
					+ "prefix rev: <http://purl.org/stuff/rev#>  "
					+ "Select ?productType ?reviewCount {  {"
					+ "Select ?productType (count(?review) As ?reviewCount)   {"
					+ "?productType a bsbm:ProductType ."
					+ "?product a ?productType ."
					+ "?product bsbm:producer ?producer ."
					+ "?producer bsbm:country Country1 ."
					+ "?review bsbm:reviewFor ?product ."
					+ "?review rev:reviewer ?reviewer ."
					+ "?reviewer bsbm:country Country2 .}"
					+ "Group By ?productType  } } Order By desc(?reviewCount) ?productType Limit 10";
	 //  String s = go.toString();
	   String s = openOWL.GetResultAsString(queryString); //call method GetResultAsString from OpenOWL class
		   if (s == null)
		   {
			   errorRequirementLoad.setText("There is an error in the syntax of the query");
		   } else {
			   requirementDescr.setText(requirementList.get(0));
			   requirementQuery.setText(queryString);
			   requirementResultQuery.setText(s);
			   
			   // Pour generaliser le préfixe de le calsse doit être une variable en entrée [ A revoir]
			   OntClass requirement = Controller.ontology.getOntClass("http://www.co-ode.org/ontologies/ont.owl#Requirement"); 
		   }
		} else {
			if ( choixBesoin.getValue().toString().equals("Requirement_2")) {
				Controller.ontology = openOWL.OpenConnectOWL();
				String queryString = "prefix bsbm: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/> "
		                + "prefix ind:<http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/> "
		                + "prefix indProduct: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/dataFromProducer1/>"
		                + "SELECT ?otherProduct ?sameFeatures {"
		                + "?otherProduct a bsbm:Product ."
		                + "FILTER(?otherProduct != indProduct:Product1) {"
		                + "SELECT ?otherProduct (count(?otherFeature) As ?sameFeatures) {"
		    			+ " indProduct:Product1 bsbm:productFeature ?feature ."
		    			+ "?otherProduct bsbm:productFeature ?otherFeature ."
		    			+ "FILTER(?feature=?otherFeature) }"
		    			+ "Group By ?otherProduct }}"
		    			+ "Order By desc(?sameFeatures) ?otherProduct Limit 10";
		 //  String s = go.toString();
		   String s = openOWL.GetResultAsString(queryString); //call method GetResultAsString from OpenOWL class
		 // test ;)   
			   if (s == null)
			   {
				   errorRequirementLoad.setText("There is an error in the syntax of the query");
			   } else {
				   requirementDescr.setText(requirementList.get(1));
				   requirementQuery.setText(queryString);
				   requirementResultQuery.setText(s);
			   }
		   } else {
			   if ( choixBesoin.getValue().toString() == "Requirement_3") {
				   Controller.ontology = openOWL.OpenConnectOWL();
					String queryString = "prefix bsbm: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/>"
							+ "prefix bsbm-inst: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/>"
							+ "prefix rev: <http://purl.org/stuff/rev#>"
							+ "prefix dc: <http://purl.org/dc/elements/1.1/>"
							+ "prefix xsd: <http://www.w3.org/2001/XMLSchema#>"
							+ "Select ?product (xsd:float(?monthCount)/?monthBeforeCount As ?ratio)   {{"
							+ "Select ?product (count(?review) As ?monthCount)       {"
							+ "?review bsbm:reviewFor ?product ."
							+ "?review dc:date ?date ."
							+ "Filter(?date >= '2008-01-01'^^<http://www.w3.org/2001/XMLSchema#date> && ?date < '2008-05-01'^^<http://www.w3.org/2001/XMLSchema#date>) }"
							+ "Group By ?product }  {"
							+ "Select ?product (count(?review) As ?monthBeforeCount){"
							+ "?review bsbm:reviewFor ?product ."
							+ "?review dc:date ?date ."
							+ "Filter(?date >= '2007-01-01'^^<http://www.w3.org/2001/XMLSchema#date> && ?date < '2008-01-01'^^<http://www.w3.org/2001/XMLSchema#date>) #}"
							+ "Group By ?product Having (count(?review)>0)     }   }"
							+ "Order By desc(xsd:float(?monthCount) / ?monthBeforeCount) ?product   Limit 10";
			 //  String s = go.toString();
			   String s = openOWL.GetResultAsString(queryString); //call method GetResultAsString from OpenOWL class
			 // test ;)   
				   if (s == null)
				   {
					   errorRequirementLoad.setText("There is an error in the syntax of the query");
				   } else {
					   requirementDescr.setText(requirementList.get(2));
					   requirementQuery.setText(queryString);
					   requirementResultQuery.setText(s);
				   } 
			   } else {
				   if ( choixBesoin.getValue().toString() == "Requirement_4") {
					   Controller.ontology = openOWL.OpenConnectOWL();
						String queryString = "prefix bsbm: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/>"
								+ "prefix bsbm-inst: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/>"
								+ "prefix indProduct: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/dataFromProducer1/>"
								+ "prefix xsd: <http://www.w3.org/2001/XMLSchema#>"
								+ "Select ?feature ((?sumF*(?countTotal-?countF))/(?countF*(?sumTotal-?sumF)) As ?priceRatio){"
								+ "{ Select (count(?price) As ?countTotal) (sum(xsd:float(str(?price))) As ?sumTotal){"
								+ "?product a bsbm:Product ."
								+ "?offer bsbm:product ?product ;"
								+ "bsbm:price ?price .}}"
								+ "{ Select ?feature (count(?price2) As ?countF) (sum(xsd:float(str(?price2))) As ?sumF){"
								+ "?product2 a  bsbm:Product ;"
								+ "bsbm:productFeature ?feature ."
								+ "?offer2 bsbm:product ?product2 ;"
								+ "bsbm:price ?price2 .}"
								+ "Group By ?feature}}"
								+ "Order By desc(?priceRatio) ?feature";
				 //  String s = go.toString();
				   String s = openOWL.GetResultAsString(queryString); //call method GetResultAsString from OpenOWL class
				 // test ;)   
					   if (s == null)
					   {
						   errorRequirementLoad.setText("There is an error in the syntax of the query");
					   } else {
						   requirementDescr.setText(requirementList.get(3));
						   requirementQuery.setText(queryString);
						   requirementResultQuery.setText(s);
					   }
				   } else {
					   if ( choixBesoin.getValue().toString() == "Requirement_5") {
						   Controller.ontology = openOWL.OpenConnectOWL();
							String queryString = "prefix bsbm: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/>"
									+ "prefix bsbm-inst: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/>"
									+ "prefix rev: <http://purl.org/stuff/rev#>"
									+ "prefix xsd: <http://www.w3.org/2001/XMLSchema#>"
									+ "Select ?country ?product ?nrOfReviews ?avgPrice{"
									+ "{ Select ?country (max(?nrOfReviews) As ?maxReviews){"
									+ "{ Select ?country ?product (count(?review) As ?nrOfReviews){"
									+ "?product a bsbm:Product ."
									+ "?review bsbm:reviewFor ?product ;"
									+ "rev:reviewer ?reviewer ."
									+ "?reviewer bsbm:country ?country .}"
									+ "Group By ?country ?product}}"
									+ "Group By ?country}"
									+ "{ Select ?product (avg(xsd:float(str(?price))) As ?avgPrice){"
									+ "?product a bsbm:Product ."
									+ "?offer bsbm:product ?product ."
									+ "?offer bsbm:price ?price .}"
									+ "Group By ?product}"
									+ "{ Select ?country ?product (count(?review) As ?nrOfReviews){"
									+ "?product a bsbm:Product ."
									+ "?review bsbm:reviewFor ?product .?review rev:reviewer ?reviewer ."
									+ "?reviewer bsbm:country ?country .}"
									+ "Group By ?country ?product}"
									+ "FILTER(?nrOfReviews=?maxReviews)}"
									+ "Order By desc(?nrOfReviews) ?country ?product";
					 //  String s = go.toString();
					   String s = openOWL.GetResultAsString(queryString); //call method GetResultAsString from OpenOWL class
					 // test ;)   
						   if (s == null)
						   {
							   errorRequirementLoad.setText("There is an error in the syntax of the query");
						   } else {
							   System.out.println(requirementList.get(4));
							   requirementDescr.setText(requirementList.get(4));
							   requirementQuery.setText(queryString);
							   requirementResultQuery.setText(s);
						   } 
					   } else 
					   {
						   if ( choixBesoin.getValue().toString() == "Requirement_6") {
							   Controller.ontology = openOWL.OpenConnectOWL();
								String queryString = "prefix bsbm: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/>"
										+ "prefix bsbm-inst: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/>"
										+ "prefix rev: <http://purl.org/stuff/rev#>"
										+ "prefix indProduct: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/dataFromProducer1/>"
										+ "Select ?reviewer (avg(xsd:float(?score)) As ?reviewerAvgScore){"
										+ "{ Select (avg(xsd:float(?score)) As ?avgScore){"
										+ "?product bsbm:producer indProduct:Producer1 ."
										+ "?review bsbm:reviewFor ?product ."
										+ "{ ?review bsbm:rating1 ?score . } UNION"
										+ "{ ?review bsbm:rating2 ?score . } UNION"
										+ "{ ?review bsbm:rating3 ?score . } UNION"
										+ "{ ?review bsbm:rating4 ?score . }}}"
										+ "?product bsbm:producer indProduct:Producer1 ."
										+ "?review bsbm:reviewFor ?product ."
										+ "?review rev:reviewer ?reviewer ."
										+ "{ ?review bsbm:rating1 ?score . } UNION"
										+ "{ ?review bsbm:rating2 ?score . } UNION"
										+ "{ ?review bsbm:rating3 ?score . } UNION"
										+ "{ ?review bsbm:rating4 ?score . }}"
										+ "Group By ?reviewer"
										+ "Having (avg(xsd:float(?score)) > min(?avgScore))";
						 //  String s = go.toString();
						   String s = openOWL.GetResultAsString(queryString); //call method GetResultAsString from OpenOWL class
						 // test ;)   
							   if (s == null)
							   {
								   errorRequirementLoad.setText("There is an error in the syntax of the query");
							   } else {
								   requirementDescr.setText(requirementList.get(5));
								   requirementQuery.setText(queryString);
								   requirementResultQuery.setText(s);
							   } 
						   } else {
							   if ( choixBesoin.getValue().toString() == "Requirement_7") {
								   Controller.ontology = openOWL.OpenConnectOWL();
									String queryString = "prefix bsbm: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/>"
											+ "prefix bsbm-inst: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/>"
											+ "prefix xsd: <http://www.w3.org/2001/XMLSchema#>"
											+ "Select ?product{"
											+ "{ Select ?product{"
											+ "{ Select ?product (count(?offer) As ?offerCount){ "
											+ "?product a bsbm:Product ."
											+ "?offer bsbm:product ?product ."
											+ "Group By ?product}}"
											+ "Order By desc(?offerCount)"
											+ "Limit 1000}"
											+ "FILTER NOT EXISTS{"
											+ "?offer bsbm:product ?product ."
											+ "?offer bsbm:vendor ?vendor ."
											+ "?vendor bsbm:country ?country ."
											+ "FILTER(?country=<http://downlode.org/rdf/iso-3166/countries#GB>)}	}";
							 //  String s = go.toString();
							   String s = openOWL.GetResultAsString(queryString); //call method GetResultAsString from OpenOWL class
							 // test ;)   
								   if (s == null)
								   {
									   errorRequirementLoad.setText("There is an error in the syntax of the query");
								   } else {
									   requirementDescr.setText(requirementList.get(6));
									   requirementQuery.setText(queryString);
									   requirementResultQuery.setText(s);
								   }
							   } else {
								   if ( choixBesoin.getValue().toString() == "Requirement_8") {
									   Controller.ontology = openOWL.OpenConnectOWL();
										String queryString = "prefix bsbm: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/>"
												+ "prefix bsbm-inst: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/>"
												+ "prefix xsd: <http://www.w3.org/2001/XMLSchema#>"
												+ "Select ?vendor (xsd:float(?belowAvg)/?offerCount As ?cheapExpensiveRatio){"
												+ "{ Select ?vendor (count(?offer) As ?belowAvg){"
												+ "{ ?product a bsbm:Product ."
												+ "?offer bsbm:product ?product ."
												+ "?offer bsbm:vendor ?vendor ."
												+ "?offer bsbm:price ?price ."
												+ "{ Select ?product (avg(xsd:float(str(?price))) As ?avgPrice){"
												+ "?product a bsbm:Product ."
												+ "?offer bsbm:product ?product ."
												+ "?offer bsbm:vendor ?vendor ."
												+ "?offer bsbm:price ?price .}"
												+ "Group By ?product}} ."
												+ "FILTER (xsd:float(str(?price)) < ?avgPrice)}"
												+ "Group By ?vendor}"
												+ "{ Select ?vendor (count(?offer) As ?offerCount){"
												+ "?product a bsbm:Product ."
												+ "?offer bsbm:product ?product ."
												+ "?offer bsbm:vendor ?vendor .}"
												+ "Group By ?vendor}}"
												+ "Order by desc(xsd:float(?belowAvg)/?offerCount) ?vendor"
												+ "limit 10";
								 //  String s = go.toString();
								   String s = openOWL.GetResultAsString(queryString); //call method GetResultAsString from OpenOWL class
								 // test ;)   
									   if (s == null)
									   {
										   errorRequirementLoad.setText("There is an error in the syntax of the query");
									   } else {
										   requirementDescr.setText(requirementList.get(7));
										   requirementQuery.setText(queryString);
										   requirementResultQuery.setText(s);
									   } 
								   }
							   }
						   }
					   }
				   }
			   }
		   }
		}
    	
    }
	
	

}
