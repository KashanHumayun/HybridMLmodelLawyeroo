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
 */public class Admin extends Person {
   Scanner sc;  
   String filename="AdminData.txt";
   String AdminPasscode;

    public Admin(String name,int age,String address,String password) {
        
        setName(name);
        setAge(age);
        setAddress(address);
        setPassword(password);
    }
    public Admin() {
        setName("");
        setAge(0);
        setAddress("");
        setPassword("");
    }
   public void Enter_Data(){//To Enter the data
       
              System.out.println("Enter Name :");
              name=sc.nextLine();
              name=name.replaceAll(" ", "_");
              System.out.println("Enter Age :");
              age=sc.nextInt();
              sc.nextLine();
              System.out.println("Enter Address :");
              address=sc.nextLine();
              address=address.replaceAll(" ", "_");
              System.out.println("Enter Password :");
              password=sc.nextLine();
              password=password.replaceAll("sc.nextLine(); ", "");
              setName(name.toUpperCase());
              setAge(age);
              setId(id);
              setAddress(address.toUpperCase());
              setPassword(password);
   }
   public void format(){
        System.out.println("ID"+"\t\t\t"+"Name"+"\t\t\t"+"Age"+"\t\t\t"+"Address"+"\t\t\t"+"Password"+"\n"+"\n");
   }
   public void Enter_details() throws IOException{//To Enter Details And Save them on File
     sc = new Scanner(System.in);
     String name="";
     /////Creating ID file So every time We Get Unique Id
     try {
        File myObj = new File("Adminid.txt");
        if(!myObj.exists())
            myObj.createNewFile();
 
        if(myObj.exists()){
            Scanner myReader = new Scanner(myObj);
            if(myReader.hasNextInt())
                id = myReader.nextInt();
            else
              id=0;
          myReader.close();
         }
     
        id++;
        try{
            FileWriter fw= new FileWriter(myObj);
            fw.write(id+" ");
            fw.close();
        }
        catch(IOException e){
            System.out.println("Exception Occured While Creating Id file");
        }
     }
     catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();}
      /////Id Created
     boolean t=false;
     //loop starting
     do
     {
         try{
              Enter_Data();//To Enter Details
              System.out.println("YOUR DETAILS :");
              System.out.println("ID :"+getId());
              System.out.println("Name :"+getName());
              System.out.println("Age :"+getAge());
              System.out.println("Address :"+getAddress());
              System.out.println("Password :"+getPassword());
              t=true;
         }
         catch(InputMismatchException b)
             {
             sc.next();
             System.out.println("Please Enter Data Correctly");
              }
      } while(t==false);//will repeat untill the t variable gets true
 
     //Status checker Ends
     //Creating File to Save Data
     try {
        File myObj = new File(filename);
        FileWriter myWriter = new FileWriter(myObj,true);//File writer
        myWriter.write(getId()+" "+getName()+" "+getAge()+" "+getAddress()+" "+getPassword()+"\n");
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
   public void Enter_details_GUI() throws IOException{//To Enter Details And Save them on File
     sc = new Scanner(System.in);
     String name="";
     /////Creating ID file So every time We Get Unique Id
     try {
        File myObj = new File("Adminid.txt");
        if(!myObj.exists())
            myObj.createNewFile();
 
        if(myObj.exists()){
            Scanner myReader = new Scanner(myObj);
            if(myReader.hasNextInt())
                id = myReader.nextInt();
            else
              id=0;
          myReader.close();
         }
     
        id++;
        try{
            FileWriter fw= new FileWriter(myObj);
            fw.write(id+" ");
            fw.close();
        }
        catch(IOException e){
            System.out.println("Exception Occured While Creating Id file");
        }
     }
     catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();}
      /////Id Created
     boolean t=false;
     //loop starting
     do
     {
         try{
              
              JOptionPane.showMessageDialog(null,"YOUR DETAILS :"
              +"--ID :"+getId()+"--Name :"+getName()+"--Age :"+getAge()
              +"--Address :"+getAddress()+"--Password :"+getPassword());
              t=true;
         }
         catch(InputMismatchException b)
             {
             sc.next();
             JOptionPane.showMessageDialog(null,"Please Enter Data Correctly");
              }
      } while(t==false);//will repeat untill the t variable gets true
 
     //Status checker Ends
     //Creating File to Save Data
     try {
        File myObj = new File(filename);
        FileWriter myWriter = new FileWriter(myObj,true);//File writer
        myWriter.write(getId()+" "+getName()+" "+getAge()+" "+getAddress()+" "+getPassword()+"\n");
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
          File temp = new File("Temporary0.txt");

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
                        String passwordtemp;
                        int agetemp;
                        String addresstemp; 
                        idtemp = Reader.nextLong();
                        nametemp=Reader.next();
                        agetemp=Reader.nextInt();
                        addresstemp=Reader.next();
                        passwordtemp=Reader.next();
       
                        if(idtemp==idd){
                              Enter_Data();
                              setId(idd);
                              tempWriter.write(getId()+" "+getName()+" "+getAge()+" "+getAddress()+" "+getPassword()+"\n");
                              w=true;
                              System.out.println("Data Saved Successfuly!");
                        }
                       else
                        {
                              tempWriter.write(idtemp+" "+nametemp+" "+agetemp+" "+addresstemp+" "+passwordtemp+"\n");
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
   }
   
    public void Update_details_Gui(int idd){//The function basically transfer all data to a temporary file
    
     boolean w=false;//checking if id found or not
        try {
          File cus = new File(filename);
          File temp = new File("Temporary0.txt");
          FileWriter tempWriter = new FileWriter(temp);//File writer
          Scanner Reader = new Scanner(cus);
          while(Reader.hasNext()){
                        String nametemp;
                        long idtemp;
                        String passwordtemp;
                        int agetemp;
                        String addresstemp; 
                        idtemp = Reader.nextLong();
                        nametemp=Reader.next();
                        agetemp=Reader.nextInt();
                        addresstemp=Reader.next();
                        passwordtemp=Reader.next();
       
                        if(idtemp==idd){
                             setId(idd);
                              tempWriter.write(getId()+" "+getName()+" "+getAge()+" "+getAddress()+" "+getPassword()+"\n");
                              w=true;
                              JOptionPane.showMessageDialog(null,"Data Saved Successfuly!");
                        }
                       else
                        {
                              tempWriter.write(idtemp+" "+nametemp+" "+agetemp+" "+addresstemp+" "+passwordtemp+"\n");
                        }   
                    }
                    if(w==false){
                        JOptionPane.showMessageDialog(null,"Customer Id not Found:");
                    }  
                    tempWriter.close();
                    Reader.close();
                    cus.delete();
                    temp.renameTo(cus);
                
            
        }
       catch (IOException o) //Catches Exception
        {
           JOptionPane.showMessageDialog(null,o);
        }
      catch (InputMismatchException o) //Catches Exception
         {
           JOptionPane.showMessageDialog(null,o);
           
         }
     
   }
   
   public boolean Search_Admin(){  int op;//For the selection below                   //From the File
        int id;
        String nametemp;
        long idtemp;
        int agetemp;
        String passtemp;
        String addresstemp; 
        
        sc= new Scanner(System.in);
        
        File adm = new File(filename);
        boolean r=false;
        try{
      
            Scanner R = new Scanner(adm);
       
       
       
            System.out.println("Enter The Id:");
            id=sc.nextInt();
            
            while(R.hasNext()){
                
                idtemp = R.nextLong();
                nametemp=R.next();
                agetemp=R.nextInt();
                addresstemp=R.next();
                passtemp=R.next();
                if(idtemp==id){
                  r=true;
                  setAdminPasscode(passtemp);
                  }
               }
            R.close();
         }
        catch(FileNotFoundException f){
               System.out.println("File Not Found!");
         }
        catch(InputMismatchException b){
            sc.next();
            System.out.println("INPUT NOT MATCHED!");
            System.out.println("ENTER AGAIN!");
        }
       
       return r; 
    }
   public boolean Search_Admin_Gui(int id){ 
     
        
        String nametemp;
        long idtemp;
        int agetemp;
        String passtemp;
        String addresstemp; 
        
        boolean g=false;//to check the id exists or not
        File adm = new File(filename);
        try{
            Scanner R = new Scanner(adm);
            while(R.hasNext()){
                idtemp = R.nextLong();
                nametemp=R.next();
                agetemp=R.nextInt();
                addresstemp=R.next();
                passtemp=R.next();
                if(idtemp==id){
                    g=true;
                  JOptionPane.showMessageDialog(null,"ID:"+idtemp+"--"+"Name:"+nametemp
                  +"--Age:"+agetemp+"--Address:"+addresstemp+"");
                  setAdminPasscode(passtemp);
                  R.close();
                  return g;
                  }
               }
            if(g==false)
                JOptionPane.showMessageDialog(null,"Id Not Found");
            R.close();
         }
        catch(FileNotFoundException f){
              JOptionPane.showMessageDialog(null,f);
         }
        catch(InputMismatchException b){
            JOptionPane.showMessageDialog(null,b);
        }
       return g;
    }
   
   public void Show_Details()  {//Shows the details of the Customer
        int op;//For the selection below                   //From the File
        int id;
        String nametemp;
        long idtemp;
        int agetemp;
        String passtemp;
        String addresstemp; 
        String Statustemp;
        System.out.println("0:Back");
        System.out.println("1:All Admin List");
        System.out.println("2:Single Admin record");
        sc= new Scanner(System.in);
        boolean t=false;//to control loop
        File adm = new File(filename);
        
        while(!t){
            try{
                System.out.println("Enter Option:");
                op= sc.nextInt();
                Scanner R = new Scanner(adm);
                sc.nextLine();
                if(op==1){
            
                    format();
                    while(R.hasNext()){
              
                        idtemp = R.nextLong();
                        nametemp=R.next();
                        agetemp=R.nextInt();
                        addresstemp=R.next();
                        passtemp=R.next();
                        System.out.println(idtemp+"\t\t\t"+nametemp+"\t\t\t"+agetemp+"\t\t\t"+addresstemp+"\t\t\t"+passtemp+"\t\t\t"+"\n"+"\n");
                    }
                }
                else if(op==2){
       
                    System.out.println("Enter The Id:");
                    id=sc.nextInt();
                    boolean r=false;
                    while(R.hasNext()){
                
                        idtemp = R.nextLong();
                        nametemp=R.next();
                        agetemp=R.nextInt();
                        addresstemp=R.next();
                        passtemp=R.next();
                        if(idtemp==id){
                            format();
                            System.out.println(idtemp+"\t\t\t"+nametemp+"\t\t\t"+agetemp+"\t\t\t"+addresstemp+"\t\t\t"+passtemp+"\n"+"\n");
                            r=true;
                        }
                    }
                    if(r==false){
                        System.out.println("Id Not Found!");
                    }
                }
                else if(op==0){
                    t=true;
                    }
                else{
             
                    System.out.println("Wrong Choice!\n Enter Again!");
                    }
                 R.close();
            }
            catch(FileNotFoundException f){
                 System.out.println("File Not Found!");
                }
            catch(InputMismatchException b){
                 sc.next();
                 System.out.println("INPUT NOT MATCHED!");
                 System.out.println("ENTER AGAIN!");
                }
       
        }
    }
   
   public int total_admins()  {//Shows the details of the Customer
        int total=0;
        String nametemp;
        long idtemp;
        int agetemp;
        String passtemp;
        String addresstemp; 
        String Statustemp;
        File adm = new File(filename);
   
        try{
       
             Scanner R = new Scanner(adm);
                while(R.hasNext()){
              
                    idtemp = R.nextLong();
                    nametemp=R.next();
                    agetemp=R.nextInt();
                    addresstemp=R.next();
                    passtemp=R.next();
                    total=total+1;
                   }

             R.close();
        }
        catch(FileNotFoundException f){
             System.out.println("File Not Found!");
        }
        catch(InputMismatchException b){
            sc.next();
            System.out.println("INPUT NOT MATCHED!");
        }
       
        return total;
    }
    
   public void Remove_Customer(){//The function basically transfer all data to a temporary file
        sc = new Scanner(System.in);//except the required data and than update the required data
        int idd; 
        boolean w=false;//checking if id found or not
        boolean t=false;     
        System.out.println("0: Return !!!");
        System.out.println("1: Remove Customer !!!");
        while(t!=true){
             try {
                 File cus = new File("Customer.txt");
                 File temp = new File("Temporary6.txt");

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
                                         w=true;
                                        System.out.println("Customer Removed Successfuly!");
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
   }//Removes The Customer From the Customer File
    
    public void Remove_Customer_GUI(int idd){//The function basically transfer all data to a temporary file
       
        boolean w=false;//checking if id found or not
             try {
                 File cus = new File("Customer.txt");
                 File temp = new File("Temporary6.txt");
               
                        
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
                                         w=true;
                                        JOptionPane.showMessageDialog(null,"Customer Removed Successfuly!");
                                      }
                                    else
                                    {
                                         tempWriter.write(idtemp+" "+nametemp+" "+agetemp+" "+addresstemp+" "+Statustemp+"\n"+"\n");
                                    }
                                 }
                                 tempWriter.close();
                                 Reader.close();
                                 cus.delete();
                                 temp.renameTo(cus);
                         
                               if(w==false){
                                    JOptionPane.showMessageDialog(null,"Customer Id not Found:");
                                }  
                             
                }
                catch (IOException o) //Catches Exception
                {
                    JOptionPane.showMessageDialog(null,o);
                
                }
                catch (InputMismatchException o) //Catches Exception
                {
                   
                   JOptionPane.showMessageDialog(null,o);
                   
                }
         
   }//Removes The Customer From the Customer File
   
/////////Constructors  
/////////Constructors end    
/////////Getter and Setter    
  
  
  

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
 //////Getter And Setter End

    public String getAdminPasscode() {
        return AdminPasscode;
    }

    public void setAdminPasscode(String AdminPasscode) {
        this.AdminPasscode = AdminPasscode;
    }
    
    
    
}
  