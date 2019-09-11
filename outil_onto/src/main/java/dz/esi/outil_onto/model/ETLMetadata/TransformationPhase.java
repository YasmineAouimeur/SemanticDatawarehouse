package dz.esi.outil_onto.model.ETLMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import dz.esi.outil_onto.model.ETLOperators.ETLOperation;

public class TransformationPhase {
	
	//Metrics 
		static float executionTime;
		static long memoryConsumed;
		
		
		
		public static void instancesUnification(){
			ExtractionPhase.memoryConsumed = ExtractionPhase.memoryConsumedExt + ExtractionPhase.memoryConsumedInt;
			ExtractionPhase.executionTime = ExtractionPhase.executionTimeExt + ExtractionPhase.executionTimeInt; // représente le temps d'éxécution de la phase d'extraction des sources internes et externes
			DSA.InstanceList.addAll(DSA.ExternalInstanceList);
			DSA.InstanceList.addAll(DSA.InternalInstanceList);
			
		}
		
		public static ArrayList<Instance> sortListInstances(ArrayList<Instance> baseList){
			long startTime = System.currentTimeMillis();
			ArrayList<Instance> sortList = new ArrayList();
			sortList.addAll(baseList);
			
			Collections.sort(sortList, new Comparator<Instance>(){
				public int compare(Instance I1, Instance I2) {
					return I1.Instance.compareTo(I2.Instance);
				}
			});
			
			for (Instance inst:sortList){
				System.out.println("\n L'instance : "+inst.Instance);
			}
			
			//Get the java Runtima 
			Runtime runtime= Runtime.getRuntime();
			runtime.gc();
			memoryConsumed = runtime.totalMemory() - runtime.freeMemory(); 
			
			long stopTime = System.currentTimeMillis();
			executionTime = stopTime - startTime;
			System.out.println( " \n sort Time  : "+ executionTime);
			
			return sortList;
		}
		
		public static void detectDuplication(){
			int i=0;
			int j=0;
			String res="";
			int duplicates=0;
			
			DuplicatedInstances DupInstance = new DuplicatedInstances();
			
			long startTime = System.currentTimeMillis();
			for(i=0; i<DSA.sortInstanceList.size();i++){
				System.out.println("");
				DupInstance.DuplicatedInstList.add(DSA.sortInstanceList.get(i));
				for(Metadata met:DSA.sortInstanceList.get(i).MetadataList){
					DupInstance.MetadataList.add(met);
				}
				for(Data dat:DSA.sortInstanceList.get(i).DataList){
					DupInstance.DataList.add(dat);
				}
				duplicates ++;
				if(i+1<DSA.sortInstanceList.size()){
					if(DSA.sortInstanceList.get(i).Instance.equals(DSA.sortInstanceList.get(i+1).Instance) == false){
						DupInstance.InstName= DSA.sortInstanceList.get(i).Instance;
						DSA.DupInstancesList.add(DupInstance);
						DupInstance = new DuplicatedInstances();
						duplicates=0;
					}
					i++;
				}
				if(duplicates>1){
					DSA.totalInstDuplication ++;
				}
			}
				
			//Get the java Runtima 
			Runtime runtime= Runtime.getRuntime();
			runtime.gc();
			memoryConsumed = memoryConsumed + runtime.totalMemory() - runtime.freeMemory(); 
			
			long stopTime = System.currentTimeMillis();
			System.out.println( " \n Time detection : "+ (stopTime - startTime));
			executionTime = executionTime+ (stopTime - startTime);
			System.out.println( " \n Total Time : "+ executionTime);
			System.out.println( " \n Total Time : "+ executionTime);
		}
		
		public static ArrayList<Metadata> sortMetadataList(ArrayList<Metadata> baseList){
			long startTime = System.currentTimeMillis();
			ArrayList<Metadata> sortList = new ArrayList();
			sortList.addAll(baseList);
			
			Collections.sort(sortList, new Comparator<Metadata>(){
				public int compare(Metadata M1, Metadata M2) {
					return M1.Metadata.compareTo(M2.Metadata);
				}
			});
			
			
			//Get the java Runtima 
			Runtime runtime= Runtime.getRuntime();
			runtime.gc();
			memoryConsumed = memoryConsumed +runtime.totalMemory() - runtime.freeMemory(); 
			
			long stopTime = System.currentTimeMillis();
			executionTime = executionTime+  (stopTime - startTime);
			System.out.println( " \n sort Time  : "+ executionTime);
			
			return sortList;
		}
		
