/*
 * Created on 21-nov-2005
 *
 */
package com.code.aon.ui.planner;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;

import com.code.aon.calendar.AonCalendar;
import com.code.aon.calendar.CalendarUtil;
import com.code.aon.calendar.enumeration.EventCategory;
import com.code.aon.common.ManagerBeanException;

import com.code.aon.planner.EventException;
import com.code.aon.planner.IEvent;
import com.code.aon.planner.IPlannerListener;
import com.code.aon.planner.core.Event;
import com.code.aon.planner.model.CalendarScheduleModel;
import com.code.aon.planner.util.PlannerUtil;
import com.code.aon.ui.planner.EventManager;
import com.code.aon.ui.planner.IPlannerCallbackHandler;
import com.code.aon.ui.planner.PlannerController;

public class WorkingTimeController implements IPlannerCallbackHandler, IPlannerListener {

    /** Working Time DataModel. A list of working time periods. */
    private DataModel workingTimeModel;
    /** Working hours during the current year. */
    private double amount;
    /** Selecteed event index */
    private int selectedEventIndex;
    /** Old selected event */
    private IEvent oldEvent;
//    /** */
//    private boolean isCreated;

	/**
	 * @return Returns the model.
	 */
	public DataModel getModel() {
		return workingTimeModel;
	}

	/**
	 * @param model The model to set.
	 */
	public void setModel(DataModel workingTimeModel) {
		this.workingTimeModel = workingTimeModel;
	}

//    /**
//	 * @return Returns the isCreated.
//	 */
//	public boolean isCreated() {
//		return isCreated;
//	}
//
//	/**
//	 * @param isCreated The isCreated to set.
//	 */
//	public void setCreated(boolean isCreated) {
//		this.isCreated = isCreated;
//	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
     * Inicializa el Evento horario de trabajo asociado al Calendario pasado por parámetro.
     * 
     * @param calendar
     * @throws ManagerBeanException 
     */
    public void initialize(AonCalendar calendar) throws ManagerBeanException {
		init(calendar);
	}

	/**
	 * Acción destinada a crear un evento nuevo de tipo: WORK.
     * 
     * @return
	 */
	@SuppressWarnings("unused")
	public void onReset(ActionEvent event) {
//		this.isCreated = false;
		PlannerController planner = ControllerUtil.getPlannerController(); 
    	EventManager em = ControllerUtil.getEventManager();
    	em.setNew(true);
    	CalendarScheduleModel csm = (CalendarScheduleModel) planner.getScheduleModel();
    	int id = csm.getCalendar().getPrimaryKey().intValue();
    	em.initialize( PlannerUtil.createWorkingTimeEvent( id, new Date() ), this, "Creacion del Horario Laboral" );
//    	em.blockEventActions();
	}

