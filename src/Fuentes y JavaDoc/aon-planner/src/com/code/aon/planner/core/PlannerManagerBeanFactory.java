/*
 * Created on 29-nov-2005
 *
 */
package com.code.aon.planner.core;

import com.code.aon.common.BasicManagerBean;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.dao.IDAO;

import com.code.aon.planner.core.dao.PlannerDAOFactory;

/**
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 29-nov-2005
 * @since 1.0
 *  
 */
public class PlannerManagerBeanFactory {

	public static IManagerBean getTaskManagerBean() {
		IDAO dao = PlannerDAOFactory.getTaskDAO();
		return new BasicManagerBean( dao );
	}

    public static IManagerBean getHumanResourceManagerBean() {
        IDAO dao = PlannerDAOFactory.getHumanResourceDAO();
        return new BasicManagerBean( dao );
    }
}
