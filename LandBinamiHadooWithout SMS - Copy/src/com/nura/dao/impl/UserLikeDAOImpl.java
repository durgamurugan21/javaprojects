/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.UserLikeDAO;
import com.nura.entity.UserLikes;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class UserLikeDAOImpl implements UserLikeDAO {

    private Session session;
    private Transaction tx;

    @Override
    public List<com.nura.entity.UserLikes> userLikeList() {
        List<com.nura.entity.UserLikes> userLikeLst = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String hql = "From UserLikes";
            Query query = session.createQuery(hql);
            userLikeLst = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userLikeLst;
    }

    @Override
    public boolean saveUserLike(UserLikes userLike) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(userLike);
            session.flush();
            tx.commit();
            saved = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return saved;
    }
}
