package com.code.aon.employee.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.employee.Employee;
import com.code.aon.employee.WorkPlace;
import com.code.aon.employee.WorkActivity;
import com.code.aon.employee.Incidence;
import com.code.aon.employee.IncidenceType;
import com.code.aon.employee.Resource;
import com.code.aon.employee.WorkGroup;
import com.code.aon.employee.EmployeeWorkGroup;

/** 
* Interface for holding entity properties constants.
*/ 
public interface IEmployeeAlias {



	/** 
	* DAOConstantsEntry for Employee entity.
	*/ 
	DAOConstantsEntry EMPLOYEE_ENTRY = DAOConstants.getDAOConstant(Employee.class);

	/** 
	* Alias value: Employee_id
	* Hibernate value: Employee.id
	*/
	String  EMPLOYEE_ID = EMPLOYEE_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: Employee_registry_id
	* Hibernate value: Employee.registry.id
	*/
	String  EMPLOYEE_REGISTRY_ID = EMPLOYEE_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: Employee_registry_name
	* Hibernate value: Employee.registry.name
	*/
	String  EMPLOYEE_REGISTRY_NAME = EMPLOYEE_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: Employee_registry_surname
	* Hibernate value: Employee.registry.surname
	*/
	String  EMPLOYEE_REGISTRY_SURNAME = EMPLOYEE_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: Employee_registry_alias
	* Hibernate value: Employee.registry.alias
	*/
	String  EMPLOYEE_REGISTRY_ALIAS = EMPLOYEE_ENTRY.getAliasNames()[4];

	/** 
	* Alias value: Employee_registry_document
	* Hibernate value: Employee.registry.document
	*/
	String  EMPLOYEE_REGISTRY_DOCUMENT = EMPLOYEE_ENTRY.getAliasNames()[5];

	/** 
	* Alias value: Employee_calendar
	* Hibernate value: Employee.calendar
	*/
	String  EMPLOYEE_CALENDAR = EMPLOYEE_ENTRY.getAliasNames()[6];

	/** 
	* Alias value: Employee_socialSecurityNumber
	* Hibernate value: Employee.socialSecurityNumber
	*/
	String  EMPLOYEE_SOCIAL_SECURITY_NUMBER = EMPLOYEE_ENTRY.getAliasNames()[7];

	/** 
	* Alias value: Employee_username
	* Hibernate value: Employee.username
	*/
	String  EMPLOYEE_USERNAME = EMPLOYEE_ENTRY.getAliasNames()[8];


	/** 
	* DAOConstantsEntry for WorkPlace entity.
	*/ 
	DAOConstantsEntry WORK_PLACE_ENTRY = DAOConstants.getDAOConstant(WorkPlace.class);

	/** 
	* Alias value: WorkPlace_address_id
	* Hibernate value: WorkPlace.address.id
	*/
	String  WORK_PLACE_ADDRESS_ID = WORK_PLACE_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: WorkPlace_calendar
	* Hibernate value: WorkPlace.calendar
	*/
	String  WORK_PLACE_CALENDAR = WORK_PLACE_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: WorkPlace_description
	* Hibernate value: WorkPlace.description
	*/
	String  WORK_PLACE_DESCRIPTION = WORK_PLACE_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: WorkPlace_id
	* Hibernate value: WorkPlace.id
	*/
	String  WORK_PLACE_ID = WORK_PLACE_ENTRY.getAliasNames()[3];



	/** 
	* DAOConstantsEntry for WorkActivity entity.
	*/ 
	DAOConstantsEntry WORK_ACTIVITY_ENTRY = DAOConstants.getDAOConstant(WorkActivity.class);

	/** 
	* Alias value: WorkActivity_calendar
	* Hibernate value: WorkActivity.calendar
	*/
	String  WORK_ACTIVITY_CALENDAR = WORK_ACTIVITY_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: WorkActivity_description
	* Hibernate value: WorkActivity.description
	*/
	String  WORK_ACTIVITY_DESCRIPTION = WORK_ACTIVITY_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: WorkActivity_id
	* Hibernate value: WorkActivity.id
	*/
	String  WORK_ACTIVITY_ID = WORK_ACTIVITY_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: WorkActivity_workPlace_id
	* Hibernate value: WorkActivity.workPlace.id
	*/
	String  WORK_ACTIVITY_WORK_PLACE_ID = WORK_ACTIVITY_ENTRY.getAliasNames()[3];



	/** 
	* DAOConstantsEntry for Incidence entity.
	*/ 
	DAOConstantsEntry INCIDENCE_ENTRY = DAOConstants.getDAOConstant(Incidence.class);

