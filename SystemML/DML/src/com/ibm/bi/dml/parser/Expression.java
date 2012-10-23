package com.ibm.bi.dml.parser;

import java.util.HashMap;

import com.ibm.bi.dml.hops.Hops.FileFormatTypes;
import com.ibm.bi.dml.utils.LanguageException;


public abstract class Expression {
	public enum Kind {
		UnaryOp, BinaryOp, BooleanOp, BuiltinFunctionOp, ParameterizedBuiltinFunctionOp, DataOp, Data, Literal, RelationalOp, ExtBuiltinFunctionOp, FunctionCallOp
	};

	public enum BinaryOp {
		PLUS, MINUS, MULT, DIV, MATMULT, POW, INVALID
	};

	public enum RelationalOp {
		LESSEQUAL, LESS, GREATEREQUAL, GREATER, EQUAL, NOTEQUAL, INVALID
	};

	public enum BooleanOp {
		CONDITIONALAND, CONDITIONALOR, LOGICALAND, LOGICALOR, NOT, INVALID
	};

	public enum BuiltinFunctionOp {
		APPEND, ABS, SIN, COS, TAN, SQRT, EXP, LOG, DIAG, MIN, MAX, PMIN, PMAX, PPRED, LENGTH, NCOL, NROW, 
		SUM, MEAN, TRANS, RANGE, PROD, ROWSUM, COLSUM, ROWMAX, ROWINDEXMAX, ROWMIN, ROWMEAN, COLMAX, COLMIN, COLMEAN, 
		TRACE, CAST_AS_SCALAR, CTABLE, QUANTILE, INTERQUANTILE, ROUND, IQM, CENTRALMOMENT, COVARIANCE
	};

	public enum ParameterizedBuiltinFunctionOp {
		CDF, GROUPEDAGG, RMEMPTY, INVALID
	};
	
	public enum DataOp {
		READ, WRITE, RAND, INVALID	
	}

	public enum FunctCallOp {
		INTERNAL, EXTERNAL
	};
	
	public enum ExtBuiltinFunctionOp {
		EIGEN, CHOLESKY
	};

	public enum AggOp {
		SUM, MIN, MAX, INVALID
	};

	public enum ReorgOp {
		TRANSPOSE, DIAG
	};

	//public enum DataOp {
	//	READ, WRITE
	//};

	public enum DataType {
		MATRIX, SCALAR, OBJECT, UNKNOWN
	};

	public enum ValueType {
		INT, DOUBLE, STRING, BOOLEAN, OBJECT, UNKNOWN
	};

	public enum FormatType {
		TEXT, BINARY, UNKNOWN
	};

	public abstract Expression rewriteExpression(String prefix) throws LanguageException;
		
	
	protected Kind _kind;
	protected Identifier _output;

	private static int _tempId;

	public Expression() {
		_output = null;
	}

	public void setOutput(Identifier output) {
		_output = output;
	}

	public Kind getKind() {
		return _kind;
	}

	public Identifier getOutput() {
		return _output;
	}
	
	public void validateExpression(HashMap<String, DataIdentifier> ids, HashMap<String, ConstIdentifier> currConstVars) throws LanguageException {
		throw new LanguageException(this.printErrorLocation() + "Should never be invoked in Baseclass 'Expression'");
	};
	
	public static BinaryOp getBinaryOp(String val) {
		if (val.equalsIgnoreCase("+"))
			return BinaryOp.PLUS;
		else if (val.equalsIgnoreCase("-"))
			return BinaryOp.MINUS;
		else if (val.equalsIgnoreCase("*"))
			return BinaryOp.MULT;
		else if (val.equalsIgnoreCase("/"))
			return BinaryOp.DIV;
		else if (val.equalsIgnoreCase("^"))
			return BinaryOp.POW;
		else if (val.equalsIgnoreCase("%*%"))
			return BinaryOp.MATMULT;
		return BinaryOp.INVALID;
	}

	public static RelationalOp getRelationalOp(String val) {
		if (val == null) 
			return null;
		else if (val.equalsIgnoreCase("<"))
			return RelationalOp.LESS;
		else if (val.equalsIgnoreCase("<="))
			return RelationalOp.LESSEQUAL;
		else if (val.equalsIgnoreCase(">"))
			return RelationalOp.GREATER;
		else if (val.equalsIgnoreCase(">="))
			return RelationalOp.GREATEREQUAL;
		else if (val.equalsIgnoreCase("=="))
			return RelationalOp.EQUAL;
		else if (val.equalsIgnoreCase("!="))
			return RelationalOp.NOTEQUAL;
		return RelationalOp.INVALID;
	}

