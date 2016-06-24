package com.code.aon.sales.enumeration;

import java.util.Locale;
import java.util.ResourceBundle;

import com.code.aon.common.enumeration.IResourceable;

/**
 * Enummeration to identify the different billing periods.
 * 
 * @author Consulting & Development. Eugenio Castellano - 31-ene-2005
 * @since 1.0
 * @version 1.0
 */
public enum BillingPeriod implements IResourceable {

	 /** NO PERIOD. */
    NO_PERIOD,

	/** MONTHLY. */
	MONTHLY,

	/** Bi MONTHLY. */
	BI_MONTHLY,

    /** THREE MONTHLY. */
    THREE_MONTHLY,

    /** FOUR MONTHLY. */
    FOUR_MONTHLY,

    /** FIVE MONTHLY. */
    FIVE_MONTHLY,

    /** SIX MONTHLY. */
    SIX_MONTHLY,

    /** SEVEN MONTHLY. */
    SEVEN_MONTHLY,

    /** EIGHT MONTHLY. */
    EIGHT_MONTHLY,

    /** NINE MONTHLY. */
    NINE_MONTHLY,

    /** TEN MONTHLY. */
    TEN_MONTHLY,

    /** ELEVEN MONTHLY. */
    ELEVEN_MONTHLY,

    /** YEARLY. */
    YEARLY;

    /** Message file base path. */
    private static final String BASE_NAME = "com.code.aon.sales.i18n.messages";

    /** Message key prefix. */
    private static final String MSG_KEY_PREFIX = "aon_enum_billing_period_";


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