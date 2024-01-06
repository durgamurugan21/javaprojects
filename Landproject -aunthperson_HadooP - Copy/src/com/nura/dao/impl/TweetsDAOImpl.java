/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao.impl;

import com.nura.dao.TweetsDAO;
import com.nura.entity.Tweets;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArunRamya
 */
public class TweetsDAOImpl implements TweetsDAO {

    private Session session;
    private Transaction tx;

    @Override
    public boolean saveTweets(Tweets tweet) {
        boolean saved = false;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(tweet);
            session.flush();
            tx.commit();
            saved = true;
        } catch (HibernateException hbe) {
            hbe.printStackTrace();
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
    public List<Tweets> getOthersTweets(long id) {
        List<Tweets> tweets = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            List<Long> followersList = new FollowDtlsDAOImpl().getFollowersBasedOnMyId(id);
            System.out.println("Followers " + followersList);
            List<String> usrList = new UserDtlsDAOImpl().getUsernameById(followersList);
            System.out.println("Tweets by " + usrList);
            String hql = "From Tweets where tweetedBy in (:tweetedBy)";
            Query query = session.createQuery(hql);
            query.setParameterList("tweetedBy", usrList);
            tweets = query.list();
            System.out.println("Tweets count:=" + tweets.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tweets;
    }

    @Override
    public Tweets getSpecificTweets(long id) {
        Tweets tweets = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            String hql = "From Tweets where id =:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            tweets = (Tweets) query.list().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tweets;
    }

    public java.util.List<Tweets> getSpecificTweetsBasedOnUser(String user) {
        java.util.List<Tweets> tweets = null;
        try {
            session = hibernateutil.HibernateUtil.getSessionFactory().openSession();
            String hql = "From Tweets where tweetedBy =:tweetedBy";
            Query query = session.createQuery(hql);
            query.setParameter("tweetedBy", user);
            tweets = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tweets;
    }

    @Override
    public List<Tweets> getValidTweets() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        TweetsDAOImpl _impl = new TweetsDAOImpl();
        System.out.println(_impl.getOthersTweets(2).get(0).getTweetMsg());
    }
}
