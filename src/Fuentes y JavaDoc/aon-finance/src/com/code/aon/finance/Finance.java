package com.code.aon.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.code.aon.common.ITransferObject;
import com.code.aon.common.enumeration.SecurityLevel;
import com.code.aon.finance.enumeration.FinanceStatus;
import com.code.aon.registry.Registry;

/**
 * Transfer Object that represents a Finance.
 * 
 * @author jurkiri
 */
@Entity
@Table(name = "finance")
public class Finance implements ITransferObject{
	
	/** The id. */
	private Integer id;
	
	/** The payment. */
	private boolean payment;
	
	/** The registry. */
	private Registry registry;
	
	/** The amount. */
	private double amount;
	
	/** The concept. */
	private String concept;
	
	/** The invoice. */
	private Invoice invoice;
	
	/** The due date. */
	private Date dueDate;
	
	/** The pay method. */
	private PayMethod payMethod;
	
	/** The bank. */
	private Bank bank;
	
	/** The bank account. */
	private String bankAccount;
	
	/** The finance status. */
	private FinanceStatus financeStatus;
	
	/** The security level. */
	private SecurityLevel securityLevel;
	

	/**
	 * The Constructor. Sets TODAY to dueDate.
	 */
	public Finance() {
		this.dueDate = new Date();
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param primaryKey the primary key
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Gets the amount.
	 * 
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 * 
	 * @param amount the amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the bank.
	 * 
	 * @return the bank
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bank")
	public Bank getBank() {
		return bank;
	}

	/**
	 * Sets the bank.
	 * 
	 * @param bank the bank
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	/**
	 * Gets the bank account.
	 * 
	 * @return the bank account
	 */
	@Column(name="bank_account", length=30)
	public String getBankAccount() {
		return bankAccount;
	}

	/**
	 * Sets the bank account.
	 * 
	 * @param bankAccount the bank account
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * Gets the concept.
	 * 
	 * @return the concept
	 */
	public String getConcept() {
		return concept;
	}

	/**
	 * Sets the concept.
	 * 
	 * @param concept the concept
	 */
	public void setConcept(String concept) {
		this.concept = concept;
	}

	/**
	 * Gets the due date.
	 * 
	 * @return the due date
	 */
	@Column(name="due_date")
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * Sets the due date.
	 * 
	 * @param dueDate the due date
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * Gets the finance status.
	 * 
	 * @return the finance status
	 */
	@Column(name = "status")
	public FinanceStatus getFinanceStatus() {
		return financeStatus;
	}

	/**
	 * Sets the finance status.
	 * 
	 * @param financeStatus the finance status
	 */
	public void setFinanceStatus(FinanceStatus financeStatus) {
		this.financeStatus = financeStatus;
	}

	/**
	 * Gets the invoice.
	 * 
	 * @return the invoice
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="invoice")
	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 * Sets the invoice.
	 * 
	 * @param invoice the invoice
	 */
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	/**
	 * Checks if is payment.
	 * 
	 * @return true, if is payment
	 */
	@Column(nullable = false)
	public boolean isPayment() {
		return payment;
	}

	/**
	 * Sets the payment.
	 * 
	 * @param payment the payment
	 */
	public void setPayment(boolean payment) {
		this.payment = payment;
	}

	/**
	 * Gets the pay method.
	 * 
	 * @return the pay method
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pay_method")
	public PayMethod getPayMethod() {
		return payMethod;
	}

	/**
	 * Sets the pay method.
	 * 
	 * @param payMethod the pay method
	 */
	public void setPayMethod(PayMethod payMethod) {
		this.payMethod = payMethod;
	}

	/**
	 * Gets the registry.
	 * 
	 * @return the registry
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="registry")
	public Registry getRegistry() {
		return registry;
	}

	/**
	 * Sets the registry.
	 * 
	 * @param registry the registry
	 */
	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	/**
	 * Gets the security level.
	 * 
	 * @return the security level
	 */
	@Column(name = "security_level")
	public SecurityLevel getSecurityLevel() {
		return securityLevel;
	}

	/**
	 * Sets the security level.
	 * 
	 * @param securityLevel the security level
	 */
	public void setSecurityLevel(SecurityLevel securityLevel) {
		this.securityLevel = securityLevel;
	}
}