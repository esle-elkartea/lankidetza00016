package com.code.aon.sales.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.sales.Customer;
import com.code.aon.sales.CustomerFee;
import com.code.aon.sales.FinanceSales;
import com.code.aon.sales.PointOfSale;
import com.code.aon.sales.Sales;
import com.code.aon.sales.SalesAttachment;
import com.code.aon.sales.SalesDetail;
import com.code.aon.sales.SalesType;
import com.code.aon.sales.Seller;

/**
 * This Class manages the DAOConstantsEntry group of this package,
 * witch manages information of an IDAO constants stored in a file,
 * 
 * @author Consulting & Development.
 * @version 1.0
 */
public interface ISalesAlias {


	/**
     * DAOConstantsEntry for Seller.
     */
	DAOConstantsEntry SELLER_ENTRY = DAOConstants.getDAOConstant(Seller.class);

	/**
     * Alias name.
     */
	// Alias value: Seller_description
	// Hibernate value: Seller.description
	String  SELLER_DESCRIPTION = SELLER_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: Seller_id
	// Hibernate value: Seller.id
	String  SELLER_ID = SELLER_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: Seller_registry_id
	// Hibernate value: Seller.registry.id
	String  SELLER_REGISTRY_ID = SELLER_ENTRY.getAliasNames()[2];


	/**
     * DAOConstantsEntry for CustomerFee.
     */
	DAOConstantsEntry CUSTOMER_FEE_ENTRY = DAOConstants.getDAOConstant(CustomerFee.class);

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_billingDate
	// Hibernate value: CustomerFee.billingDate
	String  CUSTOMER_FEE_BILLING_DATE = CUSTOMER_FEE_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_customer_id
	// Hibernate value: CustomerFee.customer.id
	String  CUSTOMER_FEE_CUSTOMER_ID = CUSTOMER_FEE_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_description
	// Hibernate value: CustomerFee.description
	String  CUSTOMER_FEE_DESCRIPTION = CUSTOMER_FEE_ENTRY.getAliasNames()[2];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_discountExpression
	// Hibernate value: CustomerFee.discountExpression
	String  CUSTOMER_FEE_DISCOUNT_EXPRESSION = CUSTOMER_FEE_ENTRY.getAliasNames()[3];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_finalDate
	// Hibernate value: CustomerFee.finalDate
	String  CUSTOMER_FEE_FINAL_DATE = CUSTOMER_FEE_ENTRY.getAliasNames()[4];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_id
	// Hibernate value: CustomerFee.id
	String  CUSTOMER_FEE_ID = CUSTOMER_FEE_ENTRY.getAliasNames()[5];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_initialDate
	// Hibernate value: CustomerFee.initialDate
	String  CUSTOMER_FEE_INITIAL_DATE = CUSTOMER_FEE_ENTRY.getAliasNames()[6];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_item_id
	// Hibernate value: CustomerFee.item.id
	String  CUSTOMER_FEE_ITEM_ID = CUSTOMER_FEE_ENTRY.getAliasNames()[7];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_period
	// Hibernate value: CustomerFee.period
	String  CUSTOMER_FEE_PERIOD = CUSTOMER_FEE_ENTRY.getAliasNames()[8];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_price
	// Hibernate value: CustomerFee.price
	String  CUSTOMER_FEE_PRICE = CUSTOMER_FEE_ENTRY.getAliasNames()[9];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_quantity
	// Hibernate value: CustomerFee.quantity
	String  CUSTOMER_FEE_QUANTITY = CUSTOMER_FEE_ENTRY.getAliasNames()[10];

	/**
     * Alias name.
     */
	// Alias value: CustomerFee_securityLevel
	// Hibernate value: CustomerFee.securityLevel
	String  CUSTOMER_FEE_SECURITY_LEVEL = CUSTOMER_FEE_ENTRY.getAliasNames()[11];


	/**
     * DAOConstantsEntry for Customer.
     */
	DAOConstantsEntry CUSTOMER_ENTRY = DAOConstants.getDAOConstant(Customer.class);

	/**
     * Alias name.
     */
	// Alias value: Customer_id
	// Hibernate value: Customer.id
	String  CUSTOMER_ID = CUSTOMER_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: Customer_registry_id
	// Hibernate value: Customer.registry.id
	String  CUSTOMER_REGISTRY_ID = CUSTOMER_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: Customer_status
	// Hibernate value: Customer.status
	String  CUSTOMER_STATUS = CUSTOMER_ENTRY.getAliasNames()[2];

	/**
     * Alias name.
     */
	// Alias value: Customer_surcharge
	// Hibernate value: Customer.surcharge
	String  CUSTOMER_SURCHARGE = CUSTOMER_ENTRY.getAliasNames()[3];

	/**
     * Alias name.
     */
	// Alias value: Customer_tariff_id
	// Hibernate value: Customer.tariff.id
	String  CUSTOMER_TARIFF_ID = CUSTOMER_ENTRY.getAliasNames()[4];

