package com.code.aon.sales.dao;

import java.io.File;

import com.code.aon.common.dao.AliasWriter;
import com.code.aon.sales.Customer;
import com.code.aon.sales.CustomerFee;
import com.code.aon.sales.FinanceSales;
import com.code.aon.sales.PointOfSale;
import com.code.aon.sales.Sales;
import com.code.aon.sales.SalesAttachment;
import com.code.aon.sales.SalesDetail;
import com.code.aon.sales.SalesType;
import com.code.aon.sales.Seller;

public class SalesAliasWriter {

	public static void main(String[] args) throws Exception{
		File file = new File("c:/ISalesAlias.java");
		String[] classes = new String[9];
		classes[0] = Customer.class.getName();
		classes[1] = CustomerFee.class.getName();
		classes[2] = FinanceSales.class.getName();
		classes[3] = Sales.class.getName();
		classes[4] = SalesAttachment.class.getName();
		classes[5] = SalesDetail.class.getName();
		classes[6] = SalesType.class.getName();
		classes[7] = Seller.class.getName();
		classes[8] = PointOfSale.class.getName();
		AliasWriter writer = new AliasWriter("com.code.aon.sales.dao");
		writer.write(classes, file);
		System.out.println( file.getAbsolutePath() );
		System.out.println("Alias generados");
	}
}