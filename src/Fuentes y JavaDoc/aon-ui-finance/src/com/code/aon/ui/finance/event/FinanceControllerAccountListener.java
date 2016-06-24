package com.code.aon.ui.finance.event;

import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.code.aon.account.Account;
import com.code.aon.account.AccountEntry;
import com.code.aon.account.AccountEntryDetail;
import com.code.aon.account.bridge.CreditorAccount;
import com.code.aon.account.bridge.CustomerAccount;
import com.code.aon.account.bridge.RegistryBankAccount;
import com.code.aon.account.bridge.SupplierAccount;
import com.code.aon.account.bridge.dao.IAccountBridgeAlias;
import com.code.aon.account.enumeration.AccountEntryType;
import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.finance.Finance;
import com.code.aon.finance.enumeration.InvoiceType;
import com.code.aon.ql.Criteria;
import com.code.aon.registry.Registry;
import com.code.aon.ui.finance.controller.FinanceController;
import com.code.aon.ui.form.event.ControllerAdapter;
import com.code.aon.ui.form.event.ControllerEvent;
import com.code.aon.ui.form.event.ControllerListenerException;

public class FinanceControllerAccountListener extends ControllerAdapter{
	
	private static final Logger LOGGER = Logger.getLogger(FinanceControllerAccountListener.class.getName());
	
	@Override
	public void beforeBeanUpdated(ControllerEvent event) throws ControllerListenerException {
		Finance finance = (Finance)event.getController().getTo();
		Account account = null;
		Account bankAccount = null;
		if(finance.getInvoice().getType().equals(InvoiceType.SALES)){
			account = obtainCustomerAccount(finance.getRegistry());
		}
		if(finance.getInvoice().getType().equals(InvoiceType.PURCHASE)){
			account = obtainSupplierAccount(finance.getRegistry());
		}
		if(finance.getInvoice().getType().equals(InvoiceType.EXPENSES)){
			account = obtainCreditorAccount(finance.getRegistry());
		}
		if(((FinanceController)event.getController()).getRegistryBankId() != null){
			bankAccount = obtainBankAccount(((FinanceController)event.getController()).getRegistryBankId());
		}
		insertAccountEntry(finance, account, bankAccount, ((FinanceController)event.getController()).getPaymentDate());
	}

	// TODO si no existe cuenta habrá que crearla
	private Account obtainCustomerAccount(Registry registry) {
		try {
			IManagerBean customerAccountBean = BeanManager.getManagerBean(CustomerAccount.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(customerAccountBean.getFieldName(IAccountBridgeAlias.CUSTOMER_ACCOUNT_CUSTOMER_ID), registry.getId());
			Iterator iter = customerAccountBean.getList(criteria).iterator();
			if(iter.hasNext()){
				return ((CustomerAccount)iter.next()).getAccount();
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining customerAccount to customer with id= " + registry.getId(), e);
		}
		return null;
	}

	// TODO si no existe cuenta habrá que crearla
	private Account obtainSupplierAccount(Registry registry) {
		try {
			IManagerBean supplierAccountBean = BeanManager.getManagerBean(SupplierAccount.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(supplierAccountBean.getFieldName(IAccountBridgeAlias.SUPPLIER_ACCOUNT_SUPPLIER_ID), registry.getId());
			Iterator iter = supplierAccountBean.getList(criteria).iterator();
			if(iter.hasNext()){
				return ((SupplierAccount)iter.next()).getAccount();
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining supplierAccount to supplier with id= " + registry.getId(), e);
		}
		return null;
	}

	// TODO si no existe cuenta habrá que crearla
	private Account obtainCreditorAccount(Registry registry) {
		try {
			IManagerBean creditorAccountBean = BeanManager.getManagerBean(CreditorAccount.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(creditorAccountBean.getFieldName(IAccountBridgeAlias.CREDITOR_ACCOUNT_CREDITOR_ID), registry.getId());
			Iterator iter = creditorAccountBean.getList(criteria).iterator();
			if(iter.hasNext()){
				return ((CreditorAccount)iter.next()).getAccount();
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining creditorAccount to creditor with id= " + registry.getId(), e);
		}
		return null;
	}
	
	// TODO si no existe cuenta habrá que crearla
	private Account obtainBankAccount(Integer registryBankId) {
		try {
			IManagerBean rBankAccountBean = BeanManager.getManagerBean(RegistryBankAccount.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(rBankAccountBean.getFieldName(IAccountBridgeAlias.REGISTRY_BANK_ACCOUNT_REGISTRY_BANK_ID), registryBankId);
			Iterator iter = rBankAccountBean.getList(criteria).iterator();
			if(iter.hasNext()){
				return ((RegistryBankAccount)iter.next()).getAccount();
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining rBankAccount to registryBank with id= " + registryBankId, e);
		}
		return null;
	}
	
	private void insertAccountEntry(Finance finance, Account account, Account bankAccount, Date paymentDate) {
		try {
			IManagerBean accountEntryBean = BeanManager.getManagerBean(AccountEntry.class);
			IManagerBean accountEntryDetailBean = BeanManager.getManagerBean(AccountEntryDetail.class);
			AccountEntry entry = new AccountEntry();
			// TODO pedir period ¿¿??
			entry.setAccountPeriod("¿¿¿¿¿¿¿?????????");
			entry.setEntryDate(paymentDate);
			// TODO falta CHARGE ¿¿??
			entry.setType(AccountEntryType.PAYMENT);
			entry = (AccountEntry)accountEntryBean.insert(entry);
			// Insertar 1º asiento
			AccountEntryDetail entryDetail = new AccountEntryDetail();
			entryDetail.setAccount(account);
			entryDetail.setBalancingAccount(bankAccount);
			if(finance.getInvoice().getType().equals(InvoiceType.SALES)){
				entryDetail.setCredit(finance.getAmount());
			}else{
				entryDetail.setDebit(finance.getAmount());
			}
			// TODO qué meter en el concept ¿¿??
			entryDetail.setConcept("");
			accountEntryDetailBean.insert(entryDetail);
			// Insertar 2º asiento
			entryDetail = new AccountEntryDetail();
			entryDetail.setAccount(bankAccount);
			entryDetail.setBalancingAccount(account);
			if(finance.getInvoice().getType().equals(InvoiceType.SALES)){
				entryDetail.setDebit(finance.getAmount());
			}else{
				entryDetail.setCredit(finance.getAmount());
			}
			// TODO qué meter en el concept ¿¿??
			entryDetail.setConcept("");
			accountEntryDetailBean.insert(entryDetail);
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error inserting accountEntry for finance with id = " + finance.getId(), e);
		}
	}
}
