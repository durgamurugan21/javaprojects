/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

/**
 *
 * @author Allen
 */
public class ServerIP {
    public static String type=null;
      public static String aadhaar=null;
    public static void setName(String Name){
        type=Name;
    }
    public static String getName(){
        return type;
    }
    public static void setaano(String aano){
        aadhaar=aano;
    }
    public static String getaano(){
        return aadhaar;
    }
}
