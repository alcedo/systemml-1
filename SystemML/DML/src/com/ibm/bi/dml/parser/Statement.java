package com.ibm.bi.dml.parser;

import com.ibm.bi.dml.utils.LanguageException;


public abstract class Statement {

	public static final String INPUTSTATEMENT = "read";
	public static final String OUTPUTSTATEMENT = "write";
	public static final String PRINTSTATEMENT = "print";
	
	public static final String IO_FILENAME = "filename";
	public static final String READROWPARAM = "rows";
	public static final String READCOLPARAM = "cols";
	public static final String READNUMNONZEROPARAM = "nnz";
	public static final String FORMAT_TYPE = "format";
	public static final String ROWBLOCKCOUNTPARAM = "rows_in_block";
	public static final String COLUMNBLOCKCOUNTPARAM = "cols_in_block";
	public static final String DATATYPEPARAM = "data_type";
	public static final String VALUETYPEPARAM = "value_type";
	public static final String DESCRIPTIONPARAM = "description"; 

	public static final String RAND_ROWS 	=  "rows";	 
	public static final String RAND_COLS 	=  "cols";
	public static final String RAND_MIN  	=  "min";
	public static final String RAND_MAX  	=  "max";
	public static final String RAND_SPARSITY = "sparsity"; 
	public static final String RAND_SEED    =  "seed";
	public static final String RAND_PDF		=  "pdf";
	
	public static final String IMPORT  = "import";
	public static final  String DMLPATH = "dml-path";
	
	public static final String TEXT_FORMAT_TYPE = "text";
	public static final String BINARY_FORMAT_TYPE = "binary";
	
	public static final String MATRIX_DATA_TYPE = "matrix";
	public static final String SCALAR_DATA_TYPE = "scalar";
	
	public static final String DOUBLE_VALUE_TYPE = "double";
	public static final String BOOLEAN_VALUE_TYPE = "boolean";
	public static final String INT_VALUE_TYPE = "int";
	public static final String STRING_VALUE_TYPE = "string";
	
	
	public abstract boolean controlStatement();
	
	public abstract VariableSet variablesRead();
	public abstract VariableSet variablesUpdated();
 
	public abstract void initializeforwardLV(VariableSet activeIn) throws LanguageException;
	public abstract VariableSet initializebackwardLV(VariableSet lo) throws LanguageException;
	
	public abstract Statement rewriteStatement(String prefix) throws LanguageException;
	
	public static boolean DEBUG = true;
		
}
