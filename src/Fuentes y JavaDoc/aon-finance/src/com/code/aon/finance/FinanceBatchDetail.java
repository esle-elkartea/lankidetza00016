package com.code.aon.finance;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.code.aon.common.ITransferObject;

/**
 * Transfer Object that represents a FinanceBatchDetail.
 * 
 * @author Consulting & Development. Inigo Gayarre - 05-oct-2005
 * @since 1.0
 */
@Entity
@Table(name = "fbatch_detail")
public class FinanceBatchDetail implements ITransferObject {

    /** The id. */
    private Integer id;

    /** The finance batch. */
    private FinanceBatch financeBatch;

    /** The finance. */
    private Finance finance;

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
     * Gets the finance batch.
     * 
     * @return the finance batch
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fbatch", nullable = false)
	public FinanceBatch getFinanceBatch() {
		return financeBatch;
	}

	/**
	 * Sets the finance batch.
	 * 
	 * @param financeBatch the finance batch
	 */
	public void setFinanceBatch(FinanceBatch financeBatch) {
		this.financeBatch = financeBatch;
	}
	
	/**
	 * Gets the finance.
	 * 
	 * @return the finance
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="finance", nullable = false)
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
	 * Sets the id.
	 * 
	 * @param id the id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}