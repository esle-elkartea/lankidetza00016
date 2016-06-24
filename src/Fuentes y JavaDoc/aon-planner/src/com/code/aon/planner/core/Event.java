/*
 * Created on 10-nov-2005
 *
 */
package com.code.aon.planner.core;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.Recur;

import org.apache.myfaces.custom.schedule.model.ScheduleEntry;

import com.code.aon.calendar.CalendarUtil;
import com.code.aon.calendar.enumeration.EventCategory;

import com.code.aon.planner.IEvent;
import com.code.aon.planner.INodeVisitor;
import com.code.aon.planner.enumeration.EventStatus;

public class Event implements Cloneable, IEvent, ScheduleEntry {

    /** Identificador de la Cita*/
    private String id;
    /** T�tulo de la Cita*/
    private String title;
    /** Subt�tulo de la Cita*/
    private String subtitle;
    /** Descripci�n de la Cita*/
    private String description;
    /** Estado de la Cita*/
    private EventStatus state;
    /** Categor�a de la Cita*/
    private EventCategory category;
    /** Fecha real de inicio de la Cita*/
    private Date realStartTime;
    /** Fecha inicial de la Cita*/
    private Date startTime;
    /** Fecha real de fin de la Cita*/
    private Date realEndTime;
    /** Fecha final de visualizaci�n de la Cita*/
    private Date endTime;
    /** All day Event */
    private boolean isAllDay;
    /** Lista de repeticiones de la cita*/
    private Set<Recur> recurrences = new HashSet<Recur>();

    /** Indica el objeto persistente asociado al evento. */
    private Component component;
    /** Indica si el evento ha sido modificado para actualizar el componente. */
    private boolean isDirty;
    /** Spreadable Event */
    private boolean isSpreadable;

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param subtitle The subtitle to set.
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param state The state to set.
     */
    public void setState(EventStatus state) {
        this.state = state;
    }

    /**
     * @param category The category to set.
     */
    public void setCategory(EventCategory category) {
        this.category = category;
    }

	/**
	 * @param realStartTime The realStartTime to set.
	 */
	public void setRealStartTime(Date realStartTime) {
		this.realStartTime = realStartTime;
	}

	/**
     * @param startTime The startTime to set.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

	/**
	 * @param realEndTime The realEndTime to set.
	 */
	public void setRealEndTime(Date realEndTime) {
		this.realEndTime = realEndTime;
	}

    /**
     * @param endTime The endTime to set.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	/**
	 * @param isAllDay the isAllDay to set
	 */
	public void setAllDay(boolean isAllDay) {
		this.isAllDay = isAllDay;
	}

    /**
     * @param recurrences The recurrences to set.
     */
    public void setRecurrences(Set<Recur> recurrences) {
        this.recurrences = recurrences;
    }

    /**
     * A�ade una nueva repetici�n del evento.
     * 
     * @param recur
     */
    public void addRecurrence(Recur recur) {
        this.recurrences.add(recur);
    }

    /**
     * Elimina la repetici�n indicada en el par�metro del evento.
     * 
     * @param recur
     * @return
     */
    public boolean removeRecurrence(Recur recur) {
        return this.recurrences.remove(recur);
    }

	/**
	 * @return Returns the isDirty.
	 */
	public boolean isDirty() {
		return isDirty;
	}

	/**
	 * @param isDirty The isDirty to set.
	 */
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	/**
	 * @return the isSpreadable
	 */
	public boolean isSpreadable() {
		return isSpreadable;
	}

	/**
	 * @param isSpreadable the isSpreadable to set
	 */
	public void setSpreadable(boolean isSpreadable) {
		this.isSpreadable = isSpreadable;
	}

	/**
	 * @param component The component to set.
	 */
	public void setComponent(Component component) {
		this.component = component;
		this.isDirty = false;
	}

	public Date getUntil() {
    	if ( this.recurrences.size() > 0 ) {
    		Recur r = (Recur) this.recurrences.iterator().next();
    		return (r.getUntil() != null)? CalendarUtil.getDate(r.getUntil()): null;
    	}
    	return getEndTime();
    }

	public String getFrecuency() {
    	if ( this.recurrences.size() > 0 ) {
    		Recur r = (Recur) this.recurrences.iterator().next();
            StringBuffer b = new StringBuffer( r.getFrequency() );
            if (!r.getMonthList().isEmpty()) {
                b.append( ";BYMONTH=" );
                b.append( r.getMonthList() );
            }
            if (!r.getWeekNoList().isEmpty()) {
                b.append(";BYWEEKNO=");
                b.append(r.getWeekNoList());
            }
            if (!r.getYearDayList().isEmpty()) {
                b.append(";BYYEARDAY=");
                b.append( r.getYearDayList() );
            }
            if (!r.getMonthDayList().isEmpty()) {
                b.append(";BYMONTHDAY=");
                b.append( r.getMonthDayList() );
            }
            if (!r.getDayList().isEmpty()) {
                b.append(";BYDAY=");
                b.append( r.getDayList() );
            }
    		return b.toString();
    	}
    	return null;
	}

