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
import com.code.aon.finance.enumeration.FinanceBatchStatus;
import com.code.aon.finance.enumeration.FinanceBatchType;

/**
 * Transfer Object that represents a Finance Batch.
 * 
 * @author Consulting & Development. Inigo Gayarre - 05-oct-2005
 * @since 1.0
 */
@Entity
@Table(name = "fbatch")
public class FinanceBatch implements ITransferObject {

    /** The id. */
    private Integer id;

    /** The description. */
    private String description;
    
    /** The issue date. */
    private Date issueDate;
    
    /** The finance batch type. */
    private FinanceBatchType financeBatchType;

    /** The finance batch status. */
    private FinanceBatchStatus financeBatchStatus;

    /** The bank. */
    private Bank bank;
    
    /** The bank account. */
    private String bankAccount;
    
    private boolean payment;

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
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
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
	 * @param bank the bank account
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the finance batch status.
	 * 
	 * @return the finance batch status
	 */
	@Column(name = "status")
	public FinanceBatchStatus getFinanceBatchStatus() {
		return financeBatchStatus;
	}

	/**
	 * Sets the finance batch status.
	 * 
	 * @param financeBatchStatus the finance batch status
	 */
	public void setFinanceBatchStatus(FinanceBatchStatus financeBatchStatus) {
		this.financeBatchStatus = financeBatchStatus;
	}

	/**
	 * Gets the finance batch type.
	 * 
	 * @return the finance batch type
	 */
	@Column(name = "type")
	public FinanceBatchType getFinanceBatchType() {
		return financeBatchType;
	}

	/**
	 * Sets the finance batch type.
	 * 
	 * @param financeBatchType the finance batch type
	 */
	public void setFinanceBatchType(FinanceBatchType financeBatchType) {
		this.financeBatchType = financeBatchType;
	}

	/**
	 * Gets the issue date.
	 * 
	 * @return the issue date
	 */
	@Column(name="issue_date")
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * Sets the issue date.
	 * 
	 * @param issueDate the issue date
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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
}