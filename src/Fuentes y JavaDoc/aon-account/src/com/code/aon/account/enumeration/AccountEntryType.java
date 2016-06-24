package com.code.aon.account.enumeration;

import java.util.Locale;
import java.util.ResourceBundle;

import com.code.aon.common.enumeration.IResourceable;

/**
 * The Enum AccountEntryType.
 */
public enum AccountEntryType implements IResourceable {
	
	/** OPENING ENTRY. */
	OPENING,
	
	/** CLOSING ENTRY. */
	CLOSING,
	
	/** OPERATING ENTRY. */
	OPERATING,
	
	/** STANDARD ENTRY. */
	MANUAL,
	
	/** SALES INVOICE. */
	SALES_INVOICE,
	
	/** PURCHASE INVOICE. */
	PURCHASE_INVOICE,
	
	/** EXPENSE INVOICE. */
	EXPENSE_INVOICE,
	
	/** INVESTMENT INVOICE. */
	INVESTMENT_INVOICE,
	
	/** EXPENSES. */
	EXPENSES,
	
	/** SALARY. */
	SALARY,
	
	/** TAX. */
	TAX,
	
	/** LOAN. */
	LOAN,
	
	/** LEASING. */
	LEASING,
	
	/** PAYMENT. */
	PAYMENT,
	
	/** COLLECTION. */
	COLLECTION,
	
	/** STOCK VARIATION. */
	STOCK_VARIATION,
	
	/** The AMORTIZATION. */
	AMORTIZATION;
	
	/** Message file base path. */
    private static final String BASE_NAME = "com.code.aon.account.i18n.messages";

    /** Message key prefix. */
    private static final String MSG_KEY_PREFIX = "aon_enum_account_type_";
	
	/**
	 * Returns a <code>String</code> with the transalation <code>Locale</code>
	 * for the locale.
	 * 
	 * @param locale Required Locale.
	 * 
	 * @return String a <code>String</code>.
	 */
    public String getName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale); 
		return bundle.getString(MSG_KEY_PREFIX + toString());
    }
}