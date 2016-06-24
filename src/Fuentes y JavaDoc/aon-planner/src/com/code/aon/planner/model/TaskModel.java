/*
 * Created on 02-dic-2005
 *
 */
package com.code.aon.planner.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.code.aon.common.IManagerBean;
import com.code.aon.common.ITransferObject;
import com.code.aon.common.ManagerBeanException;

import com.code.aon.planner.ITask;
import com.code.aon.planner.core.HumanResource;
import com.code.aon.planner.core.PlannerManagerBeanFactory;
import com.code.aon.planner.core.Task;
import com.code.aon.planner.core.dao.IPlannerAlias;

import com.code.aon.ql.Criteria;
import com.code.aon.ql.util.ExpressionException;

public class TaskModel {

    /** Se obtiene el Logger adecuado */
    private static final Logger LOGGER = Logger.getLogger(TaskModel.class.getName());

    /** Tareas a realizar y creadas por el recurso seleccionado */
    private DataModel model;
    /** Indica el recurso seleccionado */
    private String resource;
    /** Indica el índice actual en la lista de tareas */
    private int currentIndex = -1;
    /** Indica el modo en el que se encuentra el Modelo. Edición ó Nuevo */
    private boolean isNew = true;

    /**
     * 
     * @param resource
     * @throws ManagerBeanException 
     * @throws ExpressionException 
     */
    public TaskModel(String resource) throws ExpressionException, ManagerBeanException {
        this.resource = resource;
        loadResourceTasks();
    }

    /**
     * @return Returns the taskModel.
     */
    public DataModel getModel() {
        return model;
    }

    /**
     * @param taskModel The taskModel to set.
     */
    public void setModel(DataModel model) {
        this.model = model;
    }

    /**
     * @return Returns the resource.
     */
    public String getResource() {
        return resource;
    }

    /**
     * @param resource The resource to set.
     */
    public void setResource(String resource) {
        this.resource = resource;
    }

    /**
     * @return Returns the currentIndex.
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * @param currentIndex The currentIndex to set.
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    /**
     * Informa acerca del modo en el que se encuentra el Bean. true si está en
     * modo Nuevo, false en caso contrario.
     * 
     * @return boolean El modo en el que se encuentra el Bean.
     */
    public boolean isNew() {
        return isNew;
    }

    /**
     * Asigna el modo, true si está en modo Nuevo, false en caso contrario.
     * 
     * @param isNew
     */
    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    /**
     * Devuelve la tarea seleccionada.
     * 
     * @return
     */
    public ITask getRowData() {
        this.model.setRowIndex(this.currentIndex);
        return (ITask)this.model.getRowData();
    }

    /**
     * Añade o modifica la tarea.
     *  
     * @param e the action event
     */
    public ITask accept(ITask task) throws ExpressionException, ManagerBeanException {
        ITask _task;
        if (!isNew()) {
            _task = (ITask)update(task);
            List list = (List)this.model.getWrappedData();
            list.remove(currentIndex);
            list.add(currentIndex, task);
        } else {
            _task = (ITask)add(task);
            List list = (List)this.model.getWrappedData();
            list.add(task);
            setCurrentIndex(list.size() -1);
        }
        return _task;
    }

    /**
     * Elimina el <code>ITransferObject</code> asignado.
     * 
     * @param task
     * @throws ManagerBeanException Si se produce algún error.
     */
    public ITask remove(ITask task) throws ManagerBeanException {
        LOGGER.fine("Removing Id:[" + task.getId() + "]" ); //$NON-NLS-1$ //$NON-NLS-2$
        IManagerBean bean = PlannerManagerBeanFactory.getTaskManagerBean();
        bean.remove(task);
        List list = (List)this.model.getWrappedData();
        list.remove(currentIndex);
        return task;
    }

    /**
     * Recarga las tareas asociadas al recurso.
     *
     */
    public void load(ITask task) throws ExpressionException, ManagerBeanException {
//        List list = (List)this.model.getWrappedData();
//        if (!isNew) {
//            list.remove(currentIndex);
//            list.add(task);
//        } else {
//            list.add(task);
//            setCurrentIndex(list.size() -1);
//        }
    }

    /**
     * Carga las tareas asignadas al recurso seleccionado <code>Registry</code>.
     * 
     * @return
     * @throws ExpressionException
     * @throws ManagerBeanException
     */
    private void loadResourceTasks() throws ExpressionException, ManagerBeanException {
// Tareas a realizar por el recurso.
        IManagerBean bean = PlannerManagerBeanFactory.getHumanResourceManagerBean();
        Criteria criteria = new Criteria();
        criteria.addExpression(bean.getFieldName(IPlannerAlias.HUMAN_RESOURCE_REGISTRY), this.resource);
        List tasks = new LinkedList();
        Iterator iter = bean.getList(criteria).iterator();
        while (iter.hasNext()) {
            HumanResource element = (HumanResource) iter.next();
            tasks.add(element.getTask());
        }
        if (this.model == null) {
            this.model = new ListDataModel(tasks);
        } else {
            this.model.setWrappedData(tasks);
        }
// Tareas creadas por el recurso.
        bean = PlannerManagerBeanFactory.getTaskManagerBean();
        criteria = new Criteria();
        criteria.addExpression(bean.getFieldName(IPlannerAlias.TASK_OWNER), this.resource);
        tasks = new LinkedList();
        iter = bean.getList(criteria).iterator();
        while (iter.hasNext()) {
            Task element = (Task) iter.next();
            tasks.add(element);
        }
        List list = (List)this.model.getWrappedData();
        list.addAll(list.size(), tasks);
    }

    /**
     * Añade el <code>ITransferObject</code> asignado.
     * 
     * @param task
     * @throws ManagerBeanException Si se produce algún error.
     */
    private ITransferObject add(ITask task) throws ManagerBeanException {
        LOGGER.fine("Adding Id:[" + task.getId() + "]" ); //$NON-NLS-1$ //$NON-NLS-2$
        IManagerBean bean = PlannerManagerBeanFactory.getTaskManagerBean();
        return bean.insert(task);
    }

    /**
     * Modifica el <code>ITransferObject</code> asignado.
     * 
     * @param task
     * @throws ManagerBeanException
     */
    private ITransferObject update(ITask task) throws ManagerBeanException {
        LOGGER.fine("Setting Id:[" + task.getId() + "]" ); //$NON-NLS-1$ //$NON-NLS-2$
        IManagerBean bean = PlannerManagerBeanFactory.getTaskManagerBean();
        bean.update(task);
        return task;
    }

}
