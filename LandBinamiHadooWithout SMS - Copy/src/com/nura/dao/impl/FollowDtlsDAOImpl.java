/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.FollowDtlsDAO;
import com.nura.entity.FollowDtls;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class FollowDtlsDAOImpl implements FollowDtlsDAO{

    private Session session;
    private Transaction tx;
    
    @Override
    public boolean saveFollowerDtls(FollowDtls fDtls) {
        boolean saved = false;
        try{
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(fDtls);
            session.flush();
            tx.commit();
            saved = true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }return saved;
    }

    @Override
    public List<Long> getFollowersBasedOnMyId(long id) {
        List<Long> followTweetsList = null;
        try{
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            String hql = "select followingId From FollowDtls where followerId =:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            followTweetsList = query.list();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }return followTweetsList;
    }       
}
