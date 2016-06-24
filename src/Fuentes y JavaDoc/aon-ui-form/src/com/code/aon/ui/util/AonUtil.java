package com.code.aon.ui.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;

import org.apache.myfaces.shared_impl.util.MessageUtils;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ITransferObject;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.common.dao.hibernate.HibernateUtil;
import com.code.aon.ql.Criteria;
import com.code.aon.ui.common.controller.ConfigurationController;
import com.code.aon.ui.form.IController;

/**
 * AonUtil includes some common methods.
 */
public class AonUtil {

	/** Key to identify an aon error. (value is ""aon_error"") */
	public static final String AON_ERROR = "aon_error";

	/** Obtains a suitable Logger. */
	private static final Logger LOGGER = Logger.getLogger(AonUtil.class.getName());

	/**
	 * Gets the controller registered in <code>faces-bean-config.xml</code>
	 * with that name.
	 * 
	 * @param name
	 *            the name
	 * 
	 * @return the controller
	 */
	public static IController getController(String name) {
		Object o = AonUtil.getRegisteredBean(name);
		if (o instanceof IController) {
			return (IController) o;
		}
		LOGGER.severe(o + " is not a instance of 'com.code.aon.ui.form.IController'");
		return null;
	}

	/**
	 * Gets the bean registered in <code>faces-bean-config.xml</code> with
	 * that name.
	 * 
	 * @param name
	 *            the name
	 * 
	 * @return the registered bean
	 */
	public static Object getRegisteredBean(String name) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueBinding vb = ctx.getApplication().createValueBinding("#{" + name + "}");
		return vb.getValue(ctx);
	}

	/**
	 * Gets the configuration controller.
	 * 
	 * @return the configuration controller
	 */
	public static ConfigurationController getConfigurationController() {
		return AonUtil.getConfigurationController("aonConfiguration");

	}

	/**
	 * Gets the configuration controller.
	 * 
	 * @param name
	 *            the name
	 * 
	 * @return the configuration controller
	 */
	private static ConfigurationController getConfigurationController(String name) {
		return (ConfigurationController) AonUtil.getRegisteredBean(name);
	}

	/**
	 * Gets the SQL connection.
	 * 
	 * @return the SQL connection
	 */
	public static Connection getSQLConnection() {
		return HibernateUtil.getSQLConnection();
	}

	/**
	 * Gets the manager bean related with a ITransferObject.
	 * 
	 * @param to
	 *            the ITransferObject
	 * 
	 * @return the manager bean
	 */
	public static IManagerBean getManagerBean(ITransferObject to) {
		return getManagerBean(to.getClass());
	}

	/**
	 * Gets the manager bean related with a class.
	 * 
	 * @param clazz
	 *            the class
	 * 
	 * @return the manager bean
	 */
	public static IManagerBean getManagerBean(Class clazz) {
		try {
			return BeanManager.getManagerBean(clazz);
		} catch (ManagerBeanException e) {
			LOGGER.severe(clazz + " has not valid IManagerBean registered!");
		}
		return null;
	}

	/**
	 * Return the login name of the user making the current request if any;
	 * otherwise, return <code>null</code>.
	 * 
	 * @return the remote user
	 */
	public static String getRemoteUser() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext ec = ctx.getExternalContext();
		return ec.getRemoteUser();
	}

	/**
	 * Return true if the currently authenticated user is included in the
	 * specified role. Otherwise, return <code>false</code>.
	 * 
	 * @param role
	 *            Logical role name to be checked.
	 * 
	 * @return the remote user
	 */
	public static boolean isUserInRole(String role) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext ec = ctx.getExternalContext();
		return ec.isUserInRole(role);
	}

	/**
	 * Gets the context path.
	 * 
	 * @return the context path
	 */
	public static String getContextPath() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext ec = ctx.getExternalContext();
		return ec.getRequestContextPath();
	}

	// direct MI invocation
	/**
	 * Prerares a controller for create new objects
	 * 
	 * @param controller
	 *            the controller
	 * 
	 * @throws ManagerBeanException
	 *             the manager bean exception
	 */
	public static void prepareControllerForEditNew(IController controller) throws ManagerBeanException {
		controller.onReset(null);
	}

	/**
	 * Prepares a controller for search objects.
	 * 
	 * @param controller
	 *            the controller
	 * 
	 * @throws ManagerBeanException
	 *             the manager bean exception
	 */
	public static void prepareControllerForEditSearch(IController controller) throws ManagerBeanException {
		controller.onEditSearch(null);
	}

	/**
	 * Prepare controller for display a list.
	 * 
	 * @param keyCriteria
	 *            the key criteria
	 * @param controller
	 *            the controller
	 * 
	 * @throws ManagerBeanException
	 *             the manager bean exception
	 */
	public static void prepareControllerForDisplayList(IController controller, Criteria keyCriteria)
			throws ManagerBeanException {
		controller.setCriteria(keyCriteria);
		controller.onSearch(null);
		controller.getModel().setRowIndex(0);
	}

	/**
	 * Prepare controller for modify an object.
	 * 
	 * @param keyCriteria
	 *            the key criteria
	 * @param controller
	 *            the controller
	 * 
	 * @throws ManagerBeanException
	 *             the managerBeanException
	 */
	public static void prepareControllerForEditModify(IController controller, Criteria keyCriteria)
			throws ManagerBeanException {
		controller.setCriteria(keyCriteria);
		controller.onSearch(null);
		controller.getModel().setRowIndex(0);
		controller.onSelect(null);
	}

	/**
	 * Gets the sql value.
	 * 
	 * @param sqlQuery
	 *            the sql query
	 * 
	 * @return the sql value
	 */
	@Deprecated
	// Will be removed in next Version.
	public static String getSqlValue(String sqlQuery) {
		String[] values = getSqlValues(sqlQuery);
		if (values != null && values.length > 0) {
			return values[0];
		}
		return null;
	}

	/**
	 * Gets the sql values.
	 * 
	 * @param sqlQuery
	 *            the sql query
	 * 
	 * @return the sql values
	 */
	@Deprecated
	// Will be removed in next Version.
	public static String[] getSqlValues(String sqlQuery) {
		Connection conn = AonUtil.getSQLConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String[] values = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			if (rs.next()) {
				values = new String[rs.getMetaData().getColumnCount()];
				for (int i = 0; i < values.length; i++) {
					values[i] = rs.getString(i + 1);
				}
			}
		} catch (SQLException e) {
			throw new AbortProcessingException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return values;
	}

	/**
	 * Adds <code>message</code> to the messages collection
	 * 
	 * @param message
	 *            Message to be added to the messages collection
	 */

	public static void addFatalMessage(String message) {
		String[] args = { message };
		MessageUtils.addMessage(FacesMessage.SEVERITY_FATAL, AonUtil.AON_ERROR, args);
	}

	/**
	 * Adds <code>message</code> as an error message to the messages
	 * collection
	 * 
	 * @param message
	 *            the message
	 */
	public static void addErrorMessage(String message) {
		String[] args = { message };
		MessageUtils.addMessage(FacesMessage.SEVERITY_ERROR, AonUtil.AON_ERROR, args);
	}

	/**
	 * Adds <code>message</code> as an info message to the messages collection
	 * 
	 * @param message
	 *            the message
	 */
	public static void addInfoMessage(String message) {
		String[] args = { message };
		MessageUtils.addMessage(FacesMessage.SEVERITY_INFO, AonUtil.AON_ERROR, args);
	}

	/**
	 * Adds <code>message</code> as an warning message to the messages
	 * collection
	 * 
	 * @param message
	 *            the message
	 */
	public static void addWarningMessage(String message) {
		String[] args = { message };
		MessageUtils.addMessage(FacesMessage.SEVERITY_WARN, AonUtil.AON_ERROR, args);
	}

}
