package dz.esi.outil_onto.model.ETLMetadata;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.iterator.ExtendedIterator;

import dz.esi.outil_onto.controller.Controller;
import dz.esi.outil_onto.model.DataSources.DataSource;
import dz.esi.outil_onto.model.DataSources.TypeSource;
import dz.esi.outil_onto.model.ETLMappings.ETLOperations;
import dz.esi.outil_onto.model.ETLOperators.ETL;
import dz.esi.outil_onto.model.ETLOperators.ETLOperation;
import dz.esi.outil_onto.model.ETLOperators.typeETLOp;
import dz.esi.outil_onto.model.OntologyManager.Jena;

public class ExtractionPhase {
	private boolean ExtractInst;
	private boolean ExtractMeta;
	private boolean ExtractData;
	
	//Metrics 
	static float executionTime;
	static long memoryConsumed;
	
	// temps d'execution de l'extraction à partir des sources externes
	static float executionTimeExt;
	static long memoryConsumedExt;
	
	// temps d'execution de l'extraction à partir des sources externes
	static float executionTimeInt;
	static long memoryConsumedInt;
	
	
	
	public static String extractInternalInstances(ETLOperations m){
		String Result="";
		String[] Statement =null;
		Boolean metaFilled = false;
		//Object instanciation
		Instance instance = new Instance();
		Data data = new Data();
		Metadata metadata = new Metadata();
		
		long startTime = System.currentTimeMillis();
		
		for (ETLOperation ETLOp:m.getETLOperations()) {
			if ((ETLOp.getType() == typeETLOp.Retrieve)){
				if(ETLOp.getTypeResource()== TypeSource.Internal){
					// Get the ressource class 
					OntClass resourceClasse = Controller.ontology.getOntClass(ETLOp.getResource());
					final ExtendedIterator<? extends OntResource> instances = resourceClasse.listInstances();
                	while(instances.hasNext()){
                		OntResource in = instances.next();
                		instance.setInstance(in.toString());
						instance.setLocalName(in.getLocalName());
						Result = Result + "\n **-** Instance : " +  in.getLocalName() + "  |  ";
						System.out.println("\n **-** Instance : " +  in.getLocalName() + "  |  ");
                		for ( final ExtendedIterator<Statement> properties = in.listProperties(); properties.hasNext(); ) {
           				 	while(properties.hasNext()){
           				 	String property = properties.next().toString();
           				 	Statement = Jena.statementSplit(property);
	           				 if(Jena.checkMetadata(Statement[1])){
								 metadata.setMetaProperty(Statement[1]);
								 DSA.addToMetaPropertyList(Statement[1]);
								 metadata.setMetadata(Jena.getLocalName(Statement[1]));
								 metadata.setMetadataType();// detect the type of the metadata and set "metaType" attribute 
								 DSA.incrementingMetaTypes(metadata.getMetaType()); // Depending to the type of the metadata this method inrement the appropriate metric 
								 DSA.incrementingIntMetadata();
								 DSA.incrementingMetadata();
								 Result = Result + "MetaProperty : " + Jena.getLocalName(Statement[1]) + "  |  ";
								 System.out.println( "MetaProperty : " + Jena.getLocalName(Statement[1]) + "  |  ");
								 metadata.setMetaValue(Statement[2]);
								 if(Jena.checkValue(Statement[2])){
									 
									 Result = Result + "MetaValue : " + Statement[2] + "  |  ";
									 System.out.println( "MetaValue : " + Jena.getValue(Statement[2]) + "  |  ");
								 }else{
									 Result = Result + "MetaValue : " + Jena.getLocalName(Statement[2]) + "  |  ";
									 System.out.println( "MetaValue : " + Jena.getLocalName(Statement[2]) + "  |  ");
								 }
								 metaFilled = true;
							 }else{
								 data.setProperty(Statement[1]);
								 data.setData( Jena.getLocalName(Statement[1]));
								 DSA.incrementingData();
								 DSA.incrementingIntData();
								 Result = Result + "DataProperty : " + Jena.getLocalName(Statement[1]) + "  |  ";
								 System.out.println( "DataProperty : " + Jena.getLocalName(Statement[1]) + "  |  ");
								 data.setValue(Statement[2]);
								 if(Jena.checkValue(Statement[2])){
									 Result = Result + "DataValue  : " + Statement[2] + "  |  ";
									 System.out.println( "DataValue : " + Jena.getValue(Statement[2]) + "  |  ");
								 }else{
									 Result = Result + " DataValue : " + Jena.getLocalName(Statement[2]) + "  |  ";
									 System.out.println( "DataValue : " + Jena.getLocalName(Statement[2]) + "  |  ");
								 }
								 metaFilled =false;
							 }
	           				Result = Result + "  \n \n  ";
	           				System.out.println("\n*********************************** NB date :" +DSA.dateMetaNb+ " NB descr :"+DSA.descriptMetaNb+ " NB prov :"+DSA.provMetaNb+ " NB stat :"+DSA.statMetaNb+ " NB Version:"+DSA.versionMetaNb);
	           				System.out.println("\n*********************************** NB data :" +DSA.totalDataNb+ " NB metadata :"+DSA.totalMetaNb+ " NB Ext Data :"+DSA.externalDataNb+ " NB Ext Metadta :"+DSA.externalMetaNb+ " NB Internal Data:"+DSA.internalDataNb+" NB Internal MetaData:"+DSA.internalMetaNb);
	           				if(metaFilled)
							 {
								 instance.MetadataList.add(metadata);
							 }else{
								 if(data.getProperty()!=null){
									 instance.DataList.add(data); 
								 }
							 }
							 data = new Data();
							 metadata = new Metadata();
           				 	}
                			
           				 }
                		Result = Result + "  \n\n \n  ";
                		 DSA.InternalInstanceList.add(instance);
						 instance = new Instance();
                	}
				}
			}
		}
		
		//Get the java Runtima 
		Runtime runtime= Runtime.getRuntime();
		runtime.gc();
		memoryConsumedInt = runtime.totalMemory() - runtime.freeMemory(); 
		
		long stopTime = System.currentTimeMillis();
		executionTimeInt = stopTime - startTime;
		return Result + " \n Time : "+ executionTimeInt;
	}
	
