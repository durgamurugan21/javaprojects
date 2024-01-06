/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.entity;

import java.io.Serializable;
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
@Table(name = "follow_dtls")
public class FollowDtls implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id_of_follower")
    private long followerId;
    @Column(name = "following_user_id")
    private long followingId;
    
    public FollowDtls(){
        
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the followerId
     */
    public long getFollowerId() {
        return followerId;
    }

    /**
     * @param followerId the followerId to set
     */
    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }

    /**
     * @return the followingId
     */
    public long getFollowingId() {
        return followingId;
    }

    /**
     * @param followingId the followingId to set
     */
    public void setFollowingId(long followingId) {
        this.followingId = followingId;
    }
}
