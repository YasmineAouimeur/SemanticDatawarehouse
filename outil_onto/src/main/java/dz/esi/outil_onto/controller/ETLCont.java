package dz.esi.outil_onto.controller;

import java.util.ArrayList;

import org.apache.jena.rdf.model.Model;

import dz.esi.outil_onto.model.DataSources.DataSource;
import dz.esi.outil_onto.model.ETLMappings.ETLOperations;
import dz.esi.outil_onto.model.ETLMappings.Mappings;
import dz.esi.outil_onto.model.ETLMetadata.DSA;
import dz.esi.outil_onto.model.ETLMetadata.DuplicatedInstances;
import dz.esi.outil_onto.model.ETLMetadata.ExtractionPhase;
import dz.esi.outil_onto.model.ETLMetadata.LoadingPhase;
import dz.esi.outil_onto.model.ETLMetadata.MetaSchema;
import dz.esi.outil_onto.model.ETLMetadata.TransformationPhase;
import dz.esi.outil_onto.model.ETLOperators.ETL;
import dz.esi.outil_onto.model.ETLOperators.ETLOperation;
import dz.esi.outil_onto.model.OntologyManager.Jena;
import dz.esi.outil_onto.model.OntologyManager.openOWL;
import dz.esi.outil_onto.model.RequirementManger.Requirements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ETLCont {

	public ETLCont() {
		// TODO Auto-generated constructor stub
	}
	
	//Metadata unification POP-UP
	
	@FXML
	public TextArea instData;
	@FXML
	public TextArea instMeta;
	@FXML
	 private VBox Message;
	@FXML
	 private AnchorPane BackMessage;
	@FXML
    public ComboBox comboMap;
	@FXML
    public ComboBox comboDupInstances;
	@FXML
	public TextArea TargetClass;
	@FXML
	public TextArea InternalClasses;
	@FXML
	public TextArea ExternalClasses;
	@FXML
	public TextArea Formats;
	@FXML
	public TextArea IntInstances;
	@FXML
	public TextArea ExtInstances;
	@FXML
	public TextArea UnionInstances;
	@FXML
	public TextArea metaProp;
	public Label InstanceValue; // Instance concerned by metadata overlapping
	@FXML
    public ComboBox metaPropertyToCheck; 
	@FXML
    public ComboBox metaValueToKeep;
	@FXML
    public ComboBox comboMetaOver;
	@FXML
	public TextArea selectedMetaValues;
	@FXML
	public TextArea allMetaValues;
	@FXML
	public TextArea ListExtractedMetaProp;
	@FXML
	public TextArea dsa;
	@FXML
	public TextArea SDW;
	/* -------------------------------- Loading Phase ---------------------------------------------*/
	@FXML
    void  showExtractedMetaProperties(){
		ListExtractedMetaProp.setText(DSA.showMetaPropertyList());
	}
	
	@FXML
    void  affectMetaSchema(){
		LoadingPhase.affectMetaMappings();
	}
	
	@FXML
    void  loadMetaSchema(){
		String res ="";
		LoadingPhase.affectMetaMappings();
		for (MetaSchema m:DSA.metaschema){
			res= res+ m.getMetaProp() + " || metaClass :"+ m.getTypeMetaClass()+"\n";
		}
		
		metaProp.setText(res);
	}
	
	/* --------------------------------Transformation Phase ---------------------------------------------*/
	 @FXML
	    void OverlapDetection() {
		
		TransformationPhase.detectMetaOverlaping();
		DSA.calculatePercentage(); // Calculer le pourcentage de chaque type de métadonnées
		 //comboDupInstances.setValue("Choose an instance");
		 //comboDupInstances.setItems(overlapMetaCombo);
		//overlapMetaCombo.addAll(DSA.fillOverlapedMetadata(DSA.DupInstancesList.get(j)));
		}
	 @FXML
	    void showDataMeta(){
		 comboMetaOver.setItems(DSA.fillOverlappedMetadata(comboDupInstances.getValue().toString()));
		 comboDupInstances.setValue(comboDupInstances.getValue().toString());
		 instData.setText(DSA.showData(comboDupInstances.getValue().toString()));
		 instMeta.setText(DSA.showMeta(comboDupInstances.getValue().toString()));
	 }
	 
	 @FXML
	    void loadDSA(){
		 dsa.clear();
		dsa.setText(DSA.displayDuplications());
	 }
	 
	 @FXML
	    void loadSDW(){
		 SDW.setText(DSA.displayDuplications());
	 }
	 
	 @FXML
	    void manageMetaOverlapping(){
		BackMessage.setVisible(true);
 		Message.setVisible(true);
 		InstanceValue.setText(comboDupInstances.getValue().toString());
 		metaPropertyToCheck.setItems(comboMetaOver.getItems());
	 }
	 
	 @FXML
	    void setMetaValuesList(){
		 metaPropertyToCheck.setValue(metaPropertyToCheck.getValue());
		 metaValueToKeep.setItems(DSA.fillOverlappedMetaValues(comboDupInstances.getValue().toString(), metaPropertyToCheck.getValue().toString()));
			 //comboList = TransformationPhase.getMetaPropertyValues(InstanceValue.setText(comboDupInstances.getValue().toString()),metaProperty);
			 //metaValueToKeep.setItems(overlapMetaCombo);
		 
	 }
	 
	 @FXML
	    void KeepSelectedMetaValue(){
		 //Keep the selected value of the metadata property
		 DSA.addSelectedMetaValue(comboDupInstances.getValue().toString(),metaPropertyToCheck.getValue().toString(), metaValueToKeep.getValue().toString());
		 
	 }
	 
	 @FXML
	    void keepAllMetaValues(){
		 //Keep all values of the metadata property
		 BackMessage.setVisible(false);
	 	Message.setVisible(false);
		 selectedMetaValues.setText(DSA.showSelectedMetaValues(comboDupInstances.getValue().toString(),comboMetaOver.getValue().toString()));	
		 allMetaValues.setText(DSA.showAllMetaValues(comboDupInstances.getValue().toString(),comboMetaOver.getValue().toString()));	
	 }
	 
	 @FXML
	    void Quit(){
		 BackMessage.setVisible(false);
	 	Message.setVisible(false);
	 	 selectedMetaValues.setText(DSA.showSelectedMetaValues(comboDupInstances.getValue().toString(),comboMetaOver.getValue().toString()));	
		 allMetaValues.setText(DSA.showAllMetaValues(comboDupInstances.getValue().toString(),comboMetaOver.getValue().toString()));	
		 //Save changes
		 //Display final result 
	 }
	 

	 
	 // -------------------- Phase d'extraction ----------------------------------------//
	 @FXML
	    void ExtractExternalInstances(){
		 String val =comboMap.getValue().toString();
	    	
	    	// La liste suivante va contenir 
	    	ArrayList<String> mapDetails =new ArrayList<String>(); 
	    	
	    	if (!(val.equals("Choose a mapping")))
	    	{
	    		for (ETLOperations m:Mappings.getMappings()) {
	    			if (m.ETLName.equals(val)){
	    				ExtInstances.setText(ExtractionPhase.extractExternalInstances(m));
	    				
	    			}
	    		}
	    	}
	 	}
	 
	 
	 @FXML
	    void ExtractInternalInstances(){
		 String val =comboMap.getValue().toString();
	    	
	    	// La liste suivante va contenir 
	    	ArrayList<String> mapDetails =new ArrayList<String>(); 
	    	
	    	if (!(val.equals("Choose a mapping")))
	    	{
	    		for (ETLOperations m:Mappings.getMappings()) {
	    			if (m.ETLName.equals(val)){
	    				IntInstances.setText(ExtractionPhase.extractInternalInstances(m));
	    				
	    			}
	    		}
	    	}
	 	}
	 
	 // Unification of internal and external instances 
	 @FXML
	    void instancesUnification(){
		 TransformationPhase.instancesUnification();
		 
	 }
	 
	 @FXML
	    void detectDuplication(){
		 //UnionInstances.setText(TransformationPhase.detectDuplication());
		 DSA.sortInstanceList.addAll(TransformationPhase.sortListInstances(DSA.InstanceList));
		 TransformationPhase.detectDuplication();
		 comboDupInstances.setValue("Choose an instance");
		 comboDupInstances.setItems(DSA.fillDuplicatedInstances());
		 DSA.calculatePercentage(); // Calculer le pourcentage de chaque type de métadonnées
	 }

	 @FXML
	    void ViewDSA(){
		 //UnionInstances.setText(DSA.display());
		 UnionInstances.clear();
		 UnionInstances.setText(DSA.displayDuplications());
	 }
	 
	 @FXML
	  public void OK(){
		 BackMessage.setVisible(false);
   		Message.setVisible(false);
	 }
    
	 @FXML 
	 public void LoadMappings(){
		 
		 comboMap.setValue("Choose a mapping");
	     comboMap.setItems(ETLOperations.initializeMappings());
	     
	 }
    
	 @FXML 
	 public void consultMapDetails(){
		    String val =comboMap.getValue().toString();
	    	String intResult="";
	    	String extResult="";
	    	String formResult="";
	    	InternalClasses.clear();
	    	ExternalClasses.clear();
	    	Formats.clear();
	    	
	    	// La liste suivante va contenir 
	    	ArrayList<String> mapDetails =new ArrayList<String>(); 
	    	
	    	if (!(val.equals("Choose a mapping")))
	    	{
	    		for (ETLOperations m:Mappings.getMappings()) {
	    			if (m.ETLName.equals(val)){
			    		m.setSourcesDetails();
			    		System.out.println("Sources extracted :"+ m.Target+"\n");
			    		TargetClass.setText(m.Target);
			    		for (String Int:m.getInternalClasses()){
			    			intResult = intResult +"\n"+ Int;
			    		}
			    		for (String Ext:m.getExternalClasses()){
			    			extResult = extResult +"\n"+ Ext;
			    		}
			    		for (String Form:m.getFormats()){
			    			formResult = formResult +"\n"+ Form;
			    		}
			    		System.out.println("Internal  :"+ intResult+"\n");
			    		System.out.println("External :"+ extResult+"\n");
			    		System.out.println("Formats :"+ formResult+"\n");
			    		InternalClasses.setText(intResult);
			    		ExternalClasses.setText(extResult);
			    		Formats.setText(formResult);
	    			}
	    		}
	    	}
	 	}
}
