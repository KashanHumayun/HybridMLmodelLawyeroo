/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.udpclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author khan
 */
public class UdpClient {

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket cs = new DatagramSocket();
        InetAddress ip = (InetAddress) InetAddress.getByName("localhost");
        byte[] data = new byte[1024];
        String yourmsg = "Hello Server Im kashan";
        data= yourmsg.getBytes();
        DatagramPacket pkt = new DatagramPacket(data,data.length,ip,5000);
        cs.send(pkt);
        cs.close();
    }
}
