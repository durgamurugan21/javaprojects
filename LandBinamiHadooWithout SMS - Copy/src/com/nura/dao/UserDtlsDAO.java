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
public interface UserDtlsDAO {

    public boolean saveUserDtls(com.nura.entity.UserDetails userDtls);
    public com.nura.entity.UserDetails getUserDetails(String userName, String password);
    public List<com.nura.entity.UserDetails> getUserListOtherThanMe(String userName);
    public String getUsername(long id);
    public List<String> getUsernameById(List<Long> id);
}
