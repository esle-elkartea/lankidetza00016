package com.code.aon.employee;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.code.aon.common.ILookupObject;
import com.code.aon.common.ITransferObject;
import com.code.aon.employee.dao.IEmployeeAlias;
import com.code.aon.registry.Registry;
import com.code.aon.registry.dao.IRegistryAlias;

/**
 * Transfer Object que representa un empleado.
 * 
 * @author Consulting & Development. Joseba Urkiri - 11-nov-2005
 *  
 * @since 1.0
 * 
 */
@Entity
@Table(name="employee")
public class Employee implements ITransferObject, ILookupObject {

	/** Employee identifier that is equal to the registry one. */
	private Integer id;

	private Registry registry;

	/** Social Security number */
	private String socialSecurityNumber;

    /** Indicates the employee calendar identifier. */
    private Integer calendar;

    /** The username. */
    private String username;

	/**
     * @return Returns the id
	 */
	@Id
	@Column(name="registry")
	@GeneratedValue(generator="registry_id")
	@GenericGenerator(name="registry_id", strategy="foreign", parameters = {
			@Parameter(name="property", value="registry")})
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return Returns the registry.
	 */
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@PrimaryKeyJoinColumn 
	public Registry getRegistry() {
		return registry;
	}

	/**
	 * @param registry
	 */
	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	@Column(name="social_security_num", nullable=false)
	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	/**
	 * Return calendar identifier.
	 * 
	 * @return
	 */
	public Integer getCalendar() {
		return calendar;
	}

	/**
	 * Set calendar identifier.
	 * 
	 * @param calendar
	 */
	public void setCalendar(Integer calendar) {
		this.calendar = calendar;
	}

    /**
     * Gets the username.
     *
     * @return the username
     */
    @Column(length=8)
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

	@Transient
	public Map<String, Object> getLookups() {
		Map<String,Object> map = new HashMap<String,Object>();
        map.put(IEmployeeAlias.EMPLOYEE_ID, getId());
        map.put(IEmployeeAlias.EMPLOYEE_REGISTRY_ID, getRegistry().getId());
        map.put(IRegistryAlias.REGISTRY_NAME, getRegistry().getName());
        map.put(IRegistryAlias.REGISTRY_SURNAME, getRegistry().getSurname());
        map.put(IRegistryAlias.REGISTRY_DOCUMENT, getRegistry().getDocument());
        map.put(IRegistryAlias.REGISTRY_ALIAS, getRegistry().getAlias());
        return map;
	}
}