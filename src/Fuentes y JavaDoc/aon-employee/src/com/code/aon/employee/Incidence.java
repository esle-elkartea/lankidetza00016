package com.code.aon.employee;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.code.aon.common.ITransferObject;

@Entity
@Table(name="incidence")
public class Incidence implements ITransferObject {
	
	/** Identifier */
	private Integer id;

	/** Type */
	private IncidenceType incidenceType;

	/** Starting date */
	private Date startingDate;

	/** Starting time */
	private Date startingTime;

	/** Length (hundredth hours). */
	private double length;

	/** Ending date */
	private Date endingDate;

	/** Resource reference */
	private Resource resource;

	/**
	 * @return the identifier
	 */
	@Id
	@GeneratedValue
	@Column(nullable=false)
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the identifier to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the incidenceType
	 */
	@OneToOne
    @JoinColumn(name="type", nullable = false)
	public IncidenceType getIncidenceType() {
		return incidenceType;
	}

	/**
	 * @param incidenceType the incidenceType to set
	 */
	public void setIncidenceType(IncidenceType incidenceType) {
		this.incidenceType = incidenceType;
	}

	/**
	 * @return the startingDate
	 */
	@Column(name="startingdate")
	public Date getStartingDate() {
		return startingDate;
	}

	/**
	 * @param startingDate the startingDate to set
	 */
	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	/**
	 * @return the startingTime
	 */
	@Column(name="startingtime")
	@Type(type="java.sql.Time")
	public Date getStartingTime() {
		return startingTime;
	}

	/**
	 * @param startingTime the startingTime to set
	 */
	public void setStartingTime(Date startingTime) {
		this.startingTime = startingTime;
	}

	/**
	 * @return the length
	 */
	@Column(name="length", nullable = false)
	public double getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * @return the endingDate
	 */
	@Column(name="endingdate")
	public Date getEndingDate() {
		return endingDate;
	}

	/**
	 * @param endingDate the endingDate to set
	 */
	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	/**
	 * @return the resource
	 */
	@OneToOne
    @JoinColumn(name="resource", nullable = false)
	public Resource getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}

}
