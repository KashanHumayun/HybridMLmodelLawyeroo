/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.javamailutil;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Crown Tech
 */
public class Javamailutil {
    public static void sendMail(String recepient) throws MessagingException{
        System.out.println("Preparing to Send Email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("nail.smtp.port","587");
        String myAccountEmail = "muhammadibrahimsh367@gmail.com";
        String password = "****";
        Session session = Session.getInstance(properties,new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(String Password){
                return new PasswordAuthentication(myAccountEmail,Password);
                
            }
        });
        Message message = prepareMessage(session,myAccountEmail,recepient);
        Transport.send(message);
        System.out.println("Message Sent Successfully");
        
    }

    private static Message prepareMessage(Session session ,String myAccountEmail,String recepient) {
     
        try {
             Message message = new MimeMessage (session);
            //Message message = new MimeMessage(Session);
            message.setFrom(new InternetAddress("myAccountEmail"));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject("E-mail using Java");
            String htmlCode ="<h1>Muhammad ibraheem SapId 24199</h1>";
            message.setContent("htmlCode","text/html");
            //message.setText("Muhammad ibreaheem sent,/n you an Email using Java");
            return message;
                    } catch (Exception ex) {
            Logger.getLogger(Javamailutil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        
        
        
        
        public static void main (String Args[])throws MessagingException{
             Javamailutil l = new Javamailutil();
             l.sendMail("muhammadibrahimsh367@gmail.com");
        
    }
        
    }