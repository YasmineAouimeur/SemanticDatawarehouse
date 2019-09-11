package dz.esi.outil_onto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dz.esi.outil_onto.model.RequirementManger.KPI;
import dz.esi.outil_onto.model.RequirementManger.Requirements;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

public class statIndicatorMappings implements Initializable {

	public statIndicatorMappings() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loadData();
	}
	
	@FXML
    private Pane paneView;
	
	// Requirement list
	ArrayList<Requirements> requirements = RequirementCont.requirements;
	
	// Methodes 
	@FXML
	private void loadData(){
		 paneView.getChildren().clear();
		 
		//Defining the axes              
	      CategoryAxis xAxis = new CategoryAxis();  
	      //xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Unique KPI value (No conflict)", "Another KPI value (Conflict)")));
	      xAxis.setLabel("requirements");
	       
	      NumberAxis yAxis = new NumberAxis();
	      yAxis.setLabel("Number of nodes concerned and mappings involved ");
	      
	    //Creating the Bar chart
	      BarChart<String, Number> mappingsChart = new BarChart<String, Number>(xAxis, yAxis); 
	      mappingsChart.setTitle("Impact of incorporating requirements: nodes concerned, mappings involved");
	      mappingsChart.setMinSize(1000, 400);
	      mappingsChart.setMaxSize(1000, 400);
	    //Prepare XYChart.Series objects by setting data       
	        mappingsChart.getData().clear();
			XYChart.Series series = new XYChart.Series();
			XYChart.Series series1 = new XYChart.Series();
	      
	      for(int i = 0; i < requirements.size(); i++)
	        {
	    	  	series.setName("Mappings involved");
	    	  	series.getData().add(new XYChart.Data(requirements.get(i).requirementName,requirements.get(i).getNbMappings()));
	    	  	series1.setName("Nodes concerned");
				series1.getData().add(new XYChart.Data(requirements.get(i).requirementName,requirements.get(i).getNbNodes()));
	        }
			mappingsChart.getData().addAll(series,series1);
	      
	      paneView.getChildren().add(mappingsChart);
	}

	
}
