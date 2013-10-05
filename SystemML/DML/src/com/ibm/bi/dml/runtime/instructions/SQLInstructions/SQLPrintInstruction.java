/**
 * IBM Confidential
 * OCO Source Materials
 * (C) Copyright IBM Corp. 2010, 2013
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */

package com.ibm.bi.dml.runtime.instructions.SQLInstructions;

import com.ibm.bi.dml.runtime.DMLRuntimeException;
import com.ibm.bi.dml.runtime.controlprogram.ExecutionContext;
import com.ibm.bi.dml.sql.sqlcontrolprogram.ExecutionResult;


public class SQLPrintInstruction extends SQLInstructionBase
{
	@SuppressWarnings("unused")
	private static final String _COPYRIGHT = "Licensed Materials - Property of IBM\n(C) Copyright IBM Corp. 2010, 2013\n" +
                                             "US Government Users Restricted Rights - Use, duplication  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	
	public SQLPrintInstruction(String sql)
	{
		this.sql = sql;
		this.instString = "print(" + sql  +")";
	}
	
	String sql;
	//PrintStream stream;
	
	@Override
	public ExecutionResult execute(ExecutionContext ec) throws DMLRuntimeException {
		String s = null;
		try {
			if(ec.isDebug())
			{
				System.out.println("#" + this.id + "\r\n");
				System.out.println(sql);
			}
			s = ec.getVariableString(sql.substring(2, sql.length()-2), false);
		} catch (Exception e) {
			throw new DMLRuntimeException(e);
		}
		System.out.println(s);
		 
		return new ExecutionResult(true, 0);
	}

	@Override
	public byte[] getAllIndexes() throws DMLRuntimeException {
		return null;
	}

	@Override
	public byte[] getInputIndexes() throws DMLRuntimeException {
		return null;
	}
	
}
