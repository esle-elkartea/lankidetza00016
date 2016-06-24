package com.code.aon.ui.registry.controller;

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
import com.code.aon.company.Company;
import com.code.aon.geozone.Country;
import com.code.aon.geozone.GeoZone;
import com.code.aon.ql.Criteria;
import com.code.aon.registry.RegistryAddress;
import com.code.aon.registry.dao.IRegistryAlias;
import com.code.aon.registry.enumeration.AddressType;
import com.code.aon.registry.enumeration.MediaType;
import com.code.aon.registry.enumeration.RegistryType;
import com.code.aon.registry.enumeration.StreetType;

/**
 * Controller used to get Collections related with clasess in <code>com.code.aon.registry</code>.
 * 
 */
public class RegistryCollectionsController {

	/** The geoZones list. */
	private List<SelectItem> geoZones;
	
	/** The countries list. */
	private List<SelectItem> countries;

    /**
     * Gets the address types.
     * 
     * @return the address types
     */
    public List<SelectItem> getAddressTypes() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	    LinkedList<SelectItem> types = new LinkedList<SelectItem>();
        for( AddressType type : AddressType.values() ) {
            String name = type.getName(locale); 
            SelectItem item = new SelectItem(type, name);
            types.add( item );
        }
        return types;
    }

    /**
     * Gets the street types.
     * 
     * @return the street types
     */
    public List<SelectItem> getStreetTypes() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	    LinkedList<SelectItem> types = new LinkedList<SelectItem>();
        for( StreetType type : StreetType.values() ) {
            String name = type.getName(locale); 
            SelectItem item = new SelectItem(type, name);
            types.add( item );
        }
        return types;
    }

    /**
     * Gets the geoZones.
     * 
     * @return the geoZones
     * 
     * @throws ManagerBeanException the manager bean exception
     */
    public List<SelectItem> getGeoZones() throws ManagerBeanException {
        if (geoZones == null) {
            geoZones = new LinkedList<SelectItem>();
            List<ITransferObject> l = BeanManager.getManagerBean(GeoZone.class).getList( null );
            Iterator<ITransferObject> iter = l.iterator();
            while (iter.hasNext()){
                GeoZone geozone = (GeoZone) iter.next();
                SelectItem item = new SelectItem(geozone.getId(), geozone.getName());
                geoZones.add( item );
            }
        }
        return geoZones;
    }

    /**
     * Gets the countries.
     * 
     * @return the countries
     * 
     * @throws ManagerBeanException the manager bean exception
     */
    public List<SelectItem> getCountries() throws ManagerBeanException {
        if (countries == null) {
        	countries = new LinkedList<SelectItem>();
            List<ITransferObject> l = BeanManager.getManagerBean(Country.class).getList( null );
            Iterator<ITransferObject> iter = l.iterator();
            while (iter.hasNext()){
            	Country country = (Country) iter.next();
                SelectItem item = new SelectItem(country.getId(), country.getName());
                countries.add( item );
            }
        }
        return countries;
    }
    
    /**
     * Gets the media types.
     * 
     * @return the media types
     */
    public List getMediaTypes() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	    LinkedList<SelectItem> types = new LinkedList<SelectItem>();
        for( MediaType type : MediaType.values() ) {
            String name = type.getName(locale); 
            SelectItem item = new SelectItem(type, name);
            types.add( item );
        }
        return types;
    }
    
    /**
     * Gets the addresses of the company.
     * 
     * @return the addresses of the company
     * @throws ManagerBeanException 
     */
    public List getCompanyAddresses() throws ManagerBeanException{
    	IManagerBean companyBean = BeanManager.getManagerBean(Company.class);
    	IManagerBean registryAddressBean = BeanManager.getManagerBean(RegistryAddress.class);
    	Iterator iter = companyBean.getList(null).iterator();
    	LinkedList<SelectItem> addresses = new LinkedList<SelectItem>();
    	if(iter.hasNext()){
    		Company company = (Company)iter.next();
    		Criteria criteria = new Criteria();
    		criteria.addEqualExpression(registryAddressBean.getFieldName(IRegistryAlias.REGISTRY_ADDRESS_REGISTRY_ID), company.getId());
    		iter = registryAddressBean.getList(criteria).iterator();
    		while(iter.hasNext()){
    			RegistryAddress rAddress = (RegistryAddress)iter.next();
    			SelectItem item = new SelectItem(rAddress.getId(),rAddress.getAddress());
    			addresses.add(item);
    		}
    	}
    	return addresses;
    }
    
    /**
     * Gets the registry types.
     * 
     * @return the registry types
     */
    public List<SelectItem> getRegistryTypes() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	    LinkedList<SelectItem> types = new LinkedList<SelectItem>();
        for( RegistryType type : RegistryType.values() ) {
            String name = type.getName(locale); 
            SelectItem item = new SelectItem(type, name);
            types.add( item );
        }
        return types;
    }
}