package com.code.aon.geozone.dao.mysql;

import com.code.aon.common.dao.IDAO;
import com.code.aon.geozone.dao.GeoZoneDAOFactory;

/**
 * <code>IGeoZoneDAO</code> factory.
 * 
 * @author Consulting & Development. Eugenio Castellano - 04-mar-2005
 * @since 1.0
 *  
 */
public class GeoZoneMySQLDAOFactory extends GeoZoneDAOFactory {

	/**
	 * Returns the Data Access Object needed to get the GeoZone entities.
	 * 
	 * @return IBasicDAO Requierd DAO.
	 */
	public IDAO getGeoZoneDAO() {
		return new GeoZoneMySQLDAO();
	}

	/**
	 * Returns the Data Access Object needed to get the Country entities.
	 * 
	 * @return IBasicDAO Requierd DAO.
	 */
	public IDAO getCountryDAO() {
		return new CountryMySQLDAO();
	}

}