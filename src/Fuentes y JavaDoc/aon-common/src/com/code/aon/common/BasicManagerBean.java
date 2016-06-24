package com.code.aon.common;

import com.code.aon.common.dao.IDAO;
import com.code.aon.common.dao.sql.DAOException;
import com.code.aon.common.event.IManagerBeanListener;
import com.code.aon.common.event.IManagerBeanVetoListener;
import com.code.aon.common.event.ManagerBeanEvent;
import com.code.aon.common.event.ManagerBeanListenerSupport;
import com.code.aon.common.event.ManagerBeanVetoListenerException;
import com.code.aon.common.event.ManagerBeanVetoListenerSupport;

/**
 * Basic implementation of the <code>IManagerBean</code> class. 
 * 
 * @author 	Consulting & Development. Aimar Tellitu - 27-jun-2005
 * @since 	1.0
 * @see 	com.code.aon.common.IManagerBean
 * @see 	com.code.aon.common.BasicFinderBean
 * 
 */
public class BasicManagerBean extends BasicFinderBean implements IManagerBean {

    // Manages the listeners.
	private ManagerBeanListenerSupport listeners;

    // Manages the vetoListeners.
	private ManagerBeanVetoListenerSupport vetoListeners;

	/**
	 * Construct a BasicManagerBean.
	 * 
	 * @param dao
	 */
	public BasicManagerBean(IDAO dao) {
		super(dao);
	}

	/**
	 * Listener registration method. Tell the ManagerBeanListenerSupport to add a new
	 * <code>IManagerBeanListener</code>.
	 * 
	 * @param listener
	 */
	public void addManagerBeanListener( IManagerBeanListener listener ) {
		if (listeners == null) {
			listeners = new ManagerBeanListenerSupport();
		}
		listeners.addListener( listener );
	}

	/**
	 * Listener registration method. Tell the ManagerBeanVetoListenerSupport to add a new
	 * <code>IManagerBeanVetoListener</code>.
	 * 
	 * @param vetoListener
	 */
	public void addManagerBeanVetoListener( IManagerBeanVetoListener vetoListener ) {
		if (vetoListeners == null) {
			vetoListeners = new ManagerBeanVetoListenerSupport(); 
		}
		vetoListeners.addListener( vetoListener );
	}

	/* 
	 * (non-Javadoc)
	 * @see com.code.aon.common.IManagerBean#remove(com.code.aon.common.ITransferObject)
	 */
	public boolean remove(ITransferObject to) throws ManagerBeanException {
		try {
			ManagerBeanEvent evt = new ManagerBeanEvent( to );
			fireVetoableBeanRemoved(evt);
			boolean ret = getDao().remove(to);
			fireBeanRemoved(evt);
			return ret;
		} catch (DAOException e) {
			throw new ManagerBeanException(e.getMessage(), e);
		} catch (ManagerBeanVetoListenerException e) {
			throw new ManagerBeanException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.code.aon.common.IManagerBean#update(com.code.aon.common.ITransferObject)
	 */
	public ITransferObject update(ITransferObject to) throws ManagerBeanException {
		try {
			ManagerBeanEvent evt = new ManagerBeanEvent( to );
			fireVetoableBeanUpdated(evt);
			ITransferObject ret = getDao().update(to);
			fireBeanUpdated(evt);
			return ret;
		} catch (DAOException e) {
			throw new ManagerBeanException(e.getMessage(), e);
		} catch (ManagerBeanVetoListenerException e) {
			throw new ManagerBeanException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.code.aon.common.IManagerBean#insert(com.code.aon.common.ITransferObject)
	 */
	public ITransferObject insert(ITransferObject to)
			throws ManagerBeanException {
		try {
			ManagerBeanEvent evt = new ManagerBeanEvent( to );
			fireVetoableBeanInserted(evt);
			ITransferObject ret = getDao().insert(to);
			fireBeanInserted(evt);
			return ret;
		} catch (DAOException e) {
			throw new ManagerBeanException(e.getMessage(), e);
		} catch (ManagerBeanVetoListenerException e) {
			throw new ManagerBeanException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.code.aon.common.IManagerBean#insert(com.code.aon.common.ITransferObject)
	 */
	public ITransferObject insertOrUpdate(ITransferObject to)
			throws ManagerBeanException {
		try {
			ManagerBeanEvent evt = new ManagerBeanEvent( to );
			fireVetoableBeanInserted(evt);
			ITransferObject ret = getDao().insertOrUpdate(to);
			fireBeanInserted(evt);
			return ret;
		} catch (DAOException e) {
			throw new ManagerBeanException(e.getMessage(), e);
		} catch (ManagerBeanVetoListenerException e) {
			throw new ManagerBeanException(e.getMessage(), e);
		}
	}
	
	/**
     * Fire an existing ManagerBeanEvent to any registered vetoListeners.
	 * 
	 * @param evt the ManagerBeanEvent object
	 * @throws ManagerBeanVetoListenerException
	 */
	private void fireVetoableBeanInserted( ManagerBeanEvent evt ) throws ManagerBeanVetoListenerException {
		if (vetoListeners != null) {
			vetoListeners.vetoableBeanInserted( evt );
		}
	}

	/**
     * Fire an existing ManagerBeanEvent to any registered vetoListeners.
	 * 
	 * @param evt the ManagerBeanEvent object
	 * @throws ManagerBeanVetoListenerException
	 */
	private void fireVetoableBeanUpdated( ManagerBeanEvent evt ) throws ManagerBeanVetoListenerException{
		if (vetoListeners != null) {
			vetoListeners.vetoableBeanUpdated( evt );
		}
	}

	/**
     * Fire an existing ManagerBeanEvent to any registered vetoListeners.
	 * 
	 * @param evt the ManagerBeanEvent object
	 * @throws ManagerBeanVetoListenerException
	 */
	private void fireVetoableBeanRemoved( ManagerBeanEvent evt ) throws ManagerBeanVetoListenerException{
		if (vetoListeners != null) {
			vetoListeners.vetoableBeanRemoved( evt );
		}
	}
	
	/**
     * Fire an existing ManagerBeanEvent to any registered listeners.
	 * 
	 * @param evt the ManagerBeanEvent object
	 * @throws ManagerBeanVetoListenerException
	 */
	private void fireBeanInserted( ManagerBeanEvent evt ) throws ManagerBeanException{
		if (listeners != null) {
			listeners.beanInserted( evt );
		}
	}
	
	/**
     * Fire an existing ManagerBeanEvent to any registered listeners.
	 * 
	 * @param evt the ManagerBeanEvent object
	 * @throws ManagerBeanVetoListenerException
	 */
	private void fireBeanUpdated( ManagerBeanEvent evt ) throws ManagerBeanException{
		if (listeners != null) {
			listeners.beanUpdated( evt );
		}
	}
	
	/**
     * Fire an existing ManagerBeanEvent to any registered listeners.
	 * 
	 * @param evt the ManagerBeanEvent object
	 * @throws ManagerBeanVetoListenerException
	 */
	private void fireBeanRemoved( ManagerBeanEvent evt ) throws ManagerBeanException{
		if (listeners != null) {
			listeners.beanRemoved( evt );
		}
	}
	
}