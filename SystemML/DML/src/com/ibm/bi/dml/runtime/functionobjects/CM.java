/**
 * IBM Confidential
 * OCO Source Materials
 * (C) Copyright IBM Corp. 2010, 2013
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */

package com.ibm.bi.dml.runtime.functionobjects;

import com.ibm.bi.dml.runtime.DMLRuntimeException;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.CM_COV_Object;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.Data;
import com.ibm.bi.dml.runtime.instructions.CPInstructions.KahanObject;
import com.ibm.bi.dml.runtime.matrix.operators.CMOperator.AggregateOperationTypes;


public class CM extends ValueFunction 
{
	@SuppressWarnings("unused")
	private static final String _COPYRIGHT = "Licensed Materials - Property of IBM\n(C) Copyright IBM Corp. 2010, 2013\n" +
                                             "US Government Users Restricted Rights - Use, duplication  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	
	private AggregateOperationTypes _type = null;
	
	//helper function objects for specific types
	private KahanPlus _plus = null;
	private KahanObject _buff2 = null;
	private KahanObject _buff3 = null;
	
	
	private CM( AggregateOperationTypes type ) 
	{
		_type = type;
		
		switch( _type ) //helper obj on demand
		{
			case COUNT:		
				break;
			case CM4:
			case CM3:
				_buff3 = new KahanObject(0, 0);
			case CM2:		
			case VARIANCE:	
				_buff2 = new KahanObject(0, 0);
			case MEAN:
				_plus = KahanPlus.getKahanPlusFnObject();
				break;
		}
	}
	
	public static CM getCMFnObject( AggregateOperationTypes type ) {
		//return new obj, required for correctness in multi-threaded
		//execution due to state in cm object	
		return new CM( type ); 
	}
	
	public Object clone() throws CloneNotSupportedException {
		// cloning is not supported for singleton classes
		throw new CloneNotSupportedException();
	}

	@Override
	public Data execute(Data in1, double in2, double w2) 
		throws DMLRuntimeException 
	{
		CM_COV_Object cm1=(CM_COV_Object) in1;
		
		if(cm1.isCMAllZeros())
		{
			cm1.w=w2;
			cm1.mean.set(in2, 0);
			cm1.m2.set(0,0);
			cm1.m3.set(0,0);
			cm1.m4.set(0,0);
			return cm1;
		}
		
		switch( _type )
		{
			case COUNT:
			{
				cm1.w = (long)cm1.w + (long)w2;
				break;
			}
			case MEAN:
			{
				double w=(long)cm1.w+(long)w2;
				double d=in2-cm1.mean._sum;
				cm1.mean=(KahanObject) _plus.execute(cm1.mean, w2*d/w);
				cm1.w=w;			
				break;
			}
			case CM2:
			{
				double w=(long)cm1.w+(long)w2;
				double d=in2-cm1.mean._sum;
				cm1.mean=(KahanObject) _plus.execute(cm1.mean, w2*d/w);
				double t1=cm1.w*w2/w*d;
				double lt1=t1*d;
				_buff2.set(cm1.m2);
				_buff2=(KahanObject) _plus.execute(_buff2, lt1);
				cm1.m2.set(_buff2);
				cm1.w=w;				
				break;
			}
			case CM3:
			{
				double w=(long)cm1.w+(long)w2;
				double d=in2-cm1.mean._sum;
				cm1.mean=(KahanObject) _plus.execute(cm1.mean, w2*d/w);
				double t1=cm1.w*w2/w*d;
				double t2=-1/cm1.w;
				double lt1=t1*d;
				double lt2=Math.pow(t1, 3)*(1/Math.pow(w2, 2)-Math.pow(t2, 2));
				double f2=w2/w;
				_buff2.set(cm1.m2);
				_buff2=(KahanObject) _plus.execute(_buff2, lt1);
				_buff3.set(cm1.m3);
				_buff3=(KahanObject) _plus.execute(_buff3, lt2-3*cm1.m2._sum*f2*d);
				cm1.m2.set(_buff2);
				cm1.m3.set(_buff3);
				cm1.w=w;
				break;
			}
			case CM4:
			{
				double w=(long)cm1.w+(long)w2;
				double d=in2-cm1.mean._sum;
				cm1.mean=(KahanObject) _plus.execute(cm1.mean, w2*d/w);
				double t1=cm1.w*w2/w*d;
				double t2=-1/cm1.w;
				double lt1=t1*d;
				double lt2=Math.pow(t1, 3)*(1/Math.pow(w2, 2)-Math.pow(t2, 2));
				double lt3=Math.pow(t1, 4)*(1/Math.pow(w2, 3)-Math.pow(t2, 3));
				double f2=w2/w;
				_buff2.set(cm1.m2);
				_buff2=(KahanObject) _plus.execute(_buff2, lt1);
				_buff3.set(cm1.m3);
				_buff3=(KahanObject) _plus.execute(_buff3, lt2-3*cm1.m2._sum*f2*d);
				cm1.m4=(KahanObject) _plus.execute(cm1.m4, 6*cm1.m2._sum*Math.pow(-f2*d, 2) + lt3-4*cm1.m3._sum*f2*d);
				cm1.m2.set(_buff2);
				cm1.m3.set(_buff3);
				cm1.w=w;
				break;
			}
			case VARIANCE:
			{
				double w=(long)cm1.w+(long)w2;
				double d=in2-cm1.mean._sum;
				cm1.mean=(KahanObject) _plus.execute(cm1.mean, w2*d/w);
				double t1=cm1.w*w2/w*d;
				double lt1=t1*d;
				_buff2.set(cm1.m2);
				_buff2=(KahanObject) _plus.execute(_buff2, lt1);
				cm1.m2.set(_buff2);
				cm1.w=w;
				break;
			}
		}
		
		return cm1;
	}
	
