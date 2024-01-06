/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.entity;

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
@Table(name = "user_likes_on_t")
public class UserLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long seq;
    @Column
    private String userId;
    @Column
    private String userLike;
    

    public UserLikes() {

    }

    /**
     * @return the seq
     */
    public long getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(long seq) {
        this.seq = seq;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the userLike
     */
    public String getUserLike() {
        return userLike;
    }

    /**
     * @param userLike the userLike to set
     */
    public void setUserLike(String userLike) {
        this.userLike = userLike;
    }
}
