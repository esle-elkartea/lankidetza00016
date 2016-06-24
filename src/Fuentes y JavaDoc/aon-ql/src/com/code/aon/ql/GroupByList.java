package com.code.aon.ql;

import java.util.ArrayList;
import java.util.List;

import com.code.aon.ql.ast.Criterion;
import com.code.aon.ql.ast.CriterionVisitor;
import com.code.aon.ql.ast.IdentExpression;

/**
 * Class for wrapping the expressions used in the group section.
 * 
 * @author Consulting & Development. Aimar Tellitu - 21-jul-2005
 * @since 1.0
 * 
 */
public class GroupByList implements Criterion {

	private List<IdentExpression> groups;

	/**
	 * Default Constructor.
	 */
	public GroupByList() {
		this.groups = new ArrayList<IdentExpression>();
	}

	/**
	 * Adds the given <code>IdentExpression</code> to the group list.
	 * 
	 * @param expression
	 *            The item to be added to the list.
	 */
	public void addGroup(IdentExpression expression) {
		if (!this.groups.contains(expression)) {
			this.groups.add(expression);
		}
	}

	/**
	 * Returns the group list. An empty list if no item were added.
	 * 
	 * @return The list of items.
	 */
	public List<IdentExpression> getGroups() {
		return groups;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.code.aon.ql.ast.Criterion#accept(com.code.aon.ql.ast.CriterionVisitor)
	 */
	public void accept(CriterionVisitor visitor) {
		visitor.visitGroupByList(this);
	}

}
