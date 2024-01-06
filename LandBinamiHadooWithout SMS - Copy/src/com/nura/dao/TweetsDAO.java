/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import com.nura.entity.Tweets;
import java.util.List;

/**
 *
 * @author ArunRamya
 */
public interface TweetsDAO {
    
    public boolean saveTweets(Tweets tweet);
    public List<Tweets> getValidTweets();
    public List<Tweets> getOthersTweets(long id);
    public Tweets getSpecificTweets(long id);
    
}
