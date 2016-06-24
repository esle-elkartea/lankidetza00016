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
import com.code.aon.geozone.GeoZone;
import com.code.aon.geozone.IGeoZoneJNDIConstants;
import com.code.aon.geozone.dao.IGeoZoneAlias;
import com.code.aon.ql.Criteria;

/**
 * <code>IGeoZoneDAO</code> implementation for MySql.
 * 
 * @author Consulting & Development. Eugenio Castellano - 03-feb-2005
 * @since 1.0
 *  
 */
public class GeoZoneMySQLDAO extends AbstractDAO implements IDAO {

    private static TOFactory GEOZONE_FACTORY = new TOFactory() {

        /* (non-Javadoc)
         * @see com.code.aon.common.dao.sql.AbstractDAO.TOFactory#newTO(java.sql.ResultSet)
         */
        public ITransferObject newTO(ResultSet rs) throws SQLException {
        	Integer pk = new Integer(rs.getInt(1));
            GeoZone geozone = new GeoZone(pk);
            geozone.setName(rs.getString(2)); // $codepro.audit.disable numericLiterals
            return geozone;
        }
    };

    /**
     * Sentence to select a GeoZone.
     */
    private static final String S_GEOZ = "SELECT id,name FROM geozone"; //$NON-NLS-1$

    /**
     * Sentence to insert a GeoZone.
     */
    private static final String I_GEOZ = "INSERT INTO geozone (id,name) VALUES (?,?)"; //$NON-NLS-1$

    /**
     * Sentence to update a GeoZone.
     */
    private static final String U_GEOZ = "UPDATE geozone SET name=? WHERE id=?"; //$NON-NLS-1$

    /**
     * Sentence to delete a GeoZone.
     */
    private static final String R_GEOZ = "DELETE FROM geozone WHERE id = ?"; //$NON-NLS-1$

    /**
     * SQL that counts the row number.
     */
    public static final String C_GEOZ = "SELECT count(*) FROM geozone"; //$NON-NLS-1$
    
	private static final Map FIELD_NAMES = DAOConstants.getDAOConstant(GeoZone.class).getSqlMap();    
    
    /* (non-Javadoc)
     * @see com.code.aon.common.dao.IDAO#get(java.io.Serializable)
     */
    public ITransferObject get(Serializable pk) throws DAOException {
        String s = CriteriaUtilities.toSQLString( getFieldName(IGeoZoneAlias.GEO_ZONE_ID), pk, S_GEOZ );
        List list = getList(s, GEOZONE_FACTORY);
        return (ITransferObject) (!list.isEmpty() ? list.get(0) : null );
	}

    /* (non-Javadoc)
     * @see com.code.aon.common.dao.IDAO#getList(com.code.aon.ql.Criteria)
     */
    public List<ITransferObject> getList(Criteria criteria) throws DAOException {
        String s = CriteriaUtilities.toSQLString(criteria, S_GEOZ);
        return getList(s, GEOZONE_FACTORY);
    }

    /* (non-Javadoc)
     * @see com.code.aon.common.dao.IDAO#getList(com.code.aon.ql.Criteria, int, int)
     */
    public List<ITransferObject> getList(Criteria criteria,int start, int count) throws DAOException {
        String s = CriteriaUtilities.toSQLString(criteria, S_GEOZ);
        s += " LIMIT "+start+","+count;
        return getList(s, GEOZONE_FACTORY);
    }
    
	/* (non-Javadoc)
	 * @see com.code.aon.common.dao.IDAO#remove(com.code.aon.common.ITransferObject)
	 */
	public boolean remove(ITransferObject to) throws DAOException {
		GeoZone gz = (GeoZone) to;
		return remove(R_GEOZ, gz.getId());
	}

    /* (non-Javadoc)
     * @see com.code.aon.common.dao.IDAO#update(com.code.aon.common.ITransferObject)
     */
    public ITransferObject update(ITransferObject to) throws DAOException {
    	GeoZone gz = (GeoZone) to;
        Object[] hv = { gz.getName(), gz.getId() };
        int[] types = { Types.CHAR, Types.INTEGER };
        execute(U_GEOZ, hv, types);
        return to;
    }

    /* (non-Javadoc)
     * @see com.code.aon.common.dao.IDAO#insert(com.code.aon.common.ITransferObject)
     */
    public ITransferObject insert(ITransferObject to) throws DAOException {
    	GeoZone gz = (GeoZone) to;
        Object[] hv = { gz.getId(), gz.getName() };
        int[] types = { Types.INTEGER, Types.CHAR };
        execute(I_GEOZ, hv, types);
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
        String s = CriteriaUtilities.toSQLString(criteria, C_GEOZ);
		return getCount(s);
	}
	
    public Serializable getId(ITransferObject to) throws DAOException {
    	GeoZone gz = (GeoZone) to;
		return gz.getId();
	}

	public void setId(ITransferObject to, Serializable id) throws DAOException {
    	GeoZone gz = (GeoZone) to;
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
		return GeoZone.class;
	}

}