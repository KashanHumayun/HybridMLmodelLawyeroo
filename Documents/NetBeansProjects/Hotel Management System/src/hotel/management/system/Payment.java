/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.management.system;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Kashan
 */
public class Payment {
    double pay=0.0;
    Scanner sc;
    long id;
    String filename="Payement.txt";
    public void payment_getter(){
        double p;
        boolean t=false;
        sc = new Scanner(System.in);
        while(t!=true){
            try
              {
                System.out.println("\nEnter Bank Balance: ");
                p=sc.nextDouble();
                if(p>50000){
                    setPay(p);
                    File pay= new File(filename);
                    FileWriter fw= new FileWriter(pay,true);
                    fw.write(getId()+" "+getPay()+" ");
                    fw.close();
                t=true;}
            }
        
            catch(IOException e){
                System.out.println("File Not Found!");
            }
            catch(InputMismatchException e){
                sc.next();
                System.out.println("Wrong Data Entered!");
                System.out.println("Please Try Again!");
            }
       }//While Loop Ends
    }//Gets Account Balance From the User
     public void payment_getter(double p){
        
        boolean t=false;
        sc = new Scanner(System.in);
        while(t!=true){
            try
              {
                  setPay(p);
                if(p>50000){
                    
                    File pay= new File(filename);
                    FileWriter fw= new FileWriter(pay,true);
                    fw.write(getId()+" "+getPay()+" ");
                    fw.close();
                }
                t=true;
            }
        
            catch(IOException e){
                JOptionPane.showMessageDialog(null, e);
            }
            catch(InputMismatchException e){
                JOptionPane.showMessageDialog(null, e);
            }
       }//While Loop Ends
    }//Using this Funtion for GUI
//Gets Account Balance From the User
    
    public void payment_checker(){
    
        double p;
        boolean t=false;//for the termination of the loop
        sc = new Scanner(System.in);
        while(t!=true){
            try
            {
                System.out.println("\nEnter Id : ");
                p=sc.nextDouble();
                int idtemp;
                double payment=0.0;
                boolean g=false;//id finder
                File pay= new File(filename);
                Scanner fs= new Scanner(pay);        
                while(fs.hasNext()){
                    idtemp=fs.nextInt();
                    payment=fs.nextDouble();
                    if(idtemp==p)
                    {
                        System.out.println("id : "+idtemp);
                        System.out.println("Balance : "+payment);
                        g=true;
                    }
                }//while has next ends here
                fs.close();
                if(g==false){System.out.println("Id Not Found!");}
                t=true;
            }        
            catch(IOException e){
                System.out.println("File Not Found!");
            }
            catch(InputMismatchException e){
                sc.next();
                System.out.println("Wrong Data Entered!");
                System.out.println("Please Try Again!");
            }
        }    
    
    }//Checks the Account Balance of the User
     public void payment_checker_Gui(int p){
    
        sc = new Scanner(System.in);
       
            try
            {
                
                int idtemp;
                double payment=0.0;
                boolean g=false;//id finder
                File pay= new File(filename);
                Scanner fs= new Scanner(pay);        
                while(fs.hasNext()){
                    idtemp=fs.nextInt();
                    payment=fs.nextDouble();
                    if(idtemp==p)
                    {
                        setPay(payment);
                        g=true;
                    }
                }//while has next ends here
                fs.close();
                if(g==false){JOptionPane.showMessageDialog(null,"Id Not Found!");}
                
            }        
            catch(IOException e){
                JOptionPane.showMessageDialog(null,"File Not Found!");
            }
            
    }//Checks the Account Balance of the User
    public boolean status(){
        if(getPay()>=50000)
            return true;
        return false;
    }//Returns True if the status is greater then 50k
    public Payment() {
    }
    
    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}
