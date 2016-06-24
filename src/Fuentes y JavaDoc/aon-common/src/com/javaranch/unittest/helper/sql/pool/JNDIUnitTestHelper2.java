package com.javaranch.unittest.helper.sql.pool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * <p>
 * Title: JNDIUnitTestHelper
 * </p>
 * <p>
 * Description: Simple class used to simulate a JNDI DataSource for use in
 * UnitTests
 * </p>
 * Usage is Simple in setUp for your UnitTest:<br>
 * 
 * <pre>
 * if (JNDIUnitTestHelper.notInitialized()) {
 * 	JNDIUnitTestHelper.init(&quot;jndi_unit_test_helper.properties&quot;);
 * }
 * </pre>
 * 
 * <br>
 * Requires the following properties be set by this example:
 * 
 * <pre>
 *  com.javaranch.unittest.helper.sql.pool.JNDIName=java/TestDB
 *  com.javaranch.unittest.helper.sql.pool.dbDriver=org.gjt.mm.mysql.Driver
 *  com.javaranch.unittest.helper.sql.pool.dbServer=jdbc:mysql://localhost/jugsoft
 *  com.javaranch.unittest.helper.sql.pool.DbConnectionBrokerPool.dbLogin=juguser
 *  com.javaranch.unittest.helper.sql.pool.DbConnectionBrokerPool.dbPassword=jugsoft
 * </pre>
 * 
 * <p>
 * Copyright: Copyright (c) 2002
 * </p> - - - - - - - - - - - - - - - - -
 * <p>
 * You are welcome to do whatever you want to with this source file provided
 * that you maintain this comment fragment (between the dashed lines). Modify
 * it, change the package name, change the class name ... personal or business
 * use ... sell it, share it ... add a copyright for the portions you add ...
 * <p>
 * 
 * My goal in giving this away and maintaining the copyright is to hopefully
 * direct developers back to JavaRanch.
 * <p>
 * 
 * The original source can be found at <a
 * href=http://www.javaranch.com>JavaRanch</a>
 * <p>
 *  - - - - - - - - - - - - - - - - -
 * <p>
 * <p>
 * Company: JavaRanch
 * </p>
 * 
 * @author Carl Trusiak, Sheriff
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class JNDIUnitTestHelper2 {

	private static boolean initialized;

	private static String jndiName;

	private JNDIUnitTestHelper2() {
	}

	/**
	 * Intializes the pool and sets it in the InitialContext
	 * 
	 * @param fileName
	 *            Name of the properties file
	 * @param fileName2
	 *            Name of the properties file
	 * @throws IOException
	 * @throws NamingException
	 */
	public static void init(String fileName, String fileName2) throws IOException, NamingException {

		SimpleDataSource source = new SimpleDataSource();
		InputStream is = JNDIUnitTestHelper2.class.getResourceAsStream(fileName);
		// FileInputStream fis = new FileInputStream( fileName );
		Properties props = new Properties();
		props.load(is);
		source.dbDriver = props.getProperty("com.javaranch.unittest.helper.sql.pool.dbDriver");
		source.dbServer = props.getProperty("com.javaranch.unittest.helper.sql.pool.dbServer");
		source.dbLogin = props.getProperty("com.javaranch.unittest.helper.sql.pool.dbLogin");
		source.dbPassword = props.getProperty("com.javaranch.unittest.helper.sql.pool.dbPassword");
		jndiName = props.getProperty("com.javaranch.unittest.helper.sql.pool.JNDIName");
		// Set up environment for creating initial context
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.javaranch.unittest.helper.sql.pool.SimpleContextFactory");
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"com.javaranch.unittest.helper.sql.pool.SimpleContextFactory");
		Context ctx = new InitialContext(env);
		// Register the data source to JNDI naming service
		ctx.bind(jndiName, source);

		SimpleDataSource source2 = new SimpleDataSource();
		InputStream is2 = JNDIUnitTestHelper2.class.getResourceAsStream(fileName2);
		Properties props2 = new Properties();
		props2.load(is2);
		source2.dbDriver = props2.getProperty("com.javaranch.unittest.helper.sql.pool.dbDriver");
		source2.dbServer = props2.getProperty("com.javaranch.unittest.helper.sql.pool.dbServer");
		source2.dbLogin = props2.getProperty("com.javaranch.unittest.helper.sql.pool.dbLogin");
		source2.dbPassword = props2.getProperty("com.javaranch.unittest.helper.sql.pool.dbPassword");
		jndiName = props2.getProperty("com.javaranch.unittest.helper.sql.pool.JNDIName");
		ctx.bind(jndiName, source2);

		initialized = true;
	}

	/**
	 * determines if the pool was successfully initialized or not.
	 * 
	 * @return true if the pool was not successfully initialized.
	 */
	public static boolean notInitialized() {
		return !initialized;
	}

	/**
	 * shutdowns down the pool and ends the Thread that DbConnectionBroker
	 * starts.
	 * 
	 * 
	 * @throws NamingException
	 * 
	 */
	public static void shutdown() throws NamingException {

		InitialContext ctx = new InitialContext();
		ctx.unbind(jndiName);
		initialized = false;
	}

	/**
	 * Gets the name of the datasource, useful in test because this is
	 * configurable for the tests ran.
	 * 
	 * @return String
	 */
	public static String getJndiName() {
		return jndiName;
	}
}
