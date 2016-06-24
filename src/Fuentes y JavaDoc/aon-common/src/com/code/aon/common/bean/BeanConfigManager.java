package com.code.aon.common.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.metadata.ClassMetadata;

import com.code.aon.common.BasicManagerBean;
import com.code.aon.common.IFinderBean;
import com.code.aon.common.ITransferObject;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.common.dao.IDAO;
import com.code.aon.common.dao.hibernate.HibernateDAO;
import com.code.aon.common.dao.hibernate.HibernateUtil;
import com.code.aon.common.event.IManagerBeanListener;
import com.code.aon.common.event.IManagerBeanVetoListener;

/**
 * This class has all the beans defined in the bean_config_digester.xml file.
 * 
 * @author Consulting & Development.
 *
 */

public class BeanConfigManager {

	/**
	 * Obtain a suitable <code>Logger</code>.
	 */
    private static final Logger LOGGER = Logger.getLogger(BeanConfigManager.class.getName());

    /**
     * Registered beans.
     */
    private static Map<Class,BeanConfig> beans = new HashMap<Class,BeanConfig>();

    /**
     * Add a new bean configuration.
     * 
     * @param config
     * @throws ManagerBeanException
     */
	public void addBeanConfiguration(BeanConfig config) throws ManagerBeanException {
//    TODO  (1) 
        if (!beans.containsKey(config.getPojoClass())) {
            beans.put(config.getPojoClass(), config);
            LOGGER.info("Registered bean configuration " + config.getPojoClass());
        }
//		IFinderBean bean = getBean(config);
//		BeanManager.register(HibernateUtil.getSessionFactoryName() + "/" + config.getPojoClass(), bean);
	}

	/**
	 * Return the DAO bound to the POJO Class.
	 * 
	 * @param pojoClass
	 * @return The DAO bound to the POJO Class.
	 * @throws ManagerBeanException
	 */
	private static IDAO getPojoDAO( Class pojoClass ) throws ManagerBeanException {
		if (! ITransferObject.class.isAssignableFrom(pojoClass) ) {
			throw new ManagerBeanException( pojoClass + " must implement ITransferObject" );
		}
		ClassMetadata cmd = HibernateUtil.getSessionFactory().getClassMetadata( pojoClass );
		if ( cmd == null ) {
			throw new ManagerBeanException( pojoClass + " must have Hiberante mapping" );
		}
		return new HibernateDAO( pojoClass );
	}

	/**
	 * Return the <code>IFinderBean</code> boundt to <code>BeanConfig</code> parameter.
	 * 
	 * @param config
	 * @return The <code>IFinderBean</code> boundt to <code>BeanConfig</code> parameter.
	 * @throws ManagerBeanException
	 */
	private static IFinderBean getBean(BeanConfig config) throws ManagerBeanException {
		try {
			IDAO dao = null;
			if ( config.getDaoFactory() != null ) {
				Class factory = Class.forName(config.getDaoFactory());
				Class[] args = null;
				Object[] params = null;
				Method inst = factory.getMethod("getInstance", args);
				Object manager = inst.invoke(null, params);
				Method method = factory.getMethod(config.getDaoMethod(), args);
				dao = (IDAO) method.invoke(manager, params);
			} else {
				dao = getPojoDAO( config.getPojoClass() );
			}
			BasicManagerBean bean = new BasicManagerBean(dao);
			if (config.getListeners() != null) {
				for (String listenerClass : config.getListeners()) {
					Class c = Class.forName(listenerClass);
					IManagerBeanListener listener = (IManagerBeanListener) c.newInstance();
					bean.addManagerBeanListener(listener);
				}
			}
			if (config.getVetoListeners() != null) {
				for (String vetoListenerClass : config.getVetoListeners()) {
					Class c = Class.forName(vetoListenerClass);
					IManagerBeanVetoListener vetoListener = (IManagerBeanVetoListener) c.newInstance();
					bean.addManagerBeanVetoListener(vetoListener);
				}
			}
			return bean;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ManagerBeanException(e.getMessage(), e);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new ManagerBeanException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new ManagerBeanException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ManagerBeanException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ManagerBeanException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new ManagerBeanException(e.getMessage(), e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ManagerBeanException(e.getMessage(), e);
		}
	}

	/**
	 * Return the <code>IFinderBean</code> bound to the given POJO Class.
	 * 
	 * @param pojoClass
	 * @return The <code>IFinderBean</code>.
	 * @throws ManagerBeanException
	 */
	public static IFinderBean getBean( Class pojoClass ) throws ManagerBeanException {
//  TODO  (1) 
        if (beans.containsKey(pojoClass)) {
            return getBean( beans.get(pojoClass) );
        }
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setPojoClass( pojoClass );
		return getBean( beanConfig );
	}
	
}
//(1)
//Modificado para dar una soluci�n al problema en la carga de 
//beans predefinidos en el fichero bean_config.xml y la pol�tica de seguridad 
//por dominio y y usuario, ya que en este punto se est� desplegando el WAR de 
// la aplicaci�n en el servidor de aplicaciones y se desconocen tanto el dominio 
// como el usuario.
