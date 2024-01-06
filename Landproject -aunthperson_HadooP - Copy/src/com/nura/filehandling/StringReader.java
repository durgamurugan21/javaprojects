/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.filehandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arun kumar
 */
public class StringReader {

    public static List<String> readFile(File f) {
        List<String> getLines = new ArrayList<String>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(f));
            while ((line = br.readLine()) != null) {
                getLines.add(line);
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StringReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StringReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(StringReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //System.out.println("Content read:-" + contents);
        return getLines;
    }
    
    public static void main(String[] args) {
        File file = new File("D:\\temp\\result.csv");
        System.out.println(StringReader.readFile(file));
    }
}
