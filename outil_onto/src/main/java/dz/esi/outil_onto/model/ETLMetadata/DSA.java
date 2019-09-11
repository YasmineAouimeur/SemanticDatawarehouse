package dz.esi.outil_onto.model.ETLMetadata;

import java.util.ArrayList;
import java.util.HashSet;

import dz.esi.outil_onto.model.ETLMappings.ETLOperations;
import dz.esi.outil_onto.model.ETLMappings.Mappings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DSA {
	public static ArrayList<Instance> InstanceList = new ArrayList<Instance>();
	public static ArrayList<Instance> ExternalInstanceList = new ArrayList<Instance>();
	public static ArrayList<Instance> InternalInstanceList = new ArrayList<Instance>();
	
	//Transformation
	public static ArrayList<Instance> sortInstanceList = new ArrayList<Instance>();
	public static ArrayList<DuplicatedInstances> DupInstancesList = new ArrayList<DuplicatedInstances>();
	
	//Load
	public static ArrayList<String> metaProperties = new ArrayList<String>();
	public static ArrayList<MetaSchema> metaschema = new ArrayList<MetaSchema>();

	
	//Metrics 
	static double totalInstanceNb=0;
	static double totalExtInstanceNb=0;
	static double totalIntInstanceNb=0;

	static double totalMetaNb=0;
	static double totalDataNb=0;
	
	static double externalMetaNb=0;
	static double internalMetaNb=0;
	
	static double externalDataNb=0;
	static double internalDataNb=0;
	
	static double dateMetaNb=0;
	static double provMetaNb=0;
	static double statMetaNb=0;
	static double versionMetaNb=0;
	static double descriptMetaNb=0;
	
	static  double dateMetaPercent;
	static  double provMetaPercent;
	static  double statMetaPercent;
	static  double descriptMetaPercent;
	static  double versionMetaPercent;
	
	// Transformation
	static double totalInstDuplication=0;
	static double totalMetaOverlap=0;
	static double totalMetaContradiction=0;
	
	static  double instDupliPercent=0;
	static  double metaOverlapPercent=0;
	static  double metaContradictionPercent=0;
	
	
	public static double getTotalInstanceNb() {
		return totalInstanceNb;
	}

	public static void setTotalInstanceNb() {
		DSA.totalInstanceNb = InstanceList.size();
	}

	public static double getTotalExtInstanceNb() {
		return totalExtInstanceNb;
	}

	public static void setTotalExtInstanceNb(double totalExtInstanceNb) {
		DSA.totalExtInstanceNb = ExternalInstanceList.size();
	}

	public static double getTotalIntInstanceNb() {
		return totalIntInstanceNb;
	}

	public static void setTotalIntInstanceNb(double totalIntInstanceNb) {
		DSA.totalIntInstanceNb = InternalInstanceList.size();
	}
	
	public static double getTotalMetaNb() {
		return totalMetaNb;
	}

	
	public static double getTotalDataNb() {
		return totalDataNb;
	}

	public static void setTotalDataNb(double totalDataNb) {
		DSA.totalDataNb = totalDataNb;
	}

	public static double getExternalMetaNb() {
		return externalMetaNb;
	}

	public static void setExternalMetaNb(double externalMetaNb) {
		DSA.externalMetaNb = externalMetaNb;
	}

	public static double getInternalMetaNb() {
		return internalMetaNb;
	}

	public static void setInternalMetaNb(double internalMetaNb) {
		DSA.internalMetaNb = internalMetaNb;
	}

	public static double getDateMetaNb() {
		return dateMetaNb;
	}

	public static void setDateMetaNb(double dateMetaNb) {
		DSA.dateMetaNb = dateMetaNb;
	}

	public static double getProvMetaNb() {
		return provMetaNb;
	}

	public static void setProvMetaNb(double provMetaNb) {
		DSA.provMetaNb = provMetaNb;
	}

	public static double getStatMetaNb() {
		return statMetaNb;
	}

	public static void setStatMetaNb(double statMetaNb) {
		DSA.statMetaNb = statMetaNb;
	}

	public static double getVersionMetaNb() {
		return versionMetaNb;
	}

	public static void setVersionMetaNb(int versionMetaNb) {
		DSA.versionMetaNb = versionMetaNb;
	}

	public static double getDescriptMetaNb() {
		return descriptMetaNb;
	}

	public static void setDescriptMetaNb(double descriptMetaNb) {
		DSA.descriptMetaNb = descriptMetaNb;
	}

	public ArrayList<Instance> getInstanceList() {
		return InstanceList;
	}

	public static ArrayList<Instance> getExternalInstanceList() {
		return ExternalInstanceList;
	}

	public static void setExternalInstanceList(ArrayList<Instance> externalInstanceList) {
		ExternalInstanceList = externalInstanceList;
	}

	public static ArrayList<Instance> getInternalInstanceList() {
		return InternalInstanceList;
	}

	public static void setInternalInstanceList(ArrayList<Instance> internalInstanceList) {
		InternalInstanceList = internalInstanceList;
	}

	public void setInstanceList(ArrayList<Instance> instanceList) {
		InstanceList = instanceList;
	}
	
	public static void incrementingMetadata(){
		totalMetaNb++;
	}
	
	public static void incrementingData(){
		totalDataNb++;
	}
	
	public static void incrementingExtMetadata(){
		externalMetaNb++;
	}
	
	public static void incrementingExtData(){
		externalDataNb++;
	}
	
	public static void incrementingIntMetadata(){
		internalMetaNb++;
	}
	
	public static void incrementingIntData(){
		internalDataNb++;
	}
	
	public static void incrementingMetaTypes(MetadataType T){
		if (T == MetadataType.Date){
			dateMetaNb ++;
		}
		if (T == MetadataType.Description){
			descriptMetaNb++;
		}
		if (T == MetadataType.Provenance){
			provMetaNb++;
		}
		if (T == MetadataType.Statistical){
			statMetaNb++;
		}
		if (T == MetadataType.Version){
			versionMetaNb++;
		}
	}
	
	public static void calculatePercentage(){
		double date=dateMetaNb;
		double prov=provMetaNb;
		double descript=descriptMetaNb;
		double stat=statMetaNb;
		double version=versionMetaNb;
		double tot=totalMetaNb;
		
		double dupInst = totalInstDuplication;
		
		dateMetaPercent = (date/tot)*100;
		provMetaPercent = (prov/tot)*100;
		statMetaPercent = (stat/tot)*100;
		descriptMetaPercent = (descript/tot)*100;
		versionMetaPercent = (version/tot)*100;
		instDupliPercent = (dupInst/DSA.InstanceList.size())*100;
		metaOverlapPercent = (DSA.totalMetaOverlap/DSA.totalMetaNb)*100;
		metaContradictionPercent = (totalMetaContradiction/DSA.totalMetaNb)*100;
		
		
		System.out.println("\n Nb Total extracted Instances :"+DSA.InstanceList.size() );
		System.out.println("Nb Total extracted Internal Instances :"+DSA.InternalInstanceList.size());
		System.out.println("Nb Total extracted External Instances :"+DSA.ExternalInstanceList.size() );
		System.out.println("Nb Total Instances after detect duplications :"+DSA.DupInstancesList.size() );
		System.out.println("\n Nb Total Metadata :"+DSA.totalMetaNb );
		System.out.println("Nb Total data :"+DSA.totalDataNb );
		System.out.println("\n Nb Total Metadata internes :"+DSA.internalMetaNb );
		System.out.println("Nb Total data internes :"+DSA.internalDataNb );
		System.out.println("\n Nb Total Metadata externes :"+DSA.externalMetaNb );
		System.out.println("Nb Total data externes :"+DSA.externalDataNb );
		System.out.println("\n Nb date :"+DSA.dateMetaNb );
		System.out.println("Nb prov :"+DSA.provMetaNb);
		System.out.println("Nb descr :"+DSA.descriptMetaNb);
		System.out.println("Nb stats :"+DSA.statMetaNb);
		System.out.println("Nb version :"+DSA.versionMetaNb);
		
		System.out.println("\nPercentage date :"+DSA.dateMetaPercent);
		System.out.println("Percentage provenance :"+DSA.provMetaPercent);
		System.out.println("Percentage description :"+DSA.descriptMetaPercent);
		System.out.println("Percentage statistiques :"+DSA.statMetaPercent);
		System.out.println("Percentage version :"+DSA.versionMetaPercent);
		
		System.out.println("\n Nb duplicated Instances :"+DSA.totalInstDuplication );
		System.out.println("Percentage duplicated Instances :"+DSA.instDupliPercent);
		
		System.out.println("\n Nb overlaped Meta :"+DSA.totalMetaOverlap );
		System.out.println("Percentage overlaped meta :"+DSA.metaOverlapPercent);
		
		System.out.println("\n Nb contradiction meta :"+DSA.totalMetaContradiction);
		System.out.println("Percentage contradiction meta :"+DSA.metaContradictionPercent);
		
		System.out.println("\nextraction exec time :"+ExtractionPhase.executionTime);
		System.out.println("transformation exec time :"+TransformationPhase.executionTime);
		System.out.println("total exec time :"+ExtractionPhase.executionTime+TransformationPhase.executionTime);
		
		System.out.println("\nextraction memory consomption :"+ExtractionPhase.memoryConsumed);
		System.out.println("transformation memory consomption :"+TransformationPhase.memoryConsumed);
		System.out.println("total memory consomption :"+ExtractionPhase.memoryConsumed+TransformationPhase.memoryConsumed);
		
		
	}
	
	
	/*------------------------ Load DSA operations -------------------------------------------------*/
	
	public static void addToMetaPropertyList(String metaProperty){
		final HashSet set = new HashSet();
		
		if(set.add(metaProperty)){
			DSA.metaProperties.add(metaProperty);
		}
	}
	
	public static String showMetaPropertyList(){
		String res="";
		
		for(String metaProp:DSA.metaProperties){
			res = res + metaProp + "\n";
		}
		return res;
	}

	/*------------------------ Transformation DSA operations -------------------------------------------------*/
	
	public static void addSelectedMetaValue(String dupInst,String overlapMeta,String metaValue){
		
		for(int i=0; i< DSA.DupInstancesList.size();i++){
			if (DSA.DupInstancesList.get(i).InstName== dupInst){
				for(int j=0; j< DSA.DupInstancesList.get(i).OverlapedMetadataList.size();j++){
					if (DSA.DupInstancesList.get(i).OverlapedMetadataList.get(j).MetaName== overlapMeta){
						DSA.DupInstancesList.get(i).OverlapedMetadataList.get(j).mataValuesToKeep.add(metaValue);
						DSA.DupInstancesList.get(i).OverlapedMetadataList.get(j).selectedValues=true;
						j=DSA.DupInstancesList.get(i).OverlapedMetadataList.size()-1;
						i=DSA.DupInstancesList.size()-1;
					}
				}
			}
			
		}
	}
	
	public static ObservableList<String> fillOverlappedMetaValues(String dupInst,String overlapMeta){
		ObservableList<String> overlapedMetaValueList = FXCollections.observableArrayList();
		
		for(int i=0; i< DSA.DupInstancesList.size();i++){
			if (DSA.DupInstancesList.get(i).InstName== dupInst){
				for(int j=0; j< DSA.DupInstancesList.get(i).OverlapedMetadataList.size();j++){
					if (DSA.DupInstancesList.get(i).OverlapedMetadataList.get(j).MetaName== overlapMeta){
						for(String metaValue: DSA.DupInstancesList.get(i).OverlapedMetadataList.get(j).OverlapedValues){
							overlapedMetaValueList.add(metaValue);		
							System.out.println("Meta overlap :"+ metaValue);
						}
						j=DSA.DupInstancesList.get(i).OverlapedMetadataList.size()-1;
						i=DSA.DupInstancesList.size()-1;
					}
				}
			}
			
		}
		
		return overlapedMetaValueList;
	}
	
	public static void countContradiction(ArrayList<String> List){
		final HashSet set = new HashSet();
		
		for(String metaValue: List){
			if(set.add(metaValue)){
				DSA.totalMetaContradiction++;
			}
		}
	}	
	

	
	
	
	public static ObservableList<String> fillOverlappedMetadata(String dupInst){
		ObservableList<String> overlapedMetaList = FXCollections.observableArrayList();
		
		for(int i=0; i< DSA.DupInstancesList.size();i++){
			if (DSA.DupInstancesList.get(i).InstName== dupInst){
				System.out.println("L'instance :"+ DSA.DupInstancesList.get(i).InstName);
				for(OverlapedMetadata metaOverl: DSA.DupInstancesList.get(i).OverlapedMetadataList){
					overlapedMetaList.add(metaOverl.MetaName);		
					System.out.println("Meta overlap :"+ metaOverl.MetaName);
				}
				i=DSA.DupInstancesList.size()-1;
			}
			
		}
		
		
		return overlapedMetaList;
	}
	
	public static ObservableList<String> fillDuplicatedInstances(){
		ObservableList<String> dupInstancesList = FXCollections.observableArrayList();
		
		for(DuplicatedInstances dup: DSA.DupInstancesList){
			dupInstancesList.add(dup.getInstName());		}
		
		return dupInstancesList;
	}
	
	
	
	public static String showData(String Inst){
		String res = "";
		DuplicatedInstances dup;
		
		for(int i=0; i< DSA.DupInstancesList.size();i++){
			if(DSA.DupInstancesList.get(i).InstName== Inst){
				for(Data data:DSA.DupInstancesList.get(i).DataList){
					res = res + data.DataProperty + " : " + data.DataValue +"\n";
				}
				i = DSA.DupInstancesList.size()-1;
			}
		}
		return res;
	}
	
	public static String showMeta(String Inst){
		String res = "";
		DuplicatedInstances dup;
		
		for(int i=0; i< DSA.DupInstancesList.size();i++){
			if(DSA.DupInstancesList.get(i).InstName== Inst){
				for(Metadata data:DupInstancesList.get(i).sortMetadataList){
					res = res + data.MetaProperty + " : " + data.MetaValue +"\n";
				}
				i = DSA.DupInstancesList.size();
			}
		}
		return res;
	}
	
	public static String showSelectedMetaValues(String Inst,String MetaProp){
		String res = "";
		DuplicatedInstances dup;
		
		for(int i=0; i< DSA.DupInstancesList.size();i++){
			if(DSA.DupInstancesList.get(i).InstName== Inst){
				for(int j=0; j<DSA.DupInstancesList.get(i).OverlapedMetadataList.size();j++){
					if(DSA.DupInstancesList.get(i).OverlapedMetadataList.get(j).MetaName==MetaProp){
						for(String selectedValues:DSA.DupInstancesList.get(i).OverlapedMetadataList.get(j).mataValuesToKeep){
							res = res + selectedValues +"\n";
						}
						i = DSA.DupInstancesList.size()-1;
						j=DSA.DupInstancesList.get(i).OverlapedMetadataList.size()-1;
					}
					
				}
			}
		}
		return res;
	}
	
	public static String showAllMetaValues(String Inst,String MetaProp){
		String res = "";
		DuplicatedInstances dup;
		
		for(int i=0; i< DSA.DupInstancesList.size();i++){
			if(DSA.DupInstancesList.get(i).InstName== Inst){
				for(int j=0; j<DSA.DupInstancesList.get(i).OverlapedMetadataList.size();j++){
					if(DSA.DupInstancesList.get(i).OverlapedMetadataList.get(j).MetaName==MetaProp){
						for(String selectedValues:DSA.DupInstancesList.get(i).OverlapedMetadataList.get(j).OverlapedValues){
							res = res + selectedValues +"\n";
						}
						i = DSA.DupInstancesList.size()-1;
						j=DSA.DupInstancesList.get(i).OverlapedMetadataList.size()-1;
					}
					
				}
			}
		}
		return res;
	}
	
	 public static String display(){
		 String result="";
		 
	    	for ( int p = 0; p < InstanceList.size(); p++) {
	    		result = result +"============================================================\n";
				
				result = result +"**-** Instance : "+ InstanceList.get(p).getLocalName() + "\n";
				System.out.println("**-** Instance "+InstanceList.size()+" : "+ InstanceList.get(p).getLocalName() + "\n");
				result = result +"============================================================\n";
				//System.out.println("**-** Metadata :\n\n");
				result = result +"**-** Metadata : "+InstanceList.get(p).MetadataList.size()+"\n\n";
		    	for( int i = 0; i < InstanceList.get(p).MetadataList.size(); i++)
		        {
		    		result = result +"- Metadata : "  + InstanceList.get(p).MetadataList.get(i).getMetadata() +"\n";
		    		result = result +"- MetaProperty : " + InstanceList.get(p).MetadataList.get(i).getMetaProperty()+"\n";
		    		result = result +"- MetaValue : " + InstanceList.get(p).MetadataList.get(i).getMetaValue()+"\n\n";
		    		//System.out.println("- Metadata : "  + InstanceList.get(p).MetadataList.get(i).getMetadata() +"\n" + "- MetaProperty : " + InstanceList.get(p).MetadataList.get(i).getMetaProperty()+"\n"+ "- MetaValue : " + InstanceList.get(p).MetadataList.get(i).getMetaValue()+"\n\n");
		        }
		    	result = result +"============================================================\n";
				result = result +"**-** Data :\n\n";
				//System.out.println("**-** Data"+InstanceList.get(p).DataList.size()+" :\n\n");
				for(int j = 0; j < InstanceList.get(p).DataList.size(); j++)
		        {
		    		result = result +"- Data : "  + InstanceList.get(p).DataList.get(j).getData() +"\n";
		    		result = result +"- DataProperty : " + InstanceList.get(p).DataList.get(j).getProperty()+"\n";
		    		result = result +"- DataValue : " + InstanceList.get(p).DataList.get(j).getValue()+"\n\n";
		    		//System.out.println("- Data : "  + InstanceList.get(p).DataList.get(j).getData() +"\n"+"- DataProperty : " + InstanceList.get(p).DataList.get(j).getProperty()+"\n"+"- DataValue : " + InstanceList.get(p).DataList.get(j).getValue()+"\n\n");
		        }
			 }
	    	System.out.println("**-** Data"+result+" :\n\n");
			return result;
		 
		 
	 }
	
	public static String displayDuplications(){
		
		String result="";
		
		for ( int p = 0; p < DupInstancesList.size(); p++) {
    		result = result +"============================================================\n";
			
			result = result +"**-** Instance : "+ DupInstancesList.get(p).InstName + "\n";
			System.out.println("**-** Instance "+DupInstancesList.size()+" : "+ DupInstancesList.get(p).InstName + "\n");
			result = result +"============================================================\n";
			//System.out.println("**-** Metadata :\n\n");
			result = result +"**-** Metadata : "+DupInstancesList.get(p).MetadataList.size()+"\n\n";
	    	for( int i = 0; i < DupInstancesList.get(p).MetadataList.size(); i++)
	        {
	    		result = result +"- Metadata : "  + DupInstancesList.get(p).MetadataList.get(i).getMetadata() +"\n";
	    		result = result +"- MetaProperty : " + DupInstancesList.get(p).MetadataList.get(i).getMetaProperty()+"\n";
	    		result = result +"- MetaValue : " + DupInstancesList.get(p).MetadataList.get(i).getMetaValue()+"\n\n";
	    		//System.out.println("- Metadata : "  + InstanceList.get(p).MetadataList.get(i).getMetadata() +"\n" + "- MetaProperty : " + InstanceList.get(p).MetadataList.get(i).getMetaProperty()+"\n"+ "- MetaValue : " + InstanceList.get(p).MetadataList.get(i).getMetaValue()+"\n\n");
	        }
	    	result = result +"============================================================\n";
			result = result +"**-** Data :\n\n";
			//System.out.println("**-** Data"+InstanceList.get(p).DataList.size()+" :\n\n");
			for(int j = 0; j < DupInstancesList.get(p).DataList.size(); j++)
	        {
	    		result = result +"- Data : "  + DupInstancesList.get(p).DataList.get(j).getData() +"\n";
	    		result = result +"- DataProperty : " + DupInstancesList.get(p).DataList.get(j).getProperty()+"\n";
	    		result = result +"- DataValue : " + DupInstancesList.get(p).DataList.get(j).getValue()+"\n\n";
	    		//System.out.println("- Data : "  + InstanceList.get(p).DataList.get(j).getData() +"\n"+"- DataProperty : " + InstanceList.get(p).DataList.get(j).getProperty()+"\n"+"- DataValue : " + InstanceList.get(p).DataList.get(j).getValue()+"\n\n");
	        }
		 }
    	//System.out.println("**-** Data"+result+" :\n\n");
		return result;
	}
}