		public static void detectMetaOverlaping(){
			OverlapedMetadata overlapedMeta = new OverlapedMetadata();
			String res="";
			int overlaped=0;
			boolean finalElem = false;
			
			long startTime = System.currentTimeMillis();
			for(int j=0; j<DSA.DupInstancesList.size();j++){
				
				System.out.println("************* New dup instance :"+DSA.DupInstancesList.get(j).getInstName());
				
				DSA.DupInstancesList.get(j).sortMetadataList.addAll(TransformationPhase.sortMetadataList(DSA.DupInstancesList.get(j).MetadataList));
				
				for(int i=0; i<DSA.DupInstancesList.get(j).sortMetadataList.size();i++){
					//System.out.println("** Metadata "+overlaped+" :"+DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaProperty);
					
					if(i+1!=DSA.DupInstancesList.get(j).sortMetadataList.size()){
						//System.out.println(" i=0 : check next: i!=i+1 :"+DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaProperty+" ||||||| "+DSA.DupInstancesList.get(j).sortMetadataList.get(i+1).MetaProperty);

						if(DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaProperty.equals(DSA.DupInstancesList.get(j).sortMetadataList.get(i+1).MetaProperty) == false){
							if(i==0){
								overlaped=0;
							}else{
								//System.out.println(" i>0 check past : i!=i-1 and :"+DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaProperty+" ||||||| "+DSA.DupInstancesList.get(j).sortMetadataList.get(i-1).MetaProperty);

								if(DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaProperty.equals(DSA.DupInstancesList.get(j).sortMetadataList.get(i-1).MetaProperty) == false){
									overlaped=0;
									//System.out.println(" No overlap : i!= i+1 && i!= i-1");
								}
								else{
									//System.out.println(" overlap &&& final elem : : i!= i+1 && i= i-1");
									overlaped++;
									finalElem=true;
								}
							}
						}
						else{
						//System.out.println(" next elem is the same");
							overlaped++;
						}
					}else{
						if(i==0){
							overlaped=0;
						}else{
						//System.out.println("Dernière meta : i>0 check past : i!=i-1 and :"+DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaProperty+" ||||||| "+DSA.DupInstancesList.get(j).sortMetadataList.get(i-1).MetaProperty);

						if(DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaProperty.equals(DSA.DupInstancesList.get(j).sortMetadataList.get(i-1).MetaProperty) == false){
							overlaped=0;
							//System.out.println(" Dernière meta : No overlap : i!= i+1 && i!= i-1");
						}
						else{
							//System.out.println(" Dernière meta : overlap &&& final elem :  i= i-1");
							overlaped++;
							finalElem=true;
						}
						
					}}
						if(overlaped>=1)
						{
							//System.out.println(" i add the elem");
							overlapedMeta.OverlapedMetaList.add(DSA.DupInstancesList.get(j).sortMetadataList.get(i));
							overlapedMeta.OverlapedMetaProp.add(DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaProperty);
							overlapedMeta.OverlapedValues.add(DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaValue);
							DSA.totalMetaOverlap ++;
						}
						if(finalElem){
							//System.out.println("___ Overlap Metadata "+overlaped+"  :"+DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaProperty + "||     Overlap Metavalue "+overlaped+"  :"+DSA.DupInstancesList.get(j).sortMetadataList.get(i).MetaValue);
							overlapedMeta.MetaName= DSA.DupInstancesList.get(j).sortMetadataList.get(i).Metadata;
							DSA.DupInstancesList.get(j).OverlapedMetadataList.add(overlapedMeta);
							DSA.countContradiction(overlapedMeta.OverlapedValues);
							overlapedMeta = new OverlapedMetadata();
							finalElem = false;
						}
				}
				overlaped=0;
			}
			
				
			//Get the java Runtima 
			Runtime runtime= Runtime.getRuntime();
			runtime.gc();
			memoryConsumed = memoryConsumed + runtime.totalMemory() - runtime.freeMemory(); 
			
			long stopTime = System.currentTimeMillis();
			//System.out.println( " \n Time detection : "+ (stopTime - startTime));
			executionTime = executionTime+ (stopTime - startTime);
			//System.out.println( " \n Total Time : "+ executionTime);
		}
				
		
		
		
		
		// algorithme avec une complexité de O(n²) ==> non efficace  
		public static String detectDuplication1(){
			DuplicatedInstances Dup = new DuplicatedInstances();
			int i=0;
			int j=0;
			String res="";
			
			
			DuplicatedInstances DupInstance = new DuplicatedInstances();
			Data data = new Data();
			Metadata metadata = new Metadata();
			
			long startTime = System.currentTimeMillis();
			for(Instance baseList:DSA.InstanceList){
				baseList.seenInstance=false;
				//System.out.println("base instance : "+ i++ +"\n");
				res = res + "\n*********************************************** Base instance : "+ baseList.Instance+i++; 
				for(Instance list:DSA.InstanceList){
					System.out.println("test instance : "+ i +" et "+ j +" Voila "+ list.Instance +"\n"+baseList.Instance+"\n");
					res = res + "\n***** test instance : "+ list.Instance+j++; 
					if(baseList.Instance.equals(list.Instance)){
						//System.out.println("base intsance = test instance "+ baseList.Instance +"et"+ list.Instance+ " \n");
						res= res+ "\n base intsance = test instance ";
						res = res + "\n index base intsance != index test instance "+ DSA.InstanceList.indexOf(baseList) +"et"+ DSA.InstanceList.indexOf(list)+ " \n";
						res = res + "\n instance test vue : " + list.seenInstance;
						if ( (list.seenInstance==false) ){
							//System.out.println("test instance : "+ list.Instance +"\n"+baseList.Instance+"\n");
							//System.out.println("index base intsance != index test instance "+ DSA.InstanceList.indexOf(baseList) +"et"+ DSA.InstanceList.indexOf(list)+ " \n");
							DupInstance.DuplicatedInstList.add(list);
							DupInstance.MetadataList.addAll(list.MetadataList);
							DupInstance.DataList.addAll(list.DataList);
							//System.out.println("Instance :"+list.Instance);
							for(Metadata met:list.MetadataList){
								System.out.println("Metadata :" + met.Metadata);
							}
							for(Data dat:list.DataList){
								System.out.println("Data :" + dat.Data);
							}
							list.seenInstance=true;
						}
						
					}
					
				}
				j =0;
				DSA.DupInstancesList.add(DupInstance);
				DupInstance = new DuplicatedInstances();
			}
			long stopTime = System.currentTimeMillis();
			executionTime = stopTime - startTime;
			return res + " \n Time : "+ executionTime;
		}
		
		
}
