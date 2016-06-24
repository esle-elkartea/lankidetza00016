package com.code.aon.ui.sales.event;

import com.code.aon.registry.RegistryAddress;
import com.code.aon.ui.form.event.ControllerAdapter;
import com.code.aon.ui.form.event.ControllerEvent;
import com.code.aon.ui.form.event.ControllerListenerException;

/**
 * Listener added to the CustomerAddressController.
 */
public class AddressListener extends ControllerAdapter {

	/**
	 * Before bean added. Sets country to null to avoid <code>TransientObjectException</code>
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void beforeBeanAdded(ControllerEvent event) throws ControllerListenerException {
//		((RegistryAddress)event.getController().getTo()).setCountry(null);
		super.beforeBeanAdded(event);
	}
	
	/**
	 * Before bean updated. Sets country to null to avoid <code>TransientObjectException</code>
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void beforeBeanUpdated(ControllerEvent event) throws ControllerListenerException {
//		((RegistryAddress)event.getController().getTo()).setCountry(null);
		super.beforeBeanUpdated(event);
	}
}