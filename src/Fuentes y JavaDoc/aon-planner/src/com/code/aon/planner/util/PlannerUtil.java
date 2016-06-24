/*
 * Created on 14-nov-2005
 *
 */
package com.code.aon.planner.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.myfaces.custom.schedule.model.ScheduleEntry;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VFreeBusy;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.ExDate;
import net.fortuna.ical4j.model.property.FreeBusy;
import net.fortuna.ical4j.model.property.RRule;

import com.code.aon.calendar.AonCalendar;
import com.code.aon.calendar.CalendarUtil;
import com.code.aon.calendar.enumeration.EventCategory;

import com.code.aon.planner.IEvent;
import com.code.aon.planner.core.Event;
import com.code.aon.planner.enumeration.EventStatus;

/**
 * This class gives planner utilities.
 * 
 * @author Consulting & Development. I�aki Ayerbe - 17/01/2007
 *
 */
public class PlannerUtil {

	public static final String UNDERLINE_SEPARATOR = "_"; //$NON-NLS-1$;
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$;

	public static final String TIME_PATTERN = "HH:mm"; // (0-23:1-60)
	public static final String DATE_PATTERN = "dd/MM/yyyy"; //$NON-NLS-1$;

    /**
     * Check if if components belong to this categories: VACATION, PUBLIC_HOLIDAY, DAY_OFF
     *    
     * @param components
     * @return
     */
    public static final Component isSpecialDay(ComponentList components) {
        if (components.size() > 0) {
        	Iterator i = components.iterator();
        	while ( i.hasNext() ) {
        		Component component = (Component) i.next();
        		String property = component.getProperties().getProperty(Property.CATEGORIES).getValue();
        		if ( EventCategory.VACATION.name().equals(property)
        				|| EventCategory.PUBLIC_HOLIDAY.name().equals(property)
        				|| EventCategory.DAY_OFF.name().equals(property) ) {
        			return component;
        		}
        	}
        }
        return null;
    }

    /**
     * Create a <code>ScheduleEntry</code> from a <code>VEvent</code> between 2 dates.
     * 
     * @param vevent
     * @param startDate
     * @param endDate
     * @return
     */
    public static final ScheduleEntry getScheduleEntry(VEvent vevent, Date startDate, Date endDate) {
        Event event = new Event();
        if (vevent.getProperties().getProperty(Property.PRODID) != null)
            event.setId(EMPTY_STRING + vevent.getProperties().getProperty(Property.PRODID).getValue());
        if (vevent.getProperties().getProperty(Property.SUMMARY) != null)
            event.setTitle(vevent.getProperties().getProperty(Property.SUMMARY).getValue());
        if (vevent.getProperties().getProperty(Property.LOCATION) != null)
            event.setSubtitle(EMPTY_STRING + vevent.getProperties().getProperty(Property.LOCATION).getValue());
        if (vevent.getProperties().getProperty(Property.DESCRIPTION) != null)
            event.setDescription(EMPTY_STRING + vevent.getProperties().getProperty(Property.DESCRIPTION).getValue());
        if (vevent.getProperties().getProperty(Property.STATUS) != null) {
            String status = vevent.getProperties().getProperty(Property.STATUS).getValue();
            event.setState( EventStatus.get(status) );
        }
        if (vevent.getProperties().getProperty(Property.CATEGORIES) != null) {
            String property = vevent.getProperties().getProperty(Property.CATEGORIES).getValue();
            event.setCategory( EventCategory.get(property) );
        }
        event.setRealStartTime(startDate);
    	event.setRealEndTime(endDate);
    	event.setStartTime(startDate);
    	event.setEndTime(endDate);
    	Duration dur = (Duration) vevent.getProperties().getProperty(Property.DURATION);
    	if ( dur != null && dur.getDuration().getDays() > 0 )
    		event.setAllDay(true);
//	TODO Solamente se visualiza 1 RRULE. M�s adelante hay que visualizar todas las existentes.
        RRule rrule = (RRule)vevent.getProperties().getProperty(Property.RRULE);
        if (rrule != null)
        	event.addRecurrence( rrule.getRecur() );
        event.setComponent(vevent);
        return event;
    }

