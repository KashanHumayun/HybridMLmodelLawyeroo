/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author khan
 */
public class TcpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5000);
        int i=0;
        while(true){
            System.out.println(++i);
            Socket s= ss.accept();
            InputStreamReader sr= new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader (sr);
            String data = br.readLine();
            System.out.println(data);           
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            pw.println("Data from Server");
            pw.flush();
        }
    }
}
