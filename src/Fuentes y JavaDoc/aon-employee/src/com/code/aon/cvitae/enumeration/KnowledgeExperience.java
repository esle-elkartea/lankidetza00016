package com.code.aon.cvitae.enumeration;

import java.util.Locale;
import java.util.ResourceBundle;

import com.code.aon.common.enumeration.IResourceable;

public enum KnowledgeExperience implements IResourceable {
	
	/**
	 * Menos de 1 a�o
	 */
	LESS_ONE_YEAR,
	
	/**
	 * Entre 1 y 2 a�os
	 */
	BETWEEN_ONE_TWO_YEARS,
	
	/**
	 * Entre 2 y 3 a�os
	 */
	BETWEEN_TWO_THREE_YEARS,
	
	/**
	 * Entre 3 y 5 a�os
	 */
	BETWEEN_THREE_FIVE_YEARS,
	
	/**
	 * M�s de 5 a�os
	 */
	MORE_FIVE_YEARS;
	
	/**
     * Ruta base del fichero de mensajes.
     */
    private static final String BASE_NAME = "com.code.aon.cvitae.i18n.messages";

    /**
     * Prefijo de la llave de mensajes. 
     */
    private static final String MSG_KEY_PREFIX = "aon_enum_experience_";
    
    /**
     * Devuelve un <code>String</code> con la traducci�n correspondiente al <code>Locale</code>
     * pasado por par�metro.
     * 
     * @return String un <code>String</code>.
     */
    public String getName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale); 
		return bundle.getString(MSG_KEY_PREFIX + toString());
    }


}
