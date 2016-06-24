package com.code.aon.ui.tas.event;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.code.aon.commercial.Target;
import com.code.aon.commercial.dao.ICommercialAlias;
import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.geozone.GeoZone;
import com.code.aon.ql.Criteria;
import com.code.aon.registry.RegistryAddress;
import com.code.aon.registry.RegistryMedia;
import com.code.aon.registry.dao.IRegistryAlias;
import com.code.aon.registry.enumeration.AddressType;
import com.code.aon.registry.enumeration.MediaType;
import com.code.aon.tas.Make;
import com.code.aon.tas.Model;
import com.code.aon.tas.SupportOrder;
import com.code.aon.tas.TasItem;
import com.code.aon.tas.dao.ITASAlias;
import com.code.aon.tas.enumeration.SupportOrderStatus;
import com.code.aon.ui.form.event.ControllerAdapter;
import com.code.aon.ui.form.event.ControllerEvent;
import com.code.aon.ui.form.event.ControllerListenerException;
import com.code.aon.ui.tas.controller.SupportOrderController;

/**
 * A listener for SupportOrderController
 * 
 * @author Consulting & Development.
 * @since 1.0
 *
 */
public class SupportOrderControllerListener extends ControllerAdapter {
	
	
	/**
	 * Launched after bean created.
	 * Initialized the controller and the TO
	 * 
	 * @param event the event
	 * @throws ControllerListenerException the controller listener exception
	 * @see com.code.aon.ui.form.event.ControllerAdapter#afterBeanCreated(com.code.aon.ui.form.event.ControllerEvent)
	 */
	@Override
	public void afterBeanCreated(ControllerEvent event) throws ControllerListenerException {
			SupportOrderController c = (SupportOrderController)event.getController();

			TasItem tasItem = new TasItem();
			Model model = new Model();
			Make make = new Make();
			model.setMake(make);
			tasItem.setModel(model);
			c.setTasItem(tasItem);
			
			Target target = new Target();
			c.setTarget(target);
			
			RegistryMedia phone = new RegistryMedia();
			phone.setRegistry(target.getRegistry());
			phone.setMediaType(MediaType.FIXED_PHONE);
			RegistryMedia cellular = new RegistryMedia();
			cellular.setRegistry(target.getRegistry());
			cellular.setMediaType(MediaType.CELLULAR);
			RegistryMedia fax = new RegistryMedia();
			fax.setRegistry(target.getRegistry());
			fax.setMediaType(MediaType.FAX);
			RegistryMedia email = new RegistryMedia();
			email.setRegistry(target.getRegistry());
			email.setMediaType(MediaType.EMAIL);
			RegistryAddress rAddress = new RegistryAddress();
			rAddress.setAddressType(AddressType.MAIN);
			rAddress.setGeozone(new GeoZone());
			rAddress.setRegistry(target.getRegistry());
			
			c.setPhone(phone);				
			c.setCellular(cellular);				
			c.setFax(fax);				
			c.setEmail(email);		
			c.setRegistryAddress(rAddress);
			c.resetDirty();
			
			SupportOrder supportOrder = (SupportOrder) c.getTo();
			supportOrder.setStartDate(new Date());
	}

