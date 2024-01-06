/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.keygenerators;

/**
 *
 * @author Arun kumar
// */
import java.util.Random;


public final class RandomInteger {
  
  public static final String main(int len){
    int keySize = len;
    String key="";
    Random randomGenerator = new Random();
    for (int idx = 1; idx <= keySize; ++idx){
      int randomInt = randomGenerator.nextInt(10);  
      key=key+randomInt;
    }
      System.out.println(""+key);
    return key;
  }
  
}