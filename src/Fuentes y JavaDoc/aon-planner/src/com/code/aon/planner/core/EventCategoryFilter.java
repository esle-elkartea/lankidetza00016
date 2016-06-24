/*
 * Created on 22-nov-2005
 *
 */
package com.code.aon.planner.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.code.aon.calendar.enumeration.EventCategory;

/**
 * Filtro con las categor�as del evento.
 * 
 * @author Consulting & Development. I�aki Ayerbe - 23-nov-2005
 * @since 1.0
 *
 */
public class EventCategoryFilter {

    /** Lista de las categor�as validadas por el filtro */
    List<EventCategory> categories = new ArrayList<EventCategory>();

    /**
     * Devuelve verdadero si la categor�a pasada por par�metro esta en la lista de categor�as, 
     * falso en el resto de casos.
     * 
     * @param category
     * @return
     */
    public boolean accept(int category) {
    	if ( this.categories.size() > 0 ) {
	        Iterator iter = this.categories.iterator();
	        while (iter.hasNext()) {
	            EventCategory element = (EventCategory) iter.next();
	            if (element.ordinal() == category)
	                return true;
	        }
    	} else {
    		return true;
    	}
        return false;
    }

    /**
     * A�ade una nueva categor�a a la lista.
     * 
     * @param eventCategory
     */
    public void add(EventCategory eventCategory) {
        categories.add(eventCategory);
    }
}
