package com.code.aon.company.dao;

import java.io.File;
import java.io.IOException;

import com.code.aon.common.dao.AliasWriter;
import com.code.aon.company.Calendar;
import com.code.aon.company.Company;

/**
 * @author Consulting & Development. ecastellano - 22/01/2007
 *
 */
public class CompanyAliasWriter {
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		File file = new File("/PROYECTOS/aon-registry/src/com/code/aon/company/dao/ICompanyAlias.java");
		String[] classes = new String[2]; 
		classes[0] = Company.class.getName();
		classes[1] = Calendar.class.getName();
		AliasWriter writer = new AliasWriter("com.code.aon.company.dao");
		writer.write(classes, file);
		System.out.println( file.getAbsolutePath() );
		System.out.println("Alias generados");
	}
}