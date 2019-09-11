package dz.esi.outil_onto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.iterator.ExtendedIterator;

import dz.esi.outil_onto.model.RequirementManger.KPI;
import dz.esi.outil_onto.model.RequirementManger.Requirements;
import dz.esi.outil_onto.model.ETLOperators.ETL;
import dz.esi.outil_onto.model.ETLOperators.ETLOperation;
import dz.esi.outil_onto.model.ETLOperators.typeETLOp;
import dz.esi.outil_onto.model.DataSources.DataSource;
import dz.esi.outil_onto.model.DataSources.IntermSource;
import dz.esi.outil_onto.model.DataSources.TypeSource;
import dz.esi.outil_onto.model.ETLMappings.ETLOperations;
import dz.esi.outil_onto.model.ETLMappings.Mappings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MappingCont implements Initializable{

	public MappingCont() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//initializeMappings();
	}
	
	//private MainCont main;
	
	@FXML private AnchorPane mappingContent;
	@FXML private TextArea sourceInstances;
	@FXML private TextArea targetInstances;
    @FXML private TextArea mappingName;
    @FXML private TextArea etlOperations;
    @FXML private TextArea targetClass;
    @FXML private SplitPane openOWLContent1111;
    @FXML private Button butExec;
    @FXML private Button butExec1;
    @FXML private Button butReqMap;
    @FXML public ComboBox comboMap;
    @FXML public ComboBox comboMap1;
    @FXML private Label source;
    @FXML private Rectangle op2Rect;
    @FXML private Label op2;
    @FXML private Rectangle op3Rect;
    @FXML private Label op1;
    @FXML private Label Target;
    @FXML private Label op3;
    @FXML private Circle sourceCircle;
    @FXML private Circle targetCircle;
    @FXML private Rectangle op1Rect;
    
    

    // Contient la liste des noms des mappings des combobox 
    public ObservableList<String> mappingList = FXCollections.observableArrayList();
    
    // mapping properties
    Property hasinputSet = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#hasinputSet");
	Property hasoutputSet = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#hasoutputSet");
	Property Resource = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#Resource");
    Property position = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#position");
    
    // Liste des besoins 
    public static ArrayList<Requirements> requirements =new ArrayList<Requirements>();
    
    
    /***********************************************************************************************************************/
    //Afficher les éléments du mapping ETL choisit 
    @FXML
    public void comboMapAct(ActionEvent event) {
    	String text = "";
    	String NameOp = "";
    	String ResourceOp = "";
    	String PositionOp = "";
    	System.out.println(comboMap.getValue());
    	String val =comboMap.getValue().toString();
    	if (val != "Mapping List")
    	{	
    		System.out.println(val);
    		for (ETLOperations m:Mappings.getMappings()) {
    			if (m.ETLName.equals(val)){
    				mappingName.setText(m.ETLName);
    				targetClass.setText(m.Target);
    				etlOperations.setText(m.displayETLOperations());
    			}
    		}
    		
    	}
    }
    
    /*********************************************************************************************************************/
    // Execute an ETLWorkflow (Mapping)
    
    @FXML 
    public void executeMapping(ActionEvent event){
    	String val =comboMap1.getValue().toString();
    	Model output=null;
    	Model input=null;
    	DataSource DS=null;
    	String result="";
    	if (!(val.equals("Mapping List")))
    	{
    		for (ETLOperations m:Mappings.getMappings()) {
    			if (m.ETLName.equals(val)){
    				//ArrayList<ETLOperation> listeOp = ETL.orderETLOperations(m.getETLOperations());
    				for (ETLOperation ETLOp:ETL.orderETLOperations(m.getETLOperations())) {
    					System.out.println("Passe a l'autre");
    	    			if ((ETLOp.getType()!=typeETLOp.Load)){
        					DS = ETL.sourceType(ETLOp);
        					if(DS.getType()==TypeSource.Intermediate){
        						DS.setDataset(input);
        					}
        					ETLOp.setInput(DS);
    	    				ETLOp.setOutput(ETLOp.run(DS));
    	    				input=ETLOp.getOutput();
    	    				if(ETLOp.getPosition().equals("1")){
    	    					sourceInstances.setText(ETL.convertModelToString(ETLOp.getOutput()));
    	    				}
    	    				result = ETL.convertModelToString(ETLOp.getOutput());
    	    				System.out.println("Coucouuuuuuu output :"+result+"\n");
    	    			}
    	    		}
    				targetInstances.setText(result);
    			}
    		}
    	}	
    }
    
    @FXML 
    public void clearResult(ActionEvent event){
    	sourceInstances.clear();
    	targetInstances.clear();
    }
    /*********************************************************************************************************************/
 // Initialisation des combobox avec les mappings ETL, et les besoins se trouvant dans l'ontology
   @FXML
    public void initializeMappings() {

    	String text="";
		int i=0;
		String instance;
    	String NameOp = "";
    	String ResourceOp = "";
    	String PositionOp = "";
    	// les indicateurs
    	int nbNodes =0;
    	int nbExt=0;
    	int nbInt=0;
		String strg="";
		
    	// Initialiser les combobox avec les noms des mappings
    	OntClass mapping = Controller.ontology.getOntClass("http://www.co-ode.org/ontologies/ont.owl#ETL"); 
    	for ( final ExtendedIterator<? extends OntResource> merlots = mapping.listInstances(true); merlots.hasNext(); ) {
            instance = merlots.next().getLocalName();
            if (instance.toLowerCase().contains(strg.toLowerCase())){
	            mappingList.add(instance);
            }
            i++;
    	}
    	comboMap.setValue("Mapping List");
    	comboMap.setItems(mappingList);
    	comboMap1.setValue("Mapping List");
    	comboMap1.setItems(mappingList);
    	
    	
       	
    	
       	
       	for (String m:mappingList) {
       		ETLOperations ETLOp = new ETLOperations();
    		ArrayList<ETLOperation> ListOperations = new ArrayList<ETLOperation>();
    		ListOperations.removeAll(ListOperations);
       		
       	// Recupérer l'ETL choisit ainsi que les propriétés "hasinputSet" et "hasoutputSet" de celui-ci 
    		Property hasinputSet = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#hasinputSet");
        	Property hasoutputSet = Controller.ontology.getProperty("http://www.co-ode.org/ontologies/ont.owl#hasoutputSet");
        	Resource ETLmap = Controller.ontology.getIndividual("http://www.co-ode.org/ontologies/ont.owl#"+ m);
        	String ETLName= ETLmap.getLocalName();
        	ETLOp.ETLName = ETLmap.getLocalName();
        	nbNodes++;
        	
        	
        	// Afficher la classe cible
        	String tgt="";
        	StmtIterator stmtIterator1 = Controller.ontology.listStatements(ETLmap, hasoutputSet, (RDFNode) null);
        	while (stmtIterator1.hasNext()){
        	    Statement s = stmtIterator1.nextStatement();
        	    Resource targetClass = (Resource) s.getObject();
        	    tgt= targetClass.toString()+ "\n";
        	    ETLOp.Target= targetClass.toString();
        	    nbNodes++;
        	}
        	
        	
        	// Récupérer et afficher l'ensemble des operations ETL que contient le mapping choisit
        	StmtIterator stmtIterator2 = Controller.ontology.listStatements(ETLmap, hasinputSet, (RDFNode) null);
        	while (stmtIterator2.hasNext()){
        		int g=0;
        	    Statement stmt = stmtIterator2.nextStatement();
        	    Resource operation = (Resource) stmt.getObject();
        	     NameOp=operation.toString();
        	     ETLOperation ETLO = ETL.verifyType(operation); //Detect the type of the ETL operator using the Resource, and return a new object of an ETL operation
        	     ETLO.setName(operation.toString());
        	     System.out.println("l'operation "+g+" est :"+ETLO.getName() +"\n");
        	     nbNodes++;
        	    
        	    // Afficher la classe resource de l'operation 
        	    StmtIterator it = Controller.ontology.listStatements(operation, Resource, (RDFNode) null);
                while ( it.hasNext()) {
                Statement st = (Statement) it.next(); 
	        	    if (st.getObject().isLiteral()== false) {
	        	    	//System.out.println("La ressource :"+st.getObject().toString()+" type = "+st.getPredicate().getLocalName());
	                	 ResourceOp=st.getObject().toString();
	                	 if (ETL.verifyExternalResource( ResourceOp)){
	                		 ETLO.setTypeResource(TypeSource.External);
	                	 }
	                	 else {
	                		 ETLO.setTypeResource(TypeSource.Internal);
	                	 }
	                	 ETLO.setResource(st.getObject().toString());
	                	 ETLO.addResource(st.getObject().toString());
	                	 System.out.println("###### la resource "+g+" est :"+ETLO.getResource() +"\n Le type de la resource : "+ ETLO.getTypeResource()+"\n\n");
	                	 nbNodes++;
	                }
	                else { 
	                	System.out.println("Error");
                     }
                }
                
                //Afficher la position de l'operation dans le processus de mapping
        	    it = Controller.ontology.listStatements(operation, position, (RDFNode) null);
                while ( it.hasNext()) {
                Statement stm = (Statement) it.next(); 
	        	    if (stm.getObject().isLiteral()) {
	                    //System.out.println("La position :"+stm.getLiteral().getLexicalForm().toString()+" type = "+stm.getPredicate().getLocalName());
	                    PositionOp = stm.getLiteral().getLexicalForm().toString();
	                    ETLO.setPosition(stm.getLiteral().getLexicalForm().toString());
	                    System.out.println("la position "+g+" est :"+ETLO.getPosition()+"\n");
	                    nbNodes++;
	        	    }
	                else { 
	                	System.out.println("Error");
	                }
                }
                System.out.print("\n\n");
                ETLOp.Operations.add(ETLO);
                i++;
        	}
        	Mappings.mappings.add(ETLOp);
        	//ETLOp.setETLOperations(ListOperations);
			//Mappings.mappings.add(new ETLOperations(ListOperations,tgt,ETLName,nbNodes));
        	
			nbNodes=0;
       	}
    }
   }
