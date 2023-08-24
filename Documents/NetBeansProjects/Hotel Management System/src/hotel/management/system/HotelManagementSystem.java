/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.management.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Kashan
 */
public class HotelManagementSystem {

       
        public static void format(){
        
            System.out.println(" \t\t \t\t1:Add Customer");
            System.out.println("\t\t \t\t2:Show All Customers");
            System.out.println("\t\t \t\t3:Update Details of Customer");
            System.out.println("\t\t \t\t4:Assign Room");
            System.out.println("\t\t \t\t5:Show Booking Details");
            System.out.println("\t\t \t\t6:Show Balance Details");
            System.out.println("\t\t \t\t7:Add Admin");
            System.out.println("\t\t \t\t0:Exit");
        }
        public static void reception_working() {//All Working Of Reception Module
        {
        // TODO code application logic here
        Admin a=new Admin();
        Reception Recp= new Reception();
        Customer c= new Customer();
        Rooms r= new Rooms();
        Payment p= new Payment();
        int option=0;
      /*  
        }*/
      Scanner sc = new Scanner(System.in);
        
        boolean t=false;
        while(t!=true){
            format();
            t=false;
            try{
                int op=0;
                System.out.println("\t\t \t\tEnter Option:");
                op=sc.nextInt();
                while(op!=0){
                    switch(op){
                        case 1:
                            if(Recp.add_record()==true){
                                    c.Enter_details();
                                    System.out.println("\t\t \t\tCustomer Added!");
                                }
                         break;
                         
                         case 2:
                             if(Recp.Show_all_customers()==true){
                                    c.Show_Details();
                                    System.out.println("\t\t \t\tDetails Showed!");
                             }
                         break;
                         case 3:
                             if(Recp.update_details()==true){
                                    c.Update_details();
                                    System.out.println("\t\t \t\tDetails Updated!");
                             }
                         break;
                         case 4:
                            if(Recp.Assign_Room()==true){
                                 r.Show_not_Booked_Rooms();
                                 if(c.Find_Customer()==true){
        
                                     r.Book_rooms(c.getIdsearch());
                                     System.out.println("\t\t \t\tRoom Booked!");
                                } 
                            }
                            break;
                        case 5:
                            if(Recp.Show_Booked_room()==true){
                                r.Show_Booked_Rooms();
                                  
                            }
                            break;
                        case 6:
                            if(Recp.Check_balance_of_customer()==true){
             
                                p.payment_checker();
                            }
                         break;
                         case 7:
                            a.Enter_details();
                         break;
                         default:{
                              System.out.println("\t\t \t\tWrong Option!");
                            }
                    }
                     format();
                     System.out.println("\t\t \t\tEnter Option:");
                     op=sc.nextInt();
                }//while
                t=true;
             }    //try
             catch(InputMismatchException e){
                sc.next();
                System.out.println("\t\t \t\tInput Mismatched!");
                System.out.println("\t\t \t\tTry Again!");
             }
            catch(FileNotFoundException e){
               
                System.out.println("\t\t \t\tFile Not Found!");
               
             }
            catch(IOException e){

                System.out.println("\t\t \t\tInput & Output Exception Occured!");
               
             }
        }
        
        
    }
        }
        
