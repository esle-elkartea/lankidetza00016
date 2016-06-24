package com.code.aon.account.bridge.dao;

import java.io.File;

import com.code.aon.account.bridge.CreditorAccount;
import com.code.aon.account.bridge.CustomerAccount;
import com.code.aon.account.bridge.RegistryBankAccount;
import com.code.aon.account.bridge.SupplierAccount;
import com.code.aon.common.dao.AliasWriter;

public class AccountBridgeAliasWriter {

	public static void main(String[] args) throws Exception{
		File file = new File("c:/IAccountBridgeAlias.java");
		String[] classes = new String[4];
		classes[0] = CustomerAccount.class.getName();
		classes[1] = SupplierAccount.class.getName();
		classes[2] = CreditorAccount.class.getName();
		classes[3] = RegistryBankAccount.class.getName();
		AliasWriter writer = new AliasWriter("com.code.aon.account.bridge.dao");
		writer.write(classes, file);
		System.out.println( file.getAbsolutePath() );
		System.out.println("Alias generados");
	}
}