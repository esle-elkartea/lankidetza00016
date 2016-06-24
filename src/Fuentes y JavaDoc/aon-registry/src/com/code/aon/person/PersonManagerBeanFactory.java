package com.code.aon.person;

import com.code.aon.common.BasicManagerBean;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.dao.IDAO;
import com.code.aon.person.dao.PersonDAOFactory;

/**
 * PersonManagerBeanFactory.
 * 
 * @author Consulting & Development. Aimar Tellitu - 27-jun-2005
 */
public class PersonManagerBeanFactory {

	/**
	 * Gets the person manager bean.
	 * 
	 * @return the person manager bean
	 */
	public static IManagerBean getPersonManagerBean() {
		IDAO dao = PersonDAOFactory.getPersonDAO();
		return new BasicManagerBean( dao );
	}
}
