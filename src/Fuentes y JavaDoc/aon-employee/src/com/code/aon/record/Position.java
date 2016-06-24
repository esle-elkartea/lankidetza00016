package com.code.aon.record;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.code.aon.common.ITransferObject;
import com.code.aon.department.Department;
import com.code.aon.employee.Employee;
import com.code.aon.employee.WorkPlace;

@Entity
@Table(name="position")
public class Position implements ITransferObject {

	/**
	 * Identificador único
	 */
	private Integer id;
	
	/**
	 * Empleado
	 */
	private Employee employee;
	
	/**
	 * Fecha de inicio
	 */
	private Date startingDate;

	/**
	 * Fecha de fin
	 */
	private Date endingDate;
	
	/**
	 * Departamento o Área donde desarrolla su actividad profesional
	 */
	private Department department;

	/**
	 * Descripción
	 */
	private String description;

	/**
	 * Lugar de Trabajo
	 */
	private WorkPlace workPlace;

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
    @JoinColumn(name="employee", nullable = false)
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	@OneToOne
    @JoinColumn(name="department", nullable = false)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(length=64)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne
    @JoinColumn(name="workplace")
	public WorkPlace getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(WorkPlace workPlace) {
		this.workPlace = workPlace;
	}
}
