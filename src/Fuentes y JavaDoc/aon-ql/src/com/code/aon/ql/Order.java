package com.code.aon.ql;

import com.code.aon.ql.ast.Criterion;
import com.code.aon.ql.ast.CriterionVisitor;
import com.code.aon.ql.ast.IdentExpression;

/**
 * Class for wrapping an <code>com.code.aon.ql.ast.IdentExpression</code> expression.
 * 
 * @author Consulting & Development. Aimar Tellitu - 21-jul-2005
 * @since 1.0
 *  
 */
public class Order implements Criterion {

	private IdentExpression expression;
	
	private boolean ascending;
	
	/**
	 * @param expression
	 * @param ascending
	 */
	public Order(IdentExpression expression, boolean ascending) {
		this.expression = expression;
		this.ascending = ascending;
	}
	
	/**
	 * @return Returns the ascending.
	 */
	public boolean isAscending() {
		return ascending;
	}

	/**
	 * @return Returns the expression.
	 */
	public IdentExpression getExpression() {
		return expression;
	}

    /**
     * Implementacion del patr�n visitante. En este m�todo se deber� llamar al
     * m�todo de <code>CriterionVisitor<code> correspondiente para este tipo 
     * de expresi�n. En este caso <code>visitOrder<code>.
     *  
     * @param visitor Visitante que debe tener un m�todo acorde para este tipo de expresi�n.
     * @see com.code.aon.ql.ast.Criterion#accept(CriterionVisitor)
     */
    public void accept(CriterionVisitor visitor) {
        visitor.visitOrder(this);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof Order ) {
			Order order = (Order) obj;
			return ( ascending == order.ascending) && expression.equals(order.expression);
		}
		return false;
	}

}
