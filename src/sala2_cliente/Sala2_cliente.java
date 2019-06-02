/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sala2_cliente;
import java.net.InetAddress;
import com.sun.java.swing.plaf.windows.resources.windows;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author chemu
 */
public class Sala2_cliente {
    
////////////////////socket connection///////////////////
 String username= " ";
    ArrayList<String> users = new ArrayList();
    int port = 1995;
    Boolean isConnected = false;
    
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;    
///////////////////    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
       Blocked_Screen set =new Blocked_Screen();
       set.setVisible(true);
     
       
    }
    
    
      public void ListenThread() 
    {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }
    
    //--------------------------//
    

    
    public void writeUsers() 
    {
         String[] tempList = new String[(users.size())];
         users.toArray(tempList);
         for (String token:tempList) 
         {
             //users.append(token + "\n");
         }
    }
    
    //--------------------------//
    


    //--------------------------//
    public void Disconnect() 
    {
        try 
        {
           
            sock.close();
        } catch(Exception ex) {
            System.out.println("Failed to disconnect. \n");
        }
        isConnected = false;
        

    }
   
   
    
    //--------------------------//
     public class IncomingReader implements Runnable
    {
        @Override
        public void run() 
        {
            String[] data;
            String stream, error = "Error", connect = "Connect", disconnect = "Disconnect";

            try 
            {
               
                while ((stream = reader.readLine()) != null) 
                {
                     data = stream.split(":");

                     
                      if (data[3].equals(connect))
                     {
                        UnBlocked_Screen bs =new UnBlocked_Screen(data[0],data[1]);
                        bs.setVisible(true);
                       
                        
                     } 
                     else if (data[3].equals(disconnect)) 
                     {
                         
                     } 
                     else if (data[3].equals(error)) 
                     {
                     Blocked_Screen set =new Blocked_Screen(data[2]);
                     set.setVisible(true);
                         
                     }
                }
           }catch(Exception ex) { }
        }
    }

   

    //--------------------------//
     public void b_connect() {     
         try {
               InetAddress address2=InetAddress.getLocalHost();
         
         
        if (isConnected == false) 
        {
            username = address2.toString();
          

            try 
            {
                
                sock = new Socket(address2, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":  has connected.:Chat");
                writer.flush(); 
                isConnected = true; 
            } 
            catch (Exception ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot Connect! Try Again. \n");
               
         
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
            JOptionPane.showMessageDialog(null,"You are already connected. \n");
        }
        } catch (Exception e) {
         }
    }                                         

                                           

                                              

    public void  b_send(String ncontrol,String num ) {                                       
    
        if (ncontrol.isEmpty()){            
        } 
        
        else {
            try {
                
            
               writer.println(num+ ":" + ncontrol + ":" + "Connect");
               writer.flush(); // flushes the buffer
               
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Message was not sent. \n");
            }  
        }
       ListenThread();
        
    }       
    
     public void b_end(String ncontrol,String num) {                                       
        
        
  if (ncontrol.isEmpty()){       
            
            
            
        } 
            else {  try {
                 
               writer.println(num+":"+ ncontrol + ":" + "Disconnect");
               writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Message was not sent. \n");
            }
            
        }
   ListenThread();
     }
    
}
 
  
     