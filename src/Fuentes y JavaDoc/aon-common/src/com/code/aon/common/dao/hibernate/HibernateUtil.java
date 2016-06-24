package com.code.aon.common.dao.hibernate;

import java.security.Principal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.ComponentType;
import org.hibernate.type.EntityType;
import org.hibernate.type.IdentifierType;
import org.hibernate.type.NullableType;
import org.hibernate.type.Type;

import com.code.aon.common.dao.DAOConstantsResolver;
import com.code.aon.common.dao.sql.DAOException;

/**
 * Hibernate utilities class.
 * 
 * @author Consulting & Development.
 *
 */

public class HibernateUtil { 

    /**
     * TODO
     */
    public static final String HIBERNATE_CONFIGURATION_FILE_PROPERTY = "com.code.aon.hibernate.cfg.xml";
    /**
     * TODO
     */
    public static final String DEFAULT_SESSION_FACTORY_NAME = "localhost/default";

    /**
     * Obtain a suitable Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class.getName()); 

    /**
     * Map that holds sessionFactory.
     */
    private static Map<String,SessionFactory> sessionFactory = new HashMap<String,SessionFactory>(); 
    
    /**
     * Map that holds current open sessions.
     */
    public static final Map<String,ThreadLocal<Session>>session = new HashMap<String,ThreadLocal<Session>>(); 

    /**
     * Map that holds current open transaction.
     */
    public static final Map<String,ThreadLocal<Transaction>>transaction = new HashMap<String,ThreadLocal<Transaction>>(); 

    /**
     * 
     */
    private static boolean MUST_CLOSE_SESSION = true;
    /**
     * 
     */
    private static boolean MUST_BEGIN_TRANSACTION = true;

    /**
     * Open session from named instance.
     * 
     * @return The session.
     * @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        if (session.get(getSessionFactoryName()) == null) {
            getSessionFactory();
        }
        Session s = session.get(getSessionFactoryName()).get(); 
        //Open a new Session, if this Thread has none yet 
        if (s == null) { 
            s = sessionFactory.get(getSessionFactoryName()).openSession(); 
            session.get(getSessionFactoryName()).set(s); 
        } 
        return s; 
    } 

    /**
     * Open session from named instance.
     * 
     * @throws HibernateException
     */
    public static void startSession() {
    	HibernateUtil.getSession();
    }

    /**
     *  Close session from named instance.
     * 
     * @throws HibernateException
     */
    public static void closeSession() { 
        Session s = session.get(getSessionFactoryName()).get(); 
        session.get(getSessionFactoryName()).set(null); 
        if (s != null) 
            s.close(); 
    } 

    /**
     * Retrieve a sessionFactory from named instance.
     * 
     * @return The SessionFactory. 
     */
    public static SessionFactory getSessionFactory() {
        SessionFactory sf = sessionFactory.get(getSessionFactoryName()); 
        // Open a new Session, if this Thread has none yet 
        if (sf == null) { 
            sf = createSessionFactory(); 
        } 
        return sf; 
    }

    /**
     * Retrieve the sessionFactory named instance.
     * 
     * @return The SessionFactory. 
     */
    public static String getSessionFactoryName() {
        InitialContext ic;
        try {
            ic = new InitialContext();
            Subject subject = (Subject)ic.lookup(DBCPConnectionProvider.SECURITY_SUBJECT);
            if (subject != null && subject.getPrincipals() != null) {
                Principal principal = subject.getPrincipals().iterator().next();
                String name = principal.getName();
                int index = name.indexOf('@');
                return (index > -1)? name.substring(index + 1, name.length()): name;
            }
        } catch (NamingException e) {
            LOGGER.fine(e.getMessage());
        } 
        return DEFAULT_SESSION_FACTORY_NAME;
    }

