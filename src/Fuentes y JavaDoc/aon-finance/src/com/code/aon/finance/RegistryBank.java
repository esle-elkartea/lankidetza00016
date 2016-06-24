package com.code.aon.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.code.aon.common.ITransferObject;
import com.code.aon.registry.Registry;

/**
 * Transfer Object that represents a union between a Registry and a Bank.
 * 
 * @author Consulting & Development. Eugenio Castellano - 31-ene-2005
 * @since 1.0
 */
@Entity
@Table(name ="rbank")
public class RegistryBank implements ITransferObject {

    /** The id. */
    private Integer id;

    /** The registry. */
    private Registry registry;

    /** The bank. */
    private Bank bank;

    /** The bank account. */
    private String bankAccount;

    /** The sufix. */
    private String sufix;

    /**
     * The empty constructor.
     */
    public RegistryBank() {
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
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the bank account.
     * 
     * @return the bank account
     */
    @Column(name = "bank_account")
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
     * Gets the bank.
     * 
     * @return the bank
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bank", nullable = false)
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
     * Gets the registry.
     * 
     * @return the registry
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="registry", nullable = false)
    public Registry getRegistry() {
        return registry;
    }

    /**
     * Sets the registry.
     * 
     * @param registry the registry
     */
    public void setRegistry(Registry registry) {
        this.registry = registry ;
    }

	/**
	 * Gets the sufix
	 * 
	 * @return the sufix
	 */
    @Column(length=3)
	public String getSufix() {
		return sufix;
	}

	/**
	 * Sets the sufix
	 * 
	 * @param sufix the sufix to set
	 */
	public void setSufix(String sufix) {
		this.sufix = sufix;
	}
    
    
}