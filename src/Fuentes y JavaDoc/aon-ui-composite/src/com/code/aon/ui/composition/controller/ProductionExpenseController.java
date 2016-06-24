package com.code.aon.ui.composition.controller;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

import com.code.aon.common.ManagerBeanException;
import com.code.aon.composition.ProductionExpense;
import com.code.aon.ui.composition.util.ProductionPriceProvider;
import com.code.aon.ui.form.LinesController;

public class ProductionExpenseController extends LinesController {

    /**
     * Object that provides the different prices that take part in the product production.
     */
    private ProductionPriceProvider provider;

    /**
     * Returns the price provider.
     * 
     * @return ProductionPriceProvider
     */
    private ProductionPriceProvider getPriceProvider() {
        if (provider == null) {
            provider = new ProductionPriceProvider();
        }
        return provider;
    }

    /**
     * Returns the total price by expense.
     * 
     * @return double
     */
    public double getListTotal() throws ManagerBeanException {
        return (this.getModel().isRowAvailable()) ? getPriceProvider().getListTotal((ProductionExpense)this.getModel().getRowData()) : 0;
    }

    /**
     * On reset.
     * 
     * @param event
     * @see com.code.aon.ui.form.IController#onReset(javax.faces.event.ActionEvent)
     */
    @Override
    public void onReset(ActionEvent event) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ValueBinding vb = ctx.getApplication().createValueBinding("#{productionDetail}");
        ProductionDetailController detailController = (ProductionDetailController)vb.getValue(ctx);
        detailController.onCancel(event);

        super.onReset(event);
    }

    /**
     * On select.
     * 
     * @param event
     * @see com.code.aon.ui.form.IController#onSelect(javax.faces.event.ActionEvent)
     */
    @Override
    public void onSelect(ActionEvent event) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ValueBinding vb = ctx.getApplication().createValueBinding("#{productionDetail}");
        ProductionDetailController detailController = (ProductionDetailController)vb.getValue(ctx);
        detailController.onCancel(event);

        super.onSelect(event);
    }

}
