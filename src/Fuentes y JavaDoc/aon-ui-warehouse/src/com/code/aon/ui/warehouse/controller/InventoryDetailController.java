package com.code.aon.ui.warehouse.controller;

import java.util.Collection;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;

import org.hibernate.Query;
import org.hibernate.Session;

import com.code.aon.common.ICollectionProvider;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.common.dao.hibernate.HibernateUtil;
import com.code.aon.ui.form.BasicController;
import com.code.aon.ui.menu.jsf.MenuEvent;
import com.code.aon.ui.util.AonUtil;
import com.code.aon.warehouse.Inventory;

/**
 * Controller for inventory detail
 * 
 * @author Consulting & Development.
 * @since 1.0
 *
 */
public class InventoryDetailController extends BasicController implements ICollectionProvider {

	/**
	 * Inventory controller 
	 */
	private static final String INVENTORY_CONTROLLER_NAME = "inventory";
	
	/**
	 * Category identifier
	 */
	private Integer categoryId = new Integer(-1); 
	
	/**
	 * Returns the categoy ident
	 * 
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	 * Assigns the category ident
	 * 
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	/**
	 * Resets the controller
	 * 
	 * @param event a menu event
	 */
	@SuppressWarnings("unused")
	public void onReset(MenuEvent event){
		this.model = null;
		super.onReset(null);
	}

	/**
	 * If needed reloads the detail model
	 * 
	 * @param event contains the new value
	 * @throws ManagerBeanException
	 */
	public void addCategoryCriteria(ValueChangeEvent event)
		throws ManagerBeanException {
		if (event.getNewValue() != null) {
			try{
				categoryId = new Integer(event.getNewValue().toString());
				loadDetailModel();
			}catch (Exception e) {
			} 
		}
	}

	/**
	 * Accepts current row and selects the next 
	 * 
	 * @param event an action event
	 */
	public void onAcceptNext(ActionEvent event) {
		accept(event);
		int current = this.model.getRowIndex();
		int max = this.model.getRowCount();
		if ((current+1) < max){
			this.model.setRowIndex(current+1);
			onSelect(event);
		}
	}

	/**
	 * Assigns a new list of inventory detail
	 */
	private void loadDetailModel() {
        this.setModel(new ListDataModel(getDetailList()));
	}
	
	/**
	 * Queries an inventory detail list for this inventory.id and category if needed
	 * 
	 * @return the list of inventory detail
	 */
	private List getDetailList(){
		InventoryController inventoryController = (InventoryController)AonUtil.getController(INVENTORY_CONTROLLER_NAME);
		Integer inventoryId = ((Inventory)inventoryController.getTo()).getId();
		Session session = HibernateUtil.getSession();
        Query q = session.createQuery(
                "select inventoryDetail " +
                "from InventoryDetail inventoryDetail, Item item, Product prod, ProductCategory cat " +
                "where inventoryDetail.item.id = item.id " +
                "and item.product.id = prod.id " +
                "and prod.category.id = cat.id " +
                "and inventoryDetail.inventory.id=" + inventoryId.intValue() + " " +
                (categoryId==null || categoryId.equals(new Integer(-1))?"":"and cat.id=" + categoryId.intValue()));
        return q.list();
	}
	
	/* (non-Javadoc)
	 * @see com.code.aon.ui.form.BasicController#getCollection()
	 */
	@Override
	public Collection getCollection() {
		return getDetailList();
	}
}