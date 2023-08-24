import java.io.*;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Newclient extends JFrame{
	
	Socket socket;	
	BufferedReader br;
	PrintWriter out;
	
    private JLabel heading = new JLabel("client Area");
    private JTextArea message = new JTextArea();
    private JTextField messageinput = new JTextField();
    private Font font = new Font("roboto",Font.PLAIN,20); 
	
	
	public Newclient() {
		try {
//			System.out.println("sending request to server");
//			socket=new Socket("127.0.0.1",7777);
//			System.out.println("connection done");
//			br =new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			out = new PrintWriter(socket.getOutputStream());
			
		   // startWriting();
            createGUi();
			handleEvents();
			startReading();

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    private void handleEvents() {
		messageinput.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("key released " +e.getKeyCode());
				if(e.getKeyCode() == 10)
				{
					//System.out.println("you have pressed enter button");
                    String contentToSend=messageinput.getText();
					message.append("me :"+contentToSend+"\n");
					out.println(contentToSend);
					out.flush();
					messageinput.setText("");
					messageinput.requestFocus();
				}
			
			}
			
		});
	}
	private void createGUi()
    {
        this.setTitle("client Message[End]");
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
        //coding for components
        heading.setFont(font);
        message.setFont(font);
        messageinput.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,30,40,80));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,30,40,80));
        message.setEditable(false);
        //frame ka layout set karagay
        this.setLayout(new BorderLayout());
        // adding the component to frame
        this.add(heading,BorderLayout.NORTH);
		JScrollPane jScrollPane = new JScrollPane();
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageinput,BorderLayout.SOUTH);
    }
	


	
	
	public void startReading()
	{
		// thread  data(message) read karka deta raha ga 
	  Runnable r1 =() -> {
			System.out.println("reader started..");
			
			
			  
			 
				try {
					
					while(true)
					{
						
					 String msg = br.readLine();
					 if(msg.equals("exit"))
					{
						System.out.println("server terminate the chat");
						JOptionPane.showMessageDialog(this,"server terminate the chat");
						messageinput.setEnabled(false);
						socket.close();
						break;
					}	
						System.out.println("server:"+msg);
						message.append("server: " + msg+"\n");
				    }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
													
				
	   };
	   
	   new Thread(r1).start();
	
	}
	public void startWriting()
	{
		// thread - data user lega or client ko send kardaga
		Runnable r2 = () -> {
			System.out.println("writer started");
			
				try {
					while(!socket.isClosed())
					{
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					String content=br1.readLine();				
					out.println(content);
					out.flush();
					if(content.equals("exit"))
					{
						socket.close();
						break;
					}
				
				
					}
		      }catch(Exception e)
				{
					e.printStackTrace();
				}
		
		  };
		  new Thread(r2).start();
		  
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("hey client here");
		new Newclient();
		}

}