/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peertopeerapplication4;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author khan
 */
public class ServerThread extends Thread{
    private ServerSocket serverSocket;
    private Set<ServerThreadThread> serverThreadThreads=new HashSet<ServerThreadThread>();
    public ServerThread(String portNumb) throws IOException{
        serverSocket= new ServerSocket(Integer.valueOf(portNumb));
        
    }
    public void run(){
        
    }
    public Set<ServerThreadThread> getServerThreadThreads(){
        return serverThreadThreads;
    }
}
