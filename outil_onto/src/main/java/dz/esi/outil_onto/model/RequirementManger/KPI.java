package dz.esi.outil_onto.model.RequirementManger;

import java.util.ArrayList;

import dz.esi.outil_onto.controller.MappingCont;

public class KPI {
	
	public String Name="";
	public ArrayList<Integer> levelPerformance =new ArrayList<Integer>();
	public boolean conflict;
	
	
	public KPI() {
		// TODO Auto-generated constructor stub
	}
	
	public KPI(String Name) {
		// TODO Auto-generated constructor stub
		this.Name = Name;
	}
	
	public KPI(String Name, ArrayList<Integer> List) {
		// TODO Auto-generated constructor stub
		this.Name = Name;
		for(int i = 0; i < List.size(); i++)
        {
    		this.levelPerformance.add(List.get(i));
        }
	}
	
	public KPI(String Name, ArrayList<Integer> List, boolean conflict) {
		// TODO Auto-generated constructor stub
		this.Name = Name;
		for(int i = 0; i < List.size(); i++)
        {
    		this.levelPerformance.add(List.get(i));
        }
		this.conflict= conflict;
	}
	
	public void setName (String Name){
		this.Name= Name;
	}
	
	public void setLevelPerf(ArrayList<Integer> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		this.levelPerformance.add(List.get(i));
        }
	}
	
	public void setName (boolean conf){
		this.conflict= conf;
	}
	
	public String getName (){
		return this.Name;
	}
	
	public ArrayList<Integer> getLevelePer (){
		return levelPerformance;
	}
	
	public boolean setConf (){
		return this.conflict;
	}
}
