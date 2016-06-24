package com.code.aon.ui.finance.event;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.finance.Finance;
import com.code.aon.finance.FinanceBatch;
import com.code.aon.finance.FinanceBatchDetail;
import com.code.aon.finance.enumeration.FinanceBatchStatus;
import com.code.aon.finance.enumeration.FinanceStatus;
import com.code.aon.ui.finance.controller.FBatchController;
import com.code.aon.ui.finance.controller.FBatchDetailController;
import com.code.aon.ui.form.event.ControllerAdapter;
import com.code.aon.ui.form.event.ControllerEvent;
import com.code.aon.ui.form.event.ControllerListenerException;
import com.code.aon.ui.util.AonUtil;

public class FBatchControllerListener extends ControllerAdapter {
	
	private static final Logger LOGGER = Logger.getLogger(FBatchControllerListener.class.getName());
	
	private static final String FINANCE_BATCH_DETAIL_CONTROLLER = "fBatchDetail"; 
	
	@Override
	public void afterBeanCreated(ControllerEvent event) throws ControllerListenerException {
		FinanceBatch fBatch = (FinanceBatch)event.getController().getTo();
		fBatch.setFinanceBatchStatus(FinanceBatchStatus.TODO);
	}
	
	@Override
	public void afterBeanAdded(ControllerEvent event) throws ControllerListenerException {
		FBatchController fBatchController = (FBatchController)event.getController();
		FinanceBatch fBatch = (FinanceBatch)fBatchController.getTo();
		fBatchController.reloadPendingFinances(fBatch.isPayment());
	}
	
	@Override
	public void afterBeanSelected(ControllerEvent event) throws ControllerListenerException {
		FBatchController fBatchController = (FBatchController)event.getController();
		FinanceBatch fBatch = (FinanceBatch)fBatchController.getTo();
		fBatchController.reloadPendingFinances(fBatch.isPayment());
	}
	
	@Override
	public void beforeBeanRemoved(ControllerEvent event) throws ControllerListenerException {
		FBatchDetailController fBatchDetailController = (FBatchDetailController)AonUtil.getController(FINANCE_BATCH_DETAIL_CONTROLLER);
		Iterator iter = fBatchDetailController.getWrappedList().iterator();
		try {
			IManagerBean financeBean = BeanManager.getManagerBean(Finance.class);
			while(iter.hasNext()){
				FinanceBatchDetail fBatchDetail = (FinanceBatchDetail)iter.next();
				fBatchDetail.getFinance().setFinanceStatus(FinanceStatus.PENDING);
				financeBean.update(fBatchDetail.getFinance());
			}
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error updating related finances status", e);
		}
	}
}