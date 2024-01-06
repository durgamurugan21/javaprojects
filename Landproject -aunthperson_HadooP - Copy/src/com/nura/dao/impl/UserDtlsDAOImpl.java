/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.UserDtlsDAO;
import com.nura.entity.UserDetails;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class UserDtlsDAOImpl implements UserDtlsDAO {

    private Session session;
    private Transaction tx;

    @Override
    public List<String> getUsernameById(List<Long> id) {
        List<String> userName = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "select userName From com.nura.entity.UserDetails where userId in (:userId)";
            Query query = session.createQuery(hql);
            query.setParameterList("userId", id);
            userName = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userName;
    }

    @Override
    public String getUsername(long id) {
        String userName = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "select userName From com.nura.entity.UserDetails where userId =:userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", id);
            userName = (String) query.list().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userName;
    }

    @Override
    public List<com.nura.entity.UserDetails> getUserListOtherThanMe(String userName) {
        List<com.nura.entity.UserDetails> usersList = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From com.nura.entity.UserDetails where userName <> :userName";
            Query query = session.createQuery(hql);
            query.setParameter("userName", userName);
            usersList = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usersList;
    }

    @Override
    public boolean saveUserDtls(UserDetails userDtls) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(userDtls);
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
    public UserDetails getUserDetails(String userName, String password) {
        UserDetails ud = null;
        try {
            String hql = "From UserDetails where userName =:userName and password =:password";
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("userName", userName);
            query.setParameter("password", password);
            ud = (UserDetails) query.list().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ud;
    }

    public static void main(String[] args) {
        UserDtlsDAOImpl _impl = new UserDtlsDAOImpl();
        System.out.println(_impl.getUserListOtherThanMe("arun@gmail.com").get(0).getUserName());
    }
}