         public static void adminformat(){
        
            System.out.println("\t\t \t\t1:Add New Admin");
            System.out.println("\t\t \t\t2:Show Admin");
            System.out.println("\t\t \t\t3:Update Details of Admin");
            System.out.println("\t\t \t\t4:Remove Customer");
            System.out.println("\t\t \t\t5:Add New Rooms");
            System.out.println("\t\t \t\t6:Show Hotel Data");
            System.out.println("\t\t \t\t7:Change Hotel Data");
            System.out.println("\t\t \t\t0:Exit");
        }
        public static void Admin_Working() throws IOException{
             Admin a=new Admin();
             Rooms r=new Rooms();
             Hotel h=new Hotel(a);
              int option=0;
              boolean t=false;
              Scanner sc= new Scanner(System.in);
              while(!t){
                  adminformat();
                try{
                    System.out.println("\t\t \t\tEnter Option:");
                    option=sc.nextInt();
                    while(option!=0){
                        switch(option){
                                case 1:
                                    a.Enter_details();
                                    break;
                                case 2:
                                    a.Show_Details();
                                    break;
                                case 3:
                                    a.Update_details();
                                    break; 
                                case 4:
                                    a.Remove_Customer();
                                    break;
                                case 5:
                                    r.enter_data();
                                    break;
                                case 6:
                                    h.Show_data();
                                    break; 
                                case 7:
                                    System.out.println("\t\t \t\tEnter New Hotel Data: ");
                                    h.enter_hotel_data();
                                    break;
                                default:
                                    System.out.println("\t\t \t\tPlease Enter between 0-7");
                            }
                        adminformat();
                        System.out.println("\t\t \t\tEnter Option:");
                        option=sc.nextInt();
                    }           
                    t=true;
                }
                catch(InputMismatchException e){
                    sc.next();
                    System.out.println("\t\t \t\tInput Is Not Matched...");
                    System.out.println("\t\t \t\tTry Again...");
                }
              }
        }
        public static boolean Admin_login(){
        Admin a= new Admin();
        boolean t=false;
        Scanner sc=new Scanner(System.in);
        if(a.Search_Admin()==true){
           String pass;
           try{
           System.out.println("\t\t \t\tEnter Password: ");
           pass=sc.nextLine();
           if(pass.matches(a.getAdminPasscode()))
               t=true;
           else
               System.out.println("\t\t \t\tWrong Password...");
           }
           catch(InputMismatchException e){
           sc.next();
           System.out.println("\t\t \t\tInput Mismatches...");
           }
        }
        
        return t;
        }
        public static void Hotel_name() throws FileNotFoundException, IOException{
          File hotel= new File("hotel.txt");
          hotel.createNewFile();
          int idtemp;
          String Nametemp;
          String Addresstemp;
          int total_room_temp;
          int total_admin_temp;
          Scanner Reader= new Scanner(hotel);
          idtemp=Reader.nextInt();
          Nametemp=Reader.next();
          Addresstemp=Reader.next();
          total_room_temp=Reader.nextInt();
          total_admin_temp=Reader.nextInt();
          Reader.close();
          System.out.println("\t\t,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\t\t");
          System.out.println("\t\t,,,,,,,,Welcome To The "+Nametemp+" Hotel,,,,,,\t\t");
        }
        
        
        public static void hotelmanagement() throws IOException{
            int op;
            boolean t=false;
            Scanner sc=new Scanner(System.in);
        while(!t){
            System.out.println("\t\t \t\t0:Exit");
            System.out.println("\t\t \t\t1:Receptoin");
            System.out.println("\t\t \t\t2:Admin");
                try{
                   System.out.println("\t\t \t\tEnter Option:");
                   op=sc.nextInt();
                   sc.nextLine();
                   while(op!=0){
                         switch(op){
                             case 1:
                                 reception_working();
                              break;
                             case 2:
                                     if(Admin_login()==true)
                                         {System.out.println("\t\t \t\tWelcome....");
                                          Admin_Working();}
                                     else
                                       System.out.println("\t\t \t\tYou Cannot Enter...");
                                 break;
                             default:
                                 System.out.println("\t\t \t\tPlease Select Between 0-3...");
                                 
                          }
                         System.out.println("\t\t \t\t0:Exit");
                         System.out.println("\t\t \t\t1:Receptoin");
                         System.out.println("\t\t \t\t2:Admin");
                         System.out.println("\t\t \t\tEnter Option:");
                         op=sc.nextInt();
                   }
                 t=true;
                }
                catch(InputMismatchException e){
                  sc.next();
                  System.out.println("\t\t \t\tInput Mismatched Exception!");
                }
        }
        
      }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       
       Hotel_name();//Shows The Name Of The Hotel  
      hotelmanagement();//All Working In This Function
}
}