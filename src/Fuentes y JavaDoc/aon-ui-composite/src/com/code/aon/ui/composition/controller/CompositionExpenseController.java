package com.code.aon.ui.composition.controller;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

import com.code.aon.common.ManagerBeanException;
import com.code.aon.composition.CompositionExpense;
import com.code.aon.ui.composition.util.CompositionPriceProvider;
import com.code.aon.ui.form.LinesController;

/**
 * Controller for Product Composition Expenses.
 * 
 * @author Consulting & Development.
 */
public class CompositionExpenseController extends LinesController {

    /**
     * Object that provides the different prices that take part in the product composition.
     */
    private CompositionPriceProvider provider;

    /**
     * Returns the price provider.
     * 
     * @return CompositionPriceProvider
     */
    private CompositionPriceProvider getPriceProvider() {
        if (provider == null) {
            provider = new CompositionPriceProvider();
        }
        return provider;
    }

    /**
     * Returns the total price by expense.
     * 
     * @return double
     */
    public double getListTotal() throws ManagerBeanException {
        return (this.getModel().isRowAvailable()) ? getPriceProvider().getListTotal((CompositionExpense)this.getModel().getRowData()) : 0;
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
        ValueBinding vb = ctx.getApplication().createValueBinding("#{compositionDetail}");
        CompositionDetailController detailController = (CompositionDetailController)vb.getValue(ctx);
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
        ValueBinding vb = ctx.getApplication().createValueBinding("#{compositionDetail}");
        CompositionDetailController detailController = (CompositionDetailController)vb.getValue(ctx);
        detailController.onCancel(event);

        super.onSelect(event);
    }

    /**
     * On accept.
     * 
     * @param event
     * @see com.code.aon.ui.form.IController#onAccept(javax.faces.event.ActionEvent)
     */
    @Override
    public void onAccept(ActionEvent event) {
        boolean wasNew = this.isNew();
        super.onAccept(event);
        if(wasNew){
            this.onReset(null);
        }
    }

}
