package com.code.aon.ui.finance.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.event.ActionEvent;

import com.code.aon.common.BeanManager;
import com.code.aon.common.IManagerBean;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.finance.Finance;
import com.code.aon.finance.FinanceBatchDetail;
import com.code.aon.finance.enumeration.FinanceStatus;
import com.code.aon.ui.form.LinesController;
import com.code.aon.ui.util.AonUtil;

public class FBatchDetailController extends LinesController {
	
	private static final Logger LOGGER = Logger.getLogger(FBatchDetailController.class.getName());
	
	private static final String FINANCE_BATCH_CONTROLLER_NAME = "fbatch";
	
	@SuppressWarnings("unused")
	public void onRemoveSelected(ActionEvent event) {
		FinanceBatchDetail fBatchDetail;
		try {
			fBatchDetail = (FinanceBatchDetail)this.getModel().getRowData();
			IManagerBean fBatchDetailBean = BeanManager.getManagerBean(FinanceBatchDetail.class);
			fBatchDetailBean.remove(fBatchDetail);
			IManagerBean financeBean = BeanManager.getManagerBean(Finance.class);
			fBatchDetail.getFinance().setFinanceStatus(FinanceStatus.PENDING);
			financeBean.update(fBatchDetail.getFinance());
			FBatchController fBatchController = (FBatchController)AonUtil.getController(FINANCE_BATCH_CONTROLLER_NAME);
			fBatchController.reloadFBatchDetailModel(fBatchDetail.getFinanceBatch());
			fBatchController.reloadPendingFinances(fBatchDetail.getFinanceBatch().isPayment());
		} catch (ManagerBeanException e) {
			LOGGER.log(Level.SEVERE, "Error removing selected Finance from the FinanceBatch", e);
		}
	}
}
