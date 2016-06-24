package com.code.aon.tasDelivery.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.tasDelivery.TasDelivery;

/**
 * This Class manages the DAOConstantsEntry group of this package,
 * witch manages information of an IDAO constants stored in a file,
 * 
 * @author Consulting & Development.
 * @version 1.0
 */
public interface ITasDeliveryAlias {


    /**
     * DAOConstantsEntry for TasDelivery.
     */
	DAOConstantsEntry TAS_DELIVERY_ENTRY = DAOConstants.getDAOConstant(TasDelivery.class);

    /**
     * Alias name.
     */
	// Alias value: TasDelivery_delivery_id
	// Hibernate value: TasDelivery.delivery.id
	String  TAS_DELIVERY_DELIVERY_ID = TAS_DELIVERY_ENTRY.getAliasNames()[0];

    /**
     * Alias name.
     */
	// Alias value: TasDelivery_id
	// Hibernate value: TasDelivery.id
	String  TAS_DELIVERY_ID = TAS_DELIVERY_ENTRY.getAliasNames()[1];

    /**
     * Alias name.
     */
	// Alias value: TasDelivery_supportOrder_id
	// Hibernate value: TasDelivery.supportOrder.id
	String  TAS_DELIVERY_SUPPORT_ORDER_ID = TAS_DELIVERY_ENTRY.getAliasNames()[2];


}