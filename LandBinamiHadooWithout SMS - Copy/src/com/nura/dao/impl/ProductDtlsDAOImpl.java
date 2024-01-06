/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.ProductDAO;
import com.nura.entity.ProductDtls;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class ProductDtlsDAOImpl implements ProductDAO {

    private Session session;
    private Transaction tx;

    @Override
    public List<Long> getOtherSellingPrdIds(long prdId) {
        List<Long> prdIds = new ArrayList<>();
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "select prd_id , count(*) from pur_dtls where parent_product_id =:parent_product_id "
                    + " and prd_id not in (:parent_product_id)  "
                    + "group by prd_id order by 2 desc limit 5";
            SQLQuery query = session.createSQLQuery(hql);
            query.setParameter("parent_product_id", prdId);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = query.list();

            for (Object object : data) {
                Map row = (Map) object;
                System.out.print("Product id: " + row.get("prd_id"));
                prdIds.add(Long.parseLong("" + row.get("prd_id")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return prdIds;
    }

    @Override
    public List<com.nura.entity.ProductDtls> getProductListBsdOnProductIdList(List<Long> prdIds) {
        List<com.nura.entity.ProductDtls> prdDtlsList = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From ProductDtls where prdId in (:prdId)";
            Query query = session.createQuery(hql);
            query.setParameterList("prdId", prdIds);
            prdDtlsList = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return prdDtlsList;
    }

    @Override
    public List<ProductDtls> getRelatedProducts(String prdName) {
        List<ProductDtls> relProducts = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From ProductDtls where relatedProduct =:relatedProduct";
            Query query = session.createQuery(hql);
            query.setParameter("relatedProduct", prdName);
            relProducts = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return relProducts;
    }

    @Override
    public List<String> getUniqueProductNamesBsdOnCat(String category) {
        List<String> productNames = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "select distinct(prd_name) from product_dtls where category =:category";
            Query query = session.createSQLQuery(hql);
            query.setParameter("category", category);
            productNames = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return productNames;
    }

    @Override
    public boolean updateQty(long prdId, int qty) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            ProductDtls prd = (ProductDtls) session.get(ProductDtls.class, prdId);
            prd.setProductQty("" + (Integer.parseInt(prd.getProductQty()) - qty));
            session.merge(prd);
            session.flush();
            tx.commit();
            saved = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return saved;
    }

    @Override
    public boolean saveProductDtls(ProductDtls prdDtls) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(prdDtls);
            session.flush();
            tx.commit();
            saved = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return saved;
    }

    @Override
    public List<ProductDtls> getAllProductDtls() {
        List<ProductDtls> prdList = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From ProductDtls";
            Query query = session.createQuery(hql);
            prdList = query.list();
            System.out.println("Product list size:-" + prdList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return prdList;
    }

    @Override
    public List<ProductDtls> getProductDtls(String category) {
        List<ProductDtls> prdList = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From ProductDtls where category =:category";
            Query query = session.createQuery(hql);
            query.setParameter("category", category);
            prdList = query.list();
            System.out.println("Product list size:-" + prdList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return prdList;
    }

    @Override
    public ProductDtls getProductDtlsBsdOnId(long prdId) {
        ProductDtls prdDtls = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From ProductDtls where prdId =:prdId";
            Query query = session.createQuery(hql);
            query.setParameter("prdId", prdId);
            prdDtls = (ProductDtls) query.list().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return prdDtls;
    }

    public static void main(String[] args) {
        ProductDtlsDAOImpl _prdDAOImpl = new ProductDtlsDAOImpl();
        System.out.println();
        List<Long> prdIds = _prdDAOImpl.getOtherSellingPrdIds(3);
        List<ProductDtls> prdLst = _prdDAOImpl.getProductListBsdOnProductIdList(prdIds);
        System.out.println(prdLst.get(0).getPrdName());
    }
}
