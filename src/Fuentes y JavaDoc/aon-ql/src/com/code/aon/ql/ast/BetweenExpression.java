package com.code.aon.ql.ast;

/**
 * Ternary expression for evaluatind an <code>Expression</code> against a range of <code>Expression</code>s.
 * 
 * @author Consulting & Development. Ra�l Trepiana - 20-nov-2003
 * @since 1.0
 *  
 */
public interface BetweenExpression extends Expression {

    /**
     * Returns the <code>Expression</code> that is desired to evaluate. 
     * @return La expresi�n que se desea evaluar.
     */
    Expression getLeftExpression();

    /**
     * Returns the minor <code>Expression</code> of the range. 
     * @return The minor <code>Expression</code> of the range.
     */
    Expression getMinorExpression();

    /**
     * Returns the major <code>Expression</code> of the range. 
     * @return The major <code>Expression</code> of the range.
     */
    Expression getMajorExpression();

}