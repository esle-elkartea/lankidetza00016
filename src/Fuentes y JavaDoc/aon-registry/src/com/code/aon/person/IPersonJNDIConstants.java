package com.code.aon.person;

/**
 * JNDI Constants (Java Naming and Directory Interface).
 * 
 * @author Consulting & Development. Aimar Tellitu - 07-jun-2005
 * @since 1.0
 *  
 */
public interface IPersonJNDIConstants {

    /**
     * Constant that constains the <code>javax.sql.DataSource</code> that will be used by DAOs 
     * (Data Access Object).
     */
    String AON_PERSON_DATASOURCE = "java:comp/env/jdbc/AonPerson"; //$NON-NLS-1$

    /**
     * Constant that contains an implementation of <code>com.code.aon.person.IPersonManagerBean</code> 
     * necessary to use entities of <code>com.code.aon.registry</code>
     *  
     */
    String AON_PERSON_MANAGER_BEAN_CLASS = "java:comp/env/AonPersonManagerBeanClass"; //$NON-NLS-1$

}