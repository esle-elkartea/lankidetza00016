package com.code.aon.person.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.person.Person;

/**
 * This Class manages the DAOConstantsEntry group of this package,
 * witch manages information of an IDAO constants stored in a file,
 * 
 * @author Consulting & Development.
 * @version 1.0
 */
public interface IPersonAlias {

	/**
     * DAOConstantsEntry for Person.
     */
	DAOConstantsEntry PERSON_ENTRY = DAOConstants.getDAOConstant(Person.class);

	/**
     * Alias name.
     */
	// Alias value: Person_alias
	// Hibernate value: Person.alias
	String  PERSON_ALIAS = PERSON_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: Person_document
	// Hibernate value: Person.document
	String  PERSON_DOCUMENT = PERSON_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: Person_id
	// Hibernate value: Person.id
	String  PERSON_ID = PERSON_ENTRY.getAliasNames()[2];

	/**
     * Alias name.
     */
	// Alias value: Person_name
	// Hibernate value: Person.name
	String  PERSON_NAME = PERSON_ENTRY.getAliasNames()[3];

	/**
     * Alias name.
     */
	// Alias value: Person_surname
	// Hibernate value: Person.surname
	String  PERSON_SURNAME = PERSON_ENTRY.getAliasNames()[4];

	/**
     * Alias name.
     */
	// Alias value: Person_active
	// Hibernate value: Person.active
	String  PERSON_ACTIVE = PERSON_ENTRY.getAliasNames()[5];

	/**
     * Alias name.
     */
	// Alias value: Person_birthDate
	// Hibernate value: Person.birthDate
	String  PERSON_BIRTH_DATE = PERSON_ENTRY.getAliasNames()[6];

	/**
     * Alias name.
     */
	// Alias value: Person_birthplace_id
	// Hibernate value: Person.birthplace.id
	String  PERSON_BIRTHPLACE_ID = PERSON_ENTRY.getAliasNames()[7];

	/**
     * Alias name.
     */
	// Alias value: Person_comments
	// Hibernate value: Person.comments
	String  PERSON_COMMENTS = PERSON_ENTRY.getAliasNames()[8];

	/**
     * Alias name.
     */
	// Alias value: Person_gender
	// Hibernate value: Person.gender
	String  PERSON_GENDER = PERSON_ENTRY.getAliasNames()[9];

	/**
     * Alias name.
     */
	// Alias value: Person_maritalStatus
	// Hibernate value: Person.maritalStatus
	String  PERSON_MARITAL_STATUS = PERSON_ENTRY.getAliasNames()[10];

	
}
