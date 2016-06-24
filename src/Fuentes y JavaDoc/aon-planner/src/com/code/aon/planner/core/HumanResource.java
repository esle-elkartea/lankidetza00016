/*
 * Created on 28-nov-2005
 *
 */
package com.code.aon.planner.core;

import java.util.Date;

import com.code.aon.common.ITransferObject;
import com.code.aon.planner.IResource;

//import com.code.aon.registry.Registry;

/**
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 18-nov-2005
 * @since 1.0 
 * 
 * @hibernate.class 
 *      table "resource"
 *      
 */
public class HumanResource implements IResource, ITransferObject {

    /** Identificador único. */
    private Integer id;
    /** Identificador de la Tarea. */
    private Task task;
//    /** Identificador de la Persona encargada de realizar la tarea. */
//    private Registry registry;
    /** Fecha de entrada del recurso. */
    private Date entryDate;

    /**
     * Devuelve el identificador único.
     * 
     * @return Devuelve el identificador único.
     * 
     * @hibernate.id
     *      generator-class = "native"
     */
    public Integer getId() {
        return id;
    }

    /**
     * Asigna el identificador único.
     * 
     * @param primaryKey
     *            El identificador único.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return Returns the task.
     * @hibernate.many-to-one
     *      column = "task"
     *      outer-join = "true"
     */
    public Task getTask() {
        return task;
    }
    /**
     * @param task The task to set.
     */
    public void setTask(Task task) {
        this.task = task;
    }

//    /**
//     * @return Returns the registry.
//     * @hibernate.many-to-one
//     *      column = "registry"
//     *      outer-join = "true"
//     */
//    public Registry getRegistry() {
//        return registry;
//    }
//    /**
//     * @param registry The registry to set.
//     */
//    public void setRegistry(Registry registry) {
//        this.registry = registry;
//    }

    /**
     * @return Returns the entryDate.
     * @hibernate.property
     *      not-null "true"
     */
    public Date getEntryDate() {
        return entryDate;
    }
    /**
     * @param entryDate The entryDate to set.
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    
}
