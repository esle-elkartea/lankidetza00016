package com.code.aon.person;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.code.aon.geozone.GeoZone;
import com.code.aon.person.enumeration.Gender;
import com.code.aon.person.enumeration.MaritalStatus;
import com.code.aon.registry.Registry;

/**
 * Transfer Object that represents a person.
 * 
 * @author Consulting & Development. Aimar Tellitu - 6-jun-2005
 * @since 1.0
 */
@Entity
@Table(name="person")
@PrimaryKeyJoinColumn(name="registry")
public class Person extends Registry {

	/** The birth date. */
	private Date birthDate;

	/** The age. */
	private int age;
	
	/** The gender. */
	private Gender gender;

	/** The marital status. */
	private MaritalStatus maritalStatus;

    /** Determines if the person is active or not. */
    private boolean active;
    
    /** The comments. */
    private String comments;

	/** The birthplace. */
	private GeoZone birthplace;
	
	/**
	 * The empty constructor.
	 */
	public Person() {
		this.age = -1;
	}
	
	/**
	 * Checks if is active.
	 * 
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets if is active.
	 * 
	 * @param active if is active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the birth date.
	 * 
	 * @return the birth date
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Sets the birth date.
	 * 
	 * @param birthDate the birth date
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
		calculateAge(birthDate);
	}

	/**
	 * Gets the comments.
	 * 
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Sets the comments.
	 * 
	 * @param comments the comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Gets the gender.
	 * 
	 * @return the gender
	 */
	@Column(nullable=false)
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 * 
	 * @param gender the gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Gets the marital status.
	 * 
	 * @return the marital status
	 */
	@Column(nullable=false)
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * Sets the marital status.
	 * 
	 * @param maritalStatus the marital status
	 */
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * Gets the birthplace.
	 * 
	 * @return the birthplace
	 */
	@ManyToOne
	@JoinColumn( name="birthplace", nullable = true )
	public GeoZone getBirthplace() {
		return birthplace;
	}

	/**
	 * Sets the birthplace.
	 * 
	 * @param birthplace the birthplace
	 */
	public void setBirthplace(GeoZone birthplace) {
		this.birthplace = birthplace;
	}
	
	/**
	 * Calculates the age of a person in the date passed as parameter.
	 * 
	 * @param date the date
	 */
	private void calculateAge(Date date) {
		if ( date != null ) {
			GregorianCalendar today = new GregorianCalendar();
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);
			this.age = today.get(Calendar.YEAR)
					- gc.get(Calendar.YEAR)
					- ((today.get(Calendar.DAY_OF_YEAR) > gc
							.get(Calendar.DAY_OF_YEAR)) ? 1 : 0);
			if (this.age < 0) {
				this.age = 0;
			}
		}
	}

	/**
	 * Gets the age.
	 * 
	 * @return the age
	 */
	@Transient
	public int getAge() {
		return this.age;
	}
}