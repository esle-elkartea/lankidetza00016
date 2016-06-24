package com.code.aon.common.dao;

import java.util.Iterator;
import java.util.Map;

import org.hibernate.type.Type;

import com.code.aon.common.AbstractFieldMapper;
import com.code.aon.common.dao.hibernate.HibernateUtil;

/**
 * This Class manages information of an IDAO constants stored in a file.
 * 
 * @author Consulting & Development. Aimar Tellitu - 29-jun-2005
 * @since 1.0
 * 
 */
public class DAOConstantsEntry {

	private String pojo;

	private String name;

	private DAOConstantsEntry parent;

	private String[] beanAliasNames;
	
	private String[] aliasNames;

	private String[] sqlNames;

	private String[] hibernateNames;

	private Map<String,Type> propertyTypes;

	private Map<String,String> sqlMap;

	private Map<String,String> hibernateMap;

	/**
	 * Constructor using POJO Class name.
	 * 
	 * @param pojo
	 * @param parentClass
	 */
	public DAOConstantsEntry(String pojo, String parentClass) {
		this.pojo = pojo;
		this.name = calculateName(pojo);
		if (parentClass != null) {
			this.parent = DAOConstants.getDAOConstant(parentClass);
		}
	}

	/**
	 * Assign Hibernate names.
	 * 
	 * @param hibernateNames
	 *            The hibernateNames to set.
	 */
	public void setHibernateNames(String[] hibernateNames) {
		this.hibernateNames = hibernateNames;
	}

	/**
	 * Assign SQL names.
	 * 
	 * @param sqlNames
	 *            The sqlNames to set.
	 */
	public void setSqlNames(String[] sqlNames) {
		this.sqlNames = sqlNames;
	}

	/**
	 * Assign alias names.
	 * 
	 * @param alias
	 *            The alias to set.
	 */
	public void setBeanAliasNames(String[] alias) {
		this.beanAliasNames = alias;
	}

	/**
	 * Calculate field name.
	 * 
	 * @param value
	 * @return Calculate field name.
	 */
	private String calculateName(String value) {
		int pos = value.lastIndexOf('.');
		if (pos != -1) {
			return value.substring(pos + 1);
		}
		return value;
	}

	/**
	 * @return Returns the bean.
	 */
	public String getPojo() {
		return this.pojo;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the parent.
	 */
	public DAOConstantsEntry getParent() {
		return this.parent;
	}

	/**
	 * @return Returns the hibernateMap.
	 */
	public Map<String,String> getHibernateMap() {
		if (hibernateMap == null) {
			hibernateMap = AbstractFieldMapper.getMap( this.beanAliasNames, hibernateNames);
			if (parent != null) {
				addParentMap(hibernateMap, parent.getHibernateMap(), true);
			}
		}
		return hibernateMap;
	}

	/**
	 * @return Returns the sqlMap.
	 */
	public Map<String,String> getSqlMap() {
		if (sqlMap == null) {
			sqlMap = AbstractFieldMapper.getMap( this.beanAliasNames, sqlNames);
			if (parent != null) {
				addParentMap(sqlMap, parent.getSqlMap(), false);
			}
		}
		return sqlMap;
	}

	/**
	 * @return Returns the propertyTypes.
	 */
	public Map<String,Type> getPropertyTypes() {
		if (propertyTypes == null) {
			propertyTypes = HibernateUtil.getMappingTypes(pojo, hibernateNames);
			if (parent != null) {
				addParentMap( propertyTypes, parent.getPropertyTypes() );
			}
		}
		return propertyTypes;
	}

	/**
	 * Return an Array of alias names.
	 * 
	 * @return An Array of alias names.
	 */
	public String[] getAliasNames() {
		if ( this.aliasNames == null ) {
			if ( this.parent != null ) {
				String[] parentAlias = this.parent.getAliasNames();
				int size = (beanAliasNames != null ? beanAliasNames.length : 0) + parentAlias.length;
				this.aliasNames = new String[size];
				int pos = parent.getName().length();
				for( int i = 0; i < parentAlias.length; i++ ) {
					this.aliasNames[i] = this.name + parentAlias[i].substring(pos);
				}
				if ( this.beanAliasNames != null ) {
					System.arraycopy( this.beanAliasNames, 0, this.aliasNames, parentAlias.length, this.beanAliasNames.length );
				}
			} else {
				this.aliasNames = this.beanAliasNames;
			}
		}
		return this.aliasNames;
	}

	private void addParentMap(Map<String,String> map, Map<String,String> parentMap, boolean changeValue) {
		int pos = this.parent.getName().length();
		Iterator<Map.Entry<String,String>> i = parentMap.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<String,String> entry = i.next();
			String key = entry.getKey();
			String newKey = this.name + key.substring(pos);
			String value = entry.getValue();
			if ( changeValue ) {
				value = this.name + value.substring(pos);
			}
			map.put( newKey, value );
		}
	}

	private void addParentMap(Map<String,Type> map, Map<String,Type> parentMap) {
		int pos = this.parent.getName().length();
		Iterator<Map.Entry<String,Type>> i = parentMap.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<String,Type> entry = i.next();
			String key = entry.getKey();
			String newKey = this.name + key.substring(pos);
			map.put( newKey, entry.getValue() );
		}
	}
	
}