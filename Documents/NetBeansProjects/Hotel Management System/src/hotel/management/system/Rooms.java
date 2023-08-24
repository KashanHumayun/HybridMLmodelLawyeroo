/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.management.system;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Kashan
 */
public class Rooms {
   int Room_no;
   String Type;
   double price;
   double capacity;
   String Status;
   int cusid;
   int Days;
   Scanner sc;
   String filename="Rooms.txt";/////File Location And Name

    public Rooms(int Room_no, String Type, double capacity) {
        this.Room_no = Room_no;
        this.Type = Type;
        this.capacity = capacity;
    }
   
  public void enter_data(){
      int cusid=0;
      int Room_no;
      String Type="";
      double price=0;
      double capacity;
      String Status="Not-Booked";
      int Days=0;
      setDays(Days);
      sc= new Scanner(System.in);
      boolean e=false;//to control loop
      try{
          System.out.println("Enter Room No:");
          Room_no=sc.nextInt();
          sc.nextLine();
          System.out.println("Type:");
          System.out.println("1:Standard");
          System.out.println("2:Premium");
          int op;//to get the option
          op=sc.nextInt();
          sc.nextLine();
          boolean t= false;
          while(t!=true){
              if(op==1)
                  {Type="Standard";
                   price=1500;
                   t=true;}
              else if(op==2)
                  {Type="Premium";t=true;
                  price=5000;
                    }
              else
                  System.out.println("Enter Again:");}
      
              System.out.println("Enter number of beds:");
              capacity= sc.nextDouble();
              setRoom_no(Room_no);
              setType(Type); 
              setPrice(price);
              setCapacity(capacity);
              setStatus(Status);
              setCusid(cusid);
              File myObj = new File(filename);
              if(myObj.createNewFile())
                  System.out.println("File Created!");
              FileWriter myWriter = new FileWriter(myObj,true);
              myWriter.write(getRoom_no()+" "+getType()+" "+getCapacity()+" "+getPrice()+" "+getStatus()+" "+getDays()+" "+getCusid()+" "+"\n"+"\n");
              myWriter.close();
              System.out.println("YOUR DETAILS :");
              System.out.println("Room no :"+getRoom_no());
              System.out.println("Type :"+getType());
              System.out.println("Capacity :"+getCapacity());
              System.out.println("Price :"+getPrice());
              System.out.println("Status :"+getStatus());
              System.out.println("Days :"+getDays());
              System.out.println("Customer Id :"+getDays());
              System.out.println("Data Saved Successfully ");
        }
      catch(InputMismatchException r){
              System.out.println("Input Is Mismatched!");
          }
      catch(IOException r){
              System.out.println("Input Is Mismatched!");
         }
  }
  //////////
  public void Save_data_GUI(){
      int cusid=0;
      int Room_no;
      String Type="";
      double price=0;
      double capacity;
      String Status="Not-Booked";
      int Days=0;
      setDays(Days);
      sc= new Scanner(System.in);
      boolean e=false;//to control loop
      try{
          
              
              if(getType().matches("Standard"))
                  {
                   price=1500;
                   }
              else if(getType().matches("Premium"))
                  {
                  price=5000;
                    }
              setPrice(price);
              setStatus(Status);
              setCusid(cusid);
              File myObj = new File(filename);
              if(myObj.createNewFile())
                  JOptionPane.showMessageDialog(null,"File Created!");
              FileWriter myWriter = new FileWriter(myObj,true);
              myWriter.write(getRoom_no()+" "+getType()+" "+getCapacity()+" "+getPrice()+" "+getStatus()+" "+getDays()+" "+getCusid()+" "+"\n"+"\n");
              myWriter.close();
              JOptionPane.showMessageDialog(null,"Room DETAILS :"+"Room no :"+getRoom_no()+
              "Type :"+getType()+"Capacity :"+getCapacity()+
              "Price :"+getPrice()+"Status :"+getStatus());
              JOptionPane.showMessageDialog(null,"Data Saved Successfully ");
        }
      catch(InputMismatchException r){
              JOptionPane.showMessageDialog(null,r);
          }
      catch(IOException r){
              JOptionPane.showMessageDialog(null,r);
         }
  }
  
