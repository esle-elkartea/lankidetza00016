/*
 * Created on 10-nov-2005
 *
 */
package com.code.aon.planner;

import java.util.Date;
import java.util.Set;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Period;

import com.code.aon.calendar.enumeration.EventCategory;
import com.code.aon.planner.enumeration.EventStatus;

/**
 * 
 * @author Consulting & Development. I�aki Ayerbe - 18-nov-2005
 * @since 1.0
 *
 */
public interface IEvent {

	static final int CREATE = 0;
	static final int READ 	= 1;
	static final int UPDATE = 2;
	static final int DELETE = 3;
	static final String[] INTERVAL = {"AM", "PM"};

	/**
     * Retorna el identificador del evento.
     * 
     * @return String
     */
    String getId();

    /**
     * @return Returns the title.
     */
    String getTitle();

    /**
     * @return Returns the subtitle.
     */
    String getSubtitle();

    /**
     * Retorna la descripci�n del evento.
     * 
     * @return La descripci�n del evento.
     */
    String getDescription();

    /**
     * Retorna la fecha de inicio del evento en el planificador.
     * 
     * @return La fecha de creaci�n del evento.
     */
    Date getStartTime();

    /**
     * Retorna la fecha de creaci�n del evento.
     * 
     * @return La fecha de creaci�n del evento.
     */
    Date getRealStartTime();

    /**
     * La fecha final de visualizacion del evento en el planificador.
     * 
     * @return La fecha fin o de finalizaci�n del evento.
     */
    Date getEndTime();

    /**
     * La fecha de finalizaci�n, dependiendo de la naturaleza del evento.
     * 
     * @return La fecha fin o de finalizaci�n del evento.
     */
    Date getRealEndTime();

    /**
     * @return Returns true if the event last all day.
     */
    boolean isAllDay();

    /**
     * @return Returns true if the event is a holiday.
     */
    boolean isHoliday();

    /**
     * @return Returns true if the event is a spreadable.
     */
    boolean isSpreadable();

    /**
     * Retorna la categor�a edl evento.
     * 
     * @return la categor�a del evento.
     */
    EventCategory getCategory();

    /**
     * Retorna el estado en el que se encuentra el evento.
     * 
     * @return El estado del evento.
     */
    EventStatus getState();

    /**
     * Devuelve la lista con las repeticiones del evento.
     * 
     * @return
     */
    Set getRecurrences();

	/**
	 * Devuelve la duraci�n del evento en D�as/Horas/Minutos/Segundos.
	 * 
	 * @return
	 */
	Dur getDur();

	/**
	 * Devuelve la hora de finalizacion del evento. 12.
	 * 
	 * @return
	 */
	int getEndHour();

	/**
	 * Devuelve la hora de inicio del evento. 8.
	 * 
	 * @return
	 */
	int getStartHour();

	/**
	 * Devuelve el intervalo horario. AM � PM.
	 * 
	 * @return Returns the wrapped shift.
	 */
	String getWrappedShift();

	/**
     * Devuelve el objeto persistente asociado que se grabara en el calendario.
     *  
     * @return
     */
    Component getComponent();

    /**
     * Devuelve el periodo de inicio y fin del evento.
     *  
     * @return
     */
    Period getPeriod();

	/**
     * Permite visitar la interfaz.
     * 
     * @param visitor
     */
    void visit(INodeVisitor visitor);

}
