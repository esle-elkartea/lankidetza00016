package com.code.aon.ui.tas.controller;

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
import com.code.aon.ql.Criteria;
import com.code.aon.tas.Make;
import com.code.aon.tas.Model;
import com.code.aon.tas.SupportOrder;
import com.code.aon.tas.dao.ITASAlias;
import com.code.aon.tas.enumeration.SupportOrderStatus;

/**
 * This class provides this packages collection
 * 
 * @author Consulting & Development. igayarre - 28-ago-2006
 * 
 */
public class TASCollectionsController {

	/**
	 * Makes collection
	 */
	private List<SelectItem> makes;

	/**
	 * Returns make�s collection
	 * It is recovered once and stay in memory
	 * 
	 * @return makes 
	 * @throws ManagerBeanException
	 */
	public List<SelectItem> getMakes() throws ManagerBeanException {
		if (makes == null) {
			makes = new LinkedList<SelectItem>();
			IManagerBean makeBean = BeanManager.getManagerBean(Make.class);
			Criteria criteria = new Criteria();
			criteria.addOrder(makeBean.getFieldName(ITASAlias.MAKE_NAME));
			Iterator<ITransferObject> iter = makeBean.getList(criteria).iterator();
			while (iter.hasNext()) {
				Make make = (Make) iter.next();
				SelectItem item = new SelectItem(make.getId().toString(),make.getName());
				makes.add(item);
			}
		}
		return makes;
	}

	/**
	 * Models collection
	 */
	private List<SelectItem> models;

	/**
	 * Returns model�s collection
	 * It is recovered once and stay in memory
	 * 
	 * @return models 
	 * @throws ManagerBeanException
	 */
	public List<SelectItem> getModels() throws ManagerBeanException {
		if (models == null) {
			models = new LinkedList<SelectItem>();
			
			IManagerBean beanManager = BeanManager.getManagerBean(Model.class);
			Criteria criteria = new Criteria();
			criteria.addOrder(beanManager.getFieldName(ITASAlias.MODEL_NAME));
			List<ITransferObject> c = beanManager.getList(criteria);
			Iterator<ITransferObject> iter = c.iterator();
			while (iter.hasNext()) {
				Model model = (Model) iter.next();
				SelectItem item = new SelectItem(model.getId().toString(),
						model.getName());
				models.add(item);
			}
		}
		return models;
	}

	/**
	 * Returns a support order status list
	 * 
	 * @return support order statuses
	 */
	public List<SelectItem> getSupportOrderStatuses() {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot()
				.getLocale();
		LinkedList<SelectItem> types = new LinkedList<SelectItem>();
		SupportOrderStatus[] cStatuses = SupportOrderStatus.values();
		for (int i = 0; i < cStatuses.length; i++) {
			SupportOrderStatus status = cStatuses[i];
			String name = status.getName(locale);
			SelectItem item = new SelectItem(status, name);
			types.add(item);
		}
		return types;
	}
	
	/**
	 * Recovers all support order with SupportOrderStatus.PENDING
	 * 
	 * @return pending support orders
	 * @throws ManagerBeanException
	 */
	public List<SelectItem> getPendingSupportOrders() throws ManagerBeanException {
		LinkedList<SelectItem> supportOrders = new LinkedList<SelectItem>();
		Criteria criteria = new Criteria();
		IManagerBean supportOrderBean = BeanManager.getManagerBean(SupportOrder.class);
		criteria.addEqualExpression(supportOrderBean.getFieldName(ITASAlias.SUPPORT_ORDER_STATUS), SupportOrderStatus.PENDING);
		Iterator iter = supportOrderBean.getList(criteria).iterator();
		while(iter.hasNext()){
			SupportOrder supportOrder = (SupportOrder)iter.next();
			SelectItem item = new SelectItem(supportOrder.getId(), (supportOrder.getSeries()==null?"":supportOrder.getSeries())+"/"+supportOrder.getNumber()+ ": " +supportOrder.getTasItem().getPublicCode() + " / " + supportOrder.getTasItem().getModel().getMake().getName()+ " " + supportOrder.getTasItem().getModel().getName());
			supportOrders.add(item);
		}
		return supportOrders;
	}

}