  public void Book_rooms(int cusid){//The function basically transfer all data to a temporary file
    sc = new Scanner(System.in);//except the required data and than update the required data
           //And rename the temporary File with Customer file
 //for looping the try and catch block
         boolean t=false;//Returns True if Id is found
     try {
     
       System.out.println("0: Return !!!");
       System.out.println("1: Book Room  !!!");
       int op;
       op=sc.nextInt();
      
       int day;
       while(op!=0){
          if(op==1)
            {
            File cus = new File(filename);
             //File writer
            Scanner Reader = new Scanner(cus);
            File temp = new File("Temporary3.txt");
            FileWriter tempWriter = new FileWriter(temp);
            System.out.println("Please Enter Room No :");
            int idd;
            idd=sc.nextInt();
            sc.nextLine();
            while(Reader.hasNext()){
                String nametemp;
                int Room_notemp;
                String Typetemp="";
                double capacitytemp;
                double pricetemp;
                int    daystemp;
                String Statustemp;
                int cusidtemp;
                Room_notemp=Reader.nextInt();
                Typetemp=Reader.next();
               capacitytemp=Reader.nextDouble();
                pricetemp=Reader.nextDouble();
                Statustemp=Reader.next();
                daystemp=Reader.nextInt();
                cusidtemp=Reader.nextInt();
               if(Room_notemp==idd&&!Statustemp.matches("Booked")){
           
                    System.out.println("Enter Number of days!!!");
                    day=sc.nextInt();
                    setDays(day);
                    setStatus("Booked");
                    setRoom_no(Room_notemp);
                    setType(Typetemp);
                    setPrice(pricetemp);
                    setCapacity(capacitytemp);
                    Payment_calculator(day,pricetemp,cusid);
                    setCusid(cusid);
                    tempWriter.write(getRoom_no()+" "+getType()+" "+getCapacity()+" "+getPrice()+" "+getStatus()+" "+getDays()+" "+getCusid()+" "+"\n"+"\n");
                    t=true;
                    System.out.println("Data Saved Successfully!");
                }
                else if(Room_notemp==idd&& Statustemp.matches("Booked"))
                {
                    t=true;
                    System.out.println("Sorry Room Already Booked...");
                    tempWriter.write(Room_notemp+" "+Typetemp+" "+capacitytemp+" "+pricetemp+" "+Statustemp+" "+daystemp+" "+cusidtemp+" "+"\n"+"\n");
                }      
                else
                {
            
                    tempWriter.write(Room_notemp+" "+Typetemp+" "+capacitytemp+" "+pricetemp+" "+Statustemp+" "+daystemp+" "+cusidtemp+" "+"\n"+"\n");
                }  
        
            }
           tempWriter.close();
           Reader.close();
           cus.delete();
           temp.renameTo(cus);
       
            if(t==false){
                System.out.println("Id Not Found!");
            }
        
        }//if ends
       
      
        
       System.out.println("Enter Option:");
       op=sc.nextInt();
       sc.nextLine();
       }
     }
       catch (IOException o) //Catches Exception
         {
           System.out.println("An error occurred.");
            o.printStackTrace();
          }
       catch(InputMismatchException q){
           System.out.println("Input Not Matched!!!");
          }
     
   }
   public void Book_rooms(int cusid,int idd,int day){//The function basically transfer all data to a temporary file
    sc = new Scanner(System.in);//except the required data and than update the required data
           //And rename the temporary File with Customer file
 //for looping the try and catch block
         boolean t=false;//Returns True if Id is found
     try {
      
            File cus = new File(filename);
             //File writer
            Scanner Reader = new Scanner(cus);
            File temp = new File("Temporary3.txt");
            FileWriter tempWriter = new FileWriter(temp);
           
            while(Reader.hasNext()){
                String nametemp;
                int Room_notemp;
                String Typetemp="";
                double capacitytemp;
                double pricetemp;
                int    daystemp;
                String Statustemp;
                int cusidtemp;
                Room_notemp=Reader.nextInt();
                Typetemp=Reader.next();
               capacitytemp=Reader.nextDouble();
                pricetemp=Reader.nextDouble();
                Statustemp=Reader.next();
                daystemp=Reader.nextInt();
                cusidtemp=Reader.nextInt();
               if(Room_notemp==idd&&!Statustemp.matches("Booked")){
                    setDays(day);
                    setStatus("Booked");
                    setRoom_no(Room_notemp);
                    setType(Typetemp);
                    setPrice(pricetemp);
                    setCapacity(capacitytemp);
                    Payment_calculator_Gui(day,pricetemp,cusid);
                    setCusid(cusid);
                    tempWriter.write(getRoom_no()+" "+getType()+" "+getCapacity()+" "+getPrice()+" "+getStatus()+" "+getDays()+" "+getCusid()+" "+"\n"+"\n");
                    t=true;
                    JOptionPane.showMessageDialog(null,"Data Saved Successfully!");
                }
                else if(Room_notemp==idd&& Statustemp.matches("Booked"))
                {
                    t=true;
                    JOptionPane.showMessageDialog(null,"Sorry Room Already Booked...");
                    tempWriter.write(Room_notemp+" "+Typetemp+" "+capacitytemp+" "+pricetemp+" "+Statustemp+" "+daystemp+" "+cusidtemp+" "+"\n"+"\n");
                }      
                else
                {
            
                    tempWriter.write(Room_notemp+" "+Typetemp+" "+capacitytemp+" "+pricetemp+" "+Statustemp+" "+daystemp+" "+cusidtemp+" "+"\n"+"\n");
                }  
        
            }
           tempWriter.close();
           Reader.close();
           cus.delete();
           temp.renameTo(cus);
       
            if(t==false){
                JOptionPane.showMessageDialog(null,"Id Not Found!");
            }
        
     }
       catch (IOException o) //Catches Exception
         {
           JOptionPane.showMessageDialog(null,"An error occurred.");
            o.printStackTrace();
          }
       catch(InputMismatchException q){
           JOptionPane.showMessageDialog(null,"Input Not Matched!!!");
          }
     
   }//Using this for GUI
  
