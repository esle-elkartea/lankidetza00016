package com.code.aon.finance;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IHeaderObject;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ITransferObject;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.common.enumeration.SecurityLevel;
import com.code.aon.finance.dao.IFinanceAlias;
import com.code.aon.finance.enumeration.InvoiceStatus;
import com.code.aon.finance.enumeration.InvoiceType;
import com.code.aon.product.strategy.ICalculableContainer;
import com.code.aon.product.util.DiscountExpression;
import com.code.aon.ql.Criteria;
import com.code.aon.registry.ITaxInfo;
import com.code.aon.registry.Registry;
import com.code.aon.registry.RegistryAddress;

/**
 * Transfer Object that represents an Invoice.
 * 
 * @author Consulting & Development. I�igo Gayarre - 13-sep-2005
 * @since 1.0
 * @hibernate.class table "invoice"
 */
@Entity
@Table(name = "invoice")
public class Invoice implements ITransferObject, IHeaderObject, ICalculableContainer, ITaxInfo {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(Invoice.class.getName());
	
	/**
	 * The Constructor. Sets TODAY to issueDate.
	 */
	public Invoice() {
		this.issueDate = new Date();
	}

    /** The id. */
    private Integer id;

    /** The serie. */
    private String series;

    /** The number. */
    private int number;

    /** The registry. */
    private Registry registry;
    
    /** The name of the registry. */
    private String registryName;
    
    /** The document of the registry. */
    private String registryDocument;

    /** The registry address. */
    private RegistryAddress registryAddress;
    
    /** The issue date. */
    private Date issueDate;

    /** The security level. */
    private SecurityLevel securityLevel;

    /** The status. */
    private InvoiceStatus status;
    
    /** The type. */
    private InvoiceType type;
    
    /** The tax free. */
    private boolean taxFree;

    /** The surcharge. */
    private boolean surcharge;
    
    /** The detail of this invoice. */
	private Set<InvoiceDetail> lines = new HashSet<InvoiceDetail>();

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
        this.registry = registry;
    }
    
    /**
     * Gets the registry name.
     * 
     * @return the registry name
     */
    @Column(name="rname", length=128)
    public String getRegistryName() {
		return registryName;
	}

	/**
	 * Sets the registry name.
	 * 
	 * @param registryName the registry name
	 */
	public void setRegistryName(String registryName) {
		this.registryName = registryName;
	}

    /**
     * Gets the registry document.
     * 
     * @return the registry document
     */
	@Column(name="rdocument", length=16)
    public String getRegistryDocument() {
		return registryDocument;
	}

	/**
	 * Sets the registry document.
	 * 
	 * @param registryDocument the registry document
	 */
	public void setRegistryDocument(String registryDocument) {
		this.registryDocument = registryDocument;
	}

	/**
	 * Gets the registry address.
	 * 
	 * @return the registry address
	 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="raddress")
    public RegistryAddress getRegistryAddress() {
        return registryAddress;
    }

    /**
     * Sets the registry address.
     * 
     * @param registryAddress the registry address
     */
    public void setRegistryAddress(RegistryAddress registryAddress) {
        this.registryAddress = registryAddress;
    }

    /**
     * Gets the issue date.
     * 
     * @return the issue date
     */
    @Column(name = "issue_date")
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
     * Gets the number.
     * 
     * @return the number
     */
    @Column(nullable = false)
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number.
     * 
     * @param number the number
     */
    public void setNumber(int number) {
        this.number = number;
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

    /**
     * Gets the serie.
     * 
     * @return the serie
     */
    public String getSeries() {
        return series;
    }

    /**
     * Sets the serie.
     * 
     * @param series the serie
     */
    public void setSeries(String series) {
        this.series = series;
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    @Column(name = "status")
    public InvoiceStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * 
     * @param status the status
     */
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
    
    /**
     * Gets the type.
     * 
     * @return the type
     */
    @Column(name = "type")
    public InvoiceType getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type the type
	 */
	public void setType(InvoiceType type) {
		this.type = type;
	}
	
	/**
	 * Checks if a surcharge has to be applied.
	 * 
	 * @return true, if a surcharge has to be applied.
	 */
	public boolean isSurcharge() {
		return surcharge;
	}

	/**
	 * Sets if a surcharge has to be applied.
	 * 
	 * @param surcharge if a surcharge has to be applied.
	 */
	public void setSurcharge(boolean surcharge) {
		this.surcharge = surcharge;
	}

	/**
	 * Checks if is tax free.
	 * 
	 * @return true, if is tax free
	 */
	public boolean isTaxFree() {
		return taxFree;
	}

	/**
	 * Sets the tax free.
	 * 
	 * @param taxFree the tax free
	 */
	public void setTaxFree(boolean taxFree) {
		this.taxFree = taxFree;
	}
	
	/**
	 * Gets the lines.
	 * 
	 * @return the lines
	 */
	@OneToMany(mappedBy = "invoice", cascade={CascadeType.REMOVE})
	public Set<InvoiceDetail> getLines() {
		return this.lines;
	}

	/**
	 * Sets the lines.
	 * 
	 * @param lines the lines
	 */
	public void setLines( Set<InvoiceDetail> lines ) {
		this.lines = lines;
	}
	
	/**
	 * Gets the date. Necessary to implement <code>ICalculableContainer</code>
	 * 
	 * @return the date
	 */
	@Transient
	public Date getDate() {
		return this.issueDate;
	}
	
	/**
	 * Gets the detail list. Used in the reports
	 * 
	 * @return the detail list
	 */
	@Transient
	public List getDetailList() {
		try {
			IManagerBean incomeDetailBean = BeanManager.getManagerBean(InvoiceDetail.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(incomeDetailBean.getFieldName(IFinanceAlias.INVOICE_DETAIL_INVOICE_ID), getId());
			return incomeDetailBean.getList(criteria);
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining incomeDetail list", e);
		}
		return null;
	}
	
	/**
	 * Gets the ordered detail list. Used in the reports
	 * 
	 * @return the ordered detail list
	 */
	@Transient
	public List getOrderedDetailList() {
		try {
			IManagerBean offerDetailBean = BeanManager.getManagerBean(InvoiceDetail.class);
			Criteria criteria = new Criteria();
			criteria.addEqualExpression(offerDetailBean.getFieldName(IFinanceAlias.INVOICE_DETAIL_INVOICE_ID), getId());
			if(getType().equals(InvoiceType.SALES)){
				criteria.addOrder(offerDetailBean.getFieldName(IFinanceAlias.INVOICE_DETAIL_ITEM_PRODUCT_TYPE));
			}
			criteria.addOrder(offerDetailBean.getFieldName(IFinanceAlias.INVOICE_DETAIL_ID));
			return offerDetailBean.getList(criteria);
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error obtaining offerDetail list", e);
		}
		return null;
	}

	/**
	 * Gets the discount expression. Necessary to implement <code>ICalculableContainer</code>
	 * 
	 * @return the discount expression
	 */
	@Transient
	public DiscountExpression getDiscountExpression() {
		return new DiscountExpression("0.0");
	}
}