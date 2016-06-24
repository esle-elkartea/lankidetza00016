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
import com.code.aon.finance.enumeration.FinanceTrackingType;

/**
 * Transfer Object that represents a FinanceTracking.
 */
@Entity
@Table(name="finance_tracking")
public class FinanceTracking implements ITransferObject {
	
	/** The id. */
	private Integer id;
	
	/** The finance. */
	private Finance finance;
	
	/** The tracking date. */
	private Date trackingDate;
	
	/** The amount. */
	private double amount;
	
	/** The description. */
	private String description;
	
	/** The type. */
	private FinanceTrackingType type;

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(nullable=false)
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
	 * Gets the finance.
	 * 
	 * @return the finance
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="finance", nullable=false)
	public Finance getFinance() {
		return finance;
	}

	/**
	 * Sets the finance.
	 * 
	 * @param finance the finance
	 */
	public void setFinance(Finance finance) {
		this.finance = finance;
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
	 * Gets the tracking date.
	 * 
	 * @return the tracking date
	 */
	@Column(name="tracking_date", nullable=false)
	public Date getTrackingDate() {
		return trackingDate;
	}

	/**
	 * Sets the tracking date.
	 * 
	 * @param trackingDate the tracking date
	 */
	public void setTrackingDate(Date trackingDate) {
		this.trackingDate = trackingDate;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	@Column(length=128)
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
	 * Gets the type.
	 * 
	 * @return the type
	 */
	@Column(nullable=false)
	public FinanceTrackingType getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type the type
	 */
	public void setType(FinanceTrackingType type) {
		this.type = type;
	}
}