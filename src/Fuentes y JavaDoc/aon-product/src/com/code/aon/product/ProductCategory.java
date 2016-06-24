package com.code.aon.product;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.code.aon.common.ILookupObject;
import com.code.aon.common.ITransferObject;
import com.code.aon.product.dao.IProductAlias;

/**
 * Transfer Object that represents product's categories.
 * 
 * @author Consulting & Development. Eugenio Castellano - 31-ene-2005
 * @since 1.0
 * @version 1.0
 */
@Entity
@Table(name="pcategory")
@Inheritance( strategy=InheritanceType.JOINED )
public class ProductCategory implements ITransferObject, ILookupObject  {

    /**
     * Unique key.
     */
    private Integer id;

    /**
     * Category name.
     */
    private String name;

    /**
     * Shows the pattern of the product's complementary description.  
     * For example, 
     * if <code>itemPattern</code> is "%TALLA% %COLOR%" and the description of the product
     * is "PANTALON LEVI'S", the description of the product will be "PANTALON
     * LEVI'S" + "42 AZUL".
     */
    private String itemPattern;

    /**
     * Void contructor.
     * 
     */
    public ProductCategory() {
    }

    /**
     * Constructor for this unique key.
     * 
     * @param pk
     *            Unique key.
     */
    public ProductCategory(Integer pk) {
        this.id = pk;
    }

    /**
     * Return the unique key.
     * 
     * @return unique key.
     */
    @Id
    @GeneratedValue
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }

    /**
     * Assigns thew unique key.
     * 
     * @param primaryKey
     *            unique key.
     */
    public void setId(Integer primaryKey) {
        this.id = primaryKey;
    }

    /**
     * Returns the product category name.
     * 
     * @return Category name.
     */
    @Column(length=32, nullable=false)
    public String getName() {
        return name;
    }

    /**
     * Assigns the product category name.
     * 
     * @param name
     *            Category name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the pattern for the product description.
     * 
     * @return pattern.
     */
    @Column(name="detail_pattern", length=64)
    public String getItemPattern() {
        return itemPattern;
    }

    /**
     * Assigns the product description pattern.
     * 
     * @param itemPattern
     *            pattern.
     */
    public void setItemPattern(String itemPattern) {
        this.itemPattern = itemPattern;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.code.aon.common.ILookupObject#lookups()
     */
    @Transient
    public Map<String,Object> getLookups() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(IProductAlias.PRODUCT_CATEGORY_ID, getId());
        map.put(IProductAlias.PRODUCT_CATEGORY_NAME, getName());
        return map;
    }


}