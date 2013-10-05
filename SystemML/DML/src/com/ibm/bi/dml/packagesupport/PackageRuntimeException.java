/**
 * IBM Confidential
 * OCO Source Materials
 * (C) Copyright IBM Corp. 2010, 2013
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */

package com.ibm.bi.dml.packagesupport;

/**
 * Class to capture a runtime exception during package execution.
 * 
 * 
 * 
 */
public class PackageRuntimeException extends RuntimeException 
{
	@SuppressWarnings("unused")
	private static final String _COPYRIGHT = "Licensed Materials - Property of IBM\n(C) Copyright IBM Corp. 2010, 2013\n" +
                                             "US Government Users Restricted Rights - Use, duplication  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	
	private static final long serialVersionUID = 7388224928778587925L;

	public PackageRuntimeException(String msg) {
		super(msg);
	}

	public PackageRuntimeException(Exception e) {
		super(e);
	}

	public PackageRuntimeException(String msg, Exception e) {
		super(msg,e);
	}

}
