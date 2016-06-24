package com.code.aon.ui.product.event;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.product.Item;
import com.code.aon.product.Product;
import com.code.aon.product.Tax;
import com.code.aon.product.dao.IProductAlias;
import com.code.aon.product.enumeration.ProductStatus;
import com.code.aon.ql.Criteria;
import com.code.aon.ui.form.event.ControllerAdapter;
import com.code.aon.ui.form.event.ControllerEvent;
import com.code.aon.ui.form.event.ControllerListenerException;
import com.code.aon.ui.product.controller.ItemController;
import com.code.aon.ui.product.controller.ProductCollectionsController;

/**
 * Listener added to the itemController.
 */
public class ItemVetoListener extends ControllerAdapter {
	
    /**
     * After bean created. Sets current item fields to default values
     * 
     * @param event the event
     * 
     * @throws ControllerListenerException the controller listener exception
     */
    @Override
    public void afterBeanCreated(ControllerEvent event) throws ControllerListenerException {
        double percentage = 0;
        FacesContext ctx = FacesContext.getCurrentInstance();
        ValueBinding vb = ctx.getApplication().createValueBinding("#{productCollections}");
        ProductCollectionsController collections = (ProductCollectionsController)vb.getValue(ctx);
        IManagerBean bean;
        try {
            List vats = collections.getVatTaxes();
            int vatId = Integer.parseInt((String)((SelectItem)vats.get(0)).getValue());

            bean = BeanManager.getManagerBean(Tax.class);
            Criteria criteria = new Criteria();
            criteria.addEqualExpression(bean.getFieldName(IProductAlias.TAX_ID), new Integer(vatId));
            percentage = ((Tax)bean.getList(criteria).get(0)).getPercentage();
        } catch (ManagerBeanException e) {
            throw new ControllerListenerException(e.getMessage(), e);
        }
        ((Item)event.getController().getTo()).getProduct().getVat().setPercentage(percentage);

        ((Item)event.getController().getTo()).setStatus(ProductStatus.ACTIVE);
        ((Item)event.getController().getTo()).getProduct().setInventoriable(true);
        ((Item)event.getController().getTo()).getProduct().setComposition(false);
    }

	/**
	 * Before bean added. Adds the product related with the current item
	 * 
	 * @param event the event
	 * 
	 * @throws ControllerListenerException the controller listener exception
	 */
	@Override
	public void beforeBeanAdded(ControllerEvent event) throws ControllerListenerException {
		if(event.getController().isNew()){
			try {
				IManagerBean productBean = BeanManager.getManagerBean(Product.class);
				Item item = (Item)event.getController().getTo();
                item.getProduct().setBrand(null);
                item.getProduct().setStatus(item.getStatus());
                Product prod = (Product)productBean.insert(item.getProduct());
				item.setProduct(prod);
			} catch (ManagerBeanException e) {
                throw new ControllerListenerException(e.getMessage(), e);
			}
		}
	}

    /**
     * Before model initialized. Adds the condition composition == false to the criteria
     * 
     * @param event the event
     * 
     * @throws ControllerListenerException the controller listener exception
     */
    @Override
    public void beforeModelInitialized(ControllerEvent event) throws ControllerListenerException {
        ItemController controller = (ItemController)event.getController();
        Criteria criteria;
        try {
            criteria = controller.getCriteria();
            criteria.addEqualExpression(controller.getManagerBean().getFieldName(IProductAlias.ITEM_PRODUCT_COMPOSITION), new Boolean(false));
            criteria.addOrder(controller.getManagerBean().getFieldName(IProductAlias.ITEM_PRODUCT_CATEGORY_ID));
            criteria.addOrder(controller.getManagerBean().getFieldName(IProductAlias.ITEM_PRODUCT_CODE));
            controller.setCriteria(criteria);
        } catch (ManagerBeanException e) {
            throw new ControllerListenerException(e.getMessage(), e);
        }
    }
}