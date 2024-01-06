/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.entity;

/**
 *

 */
public class UserEntity {

    public UserEntity() {
    }

    /**
     * @return the fileid
     */
    public static String getDocNo() {
        return DocNo;
    }

    /**
     * @param fileid the fileid to set
     */
    public static void setDocNo(String DocNo) {
        UserEntity.DocNo = DocNo;
    }
    
     public static String getAadhaarno() {
        return Aadhaarno;
    }

    /**
     * @param fileid the fileid to set
     */
    public static void setAadhaarno(String Aadhaarno) {
        UserEntity.Aadhaarno = Aadhaarno;
    }
    public static String getLegalCertificate() {
        return LegalCertificate;
    }

    /**
     * @param fileid the fileid to set
     */
    public static void setLegalCertificate(String LegalCertificate) {
        UserEntity.LegalCertificate = LegalCertificate;
    }
    
        public static int getamount() {
        return amount;
    }

    /**
     * @param fileid the fileid to set
     */
    public static void setamount(int amount) {
        UserEntity.amount = amount;
    }
    
    
    
        public static String getFromAadhaar() {
        return FromAadhaar;
    }

    /**
     * @param fileid the fileid to set
     */
    public static void setFromAadhaar(String FromAadhaar) {
        UserEntity.FromAadhaar = FromAadhaar;
    }
        public static String getName() {
        return Name;
    }

    /**
     * @param fileid the fileid to set
     */
    public static void setName(String Name) {
        UserEntity.Name = Name;
    }
   
    

     
    /**
     * @return the fileName
     */
  

    //Variable declaration
       public static String DocNo=null; 
       public static String Aadhaarno=null;
       public static String LegalCertificate=null;
       public static  int amount=0;       
       public static  String FromAadhaar=null;
       public static  String Name=null;
      
       
       
   

}
