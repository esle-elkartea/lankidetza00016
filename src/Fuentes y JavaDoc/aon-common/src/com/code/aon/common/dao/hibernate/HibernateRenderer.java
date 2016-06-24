package com.code.aon.common.dao.hibernate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.IdentifierType;
import org.hibernate.type.NullableType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.ql.Criteria;
import com.code.aon.ql.GroupByList;
import com.code.aon.ql.Order;
import com.code.aon.ql.OrderByList;
import com.code.aon.ql.ast.BetweenExpression;
import com.code.aon.ql.ast.ConstantExpression;
import com.code.aon.ql.ast.CriterionVisitor;
import com.code.aon.ql.ast.Expression;
import com.code.aon.ql.ast.IdentExpression;
import com.code.aon.ql.ast.LogicalAndExpression;
import com.code.aon.ql.ast.LogicalOrExpression;
import com.code.aon.ql.ast.NotNullExpression;
import com.code.aon.ql.ast.NullExpression;
import com.code.aon.ql.ast.RelationalExpression;

/**
 * Expression visitor that obtains an SQL WHERE clause.
 * 
 * @author Consulting & Development. Aimar Tellitu - 17-may-2005
 * @since 1.0
 * 
 */

public class HibernateRenderer implements CriterionVisitor {

	/**
	 * Obtain a suitable <code>Logger</code>.
	 */
	private static final Logger LOGGER = Logger.getLogger(HibernateRenderer.class.getName());

	/**
	 * Field daoEntry
	 */
	private DAOConstantsEntry daoEntry;

	/**
	 * Constructor for HibernateRenderer
	 * 
	 * @param criteria
	 * @param daoEntry
	 */
	public HibernateRenderer(org.hibernate.Criteria criteria, DAOConstantsEntry daoEntry) {
		this.criteria = criteria;
		this.daoEntry = daoEntry;
		this.classMetaData = HibernateUtil.getSessionFactory().getClassMetadata(daoEntry.getPojo());
		this.aliasSet = new HashSet<String>();
	}

	/**
	 * Field classMetaData
	 */
	private ClassMetadata classMetaData;

	/**
	 * Field aliasCriterias
	 */
	private Set<String> aliasSet;

	/**
	 * Field criterion
	 */
	private Criterion criterion;

	/**
	 * Field criteria
	 */
	private org.hibernate.Criteria criteria;

	/**
	 * Store literal constants value of the expression.
	 */
	private Object literal;

	/**
	 * Store identifier name of the expression.
	 */
	private String identifier;

	/**
	 * Store identifier full name of the expression.
	 */
	private String fullIdentifier;

