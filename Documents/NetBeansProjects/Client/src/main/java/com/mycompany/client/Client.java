package com.mycompany.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author khan
 */
public class Client {

     public void main(String[] args) throws IOException{
        Socket s=new Socket("localhost",5000);
        PrintWriter pw= new PrintWriter(s.getOutputStream());
        pw.println("Hello Riphah from client to server");
        pw.flush();
        
        
        InputStreamReader sr = new InputStreamReader(s.getInputStream());
        BufferedReader br= new BufferedReader(sr);
        String data= br.readLine();
        System.out.println(data);
        s.close();
    }
}