   public void Show_Booked_Rooms(){
      /////////
      sc= new Scanner(System.in);
          boolean w= false;
      try{
          File cus = new File(filename);
          Scanner Reader = new Scanner(cus);
          System.out.println("0: Return !!!");
           System.out.println("1: Show Booked Rooms  !!!");
           int op;
       
           op=sc.nextInt();
           sc.nextLine();
           while(op!=0){
                if(op==1){
                     System.out.println("Room_no"+"  "+"Type"+ "  "+"Capacity"+"  "+"Price"+"  "+"Status"+"  "+"Days"+"  "+"Customer_ID"+"\n\n");
      
                     while(Reader.hasNext()){
                               int Room_notemp;
                               String Typetemp="";
  
                               double capacitytemp;
                               double pricetemp;
                               int daystemp;
                               String Statustemp;
                               int cusidtemp;
                               Room_notemp=Reader.nextInt();
                               Typetemp=Reader.next();
                               capacitytemp=Reader.nextDouble();
                               pricetemp=Reader.nextDouble();
                               Statustemp=Reader.next();
                               daystemp=Reader.nextInt();
                               cusidtemp=Reader.nextInt();
                               if(Statustemp.matches("Booked"))
                                        System.out.println(Room_notemp+" "+Typetemp+" "+capacitytemp+" "+pricetemp+" "+Statustemp+" "+daystemp+" "+cusidtemp+"\n"+"\n");
                                 }
        
                                System.out.println("Enter Option: ");
                                op=sc.nextInt();}
                     else
                    {
                    sc.nextLine();
                    System.out.println(" Enter correct option!!!");
                    op=sc.nextInt(); }
                    }
         Reader.close();
      }
      catch(InputMismatchException q){
          sc.next();
          System.out.println("Input Mismatch! Try Again");
      }
      catch(IOException q){
          System.out.println("File not Found!");
      }
      //////////
  }
  
   public void Show_not_Booked_Rooms(){
   
        /////////
      sc= new Scanner(System.in);
      boolean w= false;
      try{
            File cus = new File(filename);
            Scanner Reader = new Scanner(cus);
            System.out.println("0: Return !!!");
            System.out.println("1: Show Not-Booked Rooms  !!!");
            int op;
       
            op=sc.nextInt();
            sc.nextLine();
            while(op!=0){
                if(op==1){
                        System.out.println("Room_no"+"  "+"Type"+ "  "+"Capacity"+"  "+"Price"+"  "+"Status"+"  "+"Days"+"  "+"Customer_ID"+"\n\n");
      
                        while(Reader.hasNext()){
       
                             int Room_notemp;
                             String Typetemp="";
  
                             double capacitytemp;
                             double pricetemp;
                             int daystemp;
                             String Statustemp;
                             int cusidtemp;
                             Room_notemp=Reader.nextInt();
                             Typetemp=Reader.next();
                             capacitytemp=Reader.nextDouble();
                             pricetemp=Reader.nextDouble();
                             Statustemp=Reader.next();
                             daystemp=Reader.nextInt();
                             cusidtemp=Reader.nextInt();
                             if(Statustemp.matches("Not-Booked"))
        
                                      System.out.println(Room_notemp+"  "+Typetemp+"  "+capacitytemp+"  "+pricetemp+"  "+Statustemp+"  "+daystemp+" "+cusidtemp+"\n"+"\n");
                              }
        
                             System.out.println("Enter Option: ");
                             op=sc.nextInt();}
                else{
                        sc.nextLine();
                        System.out.println(" Enter correct option!!!");
                        op=sc.nextInt(); }
                   }
            Reader.close();
        }
      catch(InputMismatchException q){
          sc.next();
          System.out.println("Input Mismatch! Try Again");
      }
      catch(IOException q){
          System.out.println("File not Found!");
      }
      //////////
   
   }
  
