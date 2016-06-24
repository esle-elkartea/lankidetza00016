package com.code.aon.finance.event;

import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.common.event.ManagerBeanEvent;
import com.code.aon.common.event.ManagerBeanListenerAdapter;
import com.code.aon.finance.InvoiceDetail;
import com.code.aon.finance.InvoiceTax;
import com.code.aon.finance.dao.IFinanceAlias;
import com.code.aon.finance.enumeration.InvoiceSource;
import com.code.aon.product.Tax;
import com.code.aon.product.TaxDetail;
import com.code.aon.product.dao.IProductAlias;
import com.code.aon.ql.Criteria;

/**
 * The InvoiceDetailBeanListener. Listener to be added to InvoiceDetail.class
 */
public class InvoiceDetailBeanListener extends ManagerBeanListenerAdapter {
	
	/** The LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(InvoiceDetailBeanListener.class.getName());

	/**
	 * Bean inserted. Inserts the related InvoiceTax when an InvoiceDetail is added.
	 * 
	 * @param evt the evt
	 * 
	 * @throws ManagerBeanException the manager bean exception
	 */
	@Override
	public void beanInserted(ManagerBeanEvent evt) throws ManagerBeanException {
		InvoiceDetail invoiceDetail = (InvoiceDetail)evt.getTo();
		if(!invoiceDetail.getSource().equals(InvoiceSource.ACCOUNT)){
			InvoiceTax invoiceTax = getInvoiceTax(invoiceDetail, false);
			IManagerBean invoiceTaxBean = BeanManager.getManagerBean(InvoiceTax.class);
			invoiceTaxBean.insert(invoiceTax);
		}
	}
	
	/**
	 * Bean updated. Updates the related InvoiceTax when an InvoiceDetail is updated.
	 * 
	 * @param evt the evt
	 * 
	 * @throws ManagerBeanException the manager bean exception
	 */
	@Override
	public void beanUpdated(ManagerBeanEvent evt) throws ManagerBeanException {
		InvoiceDetail invoiceDetail = (InvoiceDetail)evt.getTo();
		if(!invoiceDetail.getSource().equals(InvoiceSource.ACCOUNT)){
			InvoiceTax invoiceTax = getInvoiceTax(invoiceDetail, true);
			IManagerBean invoiceTaxBean = BeanManager.getManagerBean(InvoiceTax.class);
			invoiceTaxBean.update(invoiceTax);
		}
	}
	
	/**
	 * Gets the invoiceTax related with the parameter invoiceDetail and completes its surchage and percentage.
	 * 
	 * @param invoiceDetail the invoice detail
	 * @param dataBase if the invoiceDetail has to be retrieved from the database
	 * 
	 * @return the invoice tax
	 */
	private InvoiceTax getInvoiceTax(InvoiceDetail invoiceDetail, boolean dataBase) {
		Date date = invoiceDetail.getInvoice().getIssueDate();
		Tax tax  = new Tax();
		if(date.after(invoiceDetail.getItem().getProduct().getVat().getStartDate())){
			tax = invoiceDetail.getItem().getProduct().getVat();
		}else{
			tax = obtainTax(invoiceDetail.getItem().getProduct().getVat().getId(),date);
		}
		InvoiceTax invoiceTax = null;
		if(dataBase){
			invoiceTax = obtainInvoiceTax(invoiceDetail);
		}else{
			invoiceTax = new InvoiceTax();
		}
		invoiceTax.setInvoiceDetail(invoiceDetail);
		invoiceTax.setTaxType(tax.getType());
		double surcharge = 0.0;
		double percentage = 0.0;
		if(!invoiceDetail.getInvoice().isTaxFree()){
			percentage = tax.getPercentage();
			if(invoiceDetail.getInvoice().isSurcharge()){
				surcharge = tax.getSurcharge();
			}
		}
		invoiceTax.setPercentage(percentage);
		invoiceTax.setSurcharge(surcharge);
		return invoiceTax;
	}

	/**
	 * Gets the invoiceTax related with the parameter invoiceDetail
	 * 
	 * @param invoiceDetail the invoice detail
	 * 
	 * @return the invoice tax
	 */
	private InvoiceTax obtainInvoiceTax(InvoiceDetail invoiceDetail) {
		try {
			IManagerBean invoiceTaxBean = BeanManager.getManagerBean(InvoiceTax.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(invoiceTaxBean.getFieldName(IFinanceAlias.INVOICE_TAX_INVOICE_DETAIL_ID), invoiceDetail.getId());
			Iterator iter = invoiceTaxBean.getList(criteria).iterator();
			if(iter.hasNext()){
				return (InvoiceTax)iter.next();
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "error obtaining invoiceTax for invoiceDetail with id= " + invoiceDetail.getId(), e);
		}
		return new InvoiceTax();
	}

	/**
	 * Gets the Tax with id equals to the parameter id, and valid with the date passed as parameter.
	 * 
	 * @param date the date
	 * @param id the id
	 * 
	 * @return the tax
	 */
	private Tax obtainTax(Integer id, Date date) {
		try {
			IManagerBean taxDetailBean = BeanManager.getManagerBean(TaxDetail.class);
        	Criteria criteria = new Criteria();
        	criteria.addEqualExpression(taxDetailBean.getFieldName(IProductAlias.TAX_DETAIL_TAX_ID),id);
        	criteria.addLessThanExpression(taxDetailBean.getFieldName(IProductAlias.TAX_DETAIL_START_DATE),date);
        	criteria.addGreaterThanExpression(taxDetailBean.getFieldName(IProductAlias.TAX_DETAIL_END_DATE),date);
        	Iterator iter = taxDetailBean.getList(criteria).iterator();
        	while(iter.hasNext()){
        		TaxDetail taxDetail = (TaxDetail)iter.next();
        		Tax tax = new Tax();
        		tax.setId(taxDetail.getTax().getId());
        		tax.setPercentage(taxDetail.getValue());
        		tax.setSurcharge(taxDetail.getSurcharge());
        		tax.setType(taxDetail.getTax().getType());
        		return tax;
        	}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error getting tax for category with id= " + id, e);
		}
		return null;
	}
}
