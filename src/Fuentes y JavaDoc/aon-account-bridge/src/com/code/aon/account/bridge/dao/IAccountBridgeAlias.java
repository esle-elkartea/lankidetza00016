package com.code.aon.account.bridge.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.account.bridge.CustomerAccount;
import com.code.aon.account.bridge.SupplierAccount;
import com.code.aon.account.bridge.CreditorAccount;
import com.code.aon.account.bridge.RegistryBankAccount;

/** 
* Interface for holding entity properties constants.
*/ 
public interface IAccountBridgeAlias {



	/** 
	* DAOConstantsEntry for CustomerAccount entity.
	*/ 
	DAOConstantsEntry CUSTOMER_ACCOUNT_ENTRY = DAOConstants.getDAOConstant(CustomerAccount.class);

	/** 
	* Alias value: CustomerAccount_account_id
	* Hibernate value: CustomerAccount.account.id
	*/
	String  CUSTOMER_ACCOUNT_ACCOUNT_ID = CUSTOMER_ACCOUNT_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: CustomerAccount_customer_id
	* Hibernate value: CustomerAccount.customer.id
	*/
	String  CUSTOMER_ACCOUNT_CUSTOMER_ID = CUSTOMER_ACCOUNT_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: CustomerAccount_id
	* Hibernate value: CustomerAccount.id
	*/
	String  CUSTOMER_ACCOUNT_ID = CUSTOMER_ACCOUNT_ENTRY.getAliasNames()[2];



	/** 
	* DAOConstantsEntry for SupplierAccount entity.
	*/ 
	DAOConstantsEntry SUPPLIER_ACCOUNT_ENTRY = DAOConstants.getDAOConstant(SupplierAccount.class);

	/** 
	* Alias value: SupplierAccount_account_id
	* Hibernate value: SupplierAccount.account.id
	*/
	String  SUPPLIER_ACCOUNT_ACCOUNT_ID = SUPPLIER_ACCOUNT_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: SupplierAccount_id
	* Hibernate value: SupplierAccount.id
	*/
	String  SUPPLIER_ACCOUNT_ID = SUPPLIER_ACCOUNT_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: SupplierAccount_supplier_id
	* Hibernate value: SupplierAccount.supplier.id
	*/
	String  SUPPLIER_ACCOUNT_SUPPLIER_ID = SUPPLIER_ACCOUNT_ENTRY.getAliasNames()[2];



	/** 
	* DAOConstantsEntry for CreditorAccount entity.
	*/ 
	DAOConstantsEntry CREDITOR_ACCOUNT_ENTRY = DAOConstants.getDAOConstant(CreditorAccount.class);

	/** 
	* Alias value: CreditorAccount_account_id
	* Hibernate value: CreditorAccount.account.id
	*/
	String  CREDITOR_ACCOUNT_ACCOUNT_ID = CREDITOR_ACCOUNT_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: CreditorAccount_creditor_id
	* Hibernate value: CreditorAccount.creditor.id
	*/
	String  CREDITOR_ACCOUNT_CREDITOR_ID = CREDITOR_ACCOUNT_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: CreditorAccount_id
	* Hibernate value: CreditorAccount.id
	*/
	String  CREDITOR_ACCOUNT_ID = CREDITOR_ACCOUNT_ENTRY.getAliasNames()[2];



	/** 
	* DAOConstantsEntry for RegistryBankAccount entity.
	*/ 
	DAOConstantsEntry REGISTRY_BANK_ACCOUNT_ENTRY = DAOConstants.getDAOConstant(RegistryBankAccount.class);

	/** 
	* Alias value: RegistryBankAccount_account_id
	* Hibernate value: RegistryBankAccount.account.id
	*/
	String  REGISTRY_BANK_ACCOUNT_ACCOUNT_ID = REGISTRY_BANK_ACCOUNT_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: RegistryBankAccount_id
	* Hibernate value: RegistryBankAccount.id
	*/
	String  REGISTRY_BANK_ACCOUNT_ID = REGISTRY_BANK_ACCOUNT_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: RegistryBankAccount_registryBank_id
	* Hibernate value: RegistryBankAccount.registryBank.id
	*/
	String  REGISTRY_BANK_ACCOUNT_REGISTRY_BANK_ID = REGISTRY_BANK_ACCOUNT_ENTRY.getAliasNames()[2];


}