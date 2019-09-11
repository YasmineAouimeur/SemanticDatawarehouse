package dz.esi.outil_onto.controller;

import dz.esi.outil_onto.model.OntologyManager.Jena;
import dz.esi.outil_onto.model.RequirementManger.KPI;
import dz.esi.outil_onto.model.OntologyManager.openOWL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RSIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.util.iterator.ExtendedIterator;

import dz.esi.outil_onto.controller.*;

public class OntologyCont implements Initializable{

	public OntologyCont() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//OpenOWL();
	}
	
	
	// Menu de l'interface acceuil 
		@FXML
		 private Button openOWLMenuItem;
		@FXML
		 private VBox Message;
		@FXML
		 private AnchorPane BackMessage;
		 @FXML
		 private Button addOWLMenuItem;
		 @FXML
		 private SplitPane openOWLContent;
		 @FXML
		 private HBox accueilMenu;
		 @FXML
		 private Label OpenOWLMessage;
		 @FXML
		 private TextField Onto;
		 @FXML
		 private TextField Prefi;
		 
		 //Methodes
		@FXML
		 void openMenuItemAct(ActionEvent event) {
			 openOWLContent.setVisible(true);
		 }

		 @FXML
		 void addOWLMenuItemAct(ActionEvent event) {
			 openOWLContent.setVisible(false);
		 }
		 

		//Recuperation de l'ontologie interne et de sont préfixe
		 @FXML
		    void OpenOWL() {
		    	
		    	openOWL.path = Onto.getText(); 
		    	Controller.ontology = openOWL.OpenConnectOWL();
		    	if (Controller.ontology == null)
		    	{
		    		OpenOWLMessage.setText(" Le fichier" + Onto.getText() + " est introuvable");
		    		BackMessage.setVisible(true);
		    		Message.setVisible(true);
		    	} else {
		    		OpenOWLMessage.setText(" The file " + Onto.getText() + " is successfuly opened! ");
		    		BackMessage.setVisible(true);
		    		Message.setVisible(true);
		    	}
		    	Jena.prefix = Prefi.getText();
			}
		 @FXML
		  public void OK(){
			 BackMessage.setVisible(false);
	    		Message.setVisible(false);
		 }
		
	    
		
		/********************************************* Manage metadata*************************************************/
		 @FXML
		 private TextField metadata;
		 @FXML
		 private TextArea instancesTextField;
		 
		 @FXML
		  public void extractMetadata(){
			 String result="";
			 Model modelExt = ModelFactory.createDefaultModel() ;
			 //model.read("C:/Users/houria/Desktop/DataSet/Company-Reif.ttl") ;
			 modelExt = RDFDataMgr.loadModel("C:/Users/houria/Desktop/DataSet/Reif-data.rdf");
			 //modelExt = RDFDataMgr.loadModel("C:/Users/houria/Desktop/DataSet/Nary.rdf");
			 if (modelExt != null){
				 System.out.println("\n here is a Graph :");
				 StmtIterator R = modelExt.listStatements();
				 while (R.hasNext()){
					 String inst = R.next().toString();
					 result = result + inst + "\n";
					 System.out.println("\n Voila resultat : " + inst);
					 
				 }
				 
				 }else{
				 System.out.println("\n Rien de rien");
			 }
			 instancesTextField.setText(result);
			 /*DatasetGraph dg = RDFDataMgr.loadDatasetGraph(metadata.getText(), Lang.TURTLE);
					 
			 Graph g = dg.getDefaultGraph();
			 
			 		/*Iterator<Node> nodeIt = dg.listGraphNodes();
					 while (nodeIt.hasNext()) {
					 Node node = nodeIt.next();
					 Graph graph = dg.getGraph(node);
					 System.out.println("\n here is a Graph :"+ graph.toString());
					 
					 }			 System.out.println("\n here is a Graph :"+ g.toString());*/
					 }
}
