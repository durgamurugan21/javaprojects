/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import com.nura.entity.JSONEntity;

/**
 *
 * @author ArunRamya
 */
public interface JSONEntityDAO {
    
    public boolean deleteAllRows();
    public boolean saveDetailss(JSONEntity jsonEntity);
    public JSONEntity getCat(String userName);
    
}
