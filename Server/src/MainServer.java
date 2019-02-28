import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection;
    private ServerSocket server;
    private int totalClients = 100;
    private int port = 6789;

    public MainServer() {

    }



    public void startRunning()
    {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Waiting for a client....");
            System.out.println();
            while(true) {
                Socket socket = server.accept();

                System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
                System.out.println();

                RunSocket rSocket = new RunSocket(socket);
                Thread t = new Thread(rSocket);
                t.start();
                //System.out.println("Socket Stack Size-----"+socketMap.size());
            }
        }
        catch (Exception e) { }

    }

    class RunSocket implements Runnable {
        private Socket socket;

        public RunSocket(Socket socket) {
          this.socket = socket;
        }

        @Override
        public void run() {
          try {
              InputStream in = socket.getInputStream();
              OutputStream out = socket.getOutputStream();

              DataInputStream dataIn = new DataInputStream(in);
              DataOutputStream dataOut = new DataOutputStream(out);

              String line = null;
              while (true) {
                  line = dataIn.readUTF();
                  System.out.println("Recievd the line----" + line);
                  dataOut.writeUTF(line + " Comming back from the server");
                  dataOut.flush();
                  System.out.println("waiting for the next line....");
                }
              }
          catch (Exception e) { }
        }
    }
      /** old stuff
        try
        {
            server=new ServerSocket(port, totalClients);
            while(true)
            {
                try
                {
                    status.setText(" Waiting for Someone to Connect...");
                    connection=server.accept();
                    status.setText(" Now Connected to "+connection.getInetAddress().getHostName());


                    output = new ObjectOutputStream(connection.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(connection.getInputStream());

                    whileChatting();

                }catch(EOFException eofException)
                {
                }
            }
        }
        catch(IOException ioException)
        {
                ioException.printStackTrace();
        }
        */

   private void whileChatting() throws IOException
   {
     /** old stuff
        String message="";
        jTextField1.setEditable(true);
        do{
                try
                {
                        message = (String) input.readObject();
                        chatArea.append("\n"+message);
                }catch(ClassNotFoundException classNotFoundException)
                {

                }
        }while(!message.equals("Client - END"));
        */
   }

    private void sendMessage(String message)
    {
      /** old stuff
        try
        {
            output.writeObject("Server - " + message);
            output.flush();
            chatArea.append("\nServer - "+message);
        }
        catch(IOException ioException)
        {
            chatArea.append("\n Unable to Send Message");
        }
        */
    }
}
