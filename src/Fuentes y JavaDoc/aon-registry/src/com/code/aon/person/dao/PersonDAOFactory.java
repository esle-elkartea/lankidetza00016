package com.code.aon.person.dao;

import com.code.aon.common.dao.IDAO;
import com.code.aon.common.dao.hibernate.HibernateDAO;
import com.code.aon.person.Person;

/**
 * PersonDAOFactory
 * 
 * @author Consulting & Development. Aimar Tellitu - 07-jun-2005
 * @version 1.0
 * 
 * @since 1.0
 */
public class PersonDAOFactory {

	/**
	 * Gets the person HibernateDAO.
	 * 
	 * @return the company DAO
	 */
	public static IDAO getPersonDAO() {
		return new HibernateDAO( Person.class );
	}

}