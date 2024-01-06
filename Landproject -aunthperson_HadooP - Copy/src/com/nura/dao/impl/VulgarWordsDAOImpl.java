/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.VulgarWordsDAO;
import com.nura.entity.VulgarWords;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class VulgarWordsDAOImpl implements VulgarWordsDAO {

    private Session session;
    private Transaction tx;

    @Override
    public boolean saveVulgarWords(VulgarWords vulWords) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(vulWords);
            session.flush();
            tx.commit();
            saved = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }return saved;
    }

    @Override
    public List<VulgarWords> getAllWords() {
        List<VulgarWords> vulList = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            String hql = "From VulgarWords";
            Query query = session.createQuery(hql);
            vulList = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }return vulList;
    }

}
