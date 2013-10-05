/**
 * IBM Confidential
 * OCO Source Materials
 * (C) Copyright IBM Corp. 2010, 2013
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */


package com.ibm.bi.dml.runtime.matrix.mapred;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.util.VersionInfo;


/**
 * This class provides a central local for used hadoop configuration properties.
 * For portability, we support both hadoop 1.x and 2.x and automatically map to 
 * the currently used cluster.
 * 
 */
public abstract class MRConfigurationNames 
{
	@SuppressWarnings("unused")
	private static final String _COPYRIGHT = "Licensed Materials - Property of IBM\n(C) Copyright IBM Corp. 2010, 2013\n" +
                                             "US Government Users Restricted Rights - Use, duplication  disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	
	protected static final Log LOG = LogFactory.getLog(MRConfigurationNames.class.getName());
	
	//name definitions
	public static final String INVALID = "null";
	public static String DFS_SESSION_ID = INVALID;
	public static String DFS_BLOCK_SIZE = INVALID;
	public static String DFS_PERMISSIONS = INVALID;

	//initialize to used cluster 
	static{
		
		//determine hadoop version
		//e.g., 2.0.4-alpha from 0a11e32419bd4070f28c6d96db66c2abe9fd6d91 by jenkins source checksum f3c1bf36ae3aa5a6f6d3447fcfadbba
		String version = VersionInfo.getBuildVersion();
		boolean hadoopVersion2 = version.startsWith("2");
		LOG.debug("Hadoop build version: "+version);
		
		if( hadoopVersion2 )
		{
			LOG.debug("Using hadoop 2.x configuration properties.");
			DFS_SESSION_ID  = "dfs.metrics.session-id";
			DFS_BLOCK_SIZE  = "dfs.blocksize";
			DFS_PERMISSIONS = "dfs.permissions.enabled";
		}
		else //any older version
		{
			LOG.debug("Using hadoop 1.x configuration properties.");
			DFS_SESSION_ID  = "session.id";
			DFS_BLOCK_SIZE  = "dfs.block.size";
			DFS_PERMISSIONS = "dfs.permissions";
		}
	}
}
