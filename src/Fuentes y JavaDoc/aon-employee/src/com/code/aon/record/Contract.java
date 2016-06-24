package com.code.aon.record;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.code.aon.common.ITransferObject;
import com.code.aon.employee.Employee;
import com.code.aon.record.enumeration.ContractType;

@Entity
@Table(name="contract")
public class Contract implements ITransferObject {
	
	/**
	 * Identificador �nico
	 */
	private Integer id;
	
	/**
	 * Empleado
	 */
	private Employee employee;
	
	/**
	 * Fecha inicio
	 */
	private Date startingDate;
	
	/**
	 * Fecha fin
	 */
	private Date endingDate;
	
	/**
	 * Tipo de contrato
	 */
	private ContractType contractType;
	
	/**
	 * Salario bruto
	 */
	private double grossSalary;

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

	@Column(name="contract_type")
	public ContractType getContractType() {
		return contractType;
	}

	public void setContractType(ContractType contractType) {
		this.contractType = contractType;
	}

	@Column(name="gross_salary")
	public double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}
}
