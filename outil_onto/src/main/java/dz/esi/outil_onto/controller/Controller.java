package dz.esi.outil_onto.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;


import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.*;

import com.sun.glass.ui.View;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import org.apache.jena.assembler.JA ;
import org.apache.jena.shared.impl.* ;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.* ;
import org.semanticweb.owlapi.model.OWLIndividual;

import dz.esi.outil_onto.model.*;
import dz.esi.outil_onto.controller.*;



public class Controller implements Initializable {
	
	// Declaration des variables de l'ontology
	public static OntModel ontology= ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RULE_INF );

	
	
	
	//Declaration des variables de javafx
	@FXML
	private TextField Hello;
	@FXML
	private Button But;
	@FXML
	private Button GestionBesoins;
	@FXML
	 private Button OntoPrefi;
	 
	 
	 
	 
	public Controller() {
			// TODO Auto-generated constructor stub
	}
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
		//OntologyCont.openOWL();
			
	}

/************************************** Le menu general *******************************************************/
	@FXML
    private Button accueilMenuItem;
	@FXML
    private Button besoinsMenuItem;
	@FXML
    private Button ETLMenuItem;
	@FXML
    private Button mappingMenuItem;
	@FXML
    private Button statistiquesMenuItem;
	@FXML
    private AnchorPane accueilContent;
	@FXML
    private AnchorPane besoinsContent;
	@FXML
    private AnchorPane ETLContent;
	@FXML
    private AnchorPane mappingContent;
	@FXML
    private AnchorPane statistiquesContent;
	@FXML
    private AnchorPane test;
	@FXML
    private AnchorPane requirementContent;
	
	
	
	@FXML
    void accueilMenuItemAct(ActionEvent event) {
		accueilContent.setVisible(true);
		accueilMenuItem.focusedProperty();
		requirementContent.setVisible(false);
		ETLContent.setVisible(false);
		mappingContent.setVisible(false);
		statistiquesContent.setVisible(false);
    }
	
	
	
	@FXML
    void besoinsMenuItemAct(ActionEvent event) throws IOException {
		//AnchorPane pane = FXMLLoader.load(getClass().getResource("RequirementsView.fxml"));
		//requirementContent.getChildren().setAll(pane);
		requirementContent.setVisible(true);
		accueilContent.setVisible(false);
		ETLContent.setVisible(false);
		mappingContent.setVisible(false);
		statistiquesContent.setVisible(false);
		
    }
	
	@FXML
    void ETLMenuItemAct(ActionEvent event) throws IOException {
		//AnchorPane pane = FXMLLoader.load(getClass().getResource("ETLsView.fxml"));
		//ETLContent.getChildren().setAll(pane);
		ETLContent.setVisible(true);
		accueilContent.setVisible(false);
		requirementContent.setVisible(false);
		mappingContent.setVisible(false);
		statistiquesContent.setVisible(false);

    }
	
	
	@FXML
    void MappingMenuItemAct(ActionEvent event) throws IOException {
		//MappingCont.initializeMappings();
		//AnchorPane pane = FXMLLoader.load(getClass().getResource("MappingView.fxml"));
		//mappingContent.getChildren().setAll(pane);
		mappingContent.setVisible(true);
		accueilContent.setVisible(false);
		requirementContent.setVisible(false);
		ETLContent.setVisible(false);
		statistiquesContent.setVisible(false);

    }
	
	@FXML
    void statistiquesMenuItemAct(ActionEvent event) throws IOException {
		//statistiquesContent.setVisible(true);
		//AnchorPane pane = FXMLLoader.load(getClass().getResource("StatisticsView.fxml"));
		//statistiquesContent.getChildren().setAll(pane);
		statistiquesContent.setVisible(true);
		accueilContent.setVisible(false);
		requirementContent.setVisible(false);
		ETLContent.setVisible(false);
		mappingContent.setVisible(false);

    }
	

	

	

    
	
	
}
