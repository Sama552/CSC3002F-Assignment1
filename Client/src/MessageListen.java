
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ListSelectionModel;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


public class MessageListen implements Runnable{
  private Socket conn;
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private Connect mainClass;

  public MessageListen(Connect c, Socket socket, ObjectInputStream in, ObjectOutputStream out){
    conn = socket;
    mainClass = c;
    this.in = in;
    this.out = out;
  }

  @Override
  public void run() {
    Message mess = null;
    while(true){
      try{
        try{
          mess = (Message) in.readObject();
        }catch(ClassNotFoundException e){
          e.printStackTrace();
        }
      }catch(IOException ext){
        ext.printStackTrace();
      }
      if ("C".equals(mess.getMessageFlag())){
        Client from = new Client(conn, mess.getSenderName());
        mainClass.chats.put(mess.getSenderName(), from);
        Thread t = new Thread(from);
        t.start();
      }else{
        String senderID = mess.getSenderName();
        Client dest = mainClass.chats.get(senderID);
        dest.receivedMessage(mess);
        continue;
      }
    }
  }
}
