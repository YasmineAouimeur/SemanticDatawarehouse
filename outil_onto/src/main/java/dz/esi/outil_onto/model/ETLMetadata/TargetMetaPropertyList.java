package dz.esi.outil_onto.model.ETLMetadata;

import java.util.ArrayList;

public class TargetMetaPropertyList {
	ArrayList<Metadata> TargetMetadataList = new ArrayList<Metadata>();

	public ArrayList<Metadata> getTargetMetadataList() {
		return TargetMetadataList;
	}

	public void setTargetMetadataList(ArrayList<Metadata> targetMetadataList) {
		TargetMetadataList = targetMetadataList;
	}
}
