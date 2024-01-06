/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import java.util.List;

/**
 *
 * @author ArunRamya
 */
public interface ProductDAO {

    public boolean saveProductDtls(com.nura.entity.ProductDtls prdDtls);

    public List<com.nura.entity.ProductDtls> getProductDtls(String category);

    public List<com.nura.entity.ProductDtls> getAllProductDtls();

    public com.nura.entity.ProductDtls getProductDtlsBsdOnId(long prdId);

    public boolean updateQty(long prdId, int qty);

    public List<String> getUniqueProductNamesBsdOnCat(String category);

    public List<com.nura.entity.ProductDtls> getRelatedProducts(String prdName);

    public List<Long> getOtherSellingPrdIds(long prdId);
    
    public List<com.nura.entity.ProductDtls> getProductListBsdOnProductIdList(List<Long> prdIds);

}
