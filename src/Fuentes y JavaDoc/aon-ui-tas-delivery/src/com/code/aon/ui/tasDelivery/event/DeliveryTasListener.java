package com.code.aon.ui.tasDelivery.event;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.code.aon.commercial.Offer;
import com.code.aon.commercial.enumeration.OfferStatus;
import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.employee.Employee;
import com.code.aon.employee.dao.IEmployeeAlias;
import com.code.aon.ql.Criteria;
import com.code.aon.tas.SupportOrder;
import com.code.aon.tas.dao.ITASAlias;
import com.code.aon.tas.enumeration.SupportOrderStatus;
import com.code.aon.tasCommercial.TasOffer;
import com.code.aon.tasCommercial.dao.ITasCommercialAlias;
import com.code.aon.tasDelivery.TasDelivery;
import com.code.aon.tasDelivery.dao.ITasDeliveryAlias;
import com.code.aon.ui.form.event.ControllerAdapter;
import com.code.aon.ui.form.event.ControllerEvent;
import com.code.aon.ui.form.event.ControllerListenerException;
import com.code.aon.ui.warehouse.controller.DeliveryController;
import com.code.aon.warehouse.Delivery;
import com.code.aon.warehouse.DeliveryDetail;
import com.code.aon.warehouse.dao.IWarehouseAlias;

/**
 * Listener added to the DeliveryController.
 */
public class DeliveryTasListener extends ControllerAdapter {
	
