package com.ibm.bi.dml.parser;

public enum ScriptType {
	DML, PYDML;
	
	public String lowerCase() {
		return super.toString().toLowerCase();
	}
}
