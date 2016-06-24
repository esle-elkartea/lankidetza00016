package com.code.aon.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.code.aon.common.ILookupObject;
import com.code.aon.common.ITransferObject;
import com.code.aon.purchase.dao.IPurchaseAlias;
import com.code.aon.purchase.enumeration.SupplierStatus;
import com.code.aon.registry.Registry;

/**
 * Transfer Object that represents a supplier.
 */
@Entity
@Table(name="supplier")
@PrimaryKeyJoinColumn(name="registry")
public class Supplier extends Registry implements ITransferObject, ILookupObject{
	
	/** 
	 * The Constant SUPPLIER_FULL_NAME used to retrieve the complete name of the supplier 
	 * using the lookup. 
	 */
	private static final String SUPPLIER_FULL_NAME = "Supplier_full_name";
	
	/** The status of the supplier. */
	private SupplierStatus status;
	
	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	@Column(name="status")
	public SupplierStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status the status
	 */
	public void setStatus(SupplierStatus status) {
		this.status = status;
	}
	
	/**
	 * Gets the a map that will be used by the lookup.
	 * 
	 * @return the map
	 */
	@Transient
    public Map<String,Object> getLookups() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(IPurchaseAlias.SUPPLIER_ID, getId());
        map.put(IPurchaseAlias.SUPPLIER_NAME, getName() );
        map.put(IPurchaseAlias.SUPPLIER_SURNAME, getSurname());
        map.put(IPurchaseAlias.SUPPLIER_DOCUMENT, getDocument());
        map.put(SUPPLIER_FULL_NAME, getName() + " " + ((getSurname() == null) ? "" : getSurname()) );
        return map;
    }
}