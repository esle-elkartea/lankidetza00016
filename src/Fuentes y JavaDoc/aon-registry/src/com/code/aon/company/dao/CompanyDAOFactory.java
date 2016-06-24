/*
 * Created on 16-may-2005
 *
 */
package com.code.aon.company.dao;

import com.code.aon.common.dao.DAOFactory;
import com.code.aon.common.dao.IDAO;
import com.code.aon.company.ICompanyJNDIConstants;

/**
 * CompanyDAOFactory.
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 15-nov-2005
 * @since 1.0
 */

public abstract class CompanyDAOFactory {

	/** DAO_FACTORY. */
	private static CompanyDAOFactory DAO_FACTORY;

	/**
	 * Gets an instance of <code>CompanyDAOFactory</code>.
	 * 
	 * @return the instance
	 */
	public static CompanyDAOFactory getInstance() {
		if (DAO_FACTORY == null) {
			DAO_FACTORY = (CompanyDAOFactory) DAOFactory
					.getDAOFactory(ICompanyJNDIConstants.AON_COMPANY_DAO_FACTORY_CLASS);
		}
		return DAO_FACTORY;
	}

	/**
	 * Gets the company DAO.
	 * 
	 * @return the company DAO
	 */
	public abstract IDAO getCompanyDAO();
	
}
