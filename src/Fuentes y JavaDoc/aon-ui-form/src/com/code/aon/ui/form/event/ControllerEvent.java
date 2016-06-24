/*
 * Created on 07-abr-2005
 *
 */
package com.code.aon.ui.form.event;

import java.util.EventObject;

import com.code.aon.ui.form.IController;

/**
 * ControllerEvent represents the events that can be fired in a IController.
 * 
 * @author Consulting & Development. I�aki Ayerbe - 07-abr-2005
 * @since 1.0
 */
 
public class ControllerEvent extends EventObject {

	/**
	 * The empty constructor.
	 * 
	 * @param source the source
	 */
	public ControllerEvent(IController source) {
		super(source);
	}

	/**
	 * Gets the controller.
	 * 
	 * @return the controller
	 */
	public IController getController() {
		return (IController) super.getSource();
	}
}
