/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import com.nura.entity.FollowDtls;
import java.util.List;

/**
 *
 * @author ArunRamya
 */
public interface FollowDtlsDAO {
    
    public boolean saveFollowerDtls(FollowDtls fDtls);
    public List<Long> getFollowersBasedOnMyId(long id);
    
}
