/**
 * (C) Copyright IBM Corp. 2010, 2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.ibm.bi.dml.parser;

import java.util.HashMap;

import com.ibm.bi.dml.parser.antlr4.DMLParserWrapper;
import com.ibm.bi.dml.parser.python.PyDMLParserWrapper;

/**
 * Base class for all dml parsers in order to make the various compilation chains
 * independent of the used parser.
 */
public abstract class AParserWrapper 
{
	private boolean mlContext; // Is invocation from MLContext?	
	
	/**
	 * 
	 * @param fileName
	 * @param dmlScript
	 * @param argVals
	 * @return
	 * @throws ParseException
	 */
	public abstract DMLProgram parse(String fileName, String dmlScript, HashMap<String,String> argVals) 
		throws ParseException;
	
	
	/**
	 * Factory method for creating instances of AParserWrapper.
	 * 
	 * @param pydml Is PyDML being parsed?
	 * @param mlContext Is SystemML being invoked from MLContext? 
	 * @return
	 */
	public static AParserWrapper createParser(boolean pydml, boolean mlContext)
	{
		AParserWrapper ret = null;
		
		//create the parser instance
		if( pydml )
			ret = new PyDMLParserWrapper();
		else
			ret = new DMLParserWrapper();
		ret.mlContext = mlContext;
		return ret;
	}
	
	/**
	 * Create a DML or PyDML parser wrapper that is not invoked from MLContext
	 * 
	 * @param pydml
	 * @return
	 */
	public static AParserWrapper createParser(boolean pydml) {
		return createParser(pydml, false);
	}

	/**
	 * 
	 * @return Whether or not SystemML is being invoked from MLContext
	 */
	public boolean isMlContext() {
		return mlContext;
	}
	
	
}
