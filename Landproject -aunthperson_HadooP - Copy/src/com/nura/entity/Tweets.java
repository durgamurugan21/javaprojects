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
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author ArunRamya
 */
@Entity
@Table(name = "tweets_t")
public class Tweets implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "tweet_msg")
    private String tweetMsg;
    @Column(name = "tweeted_by")
    private String tweetedBy;
    @Lob
    @Column(name = "tweet_pic")
    private byte[] tweetPic; 
    @Column(name = "pic_msg")
    private String picMsg;
    @Column(name = "allowed")
    private String allowed;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "is_vulgar")
    private String isVulgar = constants.Constants.IS_VULGAR_STATUS[1];
    
    public Tweets(){
        
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the tweetMsg
     */
    public String getTweetMsg() {
        return tweetMsg;
    }

    /**
     * @param tweetMsg the tweetMsg to set
     */
    public void setTweetMsg(String tweetMsg) {
        this.tweetMsg = tweetMsg;
    }

    /**
     * @return the tweetedBy
     */
    public String getTweetedBy() {
        return tweetedBy;
    }

    /**
     * @param tweetedBy the tweetedBy to set
     */
    public void setTweetedBy(String tweetedBy) {
        this.tweetedBy = tweetedBy;
    }

    /**
     * @return the tweetPic
     */
    public byte[] getTweetPic() {
        return tweetPic;
    }

    /**
     * @param tweetPic the tweetPic to set
     */
    public void setTweetPic(byte[] tweetPic) {
        this.tweetPic = tweetPic;
    }

    /**
     * @return the picMsg
     */
    public String getPicMsg() {
        return picMsg;
    }

    /**
     * @param picMsg the picMsg to set
     */
    public void setPicMsg(String picMsg) {
        this.picMsg = picMsg;
    }

    /**
     * @return the allowed
     */
    public String isAllowed() {
        return allowed;
    }

    /**
     * @param allowed the allowed to set
     */
    public void setAllowed(String allowed) {
        this.allowed = allowed;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the isVulgar
     */
    public String getIsVulgar() {
        return isVulgar;
    }

    /**
     * @param isVulgar the isVulgar to set
     */
    public void setIsVulgar(String isVulgar) {
        this.isVulgar = isVulgar;
    }
}
