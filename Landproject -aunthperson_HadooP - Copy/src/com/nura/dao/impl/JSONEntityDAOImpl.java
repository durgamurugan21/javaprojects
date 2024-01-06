/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.JSONEntityDAO;
import com.nura.entity.JSONEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class JSONEntityDAOImpl implements JSONEntityDAO {
    
    private Session session;
    private Transaction tx;
    
    @Override
    public boolean deleteAllRows() {
        boolean removed = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "delete from json_entity";
            Query query = session.createSQLQuery(hql);
            query.executeUpdate();
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return removed;
    }
    
    @Override
    public boolean saveDetailss(JSONEntity jsonEntity) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(jsonEntity);
            session.flush();
            tx.commit();
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
    public JSONEntity getCat(String userName) {
        JSONEntity val = null;
        try{
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From JSONEntity where userName =:userName";
            Query query = session.createQuery(hql);
            query.setParameter("userName", userName);
            val = (JSONEntity) query.list().get(0);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return val;
    }
   
}
