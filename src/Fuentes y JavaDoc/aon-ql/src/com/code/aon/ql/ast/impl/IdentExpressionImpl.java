package com.code.aon.ql.ast.impl;

import com.code.aon.ql.ast.CriterionVisitor;
import com.code.aon.ql.ast.IdentExpression;

/**
 * Unary expresion that contains an identifier.
 * 
 * @author Consulting & Development. Ra�l Trepiana - 20-nov-2003
 * @since 1.0
 *  
 */
public class IdentExpressionImpl implements IdentExpression {

    /**
     * The identifier.
     */
    private String name;

    /**
     * Constructor for the given identifier.
     * 
     * @param name 
     *            The identifier.
     */
    public IdentExpressionImpl(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see com.code.aon.ql.ast.IdentExpression#getName()
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.code.aon.ql.ast.Criterion#accept(com.code.aon.ql.ast.CriterionVisitor)
     */
    public void accept(CriterionVisitor visitor) {
        visitor.visitIdentExpression(this);
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof IdentExpression ) {
			IdentExpression o = (IdentExpression) obj;
			return name.equals(o.getName());
		}
		return false;
	}

}