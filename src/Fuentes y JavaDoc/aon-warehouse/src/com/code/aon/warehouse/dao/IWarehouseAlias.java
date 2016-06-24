package com.code.aon.warehouse.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.warehouse.Delivery;
import com.code.aon.warehouse.DeliveryDetail;
import com.code.aon.warehouse.Stock;
import com.code.aon.warehouse.Warehouse;
import com.code.aon.warehouse.Income;
import com.code.aon.warehouse.IncomeDetail;
import com.code.aon.warehouse.Inventory;
import com.code.aon.warehouse.InventoryDetail;

/**
 * This Class manages the DAOConstantsEntry group of this package,
 * witch manages information of an IDAO constants stored in a file,
 * 
 * @author Consulting & Development.
 * @version 1.0
 */
public interface IWarehouseAlias {


    /**
     * DAOConstantsEntry for Delivery.
     */
	DAOConstantsEntry DELIVERY_ENTRY = DAOConstants.getDAOConstant(Delivery.class);

    /**
     * Alias name.
     */
	// Alias value: Delivery_customer_id
	// Hibernate value: Delivery.customer.id
	String  DELIVERY_CUSTOMER_ID = DELIVERY_ENTRY.getAliasNames()[0];

    /**
     * Alias name.
     */
	// Alias value: Delivery_id
	// Hibernate value: Delivery.id
	String  DELIVERY_ID = DELIVERY_ENTRY.getAliasNames()[1];

    /**
     * Alias name.
     */
	// Alias value: Delivery_issueTime
	// Hibernate value: Delivery.issueTime
	String  DELIVERY_ISSUE_TIME = DELIVERY_ENTRY.getAliasNames()[2];

    /**
     * Alias name.
     */
	// Alias value: Delivery_number
	// Hibernate value: Delivery.number
	String  DELIVERY_NUMBER = DELIVERY_ENTRY.getAliasNames()[3];

    /**
     * Alias name.
     */
	// Alias value: Delivery_raddress_id
	// Hibernate value: Delivery.raddress.id
	String  DELIVERY_RADDRESS_ID = DELIVERY_ENTRY.getAliasNames()[4];

    /**
     * Alias name.
     */
	// Alias value: Delivery_securityLevel
	// Hibernate value: Delivery.securityLevel
	String  DELIVERY_SECURITY_LEVEL = DELIVERY_ENTRY.getAliasNames()[5];

    /**
     * Alias name.
     */
	// Alias value: Delivery_series
	// Hibernate value: Delivery.series
	String  DELIVERY_SERIES = DELIVERY_ENTRY.getAliasNames()[6];

    /**
     * Alias name.
     */
	// Alias value: Delivery_status
	// Hibernate value: Delivery.status
	String  DELIVERY_STATUS = DELIVERY_ENTRY.getAliasNames()[7];


    /**
     * DAOConstantsEntry for DeliveryDetail.
     */
	DAOConstantsEntry DELIVERY_DETAIL_ENTRY = DAOConstants.getDAOConstant(DeliveryDetail.class);

    /**
     * Alias name.
     */
	// Alias value: DeliveryDetail_delivery_id
	// Hibernate value: DeliveryDetail.delivery.id
	String  DELIVERY_DETAIL_DELIVERY_ID = DELIVERY_DETAIL_ENTRY.getAliasNames()[0];

    /**
     * Alias name.
     */
	// Alias value: DeliveryDetail_description
	// Hibernate value: DeliveryDetail.description
	String  DELIVERY_DETAIL_DESCRIPTION = DELIVERY_DETAIL_ENTRY.getAliasNames()[1];

    /**
     * Alias name.
     */
	// Alias value: DeliveryDetail_discountExpression
	// Hibernate value: DeliveryDetail.discountExpression
	String  DELIVERY_DETAIL_DISCOUNT_EXPRESSION = DELIVERY_DETAIL_ENTRY.getAliasNames()[2];

