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

public class statIndicatorExtInt implements Initializable{

	public statIndicatorExtInt() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loadData();
	}
	
	// d�claration des �lements de l'interface 
		@FXML
	    private Pane paneView;
		
		// Liste des besoins 
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
		      yAxis.setLabel("Number of External/Internal used sources");
		      
		    //Creating the Bar chart
		      BarChart<String, Number> ExtIntChart = new BarChart<String, Number>(xAxis, yAxis); 
		      ExtIntChart.setTitle("Distribution of external and internal sources according to requirements");
		      ExtIntChart.setMinSize(1000, 400);
		      ExtIntChart.setMaxSize(1000, 400);
		      
		    //Prepare XYChart.Series objects by setting data       
		      	ExtIntChart.getData().clear();
				XYChart.Series series = new XYChart.Series();
				XYChart.Series series1 = new XYChart.Series();
		      
		      for(int i = 0; i < requirements.size(); i++)
		        {
		    	    series.setName("External sources");
					series.getData().add(new XYChart.Data(requirements.get(i).requirementName,requirements.get(i).getNbExt()));
					series1.setName("Internal sources");
					series1.getData().add(new XYChart.Data(requirements.get(i).requirementName,requirements.get(i).getNbInt()));
		        }
				ExtIntChart.getData().addAll(series,series1);
		      
		      paneView.getChildren().add(ExtIntChart);
		}

}
