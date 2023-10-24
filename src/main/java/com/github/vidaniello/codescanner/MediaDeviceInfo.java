package com.github.vidaniello.codescanner;

import java.io.Serializable;

public class MediaDeviceInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private String deviceId;
	private String label;
	private String kind;
	private String groupId;
	
	public MediaDeviceInfo() {
	
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getLabel() {
		return label;
	}

	public String getKind() {
		return kind;
	}

	public String getGroupId() {
		return groupId;
	}
	
	
	

}
