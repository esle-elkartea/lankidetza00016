package com.code.aon.geozone.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.geozone.Country;
import com.code.aon.geozone.GeoZone;

/**
 * This Class manages the DAOConstantsEntry group of this package,
 * witch manages information of an IDAO constants stored in a file,
 * 
 * @author Consulting & Development.
 * @version 1.0
 */
public interface IGeoZoneAlias {
	
    /**
     * DAOConstantsEntry for GeoZone.
     */
	DAOConstantsEntry GEO_ZONE_ENTRY = DAOConstants.getDAOConstant(GeoZone.class);

    /**
     * Alias name.
     */
	// Alias value: GeoZone_id
	// Hibernate value: GeoZone.id
	String  GEO_ZONE_ID = GEO_ZONE_ENTRY.getAliasNames()[0];

    /**
     * Alias name.
     */
	// Alias value: GeoZone_name
	// Hibernate value: GeoZone.name
	String  GEO_ZONE_NAME = GEO_ZONE_ENTRY.getAliasNames()[1];

    /**
     * DAOConstantsEntry for Contry.
     */
	DAOConstantsEntry COUNTRY_ENTRY = DAOConstants.getDAOConstant(Country.class);

    /**
     * Alias name.
     */
	// Alias value: Country_code
	// Hibernate value: Country.code
	String  COUNTRY_CODE = COUNTRY_ENTRY.getAliasNames()[0];

    /**
     * Alias name.
     */
	// Alias value: Country_id
	// Hibernate value: Country.id
	String  COUNTRY_ID = COUNTRY_ENTRY.getAliasNames()[1];

    /**
     * Alias name.
     */
	// Alias value: Country_name
	// Hibernate value: Country.name
	String  COUNTRY_NAME = COUNTRY_ENTRY.getAliasNames()[2];

}
