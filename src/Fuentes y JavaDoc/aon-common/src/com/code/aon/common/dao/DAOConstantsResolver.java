package com.code.aon.common.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;

/**
 * This Class manages operations of IDAO constants.
 * 
 * @author Consulting & Development. 
 *
 */
public class DAOConstantsResolver {

	private Configuration configuration;

	/**
	 * Constructor using Hibernate configuration.
	 * 
	 * @param configuration
	 */
	public DAOConstantsResolver( Configuration configuration ) {
		this.configuration = configuration;
	}

	/**
	 * Create a new DAO Constants.
	 *
	 */
	public void createDAOConstants() {
		Iterator i = this.configuration.getClassMappings();
		while ( i.hasNext() ) {
			PersistentClass persistentClass = (PersistentClass) i.next();
			createDAOConstant( persistentClass );
		}
	}
	
	private void createDAOConstant( PersistentClass persistentClass ) {
		if ( persistentClass.isInherited() ) {
			createDAOConstant( persistentClass.getSuperclass() );
		}
		String pojo = persistentClass.getEntityName();
		DAOConstantsEntry entry = DAOConstants.createDAOConstant( pojo );
		if ( entry == null ) {
			entry = resolve( persistentClass );
			DAOConstants.addBeanEntry( entry );
		}
	}

	private PersistentClass getPersistentClass( String pojo ) {
		return this.configuration.getClassMapping( pojo );
	}
	
	private String getParent( PersistentClass pc ) {
		if ( pc.isInherited() ) {
			return pc.getSuperclass().getEntityName();
		}
		return null;
	}
	
	private Set<String> getParentProperties( PersistentClass pc ) {
		Set<String> result;
		if ( pc.isInherited() ) {
			result = new HashSet<String>();
			Iterator i = pc.getSuperclass().getPropertyIterator();
			while ( i.hasNext() ) {
				result.add( ((Property)i.next()).getName() );
			}
		} else {
			result = Collections.emptySet();			
		}
		return result;
	}
	
	private void compositeAlias(List<AliasEntry> list, String aliasPreffix, String hibernatePreffix, Property property ) {
		Component component = (Component) property.getValue();
		Iterator propertyIterator = component.getPropertyIterator();
		while ( propertyIterator.hasNext() ) {
			Property p = (Property) propertyIterator.next();
			String alias = aliasPreffix + "_" + p.getName();
			String hibernate = hibernatePreffix + "." + p.getName();
			list.add( new AliasEntry(alias, hibernate, p.getType()) );
		}
	}
	
	private void calculateAlias(List<AliasEntry> list, String className, Property property ) {
		String name = property.getName();
		String aliasPreffix = className + "_" + name;
		String hibernatePreffix = className + "." + name;
		if ( property.isComposite() ) {
			compositeAlias( list, aliasPreffix, hibernatePreffix, property );
		} else if ( property.getType().isEntityType() ) {
			EntityType et = (EntityType) property.getType();
			PersistentClass pc = getPersistentClass( et.getAssociatedEntityName() );
			Property id = pc.getIdentifierProperty();
			String aliasId = aliasPreffix + "_" + id.getName();
			String hibernateId = hibernatePreffix + "." + id.getName();
			if ( id.isComposite() ) {
				compositeAlias( list, aliasId, hibernateId, id );
			} else {
				list.add( new AliasEntry(aliasId, hibernateId, et) );
			}
		} else if (! property.getType().isCollectionType() ) {
			list.add( new AliasEntry(aliasPreffix, hibernatePreffix, property.getType()) );
		}
	}
	
	private List<AliasEntry> getAliasEntries( PersistentClass pc, DAOConstantsEntry entry ) {
		List<AliasEntry> list = new ArrayList<AliasEntry>();
		String className = entry.getName();
		if (! pc.isInherited() ) {
			Property id = pc.getIdentifierProperty();
			calculateAlias( list, className, id );
		}
		Set<String> parentProperties = getParentProperties( pc );
		Iterator i = pc.getPropertyIterator();
		while ( i.hasNext() ) {
			Property property = (Property) i.next();
			if (! parentProperties.contains(property.getName()) ) {
				calculateAlias( list, className, property );
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private DAOConstantsEntry resolve( PersistentClass pc ) {
		String pojo = pc.getEntityName();
		DAOConstantsEntry entry = new DAOConstantsEntry( pojo, getParent(pc) );
		List<AliasEntry> list = getAliasEntries( pc, entry );
		Collections.sort( list );
		int i = 0;
		String[] aliasNames = new String[list.size()];
		String[] hibernateNames = new String[list.size()];
		for( AliasEntry ae : list ) {
			aliasNames[i] = ae.getAlias();
			hibernateNames[i++] = ae.getHibernate();
		}
		entry.setBeanAliasNames( aliasNames );
		entry.setHibernateNames( hibernateNames );		
		return entry;
	}
	
	private static class AliasEntry implements Comparable {

		private Type type;
		
		private String alias;
		
		private String hibernate;

		/**
		 * TODO
		 * @param alias
		 * @param hibernate
		 * @param type
		 */
		public AliasEntry(String alias, String hibernate, Type type) {
			this.type = type;
			this.alias = alias;
			this.hibernate = hibernate;
		}

		/**
		 * TODO
		 * @return String 
		 */
		public String getAlias() {
			return alias;
		}

		/**
		 * TODO
		 * @param alias
		 */
		public void setAlias(String alias) {
			this.alias = alias;
		}

		/**
		 * TODO
		 * @return String 
		 */
		public String getHibernate() {
			return hibernate;
		}

		/**
		 * TODO
		 * @param hibernate
		 */
		public void setHibernate(String hibernate) {
			this.hibernate = hibernate;
		}

		/**
		 * @return Type
		 */
		public Type getType() {
			return type;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		public int compareTo(Object o) {
			return this.alias.compareTo( ((AliasEntry) o).getAlias() );
		}

	}
	
}
