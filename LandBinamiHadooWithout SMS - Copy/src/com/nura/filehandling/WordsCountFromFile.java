/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.filehandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author ArunRamya
 */
public class WordsCountFromFile {

    public long wordCount(File file) throws FileNotFoundException {
        long count = 0;
        Scanner sc = new Scanner(new FileInputStream(file));
        while (sc.hasNext()) {
            sc.next();
            count++;
        }
        System.out.println("Number of words: " + count);
        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        WordsCountFromFile wc = new WordsCountFromFile();
        System.out.println(wc.wordCount(new File("D:\\tweets - Copy.csv")));
    }
}
