package com.code.aon.test.calendar; // $codepro.audit.disable packageJavadoc

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 * @author Consulting & Development. Aimar Tellitu - 26-sep-2005
 * @since 1.0
 *  
 */
public class CalendarTestSuite {
	
    public static Test suite() {

        TestSuite suite = new TestSuite("Test sobre AON-CALENDAR"); //$NON-NLS-1$
        
        suite.addTestSuite( iCal4jTest.class );
        suite.addTestSuite( CalendarTest.class );

        return suite;

    }
}