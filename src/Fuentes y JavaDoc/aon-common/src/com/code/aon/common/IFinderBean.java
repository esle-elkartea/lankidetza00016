package com.code.aon.common;

import java.io.Serializable;
import java.util.List;

import com.code.aon.ql.Criteria;

/**
 * Interface that allows search operations over the entities of the model.
 * 
 * @author Consulting & Development. Aimar Tellitu - 27-jun-2005
 * @since 1.0
 *  
 */
public interface IFinderBean {

    /**
     * Get a <code>Collection</code> of Transfer Objects.
     * 
     * @param criteria Search filter.
     * @return The <code>Collection</code>.
     * @throws ManagerBeanException if an unexpected error occurs.
     */
    List<ITransferObject> getList(Criteria criteria) throws ManagerBeanException;

    /**
     * Get a <code>Collection</code> of Transfer Objects.
     * 
     * @param criteria Search filter.
     * @param offset int
     * @param count int
     * @return the <code>Collection</code>.
     * @throws ManagerBeanException if an unexpected error occurs.
     */
    List<ITransferObject> getList(Criteria criteria,int offset,int count) throws ManagerBeanException;

    /**
     * Return database field name bound to the alias passed by parameter.
     * 
     * @param alias Field name used in the view.
     * @return String.
     * @throws ManagerBeanException if an unexpected error occurs.
     */
    String getFieldName(String alias) throws ManagerBeanException;

    
    /**
     * Return the number of rows.
     * 
     * @param criteria Search filter.
     * @return int
     * @throws ManagerBeanException if an unexpected error occurs.
     */
    int getCount(Criteria criteria) throws ManagerBeanException;

	/**
	 * Return the Class of this POJO(Plain Old Java Object).
	 * 
	 * @return The POJO Class.
	 */
	Class getPOJOClass();

	/**
	 * Return the <code>ITransferObject</code>.
	 * 
	 * @param pk Transfer Object Primary Key.
	 * @return ITransferObject.
     * @throws ManagerBeanException if an unexpected error occurs.
	 */
	ITransferObject get(Serializable pk) throws ManagerBeanException;

	/**
	 * Return the <code>Serializable</code> Primary Key.
	 * 
	 * @param to Transfer Object.
	 * @return Serializable.
     * @throws ManagerBeanException if an unexpected error occurs.
	 */
	Serializable getId(ITransferObject to) throws ManagerBeanException;

	/**
	 * Set the Transfer Object Primary Key <code>pk</code>.
	 * 
	 * @param to Transfer Object.
	 * @param id Primary Key.
     * @throws ManagerBeanException if an unexpected error occurs.
	 */
	void setId(ITransferObject to, Serializable id) throws ManagerBeanException;
}