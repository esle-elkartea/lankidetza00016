package com.code.aon.ui.form.event;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerListenerSupport.
 */
public class ControllerListenerSupport {

	/** Indica la lista de clases que escuchan al Controlador. */
	private List<IControllerListener> listeners;

	/**
	 * A�ade el listener <code>l</code> al controlador.
	 * 
	 * @param l the l
	 */
	public void addControllerListener(IControllerListener l) {
		if (listeners == null) {
			listeners = new LinkedList<IControllerListener>();
		}
		if (!listeners.contains(l))
			listeners.add(l);
	}

	/**
	 * Elimina el listener <code>l</code> del controlador.
	 * 
	 * @param l the l
	 */
	public void removeControllerListener(IControllerListener l) {
		listeners.remove(l);
	}

	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido a�adido.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireAfterBeanAdded(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.afterBeanAdded(event);
			}
		}
	}
	
	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido a�adido.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireBeforeBeanAdded(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.beforeBeanAdded(event);
			}
		}
	}

	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido modificado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireAfterBeanUpdated(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.afterBeanUpdated(event);
			}
			
		}
	}
	
	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido modificado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireBeforeBeanUpdated(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.beforeBeanUpdated(event);
			}
			
		}
	}

	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido borrado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireAfterBeanRemoved(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.afterBeanRemoved(event);
			}
		}
	}

	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido borrado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireBeforeBeanRemoved(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.beforeBeanRemoved(event);
			}
		}
	}
	
	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que la operaci�n ha sido cancelada.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireAfterBeanCanceled(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.afterBeanCanceled(event);
			}
		}
	}

	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que la operaci�n ha sido cancelada.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireBeforeBeanCanceled(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.beforeBeanCanceled(event);
			}
		}
	}

	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido seleccionado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireAfterBeanSelected(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.afterBeanSelected(event);
			}
		}
	}
	
	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido seleccionado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireBeforeBeanSelected(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.beforeBeanSelected(event);
			}
		}
	}

	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido seleccionado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireAfterBeanReset(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.afterBeanReset(event);
			}
		}
	}
	
	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido seleccionado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireBeforeBeanReset(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.beforeBeanReset(event);
			}
		}
	}
	
	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido seleccionado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireAfterBeanCreated(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.afterBeanCreated(event);
			}
		}
	}
	
	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el Bean ha sido seleccionado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireBeforeBeanCreated(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.beforeBeanCreated(event);
			}
		}
	}
	
	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el model de datos ha sido inicializado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireAfterModelInitialized(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.afterModelInitialized(event);
			}
		}
	}
	
	/**
	 * Propaga un <code>ControllerEvent</code> a todos los subscriptores
	 * registrados. En este caso informa de que el model de datos va a ser inicializado.
	 * 
	 * @param event El evento de esta acci�n.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	public void fireBeforeModelInitialized(ControllerEvent event) throws ControllerListenerException {
		if (listeners!= null && !listeners.isEmpty()){
			Iterator<IControllerListener> iter = listeners.iterator();
			while (iter.hasNext()) {
				IControllerListener l = iter.next();
				l.beforeModelInitialized(event);
			}
		}
	}
}