	/** The LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(DeliveryTasListener.class.getName());
	
	/**
	 * After bean added. If the current delivery has a support order related, adds a <code>TasDelivery</code> 
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void afterBeanAdded(ControllerEvent event) throws ControllerListenerException {
		DeliveryController deliveryController = (DeliveryController)event.getController();
		if(deliveryController.getSupportOrderId() != null){
			TasDelivery tasDelivery = new TasDelivery();
			tasDelivery.setDelivery((Delivery)deliveryController.getTo());
			tasDelivery.setSupportOrder(obtainSupportOrder(deliveryController.getSupportOrderId()));
			try {
				IManagerBean tasDeliveryBean = BeanManager.getManagerBean(TasDelivery.class);
				tasDeliveryBean.insert(tasDelivery);
				closeStatuses(tasDelivery.getSupportOrder());
				updateSupportOrderEmployee(tasDelivery.getSupportOrder(), deliveryController.getEmployeeId());
			} catch (ManagerBeanException e) {
				LOGGER.log(Level.SEVERE, "Error inserting TasDelivery for Delivery with id= " + ((Delivery)deliveryController.getTo()).getId(), e);
			}
		}
	}
	
	/**
	 * After bean updated. Updates the related <code>TasDelivery</code> if necessary
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void afterBeanUpdated(ControllerEvent event) throws ControllerListenerException {
		DeliveryController deliveryController = (DeliveryController)event.getController();
		if(deliveryController.getSupportOrderId() != null){
			SupportOrder supportOrder = obtainSupportOrder(deliveryController.getSupportOrderId());
			if(supportOrder.getEmployee() == null && deliveryController.getEmployeeId() != null){
				updateSupportOrderEmployee(supportOrder, deliveryController.getEmployeeId());
			}
			if(supportOrder.getEmployee() != null && !supportOrder.getEmployee().getId().equals(deliveryController.getEmployeeId())){
				updateSupportOrderEmployee(supportOrder, deliveryController.getEmployeeId());
			}
		}
	}
	
	/**
	 * Before bean removed. Removes the related <code>TasDelivery</code> if necessary
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void beforeBeanRemoved(ControllerEvent event) throws ControllerListenerException {
		Delivery delivery = (Delivery)((DeliveryController)event.getController()).getTo();
		try {
			IManagerBean tasDeliveryBean = BeanManager.getManagerBean(TasDelivery.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(tasDeliveryBean.getFieldName(ITasDeliveryAlias.TAS_DELIVERY_DELIVERY_ID), delivery.getId());
			Iterator iter = tasDeliveryBean.getList(criteria).iterator();
			if(iter.hasNext()){
				TasDelivery tasDelivery = (TasDelivery)iter.next();
				tasDeliveryBean.remove(tasDelivery);
				setStatusesPending(tasDelivery.getSupportOrder());
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error removing TasDelivery for delivery with id= " + delivery.getId(), e);
		}
	}
	
	/**
	 * After bean selected. Gets the the Employee id from the supportOrder of the related <code>TasDelivery</code>
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void afterBeanSelected(ControllerEvent event) throws ControllerListenerException {
		Delivery delivery = (Delivery)((DeliveryController)event.getController()).getTo();
		DeliveryDetail deliveryDetail = obtainDeliveryDetail(delivery.getId());
		if(deliveryDetail != null){
			try {
				IManagerBean tasDeliveryBean = BeanManager.getManagerBean(TasDelivery.class);
				Criteria criteria = new Criteria();
				criteria.addEqualExpression(tasDeliveryBean.getFieldName(ITasDeliveryAlias.TAS_DELIVERY_DELIVERY_ID), deliveryDetail.getDelivery().getId());
				Iterator iter = tasDeliveryBean.getList(criteria).iterator();
				if(iter.hasNext()){
					TasDelivery tasDelivery = (TasDelivery)iter.next();
					DeliveryController deliveryController = (DeliveryController)event.getController();
					deliveryController.setSupportOrderId(tasDelivery.getSupportOrder().getId());
					deliveryController.setDescription(tasDelivery.getSupportOrder().getDescription());
					if(tasDelivery.getSupportOrder().getEmployee() != null){
						deliveryController.setEmployeeId(tasDelivery.getSupportOrder().getEmployee().getId());
					}
				}
			} catch (ManagerBeanException e) {
				LOGGER.log(Level.SEVERE, "Error obtaining TasDeliveryId for delivery with id= " + delivery.getId(), e);
			}
		}
	}
	
	/**
	 * After bean reset. Sets to null the supportOrderId, employeeId and description of the controller
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void afterBeanReset(ControllerEvent event) throws ControllerListenerException {
		DeliveryController deliveryController = (DeliveryController)event.getController();
		deliveryController.setSupportOrderId(null);
		deliveryController.setEmployeeId(null);
		deliveryController.setDescription(null);
	}
	
	/**
	 * After bean created. Sets to null the supportOrderId, employeeId and description of the controller
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void afterBeanCreated(ControllerEvent event) throws ControllerListenerException {
		DeliveryController deliveryController = (DeliveryController)event.getController();
		deliveryController.setSupportOrderId(null);
		deliveryController.setEmployeeId(null);
		deliveryController.setDescription(null);
	}
	
	/**
	 * Obtain support order. Obtains the supportOrder with id equals to parameter <code>supportOrderId</code>.
	 * 
	 * @param supportOrderId the support order id
	 * 
	 * @return the support order
	 */
	private SupportOrder obtainSupportOrder(Integer supportOrderId) {
		try {
			IManagerBean supportOrderBean = BeanManager.getManagerBean(SupportOrder.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(supportOrderBean.getFieldName(ITASAlias.SUPPORT_ORDER_ID), supportOrderId);
			Iterator iter = supportOrderBean.getList(criteria).iterator();
			if(iter.hasNext()){
				return (SupportOrder)iter.next();
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining SupportOrder with id= " + supportOrderId, e);
		}
		return null;
	}
	
	/**
	 * Close statuses. Sets to finished the status of the supportOrder and to processed the status of the 
	 * related offer if it exists.
	 * 
	 * @param supportOrder the support order
	 */
	private void closeStatuses(SupportOrder supportOrder) {
		try {
			IManagerBean supportOrderBean = BeanManager.getManagerBean(SupportOrder.class);
			IManagerBean offerBean = BeanManager.getManagerBean(Offer.class);
			supportOrder.setStatus(SupportOrderStatus.FINISHED);
			supportOrderBean.update(supportOrder);
			Offer offer = obtainOffer(supportOrder.getId());
			if(offer != null){
				offer.setStatus(OfferStatus.PROCESSED);
				offerBean.update(offer);
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error updating statuses for SupportOrder with id= " + supportOrder.getId(), e);
		}
	}
	
	/**
	 * Updates the employee of the supportOrder
	 * 
	 * @param supportOrder the support order
	 * @param employeeId the employee id
	 */
	private void updateSupportOrderEmployee(SupportOrder supportOrder, Integer employeeId) {
		try {
			IManagerBean supportOrderBean = BeanManager.getManagerBean(SupportOrder.class);
			supportOrder.setEmployee(obtainEmployee(employeeId));
			supportOrderBean.update(supportOrder);
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error updating employee with id= " + employeeId + " for SupportOrder with id= " + supportOrder.getId(), e);
		}
	}
	
	/**
	 * Obtains the employee with id equals to parameter <code>employeeId</code>.
	 * 
	 * @param employeeId the employee id
	 * 
	 * @return the employee
	 */
	private Employee obtainEmployee(Integer employeeId) {
		try {
			IManagerBean employeeBean = BeanManager.getManagerBean(Employee.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(employeeBean.getFieldName(IEmployeeAlias.EMPLOYEE_ID), employeeId);
			Iterator iter = employeeBean.getList(criteria).iterator();
			if(iter.hasNext()){
				return (Employee)iter.next();
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining Employee with id= " + employeeId, e);
		}
		return null;
	}

	/**
	 * Obtains the offer with id equals to parameter <code>id</code>.
	 * 
	 * @param id the id
	 * 
	 * @return the offer
	 */
	private Offer obtainOffer(Integer id) {
		try {
			IManagerBean tasOfferBean = BeanManager.getManagerBean(TasOffer.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(tasOfferBean.getFieldName(ITasCommercialAlias.TAS_OFFER_SUPPORT_ORDER_ID), id);
			Iterator iter = tasOfferBean.getList(criteria).iterator();
			if(iter.hasNext()){
				return ((TasOffer)iter.next()).getOffer();
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining Offer for supportOrder with id= " + id, e);
		}
		return null;
	}

	/**
	 * Obtains the deliveryDetail with id equals to parameter <code>id</code>.
	 * 
	 * @param id the id
	 * 
	 * @return the delivery detail
	 */
	private DeliveryDetail obtainDeliveryDetail(Integer id) {
		try {
			IManagerBean deliveryDetailBean = BeanManager.getManagerBean(DeliveryDetail.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(deliveryDetailBean.getFieldName(IWarehouseAlias.DELIVERY_DETAIL_DELIVERY_ID), id);
			Iterator iter = deliveryDetailBean.getList(criteria).iterator();
			if(iter.hasNext()){
				return (DeliveryDetail)iter.next();
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining DeliveryDetail for delivery with id= " + id, e);
		}
		return null;
	}
	
	/**
	 * Sets to pending the status of the supportOrder of the 
	 * relatedd offer if it exists.
	 * 
	 * @param supportOrder the support order
	 */
	private void setStatusesPending(SupportOrder supportOrder) {
		try {
			IManagerBean supportOrderBean = BeanManager.getManagerBean(SupportOrder.class);
			IManagerBean offerBean = BeanManager.getManagerBean(Offer.class);
			IManagerBean tasOfferBean = BeanManager.getManagerBean(TasOffer.class);
			supportOrder.setStatus(SupportOrderStatus.PENDING);
			supportOrder.setEmployee(null);
			supportOrderBean.update(supportOrder);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(tasOfferBean.getFieldName(ITasCommercialAlias.TAS_OFFER_SUPPORT_ORDER_ID), supportOrder.getId());
			Iterator iter = tasOfferBean.getList(criteria).iterator();
			if(iter.hasNext()){
				TasOffer tasOffer = (TasOffer)iter.next();
				tasOffer.getOffer().setStatus(OfferStatus.PENDING);
				offerBean.update(tasOffer.getOffer());
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error updating statuses for SupportOrder with id= " + supportOrder.getId(), e);
		}
	}
}