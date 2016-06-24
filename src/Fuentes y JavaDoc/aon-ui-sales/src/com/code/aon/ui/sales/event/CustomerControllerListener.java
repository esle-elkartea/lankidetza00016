package com.code.aon.ui.sales.event;

import com.code.aon.sales.Customer;
import com.code.aon.ui.form.event.ControllerAdapter;
import com.code.aon.ui.form.event.ControllerEvent;
import com.code.aon.ui.form.event.ControllerListenerException;

/**
 * Listener added to CustomerController.
 */
public class CustomerControllerListener extends ControllerAdapter{

	/**
	 * Before bean added. Sets tariff to null to avoid <code>TransientObjectException</code>
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void beforeBeanAdded(ControllerEvent event) throws ControllerListenerException {
		((Customer)event.getController().getTo()).setTariff(null);
	}

	/**
	 * Before bean updated. Sets tariff to null to avoid <code>TransientObjectException</code>
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void beforeBeanUpdated(ControllerEvent event) throws ControllerListenerException {
		((Customer)event.getController().getTo()).setTariff(null);
	}
}