	/**
     * Alias name.
     */
	// Alias value: Customer_taxFree
	// Hibernate value: Customer.taxFree
	String  CUSTOMER_TAX_FREE = CUSTOMER_ENTRY.getAliasNames()[5];

	/**
     * Alias name.
     */
	// Alias value: Customer_registry_name
	// Hibernate value: Customer.registry.name
	String  CUSTOMER_REGISTRY_NAME = CUSTOMER_ENTRY.getAliasNames()[6];

	/**
     * Alias name.
     */
	// Alias value: Customer_registry_surname
	// Hibernate value: Customer.registry.surname
	String  CUSTOMER_REGISTRY_SURNAME = CUSTOMER_ENTRY.getAliasNames()[7];

	/**
     * Alias name.
     */
	// Alias value: Customer_registry_alias
	// Hibernate value: Customer.registry.alias
	String  CUSTOMER_REGISTRY_ALIAS = CUSTOMER_ENTRY.getAliasNames()[8];

	/**
     * Alias name.
     */
	// Alias value: Customer_registry_document
	// Hibernate value: Customer.registry.document
	String  CUSTOMER_REGISTRY_DOCUMENT = CUSTOMER_ENTRY.getAliasNames()[9];


	/**
     * DAOConstantsEntry for Sales.
     */
	DAOConstantsEntry SALES_ENTRY = DAOConstants.getDAOConstant(Sales.class);

	/**
     * Alias name.
     */
	// Alias value: Sales_customer_id
	// Hibernate value: Sales.customer.id
	String  SALES_CUSTOMER_ID = SALES_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: Sales_discountExpression
	// Hibernate value: Sales.discountExpression
	String  SALES_DISCOUNT_EXPRESSION = SALES_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: Sales_documentType
	// Hibernate value: Sales.documentType
	String  SALES_DOCUMENT_TYPE = SALES_ENTRY.getAliasNames()[2];

	/**
     * Alias name.
     */
	// Alias value: Sales_id
	// Hibernate value: Sales.id
	String  SALES_ID = SALES_ENTRY.getAliasNames()[3];

	/**
     * Alias name.
     */
	// Alias value: Sales_issueDate
	// Hibernate value: Sales.issueDate
	String  SALES_ISSUE_DATE = SALES_ENTRY.getAliasNames()[4];

	/**
     * Alias name.
     */
	// Alias value: Sales_number
	// Hibernate value: Sales.number
	String  SALES_NUMBER = SALES_ENTRY.getAliasNames()[5];

	/**
     * Alias name.
     */
	// Alias value: Sales_payMethod_id
	// Hibernate value: Sales.payMethod.id
	String  SALES_PAY_METHOD_ID = SALES_ENTRY.getAliasNames()[6];

	/**
     * Alias name.
     */
	// Alias value: Sales_pos
	// Hibernate value: Sales.pos
	String  SALES_POS = SALES_ENTRY.getAliasNames()[7];

	/**
     * Alias name.
     */
	// Alias value: Sales_securityLevel
	// Hibernate value: Sales.securityLevel
	String  SALES_SECURITY_LEVEL = SALES_ENTRY.getAliasNames()[8];

	/**
     * Alias name.
     */
	// Alias value: Sales_seller_id
	// Hibernate value: Sales.seller.id
	String  SALES_SELLER_ID = SALES_ENTRY.getAliasNames()[9];

	/**
     * Alias name.
     */
	// Alias value: Sales_series
	// Hibernate value: Sales.series
	String  SALES_SERIES = SALES_ENTRY.getAliasNames()[10];

	/**
     * Alias name.
     */
	// Alias value: Sales_shippingAddress_id
	// Hibernate value: Sales.shippingAddress.id
	String  SALES_SHIPPING_ADDRESS_ID = SALES_ENTRY.getAliasNames()[11];

	/**
     * Alias name.
     */
	// Alias value: Sales_status
	// Hibernate value: Sales.status
	String  SALES_STATUS = SALES_ENTRY.getAliasNames()[12];


	/**
     * DAOConstantsEntry for SalesDetail.
     */
	DAOConstantsEntry SALES_DETAIL_ENTRY = DAOConstants.getDAOConstant(SalesDetail.class);

	/**
     * Alias name.
     */
	// Alias value: SalesDetail_description
	// Hibernate value: SalesDetail.description
	String  SALES_DETAIL_DESCRIPTION = SALES_DETAIL_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: SalesDetail_discountExpression
	// Hibernate value: SalesDetail.discountExpression
	String  SALES_DETAIL_DISCOUNT_EXPRESSION = SALES_DETAIL_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: SalesDetail_id
	// Hibernate value: SalesDetail.id
	String  SALES_DETAIL_ID = SALES_DETAIL_ENTRY.getAliasNames()[2];

