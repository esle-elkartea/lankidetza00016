package com.code.aon.purchase.dao;

import java.io.File;
import java.io.IOException;

import com.code.aon.common.dao.AliasWriter;
import com.code.aon.purchase.Purchase;
import com.code.aon.purchase.PurchaseDetail;
import com.code.aon.purchase.Supplier;

public class PurchaseAliasWriter {

	public static void main(String[] args) throws IOException {
        File file = new File("C:\\IPurchaseAlias.java");
        String[] classes = new String[3];
        classes[0] = PurchaseDetail.class.getName();
        classes[1] = Purchase.class.getName();
        classes[2] = Supplier.class.getName();
		AliasWriter writer = new AliasWriter("com.code.aon.purchase.dao");
		writer.write(classes,file);
		System.out.println("Alias generados");
	}
}

