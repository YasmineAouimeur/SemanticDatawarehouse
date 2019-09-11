package dz.esi.outil_onto.model.ETLMetadata;

import java.util.ArrayList;

import dz.esi.outil_onto.model.ETLOperators.ETLOperation;

public class Instance {
	String Instance;
	String LocalName;
	public ArrayList<Data> DataList = new ArrayList<Data>();
	public ArrayList<Metadata> MetadataList = new ArrayList<Metadata>();
	boolean seenInstance = false; // this boolean help in the transformation phase to detect duplicated instances
	
	
	public String getInstance() {
		return Instance;
	}
	public void setInstance(String instance) {
		Instance = instance;
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
	public String getLocalName() {
		return LocalName;
	}
	public void setLocalName(String localName) {
		LocalName = localName;
	}
	
}
