package com.code.aon.registry.dao;

import com.code.aon.common.dao.DAOConstants;
import com.code.aon.common.dao.DAOConstantsEntry;
import com.code.aon.registry.Category;
import com.code.aon.registry.Registry;
import com.code.aon.registry.RegistryAddress;
import com.code.aon.registry.RegistryAttachment;
import com.code.aon.registry.RegistryMedia;
import com.code.aon.registry.Relationship;
import com.code.aon.registry.RegistryRelationship;
import com.code.aon.registry.RegistryNote;

/** 
* Interface for holding entity properties constants.
*/ 
public interface IRegistryAlias {



	/** 
	* DAOConstantsEntry for Category entity.
	*/ 
	DAOConstantsEntry CATEGORY_ENTRY = DAOConstants.getDAOConstant(Category.class);

	/** 
	* Alias value: Category_id
	* Hibernate value: Category.id
	*/
	String  CATEGORY_ID = CATEGORY_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: Category_name
	* Hibernate value: Category.name
	*/
	String  CATEGORY_NAME = CATEGORY_ENTRY.getAliasNames()[1];



	/** 
	* DAOConstantsEntry for Registry entity.
	*/ 
	DAOConstantsEntry REGISTRY_ENTRY = DAOConstants.getDAOConstant(Registry.class);

	/** 
	* Alias value: Registry_alias
	* Hibernate value: Registry.alias
	*/
	String  REGISTRY_ALIAS = REGISTRY_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: Registry_document
	* Hibernate value: Registry.document
	*/
	String  REGISTRY_DOCUMENT = REGISTRY_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: Registry_id
	* Hibernate value: Registry.id
	*/
	String  REGISTRY_ID = REGISTRY_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: Registry_name
	* Hibernate value: Registry.name
	*/
	String  REGISTRY_NAME = REGISTRY_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: Registry_surname
	* Hibernate value: Registry.surname
	*/
	String  REGISTRY_SURNAME = REGISTRY_ENTRY.getAliasNames()[4];

	/** 
	* Alias value: Registry_type
	* Hibernate value: Registry.type
	*/
	String  REGISTRY_TYPE = REGISTRY_ENTRY.getAliasNames()[5];



	/** 
	* DAOConstantsEntry for RegistryAddress entity.
	*/ 
	DAOConstantsEntry REGISTRY_ADDRESS_ENTRY = DAOConstants.getDAOConstant(RegistryAddress.class);

	/** 
	* Alias value: RegistryAddress_address
	* Hibernate value: RegistryAddress.address
	*/
	String  REGISTRY_ADDRESS_ADDRESS = REGISTRY_ADDRESS_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: RegistryAddress_address2
	* Hibernate value: RegistryAddress.address2
	*/
	String  REGISTRY_ADDRESS_ADDRESS2 = REGISTRY_ADDRESS_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: RegistryAddress_address3
	* Hibernate value: RegistryAddress.address3
	*/
	String  REGISTRY_ADDRESS_ADDRESS3 = REGISTRY_ADDRESS_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: RegistryAddress_addressType
	* Hibernate value: RegistryAddress.addressType
	*/
	String  REGISTRY_ADDRESS_ADDRESS_TYPE = REGISTRY_ADDRESS_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: RegistryAddress_city
	* Hibernate value: RegistryAddress.city
	*/
	String  REGISTRY_ADDRESS_CITY = REGISTRY_ADDRESS_ENTRY.getAliasNames()[4];

	/** 
	* Alias value: RegistryAddress_geozone_id
	* Hibernate value: RegistryAddress.geozone.id
	*/
	String  REGISTRY_ADDRESS_GEOZONE_ID = REGISTRY_ADDRESS_ENTRY.getAliasNames()[5];

	/** 
	* Alias value: RegistryAddress_id
	* Hibernate value: RegistryAddress.id
	*/
	String  REGISTRY_ADDRESS_ID = REGISTRY_ADDRESS_ENTRY.getAliasNames()[6];

	/** 
	* Alias value: RegistryAddress_recipient
	* Hibernate value: RegistryAddress.recipient
	*/
	String  REGISTRY_ADDRESS_RECIPIENT = REGISTRY_ADDRESS_ENTRY.getAliasNames()[7];

	/** 
	* Alias value: RegistryAddress_registry_id
	* Hibernate value: RegistryAddress.registry.id
	*/
	String  REGISTRY_ADDRESS_REGISTRY_ID = REGISTRY_ADDRESS_ENTRY.getAliasNames()[8];

	/** 
	* Alias value: RegistryAddress_streetType
	* Hibernate value: RegistryAddress.streetType
	*/
	String  REGISTRY_ADDRESS_STREET_TYPE = REGISTRY_ADDRESS_ENTRY.getAliasNames()[9];

	/** 
	* Alias value: RegistryAddress_zip
	* Hibernate value: RegistryAddress.zip
	*/
	String  REGISTRY_ADDRESS_ZIP = REGISTRY_ADDRESS_ENTRY.getAliasNames()[10];



	/** 
	* DAOConstantsEntry for RegistryAttachment entity.
	*/ 
	DAOConstantsEntry REGISTRY_ATTACHMENT_ENTRY = DAOConstants.getDAOConstant(RegistryAttachment.class);