	/**
	 * Launched after bean selected.
	 * Initialized the controller and the TO
	 * recovering data of all related TO
	 * 
	 * @param event the event
	 * @throws ControllerListenerException the controller listener exception
	 * @see com.code.aon.ui.form.event.ControllerAdapter#afterBeanSelected(com.code.aon.ui.form.event.ControllerEvent)
	 */
	@Override
	public void afterBeanSelected(ControllerEvent event) throws ControllerListenerException {
		try {
			SupportOrderController c = (SupportOrderController)event.getController();
			SupportOrder supportOrder = (SupportOrder) c.getTo();

			TasItem tasItem = supportOrder.getTasItem();
			c.setTasItem(tasItem);

			Target target = supportOrder.getTarget();
			c.setTarget(target);
			
			Criteria criteriaMedia = new Criteria();
			IManagerBean beanMedia = BeanManager.getManagerBean( RegistryMedia.class);
			String registryIdFieldName = beanMedia.getFieldName( IRegistryAlias.REGISTRY_MEDIA_REGISTRY_ID);
			criteriaMedia.addEqualExpression(registryIdFieldName,target.getId() );
			List mediaList = beanMedia.getList(  criteriaMedia );
			
			RegistryMedia phone = new RegistryMedia();
			phone.setRegistry(target.getRegistry());
			phone.setMediaType(MediaType.FIXED_PHONE);
			RegistryMedia cellular = new RegistryMedia();
			cellular.setRegistry(target.getRegistry());
			cellular.setMediaType(MediaType.CELLULAR);
			RegistryMedia fax = new RegistryMedia();
			fax.setRegistry(target.getRegistry());
			fax.setMediaType(MediaType.FAX);
			RegistryMedia email = new RegistryMedia();
			email.setRegistry(target.getRegistry());
			email.setMediaType(MediaType.EMAIL);
			
			Iterator mediaIter = mediaList.iterator();
			while (mediaIter.hasNext()){
				RegistryMedia rmedia = (RegistryMedia)mediaIter.next();
				if (MediaType.FIXED_PHONE == rmedia.getMediaType()){
					phone = rmedia;
				}else if (MediaType.CELLULAR == rmedia.getMediaType()){
					cellular = rmedia;
				}else if (MediaType.FAX == rmedia.getMediaType()){
					fax = rmedia;
				}else if (MediaType.EMAIL == rmedia.getMediaType()){
					email = rmedia;
				}
			}
			
			Criteria criteriaAddress = new Criteria();
			IManagerBean beanAddress = BeanManager.getManagerBean( RegistryAddress.class);
			criteriaAddress.addEqualExpression(beanAddress.getFieldName( IRegistryAlias.REGISTRY_ADDRESS_REGISTRY_ID),target.getId() );
			criteriaAddress.addEqualExpression(beanAddress.getFieldName( IRegistryAlias.REGISTRY_ADDRESS_ADDRESS_TYPE),AddressType.MAIN );
			List addressList = beanAddress.getList(  criteriaAddress );
			Iterator addressIter = addressList.iterator();
			RegistryAddress rAddress;
			if (addressIter.hasNext()){
				rAddress = (RegistryAddress)addressIter.next(); 
			}else{
				rAddress = new RegistryAddress();
				rAddress.setAddressType(AddressType.MAIN);
				rAddress.setGeozone(new GeoZone());
				rAddress.setRegistry(target.getRegistry());
			}
				
			c.setPhone(phone);				
			c.setCellular(cellular);				
			c.setFax(fax);				
			c.setEmail(email);		
			c.setRegistryAddress(rAddress);
			c.resetDirty();
		} catch (ManagerBeanException e) {
			throw new ControllerListenerException(e.getMessage(),e);
		}
	}

	/**
	 * Launched before bean updated.
	 * Updates all the values related to this TO in the controller
	 * 
	 * @param event the event
	 * @throws ControllerListenerException the controller listener exception
	 * @see com.code.aon.ui.form.event.ControllerAdapter#beforeBeanUpdated(com.code.aon.ui.form.event.ControllerEvent)
	 */
	@Override
	public void beforeBeanUpdated(ControllerEvent event) throws ControllerListenerException {
		try{
			updateValues(event);
			SupportOrder supportOrder = (SupportOrder)event.getController().getTo();
			if(supportOrder.getEmployee() == null || supportOrder.getEmployee().getId() == null){
				((SupportOrder)event.getController().getTo()).setEmployee(null);
			}
		} catch (ManagerBeanException e) {
			throw new ControllerListenerException(e.getMessage(),e);
		}
	}
	
	/**
	 * Launched before bean added.
	 * Updates all the values related to this TO in the controller
	 * 
	 * @param event the event
	 * @throws ControllerListenerException the controller listener exception
	 * @see com.code.aon.ui.form.event.ControllerAdapter#beforeBeanAdded(com.code.aon.ui.form.event.ControllerEvent)
	 */
	@Override
	public void beforeBeanAdded(ControllerEvent event) throws ControllerListenerException {
		try{
			SupportOrderController c = (SupportOrderController)event.getController();
			SupportOrder supportOrder = (SupportOrder) c.getTo();
			supportOrder.setStatus(SupportOrderStatus.PENDING);
			supportOrder.setEmployee(null);
			
			updateValues(event);
		} catch (ManagerBeanException e) {
			throw new ControllerListenerException(e.getMessage(),e);
		}
	}