    /**
     * Calculate yearly working hours.
     * 
     * @param list
     * @param endDate
     */
    public static final double calcYearlyHours(ComponentList list, Date endDate) {
    	float hours = 0, minutes = 0;
    	Calendar c = Calendar.getInstance();
    	c.setTime( new Date() );
    	c.set( c.get(Calendar.YEAR), 0, 01 );
		DateTime start = CalendarUtil.getICalDateTime( c.getTime(), true);
    	c.set( c.get(Calendar.YEAR), 11, 31 );
		DateTime end = CalendarUtil.getICalDateTime( c.getTime(), true);
		if ( endDate != null ) {
			end = CalendarUtil.getICalDateTime( endDate, true);
		}
		VFreeBusy request = new VFreeBusy( start, end );
		VFreeBusy busyTime = new VFreeBusy( request, list );
        FreeBusy fg = (FreeBusy) busyTime.getProperties().getProperty( Property.FREEBUSY );
        if ( fg != null ) {
	        Iterator iter = fg.getPeriods().iterator();
	        while (iter.hasNext()) {
	            Period p = (Period) iter.next();
	            hours += p.getDuration().getHours();
	            minutes += p.getDuration().getMinutes();
	        }
        }
        return hours + (minutes/60);
    }

    /**
     * Validate event, the method checks if the event is allowed inside the aonCalendar.
     * 
     * @param event
     * @return
     */
    public static final boolean validate(AonCalendar aonCalendar, IEvent event) {
		ComponentList newlist = new ComponentList();
		VEvent vevent = (VEvent) event.getComponent();
		Property id = vevent.getProperties().getProperty(Property.PRODID);
		newlist.add(vevent);
//	Load WORK category events.
    	ComponentList list = new ComponentList();
    	Iterator i = aonCalendar.getCalendar().getComponents().iterator();
    	while ( i.hasNext() ) {
    		Component component = (Component) i.next();
    		Property prodId = component.getProperties().getProperty(Property.PRODID);
    		if ( Component.VEVENT.equals(component.getName()) ) {
    			EventCategory ec = CalendarUtil.getCategory((VEvent) component);
				if ( EventCategory.WORK == ec && !prodId.getValue().equals(id.getValue()) ) {
					list.add(component);
    			}
    		}
    	}
//	Validate event against WORK category list events.
		DateTime start = (DateTime) vevent.getStartDate().getDate();
		DateTime end = (DateTime) vevent.getEndDate().getDate();
        RRule rrule = (RRule)vevent.getProperties().getProperty(Property.RRULE);
        if (rrule != null) { 
			end = CalendarUtil.getICalDate( rrule.getRecur().getUntil(), end );
        }
		VFreeBusy request = new VFreeBusy( start, end );
		VFreeBusy busyTime = new VFreeBusy( request, newlist );
        FreeBusy fg = (FreeBusy) busyTime.getProperties().getProperty( Property.FREEBUSY );
        Iterator iter = fg.getPeriods().iterator();
        while (iter.hasNext()) {
            Period element = (Period) iter.next();
            VFreeBusy lrequest = new VFreeBusy( element.getStart(), element.getEnd() );
    		VFreeBusy lbusyTime = new VFreeBusy( lrequest, list );
            FreeBusy lfreefg = (FreeBusy) lbusyTime.getProperties().getProperty( Property.FREEBUSY );
            if ( lfreefg != null )
            	return false;
        }
        return true;
    }

