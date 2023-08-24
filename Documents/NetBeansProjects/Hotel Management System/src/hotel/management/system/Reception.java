/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.management.system;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Kashan
 */
public class Reception {
   
      int option;
      Scanner sc;
    public boolean add_record(){
    
    sc= new Scanner(System.in);
    boolean t=false;
    do{
      try{
        while(option!=0|| option !=1){

           System.out.println("Do You Want To Add Record Of Customer?");
           System.out.println("0: NO");
           System.out.println("1: YES");
           System.out.println("Enter Option:");
           option=sc.nextInt();
           if(option==0){
             return false;
           }
           else if(option==1){
             return true;
           }
           else{
             System.out.println("Wrong Choice!");
           }
        }//while ends
      t=true;
    }//try ends
    catch(InputMismatchException e){
     sc.next();
    System.out.println("Input Mismatched!");
    }
    }while(!t);
       return false;  
    }//Returns true if user select y
    
    public boolean Assign_Room(){
    
    sc= new Scanner(System.in);
    boolean t=false;
    do{
      try{
        while(option!=0|| option !=1){

           System.out.println("Do You Want To Assign Room To The Customer?");
           System.out.println("0: NO");
           System.out.println("1: YES");
           System.out.println("Enter Option:");
           option=sc.nextInt();
           if(option==0){
             return false;
           }
           else if(option==1){
             return true;
           }
           else{
             System.out.println("Wrong Choice!");
           }
        }//while ends
      t=true;
    }//try ends
    catch(InputMismatchException e){
     sc.next();
    System.out.println("Input Mismatched!");
    }
    }while(!t);
       return false;  
    }//Returns true if user select y
    public boolean Show_all_customers(){
    
    sc= new Scanner(System.in);
    boolean t=false;
    do{
      try{
        while(option!=0|| option !=1){

           System.out.println("Do You Want To See List Of All The Customers?");
           System.out.println("0: NO");
           System.out.println("1: YES");
           System.out.println("Enter Option:");
           option=sc.nextInt();
           if(option==0){
             return false;
           }
           else if(option==1){
             return true;
           }
           else{
             System.out.println("Wrong Choice!");
           }
        }//while ends
      t=true;
    }//try ends
    catch(InputMismatchException e){
     sc.next();
    System.out.println("Input Mismatched!");
    }
    }while(!t);
       return false;  
    }
    public boolean update_details(){
    
    sc= new Scanner(System.in);
    boolean t=false;
    do{
      try{
        while(option!=0|| option !=1){

           System.out.println("Do You Want To Update the Details Of The Customers?");
           System.out.println("0: NO");
           System.out.println("1: YES");
           System.out.println("Enter Option:");
           option=sc.nextInt();
           if(option==0){
             return false;
           }
           else if(option==1){
             return true;
           }
           else{
             System.out.println("Wrong Choice!");
           }
        }//while ends
      t=true;
    }//try ends
    catch(InputMismatchException e){
     sc.next();
    System.out.println("Input Mismatched!");
    }
    }while(!t);
       return false;  
    }
    public boolean Show_Booked_room(){
    
    sc= new Scanner(System.in);
    boolean t=false;
    do{
      try{
        while(option!=0|| option !=1){

           System.out.println("Do You Want To See The Booked-Rooms List?");
           System.out.println("0: NO");
           System.out.println("1: YES");
           System.out.println("Enter Option:");
           option=sc.nextInt();
           if(option==0){
             return false;
           }
           else if(option==1){
             return true;
           }
           else{
             System.out.println("Wrong Choice!");
           }
        }//while ends
      t=true;
    }//try ends
    catch(InputMismatchException e){
     sc.next();
    System.out.println("Input Mismatched!");
    }
    }while(!t);
       return false;  
    }
    public boolean Check_balance_of_customer(){
    
    sc= new Scanner(System.in);
    boolean t=false;
    do{
      try{
        while(option!=0|| option !=1){

           System.out.println("Do You Want To Check Balance Of The Customer?");
           System.out.println("0: NO");
           System.out.println("1: YES");
           System.out.println("Enter Option:");
           option=sc.nextInt();
           if(option==0){
             return false;
           }
           else if(option==1){
             return true;
           }
           else{
             System.out.println("Wrong Choice!");
           }
        }//while ends
      t=true;
    }//try ends
    catch(InputMismatchException e){
     sc.next();
    System.out.println("Input Mismatched!");
    }
    }while(!t);
       return false;  
    }
}
