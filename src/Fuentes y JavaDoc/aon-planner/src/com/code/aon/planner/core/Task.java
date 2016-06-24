/*
 * Created on 25-nov-2005
 *
 */
package com.code.aon.planner.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.code.aon.planner.INodeVisitor;
import com.code.aon.planner.ITask;
import com.code.aon.planner.enumeration.Priority;
import com.code.aon.planner.enumeration.TaskStatus;

//import com.code.aon.registry.Registry;

/**
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 25-nov-2005
 * @since 1.0
 * TODO
 *
 * @hibernate.class 
 *      table "task"
 */
public class Task implements ITask {

    /** Identificador de la Tarea*/
    private String id;
    /** Título de la Tarea*/
    private String title;
    /** Fecha de comienzo de la Tarea*/
    private Date startDate;
    /** Fecha de fin de la Tarea*/
    private Date dueDate;
    /** Prioridad de la Tarea*/
    private Priority priority;
    /** Estado de la Tarea*/
    private TaskStatus status;
    /** Porcentaje de realización de la Tarea*/
    private int percent;
//    /** Propietario(Creador) de la Tarea*/
//    private Registry owner;
    /** Conjunto de recursos asociados a la Tarea */
    private Set resources = new HashSet();
    /** Fecha en la cual realizar los avisos*/
    private Date alarmDate;

    /** (non-Javadoc)
     * 
     * @hibernate.id
     *      generator-class = "native"
     * @see com.code.aon.planner.ITask#getId()
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /** (non-Javadoc)
     * 
     * @hibernate.property
     * @see com.code.aon.planner.ITask#getTitle()
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** (non-Javadoc)
     * 
     * @hibernate.property
     * @see com.code.aon.planner.ITask#getStartDate()
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /** (non-Javadoc)
     * 
     * @hibernate.property
     * @see com.code.aon.planner.ITask#getDueDate()
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate The dueDate to set.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /** (non-Javadoc)
     * 
     * @hibernate.property
     *      type = "com.code.aon.planner.enumeration.hibernate.PriorityUserType"
     *      not-null = "true" 
     * @see com.code.aon.planner.ITask#getPriority()
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority The priority to set.
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /** (non-Javadoc)
     * 
     * @hibernate.property
     *      type = "com.code.aon.planner.enumeration.hibernate.TaskStatusUserType"
     *      not-null = "true" 
     * @see com.code.aon.planner.ITask#getStatus()
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /** (non-Javadoc)
     * 
     * @hibernate.property
     * @see com.code.aon.planner.ITask#getPercent()
     */
    public int getPercent() {
        return percent;
    }

    /**
     * @param percent The percent to set.
     */
    public void setPercent(int percent) {
        this.percent = percent;
    }

//    /** (non-Javadoc)
//     * 
//     * @hibernate.many-to-one
//     *      column = "owner"
//     *      outer-join = "true"
//     * @see com.code.aon.planner.ITask#getOwner()
//     */
//    public Registry getOwner() {
//        return owner;
//    }
//
//    /**
//     * @param owner The owner to set.
//     */
//    public void setOwner(Registry owner) {
//        this.owner = owner;
//    }

    /** (non-Javadoc)
     *
     * @hibernate.set
     *      cascade = "delete"
     * @hibernate.collection-key
     *      column = "task"
     * @hibernate.collection-one-to-many
     *      class = "com.code.aon.planner.core.HumanResource"
     * @see com.code.aon.planner.ITask#getResources()
     */
    public Set getResources() {
        return this.resources;
    }

    /**
     * Method setResources
     * 
     * @param resources Set
     */
    public void setResources( Set resources ) {
        this.resources = resources;
    }

//    /**
//     * Method addResource
//     * 
//     * @param resource XRegistry
//     */
//    public void addResource(Registry registry) {
//        this.resources.add( registry );
//    }

    /** (non-Javadoc)
     * 
     * @hibernate.property
     * @see com.code.aon.planner.ITask#getAlarmDate()
     */
    public Date getAlarmDate() {
        return alarmDate;
    }

    /**
     * @param alarmDate The alarmDate to set.
     */
    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    /* (non-Javadoc)
     * @see com.code.aon.planner.ITask#visit(com.code.aon.planner.INodeVisitor)
     */
    public void visit(INodeVisitor visitor) {
        visitor.visitTask(this);
    }

    /* (non-Javadoc)
     * @see com.code.aon.planner.IAction#execute()
     */
    public void execute() {
        // TODO Auto-generated method stub

    }

}
