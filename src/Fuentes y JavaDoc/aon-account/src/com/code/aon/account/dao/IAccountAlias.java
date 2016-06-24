package com.code.aon.account.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.account.Account;
import com.code.aon.account.Period;
import com.code.aon.account.AutoConcept;
import com.code.aon.account.AccountEntry;
import com.code.aon.account.AccountEntryDetail;
import com.code.aon.account.AccountEntryInvoice;
import com.code.aon.account.AccountSummary;

/** 
* Interface for holding entity properties constants.
*/ 
public interface IAccountAlias {



	/** 
	* DAOConstantsEntry for Account entity.
	*/ 
	DAOConstantsEntry ACCOUNT_ENTRY = DAOConstants.getDAOConstant(Account.class);

	/** 
	* Alias value: Account_alias
	* Hibernate value: Account.alias
	*/
	String  ACCOUNT_ALIAS = ACCOUNT_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: Account_description
	* Hibernate value: Account.description
	*/
	String  ACCOUNT_DESCRIPTION = ACCOUNT_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: Account_entryEnabled
	* Hibernate value: Account.entryEnabled
	*/
	String  ACCOUNT_ENTRY_ENABLED = ACCOUNT_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: Account_id
	* Hibernate value: Account.id
	*/
	String  ACCOUNT_ID = ACCOUNT_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: Account_level
	* Hibernate value: Account.level
	*/
	String  ACCOUNT_LEVEL = ACCOUNT_ENTRY.getAliasNames()[4];



	/** 
	* DAOConstantsEntry for Period entity.
	*/ 
	DAOConstantsEntry PERIOD_ENTRY = DAOConstants.getDAOConstant(Period.class);

	/** 
	* Alias value: Period_deadline
	* Hibernate value: Period.deadline
	*/
	String  PERIOD_DEADLINE = PERIOD_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: Period_id
	* Hibernate value: Period.id
	*/
	String  PERIOD_ID = PERIOD_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: Period_initiationDate
	* Hibernate value: Period.initiationDate
	*/
	String  PERIOD_INITIATION_DATE = PERIOD_ENTRY.getAliasNames()[2];



	/** 
	* DAOConstantsEntry for AutoConcept entity.
	*/ 
	DAOConstantsEntry AUTO_CONCEPT_ENTRY = DAOConstants.getDAOConstant(AutoConcept.class);

	/** 
	* Alias value: AutoConcept_description
	* Hibernate value: AutoConcept.description
	*/
	String  AUTO_CONCEPT_DESCRIPTION = AUTO_CONCEPT_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: AutoConcept_id
	* Hibernate value: AutoConcept.id
	*/
	String  AUTO_CONCEPT_ID = AUTO_CONCEPT_ENTRY.getAliasNames()[1];



	/** 
	* DAOConstantsEntry for AccountEntry entity.
	*/ 
	DAOConstantsEntry ACCOUNT_ENTRY_ENTRY = DAOConstants.getDAOConstant(AccountEntry.class);

	/** 
	* Alias value: AccountEntry_accountPeriod
	* Hibernate value: AccountEntry.accountPeriod
	*/
	String  ACCOUNT_ENTRY_ACCOUNT_PERIOD = ACCOUNT_ENTRY_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: AccountEntry_entryDate
	* Hibernate value: AccountEntry.entryDate
	*/
	String  ACCOUNT_ENTRY_ENTRY_DATE = ACCOUNT_ENTRY_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: AccountEntry_id
	* Hibernate value: AccountEntry.id
	*/
	String  ACCOUNT_ENTRY_ID = ACCOUNT_ENTRY_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: AccountEntry_journal
	* Hibernate value: AccountEntry.journal
	*/
	String  ACCOUNT_ENTRY_JOURNAL = ACCOUNT_ENTRY_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: AccountEntry_securityLevel
	* Hibernate value: AccountEntry.securityLevel
	*/
	String  ACCOUNT_ENTRY_SECURITY_LEVEL = ACCOUNT_ENTRY_ENTRY.getAliasNames()[4];

	/** 
	* Alias value: AccountEntry_type
	* Hibernate value: AccountEntry.type
	*/
	String  ACCOUNT_ENTRY_TYPE = ACCOUNT_ENTRY_ENTRY.getAliasNames()[5];



	/** 
	* DAOConstantsEntry for AccountEntryDetail entity.
	*/ 
	DAOConstantsEntry ACCOUNT_ENTRY_DETAIL_ENTRY = DAOConstants.getDAOConstant(AccountEntryDetail.class);