	public static String extractExternalInstances(ETLOperations m){
		String Result="";
		String Consol="";
		String[] Statement = null;
		int i=0;
		
		//Object instanciation
		Instance instance = new Instance();
		Data data = new Data();
		Metadata metadata = new Metadata();
		
		int check= 0; // We use this number to check if we have all the informations of an instance (if check = 2 we have the instance name) if (check = 3, we have the the meta property) ( if check = 4, we have the instance, the meta property and the meta value) 
		boolean metaFilled = false;
		
		// Clear the DSA
		//DSA.InstanceList.clear();
		long startTime = System.currentTimeMillis();
		for (ETLOperation ETLOp:m.getETLOperations()) {
			System.out.println("Passe a l'autre");
			if ((ETLOp.getType() == typeETLOp.Retrieve)){
				if(ETLOp.getTypeResource()== TypeSource.External){
					if (ETLOp.getFormat().contains("REIF")){
					Model modelExt = ModelFactory.createDefaultModel() ;
					 modelExt = RDFDataMgr.loadModel(ETLOp.getPath());
					 if (modelExt != null){
						 //Read the next statement 
						 StmtIterator R = modelExt.listStatements();
						 while (R.hasNext()){
							 
							 String inst = R.next().toString();
							 Statement = Jena.statementSplit(inst); 
							 
							 // Verify if the statement concern the "Type","subject","predicate" or "object", "hasMeta"
							 if(Statement[1].contains("#type")){
								 if (check != 0){
									 check = 0;
								 }else{
									 check = 1;// it's a new instance
									 //Result = Result + Statement[2] + "  |  ";
								 }
							 }
							 if(Statement[1].contains("#subject")){
								 if (check == 1){// we get the name of the instance 
									 instance.setInstance(Statement[2]);
									 instance.setLocalName(Jena.getLocalName(Statement[2]));
									 check = 2; //The next statement will contain the meta property
									 Result = Result +" << "+i++ +" >> "+ Jena.getLocalName(Statement[2]) + "  |  ";
								 }
							 }
							 if(Statement[1].contains("#predicate")){
								 if (check == 2){// We get the name of the meta property or the data property
									 if(Jena.checkMetadata(Statement[2])){
										 metadata.setMetaProperty(Statement[2]);
										 DSA.addToMetaPropertyList(Statement[2]);
										 metadata.setMetadata(Jena.getLocalName(Statement[2]));
										 metadata.setMetadataType();// detect the type of the metadata and set "metaType" attribute 
										 DSA.incrementingMetaTypes(metadata.getMetaType()); // Depending to the type of the metadata this method inrement the appropriate metric 
										 DSA.incrementingExtMetadata();
										 DSA.incrementingMetadata();
										 Result = Result + "MetaProperty : " ;
										 metaFilled = true;
									 }else{
										 data.setProperty(Statement[2]);
										 data.setData( Jena.getLocalName(Statement[2]));
										 DSA.incrementingExtData();
										 DSA.incrementingData();
										 Result = Result + "DataProperty : ";
										 metaFilled =false;
									 }
									 check = 3; //The next statement will contain the meta value
									 Result = Result + Jena.getLocalName(Statement[2]) + "  |  ";
								 }
							 }
							 if(Statement[1].contains("#object")){
								 if (check == 3){// We get the metadata value
									 if(metaFilled)
									 {
										 metadata.setMetaValue(Statement[2]);
										 Result = Result + "MetaValue : " ;
									 }else{
										 if(data.getProperty()!=null){
											 data.setValue(Statement[2]); 
											 Result = Result + "DataValue : " ;
										 }
									 }
									 // Verify if the object is a literal 
									 if(Jena.checkValue(Statement[2])){
										 
										 Result = Result + Jena.getValue(Statement[2]) + "  |  ";
									 }else{
										 Result = Result + Jena.getLocalName(Statement[2]) + "  |  ";
									 }
									 check = 4; //The next statement will contain the shared meta
								 }
							 }
							 if(Statement[1].contains("/hasSharedMeta")){
								 if (check == 4){// We get the metadata value
									 if(metaFilled)
									 {
										 instance.MetadataList.add(metadata);
									 }else{
										 if(data.getProperty()!=null){
											 instance.DataList.add(data); 
										 }
									 }
									 DSA.ExternalInstanceList.add(instance);
									 instance = new Instance();
									 data = new Data();
									 metadata = new Metadata();
									 check = 0; //The; next statement will contain the shared meta
									 Result = Result + "\n\n";
									 System.out.println(Result);
									 System.out.println("*********************************** NB date :" +DSA.dateMetaNb+ " NB descr :"+DSA.descriptMetaNb+ " NB prov :"+DSA.provMetaNb+ " NB stat :"+DSA.statMetaNb+ " NB Version:"+DSA.versionMetaNb);
									 System.out.println("*********************************** NB data :" +DSA.totalDataNb+ " NB metadata :"+DSA.totalMetaNb+ " NB Ext Data :"+DSA.externalDataNb+ " NB Ext Metadta :"+DSA.externalMetaNb+ " NB Internal Data:"+DSA.internalDataNb+" NB Internal MetaData:"+DSA.internalMetaNb);
								 }
							 }
							 Consol = Consol + inst +"\n";
							 Statement = null;
						 }
						 
						 }else{
						 System.out.println("\n Rien de rien");
					 }
				   }
				}
			}
		}
		
		//Get the java Runtima 
		Runtime runtime= Runtime.getRuntime();
		runtime.gc();
		memoryConsumedExt = runtime.totalMemory() - runtime.freeMemory(); 
				
		long stopTime = System.currentTimeMillis();
		executionTimeExt = stopTime - startTime;
		return Result + " \n Time : "+ executionTimeExt;
}

	/*public void extractInstances(ETLOperations map){
		
			for (ETLOperation ETLOp:ETL.orderETLOperations(map.getETLOperations())) {
				System.out.println("Passe a l'autre");
				if ((ETLOp.getType() == typeETLOp.Retrieve)){
					if(ETLOp.getTypeResource()== TypeSource.External){
						
						Model modelExt = ModelFactory.createDefaultModel() ;
						 modelExt = RDFDataMgr.loadModel(ETLOp.getPath());
						 if (modelExt != null){
							 StmtIterator R = modelExt.listStatements();
							 while (R.hasNext()){
								 String inst = R.next().toString();
								 if(inst.contains("created")||inst.contains("modified")||inst.contains("description")||inst.contains("subject")||inst.contains("wasderivedfrom")){
									 System.out.println("\n Voila resultat : " + inst);
								 }
							 }
							 
							 }else{
							 System.out.println("\n Rien de rien");
						 }
					}
				}
			}
		//targetInstances.setText(result);
	}
	
	*/
}
