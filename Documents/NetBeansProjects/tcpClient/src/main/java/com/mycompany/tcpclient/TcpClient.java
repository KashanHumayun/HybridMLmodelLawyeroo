/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.tcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author khan
 */
public class TcpClient {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost",5000);
        Scanner sc= new Scanner(System.in);
        int i=0;
        while(1==1){
            System.out.println(++i);
        PrintWriter pw= new PrintWriter(s.getOutputStream());
        String n = sc.nextLine();
        pw.println(n);
        pw.flush();
        System.out.println("Message from Server: ");
        InputStreamReader sr = new InputStreamReader(s.getInputStream());
        BufferedReader br = new BufferedReader(sr);
        String data = br.readLine();
        System.out.println(data);
        }
            
    }
}
