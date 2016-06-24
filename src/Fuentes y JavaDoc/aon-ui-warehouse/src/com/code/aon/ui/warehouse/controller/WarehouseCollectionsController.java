package com.code.aon.ui.warehouse.controller;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ITransferObject;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.warehouse.Inventory;
import com.code.aon.warehouse.Warehouse;
import com.code.aon.warehouse.enumeration.DeliveryStatus;
import com.code.aon.warehouse.enumeration.IncomeStatus;

/**
 * Controller for collections
 * 
 * @author Consulting & Development. Joseba Urkiri - 31-may-2006
 * 
 */
public class WarehouseCollectionsController {

	/**
	 * Returns a list of income statuses
	 * 
	 * @return IncomeStatus list
	 */
	public List<SelectItem> getIncomeStatuses() {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot()
				.getLocale();
		LinkedList<SelectItem> types = new LinkedList<SelectItem>();
		for (IncomeStatus type : IncomeStatus.values()) {
			String name = type.getName(locale);
			SelectItem item = new SelectItem(type, name);
			types.add(item);
		}
		return types;
	}

	/**
	 * The list of warehouses 
	 */
	private List<SelectItem> warehouses;

	/**
	 * Returns the list of warehouses
	 * 
	 * @return a list of warehouses
	 * @throws ManagerBeanException
	 */
	public List<SelectItem> getWarehouses() throws ManagerBeanException {
		if (warehouses == null) {
			warehouses = new LinkedList<SelectItem>();
			List<ITransferObject> c = BeanManager.getManagerBean(
					Warehouse.class).getList(null);
			Iterator<ITransferObject> iter = c.iterator();
			while (iter.hasNext()) {
				Warehouse warehouse = (Warehouse) iter.next();
				SelectItem item = new SelectItem(warehouse.getId().toString(),
						warehouse.getName());
				warehouses.add(item);
			}
		}
		return warehouses;
	}

	/**
	 * Returns a list with the delivery statuses
	 * 
	 * @return list of DeliveryStatus
	 */
	public List<SelectItem> getDeliveryStatuses() {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot()
				.getLocale();
		LinkedList<SelectItem> types = new LinkedList<SelectItem>();
		for (DeliveryStatus type : DeliveryStatus.values()) {
			String name = type.getName(locale);
			SelectItem item = new SelectItem(type, name);
			types.add(item);
		}
		return types;
	}
	
	/**
	 * Return a list of all inventories
	 * 
	 * @return Inventory list
	 * @throws ManagerBeanException 
	 */
	public List<SelectItem> getInventories() throws ManagerBeanException {
		List<SelectItem> inventories = new LinkedList<SelectItem>();
		IManagerBean inventoryBean = BeanManager.getManagerBean(Inventory.class);
		try {
			Iterator iter = inventoryBean.getList(null).iterator();
			while(iter.hasNext()){
				Inventory inventory = (Inventory)iter.next();
				String date = new SimpleDateFormat("dd/MM/yyyy").format(inventory.getInventoryDate());
				SelectItem item = new SelectItem(date, inventory.getDescription() + " [" + date + "]");
				inventories.add(item);
			}
		} catch (ManagerBeanException e) {
			e.printStackTrace();
		}
		return inventories;
	}
}