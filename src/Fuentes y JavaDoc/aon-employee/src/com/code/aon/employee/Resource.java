/**
 * 
 */
package com.code.aon.employee;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.code.aon.common.ITransferObject;

/**
 * @author Consulting & Development. Iñaki Ayerbe - 21/12/2006
 *
 */

@Entity
@Table(name="resource")
public class Resource implements ITransferObject, INode {

	/** Transfer Object Identifier. */
	private Integer id;
	
	/** Employee reference. */
	private Employee employee;

	/** Work Place reference. */
	private WorkPlace workPlace;

	/** Work Activity reference. */
	private WorkActivity workActivity;
	
	/** Starting date. */
	private Date startingDate;

	/** Ending date. */
	private Date endingDate;

	@Id
	@GeneratedValue
	@Column(nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name="employee")
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@OneToOne
	@JoinColumn(name="workplace")
	public WorkPlace getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(WorkPlace workPlace) {
		this.workPlace = workPlace;
	}

	@OneToOne
	@JoinColumn(name="workactivity")
	public WorkActivity getWorkActivity() {
		return workActivity;
	}

	public void setWorkActivity(WorkActivity workActivity) {
		this.workActivity = workActivity;
	}

	@Column(name="startingdate")
	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	@Column(name="endingdate")
	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	public void accept(INodeVisitor visitor) {
		visitor.visitResource( this );
	}

}
