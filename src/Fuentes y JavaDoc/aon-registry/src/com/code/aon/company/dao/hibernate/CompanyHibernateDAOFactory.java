package com.code.aon.company.dao.hibernate;

import com.code.aon.common.dao.IDAO;
import com.code.aon.common.dao.hibernate.HibernateDAO;
import com.code.aon.company.Company;
import com.code.aon.company.dao.CompanyDAOFactory;

/**
 * CompanyHibernateDAOFactory.
 */
public class CompanyHibernateDAOFactory extends CompanyDAOFactory {

	/**
	 * Gets the company HibernateDAO.
	 * 
	 * @return the company DAO
	 */
	@Override
	public IDAO getCompanyDAO() {
		return new HibernateDAO( Company.class );
	}

}

