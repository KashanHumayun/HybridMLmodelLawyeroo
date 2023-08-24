/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.udpserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author khan
 */
public class UdpServer {

    public static void main(String[] args) throws SocketException, IOException {
        DatagramSocket ss = new DatagramSocket(5000);
        byte[]data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data,data.length);
        ss.receive(packet);
        InetAddress ip = packet.getAddress();
        String st = new String(data,0,data.length);
        System.out.println(st);
    }
}