	/**
	 * This constroller instead of TO has to control other entities
	 * The tas item and the target, with all data related to the target
	 * This method saves all this entities
	 * 
	 * @param event
	 * @throws ManagerBeanException
	 */
	private void updateValues(ControllerEvent event) throws ManagerBeanException{
		SupportOrderController c = (SupportOrderController)event.getController();
		SupportOrder so = (SupportOrder)c.getTo();

		if (c.isTasItemDirty()){
			updateChangedTasItem(c);
			saveTasItem(c.getTasItem());
			so.setTasItem(c.getTasItem());
		}

		if (c.isTargetDirty()){
			updateChangedTarget(c);
			saveTarget(c.getTarget());
			updateChangedTargetMedias(c);
			updateChangedTargetAddress(c);
			so.setTarget(c.getTarget());
			saveRegistryMedia(c.getPhone());
			saveRegistryMedia(c.getCellular());
			saveRegistryMedia(c.getFax());
			saveRegistryMedia(c.getEmail());
			saveRegistryAddress(c.getRegistryAddress());
		}
		c.resetDirty();
	}

	/**
	 * Saves the data all the tas item of this support order
	 * 
	 * @param c the support order controller
	 * @throws ManagerBeanException
	 */
	private void updateChangedTasItem(SupportOrderController c) throws ManagerBeanException{
		Criteria criteriaMedia = new Criteria();
		IManagerBean beanMedia = BeanManager.getManagerBean( TasItem.class);
		String tasItemIdFieldName = beanMedia.getFieldName( ITASAlias.TAS_ITEM_ID);
		criteriaMedia.addEqualExpression(tasItemIdFieldName,c.getTasItem().getId() );
		List list = beanMedia.getList(  criteriaMedia );
		
		Iterator iter = list.iterator();
		if (iter.hasNext()){
			TasItem tasItem = (TasItem)iter.next();
			
			tasItem.setPrivateCode(c.getTasItem().getPrivateCode());
			tasItem.setPublicCode(c.getTasItem().getPublicCode());
			tasItem.setDescription(c.getTasItem().getDescription());
			tasItem.setModel(c.getTasItem().getModel());
			
			c.setTasItem(tasItem);
		}
	}

	/**
	 * Saves the data all the target of this support order
	 * 
	 * @param c the support order controller
	 * @throws ManagerBeanException
	 */
	private void updateChangedTarget(SupportOrderController c) throws ManagerBeanException{
		
		Criteria criteriaMedia = new Criteria();
		IManagerBean beanMedia = BeanManager.getManagerBean( Target.class);
		String targetIdFieldName = beanMedia.getFieldName( ICommercialAlias.TARGET_ID);
		criteriaMedia.addEqualExpression(targetIdFieldName,c.getTarget().getId() );
		List list = beanMedia.getList(  criteriaMedia );
		
		Iterator iter = list.iterator();
		if (iter.hasNext()){
			Target target = (Target)iter.next();
			
			target.getRegistry().setName(c.getTarget().getRegistry().getName());
			target.getRegistry().setSurname(c.getTarget().getRegistry().getSurname());
			target.getRegistry().setAlias(c.getTarget().getRegistry().getAlias());
			
			c.setTarget(target);
		}else{
			Target target = c.getTarget();
			target.getRegistry().setId(target.getId());
		}
		
		c.getPhone().setRegistry(c.getTarget().getRegistry());
		c.getCellular().setRegistry(c.getTarget().getRegistry());
		c.getFax().setRegistry(c.getTarget().getRegistry());
		c.getEmail().setRegistry(c.getTarget().getRegistry());
		c.getRegistryAddress().setRegistry(c.getTarget().getRegistry());
	}
	
	/**
	 * Saves all the medias of the target of the support order
	 * 
	 * @param c the support order controller
	 * @throws ManagerBeanException
	 */
	private void updateChangedTargetMedias(SupportOrderController c) throws ManagerBeanException{
		Target target = c.getTarget();
		
		Criteria criteriaMedia = new Criteria();
		IManagerBean beanMedia = BeanManager.getManagerBean( RegistryMedia.class);
		String registryIdFieldName = beanMedia.getFieldName( IRegistryAlias.REGISTRY_MEDIA_REGISTRY_ID);
		criteriaMedia.addEqualExpression(registryIdFieldName,target.getId() );
		List mediaList = beanMedia.getList(  criteriaMedia );
		
		Iterator mediaIter = mediaList.iterator();
		while (mediaIter.hasNext()){
			RegistryMedia rmedia = (RegistryMedia)mediaIter.next();
			if (MediaType.FIXED_PHONE == rmedia.getMediaType()){
				rmedia.setValue(c.getPhone().getValue());
				c.setPhone(rmedia); 
			}else if (MediaType.CELLULAR == rmedia.getMediaType()){
				rmedia.setValue(c.getCellular().getValue());
				c.setCellular(rmedia); 
			}else if (MediaType.FAX == rmedia.getMediaType()){
				rmedia.setValue(c.getFax().getValue());
				c.setFax(rmedia); 
			}else if (MediaType.EMAIL == rmedia.getMediaType()){
				rmedia.setValue(c.getEmail().getValue());
				c.setEmail(rmedia); 
			}
		}
	}