	/** 
	* Alias value: AccountEntryDetail_accountEntry_id
	* Hibernate value: AccountEntryDetail.accountEntry.id
	*/
	String  ACCOUNT_ENTRY_DETAIL_ACCOUNT_ENTRY_ID = ACCOUNT_ENTRY_DETAIL_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: AccountEntryDetail_accountEntry_entryDate
	* Hibernate value: AccountEntryDetail.accountEntry.entryDate
	*/
	String  ACCOUNT_ENTRY_DETAIL_ACCOUNT_ENTRY_ENTRY_DATE = ACCOUNT_ENTRY_DETAIL_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: AccountEntryDetail_accountEntry_accountPeriod
	* Hibernate value: AccountEntryDetail.accountEntry.accountPeriod
	*/
	String  ACCOUNT_ENTRY_DETAIL_ACCOUNT_ENTRY_ACCOUNT_PERIOD = ACCOUNT_ENTRY_DETAIL_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: AccountEntryDetail_account_id
	* Hibernate value: AccountEntryDetail.account.id
	*/
	String  ACCOUNT_ENTRY_DETAIL_ACCOUNT_ID = ACCOUNT_ENTRY_DETAIL_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: AccountEntryDetail_balancingAccount_id
	* Hibernate value: AccountEntryDetail.balancingAccount.id
	*/
	String  ACCOUNT_ENTRY_DETAIL_BALANCING_ACCOUNT_ID = ACCOUNT_ENTRY_DETAIL_ENTRY.getAliasNames()[4];

	/** 
	* Alias value: AccountEntryDetail_concept
	* Hibernate value: AccountEntryDetail.concept
	*/
	String  ACCOUNT_ENTRY_DETAIL_CONCEPT = ACCOUNT_ENTRY_DETAIL_ENTRY.getAliasNames()[5];

	/** 
	* Alias value: AccountEntryDetail_credit
	* Hibernate value: AccountEntryDetail.credit
	*/
	String  ACCOUNT_ENTRY_DETAIL_CREDIT = ACCOUNT_ENTRY_DETAIL_ENTRY.getAliasNames()[6];

	/** 
	* Alias value: AccountEntryDetail_debit
	* Hibernate value: AccountEntryDetail.debit
	*/
	String  ACCOUNT_ENTRY_DETAIL_DEBIT = ACCOUNT_ENTRY_DETAIL_ENTRY.getAliasNames()[7];

	/** 
	* Alias value: AccountEntryDetail_id
	* Hibernate value: AccountEntryDetail.id
	*/
	String  ACCOUNT_ENTRY_DETAIL_ID = ACCOUNT_ENTRY_DETAIL_ENTRY.getAliasNames()[8];

	/** 
	* Alias value: AccountEntryDetail_line
	* Hibernate value: AccountEntryDetail.line
	*/
	String  ACCOUNT_ENTRY_DETAIL_LINE = ACCOUNT_ENTRY_DETAIL_ENTRY.getAliasNames()[9];



	/** 
	* DAOConstantsEntry for AccountEntryInvoice entity.
	*/ 
	DAOConstantsEntry ACCOUNT_ENTRY_INVOICE_ENTRY = DAOConstants.getDAOConstant(AccountEntryInvoice.class);

	/** 
	* Alias value: AccountEntryInvoice_accountEntry_id
	* Hibernate value: AccountEntryInvoice.accountEntry.id
	*/
	String  ACCOUNT_ENTRY_INVOICE_ACCOUNT_ENTRY_ID = ACCOUNT_ENTRY_INVOICE_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: AccountEntryInvoice_id
	* Hibernate value: AccountEntryInvoice.id
	*/
	String  ACCOUNT_ENTRY_INVOICE_ID = ACCOUNT_ENTRY_INVOICE_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: AccountEntryInvoice_invoice_id
	* Hibernate value: AccountEntryInvoice.invoice.id
	*/
	String  ACCOUNT_ENTRY_INVOICE_INVOICE_ID = ACCOUNT_ENTRY_INVOICE_ENTRY.getAliasNames()[2];



	/** 
	* DAOConstantsEntry for AccountSummary entity.
	*/ 
	DAOConstantsEntry ACCOUNT_SUMMARY_ENTRY = DAOConstants.getDAOConstant(AccountSummary.class);

	/** 
	* Alias value: AccountSummary_accountPeriod
	* Hibernate value: AccountSummary.accountPeriod
	*/
	String  ACCOUNT_SUMMARY_ACCOUNT_PERIOD = ACCOUNT_SUMMARY_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: AccountSummary_account_id
	* Hibernate value: AccountSummary.account.id
	*/
	String  ACCOUNT_SUMMARY_ACCOUNT_ID = ACCOUNT_SUMMARY_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: AccountSummary_credit
	* Hibernate value: AccountSummary.credit
	*/
	String  ACCOUNT_SUMMARY_CREDIT = ACCOUNT_SUMMARY_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: AccountSummary_debit
	* Hibernate value: AccountSummary.debit
	*/
	String  ACCOUNT_SUMMARY_DEBIT = ACCOUNT_SUMMARY_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: AccountSummary_entryDate
	* Hibernate value: AccountSummary.entryDate
	*/
	String  ACCOUNT_SUMMARY_ENTRY_DATE = ACCOUNT_SUMMARY_ENTRY.getAliasNames()[4];

	/** 
	* Alias value: AccountSummary_entryMonth
	* Hibernate value: AccountSummary.entryMonth
	*/
	String  ACCOUNT_SUMMARY_ENTRY_MONTH = ACCOUNT_SUMMARY_ENTRY.getAliasNames()[5];

	/** 
	* Alias value: AccountSummary_id
	* Hibernate value: AccountSummary.id
	*/
	String  ACCOUNT_SUMMARY_ID = ACCOUNT_SUMMARY_ENTRY.getAliasNames()[6];


}