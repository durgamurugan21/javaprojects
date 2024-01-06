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
import javax.persistence.Table;

/**
 *
 * @author ArunRamya
 */
@Entity
@Table(name = "pur_dtls_t")
public class PurchaseDtls implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long seq;
    @Column(name = "prd_name")
    private String prdName;
    @Column(name = "prd_ctg")
    private String prdCtg;
    @Column(name = "amt")
    private String amt;
    @Column(name = "qty")
    private int qty;
    @Column(name = "prd_id")
    private long prdId;
    @Column(name = "parent_product_id")
    private long parentPrdId;

    public PurchaseDtls() {

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
     * @return the prdCtg
     */
    public String getPrdCtg() {
        return prdCtg;
    }

    /**
     * @param prdCtg the prdCtg to set
     */
    public void setPrdCtg(String prdCtg) {
        this.prdCtg = prdCtg;
    }

    /**
     * @return the amt
     */
    public String getAmt() {
        return amt;
    }

    /**
     * @param amt the amt to set
     */
    public void setAmt(String amt) {
        this.amt = amt;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
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
     * @return the parentPrdId
     */
    public long getParentPrdId() {
        return parentPrdId;
    }

    /**
     * @param parentPrdId the parentPrdId to set
     */
    public void setParentPrdId(long parentPrdId) {
        this.parentPrdId = parentPrdId;
    }

}