	/*
	 //following the SPSS definition.
	public Data execute(Data in1, double in2, double w2) throws DMLRuntimeException {
		CMObject cm=(CMObject) in1;
		double oldweight=cm._weight;
		cm._weight+=w2;
		double v=w2/cm._weight*(in2-cm._mean);
		cm._mean+=v;
		double oldm2=cm._m2;
		double oldm3=cm._m3;
		double oldm4=cm._m4;
		double weightProduct=cm._weight*oldweight;
		double vsquare=Math.pow(v, 2);
		cm._m2=oldm2+weightProduct/w2*vsquare;
		cm._m3=oldm3-3*v*oldm2+weightProduct/Math.pow(w2,2)*(cm._weight-2*w2)*Math.pow(v, 3);
		cm._m4=oldm4-4*v*oldm3+6*vsquare*oldm2
		+((Math.pow(cm._weight, 2)-3*w2*oldweight)/Math.pow(w2,3))*Math.pow(v, 4)*weightProduct;
		return cm;
	}*/

	@Override
	public Data execute(Data in1, Data in2) throws DMLRuntimeException 
	{
		CM_COV_Object cm1=(CM_COV_Object) in1;
		CM_COV_Object cm2=(CM_COV_Object) in2;
		
		if(cm1.isCMAllZeros())
		{
			cm1.w=cm2.w;
			cm1.mean.set(cm2.mean);
			cm1.m2.set(cm2.m2);
			cm1.m3.set(cm2.m3);
			cm1.m4.set(cm2.m4);
			return cm1;
		}
		if(cm2.isCMAllZeros())
			return cm1;
		
		switch( _type )
		{
			case COUNT:
			{
				cm1.w=(long)cm1.w+(long)cm2.w;				
				break;
			}
			case MEAN:
			{
				double w=(long)cm1.w+(long)cm2.w;
				double d=cm2.mean._sum-cm1.mean._sum;
				cm1.mean=(KahanObject) _plus.execute(cm1.mean, cm2.w*d/w);
				cm1.w=w;
				break;
			}
			case CM2:
			{
				double w=(long)cm1.w+(long)cm2.w;
				double d=cm2.mean._sum-cm1.mean._sum;
				cm1.mean=(KahanObject) _plus.execute(cm1.mean, cm2.w*d/w);
				double t1=cm1.w*cm2.w/w*d;
				double lt1=t1*d;
				_buff2.set(cm1.m2);
				_buff2=(KahanObject) _plus.execute(_buff2, cm2.m2._sum, cm2.m2._correction);
				_buff2=(KahanObject) _plus.execute(_buff2, lt1);
				cm1.m2.set(_buff2);
				cm1.w=w;
				break;
			}
			case CM3:
			{
				double w=(long)cm1.w+(long)cm2.w;
				double d=cm2.mean._sum-cm1.mean._sum;
				cm1.mean=(KahanObject) _plus.execute(cm1.mean, cm2.w*d/w);
				double t1=cm1.w*cm2.w/w*d;
				double t2=-1/cm1.w;
				double lt1=t1*d;
				double lt2=Math.pow(t1, 3)*(1/Math.pow(cm2.w, 2)-Math.pow(t2, 2));
				double f1=cm1.w/w;
				double f2=cm2.w/w;
				_buff2.set(cm1.m2);
				_buff2=(KahanObject) _plus.execute(_buff2, cm2.m2._sum, cm2.m2._correction);
				_buff2=(KahanObject) _plus.execute(_buff2, lt1);
				_buff3.set(cm1.m3);
				_buff3=(KahanObject) _plus.execute(_buff3, cm2.m3._sum, cm2.m3._correction);
				_buff3=(KahanObject) _plus.execute(_buff3, 3*(-f2*cm1.m2._sum+f1*cm2.m2._sum)*d + lt2);
				cm1.m2.set(_buff2);
				cm1.m3.set(_buff3);
				cm1.w=w;
				break;
			}
			case CM4:
			{
				double w=(long)cm1.w+(long)cm2.w;
				double d=cm2.mean._sum-cm1.mean._sum;
				cm1.mean=(KahanObject) _plus.execute(cm1.mean, cm2.w*d/w);
				double t1=cm1.w*cm2.w/w*d;
				double t2=-1/cm1.w;
				double lt1=t1*d;
				double lt2=Math.pow(t1, 3)*(1/Math.pow(cm2.w, 2)-Math.pow(t2, 2));
				double lt3=Math.pow(t1, 4)*(1/Math.pow(cm2.w, 3)-Math.pow(t2, 3));
				double f1=cm1.w/w;
				double f2=cm2.w/w;
				_buff2.set(cm1.m2);
				_buff2=(KahanObject) _plus.execute(_buff2, cm2.m2._sum, cm2.m2._correction);
				_buff2=(KahanObject) _plus.execute(_buff2, lt1);
				_buff3.set(cm1.m3);
				_buff3=(KahanObject) _plus.execute(_buff3, cm2.m3._sum, cm2.m3._correction);
				_buff3=(KahanObject) _plus.execute(_buff3, 3*(-f2*cm1.m2._sum+f1*cm2.m2._sum)*d + lt2);
				cm1.m4=(KahanObject) _plus.execute(cm1.m4, cm2.m4._sum, cm2.m4._correction);
				cm1.m4=(KahanObject) _plus.execute(cm1.m4, 4*(-f2*cm1.m3._sum+f1*cm2.m3._sum)*d 
						+ 6*(Math.pow(-f2, 2)*cm1.m2._sum+Math.pow(f1, 2)*cm2.m2._sum)*Math.pow(d, 2) + lt3);				
				cm1.m2.set(_buff2);
				cm1.m3.set(_buff3);
				cm1.w=w;
				break;
			}
			case VARIANCE:
			{
				double w=(long)cm1.w+(long)cm2.w;
				double d=cm2.mean._sum-cm1.mean._sum;
				cm1.mean=(KahanObject) _plus.execute(cm1.mean, cm2.w*d/w);
				double t1=cm1.w*cm2.w/w*d;
				double lt1=t1*d;
				_buff2.set(cm1.m2);
				_buff2=(KahanObject) _plus.execute(_buff2, cm2.m2._sum, cm2.m2._correction);
				_buff2=(KahanObject) _plus.execute(_buff2, lt1);
				cm1.m2.set(_buff2);
				cm1.w=w;
				break;
			}
		}
		
		return cm1;
	}
	/*
	 private double Q(CMObject cm1, CMObject cm2, int power)
	{
		return cm1._weight*Math.pow(cm1._mean,power)+cm2._weight*Math.pow(cm2._mean,power);
	}
	
	//following the SPSS definition, it is wrong
	public Data execute(Data in1, Data in2) throws DMLRuntimeException 
	{
		CMObject cm1=(CMObject) in1;
		CMObject cm2=(CMObject) in2;
		double w=cm1._weight+cm2._weight;
		double q1=cm1._mean*cm1._weight+cm2._mean*cm2._weight;
		double mean=q1/w;
		double p1=mean-cm1._mean;
		double p2=mean-cm2._mean;
		double q2=Q(cm1, cm2, 2);
		double q3=Q(cm1, cm2, 3);
		double q4=Q(cm1, cm2, 4);
		double mean2=Math.pow(mean, 2);
		double mean3=Math.pow(mean, 3);
		double mean4=Math.pow(mean, 4);
		double m2 = cm1._m2+cm2._m2 + q2 - 2*mean*q1 + w*mean2;
		double m3 = cm1._m3+cm2._m3 - 3*(p1*cm1._m2+p2*cm2._m2) 
		- 3*mean*(Math.pow(cm1._mean, 2)+Math.pow(cm2._mean, 2)) + 4*q3 - w*mean3;
		double m4 = cm1._m4+cm2._m4 - 4*(p1*cm1._m3+p2*cm2._m3) + 6*(Math.pow(p1, 2)*cm1._m2+Math.pow(p2, 2)*cm2._m2)-4*q4-4*mean*q3+6*mean2*q2-4*mean3*q1+2*w*mean4;
		cm1._m2=m2;
		cm1._m3=m3;
		cm1._m4=m4;
		cm1._mean=mean;
		cm1._weight=w;
		return cm1;
	}*/

}
