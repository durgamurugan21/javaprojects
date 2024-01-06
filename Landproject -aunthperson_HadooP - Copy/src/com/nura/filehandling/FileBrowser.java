/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.filehandling;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Arun kumar
 */
public class FileBrowser {

    
    public static File selectFile() {
       
        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File file = null;
        int retVal = jf.showOpenDialog(jf);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            file = jf.getSelectedFile();
            System.out.println("File Selected=>" + file.getName());
        }
       
        return file;
    }

    public static File selectFolder() {
        
        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File file = null;
        int retVal = jf.showOpenDialog(jf);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            file = jf.getCurrentDirectory();
            System.out.println("File Selected=>" + file.getName());
        }
        
        return file;
    }
}
