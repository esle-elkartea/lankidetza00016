/*
 * Created on 16-dic-2005
 *
 */
package com.code.aon.planner.core;

import java.util.Iterator;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Status;

import com.code.aon.calendar.CalendarUtil;

import com.code.aon.planner.IEvent;
import com.code.aon.planner.INodeVisitor;
import com.code.aon.planner.ITask;
import com.code.aon.planner.enumeration.EventStatus;
import com.code.aon.planner.util.PlannerUtil;

public class VEventVisitor implements INodeVisitor {

    private static final VEventVisitor VISITOR = new VEventVisitor();

    VEvent vEvent;

    /**
     * @return Returns the vEvent.
     */
    public VEvent getVEvent() {
        return vEvent;
    }

    /**
     * @param vevent The vEvent to set.
     */
    public void setVEvent(VEvent vEvent) {
        this.vEvent = vEvent;
    }

    /* (non-Javadoc)
     * @see com.code.aon.planner.INodeVisitor#visitEvent(com.code.aon.planner.IEvent)
     */
    public void visitEvent(IEvent event) {
        if ( event.isAllDay() ) {
            Date start = CalendarUtil.getICalDateTime( event.getStartTime(), false );
			vEvent = new VEvent( start, new Dur(1, 0, 0, 0), event.getTitle() );
        } else { 
            Date start = CalendarUtil.getICalDateTime( event.getStartTime(), false );
            if (event.getRecurrences().size() == 0) {
            	Date end = CalendarUtil.getICalDateTime( event.getRealEndTime(), false );
            	vEvent = new VEvent( start, end, event.getTitle() );
            } else {
                vEvent = new VEvent( start, event.getDur(), event.getTitle() );
            }
        }
        String id = event.getId();
        vEvent.getProperties().add( new ProdId(id) );
        String subtitle = PlannerUtil.convertNull2Blank( event.getSubtitle() );
        vEvent.getProperties().add( new Location(subtitle) );
        vEvent.getProperties().add( new Description(event.getDescription()) );
        EventStatus status = (event.getState() == null)? EventStatus.TENTATIVE: event.getState();
        vEvent.getProperties().add( new Status(status.name()) );
        vEvent.getProperties().add( new Categories(event.getCategory().name()) );
//	TODO Alarma(VAlarm)
        Iterator iter = event.getRecurrences().iterator();
        while (iter.hasNext()) {
            Recur element = (Recur)iter.next();
            visitRecur(element);
        }
    }

    /* (non-Javadoc)
     * @see com.code.aon.planner.INodeVisitor#visitRecur(com.code.aon.planner.IRecur)
     */
    public void visitRecur(Recur recur) {
        vEvent.getProperties().add( new RRule(recur) );
    }

    /* (non-Javadoc)
     * @see com.code.aon.planner.INodeVisitor#visitTask(com.code.aon.planner.ITask)
     */
    public void visitTask(ITask task) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Crea un evento válido para el calendario ics.
     * 
     * @param event
     * @return
     */
    public static final VEvent createVEvent(IEvent event) {
        VISITOR.visitEvent(event);
        return VISITOR.getVEvent();
    }

}
