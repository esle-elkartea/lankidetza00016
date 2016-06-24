package com.code.aon.ui.sales.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.sales.PointOfSale;
import com.code.aon.sales.enumeration.BillingPeriod;
import com.code.aon.sales.enumeration.CustomerStatus;

/**
 * Controller used to get Collections related with clasess in <code>com.code.aon.sales</code>
 * 
 * @author Consulting & Development. igayarre - 22-jun-2006
 */
public class SalesCollectionsController {
	
	/**
	 * Gets the customer statuses.
	 * 
	 * @return the customer statuses
	 */
	public List<SelectItem> getCustomerStatuses() {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot()
				.getLocale();
		LinkedList<SelectItem> types = new LinkedList<SelectItem>();
		CustomerStatus[] cStatuses = CustomerStatus.values();
		for (int i = 0; i < cStatuses.length; i++) {
			CustomerStatus status = cStatuses[i];
			String name = status.getName(locale);
			SelectItem item = new SelectItem(status, name);
			types.add(item);
		}
		return types;
	}

	/**
	 * Gets the Points of sale.
	 * 
	 * @return the Points of sale
	 * 
	 * @throws ManagerBeanException the manager bean exception
	 */
	public List<SelectItem> getPointsOfSale() throws ManagerBeanException {
		List<SelectItem>pointsOfSale = new LinkedList<SelectItem>();
		IManagerBean pointOfSaleBean = BeanManager.getManagerBean(PointOfSale.class);
		Iterator iter = pointOfSaleBean.getList(null).iterator();
		while(iter.hasNext()){
			PointOfSale pos = (PointOfSale)iter.next();
			SelectItem item = new SelectItem(pos.getId(), pos.getDescription());
			pointsOfSale.add(item);
		}
		return pointsOfSale;
	}
	
	public List<SelectItem> getBillingPeriods() {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		LinkedList<SelectItem> periods = new LinkedList<SelectItem>();
		BillingPeriod[] billingPeriods = BillingPeriod.values();
		for (int i = 0; i < billingPeriods.length; i++) {
			BillingPeriod period = billingPeriods[i];
			String name = period.getName(locale);
			SelectItem item = new SelectItem(period, name);
			periods.add(item);
		}
		return periods;
	}
}