	/** 
	* Alias value: RegistryAttachment_category_id
	* Hibernate value: RegistryAttachment.category.id
	*/
	String  REGISTRY_ATTACHMENT_CATEGORY_ID = REGISTRY_ATTACHMENT_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: RegistryAttachment_data
	* Hibernate value: RegistryAttachment.data
	*/
	String  REGISTRY_ATTACHMENT_DATA = REGISTRY_ATTACHMENT_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: RegistryAttachment_description
	* Hibernate value: RegistryAttachment.description
	*/
	String  REGISTRY_ATTACHMENT_DESCRIPTION = REGISTRY_ATTACHMENT_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: RegistryAttachment_id
	* Hibernate value: RegistryAttachment.id
	*/
	String  REGISTRY_ATTACHMENT_ID = REGISTRY_ATTACHMENT_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: RegistryAttachment_mimeType
	* Hibernate value: RegistryAttachment.mimeType
	*/
	String  REGISTRY_ATTACHMENT_MIME_TYPE = REGISTRY_ATTACHMENT_ENTRY.getAliasNames()[4];

	/** 
	* Alias value: RegistryAttachment_registry_id
	* Hibernate value: RegistryAttachment.registry.id
	*/
	String  REGISTRY_ATTACHMENT_REGISTRY_ID = REGISTRY_ATTACHMENT_ENTRY.getAliasNames()[5];

	/** 
	* Alias value: RegistryAttachment_size
	* Hibernate value: RegistryAttachment.size
	*/
	String  REGISTRY_ATTACHMENT_SIZE = REGISTRY_ATTACHMENT_ENTRY.getAliasNames()[6];



	/** 
	* DAOConstantsEntry for RegistryMedia entity.
	*/ 
	DAOConstantsEntry REGISTRY_MEDIA_ENTRY = DAOConstants.getDAOConstant(RegistryMedia.class);

	/** 
	* Alias value: RegistryMedia_comment
	* Hibernate value: RegistryMedia.comment
	*/
	String  REGISTRY_MEDIA_COMMENT = REGISTRY_MEDIA_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: RegistryMedia_id
	* Hibernate value: RegistryMedia.id
	*/
	String  REGISTRY_MEDIA_ID = REGISTRY_MEDIA_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: RegistryMedia_mediaType
	* Hibernate value: RegistryMedia.mediaType
	*/
	String  REGISTRY_MEDIA_MEDIA_TYPE = REGISTRY_MEDIA_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: RegistryMedia_registry_id
	* Hibernate value: RegistryMedia.registry.id
	*/
	String  REGISTRY_MEDIA_REGISTRY_ID = REGISTRY_MEDIA_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: RegistryMedia_value
	* Hibernate value: RegistryMedia.value
	*/
	String  REGISTRY_MEDIA_VALUE = REGISTRY_MEDIA_ENTRY.getAliasNames()[4];



	/** 
	* DAOConstantsEntry for Relationship entity.
	*/ 
	DAOConstantsEntry RELATIONSHIP_ENTRY = DAOConstants.getDAOConstant(Relationship.class);

	/** 
	* Alias value: Relationship_description
	* Hibernate value: Relationship.description
	*/
	String  RELATIONSHIP_DESCRIPTION = RELATIONSHIP_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: Relationship_id
	* Hibernate value: Relationship.id
	*/
	String  RELATIONSHIP_ID = RELATIONSHIP_ENTRY.getAliasNames()[1];



	/** 
	* DAOConstantsEntry for RegistryRelationship entity.
	*/ 
	DAOConstantsEntry REGISTRY_RELATIONSHIP_ENTRY = DAOConstants.getDAOConstant(RegistryRelationship.class);

	/** 
	* Alias value: RegistryRelationship_comments
	* Hibernate value: RegistryRelationship.comments
	*/
	String  REGISTRY_RELATIONSHIP_COMMENTS = REGISTRY_RELATIONSHIP_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: RegistryRelationship_id
	* Hibernate value: RegistryRelationship.id
	*/
	String  REGISTRY_RELATIONSHIP_ID = REGISTRY_RELATIONSHIP_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: RegistryRelationship_registry_id
	* Hibernate value: RegistryRelationship.registry.id
	*/
	String  REGISTRY_RELATIONSHIP_REGISTRY_ID = REGISTRY_RELATIONSHIP_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: RegistryRelationship_relatedRegistry_id
	* Hibernate value: RegistryRelationship.relatedRegistry.id
	*/
	String  REGISTRY_RELATIONSHIP_RELATED_REGISTRY_ID = REGISTRY_RELATIONSHIP_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: RegistryRelationship_relationship_id
	* Hibernate value: RegistryRelationship.relationship.id
	*/
	String  REGISTRY_RELATIONSHIP_RELATIONSHIP_ID = REGISTRY_RELATIONSHIP_ENTRY.getAliasNames()[4];



	/** 
	* DAOConstantsEntry for RegistryNote entity.
	*/ 
	DAOConstantsEntry REGISTRY_NOTE_ENTRY = DAOConstants.getDAOConstant(RegistryNote.class);

	/** 
	* Alias value: RegistryNote_comments
	* Hibernate value: RegistryNote.comments
	*/
	String  REGISTRY_NOTE_COMMENTS = REGISTRY_NOTE_ENTRY.getAliasNames()[0];

	/** 
	* Alias value: RegistryNote_description
	* Hibernate value: RegistryNote.description
	*/
	String  REGISTRY_NOTE_DESCRIPTION = REGISTRY_NOTE_ENTRY.getAliasNames()[1];

	/** 
	* Alias value: RegistryNote_id
	* Hibernate value: RegistryNote.id
	*/
	String  REGISTRY_NOTE_ID = REGISTRY_NOTE_ENTRY.getAliasNames()[2];

	/** 
	* Alias value: RegistryNote_noteDate
	* Hibernate value: RegistryNote.noteDate
	*/
	String  REGISTRY_NOTE_NOTE_DATE = REGISTRY_NOTE_ENTRY.getAliasNames()[3];

	/** 
	* Alias value: RegistryNote_registry_id
	* Hibernate value: RegistryNote.registry.id
	*/
	String  REGISTRY_NOTE_REGISTRY_ID = REGISTRY_NOTE_ENTRY.getAliasNames()[4];


}