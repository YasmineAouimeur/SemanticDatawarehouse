package dz.esi.outil_onto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import dz.esi.outil_onto.model.RequirementManger.KPI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class statIndicatorKpi implements Initializable {

	public statIndicatorKpi() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loadData();
	}

	// déclaration des élements de l'interface 
	@FXML
    private Pane paneView;
	
	// Liste des KPI 
	ArrayList<KPI> KPIs = RequirementCont.KPIs;
	
	// Methodes 
	@FXML
	private void loadData(){
		 paneView.getChildren().clear();
		 
		//Defining the axes              
	      CategoryAxis xAxis = new CategoryAxis();  
	      //xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Unique KPI value (No conflict)", "Another KPI value (Conflict)")));
	      xAxis.setLabel("KPIs");
	       
	      NumberAxis yAxis = new NumberAxis();
	      yAxis.setLabel("Performance KPI value");
	      
	    //Creating the Bar chart
	      BarChart<String, Number> kpiChart = new BarChart<String, Number>(xAxis, yAxis); 
	      kpiChart.setTitle("KPI performance values and conflict detection");
	      
	      kpiChart.setMinSize(1000, 300);
	      kpiChart.setMaxSize(1000, 300);
	    //Prepare XYChart.Series objects by setting data       
	        kpiChart.getData().clear();
			XYChart.Series<String, Number> series = new XYChart.Series();
			XYChart.Series<String, Number> series1 = new XYChart.Series();
	      
	      for(int i = 0; i < KPIs.size(); i++)
	        {
					if(KPIs.get(i).levelPerformance.size() == 1){
	    	  			//XYChart.Series series = new XYChart.Series();
	    	  			series.setName("Non conflictual KPI value");
	    	  			Data gg = new XYChart.Data(KPIs.get(i).Name,KPIs.get(i).levelPerformance.get(0));
	    	  			//displayLabelForData(gg);
	    	  			series.getData().add(gg);
	    	  			
					} else {
						series.getData().add(new XYChart.Data(KPIs.get(i).Name,KPIs.get(i).levelPerformance.get(0)));
						series1.setName("Conflictual KPI value");
						series1.getData().add(new XYChart.Data(KPIs.get(i).Name,KPIs.get(i).levelPerformance.get(1)));
						
						
					
					}
	        }
			kpiChart.getData().addAll(series,series1);
	      
	      paneView.getChildren().add(kpiChart);
	      
	}
	
	/*
	private void displayLabelForData(XYChart.Data<String, Number> data) {
		  final Node node = data.getNode();
		  final Text dataText = new Text(data.getYValue() + "");
		  node.parentProperty().addListener(new ChangeListener<Parent>() {
		    @Override public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
		      Group parentGroup = (Group) parent;
		      parentGroup.getChildren().add(dataText);
		    }
		  });

		  node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
		    @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
		      dataText.setLayoutX(
		        Math.round(
		          bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
		        )
		      );
		      dataText.setLayoutY(
		        Math.round(
		          bounds.getMinY() - dataText.prefHeight(-1) * 0.5
		        )
		      );
		    }
		  });
		} */
	

}