    /**
     * Alias name.
     */
	// Alias value: DeliveryDetail_id
	// Hibernate value: DeliveryDetail.id
	String  DELIVERY_DETAIL_ID = DELIVERY_DETAIL_ENTRY.getAliasNames()[3];

    /**
     * Alias name.
     */
	// Alias value: DeliveryDetail_item_id
	// Hibernate value: DeliveryDetail.item.id
	String  DELIVERY_DETAIL_ITEM_ID = DELIVERY_DETAIL_ENTRY.getAliasNames()[4];

    /**
     * Alias name.
     */
	// Alias value: DeliveryDetail_line
	// Hibernate value: DeliveryDetail.line
	String  DELIVERY_DETAIL_LINE = DELIVERY_DETAIL_ENTRY.getAliasNames()[5];

    /**
     * Alias name.
     */
	// Alias value: DeliveryDetail_price
	// Hibernate value: DeliveryDetail.price
	String  DELIVERY_DETAIL_PRICE = DELIVERY_DETAIL_ENTRY.getAliasNames()[6];

    /**
     * Alias name.
     */
	// Alias value: DeliveryDetail_quantity
	// Hibernate value: DeliveryDetail.quantity
	String  DELIVERY_DETAIL_QUANTITY = DELIVERY_DETAIL_ENTRY.getAliasNames()[7];

	/**
     * Alias name.
     */
	// Alias value: DeliveryDetail_salesDetail_id
	// Hibernate value: DeliveryDetail.salesDetail.id
	String  DELIVERY_DETAIL_SALES_DETAIL_ID = DELIVERY_DETAIL_ENTRY.getAliasNames()[8];

    /**
     * Alias name.
     */
	// Alias value: DeliveryDetail_warehouse_id
	// Hibernate value: DeliveryDetail.warehouse.id
	String  DELIVERY_DETAIL_WAREHOUSE_ID = DELIVERY_DETAIL_ENTRY.getAliasNames()[9];

    /**
     * Alias name.
     */
	// Alias value: DeliveryDetail_item_product_type
	// Hibernate value: DeliveryDetail.item.product.type
	String  DELIVERY_DETAIL_ITEM_PRODUCT_TYPE = DELIVERY_DETAIL_ENTRY.getAliasNames()[10];


    /**
     * DAOConstantsEntry for Stock.
     */
	DAOConstantsEntry STOCK_ENTRY = DAOConstants.getDAOConstant(Stock.class);

    /**
     * Alias name.
     */
	// Alias value: Stock_id
	// Hibernate value: Stock.id
	String  STOCK_ID = STOCK_ENTRY.getAliasNames()[0];

    /**
     * Alias name.
     */
	// Alias value: Stock_item_id
	// Hibernate value: Stock.item.id
	String  STOCK_ITEM_ID = STOCK_ENTRY.getAliasNames()[1];

    /**
     * Alias name.
     */
	// Alias value: Stock_quantity
	// Hibernate value: Stock.quantity
	String  STOCK_QUANTITY = STOCK_ENTRY.getAliasNames()[2];

    /**
     * Alias name.
     */
	// Alias value: Stock_warehouse_id
	// Hibernate value: Stock.warehouse.id
	String  STOCK_WAREHOUSE_ID = STOCK_ENTRY.getAliasNames()[3];


    /**
     * DAOConstantsEntry for Warehouse.
     */
	DAOConstantsEntry WAREHOUSE_ENTRY = DAOConstants.getDAOConstant(Warehouse.class);

    /**
     * Alias name.
     */
	// Alias value: Warehouse_id
	// Hibernate value: Warehouse.id
	String  WAREHOUSE_ID = WAREHOUSE_ENTRY.getAliasNames()[0];

    /**
     * Alias name.
     */
	// Alias value: Warehouse_name
	// Hibernate value: Warehouse.name
	String  WAREHOUSE_NAME = WAREHOUSE_ENTRY.getAliasNames()[1];

