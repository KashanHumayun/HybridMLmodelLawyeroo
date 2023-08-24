/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.socketsprogrammingusingjava;

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
public class Server {
    public void main(String[] args) throws IOException{
        ServerSocket ss= new ServerSocket(5000);
        while(true){
        Socket s=ss.accept();
        System.out.println("Connected: "+s.getInetAddress());
        s.getInputStream();
        InputStreamReader sr = new InputStreamReader(s.getInputStream());
        BufferedReader br= new BufferedReader(sr);
        String data= br.readLine();
        System.out.println(data);
        PrintWriter pw= new PrintWriter(s.getOutputStream());
        pw.println("Hello Riphah from Server");
        pw.flush();
        s.close();
        }
    }
}
