/*
 * Created on 29-nov-2005
 *
 */
package com.code.aon.planner.core.dao;

import com.code.aon.common.dao.IDAO;
import com.code.aon.common.dao.hibernate.HibernateDAO;

import com.code.aon.planner.core.HumanResource;
import com.code.aon.planner.core.Task;

/**
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 29-nov-2005
 * @since 1.0
 *  
 */
public class PlannerDAOFactory {

    public static IDAO getTaskDAO() {
        return new HibernateDAO(Task.class);
    }

    public static IDAO getHumanResourceDAO() {
        return new HibernateDAO(HumanResource.class);
    }

}
