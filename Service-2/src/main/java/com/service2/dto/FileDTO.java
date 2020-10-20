package com.service2.dto;

import java.util.List;
import java.util.Map;

public class FileDTO {

	private List<Map<String, Object>> fileName;

	public List<Map<String, Object>> getFileName() {
		return fileName;
	}

	public void setFileName(List<Map<String, Object>> fileName) {
		this.fileName = fileName;
	}

	
//	@Override
//	public String toString() {
//		return "{fileName:" + fileName + "}";
//	}
//	

}
