package com.code.aon.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.code.aon.common.ITransferObject;

@Entity
@Table(name="employee_workgroup")
public class EmployeeWorkGroup implements ITransferObject {

	private Integer id;
	
	private Employee employee;
	
	private WorkGroup group;
	
	@Id
	@GeneratedValue
	@Column(nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="employee", nullable=false)
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@ManyToOne
	@JoinColumn(name="workgroup", nullable=false)
	public WorkGroup getGroup() {
		return group;
	}

	public void setGroup(WorkGroup group) {
		this.group = group;
	}
}