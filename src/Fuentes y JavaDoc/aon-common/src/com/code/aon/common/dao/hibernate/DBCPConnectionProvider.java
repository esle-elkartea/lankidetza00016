package com.code.aon.common.dao.hibernate;

import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.util.NamingHelper;

/**
 * A strategy for obtaining JDBC connections.
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 20/07/2006
 * 
 */

public class DBCPConnectionProvider implements ConnectionProvider {

	/**
	 * TODO
	 */
	public static final String CONNECTION_SERVICE = "hibernate.connection.service";

	/**
	 * TODO
	 */
	public static final String SECURITY_SUBJECT = "java:comp/env/security/subject";

	/**
	 * TODO
	 */
	public static final String SECURITY_MBEAN = "java:comp/env/SecurityMBean";

	/**
	 * TODO
	 */
	// "Catalina:type=Security,name=AonSecurity" --
	// "jboss.admin:service=AonSecurity"
	public static final String DEFAULT_OBJECT_NAME = "Catalina:type=Security,name=AonSecurity";

	/**
	 * Logger initialization
	 */
	private static final Logger LOG = Logger.getLogger(DBCPConnectionProvider.class.getName());

	/** Data source. */
	private DataSource ds;

	/** User */
	private String user;

	/** Password */
	private String pass;

	/**
	 * Return the {@link DataSource}.
	 * 
	 * @return DataSource.
	 */
	protected DataSource getDataSource() {
		return ds;
	}

	/**
	 * Assign the {@link DataSource}.
	 * 
	 * @param ds
	 */
	protected void setDataSource(DataSource ds) {
		this.ds = ds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.connection.ConnectionProvider#configure(java.util.Properties)
	 */
	public void configure(Properties props) throws HibernateException {
		String jndiName = props.getProperty(Environment.DATASOURCE);
		if (jndiName == null) {
			String msg = "datasource JNDI name was not specified by property " + Environment.DATASOURCE;
			LOG.severe(msg);
			throw new HibernateException(msg);
		}
		user = props.getProperty(Environment.USER);
		pass = props.getProperty(Environment.PASS);

		ds = createDataSource(props);
		LOG.info("Using datasource: " + jndiName + ds.getClass().getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.connection.ConnectionProvider#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		if (user != null || pass != null) {
			return ds.getConnection(user, pass);
		}
		return ds.getConnection();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.connection.ConnectionProvider#closeConnection(java.sql.Connection)
	 */
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.connection.ConnectionProvider#close()
	 */
	public void close() throws HibernateException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see ConnectionProvider#supportsAggressiveRelease()
	 */
	public boolean supportsAggressiveRelease() {
		return true;
	}

	/**
	 * Create DataSource, there are two ways, if the application has a security
	 * policy the it will create from that policy properties, otherwise it will
	 * use <code>connection.datasource</code> property defined in
	 * <code>hibernate.cfg.xml</code> file.
	 * 
	 * @param props
	 * @return DataSource
	 */
	private DataSource createDataSource(Properties props) {
		try {
			try {
				Properties dsProperties = null;
				InitialContext ic = new InitialContext();
				Subject subject = (Subject) ic.lookup(SECURITY_SUBJECT);
				// Obtiene las propiedades de la Fuente de Datos.
				String name = (String) ic.lookup(SECURITY_MBEAN);
				if (name == null)
					name = DEFAULT_OBJECT_NAME;
				ObjectName objectName = new ObjectName(name);
				MBeanServer server = null;
				List servers = MBeanServerFactory.findMBeanServer(null);
				if (servers.size() > 0) {
					server = (MBeanServer) servers.get(0);
				}
				Principal principal = subject.getPrincipals().iterator().next();
				dsProperties = (Properties) server.invoke(objectName, "getDSMDProperties", new Object[] { principal },
						new String[] { Principal.class.getName() });
				// TODO Eliminar la depencia con TOMCAT
				return BasicDataSourceFactory.createDataSource(dsProperties);
			} catch (NamingException ne) {
				String jndiName = props.getProperty(Environment.DATASOURCE);
				DataSource ds;
				try {
					ds = (DataSource) NamingHelper.getInitialContext(props).lookup(jndiName);
				} catch (NamingException e) {
					throw new HibernateException("Could not find datasource", e);
				}
				if (ds == null) {
					throw new HibernateException("Could not find datasource: " + jndiName);
				}
				return ds;
			}
		} catch (Exception e) {
			throw new HibernateException("Could not find connection service", e);
		}
	}

}
