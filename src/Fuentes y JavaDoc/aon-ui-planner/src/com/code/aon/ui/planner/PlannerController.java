/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.code.aon.ui.planner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Duration;

import org.apache.myfaces.custom.calendar.HtmlInputCalendar;
import org.apache.myfaces.custom.schedule.ScheduleMouseEvent;
import org.apache.myfaces.custom.schedule.model.ScheduleEntry;
import org.apache.myfaces.custom.schedule.model.ScheduleModel;
import org.apache.myfaces.custom.schedule.util.ScheduleUtil;

import com.code.aon.calendar.AonCalendar;
import com.code.aon.calendar.CalendarUtil;
import com.code.aon.calendar.enumeration.EventCategory;
import com.code.aon.common.ManagerBeanException;

import com.code.aon.planner.EventException;
import com.code.aon.planner.IEvent;
import com.code.aon.planner.model.CalendarScheduleModel;
import com.code.aon.planner.model.TaskModel;
import com.code.aon.planner.util.PlannerUtil;

/**
 * Controlador de la agenda. Se encarga de gestionar las operaciones a realizar sobre la misma.
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 14-nov-2005
 * @since 1.0
 *
 */
public class PlannerController implements IPlannerCallbackHandler {

	/** Obtain a suitable Logger. */
	static final Logger LOGGER = Logger.getLogger( PlannerController.class.getName() );
	/** Default faces outcome. */
    static final String CALENDAR = "_calendar";
	/** Default faces outcome. */
    static final String EVENT = "_event";
	/** Holiday event category. */
    static final String HOLIDAY = "_holiday";

    /** Calendar schedule model */
    private CalendarScheduleModel scheduleModel;
    /** Selected Date. */
    private Date date = new Date();
    /** Date input component. */
    private HtmlInputCalendar dateInput;
    /** Planner mode. Day, Week Work, Week, Month, Year(In future version). */
    private Integer mode = new Integer(ScheduleModel.WORKWEEK);
    /** Planner theme. */
    private String theme;
    /** Event tooltip. */
    private Boolean tooltip;
    /** Read only planner. */
    private Boolean readonly;
    /** Planner Row height. */
    private int detailedRowHeight;
    
    /** Planner EventCategory list. */
    private List<SelectItem> categories;
    /** Controller outcome. */
    private String outcome = PlannerUtil.EMPTY_STRING;
    /** Tell if the planner generated events are spreadable through calendars hierarchy. */
    private boolean isSpreadable;
    /** Diary view mode. */
    private Integer diary;
    /** All year Events of the following categories: APPOINTMENT, INCIDENCE. */
    private DataModel events;
    /** All year Events of the following categories: VACATION, DAY_OFF, PUBLIC_HOLIDAY. */
    private DataModel holidays;

    /** Modelo que define las tareas a realizar y creadas por el recurso seleccionado */
    private TaskModel taskModel;
    private int startHour;
    private int endHour;

    /**
     * @param model The model to set.
     */
    public void setScheduleModel(CalendarScheduleModel model) {
        this.scheduleModel = model;
        this.scheduleModel.setMode( this.mode.intValue() );
        this.scheduleModel.setSelectedDate( this.date );
    }

    /**
     * @return Returns the model.
     */
    public ScheduleModel getScheduleModel() {
        return scheduleModel;
    }

    /**
     * @param date The date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return Returns the date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param dateInput The dateInput to set.
     */
    public void setDateInput(HtmlInputCalendar dateInput) {
        this.dateInput = dateInput;
    }

    /**
     * @return Returns the dateInput.
     */
    public HtmlInputCalendar getDateInput() {
        return dateInput;
    }

    /**
     * @param mode The mode to set.
     */
    public void setMode(Integer mode) {
        this.mode = mode;
    }

    /**
     * @return Returns the mode.
     */
    public Integer getMode() {
        return mode;
    }

    /**
     * <p>
     * The theme that will be used to render the schedule
     * </p>
     * 
     * @param theme The theme to set.
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * <p>
     * The theme that will be used to render the schedule
     * </p>
     * 
     * @return Returns the theme.
     */
    public String getTheme() {
        return theme;
    }

