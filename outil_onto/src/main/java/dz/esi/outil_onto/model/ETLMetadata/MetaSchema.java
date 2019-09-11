package dz.esi.outil_onto.model.ETLMetadata;

import java.util.ArrayList;
import java.util.HashSet;

public class MetaSchema {
	// Metaproperties to load in the metaSchema 
		
		MetaClassesType TypeMetaClass;
		String metaProp="";
		
		
		
	
		public String getMetaProp() {
			return metaProp;
		}
		public void setMetaProp(String metaProp) {
			this.metaProp = metaProp;
		}
		public MetaClassesType getTypeMetaClass() {
			return TypeMetaClass;
		}
		public void setTypeMetaClass(MetaClassesType typeMetaClass) {
			TypeMetaClass = typeMetaClass;
		}
		
		
}
