/**
 * 
 */
package com.code.aon.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.code.aon.common.ITransferObject;

@Entity
@Table(name="workactivity")
public class WorkActivity implements ITransferObject, INode {
	
	/** Working activity identifier */
	private Integer id;
	
	/** Working activity description */
    private String description;

	/** Indicates the working place that this activity belongs to */
    private WorkPlace workPlace; 

    /** Indicates the working activity calendar identifier. */
    private Integer calendar;

	@Id
	@GeneratedValue
	@Column(nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne
    @JoinColumn(name="workplace", nullable = false, updatable = false)    
    public WorkPlace getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(WorkPlace workPlace) {
		this.workPlace = workPlace;
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

	public void accept(INodeVisitor visitor) {
		visitor.visitWorkActivity( this );
	}
}
