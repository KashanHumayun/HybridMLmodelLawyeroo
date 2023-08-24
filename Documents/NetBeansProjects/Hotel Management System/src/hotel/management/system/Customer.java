/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.management.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Kashan
 */
public class Customer extends Person{
    Scanner sc; //Scanner Object    
    private boolean status;//Used to check if the Customer is eligible for booking
    private Payment pay;//Payment class object
    double payment;
    //Getting details from the User And Setting it to the super class object
    private int idsearch;
    private String filename="Customer.txt";
  
    public void Enter_data(){
        boolean t=false;
        while(t!=true){
                try{
                     sc= new Scanner(System.in);
                     System.out.println("Enter Your Name :");
                     name=sc.nextLine();
                     name=name.replaceAll(" ", "_");
                     System.out.println("Enter Your Age :");
                     age=sc.nextInt();
                     sc.nextLine();
                     System.out.println("Enter Your Address :");
                     address=sc.nextLine();
                     address=address.replaceAll(" ", "_");
                     setName(name.toUpperCase());
                     setAge(age);
                     setId(id);
                     setAddress(address.toUpperCase());
                     t=true;
                    }
                catch(InputMismatchException e){
                     sc.next();
                     System.out.println("Wrong Input Entered!");
                     System.out.println("Enter Data  Again!");
                    }
        
        }//While Ends
    }
    public void Enter_details() throws IOException{
         sc = new Scanner(System.in);
         String filename="Customer.txt";
         String name="";
         /////Creating ID file So every time We Get Unique Id
         int id=0;
          try {
              File idfile = new File("id.txt");
              if(!idfile.exists())
                  idfile.createNewFile();
      
              if(idfile.exists()){
                  Scanner myReader = new Scanner(idfile);
                  if(myReader.hasNextInt())
                      id = myReader.nextInt();
                  else
                  id=0;
                  myReader.close();
             }

               id++;
              try{
                  FileWriter fw= new FileWriter(idfile);
                  fw.write(id+"");
                  fw.close();
                  }
              catch(IOException e){
                  System.out.println("Exception Occured While Creating Id file");
                  }
            }
          catch (FileNotFoundException e) {
             System.out.println("An error occurred.");}
      /////Id Created
        int age=0;
         String address=""; 
         boolean t=false;
         //loop starting
         setId(id);
         do
         {
             try{
                      Enter_data();
                      System.out.println("YOUR DETAILS :");
                      System.out.println("ID :"+getId());
                      System.out.println("Name :"+getName());
                      System.out.println("Age :"+getAge());
                      System.out.println("Address :"+getAddress());
                    t=true;
                }
              
             catch(InputMismatchException b)
               {
                     sc.next();
                     System.out.println("Please Enter Data Correctly");
              }
          } while(t==false);//will repeat untill the t variable gets true
         System.out.print("::::Balance details::::");
         pay = new Payment();
         pay.setId(id);
         pay.payment_getter();//Asks the user about his total amount
         setStatus(pay.status());
         //Status checker
         String status="";
         if(getStatus()==true)
             status="Eligible";
         else
             status="Not_Eligible";
     //Status checker Ends
     //Creating File to Save Data
         if(status=="Eligible"){
             try {
                 File myObj = new File(filename);
                  if(myObj.createNewFile())
                        System.out.println("File Created!");
                 FileWriter myWriter = new FileWriter(myObj,true);//File writer
                 myWriter.write(getId()+" "+getName()+" "+getAge()+" "+getAddress()+" "+status+"\n"+"\n");
                 myWriter.close();
                 System.out.println("Data Saved Successfully ");
                 }
               catch (IOException o) //Catches Exception
                 {
                   System.out.println("An error occurred.");
                    o.printStackTrace();
                  }
     //Saving Customer Data Ended
            }
        else
             System.out.println("Sorry You Are Not Eligible...");
    }//It Takes info from the Customer And Save it in   
    //Gui Funtion To Set Data To File
    public void Enter_details_Gui() throws IOException{
         sc = new Scanner(System.in);
         String filename="Customer.txt";
         String name="";
         /////Creating ID file So every time We Get Unique Id
         int id=0;
          try {
              File idfile = new File("id.txt");
              if(!idfile.exists())
                  idfile.createNewFile();
      
              if(idfile.exists()){
                  Scanner myReader = new Scanner(idfile);
                  if(myReader.hasNextInt())
                      id = myReader.nextInt();
                  else
                  id=0;
                  myReader.close();
             }

               id++;
              try{
                  FileWriter fw= new FileWriter(idfile);
                  fw.write(id+"");
                  fw.close();
                  }
              catch(IOException e){
                  JOptionPane.showMessageDialog(null,"Exception Occured While Creating Id file");
                  }
            }
          catch (FileNotFoundException e) {
             System.out.println("An error occurred.");}
      /////Id Created
     
         //loop starting
         setId(id);
          JOptionPane.showMessageDialog(null,("ID :"+getId())
         +("--Name :"+getName())
          +("--Age :"+getAge())
         +("--Address :"+getAddress()));
          
                
              
         pay = new Payment();
         pay.setId(id);
         pay.payment_getter(getPayment());
         setStatus(pay.status());
         //Status checker
         String status="";
         if(getStatus()==true)
             status="Eligible";
         else
             status="Not_Eligible";
     //Status checker Ends
     //Creating File to Save Data
         if(status.equals("Eligible")){
             try {
                 File myObj = new File(filename);
                  if(myObj.createNewFile())
                        System.out.println("File Created!");
                 FileWriter myWriter = new FileWriter(myObj,true);//File writer
                 myWriter.write(getId()+" "+getName()+" "+getAge()+" "+getAddress()+" "+status+"\n"+"\n");
                 myWriter.close();
                 JOptionPane.showMessageDialog(null,"Data Saved Successfully ");
                 }
               catch (IOException o) //Catches Exception
                 {
                   JOptionPane.showMessageDialog(null,"An error occurred.");
                    o.printStackTrace();
                  }
     //Saving Customer Data Ended
            }
        else
             JOptionPane.showMessageDialog(null,"Sorry You Are Not Eligible...Balance should be more tha 50k");
    }//It Takes info from the Customer And Save it in   

    
    public boolean Find_Customer(){//The function basically transfer all data to a temporary file
         sc = new Scanner(System.in);//except the required data and than update the required data
         int idd; 
         boolean w=false;//checking if id found or not
    
             try {
                  File cus = new File(filename);
                  System.out.println("0:Return");
                  System.out.println("1:Find Customer:");
                   System.out.println("Enter option:");
                   int op;
                   op=sc.nextInt();
                   while(op!=0){
                        if(op==1){
                              Scanner Reader = new Scanner(cus);
                              System.out.println("Enter id:");
                              idd=sc.nextInt();
                              sc.nextLine();
                              while(Reader.hasNext()){
                                 String nametemp;
                                 long idtemp=idd;
       
                                 int agetemp;
                                 String addresstemp; 
                                 String Statustemp;
                                 idtemp = Reader.nextLong();
                                 nametemp=Reader.next();
                                 agetemp=Reader.nextInt();
                                 addresstemp=Reader.next();
                                 Statustemp=Reader.next();
       
                                 if(idtemp==idd){
           
                                     w=true;
                                     setIdsearch(idd);
                                     return true;
                                    }
                                }
                               if(w==false){
                                     System.out.println("Customer Id not Found:");
                                }  
                               Reader.close();
                        }
       
                       else if(op!=0&&op!=1)
                              System.out.println("Wrong input!");
                       System.out.println("Enter Option: ");
                       op=sc.nextInt();
                    }
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
               
                 }

     return false;
   }
    public boolean Find_Customer(int idd){//The function basically transfer all data to a temporary file
         sc = new Scanner(System.in);//except the required data and than update the required data
          int op;
         boolean w=false;//checking if id found or not
    
             try {
                  File cus = new File(filename);
       
                        
                              Scanner Reader = new Scanner(cus);
                              while(Reader.hasNext()){
                                 String nametemp;
                                 long idtemp=idd;
       
                                 int agetemp;
                                 String addresstemp; 
                                 String Statustemp;
                                 idtemp = Reader.nextLong();
                                 nametemp=Reader.next();
                                 agetemp=Reader.nextInt();
                                 addresstemp=Reader.next();
                                 Statustemp=Reader.next();
       
                                 if(idtemp==idd){
                                     Reader.close();
                                     w=true;
                                     JOptionPane.showMessageDialog(null,"ID="+idtemp+";;;NAME="+nametemp
                                     +";;;Age="+agetemp+";;;Address="+addresstemp+";;;Status="+Statustemp);
                                     return true;
                                    }
                                }
                               if(w==false){
                                     JOptionPane.showMessageDialog(null,"Customer Id not Found:");
                                }  
                             Reader.close();  
                }
               catch (IOException o) //Catches Exception
                 {
                     JOptionPane.showMessageDialog(null,"An error occurred.");
                   
                  }
               catch (InputMismatchException o) //Catches Exception
                 {
                  
                    JOptionPane.showMessageDialog(null,"Wrong Input!");
               
                 }

     return false;
   }//For GUI
    public void Update_details(){//The function basically transfer all data to a temporary file
         sc = new Scanner(System.in);//except the required data and than update the required data
         int idd; 
         boolean w=false;//checking if id found or not
         boolean t=false;     
         System.out.println("0: Return !!!");
         System.out.println("1: Update Record  !!!");
         while(t!=true){
             try {
                  File cus = new File(filename);
                  File temp = new File("Temporary4.txt");

                  int op;
                  op=sc.nextInt();
                  while(op!=0){
                    if(op==1){
                         FileWriter tempWriter = new FileWriter(temp);//File writer
                         Scanner Reader = new Scanner(cus);
                         System.out.println("Enter id:");
                         idd=sc.nextInt();
                         sc.nextLine();
                         while(Reader.hasNext()){
                                 String nametemp;
                                 long idtemp=idd;
       
                                 int agetemp;
                                 String addresstemp; 
                                 String Statustemp;
                                 idtemp = Reader.nextLong();
                                 nametemp=Reader.next();
                                 agetemp=Reader.nextInt();
                                 addresstemp=Reader.next();
                                 Statustemp=Reader.next();
       
                                 if(idtemp==idd){
           
                                      Enter_data();
                                      System.out.println("::::Payment details::::");
                                      pay = new Payment();
                                      pay.setId(idd);
                                      pay.payment_checker();//Asks the user about his total amount in bank
                                      System.out.println("Enter new Balance:");
                                      pay.payment_getter();
                                      setStatus(pay.status());
                                      
                                      setId(idd);
            //Status checker
                                      String status="";
                                      if(getStatus()==true)
                                            status="Eligible";
                                      else
                                            status="Not_Eligible";
                                      tempWriter.write(getId()+" "+getName()+" "+getAge()+" "+getAddress()+" "+" "+status+"\n"+"\n");
                                      w=true;
                                      System.out.println("Data Saved Successfuly!");
                                    }
                                 else
                                   {
                                      tempWriter.write(idtemp+" "+nametemp+" "+agetemp+" "+addresstemp+" "+Statustemp+"\n"+"\n");
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
               }//try
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
   }//It updates the Detail of the Customer
    public void Update_details(int idd){//The function basically transfer all data to a temporary file
         sc = new Scanner(System.in);//except the required data and than update the required data
         boolean w=false;//checking if id found or not
        
             try {
                         File cus = new File(filename);
                         File temp = new File("Temporary4.txt");
                         FileWriter tempWriter = new FileWriter(temp);//File writer
                         Scanner Reader = new Scanner(cus);
                         
                         while(Reader.hasNext()){
                                 String nametemp;
                                 long idtemp=idd;
       
                                 int agetemp;
                                 String addresstemp; 
                                 String Statustemp;
                                 idtemp = Reader.nextLong();
                                 nametemp=Reader.next();
                                 agetemp=Reader.nextInt();
                                 addresstemp=Reader.next();
                                 Statustemp=Reader.next();
       
                                 if(idtemp==idd){
                                      pay = new Payment();
                                      pay.payment_getter(getPayment());
                                      setStatus(pay.status());
                                      pay.setId(idd);
                                      setId(idd);
 
                                      String status="";
                                      if(getStatus()==true)
                                      {   status="Eligible";
                                          tempWriter.write(getId()+" "+getName()+" "+getAge()+" "+getAddress()+" "+" "+status+"\n"+"\n");
                                          JOptionPane.showMessageDialog(null, "Data Saved Successfuly!");
                                      }
                                      else
                                      {   status="Not_Eligible";
                                          tempWriter.write(idtemp+" "+nametemp+" "+agetemp+" "+addresstemp+" "+status+"\n"+"\n");
                                          JOptionPane.showMessageDialog(null, "Not Eligible! Balance Should be More than 50k");
                                      }
                                      
                                      w=true;
                                      
                                    }
                                 else
                                   {
                                      tempWriter.write(idtemp+" "+nametemp+" "+agetemp+" "+addresstemp+" "+Statustemp+"\n"+"\n");
                                   }

                              }
                           if(w==false){
                                 JOptionPane.showMessageDialog(null,"Customer Id not Found:");
                              }  
                           tempWriter.close();
                           Reader.close();
                           cus.delete();
                           temp.renameTo(cus);
               }//try
           catch (IOException o) //Catches Exception
             {
                 JOptionPane.showMessageDialog(null,o);
              }
           catch (InputMismatchException o) //Catches Exception
            {
               JOptionPane.showMessageDialog(null,o);
            }
        
   }//For GUI It updates the Detail of the Customer
  
    
    
    public void Show_Details() throws FileNotFoundException {//Shows the details of the Customer
        int op;//For the selection below                   //From the File
        int id;
        System.out.println("0:Return");
        System.out.println("1:All records");
        System.out.println("2:Single record");
        
        sc= new Scanner(System.in);
        
        String nametemp;
        long idtemp;
        int agetemp;
        String addresstemp; 
        String Statustemp;
        boolean t=false;
        
        while(t!=true){
            try{
                 File cus = new File(filename);
                 op= sc.nextInt();
                 Scanner Reader = new Scanner(cus);
                 if(op==1){
                    format();
           
                    while(Reader.hasNext()){
                
                             idtemp = Reader.nextLong();
                             nametemp=Reader.next();
                             agetemp=Reader.nextInt();
                             addresstemp=Reader.next();
                             Statustemp=Reader.next();
                             System.out.println(idtemp+"\t\t\t"+nametemp+"\t\t\t"+agetemp+"\t\t\t"+addresstemp+"\t\t\t"+Statustemp+"\n"+"\n");
                    }
                 }
                 else if(op==2){

                    System.out.println("Enter The Id:");
                    id=sc.nextInt();
            
                    boolean g=false;
                    while(Reader.hasNext()){
                    idtemp = Reader.nextLong();
                    nametemp=Reader.next();
                    agetemp=Reader.nextInt();
                    addresstemp=Reader.next();
                    Statustemp=Reader.next();
                    if(idtemp==id)
                       {  format();
                          System.out.println(idtemp+"\t\t\t"+nametemp+"\t\t\t"+agetemp+"\t\t\t"+addresstemp+"\t\t\t"+Statustemp+"\n"+"\n");
                       g=true;}
                    }
                   if(g==false)
                         System.out.println("Id not Found!");    

                 }
                else if(op==0){
                    t=true;
                    }
                else{
                   Reader.nextLine();
                   System.out.println("Wrong Choice!");
                  }
                if(t!=true) System.out.println("Enter Option!"); 
                Reader.close();
            }
        
            catch(InputMismatchException g){
                sc.next();
                System.out.println("Input Mismatched!");
                System.out.println("Enter Option!"); 
             }
            catch(FileNotFoundException g){
                System.out.println("File Not Found!");
            }
        }
    }
    
    public void format(){
         System.out.println("ID"+"\t\t\t"+"Name"+"\t\t\t"+"Age"+"\t\t\t"+"Address"+"\t\t\t"+"Status"+"\n"+"\n");
    }
    
////Constructors
    
     public Customer(String Name, int Age , String Address, double Payment) {
         
        setName(Name);
        setAge(Age);
        setAddress(Address);
        this.status = status;
        setPayment(Payment);
    }
    
    public Customer(boolean status, Payment pay) {
        this.status = status;
        this.pay = pay;
    }
    
    public Customer() {
         
        this.status =false;
    }
////////Constructors end
    
////////Getter and Setter    


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean getStatus(){
        return status;
    }

    public Payment getPay() {
        return pay;
    }

    public void setPay(Payment pay) {
        this.pay = pay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = (int) id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
      public int getIdsearch() {
        return idsearch;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public void setIdsearch(int idsearch) {
        this.idsearch = idsearch;
    }
 //////Getter And Setter End
}
