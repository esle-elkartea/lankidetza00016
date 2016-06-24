/**
 * 
 */
package com.code.aon.ui.planner;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


/**
 * Manages application controller classes.
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 21/11/2006
 *
 */
public class ControllerUtil {

    /**
     * Return CalendarManagerBean controller.
     *  
     * @return
     */
    public static final CalendarManagerBean getCalendarManagerBean() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ValueBinding vb = 
        	ctx.getApplication().createValueBinding( PlannerConstants.CALENDAR_MANAGER );
        return (CalendarManagerBean) vb.getValue(ctx);
    }

    /**
     * Return PlannerController controller.
     *  
     * @return
     */
    public static final PlannerController getPlannerController() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ValueBinding vb = 
        	ctx.getApplication().createValueBinding( PlannerConstants.PLANNER );
        return (PlannerController) vb.getValue(ctx);
    }

    /**
     * Return WorkingTimeController controller.
     *  
     * @return
     */
    public static final WorkingTimeController getWorkingTimeController() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ValueBinding vb = 
        	ctx.getApplication().createValueBinding( PlannerConstants.WORKING_TIME );
        return (WorkingTimeController) vb.getValue(ctx);
    }

	/**
	 * Return EventManager Controller.
	 * 
	 * @return
	 */
	public static final EventManager getEventManager() {
      FacesContext ctx = FacesContext.getCurrentInstance();      
      ValueBinding vb = ctx.getApplication().createValueBinding( PlannerConstants.EVENT );
      return (EventManager)vb.getValue(ctx);
	}

	/**
	 * Recupera ReassignManager Controller.
	 * 
	 * @return
	 */
	public static final ReassignManager getReassignManager() {
      FacesContext ctx = FacesContext.getCurrentInstance();      
      ValueBinding vb = ctx.getApplication().createValueBinding( PlannerConstants.REASSIGN );
      return (ReassignManager)vb.getValue(ctx);
	}

}
