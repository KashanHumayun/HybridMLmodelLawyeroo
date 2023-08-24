import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Main extends JFrame{
    JLabel l1= new JLabel("PreMid");
    JLabel l2= new JLabel("Mid");
    JLabel l3= new JLabel("Final");
    JLabel avg= new JLabel("Final");
    JLabel sts= new JLabel("Final");

    JTextField l1txt = new JTextField();
    JTextField l2txt = new JTextField();
    JTextField l3txt = new JTextField();

    JTextField avgtxt = new JTextField();
    JTextField ststxt = new JTextField();

    public Main(){
        setSize(700,700);
        JPanel p1 = new JPanel();JPanel p2= new JPanel(); JPanel p3=new JPanel();
        p1.setBorder(new LineBorder(Color.black,15));
        p1.setLayout(new GridLayout(1,2));
        p2.setLayout(new FlowLayout(FlowLayout.LEADING));
        l1.setFont(new Font("",3,50) );
        p2.add(l1);p2.add(l2);p2.add(l3);
        
        p1.add(p2);
        p1.add(p3);
        add(p1);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String Args[]){
        Main m1=new Main();
    }
}