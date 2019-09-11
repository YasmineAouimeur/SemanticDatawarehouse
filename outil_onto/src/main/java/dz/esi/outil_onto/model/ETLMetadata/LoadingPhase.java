package dz.esi.outil_onto.model.ETLMetadata;

import dz.esi.outil_onto.model.ETLMetadata.*;

public class LoadingPhase {

	
	// Meta-mappings
	public static void affectMetaMappings(){
		
		
		for(String metaProp: DSA.metaProperties){
			MetaSchema MetaSchema = new MetaSchema();
			System.out.println("le voilaa : "+ metaProp +"\n");
			if(metaProp.contains("date")||metaProp.contains("created")||metaProp.contains("modified")||metaProp.contains("description")||metaProp.contains("subject")||metaProp.contains("wasDerivedFrom")){
				MetaSchema.TypeMetaClass = MetaClassesType.DataSource;
				MetaSchema.metaProp=metaProp;
				DSA.metaschema.add(MetaSchema);
			}else{
				if(metaProp.contains("title")||metaProp.contains("reviewer")||metaProp.contains("publisher")){
					MetaSchema.TypeMetaClass = MetaClassesType.VocabularyTerm;
					MetaSchema.metaProp=metaProp;
					DSA.metaschema.add(MetaSchema);
				}else{
					if(metaProp.contains("priorVersion")||metaProp.contains("versionInfo")||metaProp.contains("uniqueContributorNb")||metaProp.contains("revPerYear")||metaProp.contains("revPerLastMonth")||metaProp.contains("isVersion")){
						MetaSchema.TypeMetaClass = MetaClassesType.StatisticalRecord;
						MetaSchema.metaProp=metaProp;
						DSA.metaschema.add(MetaSchema);
					}else{
						MetaSchema.TypeMetaClass = MetaClassesType.TraceabilityEvidence;
						MetaSchema.metaProp=metaProp;
						DSA.metaschema.add(MetaSchema);
					}
				}
			}
			
			
		}	
	}
}
