package com.code.aon.ui.planner.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.code.aon.planner.enumeration.TaskStatus;
import com.code.aon.planner.util.PlannerUtil;

/**
 * 
 * @author Consulting & Development. I�aki Ayerbe - 24-nov-2005
 * @since 1.0
 *
 */

public class TaskStatusConverter implements Converter {

    /* (non-Javadoc)
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     */
    public Object getAsObject(FacesContext ctx, UIComponent c, String text)
            throws ConverterException {
    	TaskStatus result = null;
		try {
		    if (text == null || text.trim().equals(PlannerUtil.EMPTY_STRING)) {
		        return text;
		    }
		    int i = Integer.parseInt(text);
		    result = TaskStatus.get((byte) i);
		} catch (NumberFormatException e) {
			result = TaskStatus.get(text, ctx.getViewRoot().getLocale());
		}
		return result;
    }

    /* (non-Javadoc)
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    public String getAsString(FacesContext ctx, UIComponent c, Object value)
            throws ConverterException {
		if (value == null) {
		    return null;
		}
		if (! (value instanceof TaskStatus) ) {
			return value.toString();
		}
		String result = ((TaskStatus)value).getName(ctx.getViewRoot().getLocale());
		return result;
    }

}
