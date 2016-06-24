package com.code.aon.ui.sales.event;

import com.code.aon.common.enumeration.SecurityLevel;
import com.code.aon.sales.Customer;
import com.code.aon.sales.CustomerFee;
import com.code.aon.ui.form.event.ControllerAdapter;
import com.code.aon.ui.form.event.ControllerEvent;
import com.code.aon.ui.form.event.ControllerListenerException;
import com.code.aon.ui.sales.controller.CustomerController;
import com.code.aon.ui.util.AonUtil;

public class CustomerFeeControllerListener extends ControllerAdapter {
	
	private static final String CUSTOMER_CONTROLLER_NAME = "customer";

	@Override
	public void afterBeanCreated(ControllerEvent event) throws ControllerListenerException {
		CustomerController customerController = (CustomerController)AonUtil.getController(CUSTOMER_CONTROLLER_NAME);
		Customer customer = (Customer)customerController.getTo();
		((CustomerFee)event.getController().getTo()).setCustomer(customer);
		((CustomerFee)event.getController().getTo()).setSecurityLevel(SecurityLevel.OFFICIAL);
	}

}