    public ArrayList<String> Show_not_Booked_Rooms_Gui(){
        ArrayList<String> roomid=new ArrayList();
        int i=0;
        /////////
      sc= new Scanner(System.in);
      boolean w= false;
      try{
            File cus = new File(filename);
            Scanner Reader = new Scanner(cus);
                        
                        while(Reader.hasNext()){
       
                             int Room_notemp;
                             String Typetemp="";
  
                             double capacitytemp;
                             double pricetemp;
                             int daystemp;
                             String Statustemp;
                             int cusidtemp;
                             Room_notemp=Reader.nextInt();
                             Typetemp=Reader.next();
                             capacitytemp=Reader.nextDouble();
                             pricetemp=Reader.nextDouble();
                             Statustemp=Reader.next();
                             daystemp=Reader.nextInt();
                             cusidtemp=Reader.nextInt();
                             if(Statustemp.matches("Not-Booked"))
                             {
                                      
                                      roomid.add(Room_notemp+","+Typetemp);
                                      i++;
                             }   
                        }
        
                             
               
            Reader.close();
       }
      catch(InputMismatchException q){
          
          System.out.println("Input Mismatch! Try Again");
      }
      catch(IOException q){
          System.out.println("File not Found!");
      }
      //////////
    return roomid;
   }
  
   
   public int total_rooms(){
   
      /////////
     
      int total_rom=0;
      try{
             File cus = new File(filename);
             Scanner Reader = new Scanner(cus);
      
             while(Reader.hasNext()){
                int Room_notemp;
                String Typetemp="";
  
                double capacitytemp;
                double pricetemp;
                int daystemp;
                String Statustemp;
                int cusidtemp;
                Room_notemp=Reader.nextInt();
                Typetemp=Reader.next();
                capacitytemp=Reader.nextDouble();
                pricetemp=Reader.nextDouble();
                Statustemp=Reader.next();
                daystemp=Reader.nextInt();
                cusidtemp=Reader.nextInt();
                total_rom=total_rom+1;
                }
        
            Reader.close();
       }
    catch(InputMismatchException q){
          sc.next();
          System.out.println("Input Mismatch! Try Again");
      }
    catch(IOException q){
      System.out.println("File not Found!");
      }
      //////////
   return total_rom;
   }
   
   public void Show_Rooms(){
      /////////
      sc= new Scanner(System.in);
          boolean w= false;
      try{
          File cus = new File(filename);
          Scanner Reader = new Scanner(cus);
          System.out.println("0: Return !!!");
          System.out.println("1: Show Rooms  !!!");
          int op;
       
          op=sc.nextInt();
          sc.nextLine();
          while(op!=0){
            if(op==1){
                System.out.println("Room_no"+"  "+"Type"+ "  "+"Capacity"+"  "+"Price"+"  "+"Status"+"  "+"Days"+"  "+"Customer_ID"+"\n\n");
      
               while(Reader.hasNext()){
                       int Room_notemp;
                       String Typetemp="";
                       double capacitytemp;
                       double pricetemp;
                       int daystemp;
                       String Statustemp;
                       int cusidtemp;
                       Room_notemp=Reader.nextInt();
                       Typetemp=Reader.next();
                       capacitytemp=Reader.nextDouble();
                       pricetemp=Reader.nextDouble();
                       Statustemp=Reader.next();
                       daystemp=Reader.nextInt();
                       cusidtemp=Reader.nextInt();
                       System.out.println(Room_notemp+"  "+Typetemp+"  "+capacitytemp+"  "+pricetemp+"  "+Statustemp+"  "+daystemp+" "+cusidtemp+"\n"+"\n");
                  }

            System.out.println("Enter Option: ");
            op=sc.nextInt();}
          else
            {
            sc.nextLine();
            System.out.println(" Enter correct option!!!");
            op=sc.nextInt(); }
         }
        Reader.close();
      }
      catch(InputMismatchException q){
          sc.next();
          System.out.println("Input Mismatch! Try Again");
      }
      catch(IOException q){
          System.out.println("File not Found!");
      }
      //////////
  }
   
