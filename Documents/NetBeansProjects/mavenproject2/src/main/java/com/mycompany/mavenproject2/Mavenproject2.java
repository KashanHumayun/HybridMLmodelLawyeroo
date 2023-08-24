package com.mycompany.mavenproject2;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class Mavenproject2 extends JFrame
{
    public static final Color LIGHT_Purple = new Color(170,147,245);
    
    Mavenproject2()
    {
        JFrame newFrame = new JFrame("Pizza Shop");
        newFrame.setVisible(true);
        newFrame.setSize(700, 550);
        newFrame.setLocationRelativeTo(null);
        
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.getContentPane().setBackground(LIGHT_Purple);
        Border b = BorderFactory.createLineBorder(Color.lightGray);
        
        JPanel panel=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        JPanel panel4=new JPanel();
        
        panel.setLayout(null);
        panel2.setLayout(null);
        panel3.setLayout(null);
        panel4.setLayout(null);
        
        JLabel label=new JLabel("Pizza");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        
        JLabel label2=new JLabel("Deals");
        label2.setFont(new Font("Arial", Font.BOLD, 20));
        
        JLabel label3=new JLabel("Extras");
        label3.setFont(new Font("Arial", Font.BOLD, 20));
        
        JLabel label4=new JLabel("Net Total ---->");
        label4.setFont(new Font("Arial", Font.BOLD, 28));

        label.setBounds(20,-15,200,50);
        label2.setBounds(235,-15,200,50);
        label3.setBounds(20,165,200,50);
        label4.setBounds(20,450,200,50);
        
        newFrame.add(label);
        newFrame.add(label2);
        newFrame.add(label3);
        newFrame.add(label4);
        
        panel.setBackground(LIGHT_Purple);
        panel.setBounds(10,20,190,140);
        panel.setBorder(b);
        
        panel2.setBackground(LIGHT_Purple);
        panel2.setBounds(225,20,400,300);
        panel2.setBorder(b);
        
        panel3.setBackground(LIGHT_Purple);
        panel3.setBounds(10,200,190,230);
        panel3.setBorder(b);
        
        panel4.setBackground(LIGHT_Purple);
        panel4.setBounds(225,330,400,150);
        panel4.setBorder(b);
        
        newFrame.add(panel);
        newFrame.add(panel2);
        newFrame.add(panel3);
        newFrame.add(panel4);
 
        JRadioButton small = new JRadioButton();
        small.setBounds(10, 10, 20, 40);
        small.setBackground(LIGHT_Purple);
        panel.add(small);
        
        JLabel s = new JLabel("Small");
        s.setFont(new Font("Arial", Font.BOLD, 16));
        s.setBounds(35,5,200,50);
        panel.add(s);
        
        JLabel m = new JLabel("Medium");
        m.setFont(new Font("Arial", Font.BOLD, 16));
        m.setBounds(35,40,200,50);
        panel.add(m);
        
        JLabel l = new JLabel("Large");
        l.setFont(new Font("Arial", Font.BOLD, 16));
        l.setBounds(35,80,200,50);
        panel.add(l);
       
        
        JRadioButton medium = new JRadioButton();
        medium.setBounds(10,40,200,50);
        medium.setBackground(LIGHT_Purple);
        panel.add(medium);
        
        JRadioButton large = new JRadioButton();
        large.setBounds(10,80,200,50);
        large.setBackground(LIGHT_Purple);
        panel.add(large);
        
        JTextArea t1 = new JTextArea();
        t1.setBorder(b);
        t1.setVisible(true);
        t1.setText("80");
        panel.add(t1);
        
        
        
    }
}