	public static BooleanOp getBooleanOp(String val) {
		if (val.equalsIgnoreCase("&&"))
			return BooleanOp.CONDITIONALAND;
		else if (val.equalsIgnoreCase("&"))
			return BooleanOp.LOGICALAND;
		else if (val.equalsIgnoreCase("||"))
			return BooleanOp.CONDITIONALOR;
		else if (val.equalsIgnoreCase("|"))
			return BooleanOp.LOGICALOR;
		else if (val.equalsIgnoreCase("!"))
			return BooleanOp.NOT;
		return BooleanOp.INVALID;
	}

	/**
	 * Convert format types from parser to Hops enum : default is text
	 */
	public static FileFormatTypes convertFormatType(String fn) {
		if (fn == null)
			return FileFormatTypes.TEXT;
		if (fn.equalsIgnoreCase(Statement.TEXT_FORMAT_TYPE)) {
			return FileFormatTypes.TEXT;
		}
		if (fn.equalsIgnoreCase(Statement.BINARY_FORMAT_TYPE)) {
			return FileFormatTypes.BINARY;
		}
		// ToDo : throw parse exception for invalid / unsupported format type
		return FileFormatTypes.TEXT;
	}

	/**
	 * Construct Hops from parse tree : Create temporary views in expressions
	 */
	public static String getTempName() {
		return "parsertemp" + _tempId++;
	}

	public abstract VariableSet variablesRead();

	public abstract VariableSet variablesUpdated();

	public static DataType computeDataType(Expression e1, Expression e2, boolean cast) throws LanguageException {
		return computeDataType(e1.getOutput(), e2.getOutput(), cast);
	}

	public static DataType computeDataType(Identifier id1, Identifier id2, boolean cast) throws LanguageException {
		DataType d1 = id1.getDataType();
		DataType d2 = id2.getDataType();

		if (d1 == d2)
			return d1;

		if (cast) {
			if (d1 == DataType.MATRIX && d2 == DataType.SCALAR)
				return DataType.MATRIX;
			if (d1 == DataType.SCALAR && d2 == DataType.MATRIX)
				return DataType.MATRIX;
		}

		throw new LanguageException(id1.printErrorLocation() + "Invalid Datatypes for operation",
				LanguageException.LanguageErrorCodes.INVALID_PARAMETERS);
	}

	public static ValueType computeValueType(Expression e1, Expression e2, boolean cast) throws LanguageException {
		return computeValueType(e1.getOutput(), e2.getOutput(), cast);
	}

	public static ValueType computeValueType(Identifier id1, Identifier id2, boolean cast) throws LanguageException {
		ValueType v1 = id1.getValueType();
		ValueType v2 = id2.getValueType();

		if (v1 == v2)
			return v1;

		if (cast) {
			if (v1 == ValueType.DOUBLE && v2 == ValueType.INT)
				return ValueType.DOUBLE;
			if (v2 == ValueType.DOUBLE && v1 == ValueType.INT)
				return ValueType.DOUBLE;
			
			// String value type will override others
			// Primary operation involving strings is concatenation (+)
			if ( v1 == ValueType.STRING || v2 == ValueType.STRING )
				return ValueType.STRING;
		}

		throw new LanguageException(id1.printErrorLocation() + "Invalid Valuetypes for operation",
				LanguageException.LanguageErrorCodes.INVALID_PARAMETERS);
	}

	
	///////////////////////////////////////////////////////////////////////////
	// store position information for expressions
	///////////////////////////////////////////////////////////////////////////
	public int _beginLine, _beginColumn;
	public int _endLine, _endColumn;
	
	public void setBeginLine(int passed)    { _beginLine = passed;   }
	public void setBeginColumn(int passed) 	{ _beginColumn = passed; }
	public void setEndLine(int passed) 		{ _endLine = passed;   }
	public void setEndColumn(int passed)	{ _endColumn = passed; }
	
	public void setAllPositions(int blp, int bcp, int elp, int ecp){
		_beginLine	 = blp; 
		_beginColumn = bcp; 
		_endLine 	 = elp;
		_endColumn 	 = ecp;
	}

	public int getBeginLine()	{ return _beginLine;   }
	public int getBeginColumn() { return _beginColumn; }
	public int getEndLine() 	{ return _endLine;   }
	public int getEndColumn()	{ return _endColumn; }
	
	public String printErrorLocation(){
		return "ERROR: line " + _beginLine + ", column " + _beginColumn + " -- ";
	}
	
	public String printWarningLocation(){
		return "WARNING: line " + _beginLine + ", column " + _beginColumn + " -- ";
	}
	
	public String printInfoLocation(){
		return "INFO: line " + _beginLine + ", column " + _beginColumn + " -- ";
	}
}
