/**
 * IBM Confidential
 * OCO Source Materials
 * (C) Copyright IBM Corp. 2010, 2013
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */

package com.ibm.bi.dml.test.integration.functions.aggregate;

import org.junit.Test;

import com.ibm.bi.dml.parser.LanguageException;
import com.ibm.bi.dml.test.integration.AutomatedTestBase;
import com.ibm.bi.dml.test.integration.TestConfiguration;


/**
 * <p><b>Positive tests:</b></p>
 * <ul>
 *  <li>general test</li>
 * </ul>
 * <p><b>Negative tests:</b></p>
 * <ul>
 *  <li>scalar test</li>
 * </ul>
 * 
 * 
 */
public class NColTest extends AutomatedTestBase 
{
	@SuppressWarnings("unused")
	private static final String _COPYRIGHT = "Licensed Materials - Property of IBM\n(C) Copyright IBM Corp. 2010, 2013\n" +
                                             "US Government Users Restricted Rights - Use, duplication  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	
    private final static String TEST_DIR = "functions/aggregate/";
    private final static String TEST_GENERAL = "General";
    private final static String TEST_SCALAR = "Scalar";


    @Override
    public void setUp() {
        // positive tests
        addTestConfiguration(TEST_GENERAL, new TestConfiguration(TEST_DIR, "NColTest", new String[] { "vector_cols",
                "matrix_cols" }));
        
        // negative tests
        addTestConfiguration(TEST_SCALAR, new TestConfiguration(TEST_DIR, "NColScalarTest", new String[] {
                "vector_cols", "matrix_cols" }));
    }

    @Test
    public void testGeneral() {
        int rows = 10;
        int cols = 10;

        TestConfiguration config = getTestConfiguration(TEST_GENERAL);
        config.addVariable("rows", rows);
        config.addVariable("cols", cols);

        loadTestConfiguration(TEST_GENERAL);

        createHelperMatrix();
        double[][] vector = getRandomMatrix(rows, 1, 0, 1, 1, -1);
        writeInputMatrix("vector", vector);
        writeExpectedHelperMatrix("vector_cols", 1);

        double[][] matrix = getRandomMatrix(rows, cols, 0, 1, 1, -1);
        writeInputMatrix("matrix", matrix);
        writeExpectedHelperMatrix("matrix_cols", cols);

        runTest();

        compareResults();
    }

    @Test
    public void testScalar() {
        int scalar = 12;

        TestConfiguration config = getTestConfiguration(TEST_SCALAR);
        config.addVariable("scalar", scalar);

        createHelperMatrix();

        loadTestConfiguration(TEST_SCALAR);

        runTest(true, LanguageException.class);
    }

}
