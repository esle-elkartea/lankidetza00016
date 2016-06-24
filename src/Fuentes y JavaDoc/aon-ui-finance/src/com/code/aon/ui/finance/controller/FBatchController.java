package com.code.aon.ui.finance.controller;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.finance.Finance;
import com.code.aon.finance.FinanceBatch;
import com.code.aon.finance.FinanceBatchDetail;
import com.code.aon.finance.dao.IFinanceAlias;
import com.code.aon.finance.enumeration.FinanceBatchStatus;
import com.code.aon.finance.enumeration.FinanceStatus;
import com.code.aon.ql.Criteria;
import com.code.aon.ql.util.ExpressionException;
import com.code.aon.ui.form.BasicController;
import com.code.aon.ui.form.LinesController;
import com.code.aon.ui.util.AonUtil;

/**
 * Controller used in the fbatch maintenance.
 * 
 */
public class FBatchController extends BasicController {
	
	private static final Logger LOGGER = Logger.getLogger(FBatchController.class.getName());
	
	private static final String FINANCE_CONTROLLER_NAME = "finance";
	
	private static final String FINANCE_BATCH_DETAIL_CONTROLLER_NAME = "fBatchDetail";
	
	/** Determines if the fbatch is a payment or a charge. */
	private Boolean payment;

	/**
	 * Gets if the fbatch is a payment or a charge.
	 * 
	 * @return true if the fbatch is a payment
	 */
	public Boolean getPayment() {
		return payment;
	}

	/**
	 * Sets if the fbatch is a payment or a charge.
	 * 
	 * @param payment true if the fbatch is a payment
	 */
	public void setPayment(Boolean payment) {
		this.payment = payment;
	}
	
	@Override
	public void onEditSearch(ActionEvent event) {
		setPayment(null);
		super.onEditSearch(event);
	}
	
	public boolean isRemovable(){
		if(this.getTo() != null){
			return ((FinanceBatch)this.getTo()).getFinanceBatchStatus().equals(FinanceBatchStatus.TODO);
		}
		return false;
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
	public void addIssueDate1Expression(ValueChangeEvent event) throws ManagerBeanException {
	    if (event.getNewValue() != null) {
	    	Criteria c = getCriteria();
			Object value = event.getNewValue();
			c.addGreaterThanOrEqualExpression(getFieldName(IFinanceAlias.FINANCE_BATCH_ISSUE_DATE), value);
			setCriteria(c);
		}
	}
	
	/**
	 * Adds to criteria the invoice issueDate. Less than or equal
	 * 
	 * @param event the event that contains the new value
	 * @throws ManagerBeanException
	 */
	public void addIssueDate2Expression(ValueChangeEvent event) throws ManagerBeanException {
	    if (event.getNewValue() != null) {
			Object value = event.getNewValue();
			Criteria c = getCriteria();
			c.addLessThanOrEqualExpression(getFieldName(IFinanceAlias.FINANCE_BATCH_ISSUE_DATE), value);
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
			c.addExpression(getFieldName(IFinanceAlias.FINANCE_BATCH_BANK_ID), value.toString());
			setCriteria(c);
		}
	}
	
	@SuppressWarnings("unused")
	public void onBatchSelected(ActionEvent event){
		FinanceBatch fBatch = (FinanceBatch)getTo();
		FinanceController financeController = (FinanceController)AonUtil.getController(FINANCE_CONTROLLER_NAME);
		Iterator iter = financeController.getCheckedFinances().iterator();
		try {
			IManagerBean financeBatchDetailBean = BeanManager.getManagerBean(FinanceBatchDetail.class);
			IManagerBean financeBean = BeanManager.getManagerBean(Finance.class);
			while(iter.hasNext()){
				Finance finance = (Finance)iter.next();
				FinanceBatchDetail fBatchDetail = new FinanceBatchDetail();
				fBatchDetail.setFinance(finance);
				fBatchDetail.setFinanceBatch(fBatch);
				financeBatchDetailBean.insert(fBatchDetail);
				finance.setFinanceStatus(FinanceStatus.BATCHED);
				financeBean.update(finance);
			}
			financeController.clearCheckedFinances();
			reloadFBatchDetailModel(fBatch);
			reloadPendingFinances(fBatch.isPayment());
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error adding selected finances to the financeBatch with id= " + fBatch.getId(), e);
		}
	}

	public void reloadFBatchDetailModel(FinanceBatch fbatch) {
		try {
			LinesController fBatchDetailController = (LinesController)AonUtil.getController(FINANCE_BATCH_DETAIL_CONTROLLER_NAME);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(fBatchDetailController.getFieldName(IFinanceAlias.FINANCE_BATCH_DETAIL_FINANCE_BATCH_ID), fbatch.getId());
			fBatchDetailController.setCriteria(criteria);
			fBatchDetailController.onSearch(null);
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error reloading FBatchDetail model", e);
		}
	}
	
	public void reloadPendingFinances(boolean payment) {
		try {
			FinanceController financeController = (FinanceController)AonUtil.getController(FINANCE_CONTROLLER_NAME);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(financeController.getFieldName(IFinanceAlias.FINANCE_PAYMENT), new Boolean(payment));
			criteria.addEqualExpression(financeController.getFieldName(IFinanceAlias.FINANCE_FINANCE_STATUS), FinanceStatus.PENDING);
			financeController.setCriteria(criteria);
			financeController.onSearch(null);
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error reloading Finance model", e);
		}
	}
}