	/**
     * Alias name.
     */
	// Alias value: SalesDetail_item_id
	// Hibernate value: SalesDetail.item.id
	String  SALES_DETAIL_ITEM_ID = SALES_DETAIL_ENTRY.getAliasNames()[3];

	/**
     * Alias name.
     */
	// Alias value: SalesDetail_line
	// Hibernate value: SalesDetail.line
	String  SALES_DETAIL_LINE = SALES_DETAIL_ENTRY.getAliasNames()[4];

	/**
     * Alias name.
     */
	// Alias value: SalesDetail_price
	// Hibernate value: SalesDetail.price
	String  SALES_DETAIL_PRICE = SALES_DETAIL_ENTRY.getAliasNames()[5];

	/**
     * Alias name.
     */
	// Alias value: SalesDetail_quantity
	// Hibernate value: SalesDetail.quantity
	String  SALES_DETAIL_QUANTITY = SALES_DETAIL_ENTRY.getAliasNames()[6];

	/**
     * Alias name.
     */
	// Alias value: SalesDetail_salesDetailStatus
	// Hibernate value: SalesDetail.salesDetailStatus
	String  SALES_DETAIL_SALES_DETAIL_STATUS = SALES_DETAIL_ENTRY.getAliasNames()[7];

	/**
     * Alias name.
     */
	// Alias value: SalesDetail_sales_id
	// Hibernate value: SalesDetail.sales.id
	String  SALES_DETAIL_SALES_ID = SALES_DETAIL_ENTRY.getAliasNames()[8];

	/**
     * Alias name.
     */
	// Alias value: SalesDetail_taxes
	// Hibernate value: SalesDetail.taxes
	String  SALES_DETAIL_TAXES = SALES_DETAIL_ENTRY.getAliasNames()[9];


	/**
     * DAOConstantsEntry for SalesAttachment.
     */
	DAOConstantsEntry SALES_ATTACHMENT_ENTRY = DAOConstants.getDAOConstant(SalesAttachment.class);

	/**
     * Alias name.
     */
	// Alias value: SalesAttachment_data
	// Hibernate value: SalesAttachment.data
	String  SALES_ATTACHMENT_DATA = SALES_ATTACHMENT_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: SalesAttachment_id
	// Hibernate value: SalesAttachment.id
	String  SALES_ATTACHMENT_ID = SALES_ATTACHMENT_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: SalesAttachment_mimeType
	// Hibernate value: SalesAttachment.mimeType
	String  SALES_ATTACHMENT_MIME_TYPE = SALES_ATTACHMENT_ENTRY.getAliasNames()[2];

	/**
     * Alias name.
     */
	// Alias value: SalesAttachment_sales_id
	// Hibernate value: SalesAttachment.sales.id
	String  SALES_ATTACHMENT_SALES_ID = SALES_ATTACHMENT_ENTRY.getAliasNames()[3];


	/**
     * DAOConstantsEntry for SalesType.
     */
	DAOConstantsEntry SALES_TYPE_ENTRY = DAOConstants.getDAOConstant(SalesType.class);

	/**
     * Alias name.
     */
	// Alias value: SalesType_description
	// Hibernate value: SalesType.description
	String  SALES_TYPE_DESCRIPTION = SALES_TYPE_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: SalesType_id
	// Hibernate value: SalesType.id
	String  SALES_TYPE_ID = SALES_TYPE_ENTRY.getAliasNames()[1];


	/**
     * DAOConstantsEntry for FinanceSales.
     */
	DAOConstantsEntry FINANCE_SALES_ENTRY = DAOConstants.getDAOConstant(FinanceSales.class);

	/**
     * Alias name.
     */
	// Alias value: FinanceSales_finance_id
	// Hibernate value: FinanceSales.finance.id
	String  FINANCE_SALES_FINANCE_ID = FINANCE_SALES_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: FinanceSales_id
	// Hibernate value: FinanceSales.id
	String  FINANCE_SALES_ID = FINANCE_SALES_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: FinanceSales_sales_id
	// Hibernate value: FinanceSales.sales.id
	String  FINANCE_SALES_SALES_ID = FINANCE_SALES_ENTRY.getAliasNames()[2];
	
	/**
     * DAOConstantsEntry for PointOfSale.
     */
	DAOConstantsEntry POINT_OF_SALE_ENTRY = DAOConstants.getDAOConstant(PointOfSale.class);

	/**
     * Alias name.
     */
	// Alias value: PointOfSale_RAddress_id
	// Hibernate value: PointOfSale.RAddress.id
	String  POINT_OF_SALE_RADDRESS_ID = POINT_OF_SALE_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: PointOfSale_description
	// Hibernate value: PointOfSale.description
	String  POINT_OF_SALE_DESCRIPTION = POINT_OF_SALE_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: PointOfSale_id
	// Hibernate value: PointOfSale.id
	String  POINT_OF_SALE_ID = POINT_OF_SALE_ENTRY.getAliasNames()[2];

}