    /**
	 * Propaga el evento al calendario.
	 * 
	 * @param aonCalendar
	 * @param event
	 */
	public static final void spread(AonCalendar aonCalendar, IEvent event, int operation) {
		if ( operation == IEvent.CREATE) {
			aonCalendar.getCalendar().getComponents().add(event.getComponent());
	        if ( PlannerUtil.getHolidayCategories()[ event.getCategory().ordinal() ] != null ) {
	        	PlannerUtil.addExDate( aonCalendar, event );
	        }
		}
		if ( operation == IEvent.UPDATE) {
			VEvent oldVEvent = (VEvent) aonCalendar.updateEvent( (VEvent) event.getComponent() );
			if ( oldVEvent != null ) {
		        if ( PlannerUtil.getHolidayCategories()[ event.getCategory().ordinal() ] != null ) {
		        	PlannerUtil.updateExDate(aonCalendar, oldVEvent, event);
		        }
			} else {
				aonCalendar.getCalendar().getComponents().add(event.getComponent());
		        if ( PlannerUtil.getHolidayCategories()[ event.getCategory().ordinal() ] != null ) {
		        	PlannerUtil.addExDate( aonCalendar, event );
		        }
			}
		}
		if ( operation == IEvent.DELETE) {
			aonCalendar.getCalendar().getComponents().remove(event.getComponent());
	        if ( PlannerUtil.getHolidayCategories()[ event.getCategory().ordinal() ] != null ) {
	        	PlannerUtil.removeExDate(aonCalendar, event);
	        }
		}
	}

    /**
     * Find date exceptions from Holiday VEvents. This method does not take care about VEvents
     * repetitions.
	 * 
	 * @param calendar
	 * @return
	 */
	public static final DateList findExDates(AonCalendar calendar) {
		DateList dl = new DateList();
		EventCategory[] ec = getHolidayCategories();
		ComponentList holidays = calendar.getVEvents(ec);
        for (Iterator i = holidays.iterator(); i.hasNext();) {
        	VEvent vevent = (VEvent) i.next();
		    if (vevent.getProperties().getProperty(Property.RRULE) != null) {
		    	// Do Nothing
		    } else {
			    Date start = CalendarUtil.getDate( vevent.getStartDate().getDate() );
			    Date end = start;
			    if ( vevent.getProperties().getProperty(Property.DTEND) != null )
			    	end = CalendarUtil.getDate( vevent.getEndDate().getDate() );
			    if ( start.compareTo(end) == 0 ) {
		        	dl.add( vevent.getStartDate().getDate() );
			    } else {
			    	Calendar c = Calendar.getInstance();
			    	c.setTime(start);
			    	while ( c.getTime().before(end) ) {
			        	dl.add( CalendarUtil.getICalDate( c.getTime() ) );
						c.add(Calendar.DATE, 1);
					}
			    }
		    }
		}
        return dl;
	}

    /**
     * Add ExDate objects to the event. 
     * 
     * @param aonCalendar 
     * @param event
     */
	public static final void addExDates(AonCalendar aonCalendar, IEvent event) {
//Look for all kind of Holidays and add to VEvent object the date exceptions.
		DateList dl = PlannerUtil.findExDates( aonCalendar );
		VEvent vevent = (VEvent) event.getComponent();
		ExDate exdate = (ExDate) vevent.getProperties().getProperty(Property.EXDATE);
		if ( exdate != null ) {
	        vevent.getProperties().remove(exdate);
		}
        ParameterList parameterList = new ParameterList();
        parameterList.add(Value.DATE);
        exdate = new ExDate(parameterList, dl);
        vevent.getProperties().add(exdate);
    	
    }

    /**
     * Add an exception date to the WORK category events if exists.
     *  
     * @param aonCalendar 
     * @param event
     */
    public static final void addExDate(AonCalendar aonCalendar, IEvent event) {
	    if ( event.getRecurrences().size() == 0 ) {
			ComponentList components = aonCalendar.getVEvents(EventCategory.WORK); 
			Iterator iter = components.iterator();
			while (iter.hasNext()) {
				VEvent vevent = (VEvent) iter.next();
				ExDate exdate = (ExDate) vevent.getProperties().getProperty(Property.EXDATE);
				addExDates( exdate, event.getStartTime(), event.getEndTime() );
			}
	    }
    }

    /**
     * Remove an exception date from the WORK category events if exists. 
     * 
     * @param aonCalendar 
     * @param event
     */
    public static final void removeExDate(AonCalendar aonCalendar, IEvent event) {
	    if ( event.getRecurrences().size() == 0 ) {
		    ComponentList components = aonCalendar.getVEvents(EventCategory.WORK); 
			Iterator iter = components.iterator();
			while (iter.hasNext()) {
				VEvent vevent = (VEvent) iter.next();
				ExDate exdate = (ExDate) vevent.getProperties().getProperty(Property.EXDATE);
				if ( exdate != null ) {
					removeExDates( exdate, event.getStartTime(), event.getEndTime() );
				}
			}
	    }
    }

