/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.mavenproject4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author khan
 */
public class Mavenproject4 {

    public static void main(String[] args) throws IOException {
         Socket s=new Socket("localhost",5000);
        PrintWriter pw= new PrintWriter(s.getOutputStream());
        pw.println("Hello Riphah from KASHAN to server");
        pw.flush();
        
        
        InputStreamReader sr = new InputStreamReader(s.getInputStream());
        BufferedReader br= new BufferedReader(sr);
        String data= br.readLine();
        System.out.println(data);
        s.close();
    }
}
