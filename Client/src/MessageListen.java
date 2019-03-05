
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
import java.util.HashSet;
import java.util.Arrays;


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
    Object inObject = null;
    Message mess = null;
    while(true){
      try{
        try{
          inObject = in.readObject();
        }catch(ClassNotFoundException e){
          e.printStackTrace();
        }
      }catch(IOException ext){
        ext.printStackTrace();
      }
      if (inObject instanceof String[]){
        String[] inArr = (String[]) inObject;
        Set<String> userSet = new HashSet<String>(Arrays.asList(inArr));
        userSet.remove(mainClass.username);
        int size = userSet.size();
        String[] arr = new String[size];
        arr = (String[]) userSet.toArray(arr);
        mainClass.updateList(arr);

        continue;
      }
      else{
        mess = (Message) inObject;
      }
      if ("C".equals(mess.getMessageFlag())){
        Client from = new Client(conn, mess.getSenderName(), out);
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
