package com.code.aon.employee;

import java.io.Serializable;

/**
 * 
 * @author Consulting & Development. Iñaki Ayerbe - 30-jan-2007
 * @since 1.0
 *  
 */
public interface INode extends Serializable {

    /**
     * Returns Node identifier.
     *  
     * @return Integer
     */
    Integer getId();

    /**
     * Accept visitor node and execute its visitant method.
     * 
     * @param visitor INodeVisitor
     */
    void accept(INodeVisitor visitor);
}