   public void Payment_calculator(int days,double price,int idd){
   //////////////////
      double bill=days*price;
      sc = new Scanner(System.in);//except the required data and than update the required data

      boolean w=false;//checking if id found or not
      boolean t=false;     
      System.out.println("0: Return !!!");
      System.out.println("1: Calculate  !!!");
      while(t!=true){
         try {
              File cus = new File("Payement.txt");
              File temp = new File("Temporary.txt");
      
              int op;
              op=sc.nextInt();
              while(op!=0){
                    if(op==1){
                          FileWriter tempWriter = new FileWriter(temp);//File writer
                          Scanner Reader = new Scanner(cus);
                          while(Reader.hasNext()){
                                   long idtemp;
                                   double payment;
                                   idtemp=Reader.nextLong();
                                   payment=Reader.nextDouble();
                                   if(idtemp==idd){
                                       System.out.println("id : "+idtemp);
                                       System.out.println("Balance : "+payment);
                                       System.out.println("Bill : "+bill);
                                       System.out.println("New Balance : "+(payment-bill));
                                       tempWriter.write(idtemp+" "+(payment-bill)+" ");
                                       w=true;
                                       System.out.println("Data Saved Successfuly!");
                                    }
                                   else
                                      {
                                       tempWriter.write(idtemp+" "+payment+" ");
                                      }   
                            }
                   if(w==false){
                         System.out.println("Customer Id not Found:");
                               }  
                   tempWriter.close();
                   Reader.close();
                   cus.delete();
                   temp.renameTo(cus);
                }
        //inner while ends
                else if(op!=0&&op!=1)
                    System.out.println("Wrong input!");
                System.out.println("Enter Option: ");
                op=sc.nextInt();
           }//while
          t=true;  
       }
      catch (IOException o) //Catches Exception
         {
           System.out.println("An error occurred.");
            o.printStackTrace();
          }
      catch (InputMismatchException o) //Catches Exception
         {
           sc.next();
           System.out.println("Wrong Input!");
           System.out.println("Enter Again!");
          }
     }

   //////////////////
   }
  ////////////////
   
    public void Payment_calculator_Gui(int days,double price,int idd){
   //////////////////
      double bill=days*price;
      
      boolean w=false;//checking if id found or not
    
         try {
              File cus = new File("Payement.txt");
              File temp = new File("Temporary.txt");
      
         
                    
                          FileWriter tempWriter = new FileWriter(temp);//File writer
                          Scanner Reader = new Scanner(cus);
                          while(Reader.hasNext()){
                                   long idtemp;
                                   double payment;
                                   idtemp=Reader.nextLong();
                                   payment=Reader.nextDouble();
                                   if(idtemp==idd){
                                       JOptionPane.showMessageDialog(null,"id : "+idtemp+
                                       "--Balance : "+payment
                                       +"--Bill : "+bill
                                       +"--New Balance : "+(payment-bill));
                                       tempWriter.write(idtemp+" "+(payment-bill)+" ");
                                       w=true;
                                       
                                    }
                                   else
                                      {
                                       tempWriter.write(idtemp+" "+payment+" ");
                                      }   
                            }
                   if(w==false){
                         System.out.println("Customer Id not Found:");
                               }  
                   tempWriter.close();
                   Reader.close();
                   cus.delete();
                   temp.renameTo(cus);
      }
      catch (IOException o) //Catches Exception
         {
           System.out.println("An error occurred.");
            o.printStackTrace();
          }
      catch (InputMismatchException o) //Catches Exception
         {
           sc.next();
           System.out.println("Wrong Input!");
           System.out.println("Enter Again!");
          }
   //////////////////
   }
  
   
   
    public Rooms(int Room_no, String Type, double price, double capacity, String Status,int cusid) {
        this.Room_no = Room_no;
        this.Type = Type;
        this.price = price;
        this.capacity = capacity;
        this.Status = Status;
        this.cusid=cusid;
    }
     public Rooms() {
        this.Room_no = 0;
        this.Type = "";
        this.price = 0.0;
        this.capacity = 0;
        this.Status = "";
        this.cusid=0;
    }
//GEtter And Setter

    public int getCusid() {
        return cusid;
    }

    public void setCusid(int cusid) {
        this.cusid = cusid;
    }
     
    public int getRoom_no() {
        return Room_no;
    }

    public void setRoom_no(int Room_no) {
        this.Room_no = Room_no;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
     public int getDays() {
        return Days;
    }

    public void setDays(int Days) {
        this.Days = Days;
    }

}
