package com.code.aon.ui.finance.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ITransferObject;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.finance.Invoice;
import com.code.aon.finance.InvoiceDetail;
import com.code.aon.finance.dao.IFinanceAlias;
import com.code.aon.finance.enumeration.InvoiceSource;
import com.code.aon.finance.enumeration.InvoiceStatus;
import com.code.aon.product.strategy.IPriceStrategy;
import com.code.aon.product.strategy.PriceStrategyFactory;
import com.code.aon.purchase.Supplier;
import com.code.aon.purchase.dao.IPurchaseAlias;
import com.code.aon.ql.Criteria;
import com.code.aon.registry.Registry;
import com.code.aon.ui.form.BasicController;
import com.code.aon.ui.menu.jsf.MenuEvent;
import com.code.aon.ui.report.OutputFormat;
import com.code.aon.ui.report.controller.ReportManager;
import com.code.aon.ui.util.AonUtil;
import com.code.aon.ui.warehouse.controller.IncomeController;
import com.code.aon.warehouse.Income;
import com.code.aon.warehouse.IncomeDetail;
import com.code.aon.warehouse.dao.IWarehouseAlias;
import com.code.aon.warehouse.enumeration.IncomeStatus;

/**
 * The invoicing controller 
 * 
 * @author Consulting & Development.
 * @since 1.0
 *
 */
public class InvoicingController extends BasicController {
	
	/**
	 * The logger of the class
	 */
	private static final Logger LOGGER = Logger.getLogger(InvoicingController.class.getName());
	
	/**
	 * Income controller's name
	 */
	private static final String INCOME_CONTROLLER_NAME = "income";

	/**
	 * IncomeDetail controller's name
	 */
	private static final String INVOICING_DETAIL_CONTROLLER_NAME = "invoicingDetail";
	
	/**
	 * Price strategy
	 */
	private IPriceStrategy priceStrategy;
	
	/**
	 * Warehouse ident
	 */
	private Integer warehouseId;
	
	/**
	 * Returns the warehouse ident
	 * 
	 * @return warehouse ident
	 */
	public Integer getWarehouseId() {
		return warehouseId;
	}

	/**
	 * Assigns the warehouse ident
	 * 
	 * @param warehouseId warehouse ident
	 */
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * Resets the controller
	 * 
	 * @param event menu event
	 */
	@SuppressWarnings("unused")
	public void onReset(MenuEvent event){
		super.onReset(null);
	}
	
	public boolean isEditable() {
		if(this.getTo() != null){
			if(((Invoice)this.getTo()).getStatus() == null){
				return true;
			}
			return ((Invoice)this.getTo()).getStatus().equals(InvoiceStatus.PENDING);
		}
		return false;
	}
	
