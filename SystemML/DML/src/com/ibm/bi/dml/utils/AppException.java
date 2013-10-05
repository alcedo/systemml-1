/**
 * IBM Confidential
 * OCO Source Materials
 * (C) Copyright IBM Corp. 2010, 2013
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */

package com.ibm.bi.dml.utils;

public class AppException extends DMLException 
{
	@SuppressWarnings("unused")
	private static final String _COPYRIGHT = "Licensed Materials - Property of IBM\n(C) Copyright IBM Corp. 2010, 2013\n" +
                                             "US Government Users Restricted Rights - Use, duplication  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	
	/**
	 * This exception should be thrown to flag errors by systemML apps
	 * including data transformation, post-processing etc.
	 */
	private static final long serialVersionUID = 1L;

	public AppException(String string) {
		super(string);
	}

	public AppException(String string, Exception ex){
		super(string,ex);
	}
}