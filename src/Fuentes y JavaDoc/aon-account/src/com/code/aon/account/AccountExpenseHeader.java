package com.code.aon.account;

import java.util.Date;

import com.code.aon.common.ITransferObject;
import com.code.aon.finance.RegistryBank;

/**
 * The Class AccountExpenseHeader.
 */
public class AccountExpenseHeader implements ITransferObject {
	
	/** The account. */
	private Account account;
	
	/** The date. */
	private Date date;
	
	/** The period. */
	private Period period;
	
	/** The description. */
	private String description;
	
	/** The amount. */
	private double amount;
	
	/** The Registry Bank. */
	private RegistryBank rBank;

	/**
	 * Gets the account.
	 * 
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Sets the account.
	 * 
	 * @param account the account
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 * 
	 * @param date the date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the period.
	 * 
	 * @return the period
	 */
	public Period getPeriod() {
		return period;
	}

	/**
	 * Sets the period.
	 * 
	 * @param period the period
	 */
	public void setPeriod(Period period) {
		this.period = period;
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
	 * Gets the Registry bank.
	 * 
	 * @return the Registry bank
	 */
	public RegistryBank getRBank() {
		return rBank;
	}

	/**
	 * Sets the Registry bank.
	 * 
	 * @param rBank the Registry
	 */
	public void setRBank(RegistryBank rBank) {
		this.rBank = rBank;
	}
}