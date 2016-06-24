package com.code.aon.ql.ast.impl;

import com.code.aon.ql.ast.Expression;
import com.code.aon.ql.ast.CriterionVisitor;
import com.code.aon.ql.ast.RelationalExpression;

/**
 * Binary expression that will be evaluated with a supported relational operator. 
 * @author Consulting & Development. Raúl Trepiana - 20-nov-2003
 * @since 1.0
 * 
 */
class RelationalExpressionImpl extends AbstractBinaryExpressionImpl implements RelationalExpression {

	/**
	 * Supported relational operator.
	 * 
	 */
	private int type;

	/**
	 * Constructor. indicado.
	 * 
	 * @param left
	 *            The left expression.
	 * @param rigth
	 *            The right expression.
	 * @param type
	 *            A supported relational operator.
	 * 
	 */
	public RelationalExpressionImpl(Expression left, Expression rigth, int type) {
		super(left, rigth);
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.code.aon.ql.ast.RelationalExpression#getType()
	 */
	public int getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.code.aon.ql.ast.Criterion#accept(com.code.aon.ql.ast.CriterionVisitor)
	 */
	public void accept(CriterionVisitor visitor) {
		visitor.visitRelationalExpression(this);
	}

}