package com.code.aon.ui.menu.jsf;

import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;

import com.code.aon.ui.menu.IMenuItem;

/**
 * This class provides default implementations for the Managed Beans that
 * are used to represent the menu structure in JavaServer Faces.
 * 
 * @author Consulting & Development. Aimar Tellitu - 06-jun-2005
 * @since 1.0
 *  
 */
public abstract class AbstractComponent implements MenuChangeListener, MenuListener {

    /**
	 * Logger initialization.
     */
	private static Logger LOGGER = Logger.getLogger(AbstractComponent.class.getName());

	private MenuManager menuManager;
	
	/**
	 * Gets the menu manager.
	 * 
	 * @return the menu manager
	 */
	public MenuManager getMenuManager() {
		return menuManager;
	}

	/**
	 * Sets the menu manager.
	 * 
	 * @param menuManager the menu manager
	 */
	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
		this.menuManager.addMenuChangeListener(this);
	}

	/**
	 * Gets the menu model Managed Bean. Ensures that the menu model is
	 * initialized.
	 * 
	 * @return the menu model Managed Bean
	 */
	public IMenuModel getMenuModel() {
		return this.menuManager.getCurrentMenuModel();
	}
	
	/**
	 * Returns the current option element's action.
	 * 
	 * @return the current option element's action
	 */
	public String nodeAction() {
		String id = getMenuModel().getSelectedNodeId();		
		IMenuItem menuItem = (IMenuItem) getMenuModel().getMenuItem( id );		
		String action = menuItem.getReference();
        if ( UIComponentTag.isValueReference(action) ) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            MethodBinding mb = facesContext.getApplication().createMethodBinding(action, null);
            action = (String) mb.invoke(facesContext, null);
        }
		LOGGER.fine( "nodeAction: " + id + " action: " + action );
		return action;
	}

	public void valueChanged(String id) {
		LOGGER.finest("select node: " + id );
	}
	
	/**
	 * Creates and returns a copy of the <code>node</code> object.
	 * 
	 * @param node the node to be copied.
	 * 
	 * @return the tree node copy of <code>node</code>.
	 */
	public TreeNode clone( TreeNode node ) {
		if (node instanceof MenuTreeNode) {
			MenuTreeNode menuNode = (MenuTreeNode) node;
			return new MenuTreeNode(menuNode.getMenuItem(),node.getType(), 
					node.getDescription(),menuNode.getLargeDescription(), 
					node.getIdentifier(), node.isLeaf() );
		}
		return new TreeNodeBase(node.getType(), node.getDescription(), node.getIdentifier(), node.isLeaf() );
	}
	
	private static int counter = 0;
	
	/**
	 * Gets the folder identifier.
	 * 
	 * @return the folder identifier
	 */
	public String getFolderId() {
		return "folderId" + (++counter);
	}
	
	/**
	 * Gets the menu identifier. Needed for the representation
	 * componet of the {@link com.code.aon.ui.menu.IMenu}.
	 * 
	 * @return the menu identifier
	 */
	public String getMenuId() {
		return "menuId" + counter;
	}
	
	/**
	 * Gets the node identifier. Needed for the representation
	 * componet of the {@link com.code.aon.ui.menu.IOption}.
	 * 
	 * @return the node id
	 */
	public String getNodeId() {
		return "nodeId" + (++counter);
	}
	
	public void menuChanged(MenuChangeEvent event) throws AbortProcessingException {
		if ( event.getOldMenu() != null ) {
			event.getOldMenu().removeMenuListener( this );
		}
		event.getNewMenu().addMenuListener( this );
	}

	/**
	 * Invoked when an {@link IMenuItem} is pressed..
	 * 
	 * @param event The {@link ActionEvent} that has occurred
	 */
	public abstract void pressed(ActionEvent event);	
	
}
