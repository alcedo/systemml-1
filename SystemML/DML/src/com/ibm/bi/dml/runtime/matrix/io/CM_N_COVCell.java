/**
 * IBM Confidential
 * OCO Source Materials
 * (C) Copyright IBM Corp. 2010, 2013
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */


package com.ibm.bi.dml.runtime.matrix.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.io.WritableComparable;

import com.ibm.bi.dml.runtime.DMLRuntimeException;
import com.ibm.bi.dml.runtime.DMLUnsupportedOperationException;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.CM_COV_Object;
import com.ibm.bi.dml.runtime.instructions.MRInstructions.RangeBasedReIndexInstruction.IndexRange;
import com.ibm.bi.dml.runtime.matrix.mapred.IndexedMatrixValue;
import com.ibm.bi.dml.runtime.matrix.operators.AggregateBinaryOperator;
import com.ibm.bi.dml.runtime.matrix.operators.AggregateOperator;
import com.ibm.bi.dml.runtime.matrix.operators.AggregateUnaryOperator;
import com.ibm.bi.dml.runtime.matrix.operators.BinaryOperator;
import com.ibm.bi.dml.runtime.matrix.operators.Operator;
import com.ibm.bi.dml.runtime.matrix.operators.ReorgOperator;
import com.ibm.bi.dml.runtime.matrix.operators.ScalarOperator;
import com.ibm.bi.dml.runtime.matrix.operators.UnaryOperator;

public class CM_N_COVCell extends MatrixValue implements WritableComparable
{
	@SuppressWarnings("unused")
	private static final String _COPYRIGHT = "Licensed Materials - Property of IBM\n(C) Copyright IBM Corp. 2010, 2013\n" +
                                             "US Government Users Restricted Rights - Use, duplication  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	
	private CM_COV_Object cm=new CM_COV_Object();
	
	public String toString()
	{
		return cm.toString();
	}
	
	@Override
	public void addValue(int r, int c, double v) {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public MatrixValue aggregateBinaryOperations(MatrixValue m1Value,
			MatrixValue m2Value, MatrixValue result, AggregateBinaryOperator op)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public MatrixValue aggregateUnaryOperations(AggregateUnaryOperator op,
			MatrixValue result, int brlen, int bclen, MatrixIndexes indexesIn)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public MatrixValue binaryOperations(BinaryOperator op,
			MatrixValue thatValue, MatrixValue result)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void binaryOperationsInPlace(BinaryOperator op, MatrixValue thatValue)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");		
	}

	@Override
	public void copy(MatrixValue that, boolean sp) {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void copy(MatrixValue that) {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void getCellValues(Collection<Double> ret) {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void getCellValues(Map<Double, Integer> ret) {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public int getMaxColumn() throws DMLRuntimeException {
		return 1;
	}

	@Override
	public int getMaxRow() throws DMLRuntimeException {
		return 1;
	}

	@Override
	public int getNonZeros() {
		return 1;
	}

	@Override
	public int getNumColumns() {
		return 1;
	}

	@Override
	public int getNumRows() {
		return 1;
	}

	@Override
	public double getValue(int r, int c) {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void incrementalAggregate(AggregateOperator aggOp,
			MatrixValue correction, MatrixValue newWithCorrection)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}
	
	@Override
	public void incrementalAggregate(AggregateOperator aggOp,
			MatrixValue newWithCorrection)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public boolean isInSparseFormat() {
		return false;
	}

	@Override
	public MatrixValue reorgOperations(ReorgOperator op, MatrixValue result,
			int startRow, int startColumn, int length)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void reset() {
	}

	@Override
	public void reset(int rl, int cl) {
		
	}

	@Override
	public void reset(int rl, int cl, boolean sp) {
	}
	
	public void reset(int rl, int cl, boolean sp, int nnzs)
	{
	}

	@Override
	public void resetDenseWithValue(int rl, int cl, double v) {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public MatrixValue scalarOperations(ScalarOperator op, MatrixValue result)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void scalarOperationsInPlace(ScalarOperator op)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void setMaxColumn(int c) throws DMLRuntimeException {
		
	}

	@Override
	public void setMaxRow(int r) throws DMLRuntimeException {
		
	}

	@Override
	public void setValue(int r, int c, double v) {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void setValue(CellIndex index, double v) {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public MatrixValue unaryOperations(UnaryOperator op, MatrixValue result)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void unaryOperationsInPlace(UnaryOperator op)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		cm.w=in.readDouble();
		cm.mean.read(in);
		cm.m2.read(in);
		cm.m3.read(in);
		cm.m4.read(in);
		cm.mean_v.read(in);
		cm.c2.read(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(cm.w);
		cm.mean.write(out);
		cm.m2.write(out);
		cm.m3.write(out);
		cm.m4.write(out);
		cm.mean_v.write(out);
		cm.c2.write(out);
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof CM_N_COVCell)
		{
			CM_N_COVCell that=(CM_N_COVCell)o;
			return cm.compareTo(that.cm);
		}
		return -1;
	}
	
	public CM_COV_Object getCM_N_COVObject()
	{
		return cm;
	}

	public void setCM_N_COVObject(double u, double v, double w)
	{
		cm.w=w;
		cm.mean.set(u,0);
		cm.mean_v.set(v, 0);
		cm.m2.set(0,0);
		cm.m3.set(0,0);
		cm.m4.set(0,0);
		cm.c2.set(0,0);
	}
	public void setCM_N_COVObject(CM_COV_Object that)
	{
		cm.set(that);
	}

	@Override
	public MatrixValue zeroOutOperations(MatrixValue result, IndexRange range, boolean complementary)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void tertiaryOperations(Operator op, MatrixValue that,
			MatrixValue that2,
			HashMap<CellIndex, Double> ctableResult)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
		
	}

	@Override
	public void tertiaryOperations(Operator op, MatrixValue that,
			double scalarThat2,
			HashMap<CellIndex, Double> ctableResult)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void tertiaryOperations(Operator op, double scalarThat,
			double scalarThat2,
			HashMap<CellIndex, Double> ctableResult)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void tertiaryOperations(Operator op, double scalarThat,
			MatrixValue that2,
			HashMap<CellIndex, Double> ctableResult)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void slideOperations(ArrayList<IndexedMatrixValue> outlist,
			IndexRange range, int rowCut, int colCut, int blockRowFactor,
			int blockColFactor, int boundaryRlen, int boundaryClen)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
		
	}

	@Override
	public MatrixValue aggregateUnaryOperations(AggregateUnaryOperator op,
			MatrixValue result, int blockingFactorRow, int blockingFactorCol,
			MatrixIndexes indexesIn, boolean inCP)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public MatrixValue aggregateBinaryOperations(MatrixIndexes m1Index,
			MatrixValue m1Value, MatrixIndexes m2Index, MatrixValue m2Value,
			MatrixValue result, AggregateBinaryOperator op, boolean partialMult)
			throws DMLUnsupportedOperationException, DMLRuntimeException {
		// TODO Auto-generated method stub
		throw new RuntimeException("operation not supported fro WeightedCell");
	}

	@Override
	public void appendOperations(MatrixValue valueIn2, ArrayList<IndexedMatrixValue> outlist,
			int blockRowFactor, int blockColFactor, boolean m2IsLast, int nextNCol)
	throws DMLUnsupportedOperationException, DMLRuntimeException   {
		throw new RuntimeException("operation not supported fro WeightedCell");
	}
}
