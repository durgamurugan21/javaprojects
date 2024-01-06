/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author ArunRamya
 */
@Entity
@Table(name = "product_dtls_t")
public class ProductDtls implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prd_id")
    private long prdId;
    @Column(name = "category")
    private String category;
    @Column(name = "sub_cat")
    private String subCategory;
    @Column(name = "prd_name")
    private String prdName;
    @Column(name = "rel_prd")
    private String relatedProduct;
    @Column(name = "prd_price")
    private String productPrice;
    @Column(name = "prd_qty")
    private String productQty;
    @Lob
    @Column(name = "prd_img")
    private byte[] prdImg;
    

    public ProductDtls() {

    }

    /**
     * @return the prdId
     */
    public long getPrdId() {
        return prdId;
    }

    /**
     * @param prdId the prdId to set
     */
    public void setPrdId(long prdId) {
        this.prdId = prdId;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the subCategory
     */
    public String getSubCategory() {
        return subCategory;
    }

    /**
     * @param subCategory the subCategory to set
     */
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    /**
     * @return the prdName
     */
    public String getPrdName() {
        return prdName;
    }

    /**
     * @param prdName the prdName to set
     */
    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    /**
     * @return the prdImg
     */
    public byte[] getPrdImg() {
        return prdImg;
    }

    /**
     * @param prdImg the prdImg to set
     */
    public void setPrdImg(byte[] prdImg) {
        this.prdImg = prdImg;
    }

    /**
     * @return the productPrice
     */
    public String getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice the productPrice to set
     */
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return the productQty
     */
    public String getProductQty() {
        return productQty;
    }

    /**
     * @param productQty the productQty to set
     */
    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    /**
     * @return the relatedProduct
     */
    public String getRelatedProduct() {
        return relatedProduct;
    }

    /**
     * @param relatedProduct the relatedProduct to set
     */
    public void setRelatedProduct(String relatedProduct) {
        this.relatedProduct = relatedProduct;
    }
    
}
