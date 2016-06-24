/*
 * Created on 07-abr-2005
 *
 */
package com.code.aon.ui.form.event;

import com.code.aon.ui.form.IController;

/**
 * The Interface IControllerListener.
 * 
 * @author Consulting & Development. I�aki Ayerbe - 07-abr-2005
 * @since 1.0
 */
public interface IControllerListener {

	/**
	 * Sets the controller.
	 * 
	 * @param controller the controller
	 */
	void setController( IController controller );
	
	/**
	 * Gets the controller.
	 * 
	 * @return the controller
	 */
	IController getController();
	
	/**
	 * M�todo invocado cuando se a�ade un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void afterBeanAdded(ControllerEvent event) throws ControllerListenerException;
	
	/**
	 * M�todo invocado cuando se a�ade un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void beforeBeanAdded(ControllerEvent event) throws ControllerListenerException;
	
	/**
	 * M�todo invocado cuando se elimina un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void afterBeanRemoved(ControllerEvent event) throws ControllerListenerException;

	/**
	 * M�todo invocado cuando se elimina un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void beforeBeanRemoved(ControllerEvent event) throws ControllerListenerException;

	/**
	 * M�todo invocado cuando se modifica un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void afterBeanUpdated(ControllerEvent event) throws ControllerListenerException;

	/**
	 * M�todo invocado cuando se modifica un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void beforeBeanUpdated(ControllerEvent event) throws ControllerListenerException;

	/**
	 * M�todo invocado cuando se cree un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void afterBeanCreated(ControllerEvent event) throws ControllerListenerException;

	/**
	 * M�todo invocado cuando se crea un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void beforeBeanCreated(ControllerEvent event) throws ControllerListenerException;

	/**
	 * M�todo invocado cuando se inicialize un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void afterBeanReset(ControllerEvent event) throws ControllerListenerException;

	/**
	 * M�todo invocado cuando se selecciona un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void beforeBeanReset(ControllerEvent event) throws ControllerListenerException;

	/**
	 * M�todo invocado cuando se selecciona un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void afterBeanSelected(ControllerEvent event) throws ControllerListenerException;

	/**
	 * M�todo invocado cuando se selecciona un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void beforeBeanSelected(ControllerEvent event) throws ControllerListenerException;

	/**
	 * M�todo invocado cuando se cancela la edici�n de un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void afterBeanCanceled(ControllerEvent event) throws ControllerListenerException;
	
	/**
	 * M�todo invocado cuando se cancela la edici�n de un elemento.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void beforeBeanCanceled(ControllerEvent event) throws ControllerListenerException;
	
	/**
	 * M�todo invocado cuando se inicializa el modelode datos.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void afterModelInitialized(ControllerEvent event) throws ControllerListenerException;
	
	/**
	 * M�todo invocado cuando se inicializa el modelode datos.
	 * 
	 * @param event ChangeEvent Evento propagado.
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	void beforeModelInitialized(ControllerEvent event) throws ControllerListenerException;
}
