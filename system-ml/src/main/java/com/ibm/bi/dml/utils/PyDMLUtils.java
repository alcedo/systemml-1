package com.ibm.bi.dml.utils;

import org.apache.commons.lang.StringUtils;

public class PyDMLUtils {

	/**
	 * Typically, converts String TRUE/FALSE to String True/False
	 * 
	 * @param val
	 *            Original boolean String value
	 * @return Converted boolean String value
	 */
	public static String convertBooleanStringToPyDMLBooleanString(String val) {
		return StringUtils.capitalize(StringUtils.lowerCase(val));
	}
}
