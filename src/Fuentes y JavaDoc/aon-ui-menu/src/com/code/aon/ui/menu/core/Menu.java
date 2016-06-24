package com.code.aon.ui.menu.core;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.code.aon.ui.menu.IMenu;
import com.code.aon.ui.menu.IMenuItem;
import com.code.aon.ui.menu.IMenuVisitor;
import com.code.aon.ui.menu.MenuVisitorException;

/**
 * Default implementation of {@link com.code.aon.ui.menu.IMenu}.
 * 
 * @author Consulting & Development. Eugenio Castellano - 07-mar-2005
 * @since 1.0
 *  
 */
public class Menu extends MenuItem implements IMenu {

    /**
	 * Logger initialization.
     */
    private static Logger LOGGER = Logger.getLogger(Menu.class.getName());

    /**
     * Lista para contener los elementos.
     */
    private List<IMenuItem> list = new LinkedList<IMenuItem>();

    public void addItem(IMenuItem item) {
        list.add(item);
        item.setParent(this);
        LOGGER.finest("Item Added " + this + " [" + item + "]"); //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
    }

    public List<IMenuItem> getItems() {
        return this.list;
    }

    public void visit(IMenuVisitor visitor) throws MenuVisitorException {
        visitor.visitMenu(this);
    }

    public IMenuItem find(String id) {
        if (id.equals(getId())) {
            return this;
        } 
        for( IMenuItem item : getItems() ) {
            if (id.equals(item.getId())) {
                return item;
            } 
            if (item instanceof IMenu) {
                IMenu menu = (IMenu) item;
                IMenuItem mi = menu.find(id);
                if (mi != null) {
                    return mi;
                }
            }
        }
        return null;
    }

    public int getChildCount() {
        return list.size();
    }
}