    /**
     * <p>
     * Should the schedule be readonly?
     * </p>
     * 
     * @param readonly The readonly to set.
     */
    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }

    /**
     * <p>
     * Should the schedule be readonly?
     * </p>
     * 
     * @return Returns the readonly.
     */
    public Boolean getReadonly() {
        return readonly;
    }

    /**
	 * @return the detailedRowHeight
	 */
	public int getDetailedRowHeight() {
		return detailedRowHeight;
	}

	/**
	 * @param detailedRowHeight the detailedRowHeight to set
	 */
	public void setDetailedRowHeight(int detailedRowHeight) {
		this.detailedRowHeight = detailedRowHeight;
	}

	/**
     * <p>
     * Should tooltips be shown on the schedule?
     * </p>
     * 
     * @param tooltip The tooltip to set.
     */
    public void setTooltip(Boolean tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * <p>
     * Should tooltips be shown on the schedule?
     * </p>
     * 
     * @return Returns the tooltip.
     */
    public Boolean getTooltip() {
        return tooltip;
    }

    /**
	 * @param categories the categories to set
	 */
	public void setCategories(List<SelectItem> categories) {
		this.categories = categories;
	}

	/**
	 * @param outcome The outcome to set.
	 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	/**
	 * @param isSpreadable the isSpreadable to set
	 */
	public void setSpreadable(boolean isSpreadable) {
		this.isSpreadable = isSpreadable;
	}

	/**
	 * @return the diary
	 */
	public Integer getDiary() {
		return diary;
	}

	/**
	 * @param diary the diary to set
	 */
	public void setDiary(Integer diary) {
		this.diary = diary;
	}

	/**
	 * @return Returns the events.
	 */
	public DataModel getEvents() {
		return events;
	}

	/**
	 * @param events The events to set.
	 */
	public void setEvents(DataModel events) {
		this.events = events;
	}

	/**
	 * @return the holidays
	 */
	public DataModel getHolidays() {
		return holidays;
	}

	/**
	 * @param holidays the holidays to set
	 */
	public void setHolidays(DataModel holidays) {
		this.holidays = holidays;
	}

	/**
	 * @return Returns the endHour.
	 */
	public int getEndHour() {
		return endHour;
	}

	/**
	 * @param endHour The endHour to set.
	 */
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	/**
	 * @return Returns the startHour.
	 */
	public int getStartHour() {
		return startHour;
	}

	/**
	 * @param startHour The startHour to set.
	 */
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

   /**
     * @return Returns the taskModel.
     */
    public TaskModel getTaskModel() {
        return taskModel;
    }

    /**
     * @param taskModel The taskModel to set.
     */
    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }

