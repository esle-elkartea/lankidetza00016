package com.code.aon.ui.menu.jsf;

import org.apache.myfaces.custom.tree2.HtmlTree;
import org.apache.myfaces.custom.tree2.TreeNode;

import com.code.aon.ui.menu.MenuBeanException;

/**
 * The common interface of the all Managed Bean used to represent the menu structre
 * with the JSF components.
 */
public interface IJSFMenu {

	/**
	 * Gets the title of the menu.
	 * 
	 * @return the title the menu.
	 */
	String getTitle();	
	
	/**
	 * Gets the menu structure needed by the tomahawk Tree2 component.
	 * 
	 * @return the menu structure
	 * @throws MenuBeanException
	 * 			if an unexpected error occurs.
	 */
	TreeNode getMenu() throws MenuBeanException;
	
	/**
	 * Sets the binding with the HtmlTree JSF component.
	 * 
	 * @param htmlTree the HtmlTree JSF component.
	 */
	void setHtmlTree(HtmlTree htmlTree);
	
	/**
	 * Gets the binded {@link HtmlTree} JSF component.
	 * 
	 * @return the {@link HtmlTree} JSF component.
	 */
	HtmlTree getHtmlTree();
	
}
