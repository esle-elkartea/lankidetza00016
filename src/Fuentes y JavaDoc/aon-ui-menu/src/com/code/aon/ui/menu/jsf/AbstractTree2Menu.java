package com.code.aon.ui.menu.jsf;

import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.custom.tree2.HtmlTree;
import org.apache.myfaces.custom.tree2.TreeNode;

import com.code.aon.ui.menu.IOption;

/**
 * Defines common for Managed Bean that work the Tree2 component of Tomahawk. 
 * 
 * @author Consulting & Development. Aimar Tellitu - 06-jun-2005
 * @since 1.0
 */
public abstract class AbstractTree2Menu extends AbstractComponent implements IJSFMenu {

    /**
	 * Logger initialization.
     */
	private static Logger LOGGER = Logger.getLogger(AbstractTree2Menu.class.getName());

	private HtmlTree htmlTree;

	public void setHtmlTree(HtmlTree htmlTree) {
		this.htmlTree = htmlTree;
	}

	public HtmlTree getHtmlTree() {
		return this.htmlTree;
	}
	
	public void pressed(ActionEvent e) {
		TreeNode node = this.htmlTree.getNode();
		LOGGER.fine("pressed: " + node.getDescription() );
		getMenuModel().setSelectedNode( node.getIdentifier() );
	}

	/**
     * Indicates whether the current node of the HtmlTree is selected.
     * 
     * @return <code>true</code> if so; <code>false</code> otherwise.
	 */
	public boolean isSelected() {
		TreeNode node = this.htmlTree.getNode();
		return node.getIdentifier().equals( getMenuModel().getSelectedNodeId() );
	}

	/**
	 * Returns the current menu's action.
	 * 
	 * @return the current menu's action.
	 */
	public String menuAction() {
		return menuAction(true);
	}

	/**
	 * Returns the current menu's text.
	 * 
	 * @return the current menu's text
	 */
	public String menuTextAction() {
		return menuAction(false);
	}

	private String menuAction(boolean toggleExpanded) {
		String id = getMenuModel().getSelectedNodeId();
		if ( ! this.htmlTree.isNodeExpanded() || toggleExpanded ) {
			LOGGER.fine( "menuAction: " + id + " toggle expanded" );
			this.htmlTree.toggleExpanded();			
		}
		return nodeAction();
	}
	
	/**
	 * Gets the current option's attribute value.
	 * 
	 * @return the current option's attribute value
	 */
	public String getOptionValue() {
		String id = this.htmlTree.getNode().getIdentifier();		
		IOption option = (IOption) getMenuModel().getMenuItem( id );		
		String value = option.getReference();
        if ( UIComponentTag.isValueReference(value) ) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ValueBinding vb = facesContext.getApplication().createValueBinding(value);
            value = (String) vb.getValue(facesContext);
        }
		if ( option.isContextRelative() ) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            value = facesContext.getExternalContext().getRequestContextPath() + value;
		}
		LOGGER.fine( "nodeValue: " + id + " value: " + value );
		return value;
	}
	
}