//    public String getFormattedStartDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        return sdf.format( this.scheduleModel.getSelectedDate() );
//    }
//
//    public String getFormattedEndDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Calendar _calendar = Calendar.getInstance();
//        _calendar.setTime( this.scheduleModel.getSelectedDate() );
//        int days2Add = 1;
//        if ( ScheduleModel.WORKWEEK == this.mode.intValue() )
//                days2Add = 6; // Último día incluido.
//        _calendar.add( Calendar.DATE, days2Add );
//        return sdf.format( _calendar.getTime() );
//    }
//
//    /**
//     * Calcula la hora de finalización a partir de una hora dada y aplicándole 
//     * la duración, en minutos, del planificador.
//     * 
//     * @param date
//     * @return
//     */
//    public Date getEndDate(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE, this.scheduleModel.getInterval().getMinutes());
//        return calendar.getTime();
//    }

	/**
     * <p>
     * a String describing the selected entry on the schedule
     * </p>
     *
     * @return the entry as a string
     */
    public String getSelectedEntry() {
        if ((this.scheduleModel == null) || (this.scheduleModel.getSelectedEntry() == null)) {
            return PlannerUtil.EMPTY_STRING;
        }
        FacesContext ctx = FacesContext.getCurrentInstance();
        IEvent event = (IEvent)this.scheduleModel.getSelectedEntry();
        return event.getCategory().getName(ctx.getViewRoot().getLocale()) + ": " + //$NON-NLS-1$
               event.getTitle() + " - " +  //$NON-NLS-1$
               event.getState().getName(ctx.getViewRoot().getLocale());
    }

    /**
     * Is there an entry currently selected?
     * 
     * @return if an entry is currently selected
     */
    public boolean isEntrySelected() {
        return this.scheduleModel != null && this.scheduleModel.getSelectedEntry() != null;
    }

    /**
     * Return Calendar outcome.
     * 
     * @return
     */
    public String calendarOutcome() {
    	return this.outcome + CALENDAR;
    }

    /**
     * Return Event outcome.
     * 
     * @return
     */
    public String eventOutcome() {
    	return this.outcome + EVENT;
    }

    /**
     * Initialize controller with the given <code>ScheduleModel</code>.
     * 
     * @param csm
     * @param outcome
     * @param spreadable
     * @throws ManagerBeanException
     */
    public void initialize(CalendarScheduleModel csm, String outcome, boolean spreadable) 
    			throws ManagerBeanException {
		csm.setMode( ScheduleModel.WORKWEEK );
		setMode( ScheduleModel.WORKWEEK );
       	setDate( new Date() );
       	setScheduleModel(csm);
		setCategories( PlannerUtil.getCategories( PlannerUtil.getHolidayCategories() ) );
		setOutcome(outcome);
		setSpreadable( spreadable );
		setEvents( new ListDataModel( new ArrayList<ScheduleEntry>() ) );
		ControllerUtil.getWorkingTimeController().initialize( csm.getCalendar() );
    }

    /**
     * Load all holiday events of this calendar during the year.
     * 
     * @param event
     */
    public void loadHolidays(ActionEvent event) {
		List<ScheduleEntry> list = new ArrayList<ScheduleEntry>();
        EventCategory[] ec = PlannerUtil.getHolidayCategories();
		ComponentList components = this.scheduleModel.getCalendar().getVEvents(ec);
        for (Iterator i = components.iterator(); i.hasNext();) {
        	VEvent vevent = (VEvent) i.next();
		    Date start = CalendarUtil.getDate( vevent.getStartDate().getDate() );
		    Date end = start;
		    if (vevent.getProperties().getProperty(Property.DTEND) != null)
		    	end = CalendarUtil.getDate( (DateTime)vevent.getEndDate().getDate() );
            list.add( PlannerUtil.getScheduleEntry(vevent, start, end) );
        }
        this.holidays = new ListDataModel(list);
        String view = event.getComponent().getId();
        this.mode = new Integer( view.substring(view.lastIndexOf("_") + 1) );
        this.scheduleModel.setMode( this.mode.intValue() );
        this.dateInput.setValue( this.date = this.scheduleModel.getSelectedDate() );
    }

    /**
     * Load all events, except holidays and work, of this calendar during the year.
     * 
     * @param actionEvent the action event
     */
	public void loadEvents(ActionEvent event) {
		List<ScheduleEntry> list = new ArrayList<ScheduleEntry>();
        EventCategory[] ec = PlannerUtil.getEventCategories();
		ComponentList components = this.scheduleModel.getCalendar().getVEvents(ec);
        for (Iterator i = components.iterator(); i.hasNext();) {
        	VEvent vevent = (VEvent) i.next();
		    Date start = CalendarUtil.getDate( (DateTime) vevent.getStartDate().getDate() );
		    Date end = start;
		    if (vevent.getProperties().getProperty(Property.DTEND) != null)
		    	end = CalendarUtil.getDate( (DateTime) vevent.getEndDate().getDate() );
		    else if (vevent.getProperties().getProperty(Property.DURATION) != null) {
		    	Duration duration = 
		    		(Duration) vevent.getProperties().getProperty(Property.DURATION);
		    	Period p = 
		    		new Period( (DateTime) vevent.getStartDate().getDate(), duration.getDuration() );
		    	end = CalendarUtil.getDate( p.getEnd() );
		    	
		    }
            list.add( PlannerUtil.getScheduleEntry(vevent, start, end) );
        }
        this.events = new ListDataModel(list);
        String view = event.getComponent().getId();
        this.mode = new Integer( view.substring(view.lastIndexOf("_") + 1) );
        this.scheduleModel.setMode( this.mode.intValue() );
        this.dateInput.setValue( this.date = this.scheduleModel.getSelectedDate() );
	}

    /**
     * Create a new Event which its Category depends on the list of event categories allowed.
     * 
     * @param actionEvent the action event
     */
    public void onNewEvent(ActionEvent event) {
        int id = this.scheduleModel.getCalendar().getPrimaryKey().intValue();
        EventManager em = ControllerUtil.getEventManager();
        em.setNew(true);
        IEvent evt = PlannerUtil.createEvent( id, new Date() );
        if ( event.getComponent().getId().indexOf(HOLIDAY) > -1 )
            evt = PlannerUtil.createHolidayEvent( id, new Date() );
        em.initialize( evt, this, "Creacion del Evento" );
    }

	/**
     * Select an Event which its Category depends on the list of event categories allowed.
     * 
     * @return
	 */
	public void onSelectEvent(ActionEvent evt) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		EventManager em = ControllerUtil.getEventManager();
		em.setNew(false);
		IEvent event = null;
		if ( evt.getComponent().getId().indexOf(HOLIDAY) > -1 )
			event = (IEvent) this.holidays.getRowData();
		else
			event = (IEvent) this.events.getRowData();
		em.initialize( event, this, "Edicion del evento: " + event.getCategory().getName( ctx.getViewRoot().getLocale() ) );
	}

	/**
     * <p>
     * When a date is selected in the calendar component, navigate to that date
     * in the schedule component.
     * </p>
     *
     * @param event the action event
     */
    public void calendarValueChanged(ValueChangeEvent event) {
        if (this.date != null && 
                ScheduleUtil.truncate(this.date).equals(ScheduleUtil.truncate((Date)this.dateInput.getValue())))
            return;

        this.date = (Date)this.dateInput.getValue();
        this.scheduleModel.setSelectedDate(this.date);
        this.scheduleModel.refresh();
    }

	/**
     * <p>
     * When the previous range of days event is sent, navigate to that date in the schedule component.
     * </p>
     *
     * @param event the action event
     */
    public void prevDayChanged(ActionEvent event) {
        Calendar _calendar = Calendar.getInstance();
        _calendar.setTime( this.scheduleModel.getSelectedDate() );
        int days2Add = -1;
        if ( ScheduleModel.WORKWEEK == this.mode.intValue() )
        		days2Add = -7;
        _calendar.add( Calendar.DATE, days2Add );
        if ( _calendar.getTime().compareTo( new Date() ) <= 0 ) {
        	Calendar today = Calendar.getInstance();
        	today.setTime( new Date() );
        	_calendar.set( Calendar.DATE, today.get(Calendar.DATE) );
        }
        this.dateInput.setValue( _calendar.getTime() );
        this.date = (Date)this.dateInput.getValue();
        this.scheduleModel.setSelectedDate(this.date);
        this.scheduleModel.refresh();
    }

	/**
     * <p>
     * When the next range of days event is sent, navigate to that date in the schedule component.
     * </p>
     *
     * @param event the action event
     */
    public void nextDayChanged(ActionEvent event) {
        Calendar _calendar = Calendar.getInstance();
        _calendar.setTime( this.scheduleModel.getSelectedDate() );
        int days2Add = 1;
        if ( ScheduleModel.WORKWEEK == this.mode.intValue() )
        		days2Add = 7;
        _calendar.add( Calendar.DATE, days2Add );
        this.dateInput.setValue( _calendar.getTime() );
        this.date = (Date)this.dateInput.getValue();
        this.scheduleModel.setSelectedDate(this.date);
        this.scheduleModel.refresh();
    }

    /** 
     * Cambia la vista de la agenda.
     * 
     * @param event
     */
    public void calendarView(ActionEvent event) {
        String view = event.getComponent().getId();
        if (mode != null && view.indexOf(PlannerUtil.EMPTY_STRING + this.mode) > -1)
            return;

        this.mode = new Integer( view.substring(view.lastIndexOf("_") + 1) );
        this.scheduleModel.setMode( this.mode.intValue() );
        this.scheduleModel.refresh();
        this.dateInput.setValue( this.date = this.scheduleModel.getSelectedDate() );
    }

    /**
     * Elimina un evento de la agenda desde.
     * 
     * @param event
     */
    public void remove(ActionEvent event) {
    	IEvent _event = (IEvent) scheduleModel.getSelectedEntry();
        remove(_event);
    }

    /**
     * Busca el tiempo libre disponible para la agenda asociada al <code>ScheduleModel</code>
     * 
     * @return
     */
    public String onLook4FreeTime(CalendarScheduleModel csm) {
        this.scheduleModel = csm;
//  TODO Añadir mas filtros. Cargarlos desde un XML configurable, por ejemplo.
        this.scheduleModel.getFilter().add(EventCategory.APPOINTMENT);
        this.scheduleModel.setMode(this.mode.intValue());
        this.date = PlannerUtil.fetchFirstFreeDate( this.scheduleModel.getCalendar(), this.date );
        this.dateInput.setValue(this.date);
        this.scheduleModel.setSelectedDate(this.date);
        return this.outcome + CALENDAR;
    }

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#getCategories()
	 */
	public List getCategories() {
		return this.categories;
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#outcome()
	 */
	public String getOutcome() {
		return this.outcome;
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#isSpreadable()
	 */
	public boolean isSpreadable() {
		return this.isSpreadable;
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#refresh(com.code.aon.planner.IEvent)
	 */
	public void refresh(IEvent event) {
        this.dateInput.setValue( this.date = this.scheduleModel.getSelectedDate() );
		this.scheduleModel.setSelectedEntry( (ScheduleEntry)event );
		this.scheduleModel.refresh();
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#hasEvents(com.code.aon.planner.IEvent)
	 */
	public boolean hasEvents(Date from, Date to, EventCategory[] ec, String id) {
		AonCalendar aonCalendar = this.scheduleModel.getCalendar();

		ec = new EventCategory[EventCategory.values().length];
        ec[EventCategory.APPOINTMENT.ordinal()] = EventCategory.APPOINTMENT;
        ec[EventCategory.INCIDENCE.ordinal()] = EventCategory.INCIDENCE;
		
		return aonCalendar.hasEvents( from, to, ec, id );
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#add(com.code.aon.planner.IEvent)
	 */
	@SuppressWarnings("unchecked")
	public void add(IEvent event) throws EventException {
		AonCalendar aonCalendar = this.scheduleModel.getCalendar();
		aonCalendar.getCalendar().getComponents().add(event.getComponent());
        if ( PlannerUtil.getHolidayCategories()[ event.getCategory().ordinal() ] != null ) {
        	PlannerUtil.addExDate(aonCalendar, event);
        }
		ControllerUtil.getCalendarManagerBean().updateCalendar(aonCalendar);
		if ( PlannerUtil.getHolidayCategories()[ event.getCategory().ordinal() ] != null ) {
			if ( this.holidays == null ) {
				this.holidays = new ListDataModel( new ArrayList<ScheduleEntry>() );
			}
        	( (List)this.holidays.getWrappedData() ).add(event);
        } else {
			if ( this.events == null ) {
				this.events = new ListDataModel( new ArrayList<ScheduleEntry>() );
			}
        	( (List)this.events.getWrappedData() ).add(event);
        }
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#remove(com.code.aon.planner.IEvent)
	 */
	public boolean remove(IEvent event) {
		AonCalendar aonCalendar = this.scheduleModel.getCalendar();
		boolean removed = aonCalendar.getCalendar().getComponents().remove(event.getComponent());
        if ( PlannerUtil.getHolidayCategories()[ event.getCategory().ordinal() ] != null ) {
        	PlannerUtil.removeExDate(aonCalendar, event);
        }
		ControllerUtil.getCalendarManagerBean().updateCalendar(aonCalendar);
        if ( PlannerUtil.getHolidayCategories()[ event.getCategory().ordinal() ] != null ) {
        	( (List)this.holidays.getWrappedData() ).remove(event);
        } else {
        	( (List)this.events.getWrappedData() ).remove(event);
        }
		return removed;
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#update(com.code.aon.planner.IEvent)
	 */
	public void update(IEvent event) throws EventException {
		AonCalendar aonCalendar = this.scheduleModel.getCalendar();
		VEvent oldVEvent = (VEvent) aonCalendar.updateEvent( (VEvent) event.getComponent() );
        if ( PlannerUtil.getHolidayCategories()[ event.getCategory().ordinal() ] != null ) {
        	PlannerUtil.updateExDate(aonCalendar, oldVEvent, event);
        }
		ControllerUtil.getCalendarManagerBean().updateCalendar(aonCalendar);
	}

	public void scheduleClicked(ScheduleMouseEvent event) {
		switch(event.getEventType()) {
			case 1: // '\001'
				int id1 = this.scheduleModel.getCalendar().getPrimaryKey().intValue();
				IEvent e1 = PlannerUtil.createEvent( id1, event.getClickedTime() );
				ControllerUtil.getEventManager().initialize( e1, this, "Creacion del evento" );
				LOGGER.fine( "schedule body was clicked: " + event.getClickedTime() );
				break;
			case 2: // '\002'
				int id2 = this.scheduleModel.getCalendar().getPrimaryKey().intValue();
				IEvent e2 = PlannerUtil.createHolidayEvent( id2, event.getClickedDate() );
				ControllerUtil.getEventManager().initialize( e2, this, "Creacion del evento" );
				LOGGER.fine( "schedule header was clicked: " + event.getClickedDate() );
				break;
			case 3: // '\003' TODO
//	            UISchedule schedule = (UISchedule) event.getComponent();
//	            IEvent e3 = (IEvent) schedule.getSubmittedEntry();
//				ControllerUtil.getEventManager().initialize( e3, this );
				LOGGER.fine( "schedule entry was clicked." );
		        break;
			default:
				LOGGER.fine( "no schedule mouse events registered" );
				break;
		}
	}

}
