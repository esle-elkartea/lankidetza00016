package com.code.aon.cvitae.enumeration;

import java.util.Locale;
import java.util.ResourceBundle;

import com.code.aon.common.enumeration.IResourceable;

public enum EvaluationLevel implements IResourceable {
	
	/**
	 * Nulo
	 */
	NULL,
	
	/**
	 * Malo
	 */
	FEW,

	/**
	 * B�sico
	 */
	BASIC,
	
	/**
	 * Medio
	 */
	MEDIUM,
	
	/**
	 * Alto
	 */
	HIGH,
	
	/**
	 * Excelente
	 */
	EXCELENT;
	
	/**
     * Ruta base del fichero de mensajes.
     */
    private static final String BASE_NAME = "com.code.aon.cvitae.i18n.messages";

    /**
     * Prefijo de la llave de mensajes. 
     */
    private static final String MSG_KEY_PREFIX = "aon_enum_evaluate_level_";
    
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
