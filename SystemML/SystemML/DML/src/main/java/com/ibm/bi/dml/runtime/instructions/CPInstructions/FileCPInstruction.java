/**
 * IBM Confidential
 * OCO Source Materials
 * (C) Copyright IBM Corp. 2010, 2013
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */

package com.ibm.bi.dml.runtime.instructions.CPInstructions;

import java.io.IOException;

import com.ibm.bi.dml.runtime.DMLRuntimeException;
import com.ibm.bi.dml.runtime.DMLUnsupportedOperationException;
import com.ibm.bi.dml.runtime.controlprogram.ExecutionContext;
import com.ibm.bi.dml.runtime.functionobjects.RemoveFile;
import com.ibm.bi.dml.runtime.functionobjects.RenameFile;
import com.ibm.bi.dml.runtime.instructions.Instruction;
import com.ibm.bi.dml.runtime.instructions.InstructionUtils;
import com.ibm.bi.dml.runtime.matrix.operators.Operator;
import com.ibm.bi.dml.runtime.matrix.operators.SimpleOperator;
import com.ibm.bi.dml.runtime.util.MapReduceTool;



public class FileCPInstruction extends CPInstruction 
{
	@SuppressWarnings("unused")
	private static final String _COPYRIGHT = "Licensed Materials - Property of IBM\n(C) Copyright IBM Corp. 2010, 2013\n" +
                                             "US Government Users Restricted Rights - Use, duplication  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	
	private enum FileOperationCode {
		RemoveFile, MoveFile
	};

	private FileOperationCode code;
	String input1;
	String input2;
	int arity;
	
	public FileCPInstruction (Operator op, FileOperationCode _code, String in1, String in2, int _arity, String istr )
	{
		super(op);
		cptype = CPINSTRUCTION_TYPE.File;
		code = _code;
		input1 = in1;
		input2 = in2;
		arity = _arity;
		instString = istr;
	}

	private static FileOperationCode getFileOperationCode ( String str ) throws DMLUnsupportedOperationException {
		if ( str.equalsIgnoreCase("rm"))
			return FileOperationCode.RemoveFile;
		else if ( str.equalsIgnoreCase("mv") ) 
			return FileOperationCode.MoveFile;
		else
			throw new DMLUnsupportedOperationException("Invalid function: " + str);
	}
	
	public static Instruction parseInstruction ( String str ) throws DMLRuntimeException, DMLUnsupportedOperationException {
		String opcode = InstructionUtils.getOpCode(str);
		
		int _arity = 2;
		if ( opcode.equalsIgnoreCase("rm") ) 
			_arity = 1;
		
		InstructionUtils.checkNumFields ( str, _arity ); // there is no output, so we just have _arity
		
		String[] parts = InstructionUtils.getInstructionPartsWithValueType ( str );
		
		String in1, in2;
		opcode = parts[0];
		FileOperationCode focode = getFileOperationCode(opcode); 
		in1 = parts[1];
		in2 = null;
		if ( _arity == 2 )
			in2 = parts[2];
		
		// Determine appropriate Function Object based on opcode
		
		if ( opcode.equalsIgnoreCase("rm") ) {
			return new FileCPInstruction(new SimpleOperator(RemoveFile.getRemoveFileFnObject()), focode, in1, in2, _arity, str);
		} 
		else if ( opcode.equalsIgnoreCase("mv") ) {
			return new FileCPInstruction(new SimpleOperator(RenameFile.getRenameFileFnObject()), focode, in1, in2, _arity, str);
		}
		return null;
	}
	
	@Override
	public void processInstruction(ExecutionContext ec) throws DMLRuntimeException {
		
		try {
		switch(code) {
		case RemoveFile:
			MapReduceTool.deleteFileIfExistOnHDFS(input1);
			MapReduceTool.deleteFileIfExistOnHDFS(input1+".mtd");
			break;
		case MoveFile:
			MapReduceTool.renameFileOnHDFS(input1, input2);
			MapReduceTool.renameFileOnHDFS(input1+".mtd", input2+".mtd");
			break;
			
		default:
			throw new DMLRuntimeException("Unexpected opcode: " + code);
		}
		}
		catch ( IOException e ) {
			throw new DMLRuntimeException(e);
		}
		
		// NO RESULT is produced
		// pb.setVariable(output.get_name(), sores);
	}
	
	

}