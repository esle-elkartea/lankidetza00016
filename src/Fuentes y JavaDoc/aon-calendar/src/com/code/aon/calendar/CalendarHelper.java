package com.code.aon.calendar;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.code.aon.calendar.AonCalendar;
import com.code.aon.calendar.CalendarException;
import com.code.aon.calendar.CalendarManager;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.company.Calendar;
import com.code.aon.company.dao.ICompanyAlias;
import com.code.aon.ql.Criteria;

/**
 * Calendar store helper. 
 * 
 * @author Consulting & Development. 
 *
 */
public class CalendarHelper {

	/**
	 * Returns the <code>AonCalendar</code> bound to this identifier.
	 * 
	 * @param Id
	 * @return
	 * @throws ManagerBeanException
	 * @throws CalendarException
	 */
	public static AonCalendar getCalendar(Integer id) throws CalendarException, ManagerBeanException {
		AonCalendar aonCalendar = null;
		Criteria criteria = new Criteria();    	
		IManagerBean bean = BeanManager.getManagerBean( Calendar.class );
		String field = bean.getFieldName( ICompanyAlias.CALENDAR_ID );
		criteria.addEqualExpression( field, id );
		List list = bean.getList( criteria );
		if ( list.size() > 0 ) {
			Calendar calendar = (Calendar) list.get(0);
			aonCalendar = CalendarManager.getCalendar( calendar.getData() );
			aonCalendar.setPrimaryKey( calendar.getId() );
			aonCalendar.setDescription( calendar.getDescription() );
		}
		return aonCalendar;
	}

	/**
	 * Update calendar object.
	 * 
	 * @param aonCalendar
	 * @return
	 * @throws CalendarException
	 * @throws ManagerBeanException
	 */
	public static Calendar updateCalendar(AonCalendar aonCalendar) throws CalendarException, ManagerBeanException {
		IManagerBean bean = BeanManager.getManagerBean( Calendar.class );
		Calendar calendar = new Calendar();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CalendarManager.write( aonCalendar, out );
		calendar.setData( out.toByteArray() );
		calendar.setDescription( aonCalendar.getDescription() );
		if ( aonCalendar.getPrimaryKey() != null ) {
			calendar.setId( aonCalendar.getPrimaryKey() );
			bean.update(calendar);
		} else {
			bean.insert(calendar);
			aonCalendar.setPrimaryKey( calendar.getId() );
		}
		return calendar;
	}
}
