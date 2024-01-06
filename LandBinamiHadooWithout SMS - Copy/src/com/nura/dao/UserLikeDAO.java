/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import java.util.List;

/**
 *
 * @author ArunRamya
 */
public interface UserLikeDAO {
    
    public boolean saveUserLike(com.nura.entity.UserLikes userLike);
    public List<com.nura.entity.UserLikes> userLikeList();
}
