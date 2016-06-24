package com.code.aon.ui.form;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.myfaces.shared_impl.util.MessageUtils;

import com.code.aon.common.BeanManager;
import com.code.aon.common.ILookupObject;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ITransferObject;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.ui.common.lookup.LookupUtils;

/**
 * Abstract POJO Controller.
 * 
 * @author Consulting & Development.
 */
public class AbstractPojoController {

    private static final Logger LOGGER = Logger.getLogger(AbstractPojoController.class.getName());

    /** Clave para identificar el mensaje de error. (El valor es ""aon_error"") */
    public static final String AON_ERROR = "aon_error";

    private IManagerBean managerBean;

    private String beanName;

    private String pojo;

    private Stack<Class> pojoDependences;

    /**
     * Empty constructor.
     * 
     */
    public AbstractPojoController() {
    }

    /**
     * Return the POJO associated to controller.
     * 
     * @return String
     */
    public String getPojo() {
        return pojo;
    }

    /**
     * Set the POJO associated to controller.
     * 
     * @param bean
     */
    public void setPojo(String bean) {
        this.pojo = bean;
    }

    /**
     * Return name of the bean associated to controller.
     * 
     * @return String
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * Set the name of the bean associated to controller.
     * 
     * @param beanName
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    /**
     * Return the manager of bean associated to controller.
     * 
     * @return IManagerBean
     * @throws ManagerBeanException
     */
    public IManagerBean getManagerBean() throws ManagerBeanException {
        if (this.managerBean == null) {
            this.managerBean = BeanManager.getManagerBean(getPojo());
            if (this.managerBean == null) {
                String msg = "Unknown IManagerBean for " + getPojo();
                LOGGER.severe(msg);
                throw new ManagerBeanException(msg);
            }
        }
        return this.managerBean;
    }

    /**
     * Return the name of the field that corresponds to the parameter alias.
     * 
     * @param alias
     * @return String
     * @throws ManagerBeanException
     */
    public String getFieldName(String alias) throws ManagerBeanException {
        LOGGER.fine("Getting field name for[" + alias + "]");
        return getManagerBean().getFieldName(alias);
    }

    /**
     * Create a new <code>ITransferObject</code> attending to the POJO.
     * associated to controller.
     * 
     * @return ITransferObject.
     * @throws ManagerBeanException
     */
    protected ITransferObject createNewTo() throws ManagerBeanException {
        try {
            Class clazz = getManagerBean().getPOJOClass();
            Object o = clazz.newInstance();
            if (o instanceof ITransferObject) {
                ITransferObject to = (ITransferObject) o;
                initializePOJO(to);
                return to;
            }
            String msg = "Can not create new POJO." + clazz.getName() + " must be a implementation of ITransferObject";
            LOGGER.severe(msg);
            throw new ManagerBeanException(msg);
        } catch (InstantiationException e) {
            LOGGER.severe(e.getMessage());
            throw new ManagerBeanException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LOGGER.severe(e.getMessage());
            throw new ManagerBeanException(e.getMessage(), e);
        }
    }

    /**
     * Return when it is necessary to initialize and when not.
     * 
     * @param bean
     * @param pd
     * @return boolean
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private boolean needInitialize(Object bean, PropertyDescriptor pd) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return (!pd.getPropertyType().isEnum())
                && (!pd.getPropertyType().isArray())
                && ((pd.getPropertyType().getModifiers() & (Modifier.INTERFACE | Modifier.ABSTRACT)) == 0)
                && (pd.getWriteMethod() != null)
                && (!pd.getReadMethod().isAnnotationPresent(Transient.class) && (PropertyUtils.getProperty(bean, pd.getName()) == null));
    }

    /**
     * Initialize POJO.
     * 
     * @param to
     * @throws ManagerBeanException
     */
    protected void initializePOJO(ITransferObject to) throws ManagerBeanException {
        try {
            Class clazz = to.getClass();
            LOGGER.fine("Initializing " + clazz.getName());
            getPojoDependences().push(clazz);
            PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clazz);
            LOGGER.fine("Found " + pds.length + " properties");
            for (PropertyDescriptor pd : pds) {
                Class fieldClass = pd.getPropertyType();
                String name = pd.getName();
                if (needInitialize(to, pd)) {
                    if (ITransferObject.class.isAssignableFrom(fieldClass)) {
                        LOGGER.fine("Initializing TO " + name + " property");
                        ITransferObject childTO = (ITransferObject) fieldClass.newInstance();
                        if (!getPojoDependences().contains(fieldClass)) {
                            initializePOJO(childTO);
                            getPojoDependences().pop();
                        }
                        PropertyUtils.setProperty(to, name, childTO);
                        LOGGER.fine("Assigned TO " + fieldClass + " to " + clazz.getName());
                    } else if (!fieldClass.getName().startsWith("java")) {
                        LOGGER.fine("Initializing " + name + " property");
                        Object o = fieldClass.newInstance();
                        PropertyUtils.setProperty(to, name, o);
                        LOGGER.fine("Assigned " + fieldClass + " to " + clazz.getName());
                    }
                }
            }
        } catch (SecurityException e) {
            throw new ManagerBeanException(e.getMessage());
        } catch (InstantiationException e) {
            throw new ManagerBeanException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ManagerBeanException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ManagerBeanException(e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new ManagerBeanException(e.getMessage());
        }
    }

    /**
     * Return POJO dependences.
     * 
     * @return Stack<Class>
     */
    private Stack<Class> getPojoDependences() {
        if (pojoDependences == null) {
            pojoDependences = new Stack<Class>();
        }
        return pojoDependences;
    }

    /**
     * Return lookups in XML Format.
     * 
     * @param lo
     * @param ids
     * @return String
     */
    protected String getLookupsAsXML(ILookupObject lo, String ids) {
        Map<String, Object> map = lo.getLookups();
        customizeLookupMap(lo, map);
        return LookupUtils.getResponseXML(map, ids);
    }

    /**
     * To redefine in childs when you want to customize your lookups.
     * 
     * @param ito
     * @param map
     */
    @SuppressWarnings("unused")
    protected void customizeLookupMap(ILookupObject ito, Map<String, Object> map) {
    }

    /**
     * Add message to the collection of messages.
     * 
     * @param message
     */
    protected void addMessage(String message) {
        String[] args = { message };
        MessageUtils.addMessage(FacesMessage.SEVERITY_FATAL, AON_ERROR, args);
    }

}
