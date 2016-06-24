package com.code.aon.planner.recurrence;

import java.io.Serializable;
import java.text.MessageFormat;

public interface IRecurrence extends Serializable {

	/** Indica el tipo de repetici�n, cada x d�as, semanas, meses, a�os. */
	String EACH = "0";
	/** Indica el tipo de repetici�n, cada cierto rango en el tiempo. */
	String RANGE = "1";
	/** Indica que no se especifica fecha de finalizaci�n. */
	String WITHOUT = "0";
	/** Indica el n�mero de veces a repetir el evento. */
	String COUNT = "1";
	/** Indica hasta que fecha se repetir� el evento. */
	String UNTILDATE = "2";
	
	/** Formato de periodicidad. 
	 * 		{0}-FREQ 
	 * 		{1}-INTERVAL 
	 * 		{2}-COUNT || UNTIL 
	 * 		{3}-BYMONTH 
	 * 		{4}-BYDAY 
	 */
	MessageFormat RRULE = new MessageFormat("FREQ={0}{1}{2}{3}{4}");

	/**
	 * Construye el objeto <code>String</code> de periodicidad.
	 * 
	 * @return
	 */
	String toRRULEString();

}
