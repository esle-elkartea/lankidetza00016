package com.code.aon.ql.ast;

import com.code.aon.ql.Criteria;
import com.code.aon.ql.GroupByList;
import com.code.aon.ql.Order;
import com.code.aon.ql.OrderByList;


/**
 * Expressions visitor.
 * 
 * @author Consulting & Development. Ra�l Trepiana - 19-nov-2003
 * @since 1.0
 *  
 */
public interface CriterionVisitor {
	
	/**
	 * Method to be invoked from a <code>Criteria</code>. 
	 * @param criteria Criteria visited.
	 */
	void visitCriteria( Criteria criteria );
	/**
	 * Method to be invoked from a <code>OrderByList</code>. 
	 * @param orderByList OrderByList visited.
	 */
	void visitOrderByList( OrderByList orderByList );
	/**
	 * Method to be invoked from a <code>Order</code>. 
	 * @param order Order visited.
	 */
	void visitOrder( Order order );
	/**
	 * Method to be invoked from a <code>GroupByList</code>. 
	 * @param groupByList GroupByList visited.
	 */
	void visitGroupByList( GroupByList groupByList );
	/**
	 * Method to be invoked from a <code>LogicalOrExpression</code>. 
	 * @param expression Expresi�n visited.
	 */
	void visitLogicalOrExpression( LogicalOrExpression expression );
	/**
	 * Method to be invoked from a <code>LogicalAndExpression</code>. 
	 * @param expression Expresi�n visited.
	 */
	void visitLogicalAndExpression( LogicalAndExpression expression );

	/**
	 * Method to be invoked from a <code>NullExpression</code>. 
	 * @param expression Expresi�n visited.
	 */
	void visitNullExpression( NullExpression expression );
	/**
	 * Method to be invoked from a <code>NotNullExpression</code>. 
	 * @param expression Expresi�n visited.
	 */
	void visitNotNullExpression( NotNullExpression  expression );
	/**
	 * Method to be invoked from a <code>IdentExpression</code>. 
	 * @param expression Expresi�n visited.
	 */
	void visitIdentExpression( IdentExpression expression );
	/**
	 * Method to be invoked from a <code>ConstantExpression</code>. 
	 * @param expression Expresi�n visited.
	 */
	void visitConstantExpression( ConstantExpression expression );
	
	/**
	 * Method to be invoked from a <code>BetweenExpression</code>. 
	 * @param expression Expresi�n visited.
	 */
	void visitBetweenExpression( BetweenExpression expression );

	/**
	 * Method to be invoked from a <code>RelationalExpression</code>. 
	 * @param expression Expresi�n visited.
	 */
	void visitRelationalExpression( RelationalExpression expression );

}