	/**
     * DAOConstantsEntry for Income.
     */
	DAOConstantsEntry INCOME_ENTRY = DAOConstants.getDAOConstant(Income.class);

    /**
     * Alias name.
     */
	// Alias value: Income_id
	// Hibernate value: Income.id
	String  INCOME_ID = INCOME_ENTRY.getAliasNames()[0];

    /**
     * Alias name.
     */
	// Alias value: Income_incomeStatus
	// Hibernate value: Income.incomeStatus
	String  INCOME_INCOME_STATUS = INCOME_ENTRY.getAliasNames()[1];

    /**
     * Alias name.
     */
	// Alias value: Income_issueTime
	// Hibernate value: Income.issueTime
	String  INCOME_ISSUE_TIME = INCOME_ENTRY.getAliasNames()[2];

    /**
     * Alias name.
     */
	// Alias value: Income_number
	// Hibernate value: Income.number
	String  INCOME_NUMBER = INCOME_ENTRY.getAliasNames()[3];

    /**
     * Alias name.
     */
	// Alias value: Income_registryAddress_id
	// Hibernate value: Income.registryAddress.id
	String  INCOME_REGISTRY_ADDRESS_ID = INCOME_ENTRY.getAliasNames()[4];

    /**
     * Alias name.
     */
	// Alias value: Income_securityLevel
	// Hibernate value: Income.securityLevel
	String  INCOME_SECURITY_LEVEL = INCOME_ENTRY.getAliasNames()[5];

    /**
     * Alias name.
     */
	// Alias value: Income_series
	// Hibernate value: Income.series
	String  INCOME_SERIES = INCOME_ENTRY.getAliasNames()[6];

    /**
     * Alias name.
     */
	// Alias value: Income_supplier_id
	// Hibernate value: Income.supplier.id
	String  INCOME_SUPPLIER_ID = INCOME_ENTRY.getAliasNames()[7];


    /**
     * DAOConstantsEntry for IncomeDetail.
     */
	DAOConstantsEntry INCOME_DETAIL_ENTRY = DAOConstants.getDAOConstant(IncomeDetail.class);

    /**
     * Alias name.
     */
	// Alias value: IncomeDetail_description
	// Hibernate value: IncomeDetail.description
	String  INCOME_DETAIL_DESCRIPTION = INCOME_DETAIL_ENTRY.getAliasNames()[0];

    /**
     * Alias name.
     */
	// Alias value: IncomeDetail_discountExpression
	// Hibernate value: IncomeDetail.discountExpression
	String  INCOME_DETAIL_DISCOUNT_EXPRESSION = INCOME_DETAIL_ENTRY.getAliasNames()[1];

    /**
     * Alias name.
     */
	// Alias value: IncomeDetail_id
	// Hibernate value: IncomeDetail.id
	String  INCOME_DETAIL_ID = INCOME_DETAIL_ENTRY.getAliasNames()[2];

    /**
     * Alias name.
     */
	// Alias value: IncomeDetail_income_id
	// Hibernate value: IncomeDetail.income.id
	String  INCOME_DETAIL_INCOME_ID = INCOME_DETAIL_ENTRY.getAliasNames()[3];

    /**
     * Alias name.
     */
	// Alias value: IncomeDetail_item_id
	// Hibernate value: IncomeDetail.item.id
	String  INCOME_DETAIL_ITEM_ID = INCOME_DETAIL_ENTRY.getAliasNames()[4];

    /**
     * Alias name.
     */
	// Alias value: IncomeDetail_price
	// Hibernate value: IncomeDetail.price
	String  INCOME_DETAIL_PRICE = INCOME_DETAIL_ENTRY.getAliasNames()[5];

    /**
     * Alias name.
     */
	// Alias value: IncomeDetail_purchaseDetail_id
	// Hibernate value: IncomeDetail.purchaseDetail.id
	String  INCOME_DETAIL_PURCHASE_DETAIL_ID = INCOME_DETAIL_ENTRY.getAliasNames()[6];

