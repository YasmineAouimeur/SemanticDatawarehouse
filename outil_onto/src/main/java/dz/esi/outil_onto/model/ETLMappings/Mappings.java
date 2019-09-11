package dz.esi.outil_onto.model.ETLMappings;

import java.util.ArrayList;

public class Mappings {
	
	public static ArrayList<ETLOperations> mappings = new ArrayList<ETLOperations>();

	public Mappings() {
		// TODO Auto-generated constructor stub
	}
	
	public static void setMappings(ArrayList<ETLOperations> List) {
		for(int i = 0; i < List.size(); i++)
        {
    		mappings.add(List.get(i));
        }
	}
	
	public static ArrayList<ETLOperations> getMappings(){
		return mappings;
	}
	

}
