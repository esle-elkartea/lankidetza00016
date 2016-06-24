package com.code.aon.employee.dao;

import java.io.File;
import java.io.IOException;

import com.code.aon.common.dao.AliasWriter;
import com.code.aon.employee.Employee;
import com.code.aon.employee.EmployeeWorkGroup;
import com.code.aon.employee.Incidence;
import com.code.aon.employee.IncidenceType;
import com.code.aon.employee.Resource;
import com.code.aon.employee.WorkActivity;
import com.code.aon.employee.WorkGroup;
import com.code.aon.employee.WorkPlace;

public class EmployeeAliasWriter {
	
	public static void main(String[] args) throws IOException {
		File file = new File("/PROYECTOS/aon-employee/src/com/code/aon/employee/dao/IEmployeeAlias.java");
		String[] classes = new String[8];
		classes[0] = Employee.class.getName();
		classes[1] = WorkPlace.class.getName();
		classes[2] = WorkActivity.class.getName();
		classes[3] = Incidence.class.getName();
		classes[4] = IncidenceType.class.getName();
		classes[5] = Resource.class.getName();
		classes[6] = WorkGroup.class.getName();
		classes[7] = EmployeeWorkGroup.class.getName();
		AliasWriter writer = new AliasWriter("com.code.aon.employee.dao");
		writer.write(classes, file);
		System.out.println( file.getAbsolutePath() );
		System.out.println("Alias generados");
	}
}