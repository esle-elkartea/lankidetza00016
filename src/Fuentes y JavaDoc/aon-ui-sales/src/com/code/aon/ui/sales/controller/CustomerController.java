package com.code.aon.ui.sales.controller;

import javax.faces.event.ActionEvent;

import com.code.aon.ui.form.BasicController;
import com.code.aon.ui.form.IController;
import com.code.aon.ui.menu.jsf.MenuEvent;
import com.code.aon.ui.util.AonUtil;

/**
 * Controller used in the customer maintenance.
 */
public class CustomerController extends BasicController {
	
	private static final String CUSTOMER_ADDRESS_CONTROLLER_NAME = "customerAddress";
	
	private static final String CUSTOMER_MEDIA_CONTROLLER_NAME = "customerMedia";
	
	/**
     * On reset. Method launched by the menu
     * 
     * @param event the event
     */
    @SuppressWarnings("unused")
    public void onReset(MenuEvent event) {
        this.onReset((ActionEvent)event);
    }

    /**
     * On reset. Sends a cancel to the media and address controllers to avoid having editing any of them
     * 
     * @param event the event
     * 
     * @see com.code.aon.ui.form.BasicController#onReset(javax.faces.event.ActionEvent)
     */
    @Override
    public void onReset(ActionEvent event) {
    	IController addressController = AonUtil.getController(CUSTOMER_ADDRESS_CONTROLLER_NAME);
        addressController.onCancel(event);

        IController mediaController = AonUtil.getController(CUSTOMER_MEDIA_CONTROLLER_NAME);
        mediaController.onCancel(event);

        super.onReset(event);
    }

    /**
     * On select. Sends a cancel to the media and address controllers to avoid having editing any of them
     * 
     * @param event the event
     * 
     * @see com.code.aon.ui.form.BasicController#onSelect(javax.faces.event.ActionEvent)
     */
    @Override
    public void onSelect(ActionEvent event) {
    	IController addressController = AonUtil.getController(CUSTOMER_ADDRESS_CONTROLLER_NAME);
        addressController.onCancel(event);

        IController mediaController = AonUtil.getController(CUSTOMER_MEDIA_CONTROLLER_NAME);
        mediaController.onCancel(event);

        super.onSelect(event);
    }
}