	public float getDuration() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(this.startTime);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(this.startTime);
		Calendar _end = Calendar.getInstance();
		_end.setTime(this.endTime);
		endCalendar.set( Calendar.HOUR_OF_DAY, _end.get(Calendar.HOUR_OF_DAY) );
		endCalendar.set( Calendar.MINUTE, _end.get(Calendar.MINUTE) );
		long from = startCalendar.getTime().getTime(); 
		long to = endCalendar.getTime().getTime(); 
		return to - from; 
	}

//	********************** ScheduleEntry Interface methods **********************
    /* (non-Javadoc)
     * @see org.apache.myfaces.custom.schedule.model.ScheduleEntry#getId()
     */
    public String getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see org.apache.myfaces.custom.schedule.model.ScheduleEntry#getTitle()
     */
    public String getTitle() {
        return title;
    }

    /* (non-Javadoc)
     * @see org.apache.myfaces.custom.schedule.model.ScheduleEntry#getSubtitle()
     */
    public String getSubtitle() {
        return subtitle;
    }

    /* (non-Javadoc)
     * @see org.apache.myfaces.custom.schedule.model.ScheduleEntry#getDescription()
     */
    public String getDescription() {
        return description;
    }

    /* (non-Javadoc)
     * @see org.apache.myfaces.custom.schedule.model.ScheduleEntry#getStartTime()
     */
    public Date getStartTime() {
        return startTime;
    }

    /* (non-Javadoc)
     * @see org.apache.myfaces.custom.schedule.model.ScheduleEntry#getEndTime()
     */
    public Date getEndTime() {
        return endTime;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.myfaces.custom.schedule.model.ScheduleEntry#isAllDay()
     */
	public boolean isAllDay() {
		return isAllDay;
	}

	/* (non-Javadoc)
	 * @see com.code.aon.planner.IEvent#isHoliday()
	 */
	public boolean isHoliday() {
		return this.category.name().equals( EventCategory.VACATION.name() ) ||
				this.category.name().equals( EventCategory.PUBLIC_HOLIDAY.name() ) ||
				this.category.name().equals( EventCategory.DAY_OFF.name() );
	}

	//	********************** IEvent Interface methods ***************************
	/* (non-Javadoc)
	 * @see com.code.aon.planner.IEvent#getRealStartTime()
	 */
	public Date getRealStartTime() {
		return this.realStartTime;
	}

    /* (non-Javadoc)
	 * @see com.code.aon.planner.IEvent#getRealEndTime()
	 */
	public Date getRealEndTime() {
		return this.realEndTime;
	}

    /* (non-Javadoc)
     * @see com.code.aon.planner.IEvent#getCategory()
     */
    public EventCategory getCategory() {
        return category;
    }

    /* (non-Javadoc)
     * @see com.code.aon.planner.IEvent#getState()
     */
    public EventStatus getState() {
        return state;
    }

	/* (non-Javadoc)
     * @see com.code.aon.planner.IEvent#getRecurrences()
     */
    public Set getRecurrences() {
        return Collections.unmodifiableSet(this.recurrences);
    }

	/* (non-Javadoc)
	 * @see com.code.aon.planner.IEvent#getDur()
	 */
	public Dur getDur() {
		Dur dur = new Dur(1, 0, 0, 0);
    	float duration = getDuration();
    	if ( duration > 0 ) {
    		int _hours = (int) Math.floor( duration/(1000*60*60) ); 
        	int _minutes = 
        		(int) Math.floor( ( duration/(1000*60*60) - _hours) * 60.0 + 0.5 );
    		dur = new Dur( 0, _hours, _minutes, 0 );
    	}
    	return dur;
	}

	/* (non-Javadoc)
	 * @see com.code.aon.planner.IEvent#getEndHour()
	 */
	public int getEndHour() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.endTime);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/* (non-Javadoc)
	 * @see com.code.aon.planner.IEvent#getStartHour()
	 */
	public int getStartHour() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.startTime);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/* (non-Javadoc)
	 * @see com.code.aon.planner.IEvent#getWrappedShift()
	 */
	public String getWrappedShift() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.startTime);
		return INTERVAL[calendar.get(Calendar.AM_PM)];
	}

	/* (non-Javadoc)
	 * @see com.code.aon.planner.IEvent#getComponent()
	 */
	public Component getComponent() {
		if (this.isDirty) {
			this.component = VEventVisitor.createVEvent(this);
			this.isDirty = false;
		}
		return this.component;
	}

    /* (non-Javadoc)
	 * @see com.code.aon.planner.IEvent#getPeriod()
	 */
	public Period getPeriod() {
		return new Period(CalendarUtil.getICalDateTime(this.startTime, false), CalendarUtil.getICalDateTime(this.endTime, false));
	}

	/* (non-Javadoc)
     * @see com.code.aon.planner.IEvent#visit(com.code.aon.planner.INodeVisitor)
     */
    public void visit(INodeVisitor visitor) {
        visitor.visitEvent(this);
    }

	// added for performance comparison
	@SuppressWarnings("unchecked")
	public Object clone() throws CloneNotSupportedException {
		Event cClone = (Event) super.clone();
		cClone.setCategory( this.getCategory() ); 
		cClone.setState( this.getState() );
		Iterator<Recur> iter = this.getRecurrences().iterator();
		while (iter.hasNext()) {
			Recur r = iter.next();
			cClone.addRecurrence(r);
		}
		return cClone;
	}
    
}
