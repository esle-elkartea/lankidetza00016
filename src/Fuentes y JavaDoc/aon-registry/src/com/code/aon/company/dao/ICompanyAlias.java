package com.code.aon.company.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.company.Calendar;
import com.code.aon.company.Company;

/**
 * This Class manages the DAOConstantsEntry group of this package,
 * witch manages information of an IDAO constants stored in a file.
 * 
 * @author Consulting & Development.
 * @version 1.0
 */
public interface ICompanyAlias {

	/**
     * DAOConstantsEntry for Company.
     */
	DAOConstantsEntry COMPANY_ENTRY = DAOConstants.getDAOConstant(Company.class);

	/**
     * Alias name.
     */
	// Alias value: Company_alias
	// Hibernate value: Company.alias
	String  COMPANY_ALIAS = COMPANY_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: Company_document
	// Hibernate value: Company.document
	String  COMPANY_DOCUMENT = COMPANY_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: Company_id
	// Hibernate value: Company.id
	String  COMPANY_ID = COMPANY_ENTRY.getAliasNames()[2];

	/**
     * Alias name.
     */
	// Alias value: Company_name
	// Hibernate value: Company.name
	String  COMPANY_NAME = COMPANY_ENTRY.getAliasNames()[3];

	/**
     * Alias name.
     */
	// Alias value: Company_surname
	// Hibernate value: Company.surname
	String  COMPANY_SURNAME = COMPANY_ENTRY.getAliasNames()[4];

	/**
     * Alias name.
     */
	// Alias value: Company_active
	// Hibernate value: Company.active
	String  COMPANY_ACTIVE = COMPANY_ENTRY.getAliasNames()[5];

	/**
     * Alias name.
     */
	// Alias value: Company_calendar
	// Hibernate value: Company.calendar
	String  COMPANY_CALENDAR = COMPANY_ENTRY.getAliasNames()[6];

	/**
     * Alias name.
     */
	// Alias value: Company_taxfree
	// Hibernate value: Company.taxfree
	String  COMPANY_TAXFREE = COMPANY_ENTRY.getAliasNames()[7];

	/**
     * DAOConstantsEntry for Calendar.
     */
	DAOConstantsEntry CALENDAR_ENTRY = DAOConstants.getDAOConstant(Calendar.class);

	/**
     * Alias name.
     */
	// Alias value: Calendar_data
	// Hibernate value: Calendar.data
	String  CALENDAR_DATA = CALENDAR_ENTRY.getAliasNames()[0];

	/**
     * Alias name.
     */
	// Alias value: Calendar_description
	// Hibernate value: Calendar.description
	String  CALENDAR_DESCRIPTION = CALENDAR_ENTRY.getAliasNames()[1];

	/**
     * Alias name.
     */
	// Alias value: Calendar_id
	// Hibernate value: Calendar.id
	String  CALENDAR_ID = CALENDAR_ENTRY.getAliasNames()[2];

}
