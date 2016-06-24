package com.code.aon.ui.finance.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.finance.Finance;
import com.code.aon.finance.FinanceTracking;
import com.code.aon.finance.dao.IFinanceAlias;
import com.code.aon.finance.enumeration.FinanceStatus;
import com.code.aon.ql.Criteria;
import com.code.aon.ql.util.ExpressionException;
import com.code.aon.ui.form.BasicController;

/**
 * Controller used in the finance maintenance.
 * 
 */
public class FinanceController extends BasicController {
	
	/** Determines if the finance is a payment or a charge. */
	private Boolean payment;
	
	/**
	 * A list of finances currently checked
	 */
	private ArrayList<Finance> checks= new ArrayList<Finance>();
	
	private Integer registryBankId;
	
	private Date paymentDate;

	/**
	 * Gets if the finance is a payment or a charge.
	 * 
	 * @return true if the finance is a payment
	 */
	public Boolean getPayment() {
		return payment;
	}

	/**
	 * Sets if the finance is a payment or a charge.
	 * 
	 * @param payment true if the finance is a payment
	 */
	public void setPayment(Boolean payment) {
		this.payment = payment;
	}
	
	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Integer getRegistryBankId() {
		return registryBankId;
	}

	public void setRegistryBankId(Integer registryBankId) {
		this.registryBankId = registryBankId;
	}

	/**
	 * If the value changed sets the row to be checked or not.
	 * true to add or false to remove
	 * 
	 * @param event the event that is launched by value change
	 */
	public void rowSelected(ValueChangeEvent event){
		if(event.getNewValue() != null){
			setRowChecked(((Boolean)event.getNewValue()).booleanValue());
		}
	}
	
	/**
	 * Determines if the current row is selected or not
	 * 
	 * @return true if the current row is selected
	 */
	public boolean getRowChecked() {
		Finance to = (Finance) model.getRowData();
		return checks.contains( to );
	}
	
	/**
	 * Adds or removes a Delivery in the checks list
	 * 
	 * @param rowChecked true to add and false to remove
	 */
	public void setRowChecked(boolean rowChecked) {
		if ( rowChecked ) {
			Finance to = (Finance) model.getRowData();
			if (!checks.contains( to )) {
				checks.add( to );
			}
		} else {
			Finance to = (Finance) model.getRowData();
			if (checks.contains( to )) {
				checks.remove( to );
			}
		}
	}
	
	public ArrayList<Finance> getCheckedFinances() {
		return checks;
	}
	
	public void clearCheckedFinances(){
		checks = new ArrayList<Finance>();
	}

	@Override
	public void onEditSearch(ActionEvent event) {
		setPayment(null);
		super.onEditSearch(event);
	}

	/**
	 * Adds to criteria the finance_payment, depending on the value of <code>payment</code>.
	 * 
	 * @param event the event
	 * 
	 * @throws ManagerBeanException the manager bean exception
	 */
	public void onPaymentChange(ValueChangeEvent event) throws ManagerBeanException{
		if(event.getNewValue() != null){
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(getManagerBean().getFieldName(IFinanceAlias.FINANCE_PAYMENT), event.getNewValue());
			this.setCriteria(criteria);
		}
	}

	/**
	 * Adds to criteria the finance status.
	 * 
	 * @param event the event that contains the new value
	 * 
	 * @throws ManagerBeanException the manager bean exception
	 */
	public void addStatusExpression(ValueChangeEvent event) throws ManagerBeanException{
		Criteria criteria = getCriteria();
		FinanceStatus status = (((Boolean)event.getNewValue()).booleanValue()?FinanceStatus.PENDING:FinanceStatus.PAID);
		criteria.addEqualExpression(getManagerBean().getFieldName(IFinanceAlias.FINANCE_FINANCE_STATUS), status);
		setCriteria(criteria);
	}
	
	/**
	 * Adds to criteria the registry ident.
	 * 
	 * @param event the event that contains the new value
	 * 
	 * @throws ManagerBeanException the manager bean exception
	 * @throws ExpressionException the expression exception
	 */
	public void addRegistryExpression(ValueChangeEvent event) throws ManagerBeanException, ExpressionException {
		if(event.getNewValue() != null && !event.getNewValue().equals("")) {
			Criteria c = getCriteria();
			c.addExpression(getFieldName(IFinanceAlias.FINANCE_REGISTRY_ID), event.getNewValue().toString());
			setCriteria(c);
		}
	}
	
