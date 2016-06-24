package com.javaranch.unittest.helper.sql.pool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
 *      com.javaranch.unittest.helper.sql.pool.JNDIName=java/TestDB
 *      com.javaranch.unittest.helper.sql.pool.dbDriver=org.gjt.mm.mysql.Driver
 *      com.javaranch.unittest.helper.sql.pool.dbServer=jdbc:mysql://localhost/jugsoft
 *      com.javaranch.unittest.helper.sql.pool.DbConnectionBrokerPool.dbLogin=juguser
 *      com.javaranch.unittest.helper.sql.pool.DbConnectionBrokerPool.dbPassword=jugsoft
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
 * <p> - - - - - - - - - - - - - - - - -
 * <p>
 * <p>
 * Company: JavaRanch
 * </p>
 * 
 * @author Carl Trusiak, Sheriff
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class JNDIUnitTestHelper {

	private static final String PREFIX = "java.com.env.";

	private static final String DB_DRIVER = "com.javaranch.unittest.helper.sql.pool.dbDriver";

	private static final String DB_SERVER = "com.javaranch.unittest.helper.sql.pool.dbServer";

	private static final String DB_LOGIN = "com.javaranch.unittest.helper.sql.pool.dbLogin";

	private static final String DB_PASSWORD = "com.javaranch.unittest.helper.sql.pool.dbPassword";

	private static final String JNDI_NAME = "com.javaranch.unittest.helper.sql.pool.JNDIName";

	private static final String CONTEXT_FACTORY = "com.javaranch.unittest.helper.sql.pool.SimpleContextFactory";

	private static boolean initialized;

	private static List<String> jndiNames;

	private JNDIUnitTestHelper() {
	}

	/**
	 * Intializes the pool and sets it in the InitialContext
	 * 
	 * @param fileName
	 *            Name of the properties file
	 * @throws IOException
	 * @throws NamingException
	 */
	public static void init(String fileName) throws IOException, NamingException {

		InputStream is = JNDIUnitTestHelper.class.getResourceAsStream(fileName);
		// FileInputStream fis = new FileInputStream( fileName );
		Properties props = new Properties();
		props.load(is);
		// Set up environment for creating initial context
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
		Context ctx = new InitialContext(env);
		// Register the data source to JNDI naming service
		String suffix = "";
		int index = 1;
		jndiNames = new ArrayList<String>();
		while (index != -1) {
			SimpleDataSource source = new SimpleDataSource();
			source.dbDriver = props.getProperty(DB_DRIVER + suffix);
			source.dbServer = props.getProperty(DB_SERVER + suffix);
			source.dbLogin = props.getProperty(DB_LOGIN + suffix);
			source.dbPassword = props.getProperty(DB_PASSWORD + suffix);
			String jndiName = props.getProperty(JNDI_NAME + suffix);
			jndiNames.add(jndiName);
			ctx.bind(jndiName, source);
			suffix = "." + index++;
			index = (props.getProperty(DB_DRIVER + suffix) != null) ? index : -1;
		}
		bindProperties(props, ctx);
		initialized = true;
	}

	private static void bindProperties(Properties properties, Context context) throws NamingException {
		Iterator i = properties.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry entry = (Map.Entry) i.next();
			String key = (String) entry.getKey();
			if (key.startsWith(PREFIX)) {
				String name = "java:comp/env/" + key.substring(PREFIX.length());
				String value = (String) entry.getValue();
				context.bind(name, value);
			}
		}
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
		for (String jndiName : getJndiNames()) {
			ctx.unbind(jndiName);
		}
		initialized = false;
	}

	/**
	 * Gets the name of the datasource, useful in test because this is
	 * configurable for the tests ran.
	 * 
	 * @return List<String>
	 */
	public static List<String> getJndiNames() {
		return jndiNames;
	}
}
