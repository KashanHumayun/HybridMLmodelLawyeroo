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
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Kashan
 */
public class Hotel {
    int id;
    String Name;
    String Address;
    int total_rooms;
    Scanner sc;
    String filename="hotel.txt";
    Rooms rooms;//Composition
    int total_admins;
    public Hotel(Admin a){
     rooms=new Rooms();
     setTotal_rooms(rooms.total_rooms());
     setTotal_admins(a.total_admins());
    }

    public Hotel() {
        rooms=new Rooms();
        this.id = 0;
        this.Name = "";
        this.Address = "";
        this.total_rooms=rooms.total_rooms();
        this.total_admins = 0;
    }
    
    public Hotel(Admin a,int id,String Name,String Address){
        setId(id);
        setName(Name);
        setAddress(Address);
     setTotal_admins(a.total_admins());
     rooms=new Rooms();
     setTotal_rooms(rooms.total_rooms());
    }
    public void enter_hotel_data(){
    
    sc=new Scanner(System.in);
    int id;
    String Name;
    String Address;
    int total_rooms=rooms.total_rooms();
        try{
            System.out.println("Enter Hotel Id :");
            id=sc.nextInt();
            setId(id);
            sc.nextLine();
            System.out.println("Enter Hotel Name :");
            Name=sc.nextLine();
            Name=Name.replace(" ", "_");
            Name=Name.toUpperCase();
            setName(Name);
            System.out.println("Enter Hotel Address :");
            Address=sc.nextLine();
            Address=Address.replace(" ", "_");
            Address=Address.toUpperCase();
            setAddress(Address);
            File hotel=new File(filename);
            if(hotel.createNewFile())System.out.println("File Created!");
            FileWriter fw= new FileWriter(hotel);
            fw.write(getId()+" "+getName()+" "+getAddress()+" "+getTotal_rooms()+" "+getTotal_admins());
            fw.close();
        }
        catch(InputMismatchException e){
            sc.next();
            System.out.println("Input Mismatched!!!");
        }
        catch(IOException e){
            System.out.println("IOException Occured!");
        }
    }
    public void enter_hotel_data_Gui(){
    
        try{
            File hotel=new File(filename);
            if(hotel.createNewFile())JOptionPane.showMessageDialog(null,"File Created!");
            FileWriter fw= new FileWriter(hotel);
            fw.write(getId()+" "+getName()+" "+getAddress()+" "+getTotal_rooms()+" "+getTotal_admins());
            fw.close();
            JOptionPane.showMessageDialog(null,"Data Saved!");
        }
        catch(InputMismatchException e){
            
            JOptionPane.showMessageDialog(null,e);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void Show_data() throws FileNotFoundException{
          File hotel= new File(filename);
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
         System.out.println("Hotel Id: "+idtemp);
         System.out.println("Hotel Name: "+Nametemp);
         System.out.println("Hotel Address: "+Addresstemp);
         System.out.println("Total Rooms: "+total_room_temp);
         System.out.println("Total Admins: "+total_admin_temp);
    }
     public void Show_data_GUI() throws FileNotFoundException{
          File hotel= new File(filename);
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
          setId(idtemp);
          setName(Nametemp);
          setAddress(Addresstemp);
          
          setTotal_admins(total_admin_temp);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getTotal_rooms() {
        return total_rooms;
    }

    public void setTotal_rooms(int total_rooms) {
        this.total_rooms = total_rooms;
    }

    public int getTotal_admins() {
        return total_admins;
    }

    public void setTotal_admins(int total_admins) {
        this.total_admins = total_admins;
    }
    
    
    
}
