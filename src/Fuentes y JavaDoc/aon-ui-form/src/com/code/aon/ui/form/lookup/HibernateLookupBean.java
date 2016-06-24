package com.code.aon.ui.form.lookup;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ClassUtils;

import com.code.aon.common.IManagerBean;
import com.code.aon.common.ITransferObject;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.ql.Criteria;
import com.code.aon.ql.util.ExpressionException;
import com.code.aon.ui.common.lookup.LookupUtils;
import com.code.aon.ui.form.BasicController;
import com.code.aon.ui.form.IDataModelDataProvider;


/**
 * LookupBean is the class used to implement a Lookup creating an SQL sentence which will be
 * executed to retrive the required data.
 */
public class HibernateLookupBean extends AbstractLookupBean implements IDataModelDataProvider {

	/** Obtains a suitable Logger. */
	private static final Logger LOGGER = Logger.getLogger(HibernateLookupBean.class
			.getName());
	
	/** The foreign controller. */
	private BasicController controller;
	
	/**
	 * 
	 */
	public HibernateLookupBean() {
		this.controller = new BasicController();
	}

	/**
	 * Sets the foreign pojo.
	 * 
	 * @param foreignPojo the foreign pojo
	 */
	public void setForeignPojo(String foreignPojo) {
		this.controller.setPojo( foreignPojo );
	}

    /**
     * Set the limit of page in the model associated to controller.
     * 
     * @param pageLimit the page limit
     */
    public void setPageLimit(int pageLimit) {
    	this.controller.setPageLimit(pageLimit);
    }

    /**
     * Set if a query will be executed on the model associated to controller when starting up.
     * 
     * @param queryOnStartUP
     */
    public void setQueryOnStartUP(boolean queryOnStartUP) {
    	this.controller.setQueryOnStartUP(queryOnStartUP);
    }

	
	/**
	 * Gets the foreign manager bean.
	 * 
	 * @return the foreign manager bean
	 * 
	 * @throws ManagerBeanException the manager bean exception
	 */
	private IManagerBean getForeignManagerBean() throws ManagerBeanException {
		return this.controller.getManagerBean();
	}
	
	/**
	 * Gets the controller.
	 * 
	 * @return the controller
	 */
	public BasicController getController() {
		return controller;
	}

	/**
	 * Gets the criteria.
	 * 
	 * @param values the values
	 * @param bean the bean
	 * 
	 * @return the criteria
	 * 
	 * @throws ManagerBeanException the manager bean exception
	 * @throws ExpressionException the expression exception
	 */
	private Criteria getCriteria( IManagerBean bean, Object[] values ) throws ManagerBeanException, ExpressionException {
		Criteria criteria = null;
		if ( values != null ) {
			criteria = new Criteria();
			String prefix = ClassUtils.getShortClassName( bean.getPOJOClass() ) + "_";
			for( int i = 0; i < values.length; i++ ) {
				String fieldName = bean.getFieldName( prefix + getForeignJoinProperties().get(i) );
				if ( values[i] != null ) {
					criteria.addExpression( fieldName, values[i].toString() );
				} else {
					criteria.addNullExpression( fieldName );
				}
			}
		}
		return criteria;
	}
	
	/**
	 * Gets the map of ids and object for the Transfer Object <code>to</code>.
	 * 
	 * @param to the to
	 * @param alias the list of alias
	 * 
	 * @return the to map
	 * 
	 * @throws NoSuchMethodException the no such method exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	protected Map<String,Object> getToMap( ITransferObject to, List<String> alias ) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, Object> map = new HashMap<String,Object>();
		int i = 0;
		for( String joinProperty : getForeignJoinProperties() ) {		
			Object value = PropertyUtils.getProperty( to, joinProperty );					
			map.put( alias.get(i++), value );
		}
		for( String displayProperty : getForeignDisplayProperties() ) {
			Object value = PropertyUtils.getProperty( to, displayProperty );					
			map.put( alias.get(i++), value );
		}
		customizeLookupMap(to, map);
		return map;
	}
	
	/**
	 * Executes the sql sentence created by <code>getSql</code>.
	 * 
	 * @param values the values
	 * 
	 * @return the list< map< string, object>>
	 */
	protected List<Map<String, Object>> resolveLookups( Object[] values ) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			IManagerBean bean = getForeignManagerBean();
			Criteria criteria = getCriteria( bean, values ); 
			List<ITransferObject> list = bean.getList(criteria);
			if (list.size() > 0) {
				List<String> alias = getAlias();
				for( ITransferObject to : list ) {
					result.add( getToMap(to, alias) );
				}
			}
		} catch (ManagerBeanException e) {
			LOGGER.severe(e.getMessage());
		} catch (IllegalAccessException e) {
			LOGGER.severe(e.getMessage());
		} catch (InvocationTargetException e) {
			LOGGER.severe(e.getMessage());
		} catch (NoSuchMethodException e) {
			LOGGER.severe(e.getMessage());
		} catch (ExpressionException e) {
			LOGGER.severe(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * Search.
	 * 
	 * @param start the start
	 * @param count the count
	 * 
	 * @return the list< I transfer object>
	 * 
	 * @throws ManagerBeanException the manager bean exception
	 */
	public List<ITransferObject> search(int start, int count) throws ManagerBeanException {
        List<ITransferObject> list = getForeignManagerBean().getList(null, start, count);
        return list;
	}

    /**
     * Executes search action.
     * 
     * @param event
     */
	@Override
	public void onSearch(ActionEvent event) {
    	this.controller.onSearch( event );
    }
    

    /**
     * Return the model associated to controller. The model represents a list of <code>ITransferObject</code> 
     *  with which we will be able to interact.
     * 
     * @return DataModel
     * @throws ManagerBeanException
     */
    public DataModel getModel() throws ManagerBeanException {
    	return this.controller.getModel();
    }
	
	/**
	 * Gets the lookups as XML.
	 * 
	 * @return the lookups as XML
	 * @throws ManagerBeanException 
	 */
	@SuppressWarnings("unchecked")
	public String getLookupsAsXML() throws ManagerBeanException {
		ITransferObject to = (ITransferObject) getModel().getRowData();
		try {		
			Map<String, Object> map = getToMap(to, getAlias());
			return LookupUtils.getResponseXML( map, getIds() );			
		} catch (Throwable th) {
			LOGGER.severe(th.getMessage());
		}
		return null;
	}

	/**
	 * Method used to add entries in the map which can't be added in the method <code>getLookups()</code>
	 * of the ILookupObject.
	 * 
	 * @param to the ILookupObject
	 * @param map the map
	 */
	protected void customizeLookupMap(ITransferObject to, Map<String, Object> map) {
	}
	
}