    /**
     * Update an exception date on the WORK category events if exists. 
     * 
     * @param aonCalendar 
     * @param oldVEvent
     * @param event
     */
    public static final void updateExDate(AonCalendar aonCalendar, VEvent oldVEvent, IEvent event) {
	    Date start = CalendarUtil.getDate( oldVEvent.getStartDate().getDate() );
	    Date end = start;
	    if ( oldVEvent.getProperties().getProperty(Property.DTEND) != null )
	    	end = CalendarUtil.getDate( oldVEvent.getEndDate().getDate() );
		ComponentList components = aonCalendar.getVEvents(EventCategory.WORK); 
		Iterator iter = components.iterator();
		while (iter.hasNext()) {
			VEvent vevent = (VEvent) iter.next();
			ExDate exdate = (ExDate) vevent.getProperties().getProperty(Property.EXDATE);
			if ( exdate != null ) {
				removeExDates( exdate, start, end );
			}
		    if ( event.getRecurrences().size() == 0 ) {
		    	addExDates( exdate, event.getStartTime(), event.getEndTime() );
		    }
		}
    }

    /**
     * Remove from <code>ExDate</code> object all dates between start and end dates.
     * 
     * @param exdate
     * @param start
     * @param end
     */
    public static final void removeExDates(ExDate exdate, Date start, Date end) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(start);
    	while ( c.getTime().before(end) ) {
    		exdate.getDates().remove( CalendarUtil.getICalDate( c.getTime() ) );
			c.add(Calendar.DATE, 1);
		}
    }

    /**
     * Add to <code>ExDate</code> object all dates between start and end dates.
     * 
     * @param exdate
     * @param start
     * @param end
     */
    public static final void addExDates(ExDate exdate, Date start, Date end) {
    	DateList dl;
		if ( exdate != null ) {
			dl = exdate.getDates();
		} else {
			dl = new DateList();
	        ParameterList parameterList = new ParameterList();
	        parameterList.add(Value.DATE);
	        exdate = new ExDate(parameterList, dl);
		}
    	if ( start.compareTo(end) == 0 ) {
        	dl.add( CalendarUtil.getICalDate(start) );
	    } else {
	    	Calendar c = Calendar.getInstance();
	    	c.setTime(start);
	    	while ( c.getTime().before(end) ) {
	        	dl.add( CalendarUtil.getICalDate( c.getTime() ) );
				c.add(Calendar.DATE, 1);
			}
	    }
    }

	/**
	 * @return the event categories array.
	 */
	public static final EventCategory[] getEventCategories() {
		EventCategory[] ec = new EventCategory[ EventCategory.values().length ];
        ec[EventCategory.APPOINTMENT.ordinal()] = EventCategory.APPOINTMENT; 
        ec[EventCategory.INCIDENCE.ordinal()] = EventCategory.INCIDENCE; 
        return ec;
	}

	/**
	 * @return the holiday categories array.
	 */
	public static final EventCategory[] getHolidayCategories() {
		EventCategory[] ec = new EventCategory[ EventCategory.values().length ];
        ec[EventCategory.VACATION.ordinal()] = EventCategory.VACATION; 
        ec[EventCategory.DAY_OFF.ordinal()] = EventCategory.DAY_OFF; 
        ec[EventCategory.PUBLIC_HOLIDAY.ordinal()] = EventCategory.PUBLIC_HOLIDAY;
        return ec;
	}

	/**
	 * Returns a list of a <code>SelectItem</code> categories.
	 * 
	 * @return
	 */
	public static final List<SelectItem> getCategories(EventCategory[] ec) {
        List<SelectItem> categories = new LinkedList<SelectItem>();
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		for (int i = 0; i < ec.length; i++) {
			if ( ec[i] != null )
				categories.add( new SelectItem( ec[i], ec[i].getName(locale) ) );
		}
		return categories;
	}

	/**
     * Create an <code>Event</code> object.
     * 
     * @param identifier
     * @param startDate
     * @reutrn
     */
    public static final IEvent createEvent(int identifier, Date startDate) {
    	Event event = new Event();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(startDate);
        String id = EMPTY_STRING + identifier + "_" + 
        	 		calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE) + 
        	 		calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE);
        setCurrentTimeEvent(event, startDate);
    	event.setId(id);
    	event.setCategory(EventCategory.APPOINTMENT);
    	event.setDirty(true);
        return event;
    }

    /**
     * Create an <code>Event</code> object of category: VACATION, PUBLIC_HOLIDAY, DAY_OFF.
     * 
     * @param identifier
     * @param startDate
     * @reutrn
     */
    public static final IEvent createHolidayEvent(int identifier, Date startDate) {
    	Event event = (Event) createEvent( identifier, startDate );
    	event.setCategory(EventCategory.VACATION);
    	event.setAllDay(true);
    	setAllDayTimeEvent(event, startDate);
    	return event;
    }

    /**
     * Create an <code>Event</code> object of category: WORK.
     *   
     * @param identifier
     * @param startDate
     * @reutrn
     */
    public static final IEvent createWorkingTimeEvent(int identifier, Date startDate) {
    	Event event = (Event) createEvent( identifier, startDate );
    	event.setCategory(EventCategory.WORK);
    	event.setState(EventStatus.CONFIRMED);
    	setWorkTimeEvent(event, startDate);
        return event;
    }

    /**
     * Set current time to the event.
     * 
     * @param event
     * @param startDate
     */
    public static final void setCurrentTimeEvent(Event event, Date startDate) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(startDate);
        int minute = calendar.get( Calendar.MINUTE );
        minute = Math.round( minute/10 ) * 10;
        calendar.set( Calendar.MINUTE, minute );
        calendar.add( Calendar.MINUTE, 10 );
        calendar.set( Calendar.SECOND, 0 );
    	event.setStartTime( calendar.getTime() );
        calendar.add( Calendar.MINUTE, 30 );
    	event.setEndTime( calendar.getTime() );
    }

    /**
     * Set all day to the event.
     * 
     * @param event
     * @param startDate
     */
    public static final void setAllDayTimeEvent(Event event, Date startDate) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(startDate);
    	c.set( Calendar.HOUR_OF_DAY, 0 );
    	c.set( Calendar.MINUTE, 0 );
    	c.set( Calendar.SECOND, 0 );
    	event.setStartTime( c.getTime() );
    	c.set( Calendar.SECOND, 1 );
    	event.setEndTime( c.getTime() );
    }

    /**
     * Set working time to the event.
     * 
     * @param event
     * @param startDate
     */
    public static final void setWorkTimeEvent(Event event, Date startDate) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime( new Date() );
    	calendar.set( calendar.get(Calendar.YEAR), 0, 01, 8, 0 );
    	event.setStartTime(calendar.getTime());
    	calendar.set( calendar.get(Calendar.YEAR), 0, 01, 13, 30 );
    	event.setEndTime(calendar.getTime());
    }

    /**
     * Busca la 1� fecha libre, en el calendario y a partir de la fecha, pasados por par�metro.
     *  
     * @param calendar
     * @param date
     * @return
     */
	public static final Date fetchFirstFreeDate(AonCalendar calendar, Date date) {
		try {
			Dur dur = new Dur(7, 0, 0, 0);
			Period period = new Period( CalendarUtil.getICalDateTime(date, true) , dur );
			PeriodList list = calendar.getWorkingTime( period );
			Period period1 = (Period) list.iterator().next();
			PeriodList freeList = calendar.getFreeTime( period1, new Dur(0, 1, 0, 0) );
			Period free1 = (Period) freeList.iterator().next();		
			return CalendarUtil.getDate( free1.getStart() );
		} catch (Exception e) {}
		return date;
	}

	/**
	 * Convert <code>null</code> string into a empty string.
	 *   
	 * @param name
	 * @return
	 */
	public static final String convertNull2Blank(String name) {
		return (name == null)? EMPTY_STRING: name;
	}

}