	/**
	 * Saves the address of the target of the support order
	 * 
	 * @param c the support order controller
	 * @throws ManagerBeanException
	 */
	private void updateChangedTargetAddress(SupportOrderController c) throws ManagerBeanException{
		Target target = c.getTarget();
		
		Criteria criteriaAddress = new Criteria();
		IManagerBean beanAddress = BeanManager.getManagerBean( RegistryAddress.class);
		criteriaAddress.addEqualExpression(beanAddress.getFieldName( IRegistryAlias.REGISTRY_ADDRESS_REGISTRY_ID),target.getId() );
		criteriaAddress.addEqualExpression(beanAddress.getFieldName( IRegistryAlias.REGISTRY_ADDRESS_ADDRESS_TYPE),AddressType.MAIN );
		List addressList = beanAddress.getList(  criteriaAddress );
		
		Iterator addressIter = addressList.iterator();
		if (addressIter.hasNext()){
			RegistryAddress raddress = (RegistryAddress)addressIter.next();
			raddress.setAddress(c.getRegistryAddress().getAddress());
			raddress.setAddress2(c.getRegistryAddress().getAddress2());
			raddress.setAddress3(c.getRegistryAddress().getAddress3());
			raddress.setCity(c.getRegistryAddress().getCity());
			raddress.setZip(c.getRegistryAddress().getZip());
			raddress.setGeozone(c.getRegistryAddress().getGeozone());
			c.setRegistryAddress(raddress); 
		}
	}

	/**
	 * Saves a registry media object
	 * 
	 * @param rmedia a registry media object
	 * @throws ManagerBeanException
	 */
	private void saveRegistryMedia(RegistryMedia rmedia) throws ManagerBeanException{
		if (rmediaHasData(rmedia)){
			IManagerBean beanMedia = BeanManager.getManagerBean( RegistryMedia.class);
			if (rmedia.getId()==null){
				beanMedia.insert(rmedia);				
			}else{
				beanMedia.update(rmedia);				
			}		
		}
	}

	/**
	 * Returns true if the media has data
	 * 
	 * @param rmedia RegistryMedia
	 * @return has data
	 */
	private boolean rmediaHasData(RegistryMedia rmedia){
		if (rmedia.getValue()==null || "".equals(rmedia.getValue().trim())){
			return false;
		}
		return true;
	}
	
	/**
	 * Saves a registry address object
	 * 
	 * @param raddress a registry address object
	 * @throws ManagerBeanException
	 */
	private void saveRegistryAddress(RegistryAddress raddress) throws ManagerBeanException{
		if (raddressHasData(raddress)){
			IManagerBean beanAddress = BeanManager.getManagerBean( RegistryAddress.class);
			if (raddress.getId()==null){
				beanAddress.insert(raddress);				
			}else{
				beanAddress.update(raddress);				
			}		
		}
	}

	/**
	 * Returns true if the address has data
	 * 
	 * @param raddress RegistryAddress
	 * @return has data
	 */
	private boolean raddressHasData(RegistryAddress raddress){
		if ("".equals(raddress.getAddress().trim()) &&
				"".equals(raddress.getAddress2().trim()) && 
				"".equals(raddress.getAddress3().trim()) &&
				"".equals(raddress.getCity().trim()) &&
				"".equals(raddress.getZip().trim()) &&
				(raddress.getGeozone().getId() == null)
				){
			return false;
		}
		return true;
	}
	

	/**
	 * Saves the target object
	 * 
	 * @param target a target object
	 * @throws ManagerBeanException
	 */
	private void saveTarget(Target target) throws ManagerBeanException{
		IManagerBean beanMedia = BeanManager.getManagerBean( Target.class);
		if (target.getId()==null){
			beanMedia.insert(target);				
		}else{
			beanMedia.update(target);				
		}		
	}

	/**
	 * Saves a tas item
	 * 
	 * @param tasItem a tas item
	 * @throws ManagerBeanException
	 */
	private void saveTasItem(TasItem tasItem) throws ManagerBeanException{
		IManagerBean beanMedia = BeanManager.getManagerBean( TasItem.class);
		if (tasItem.getId()==null){
			beanMedia.insert(tasItem);				
		}else{
			beanMedia.update(tasItem);				
		}		
	}
}