    /**
     * Alias name.
     */
	// Alias value: IncomeDetail_quantity
	// Hibernate value: IncomeDetail.quantity
	String  INCOME_DETAIL_QUANTITY = INCOME_DETAIL_ENTRY.getAliasNames()[7];

	/**
     * Alias name.
     */
	// Alias value: IncomeDetail_warehouse_id
	// Hibernate value: IncomeDetail.warehouse.id
	String  INCOME_DETAIL_WAREHOUSE_ID = INCOME_DETAIL_ENTRY.getAliasNames()[8];

    /**
     * Alias name.
     */
	// Alias value: IncomeDetail_item_product_type
	// Hibernate value: IncomeDetail.item.product.type
	String  INCOME_DETAIL_ITEM_PRODUCT_TYPE = INCOME_DETAIL_ENTRY.getAliasNames()[9];


    /**
     * DAOConstantsEntry for Inventory.
     */
	DAOConstantsEntry INVENTORY_ENTRY = DAOConstants.getDAOConstant(Inventory.class);

    /**
     * Alias name.
     */
	// Alias value: Inventory_description
	// Hibernate value: Inventory.description
	String  INVENTORY_DESCRIPTION = INVENTORY_ENTRY.getAliasNames()[0];
	
    /**
     * Alias name.
     */
	// Alias value: Inventory_id
	// Hibernate value: Inventory.id
	String  INVENTORY_ID = INVENTORY_ENTRY.getAliasNames()[1];

    /**
     * Alias name.
     */
	// Alias value: Inventory_inventoryDate
	// Hibernate value: Inventory.inventoryDate
	String  INVENTORY_INVENTORY_DATE = INVENTORY_ENTRY.getAliasNames()[2];

    /**
     * Alias name.
     */
	// Alias value: Inventory_warehouse_id
	// Hibernate value: Inventory.warehouse.id
	String  INVENTORY_WAREHOUSE_ID = INVENTORY_ENTRY.getAliasNames()[3];


    /**
     * DAOConstantsEntry for InventoryDetail.
     */
	DAOConstantsEntry INVENTORY_DETAIL_ENTRY = DAOConstants.getDAOConstant(InventoryDetail.class);

    /**
     * Alias name.
     */
	// Alias value: InventoryDetail_actualQuantity
	// Hibernate value: InventoryDetail.actualQuantity
	String  INVENTORY_DETAIL_ACTUAL_QUANTITY = INVENTORY_DETAIL_ENTRY.getAliasNames()[0];

    /**
     * Alias name.
     */
	// Alias value: InventoryDetail_cost
	// Hibernate value: InventoryDetail.cost
	String  INVENTORY_DETAIL_COST = INVENTORY_DETAIL_ENTRY.getAliasNames()[1];

    /**
     * Alias name.
     */
	// Alias value: InventoryDetail_id
	// Hibernate value: InventoryDetail.id
	String  INVENTORY_DETAIL_ID = INVENTORY_DETAIL_ENTRY.getAliasNames()[2];

    /**
     * Alias name.
     */
	// Alias value: InventoryDetail_inventory_id
	// Hibernate value: InventoryDetail.inventory.id
	String  INVENTORY_DETAIL_INVENTORY_ID = INVENTORY_DETAIL_ENTRY.getAliasNames()[3];

    /**
     * Alias name.
     */
	// Alias value: InventoryDetail_item_id
	// Hibernate value: InventoryDetail.item.id
	String  INVENTORY_DETAIL_ITEM_ID = INVENTORY_DETAIL_ENTRY.getAliasNames()[4];

    /**
     * Alias name.
     */
	// Alias value: InventoryDetail_realQuantity
	// Hibernate value: InventoryDetail.realQuantity
	String  INVENTORY_DETAIL_REAL_QUANTITY = INVENTORY_DETAIL_ENTRY.getAliasNames()[5];


}