	/**
	 * Adds to criteria the generic equal expression.
	 * 
	 * @param event the event that contains the new value
	 * 
	 * @throws ManagerBeanException the manager bean exception
	 * @throws ExpressionException the expression exception
	 */
	public void addEqualExpression(ValueChangeEvent event) throws ManagerBeanException, ExpressionException {
	    if (event.getNewValue() != null && !event.getNewValue().equals(new Integer(Integer.MAX_VALUE))) {
	    	Criteria c = getCriteria();
			Object value = event.getNewValue();
			c.addExpression(getFieldName(event.getComponent().getId()), value.toString());
			setCriteria(c);
		}
	}
	
	/**
	 * Adds to criteria the invoice issueDate. Greater than or equal
	 * 
	 * @param event the event that contains the new value
	 * @throws ManagerBeanException
	 */
	public void addInvoiceIssueDate1Expression(ValueChangeEvent event) throws ManagerBeanException {
	    if (event.getNewValue() != null) {
	    	Criteria c = getCriteria();
			Object value = event.getNewValue();
			c.addGreaterThanOrEqualExpression(getFieldName(IFinanceAlias.FINANCE_INVOICE_ISSUE_DATE), value);
			setCriteria(c);
		}
	}

	/**
	 * Adds to criteria the finance dueDate. Greater than or equal
	 * 
	 * @param event the event that contains the new value
	 * @throws ManagerBeanException
	 */
	public void addFinanceDueDate1Expression(ValueChangeEvent event) throws ManagerBeanException {
	    if (event.getNewValue() != null) {
			Object value = event.getNewValue();
			Criteria c = getCriteria();
			c.addGreaterThanOrEqualExpression(getFieldName(IFinanceAlias.FINANCE_DUE_DATE), value);
			setCriteria(c);
		}
	}
	
	/**
	 * Adds to criteria the invoice issueDate. Less than or equal
	 * 
	 * @param event the event that contains the new value
	 * @throws ManagerBeanException
	 */
	public void addInvoiceIssueDate2Expression(ValueChangeEvent event) throws ManagerBeanException {
	    if (event.getNewValue() != null) {
			Object value = event.getNewValue();
			Criteria c = getCriteria();
			c.addLessThanOrEqualExpression(getFieldName(IFinanceAlias.FINANCE_INVOICE_ISSUE_DATE), value);
			setCriteria(c);
		}
	}
	
	/**
	 * Adds to criteria the finance dueDate. Less than or equal
	 * 
	 * @param event the event that contains the new value
	 * @throws ManagerBeanException
	 */
	public void addFinanceDueDate2Expression(ValueChangeEvent event) throws ManagerBeanException {
	    if (event.getNewValue() != null) {
			Object value = event.getNewValue();
			Criteria c = getCriteria();
			c.addLessThanOrEqualExpression(getFieldName(IFinanceAlias.FINANCE_DUE_DATE), value);
			setCriteria(c);
		}
	}
	
	/**
	 * Adds to criteria the bank.
	 * 
	 * @param event the event that contains the new value
	 * @throws ManagerBeanException
	 * @throws ExpressionException 
	 */
	public void addBankExpression(ValueChangeEvent event) throws ManagerBeanException, ExpressionException {
	    if (event.getNewValue() != null && !event.getNewValue().equals("")) {
			Object value = event.getNewValue();
			Criteria c = getCriteria();
			c.addExpression(getFieldName(IFinanceAlias.FINANCE_BANK_ID), value.toString());
			setCriteria(c);
		}
	}
	
	public double getPendingAmount() {
		try {
			Finance finance = (Finance)this.getTo();
			double pendingAmount = finance.getAmount();
			IManagerBean financeTrackingBean = BeanManager.getManagerBean(FinanceTracking.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(financeTrackingBean.getFieldName(IFinanceAlias.FINANCE_TRACKING_FINANCE_ID), finance.getId());
			Iterator iter = financeTrackingBean.getList(criteria).iterator();
			while(iter.hasNext()){
				FinanceTracking tracking = (FinanceTracking)iter.next();
				pendingAmount = pendingAmount - tracking.getAmount();
			}
			return pendingAmount;
		} catch (ManagerBeanException e) {
			
		}
		return 0;
	}
	
}