	/**
	 * Recovers the supplier if exists
	 * and creates a new registry with suppliers data 
	 * to assign to the Invoice
	 * 
	 * @param event contains a supplier ident
	 * @throws ManagerBeanException
	 */
	public void supplierData(ValueChangeEvent event) throws ManagerBeanException{
		if(event.getNewValue() != null){
			IManagerBean supplierBean = BeanManager.getManagerBean(Supplier.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(supplierBean.getFieldName(IPurchaseAlias.SUPPLIER_ID),event.getNewValue());
			Iterator iter = supplierBean.getList(criteria).iterator();
			if(iter.hasNext()){
				Supplier supplier = (Supplier)iter.next();
				Registry registry = new Registry();
				registry.setAlias(supplier.getAlias());
				registry.setDocument(supplier.getDocument());
				registry.setName(supplier.getName());
				registry.setSurname(supplier.getSurname());
				((Invoice)this.getTo()).setRegistry(registry);
			}
		}
	}
	
	/**
	 * Transfers checked incomes to invoicedetails, and updates incomes as closed
	 * 
	 * @param event the action event
	 * @throws ManagerBeanException
	 */
	@SuppressWarnings("unused")
	public void onInvoice(ActionEvent event) throws ManagerBeanException{
		IncomeController incomeController = (IncomeController)AonUtil.getController(INCOME_CONTROLLER_NAME);
		Iterator iter = ((List)incomeController.getModel().getWrappedData()).iterator();
		while(iter.hasNext()){
			Income income = (Income)iter.next();
			if(incomeController.isChecked(income)){
				IManagerBean incomeBean = BeanManager.getManagerBean(Income.class);
				insertInvoiceDetails((Invoice)this.getTo(), income);
				income.setIncomeStatus(IncomeStatus.CLOSED);
				incomeBean.update(income);
			}
		}
		incomeController.clearCheckList();
		InvoicingDetailController invoicingDetailController = (InvoicingDetailController) AonUtil.getController(INVOICING_DETAIL_CONTROLLER_NAME);
		invoicingDetailController.onSearch(null);
	}
	
	/**
	 * Updates the income as pending, and removes the invoicedetails
	 * 
	 * @param event
	 * @throws ManagerBeanException
	 */
	@SuppressWarnings("unused")
	public void onRemoveIncome(ActionEvent event) throws ManagerBeanException{
		IncomeController incomeController = (IncomeController)AonUtil.getController(INCOME_CONTROLLER_NAME);
		Income income = (Income)incomeController.getModel().getRowData();
		removeInvoiceDetails(income.getId());
		IManagerBean incomeBean = BeanManager.getManagerBean(Income.class);
		income.setIncomeStatus(IncomeStatus.PENDING);
		incomeBean.update(income);
		InvoicingDetailController invoicingDetailController = (InvoicingDetailController) AonUtil.getController(INVOICING_DETAIL_CONTROLLER_NAME);
		invoicingDetailController.onSearch(null);
		incomeController.clearCheckList();
	}

	/**
	 * Recovers all IncomeDetails of this income and transfers to 
	 * the invoice as invoicedetails
	 * 
	 * @param invoice
	 * @param income
	 */
	private void insertInvoiceDetails(Invoice invoice, Income income) {
		try {
			IManagerBean incomeDetailBean = BeanManager.getManagerBean(IncomeDetail.class);
			IManagerBean invoiceDetailBean = BeanManager.getManagerBean(InvoiceDetail.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(incomeDetailBean.getFieldName(IWarehouseAlias.INCOME_DETAIL_INCOME_ID), income.getId());
			Iterator iter = incomeDetailBean.getList(criteria).iterator();
			while(iter.hasNext()){
				IncomeDetail incomeDetail = (IncomeDetail)iter.next();
				InvoiceDetail invoiceDetail = new InvoiceDetail();
				invoiceDetail.setDeliveryDetail(incomeDetail.getId());
				invoiceDetail.setDescription(incomeDetail.getDescription());
				invoiceDetail.setDiscountExpression(incomeDetail.getDiscountExpression());
				invoiceDetail.setInvoice(invoice);
				invoiceDetail.setItem(incomeDetail.getItem());
				invoiceDetail.setPrice(incomeDetail.getPrice());
				invoiceDetail.setQuantity(incomeDetail.getQuantity());
				invoiceDetail.setSource(InvoiceSource.INCOME);
				invoiceDetail.setTaxes(incomeDetail.getPurchaseDetail().getTaxes());
				invoiceDetail.setTaxableBase(obtainTaxableBase(invoiceDetail));
				invoiceDetailBean.insert(invoiceDetail);
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "error inserting invoiceDetails for invoice with id= " + invoice.getId(), e);
		}
	}
	
	/**
	 * Asks the price strategy the taxable base
	 * 
	 * @param invoiceDetail the InvoiceDetail line
	 * @return the taxable base
	 */
	private double obtainTaxableBase(InvoiceDetail invoiceDetail) {
		return getPriceStrategy().getBasePrice(invoiceDetail);
	}
	
	/**
	 * Ask price strategy the total price of the transfer object invoice
	 * 
	 * @return the total price
	 * @throws ManagerBeanException
	 */
	public double getToInvoiceTotalPrice() throws ManagerBeanException{
		Invoice invoice = (Invoice)this.getTo();
		return getPriceStrategy().getTotalPrice(invoice,invoice);
	}
	
	/**
	 * Ask price strategy the total price of the selected row invoice
	 * 
	 * @return the total price
	 * @throws ManagerBeanException
	 */
	public double getInvoiceTotalPrice() throws ManagerBeanException{
		Invoice invoice = (Invoice)this.getModel().getRowData();
		return getPriceStrategy().getTotalPrice(invoice,invoice);
	}
	
	/**
	 * Returns price strategy
	 * 
	 * @return price strategy
	 */
	public IPriceStrategy getPriceStrategy(){
		if(priceStrategy == null){
			priceStrategy = PriceStrategyFactory.getPriceStrategy();
		}
		return priceStrategy;
	}

	/**
	 * Recovers all incomedetails with this income ident
	 * and removes all invoicedetail linked to this
	 * 
	 * @param incomeId the income ident
	 */
	public void removeInvoiceDetails(Integer incomeId) {
		try {
			IManagerBean incomeDetailBean = BeanManager.getManagerBean(IncomeDetail.class);
			IManagerBean invoiceDetailBean = BeanManager.getManagerBean(InvoiceDetail.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(incomeDetailBean.getFieldName(IWarehouseAlias.INCOME_DETAIL_INCOME_ID), incomeId);
			Iterator iter = incomeDetailBean.getList(criteria).iterator();
			while(iter.hasNext()){
				IncomeDetail incomeDetail = (IncomeDetail)iter.next();
				criteria = new Criteria();
				criteria.addEqualExpression(invoiceDetailBean.getFieldName(IFinanceAlias.INVOICE_DETAIL_DELIVERY_DETAIL), incomeDetail.getId());
				Iterator iterator = invoiceDetailBean.getList(criteria).iterator();
				if(iterator.hasNext()){
					invoiceDetailBean.remove((ITransferObject)iterator.next());
				}
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error removing invoiceDetails linked with income with id= " + incomeId, e);
		}
	}
	
	/**
	 * Returns invoice collection
	 * 
	 * @see com.code.aon.ui.form.BasicController#getCollection()
	 */
	public Collection getCollection(){
		List<ITransferObject> l = new LinkedList<ITransferObject>();
		l.add(obtainInvoice(((Invoice)this.getTo()).getId()));
		return l;
	}
	
	/**
	 * Returns the invoice of this invoice ident
	 * 
	 * @param invoiceId invoice ident
	 * @return related invoice
	 */
	private ITransferObject obtainInvoice(Integer invoiceId) {
		try {
			IManagerBean invoiceBean = BeanManager.getManagerBean(Invoice.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(invoiceBean.getFieldName(IFinanceAlias.INVOICE_ID), invoiceId);
			Iterator iter = invoiceBean.getList(criteria).iterator();
			if(iter.hasNext()){
				return (Invoice)iter.next();
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining invoice with id= " + invoiceId, e);
		}
		return null;
	}
	
	/**
	 * Adds to criteria the invoice date. Greater than or equal
	 * 
	 * @param event the event that contains the new value
	 * @throws ManagerBeanException
	 */
	public void addIssuedate1Expression(ValueChangeEvent event)
		throws ManagerBeanException {
	    if (event.getNewValue() != null) {
	    	Criteria c = getCriteria();
			Object value = event.getNewValue();
			c.addGreaterThanOrEqualExpression(getFieldName(IFinanceAlias.INVOICE_ISSUE_DATE), value);
			setCriteria(c);
		}
	}
	
	/**
	 * Adds to criteria the invoice date. Less than or equal
	 * 
	 * @param event the event that contains the new value
	 * @throws ManagerBeanException
	 */
	public void addIssuedate2Expression(ValueChangeEvent event)
		throws ManagerBeanException {
	    if (event.getNewValue() != null) {
	    	Criteria c = getCriteria();
			Object value = event.getNewValue();
			c.addLessThanOrEqualExpression(getFieldName(IFinanceAlias.INVOICE_ISSUE_DATE), value);
			setCriteria(c);
		}
	}
	
	/**
	 * Adds to the criteria the supplier
	 * 
	 * @param event contains supplier's registry ident
	 */
	public void addSupplierEqualExpression(ValueChangeEvent event){
		if(event.getNewValue() != null && !((String)event.getNewValue()).trim().equals("")){
			try{
				Integer id = new Integer((String)event.getNewValue());
				Criteria criteria = getCriteria();
				criteria.addEqualExpression(getManagerBean().getFieldName(IFinanceAlias.INVOICE_REGISTRY_ID), id);
				setCriteria(criteria);
			} catch (Exception e) {
			}
		}
	}

    /**
     * Sets default parameters to report.
     * 
     * @param event that launched report
     */
	@SuppressWarnings("unused")
    public void onReport(ActionEvent event) {
        ReportManager manager = (ReportManager)AonUtil.getRegisteredBean("report");
        manager.setReportKey("invoice");
        manager.setOutputFormat(OutputFormat.PDF);
    }

}
