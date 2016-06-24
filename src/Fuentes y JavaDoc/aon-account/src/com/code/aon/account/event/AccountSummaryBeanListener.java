package com.code.aon.account.event;

import com.code.aon.account.AccountEntryDetail;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.common.event.ManagerBeanEvent;
import com.code.aon.common.event.ManagerBeanListenerAdapter;

/**
 * @author Consulting & Development
 *
 */
public class AccountSummaryBeanListener extends ManagerBeanListenerAdapter {

    @Override
    public void beanInserted(ManagerBeanEvent evt) throws ManagerBeanException {
        AccountSummaryManager.modifyAccountSummary((AccountEntryDetail)evt.getTo(), 1);
    }

    @Override
    public void beanUpdated(ManagerBeanEvent evt) throws ManagerBeanException {
        AccountSummaryManager.modifyAccountSummary((AccountEntryDetail)evt.getTo(), 1);
    }

    @Override
    public void beanRemoved(ManagerBeanEvent evt) throws ManagerBeanException {
        AccountSummaryManager.modifyAccountSummary((AccountEntryDetail)evt.getTo(), -1);
    }

}
