/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package peertopeerapplication4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author khan
 */
public class PeerToPeerApplication4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("> enter username & port # for this peer:");
        String[] setupValues = bufferedReader.readLine().split("");
        ServerThread serverThread = new ServerThread(setupValues[1]);
        serverThread.start();
        new Peer().updateListonToPeers(bufferedReader, serupValues[0],serverThread);
    }
    public void updateListenToPeers(BufferedReader bufferedReader,String username, ServerThread serverThread) throws Exception{
        
    }
}