	/**
	 * Store exceptions in Criterion constructor.
	 */
	private Exception exception;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitCriteria(com.code.aon.ql.Criteria)
	 */
	public void visitCriteria(Criteria criteria) {
		if (criteria.getExpression() != null) {
			criteria.getExpression().accept(this);
		}
		if (criteria.getGroupByList() != null) {
			criteria.getGroupByList().accept(this);
		}
		if (criteria.getOrderByList() != null) {
			criteria.getOrderByList().accept(this);
		}
		if (this.criterion != null) {
			this.criteria.add(this.criterion);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitGroupByList(com.code.aon.ql.GroupByList)
	 */
	public void visitGroupByList(GroupByList groupByList) {
		ProjectionList projectionList = Projections.projectionList();
		Iterator<IdentExpression> i = groupByList.getGroups().iterator();
		while (i.hasNext()) {
			Expression expression = i.next();
			expression.accept(this);
			String propertyId = this.identifier;
			projectionList.add(Projections.groupProperty(propertyId));
		}
		this.criteria.setProjection(projectionList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitOrderByList(com.code.aon.ql.OrderByList)
	 */
	public void visitOrderByList(OrderByList orderByList) {
		Iterator<Order> i = orderByList.getOrders().iterator();
		while (i.hasNext()) {
			Order order = i.next();
			order.accept(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitOrder(com.code.aon.ql.Order)
	 */
	public void visitOrder(Order order) {
		order.getExpression().accept(this);
		String propertyId = this.identifier;

		if (order.isAscending()) {
			this.criteria.addOrder(org.hibernate.criterion.Order.asc(propertyId));
		} else {
			this.criteria.addOrder(org.hibernate.criterion.Order.desc(propertyId));
		}
	}

	/**
	 * This method is called by a <code>LogicalOrExpression</code>. Concat
	 * "or" operator expressions.
	 * 
	 * @param expression
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitLogicalOrExpression(LogicalOrExpression)
	 */
	public void visitLogicalOrExpression(LogicalOrExpression expression) {
		expression.getLeftExpression().accept(this);
		Criterion left = this.criterion;
		expression.getRightExpression().accept(this);
		Criterion right = this.criterion;
		this.criterion = Restrictions.or(left, right);
	}

	/**
	 * This method is called by a <code>LogicalAndExpression</code>. Concat
	 * "and" operator expressions.
	 * 
	 * @param expression
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitLogicalAndExpression(LogicalAndExpression)
	 */
	public void visitLogicalAndExpression(LogicalAndExpression expression) {
		expression.getLeftExpression().accept(this);
		Criterion left = this.criterion;
		expression.getRightExpression().accept(this);
		Criterion right = this.criterion;
		this.criterion = Restrictions.and(left, right);
	}

	/**
	 * This method is called by a <code>NullExpression</code>. Concat "is
	 * null" operator expressions.
	 * 
	 * @param expression
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitNullExpression(NullExpression)
	 */
	public void visitNullExpression(NullExpression expression) {
		expression.getExpression().accept(this);
		this.criterion = Restrictions.isNull(this.identifier);
	}

	/**
	 * This method is called by a <code>NotNullExpression</code>. Concat "is
	 * not null" operator expressions.
	 * 
	 * @param expression
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitNotNullExpression(NotNullExpression)
	 */
	public void visitNotNullExpression(NotNullExpression expression) {
		expression.getExpression().accept(this);
		this.criterion = Restrictions.isNotNull(this.identifier);
	}

	/**
	 * This method is called by a <code>IdentExpression</code>. Print
	 * identifier name.
	 * 
	 * @param expression
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitIdentExpression(IdentExpression)
	 */
	public void visitIdentExpression(IdentExpression expression) {
		this.fullIdentifier = expression.getName();
		int pos = this.fullIdentifier.indexOf('.');
		String id = this.fullIdentifier.substring(pos + 1);
		pos = id.lastIndexOf('.');
		if (pos != -1) {
			String property = id.substring(0, pos);
			if (! HibernateUtil.isComponsiteIdentifier(classMetaData, property) ) {
				String alias = property.replace('.', '_') + "_";
				if (!this.aliasSet.contains(property)) {
					this.criteria.createAlias(property, alias);
					this.aliasSet.add(property);
				}
				id = alias + id.substring(pos);
			}
		}
		this.identifier = id;
	}

	/**
	 * This method is called by a <code>ConstantExpression</code>. Print
	 * constant value quoted.
	 * 
	 * @param expression
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitConstantExpression(ConstantExpression)
	 */
	public void visitConstantExpression(ConstantExpression expression) {
		if (expression.isLiteral()) {
			String propertyName = this.fullIdentifier;
			this.literal = getTypedObject(propertyName, expression.getData().toString());
		} else {
			this.literal = expression.getData();
		}
	}

	/**
	 * This method is called by a <code>BetweenExpression</code>. Print
	 * "{expr} between {minor} and {major}"
	 * 
	 * @param expression
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitBetweenExpression(BetweenExpression)
	 */
	public void visitBetweenExpression(BetweenExpression expression) {
		expression.getLeftExpression().accept(this);
		String propertyId = this.identifier;
		expression.getMinorExpression().accept(this);
		Object minor = this.literal;
		expression.getMajorExpression().accept(this);
		Object major = this.literal;
		this.criterion = Restrictions.between(propertyId, minor, major);
	}

	/**
	 * This method is called by a <code>RelationalExpression</code>. Print 2
	 * expressions separated by its operator: <table border="1">
	 * <tr>
	 * <td>&lt;</td>
	 * <td><code>if ((expression.type & RelationalExpression.LT) > 0)</code></td>
	 * </tr>
	 * <tr>
	 * <td>&gt;</td>
	 * <td><code>if ((expression.type & RelationalExpression.GT) > 0)</code></td>
	 * </tr>
	 * <tr>
	 * <td>=</td>
	 * <td><code>if ((expression.type & RelationalExpression.EQ) > 0)</code></td>
	 * </tr>
	 * <tr>
	 * <td>&lt;&gt;</td>
	 * <td><code>if ((expression.type & RelationalExpression.NEQ) > 0)</code></td>
	 * </tr>
	 * <tr>
	 * <td>LIKE</td>
	 * <td><code>if ((expression.type & RelationalExpression.LIKE) > 0)</code></td>
	 * </tr>
	 * </table>
	 * 
	 * @param expression
	 * @see com.code.aon.ql.ast.CriterionVisitor#visitRelationalExpression(RelationalExpression)
	 */
	public void visitRelationalExpression(RelationalExpression expression) {
		expression.getLeftExpression().accept(this);
		String propertyId = this.identifier;

		expression.getRightExpression().accept(this);
		Object value = this.literal;

		int type = expression.getType();

		if ((type & RelationalExpression.LT) > 0) {
			this.criterion = Restrictions.lt(propertyId, value);
		} else if ((type & RelationalExpression.GT) > 0) {
			this.criterion = Restrictions.gt(propertyId, value);
		} else if ((type & RelationalExpression.EQ) > 0) {
			this.criterion = Restrictions.eq(propertyId, value);
		} else if ((type & RelationalExpression.NEQ) > 0) {
			this.criterion = Restrictions.ne(propertyId, value);
		} else if ((type & RelationalExpression.LIKE) > 0) {
			this.criterion = Restrictions.like(propertyId, value);
		} else if ((type & RelationalExpression.LTE) > 0) {
			this.criterion = Restrictions.le(propertyId, value);
		} else if ((type & RelationalExpression.GTE) > 0) {
			this.criterion = Restrictions.ge(propertyId, value);
		}
	}

	/**
	 * Return the exception if occurs during Criterion construction.
	 * 
	 * @return Exception
	 */
	public Exception getException() {
		return this.exception;
	}

	/**
	 * Return an object bound to the property name.
	 * 
	 * @param property
	 *            String Transfer Object property name.
	 * @param value
	 *            String literal constant.
	 * @return An object bound to the property name.
	 */
	private Object getTypedObject(String property, String value) {
		Object result = value;
		Type type = this.daoEntry.getPropertyTypes().get(property);
		if (type != null) {
			if (!(type instanceof StringType)) {
				value = value.trim();
			}
			try {
				if (type instanceof IdentifierType) {
					result = ((IdentifierType) type).stringToObject(value);
				} else if (type instanceof NullableType) {
					result = ((NullableType) type).fromStringValue(value);
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Error en el formato de la propiedad " + property + " con valor " + value
						+ " con tipo " + type, e);
				this.exception = new Exception("Error en el formato del campo de la busqueda", e);
			}
		} else {
			LOGGER.warning("La propiedad " + property + " no tiene asociado ningún tipo");
		}
		return result;
	}

}