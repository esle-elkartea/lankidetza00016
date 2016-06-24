/*
 * Created on 29-nov-2005
 *
 */
package com.code.aon.planner.core.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;

import com.code.aon.planner.core.HumanResource;
import com.code.aon.planner.core.Task;

/**
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 29-nov-2005
 * @since 1.0
 *  
 */
public interface IPlannerAlias {

	DAOConstantsEntry TASK_ENTRY = DAOConstants.getDAOConstant(Task.class);
	
    String TASK_ID = TASK_ENTRY.getAliasNames()[0];
    
    String TASK_TITLE = TASK_ENTRY.getAliasNames()[1];
    
    String TASK_START_DATE = TASK_ENTRY.getAliasNames()[2];
    
    String TASK_DUE_DATE = TASK_ENTRY.getAliasNames()[3];
    
    String TASK_PRIORITY = TASK_ENTRY.getAliasNames()[4];
    
    String TASK_STATUS = TASK_ENTRY.getAliasNames()[5];
    
    String TASK_PERCENT = TASK_ENTRY.getAliasNames()[6];
    
    String TASK_OWNER = TASK_ENTRY.getAliasNames()[7];
    
    String TASK_ALARM_DATE = TASK_ENTRY.getAliasNames()[8];
    
    DAOConstantsEntry HUMAN_RESOURCE_ENTRY = DAOConstants.getDAOConstant(HumanResource.class);
    
    String HUMAN_RESOURCE_TASK = HUMAN_RESOURCE_ENTRY.getAliasNames()[0];

    String HUMAN_RESOURCE_REGISTRY = HUMAN_RESOURCE_ENTRY.getAliasNames()[1];

    String HUMAN_RESOURCE_ENTRY_DATE = HUMAN_RESOURCE_ENTRY.getAliasNames()[2];

}
