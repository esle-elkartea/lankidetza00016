package com.code.aon.ui.form.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * LookupBean is the class used to implement a Lookup creating an SQL sentence which will be
 * executed to retrive the required data.
 */
public abstract class AbstractLookupBean implements ILookupBean {

	/** Obtains a suitable Logger. */
	private static final Logger LOGGER = Logger.getLogger(AbstractLookupBean.class
			.getName());
	
	/** The foreign columns. */
	private List<String> foreignJoinProperties;

	/** The properties to be displayed. */
	private List<String> foreignDisplayProperties;
	
	/** The alias prefix. */
	private String aliasPrefix;
	
    /** The ids. */
    private String ids;	
    
	/** The list of alias. */    
    private List<String> alias;
    
	/**
	 * The Constructor.
	 */
	public AbstractLookupBean() {
		this.foreignDisplayProperties = Collections.emptyList();
	}

	/**
	 * Sets the foreign table join properties.
	 * 
	 * @param properties the foreign table join properties
	 */
	public void setForeignJoinProperties(List<String> properties) {
		this.foreignJoinProperties = properties;
	}
	
	/**
	 * Gets the foreign table join properties.
	 * 
	 * @return the foreign table join properties
	 */
	protected List<String> getForeignJoinProperties() {
		return foreignJoinProperties;
	}

	/**
	 * Sets the foreign table display columns.
	 * 
	 * @param properties the foreign table display columns
	 */
	public void setForeignDisplayProperties(List<String> properties) {
		this.foreignDisplayProperties = properties;
	}
	
	/**
	 * Gets the foreign table display columns.
	 * 
	 * @return the display properties
	 */
	protected List<String> getForeignDisplayProperties() {
		return foreignDisplayProperties;
	}

	/**
	 * Gets the alias prefix.
	 * 
	 * @return the alias prefix
	 */
	public String getAliasPrefix() {
		return this.aliasPrefix;
	}

	/**
	 * Sets the alias prefix.
	 * 
	 * @param aliasPrefix the alias prefix
	 */
	public void setAliasPrefix(String aliasPrefix) {
		this.aliasPrefix = aliasPrefix;
	}
	
	/**
	 * Gets the alias.
	 * 
	 * @return the alias
	 */
	protected List<String> getAlias() {
		if ( this.alias == null ) {
			this.alias = new ArrayList<String>();
			for( String joinProperty : getForeignJoinProperties() ) {		
				alias.add( this.aliasPrefix + "_" + joinProperty.replace('.', '_') );
			}
			for( String displayProperty : getForeignDisplayProperties() ) {
				alias.add( this.aliasPrefix + "_" + displayProperty.replace('.', '_') );
			}
		}
		return this.alias;
	}
	
	/**
	 * Gets the lookups.
	 * 
	 * @param values the values
	 * 
	 * @return the lookups
	 */
	public Map<String, Object> getLookups( Object[] values ) {
		List<Map<String, Object>> result = resolveLookups( values );
		if (! result.isEmpty() ) {
			if ( result.size() > 1 ) {
				LOGGER.warning( "More than 1 value retrieved for values " + values );
			}
			return result.get(0);
		}
		return null;
	}

	/**
	 * Executes the sql sentence created by <code>getSql</code>.
	 * 
	 * @param values the values
	 * 
	 * @return the list< map< string, object>>
	 */
	protected abstract List<Map<String, Object>> resolveLookups( Object[] values );
	
    /**
     * Execute reset action.
     * 
     * @param event
     */
	protected void onReset(ActionEvent event) {
        throw new NoSuchMethodError("Not implemented");		
	}

    /**
     * Execute search edition action.
     * 
     * @param event
     */
    public void onEditSearch(ActionEvent event) {
        throw new NoSuchMethodError("Not implemented");    	
    }
	
    /**
     * Execute search action.
     * 
     * @param event
     */
	protected void onSearch(ActionEvent event) {
        throw new NoSuchMethodError("Not implemented");		
	}
	
	/**
	 * Gets the ids.
	 * 
	 * @return the ids
	 */
	protected String getIds() {
		return this.ids;
	}

    /**
     * Get involved parameters.
     * 
     * @return boolean
     */
    public boolean getParameters() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String idsValue = (String) ec.getRequestParameterMap().get("ids");
        String typeValue = (String) ec.getRequestParameterMap().get("type");
        boolean hasParameters = (idsValue != null) || (typeValue != null);
        if (hasParameters) {
            this.ids = idsValue;
            LOGGER.fine("ids:[" + ids + "]");
            if ( typeValue != null ) {
            	if ( "new".equals(typeValue) ) {
            		onReset( null );
            	} else if ( "search".equals(typeValue) ) {
            		onEditSearch(null);
            	} else if ( "list".equals(typeValue) ) {            		
                   	onSearch(null);
            	} 
            }
        }
        return false;
    }

    /**
     * Set involved parameters.
     * 
     * @param value
     */
    @SuppressWarnings("unused")
    public void setParameters(boolean value) {
    }
    
}