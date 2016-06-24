package com.code.aon.planner.test; // $codepro.audit.disable packageJavadoc

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 * @author Consulting & Development. Aimar Tellitu - 08-jun-2005
 * @since 1.0
 *  
 */
public class AppointmentTestSuite {
	
    public static Test suite() {

        TestSuite suite = new TestSuite("Test sobre AON-PLANNER"); //$NON-NLS-1$
        
        suite.addTestSuite( AppointmentTest.class );

        return suite;

    }
}