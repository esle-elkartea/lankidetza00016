package com.code.aon.geozone.dao.mysql;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.code.aon.common.ITransferObject;
import com.code.aon.common.dao.CriteriaUtilities;
import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.IDAO;
import com.code.aon.common.dao.sql.AbstractDAO;
import com.code.aon.common.dao.sql.DAOException;
import com.code.aon.geozone.Country;
import com.code.aon.geozone.IGeoZoneJNDIConstants;
import com.code.aon.geozone.dao.IGeoZoneAlias;
import com.code.aon.ql.Criteria;

/**
 * <code>ICountryDAO</code> implementation for MySql.
 * 
 * @author Consulting & Development. Iñigo Gayarre - 24-mar-2006
 * @since 1.0
 *  
 */
public class CountryMySQLDAO extends AbstractDAO implements IDAO {

    private static TOFactory COUNTRY_FACTORY = new TOFactory() {

        /* (non-Javadoc)
         * @see com.code.aon.common.dao.sql.AbstractDAO.TOFactory#newTO(java.sql.ResultSet)
         */
        public ITransferObject newTO(ResultSet rs) throws SQLException {
        	Integer pk = new Integer(rs.getInt(1));
            Country country = new Country(pk);
            country.setName(rs.getString(2)); // $codepro.audit.disable numericLiterals
            country.setCode(rs.getString(3)); // $codepro.audit.disable numericLiterals
            return country;
        }
    };

    /**
     * Sentence to select a Country.
     */
    private static final String S_COUN = "SELECT id,name,code FROM country"; //$NON-NLS-1$

    /**
     * Sentence to insert a Country.
     */
    private static final String I_COUN = "INSERT INTO country (id,name,code) VALUES (?,?)"; //$NON-NLS-1$

    /**
     * Sentence to modify a Country.
     */
    private static final String U_COUN = "UPDATE country SET name=?,code=? WHERE id=?"; //$NON-NLS-1$

    /**
     * Sentence to delete a Country.
     */
    private static final String R_COUN = "DELETE FROM country WHERE id = ?"; //$NON-NLS-1$

    /**
     * SQL that counts the row number.
     */
    public static final String C_COUN = "SELECT count(*) FROM country"; //$NON-NLS-1$
    
	private static final Map FIELD_NAMES = DAOConstants.getDAOConstant(Country.class).getSqlMap();    
    
    /* (non-Javadoc)
     * @see com.code.aon.common.dao.IDAO#get(java.io.Serializable)
     */
    public ITransferObject get(Serializable pk) throws DAOException {
        String s = CriteriaUtilities.toSQLString( getFieldName(IGeoZoneAlias.COUNTRY_ID), pk, S_COUN );
        List list = getList(s, COUNTRY_FACTORY);
        return (ITransferObject) (!list.isEmpty() ? list.get(0) : null );
	}

    /* (non-Javadoc)
     * @see com.code.aon.common.dao.IDAO#getList(com.code.aon.ql.Criteria)
     */
    public List<ITransferObject> getList(Criteria criteria) throws DAOException {
        String s = CriteriaUtilities.toSQLString(criteria, S_COUN);
        return getList(s, COUNTRY_FACTORY);
    }

    /* (non-Javadoc)
     * @see com.code.aon.common.dao.IDAO#getList(com.code.aon.ql.Criteria, int, int)
     */
    public List<ITransferObject> getList(Criteria criteria,int start, int count) throws DAOException {
        String s = CriteriaUtilities.toSQLString(criteria, S_COUN);
        s += " LIMIT "+start+","+count;
        return getList(s, COUNTRY_FACTORY);
    }
    
	/* (non-Javadoc)
	 * @see com.code.aon.common.dao.IDAO#remove(com.code.aon.common.ITransferObject)
	 */
	public boolean remove(ITransferObject to) throws DAOException {
		Country gz = (Country) to;
		return remove(R_COUN, gz.getId());
	}

    /* (non-Javadoc)
     * @see com.code.aon.common.dao.IDAO#update(com.code.aon.common.ITransferObject)
     */
    public ITransferObject update(ITransferObject to) throws DAOException {
    	Country gz = (Country) to;
        Object[] hv = { gz.getName(), gz.getCode(), gz.getId() };
        int[] types = { Types.CHAR, Types.CHAR, Types.INTEGER };
        execute(U_COUN, hv, types);
        return to;
    }

    /* (non-Javadoc)
     * @see com.code.aon.common.dao.IDAO#insert(com.code.aon.common.ITransferObject)
     */
    public ITransferObject insert(ITransferObject to) throws DAOException {
    	Country gz = (Country) to;
        Object[] hv = { gz.getId(), gz.getName(), gz.getCode() };
        int[] types = { Types.INTEGER, Types.CHAR, Types.CHAR };
        execute(I_COUN, hv, types);
        return gz;
    }
    
	public ITransferObject insertOrUpdate(ITransferObject to) throws DAOException {
    	if ( getId(to) != null ) {
    		return update(to);
    	} else {
    		return insert(to);
    	}
	}

	/* (non-Javadoc)
	 * @see com.code.aon.common.dao.IDAO#getCount(com.code.aon.ql.Criteria)
	 */
	public int getCount(Criteria criteria) throws DAOException {
        String s = CriteriaUtilities.toSQLString(criteria, C_COUN);
		return getCount(s);
	}
	
    public Serializable getId(ITransferObject to) throws DAOException {
    	Country gz = (Country) to;
		return gz.getId();
	}
    
	public void setId(ITransferObject to, Serializable id) throws DAOException {
		Country gz = (Country) to;
		gz.setId( (Integer) id );
	}

	/* (non-Javadoc)
     * @see com.code.aon.common.dao.sql.AbstractDAO#getDataSourceName()
     */
    protected String getDataSourceName() {
        return IGeoZoneJNDIConstants.AON_GEOZONE_DATASOURCE;
    }
    
	/* (non-Javadoc)
	 * @see com.code.aon.common.AbstractFieldMapper#getFieldMap()
	 */
	protected Map getFieldMap() {
		return FIELD_NAMES;
	}

	/* (non-Javadoc)
	 * @see com.code.aon.common.dao.IDAO#getPOJOClass()
	 */
	public Class getPOJOClass() {
		return Country.class;
	}

}