package com.code.aon.employee;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.FilterJoinTable;

import com.code.aon.common.ITransferObject;
import com.code.aon.registry.RegistryAddress;

/**
 * Transfer Object that represents the workPlace.
 * 
 */
@Entity
@Table(name="workplace")
public class WorkPlace implements ITransferObject, INode {

	/** Working place identifier */
	private Integer id;

	/** Working place description */
	private String description;

	/** Working place address */
    private RegistryAddress address;

    /** Indicates the working place calendar identifier. */
    private Integer calendar;

//	/** The employee. */
//	private Set<Employee> employees = new HashSet<Employee>();

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
	 * Gets the description.
	 * 
	 * @return the description
	 */
	@Column(name="description")
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
	 * Gets the address.
	 * 
	 * @return the address
	 */
	@OneToOne
    @JoinColumn(name="address", nullable = false)
	public RegistryAddress getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 * 
	 * @param address the address
	 */
	public void setAddress(RegistryAddress address) {
		this.address = address;
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

//	/**
//	 * Gets the employees.
//	 * 
//	 * @return the employees
//	 */
//	@OneToMany(fetch=FetchType.EAGER)
//	@JoinTable(
//				name="wpresource", 
//				joinColumns = { @JoinColumn( name="workplace") }, 
//				inverseJoinColumns = @JoinColumn( name="employee")
//			)
//	@FilterJoinTable(name="endingdate", condition=":endingdate is null")			
//	public Set<Employee> getEmployees() {
//		return this.employees;
//	}
//	
//	/**
//	 * Sets the employees.
//	 * 
//	 * @param employees the employees
//	 */
//	public void setEmployees( Set<Employee> employees) {
//		this.employees = employees;
//	}

	/* (non-Javadoc)
	 * @see com.code.aon.employee.INode#accept(com.code.aon.employee.INodeVisitor)
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitWorkPlace( this );
	}

}
