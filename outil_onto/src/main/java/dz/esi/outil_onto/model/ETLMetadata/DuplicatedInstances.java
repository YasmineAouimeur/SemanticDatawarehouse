package dz.esi.outil_onto.model.ETLMetadata;

import java.util.ArrayList;

public class DuplicatedInstances {
	String InstName;
	public ArrayList<Instance> DuplicatedInstList = new ArrayList<Instance>();
	public ArrayList<Data> DataList = new ArrayList<Data>();
	public ArrayList<Metadata> MetadataList = new ArrayList<Metadata>();
	
	// List of sorted metadata + list of overlaped metadata 
	public ArrayList<Metadata> sortMetadataList = new ArrayList<Metadata>();
	public ArrayList<OverlapedMetadata> OverlapedMetadataList = new ArrayList<OverlapedMetadata>();
	
	
	public String getInstName() {
		return InstName;
	}
	
	public void setInstName(String instName) {
		InstName = instName;
	}
	public ArrayList<Instance> getDuplicatedInstList() {
		return DuplicatedInstList;
	}
	public void setDuplicatedInstList(ArrayList<Instance> duplicatedInstList) {
		DuplicatedInstList = duplicatedInstList;
	}
	public ArrayList<Data> getDataList() {
		return DataList;
	}
	public void setDataList(ArrayList<Data> dataList) {
		DataList = dataList;
	}
	public ArrayList<Metadata> getMetadataList() {
		return MetadataList;
	}
	public void setMetadataList(ArrayList<Metadata> metadataList) {
		MetadataList = metadataList;
	}
	
	public String display(){
		 String result="";
		 
	    	for ( int p = 0; p < DuplicatedInstList.size(); p++) {
	    		result = result +"============================================================\n";
				
				result = result +"**-** Instance : "+ DuplicatedInstList.get(p).getLocalName() + "\n";
				System.out.println("**-** Instance "+DuplicatedInstList.size()+" : "+ DuplicatedInstList.get(p).getLocalName() + "\n");
				result = result +"============================================================\n";
				//System.out.println("**-** Metadata :\n\n");
				result = result +"**-** Metadata : "+DuplicatedInstList.get(p).MetadataList.size()+"\n\n";
		    	for( int i = 0; i < DuplicatedInstList.get(p).MetadataList.size(); i++)
		        {
		    		result = result +"- Metadata : "  + DuplicatedInstList.get(p).MetadataList.get(i).getMetadata() +"\n";
		    		result = result +"- MetaProperty : " + DuplicatedInstList.get(p).MetadataList.get(i).getMetaProperty()+"\n";
		    		result = result +"- MetaValue : " + DuplicatedInstList.get(p).MetadataList.get(i).getMetaValue()+"\n\n";
		    		//System.out.println("- Metadata : "  + InstanceList.get(p).MetadataList.get(i).getMetadata() +"\n" + "- MetaProperty : " + InstanceList.get(p).MetadataList.get(i).getMetaProperty()+"\n"+ "- MetaValue : " + InstanceList.get(p).MetadataList.get(i).getMetaValue()+"\n\n");
		        }
		    	result = result +"============================================================\n";
				result = result +"**-** Data :\n\n";
				//System.out.println("**-** Data"+InstanceList.get(p).DataList.size()+" :\n\n");
				for(int j = 0; j < DuplicatedInstList.get(p).DataList.size(); j++)
		        {
		    		result = result +"- Data : "  + DuplicatedInstList.get(p).DataList.get(j).getData() +"\n";
		    		result = result +"- DataProperty : " + DuplicatedInstList.get(p).DataList.get(j).getProperty()+"\n";
		    		result = result +"- DataValue : " + DuplicatedInstList.get(p).DataList.get(j).getValue()+"\n\n";
		    		//System.out.println("- Data : "  + InstanceList.get(p).DataList.get(j).getData() +"\n"+"- DataProperty : " + InstanceList.get(p).DataList.get(j).getProperty()+"\n"+"- DataValue : " + InstanceList.get(p).DataList.get(j).getValue()+"\n\n");
		        }
			 }
	    	System.out.println("**-** Data"+result+" :\n\n");
			return result;
		 
		 
	 }
	
}