	/**
	 * Acción destinada a visualizar un evento de tipo: WORK, para su posterior modificación.
     * 
     * @return
	 * @throws CloneNotSupportedException 
	 */
	@SuppressWarnings("unused")
	public void onSelect(ActionEvent event) throws CloneNotSupportedException {
//		this.isCreated = true;
    	EventManager em = ControllerUtil.getEventManager();
    	em.setNew(false);
    	Event evt = (Event) this.workingTimeModel.getRowData();
    	this.oldEvent = (Event) evt.clone(); 
    	this.selectedEventIndex = this.workingTimeModel.getRowIndex();
    	em.initialize( evt, this, "Edicion del Horario Laboral" );
//    	em.blockEventActions();
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#outcome()
	 */
	public String getOutcome() {
		return ControllerUtil.getPlannerController().getOutcome();
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#isSpreadable()
	 */
	public boolean isSpreadable() {
		return ControllerUtil.getPlannerController().isSpreadable();
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#getCategories()
	 */
	public List getCategories() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        LinkedList<SelectItem> categories = new LinkedList<SelectItem>();
        categories.add( new SelectItem( EventCategory.WORK, EventCategory.WORK.getName(locale)) );
        return categories;
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#refresh(com.code.aon.planner.IEvent)
	 */
	public void refresh(IEvent event) {
		ControllerUtil.getPlannerController().refresh(event);
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#hasEvents(com.code.aon.planner.IEvent)
	 */
	public boolean hasEvents(Date from, Date to, EventCategory[] ec, String id) {
		return false;
//		if (!this.isCreated)
//			return false;
//
//        ec = new EventCategory[EventCategory.values().length];
//        ec[EventCategory.APPOINTMENT.ordinal()] = EventCategory.APPOINTMENT;
//    	Calendar _from = Calendar.getInstance();
//    	_from.setTime(from);
//    	Calendar _to = Calendar.getInstance();
//    	_to.setTime(to);
//        if (_from.get(Calendar.YEAR) == _to.get(Calendar.YEAR) &&
//        		_from.get(Calendar.MONTH) == _to.get(Calendar.MONTH) &&
//        		_from.get(Calendar.DATE) == _to.get(Calendar.DATE) ) {
//        	_to.add( Calendar.YEAR, 1);
//        }
//		return ControllerUtil.getPlannerController().hasEvents( from, _to.getTime(), ec, id );
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#add(com.code.aon.planner.IEvent)
	 */
	public void add(IEvent event) throws EventException {
		PlannerController planner = ControllerUtil.getPlannerController();
		AonCalendar aonCalendar = 
			( (CalendarScheduleModel) planner.getScheduleModel() ).getCalendar();
		if ( PlannerUtil.validate(aonCalendar, event) ) {
			PlannerUtil.addExDates(aonCalendar, event);
	        ControllerUtil.getPlannerController().add(event);
		} else {
			EventException e = new EventException("aon_planner_workevent_overlap_exception");
			e.setParameters( new Object[] {event.getTitle(), event.getStartTime(), event.getEndTime()} );
			throw e;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#remove(com.code.aon.planner.IEvent)
	 */
	public boolean remove(IEvent event) throws EventException {
		return ControllerUtil.getPlannerController().remove(event);
	}

	/* (non-Javadoc)
	 * @see com.code.aon.ui.planner.IPlannerCallbackHandler#update(com.code.aon.planner.IEvent)
	 */
	@SuppressWarnings("unchecked")
	public void update(IEvent event) throws EventException {
		PlannerController planner = ControllerUtil.getPlannerController();
		AonCalendar aonCalendar = 
			( (CalendarScheduleModel) planner.getScheduleModel() ).getCalendar();
		if ( PlannerUtil.validate(aonCalendar, event) ) {
			PlannerUtil.addExDates(aonCalendar, event);
			ControllerUtil.getPlannerController().update(event);
		} else {
			List l = (List) this.workingTimeModel.getWrappedData();
			l.set( selectedEventIndex, oldEvent );
			EventException e = new EventException("aon_planner_workevent_overlap_exception");
			e.setParameters( new Object[] {event.getTitle(), event.getStartTime(), event.getEndTime()} );
			throw e;
		}
	}

    /* (non-Javadoc)
	 * @see com.code.aon.planner.IPlannerListener#eventAdded(com.code.aon.planner.IEvent)
	 */
	@SuppressWarnings("unchecked")
	public void eventAdded(IEvent event) {
		if (event.getCategory().equals(EventCategory.WORK) ) {
			( (List)this.workingTimeModel.getWrappedData() ).add(event);
			calculateStartAndEndHours(event);
//			setCreated(true);
		}
		PlannerController planner = ControllerUtil.getPlannerController();
		CalendarScheduleModel csm = (CalendarScheduleModel) planner.getScheduleModel();
		this.amount = 
			PlannerUtil.calcYearlyHours(csm.getCalendar().getCalendar().getComponents(), null);
	}

	/* (non-Javadoc)
	 * @see com.code.aon.planner.IPlannerListener#eventRemoved(com.code.aon.planner.IEvent)
	 */
	@SuppressWarnings("unchecked")
	public void eventRemoved(IEvent event) {
		PlannerController planner = ControllerUtil.getPlannerController();
		CalendarScheduleModel csm = (CalendarScheduleModel) planner.getScheduleModel();
		if (event.getCategory().equals(EventCategory.WORK) ) {
			init( csm.getCalendar() );
		} else {
			this.amount = 
				PlannerUtil.calcYearlyHours(csm.getCalendar().getCalendar().getComponents(), null);
		}
	}

	/* (non-Javadoc)
	 * @see com.code.aon.planner.IPlannerListener#eventUpdated(com.code.aon.planner.IEvent)
	 */
	@SuppressWarnings("unchecked")
	public void eventUpdated(IEvent event) {
		if ( event.getCategory().equals(EventCategory.WORK) ) {
			calculateStartAndEndHours(event);
		}
		PlannerController planner = ControllerUtil.getPlannerController();
		CalendarScheduleModel csm = (CalendarScheduleModel) planner.getScheduleModel();
		this.amount = 
			PlannerUtil.calcYearlyHours(csm.getCalendar().getCalendar().getComponents(), null);
	}

    /**
     * Load Working Time and FreeWork Time shifts.
     * 
     * @return
     */
	@SuppressWarnings("unchecked")
    private final void init(AonCalendar calendar) {
    	int startHour = 24, endHour = 1;
		this.workingTimeModel = new ListDataModel(new ArrayList());
		ComponentList components = calendar.getVEvents(EventCategory.WORK); 
		Iterator iter = components.iterator();
		while (iter.hasNext()) {
			VEvent vevent = (VEvent) iter.next();
		    Date start = CalendarUtil.getDate( (DateTime)vevent.getStartDate().getDate() );
		    Date end = CalendarUtil.getDate( (DateTime)vevent.getEndDate().getDate() );
		    IEvent event = (IEvent) PlannerUtil.getScheduleEntry(vevent, start, end);
			( (List)this.workingTimeModel.getWrappedData() ).add(event);
			startHour = getStartHour(startHour, event);
			endHour = getEndHour(endHour, event); 
		}
		if (24 == startHour)
			startHour = 1;
		if (1 == endHour)
			endHour = 24;
		ControllerUtil.getPlannerController().setStartHour(startHour);
		ControllerUtil.getPlannerController().setEndHour(endHour);
		this.amount = 
			PlannerUtil.calcYearlyHours(calendar.getCalendar().getComponents(), null);
    }

	/**
     * Calculate the calendar start hour using the current hour and compares it with 
     * the event start hour and sets it to the planner.
     * 
     * @param startHour
     * @param event
     * @return
     */
	private int getStartHour(int startHour, IEvent event) {
		return (startHour > event.getStartHour())? event.getStartHour(): startHour;
	}

    /**
     * Calculate the calendar end hour using the current hour and compares it with 
     * the event end hour and sets it to the planner.
     * 
     * @param endHour
     * @param event
     * @return
     */
	private int getEndHour(int endHour, IEvent event) {
		return (endHour < event.getEndHour())? event.getEndHour(): endHour; 
	}

	/**
     * Re-calculate planner start and end hour comparing with the event passed by parameter.
     * 
     * @param event
     */
    private void calculateStartAndEndHours(IEvent event) {
		PlannerController planner = ControllerUtil.getPlannerController();
		int startHour = planner.getStartHour();
		int endHour = planner.getEndHour();
		startHour = getStartHour(startHour, event);
		endHour = getEndHour(endHour, event); 
		planner.setStartHour(startHour);
		planner.setEndHour(endHour);
    }

}
