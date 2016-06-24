/*
 * Created on 14-sep-2006
 *
 */
package com.code.aon.ui.planner.launcher;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;

import org.apache.myfaces.custom.schedule.model.ScheduleEntry;

import com.code.aon.calendar.AonCalendar;
import com.code.aon.calendar.CalendarException;
import com.code.aon.calendar.CalendarManager;
import com.code.aon.calendar.enumeration.EventCategory;
import com.code.aon.common.IResourceLoader;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.planner.IOwner;
import com.code.aon.planner.model.CalendarScheduleModel;
import com.code.aon.planner.util.PlannerUtil;
import com.code.aon.ui.form.BasicController;
import com.code.aon.ui.menu.jsf.MenuEvent;
import com.code.aon.ui.planner.ControllerUtil;
import com.code.aon.ui.planner.EventManager;
import com.code.aon.ui.planner.PlannerController;
import com.code.aon.ui.planner.ReassignManager;

/**
 * Esta clase se deberia implementar en la aplicacón que este usando la Agenda, 
 * de tal manera que provea al <code>PlannerController</code> de un modelo 
 * a partir de un <code>RegistryAttachment</code> u otro fichero adjunto del tipo "ics".
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 14-sep-2006
 * @since 1.0
 * TODO
 *
 */

public class Launcher extends BasicController {

    /** Se obtiene el Logger adecuado */
    private static final Logger LOGGER = Logger.getLogger(Launcher.class.getName());

    PlannerController planner;

    public void onStart(MenuEvent event) throws ManagerBeanException {
        if ( planner == null )
            planner = getPlannerController();
        try {
            planner.setScheduleModel( getCalendarScheduleModel() );
        } catch (CalendarException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Inicializa la visualización de Eventos, VACATION, DAY_OFF, PUBLIC_HOLIDAY, CONFERENCE.
     * 
     * @return
     */
    public void onFreeTimeEvents(ActionEvent event) {
		planner.setEvents( new ListDataModel( new ArrayList<ScheduleEntry>() ) );
        int id = ( (CalendarScheduleModel)planner.getScheduleModel() ).getCalendar().getPrimaryKey().intValue();
        EventManager em = ControllerUtil.getEventManager();
        em.initialize( PlannerUtil.createHolidayEvent(id, new Date()), planner, "Creacion del Evento" );
    }

    /**
     * Inicializa la visualización de Eventos, APPOINTMENT.
     * 
     * @return
     */
    public void onAppointmentEvents(ActionEvent event) {
//        planner.loadAppointments();
        ReassignManager rm = ControllerUtil.getReassignManager();
        rm.initialize( planner.getEvents(), planner, new Owner() );
    }

    /**
     * Devuelve el planificador del recurso <code>Registry</code>.
     * 
     * @param registry
     * @return
     * @throws CalendarException 
     */
    private CalendarScheduleModel getCalendarScheduleModel() throws CalendarException {
// TODO. Lo correcto es buscar el ics desde un blob de la base de datos de cada aplicación,
//       en este caso como no hay B.D. se busca el ics indicado en el fichero de configuracion 
//       del calendario.
//      RegistryAttachment ics = (RegistryAttachment)getTo();
//      AonCalendar calendar = CalendarManager.getCalendar( ics.getData() );
//      calendar.setPrimaryKey( ics.getId() );
//      calendar.setDescription(ics.getDescription());
//      calendar.setOwner(ics.getRegistry().getId());
        String CONFIG_FILE = "com/code/aon/test/calendar/calendar-config.xml";
        IResourceLoader resourceLoad = new IResourceLoader() {
            public InputStream getResourceAsStream(String resource) {
                return PlannerController.class.getClassLoader().getResourceAsStream(resource);
            }
        };
        CalendarManager calendarManager = CalendarManager.getInstance( resourceLoad, CONFIG_FILE );
        AonCalendar calendar = calendarManager.getCalendar( 2006 );
        calendar.setPrimaryKey( 2006 );
        calendar.setDescription("Agenda del 2006");
//        calendar.setOwnerId( 2006 );
        CalendarScheduleModel csm = new CalendarScheduleModel(calendar);
//  TODO Añadir mas filtros. Cargarlos desde un XML configurable, por ejemplo.
        csm.getFilter().add(EventCategory.APPOINTMENT);
        return csm;
    }

    /**
     * Devuelve el controlador encargado del manejo de la agenda.
     *  
     * @return
     */
    private PlannerController getPlannerController() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ValueBinding vb = ctx.getApplication().createValueBinding( "#{planner}" );
        return (PlannerController) vb.getValue(ctx);
    }

    /**
     * Propietario del calendario. Esta clase la implementa los propietarios del calendario 
     * segun la aplicación.
     * 
     * @author Consulting & Development. Iñaki Ayerbe - 14-sep-2006
     * @since 1.0
     *
     */
    class Owner implements IOwner {

        public Integer getId() {
            return 2006;
        }

//        public RegistryAttachment getCalendar() {
        public AonCalendar getCalendar() {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
}
