package com.code.aon.ql.ast.impl;

import com.code.aon.ql.ast.BinaryExpression;
import com.code.aon.ql.ast.Expression;

/**
 * Abstract class for support binary expressions.
 * 
 * @author Consulting & Development. Ra�l Trepiana - 20-nov-2003
 * @since 1.0
 *  
 */
abstract class AbstractBinaryExpressionImpl implements BinaryExpression {

    /**
     * The left expression.
     */
    private Expression left;

    /**
     * The right expression.
     */
    private Expression right;

    /**
     * Constructor.
     * 
     * @param left
     *            The left expression.
     * @param rigth
     *            The right expression.
     */
    public AbstractBinaryExpressionImpl(Expression left, Expression rigth) {
        this.left = left;
        this.right = rigth;
    }

    /* (non-Javadoc)
     * @see com.code.aon.ql.ast.BinaryExpression#getLeftExpression()
     */
    public Expression getLeftExpression() {
        return left;
    }

    /* (non-Javadoc)
     * @see com.code.aon.ql.ast.BinaryExpression#getRightExpression()
     */
    public Expression getRightExpression() {
        return right;
    }

}