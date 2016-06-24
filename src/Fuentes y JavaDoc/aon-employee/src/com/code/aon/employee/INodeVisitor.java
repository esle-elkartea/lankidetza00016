package com.code.aon.employee;

public interface INodeVisitor {

    /**
     * Visit WorkPlace node.
     * 
     * @param workPlace
     */
    void visitWorkPlace(WorkPlace workPlace);

    /**
     * Visit WorkActivity node.
     * 
     * @param activity
     */
    void visitWorkActivity(WorkActivity activity);

    /**
     * Visit Resource node.
     * 
     * @param resource
     */
    void visitResource(Resource resource);

}