    /**
     * Start a new database transaction.
     * 
     * @throws DAOException
     */
    public static void beginTransaction() throws DAOException {
        Transaction tx = transaction.get(getSessionFactoryName()).get(); 
        try {
            if (tx == null) {
                LOGGER.fine("Starting new database transaction in this thread.");
                tx = getSession().beginTransaction();
                transaction.get(getSessionFactoryName()).set(tx);
            }
        } catch (HibernateException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    /**
     * Commit the database transaction.
     * 
     * @throws DAOException
     */
    public static void commitTransaction() throws DAOException {
        Transaction tx = transaction.get(getSessionFactoryName()).get(); 
        try {
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                LOGGER.fine("Committing database transaction of this thread.");
                tx.commit();
            }
            transaction.get(getSessionFactoryName()).set(null); 
        } catch (HibernateException ex) {
            rollbackTransaction();
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    /**
     * Commit the database transaction.
     * 
     * @throws DAOException
     */
    public static void rollbackTransaction() throws DAOException {
        Transaction tx = transaction.get(getSessionFactoryName()).get(); 
        try {
            transaction.get(getSessionFactoryName()).set(null); 
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                LOGGER.fine("Tyring to rollback database transaction of this thread.");
                tx.rollback();
            }
        } catch (HibernateException ex) {
            throw new DAOException(ex.getMessage(), ex);
        } finally {
            closeSession();
        }
    }

    /**
     * TODO
     * 
     * @param cmd
     * @param associationPath
     * @return <code>true</code> if is an identifier.  
     */
    public static boolean isIdentifier(ClassMetadata cmd, String associationPath) {
        String idName = cmd.getIdentifierPropertyName();
        int pos = associationPath.indexOf('.');
        if (pos != -1) {
            String property = associationPath.substring(0, pos);
            if ( idName.equals(property) ) {
                return true;
            }
            Type type = cmd.getPropertyType( property );
            if ( type != null ) {
                ClassMetadata propertyCmd = null;
                if (type.isEntityType()) {
                    EntityType et = (EntityType) type;
                    propertyCmd = getSessionFactory().getClassMetadata( et.getAssociatedEntityName() );
                } else if (type.isComponentType()) {
                    ComponentType ct = (ComponentType) type;
                    propertyCmd = getSessionFactory().getClassMetadata( ct.getReturnedClass() );
                }
                return isIdentifier( propertyCmd, associationPath.substring(pos+1) );
            }
        } else {
            return associationPath.equals(idName);
        }
        return false;
    }

    /**
     * Checks if is componsite identifier.
     * 
     * @param cmd the cmd
     * @param property the property
     * 
     * @return true, if is componsite identifier
     */
    public static boolean isComponsiteIdentifier(ClassMetadata cmd, String property) {
        String idName = cmd.getIdentifierPropertyName();
        if ( idName.equals(property) ) {
        	Type type = cmd.getIdentifierType();
        	return type.isComponentType();
        }
        return false;
    }
    
    /**
     * Method getMappingTypes
     * 
     * @param persistenClass
     *            Class
     * @param properties
     *            String[]
     * @return Map
     */
    public static Map<String,Type> getMappingTypes(String persistenClass, String[] properties) {
        HashMap<String,Type> result = new HashMap<String,Type>();

        ClassMetadata cmd = getSessionFactory()
                .getClassMetadata(persistenClass);
        if (cmd != null) {
            if (properties != null) {
                for (int i = 0; i < properties.length; i++) {
                    String property = properties[i];
                    String propertyName = property.substring(property
                            .indexOf('.') + 1);
                    Type type = getType(cmd, propertyName);
                    if ((type != null)
                            && ((type instanceof IdentifierType) || (type instanceof NullableType))) {
                        result.put(property, type);
                    } else {
                        LOGGER.warning("No se ha encontrado el type para " + property);
                    }
                }
            }
        } else {
            LOGGER.severe("No se ha encontrado el mapping de la clase " + persistenClass);
        }

        return result;
    }

    /**
     * TODO
     * @param mustCloseSession
     */
    public static void setCloseSession(boolean mustCloseSession) {
        HibernateUtil.MUST_CLOSE_SESSION = mustCloseSession;
    }
    
    /**
     * TODO
     * @return boolean
     */
    public static boolean mustCloseSession() {
        return HibernateUtil.MUST_CLOSE_SESSION;
    }

    /**
     * TODO
     * @param mustBeginTransaction
     */
    public static void setBeginTransaction(boolean mustBeginTransaction) {
        HibernateUtil.MUST_BEGIN_TRANSACTION = mustBeginTransaction;
    }

    /**
     * TODO
     * @return boolean
     */
    public static boolean mustBeginTransaction() {
        return HibernateUtil.MUST_BEGIN_TRANSACTION;
    }

    /**
     * Creates a SessionFactory for a context and domain. 
     * 
     * @return a SessionFactory.
     * @see com.code.aon.common.dao.hibernate.HibernateUtil#getSessionFactoryName()
     */
    private static SessionFactory createSessionFactory() {
        SessionFactory factory = null;
        //Create the initial SessionFactory from the default configuration files
        String configurationResource = System.getProperty(HIBERNATE_CONFIGURATION_FILE_PROPERTY);
        AnnotationConfiguration configuration = new AnnotationConfiguration();
        try {
            // create session factory 
            if (configurationResource != null) {
                configuration.configure(configurationResource);
            } else {
                configuration.configure();
            }
            factory = configuration.buildSessionFactory();
            sessionFactory.put(getSessionFactoryName(), factory); 
            session.put(getSessionFactoryName(), new ThreadLocal<Session>()); 
            transaction.put(getSessionFactoryName(), new ThreadLocal<Transaction>()); 
            DAOConstantsResolver resolver = new DAOConstantsResolver(configuration);
            resolver.createDAOConstants();
        } catch (HibernateException he) {
            LOGGER.severe(he.getMessage()); 
            throw new RuntimeException("Configuration problem: " + he.getMessage(), he); 
        }
        return factory;
    }

    /**
     * Method getType
     * 
     * @param componentType
     *            ComponentType
     * @param property
     *            String
     * @return Type
     */
    private static Type getType(ComponentType componentType, String property) {
        String[] names = componentType.getPropertyNames();
        for (int i = 0; i < names.length; i++) {
            if (property.equals(names[i])) {
                return componentType.getSubtypes()[i];
            }
        }
        return null;
    }

    /**
     * Method getType
     * 
     * @param entityType
     *            EntityType
     * @param property
     *            String
     * @return Type
     */
    private static Type getType(EntityType entityType, String property) {
        ClassMetadata cmd = 
            getSessionFactory().getClassMetadata(entityType.getAssociatedEntityName());
        return getType(cmd, property);
    }

    /**
     * Method getType
     * 
     * @param cmd
     *            ClassMetadata
     * @param property
     *            String
     * @return Type
     */
    private static Type getType(ClassMetadata cmd, String property) {
        Type type = null;
        String moreProperty = null;
        int pos = property.indexOf('.');
        if (pos != -1) {
            moreProperty = property.substring(pos + 1);
            property = property.substring(0, pos);
        }
        try {
            String idName = cmd.getIdentifierPropertyName();
            if (property.equals(idName)) {
                type = cmd.getIdentifierType();
            } else {
                type = cmd.getPropertyType(property);
            }
            if (type != null) {
                if (type.isComponentType()) {
                    type = getType((ComponentType) type, moreProperty);
                } else if (type.isEntityType()) {
                    type = getType((EntityType) type, moreProperty);
                }
            }
        } catch (HibernateException he) {
            LOGGER.severe("Error obteniendo el Type de la propiedad " + property);
        }
        return type;
    }
    
    /**
     * Returns the java.sql.Connection returned by <code>HibernateUtil.getSession().connection()</code> method.
     * 
     * @return A java.sql.Connection.
     */
	public static Connection getSQLConnection() {
		return HibernateUtil.getSession().connection();
	}

} 
