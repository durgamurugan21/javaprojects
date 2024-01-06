/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.dao;

import com.nura.entity.VulgarWords;
import java.util.List;

/**
 *
 * @author ArunRamya
 */
public interface VulgarWordsDAO {
    
    public boolean saveVulgarWords(VulgarWords vulWords);
    public List<VulgarWords> getAllWords();
    
}