	/** 
	* Alias value: Incidence_endingDate
	* Hibernate value: Incidence.endingDate
	*/
	String  INCIDENCE_ENDING_DATE = INCIDENCE_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: Incidence_id
	* Hibernate value: Incidence.id
	*/
	String  INCIDENCE_ID = INCIDENCE_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: Incidence_incidenceType_id
	* Hibernate value: Incidence.incidenceType.id
	*/
	String  INCIDENCE_INCIDENCE_TYPE_ID = INCIDENCE_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: Incidence_length
	* Hibernate value: Incidence.length
	*/
	String  INCIDENCE_LENGTH = INCIDENCE_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: Incidence_resource_id
	* Hibernate value: Incidence.resource.id
	*/
	String  INCIDENCE_RESOURCE_ID = INCIDENCE_ENTRY.getAliasNames()[4];

	/** 
	* Alias value: Incidence_startingDate
	* Hibernate value: Incidence.startingDate
	*/
	String  INCIDENCE_STARTING_DATE = INCIDENCE_ENTRY.getAliasNames()[5];

	/** 
	* Alias value: Incidence_startingTime
	* Hibernate value: Incidence.startingTime
	*/
	String  INCIDENCE_STARTING_TIME = INCIDENCE_ENTRY.getAliasNames()[6];



	/** 
	* DAOConstantsEntry for IncidenceType entity.
	*/ 
	DAOConstantsEntry INCIDENCE_TYPE_ENTRY = DAOConstants.getDAOConstant(IncidenceType.class);

	/** 
	* Alias value: IncidenceType_alias
	* Hibernate value: IncidenceType.alias
	*/
	String  INCIDENCE_TYPE_ALIAS = INCIDENCE_TYPE_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: IncidenceType_compute
	* Hibernate value: IncidenceType.compute
	*/
	String  INCIDENCE_TYPE_COMPUTE = INCIDENCE_TYPE_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: IncidenceType_description
	* Hibernate value: IncidenceType.description
	*/
	String  INCIDENCE_TYPE_DESCRIPTION = INCIDENCE_TYPE_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: IncidenceType_id
	* Hibernate value: IncidenceType.id
	*/
	String  INCIDENCE_TYPE_ID = INCIDENCE_TYPE_ENTRY.getAliasNames()[3];



	/** 
	* DAOConstantsEntry for Resource entity.
	*/ 
	DAOConstantsEntry RESOURCE_ENTRY = DAOConstants.getDAOConstant(Resource.class);

	/** 
	* Alias value: Resource_employee_id
	* Hibernate value: Resource.employee.id
	*/
	String  RESOURCE_EMPLOYEE_ID = RESOURCE_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: Resource_endingDate
	* Hibernate value: Resource.endingDate
	*/
	String  RESOURCE_ENDING_DATE = RESOURCE_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: Resource_id
	* Hibernate value: Resource.id
	*/
	String  RESOURCE_ID = RESOURCE_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: Resource_startingDate
	* Hibernate value: Resource.startingDate
	*/
	String  RESOURCE_STARTING_DATE = RESOURCE_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: Resource_workActivity_id
	* Hibernate value: Resource.workActivity.id
	*/
	String  RESOURCE_WORK_ACTIVITY_ID = RESOURCE_ENTRY.getAliasNames()[4];

	/** 
	* Alias value: Resource_workPlace_id
	* Hibernate value: Resource.workPlace.id
	*/
	String  RESOURCE_WORK_PLACE_ID = RESOURCE_ENTRY.getAliasNames()[5];



	/** 
	* DAOConstantsEntry for WorkGroup entity.
	*/ 
	DAOConstantsEntry WORK_GROUP_ENTRY = DAOConstants.getDAOConstant(WorkGroup.class);

	/** 
	* Alias value: WorkGroup_description
	* Hibernate value: WorkGroup.description
	*/
	String  WORK_GROUP_DESCRIPTION = WORK_GROUP_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: WorkGroup_id
	* Hibernate value: WorkGroup.id
	*/
	String  WORK_GROUP_ID = WORK_GROUP_ENTRY.getAliasNames()[1];



	/** 
	* DAOConstantsEntry for EmployeeWorkGroup entity.
	*/ 
	DAOConstantsEntry EMPLOYEE_WORK_GROUP_ENTRY = DAOConstants.getDAOConstant(EmployeeWorkGroup.class);

	/** 
	* Alias value: EmployeeWorkGroup_employee_id
	* Hibernate value: EmployeeWorkGroup.employee.id
	*/
	String  EMPLOYEE_WORK_GROUP_EMPLOYEE_ID = EMPLOYEE_WORK_GROUP_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: EmployeeWorkGroup_group_id
	* Hibernate value: EmployeeWorkGroup.group.id
	*/
	String  EMPLOYEE_WORK_GROUP_GROUP_ID = EMPLOYEE_WORK_GROUP_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: EmployeeWorkGroup_id
	* Hibernate value: EmployeeWorkGroup.id
	*/
	String  EMPLOYEE_WORK_GROUP_ID = EMPLOYEE_WORK_GROUP_ENTRY.getAliasNames()[2];


}