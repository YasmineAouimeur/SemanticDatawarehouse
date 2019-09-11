package dz.esi.outil_onto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dz.esi.outil_onto.model.RequirementManger.KPI;
import dz.esi.outil_onto.model.RequirementManger.Requirements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;

public class StatisticsCont implements Initializable {
	
	@FXML
    private Button butStat;
	@FXML
    private Button butKPI;
	@FXML
    private Button butIntExt;
	@FXML
    private Button butInst;
	@FXML
    private Button butRetrieve;
	
	// Charts declarations 
	@FXML
    private BarChart<?, ?> nbNoeudnbMap;
	@FXML
    private BarChart<?, ?> chartKPI;
	@FXML
    private BarChart<?, ?> chartIntExt;
	@FXML
    private BarChart<?, ?> chartInst;
	
	//Liste des besoins 
	ArrayList<Requirements> requirements = RequirementCont.requirements;
	ArrayList<KPI> KPIs = RequirementCont.KPIs;
	
	
	 
	public StatisticsCont() {
		// TODO Auto-generated constructor stub
	}	

	@FXML
    void butStatAct(ActionEvent event) {
		nbNoeudnbMap.getData().clear();
		XYChart.Series series = new XYChart.Series();
		XYChart.Series series1 = new XYChart.Series();
		for(int i = 0; i < requirements.size(); i++)
        {
			series.getData().add(new XYChart.Data(requirements.get(i).requirementName,requirements.get(i).getNbMappings()));
			series1.getData().add(new XYChart.Data(requirements.get(i).requirementName,requirements.get(i).getNbNodes()));
        }
		nbNoeudnbMap.getData().addAll(series,series1);
		
    }
	
	@FXML
    void butKPIAct(ActionEvent event) {
		chartKPI.getData().clear();
		XYChart.Series series = new XYChart.Series();
		XYChart.Series series1 = new XYChart.Series();
		for(int i = 0; i < KPIs.size(); i++)
        {
				if(KPIs.get(i).levelPerformance.size() == 1){
					series.getData().add(new XYChart.Data(KPIs.get(i).Name,KPIs.get(i).levelPerformance.get(0)));
				} else {
					series.getData().add(new XYChart.Data(KPIs.get(i).Name,KPIs.get(i).levelPerformance.get(0)));
					series1.getData().add(new XYChart.Data(KPIs.get(i).Name,KPIs.get(i).levelPerformance.get(1)));
				}
				
			
        }
		chartKPI.getData().addAll(series,series1);
		
	}
	
	@FXML
    void butIntExtAct(ActionEvent event) {
		
	}
	
	@FXML
    void butInstAct(ActionEvent event) {
		
	}




	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
	}

}
