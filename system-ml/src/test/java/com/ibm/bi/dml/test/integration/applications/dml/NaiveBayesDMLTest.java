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

package com.ibm.bi.dml.test.integration.applications.dml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.ibm.bi.dml.test.integration.applications.NaiveBayesTest;

@RunWith(value = Parameterized.class)
public class NaiveBayesDMLTest extends NaiveBayesTest {

	public NaiveBayesDMLTest(int rows, int cols, int nc, double sp) {
		super(rows, cols, nc, sp);
	}

	@Test
	public void testNaiveBayesDml() {
		testNaiveBayes(ScriptType.DML);
	}

}
