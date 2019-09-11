package dz.esi.outil_onto.model.ETLMetadata;

public class Metadata{
	String Metadata;
	String MetaProperty;
	String MetaValue;
	String Format;
	MetadataType MetaType;
	
	public String getMetadata() {
		return Metadata;
	}
	public void setMetadata(String metadata) {
		Metadata = metadata;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getMetaProperty() {
		return MetaProperty;
	}
	public void setMetaProperty(String metaProperty) {
		MetaProperty = metaProperty;
	}
	public String getMetaValue() {
		return MetaValue;
	}
	public void setMetaValue(String metaValue) {
		MetaValue = metaValue;
	} 
	
	public void setMetadataType(){
		if (this.Metadata.contains("created")||
			this.Metadata.contains("builder")||
			this.Metadata.contains("developer")||
			this.Metadata.contains("publisher")||
			this.Metadata.contains("wasDerivedFrom")||
			this.Metadata.contains("foudry")||
			this.Metadata.contains("manufacturer")||
			this.Metadata.contains("owners")||
			this.Metadata.contains("hasSource")||
			this.Metadata.contains("reviewer")||
			this.Metadata.contains("isDefinedBy")||
			this.Metadata.contains("owner")){
			
				this.MetaType = MetadataType.Provenance;
		}
		
		if (this.Metadata.contains("modified")||
			this.Metadata.contains("date")||
			this.Metadata.contains("reviewDate")||
			this.Metadata.contains("foundingYear")||
			this.Metadata.contains("validFrom")||
			this.Metadata.contains("validTo")){
				
			this.MetaType = MetadataType.Date;
			}
		
		if (this.Metadata.contains("label")||
			this.Metadata.contains("seeAlso")||
			this.Metadata.contains("title")||
			this.Metadata.contains("description")||
			this.Metadata.contains("subject")||
			this.Metadata.contains("comment")||
			this.Metadata.contains("isDefinedBy")||
			this.Metadata.contains("incompatibleWith")||
			this.Metadata.contains("backwardCompatibleWith")||
			this.Metadata.contains("distributor")||
			this.Metadata.contains("recordLabel")){
				
			this.MetaType = MetadataType.Description;
			}
		
		if (this.Metadata.contains("versionInfo")||
			this.Metadata.contains("priorVersion")||
			this.Metadata.contains("isVersion")||
			this.Metadata.contains("hasMainRevision")||
			this.Metadata.contains("hasOldRevision")){
					
				this.MetaType = MetadataType.Version;
				}
		
		if (this.Metadata.contains("uniqueContributorNb")||
			this.Metadata.contains("revPerYear2016")||
			this.Metadata.contains("revPerYear2015")||
			this.Metadata.contains("revPerYear2014")||
			this.Metadata.contains("revPerYear2013")||
			this.Metadata.contains("revPerYear2012")||
			this.Metadata.contains("revPerYear2010")||
			this.Metadata.contains("revPerYear2007")||
			this.Metadata.contains("revPerYear2006")||
			this.Metadata.contains("revPerYear2005")||
			this.Metadata.contains("revPerLastMonth1")||
			this.Metadata.contains("revPerLastMonth2")){
						
				this.MetaType = MetadataType.Statistical;
				}
		
	}
	public MetadataType getMetaType() {
		return MetaType;
	}
	public void setMetaType(MetadataType metaType) {
		MetaType = metaType;
	}
}
