/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.PurchaseDtlsDAO;
import com.nura.entity.PurchaseDtls;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class PurchaseDtlsDAOImpl implements PurchaseDtlsDAO {

    private Session session;
    private Transaction tx;

    @Override
    public boolean savePurchaseDtls(PurchaseDtls prDtls) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(prDtls);
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
}
