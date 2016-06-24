package com.code.aon.ui.menu.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeState;
import org.apache.myfaces.custom.tree2.TreeStateBase;

import com.code.aon.ui.menu.IMenuItem;

/**
 * The Managed Bean used to show the full menu. Usually used to represent
 * a collapsible menu in sidebar.
 *  
 * @author Consulting & Development. Aimar Tellitu - 06-jun-2005
 * @since 1.0
 */
public class TreeMenu extends AbstractMenu {

	private boolean hidden;
	private TreeModel treeModel;
	private Map<String,TreeModel> treeModelMap;
	
    /**
	 * Logger initialization.
     */
	private static Logger LOGGER = Logger.getLogger(TreeMenu.class.getName());
	
	/**
	 * The Constructor.
	 */
	public TreeMenu() {
		this.treeModelMap = new HashMap<String, TreeModel>();
	}

	public TreeNode getMenu() {
		return getMenuModel().getRoot();
	}

	@Override
	public void menuChanged(MenuChangeEvent event) throws AbortProcessingException {
		super.menuChanged(event);
		changeTreeModel( event.getNewMenu() );
	}
	
	@Override
	public void setMenuManager(MenuManager menuManager) {
		super.setMenuManager(menuManager);
		changeTreeModel( menuManager.getCurrentMenuModel() );
	}

	private void changeTreeModel( IMenuModel menuModel ) {
		String id = menuModel.getMenuId();
		this.treeModel = this.treeModelMap.get( id );
		if ( this.treeModel == null  ) {
			TreeNode root = getMenu();
			treeModel = new TreeModelBase( root );
			TreeState treeState = new TreeStateBase();
			treeState.setTransient(true);
			treeModel.setTreeState(treeState);
			this.treeModelMap.put( id, this.treeModel );
		}
	}
	
	/**
	 * Gets the menu representation for the tomahawk Tree2 component.
	 * 
	 * @return the tree model
	 */
	public TreeModel getTreeModel() {
		return treeModel;
	}

	public String getTitle() {
		return getMenuModel().getRoot().getDescription();
	}
	
	private int getPos( IMenuItem menu ) {
		TreeNode parent = getMenuModel().getTreeNode(menu.getParent().getId());
		List childrens = parent.getChildren();
		for( int i = 0; i < parent.getChildCount(); i++ ) {
			TreeNode child = (TreeNode) childrens.get(i);
			if ( child.getIdentifier().equals(menu.getId()) ) {
				return i;
			}
		}
		return -1;
	}
	
	private String translateId( String id ) {
		StringBuffer sb = new StringBuffer( "0" );
		IMenuItem menu = (IMenuItem) getMenuModel().getMenuItem( id );
		while ( menu != null ) {
			if ( menu.getParent() != null ) {
				int pos = getPos( menu );
				sb.insert( 1, ":" + pos );
			}
			menu = menu.getParent();
		}
		return sb.toString();
	}

	public void valueChanged(String id) {
		String newId = translateId(id);
		String oldId = getHtmlTree().getNodeId();
		if (! newId.equals(oldId) ) {
			LOGGER.fine( "Node selected, old: " + newId + ", new: " + oldId );
			String[] ids = getHtmlTree().getPathInformation(newId);
			for( int i = 0; i < ids.length; i++ ) {
				getHtmlTree().setNodeId( ids[i] );
				if (! getHtmlTree().isNodeExpanded() ) {
					LOGGER.fine( "Node expanded: " + getHtmlTree().getNodeId() );
					getHtmlTree().toggleExpanded();
				}
			}
			
		}
	}

	/**
     * Indicates whether the menu is hidden.
	 * 
	 * @return <code>true</code> if so; <code>false</code> otherwise.
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Specify whether or not the menu is hidden. 
	 * 
	 * @param hidden
	 * 			when <code>true</code>, the menu is hidden.
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * Toggle visibility of the menu.
	 * 
	 * @param e the action event.
	 */
	@SuppressWarnings("unused")
	public void toggleVisibility(ActionEvent e) {
		setHidden(! isHidden() );
	}
	
}
