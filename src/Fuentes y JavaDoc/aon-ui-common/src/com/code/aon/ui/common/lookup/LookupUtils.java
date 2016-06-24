/*
 * Created on 01-jul-2005
 *
 */
package com.code.aon.ui.common.lookup;

import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * LookupUtils includes common methods used by classes related with the Lookup. 
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 01-jul-2005
 * @since 1.0
 */

public class LookupUtils {

	/**
	 * Return ids as an array.
	 * 
	 * @param ids String whith the ids
	 * 
	 * @return the ids as an array of Strings
	 */
	private static String[] getIds( String ids ) {
		if ( (ids == null) || (ids.trim().length() == 0) ) {
			return null;
		}
		StringTokenizer st = new StringTokenizer( ids, " ," );
		String[] result = new String[st.countTokens()];
		for( int i = 0; st.hasMoreTokens(); i++ ) {
			result[i] = st.nextToken();
		}
		return result;
	}
	
	/**
	 * Checks if the id is a substring of the alias
	 * 
	 * @param ids the ids
	 * @param alias the alias
	 * 
	 * @return the string
	 */
	private static String translate( String[] ids, String alias ) {
		if ( ids != null ) {
			for( String id : ids ) {
				if ( id.indexOf(alias) != -1 ) {
					return id;
				}
			}
            return null;
		}
		return alias;
	}
	
    /**
     * Creates the xml that will be sent in the response
     * 
     * @param ids the ids
     * @param map the map
     * 
     * @return the response XML
     */
    public static final String getResponseXML(Map<String,Object> map, String ids) {
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?><item>");
        String[] idArray = getIds( ids );
        for( Map.Entry<String,Object> entry : map.entrySet() ) {
            String name = translate( idArray, entry.getKey() );
            if ( name != null ) { 
                sb.append( "<entry name=\"");
                sb.append( StringEscapeUtils.escapeXml(name) );
                sb.append( "\" value=\"");
                String value = ( entry.getValue() == null ) ? "" : entry.getValue().toString();
                sb.append( StringEscapeUtils.escapeXml(value) );
                sb.append( "\" />");
            }
        }
        sb.append("</item>");
        return sb.toString();
    }

    /**
     * Gets the response XML.
     * 
     * @param map the map
     * 
     * @return the response XML
     */
    public static final String getResponseXML( Map<String,Object> map ) {
    	return getResponseXML( map, null );
    }

}
