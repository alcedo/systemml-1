/**
 * IBM Confidential
 * OCO Source Materials
 * (C) Copyright IBM Corp. 2010, 2013
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */

package com.ibm.bi.dml.lops;

import com.ibm.bi.dml.lops.LopProperties.ExecLocation;
import com.ibm.bi.dml.lops.LopProperties.ExecType;
import com.ibm.bi.dml.lops.compile.JobType;
import com.ibm.bi.dml.parser.Expression.DataType;
import com.ibm.bi.dml.parser.Expression.ValueType;


public class PartialMVMult extends Lop 
{
	@SuppressWarnings("unused")
	private static final String _COPYRIGHT = "Licensed Materials - Property of IBM\n(C) Copyright IBM Corp. 2010, 2013\n" +
                                             "US Government Users Restricted Rights - Use, duplication  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	
	/**
	 * Constructor to setup a partial Matrix-Vector Multiplication
	 * 
	 * @param input
	 * @param op
	 * @return 
	 * @throws LopsException
	 */
	
	public PartialMVMult(Lop input1, Lop input2, DataType dt, ValueType vt) throws LopsException {
		super(Lop.Type.MVMult, dt, vt);		
		this.addInput(input1);
		this.addInput(input2);
		input1.addOutput(this);
		input2.addOutput(this);
		
		/*
		 * This lop can be executed only in MMCJ job.
		 */
		
		boolean breaksAlignment = true;
		boolean aligner = false;
		boolean definesMRJob = false;
		lps.addCompatibility(JobType.GMR);
		lps.addCompatibility(JobType.RAND);
		this.lps.setProperties( inputs, ExecType.MR, ExecLocation.Map, breaksAlignment, aligner, definesMRJob );
	}


	public String toString() {
		return "Operation = MVMult";
	}
	
	@Override
	public String getInstructions(int input_index1, int input_index2, int output_index)
	{
		String opString = new String(getExecType() + Lop.OPERAND_DELIMITOR);
		opString += "mvmult";
		
		String inst = new String("");
		inst += opString + OPERAND_DELIMITOR + 
				input_index1 + DATATYPE_PREFIX + getInputs().get(0).get_dataType() + VALUETYPE_PREFIX + getInputs().get(0).get_valueType() + OPERAND_DELIMITOR + 
				input_index2 + DATATYPE_PREFIX + getInputs().get(1).get_dataType() + VALUETYPE_PREFIX + getInputs().get(1).get_valueType() + OPERAND_DELIMITOR + 
		        output_index + DATATYPE_PREFIX + get_dataType() + VALUETYPE_PREFIX + get_valueType() ;
		return inst;
	}


}
