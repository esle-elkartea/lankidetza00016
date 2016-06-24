package com.code.aon.ui.menu.jsf;

import javax.faces.event.ActionEvent;

/**
 * An {@link MenuEvent} represents the activation of a user interface
 * menu component (such as a {@link com.code.aon.ui.menu.IOption}).
 */
public class MenuEvent extends ActionEvent {

	private IJSFMenu menu;
	
	/**
	 * Construct a new event object from the specified <code>menu</code>
	 * and action command.
	 * 
	 * @param menu the menu
	 */
	public MenuEvent( IJSFMenu menu ) {
		super( menu.getHtmlTree() );
		this.menu = menu;
	}

	/**
	 * Gets the reference of the menu.
	 * 
	 * @return the reference of the menu
	 */
	public IJSFMenu getMenu() {
		return menu;
	}

}
