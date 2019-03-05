
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Arrays;

public class MainServer {

    private ObjectOutputStream Oout;
    private ObjectInputStream Oin;
    private Socket connection;
    private ServerSocket server;
    private int port = 12000;

    public Map<String, RunSocket> users = new HashMap(); // map of usernames to socket. Google documentation on how to use

    public MainServer() {

    }



    public void startRunning()
    {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Waiting for a client....");
            System.out.println();
            while(true) {
                Socket socket = server.accept(); // wait for a new connection

                System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
                System.out.println();

              //  System.out.println("printinf the sendlist");


                RunSocket rSocket = new RunSocket(socket);
                Thread t = new Thread(rSocket);
                t.start();
            }
        }
        catch (Exception e) { }

    }

    class RunSocket implements Runnable {
        private Socket socket;
        private InputStream in;
        private OutputStream out;
        private ObjectInputStream Oin;
        private ObjectOutputStream Oout;


        public RunSocket(Socket socket) {
          this.socket = socket;
        }
        public ObjectOutputStream getOutputStream(){
           return Oout;
         }

        @Override
        public void run() {
          try {
               in = socket.getInputStream();
               out = socket.getOutputStream();

               Oin = new ObjectInputStream(in);
               Oout = new ObjectOutputStream(out);

              String name = "";
              try{ // get the username from the client (will be the first they send after connecting)
                name = (String) Oin.readObject();
              }catch(IOException e){
                  e.printStackTrace();
              }


              if (users.containsKey(name)){ // check if username is taken OK if its available else closes the socket
                Oout.writeObject("no");
                Oout.flush();
                socket.close();
                throw new Exception();
              }else{
                Oout.writeObject("OK");
                Oout.flush();
              }

              users.put(name, this); // add the new username to the Map of usernames and Sockets
              printUsers(); // Used for testing DELETE AFTER

              sendList();

              String line = null;
              while (true) { // where all the input and output is gonna happen
//                  //TODO
//                  line = (String) Oin.readObject(); // wait for an input
//                  System.out.println("Recievd the line----" + line);
//                  Oout.writeObject(line + " Comming back from the server");
//                  Oout.flush();
//                  System.out.println("waiting for the next line....");

                  /*--------------------------------------------------------------------------------------------------*/
                  /* Modifications
                  /*--------------------------------------------------------------------------------------------------*/
                  Message message = (Message) Oin.readObject();
                  forward(message);

                  // Process messages based on the flags.


                  // switch (message.getMessageFlag())
                  // {
                  //     case "F":
                  //     case "M":
                  //     case "C":
                  //         /* Here it's easier because we have a handle to the sender in the Message Object,
                  //          * do something similar below.
                  //          */
                  //         if(!Message.isValidMsg(message))
                  //         {
                  //             // create output stream to the sender and send a 'resend request'.
                  //             RunSocket senderConnection = users.get(message.getSenderName());
                  //             ObjectOutputStream senderOutStream =
                  //                     new ObjectOutputStream(senderConnection.getOutputStream());
                  //
                  //             senderOutStream.writeObject(new Message(
                  //                     "R",
                  //                     "Server",
                  //                     "Last communication was invalid. Please send again."));
                  //             senderOutStream.flush();
                  //
                  //             // house-keeping.
                  //             senderOutStream.close();
                  //
                  //             break;
                  //         }
                  //         // else continue to default processing.
                  //     default: // valid messages or messages that don't require validation ('R', 'D')
                  //         // TODO: ...
                  //         RunSocket recipientConnection = users.get(/*Please add the correct name here*/"DUMMY_NAME");
                  //         ObjectOutputStream recipientOutStream =
                  //                 new ObjectOutputStream(recipientConnection.getOutputStream());
                  //
                  //         recipientOutStream.writeObject(message);
                  //         recipientOutStream.flush();
                  //
                  //         // house-keeping.
                  //         recipientOutStream.close();
                  //
                  //         break;
                  // }
                  /*--------------------------------------------------------------------------------------------------*/
                }
              }
          catch (Exception e) { }
        }
    }

   private void forward(Message m){
     String rec = m.getRecName();
     RunSocket soc = users.get(rec);
     ObjectOutputStream fOut = soc.getOutputStream();
     try{
       fOut.writeObject(m);
       fOut.flush();
     }catch(IOException e){
       e.printStackTrace();
     }

   }

   private void printUsers(){
     Set<String> names = users.keySet();
     for (String s : names){
       System.out.println(s);
     }
   }

   private void sendList(){
      Set<String> s = users.keySet();
      System.out.println("got map");
      int n = s.size();
      String arr[] = new String[n];
      arr = s.toArray(arr);
      System.out.println("got arr");
      System.out.println(Arrays.toString(arr));
      //Message userList = new Message ("U","server",arr);
      System.out.println("got message");
      for (int i = 0; i < arr.length; i++) {
          System.out.println("in loop");
          try {
            RunSocket socket = users.get(arr[i]);
            ObjectOutputStream newOut =socket.getOutputStream();
            System.out.println("new socket");

            newOut.writeObject(arr);
            System.out.println("Sent a userList");
          }
            catch (Exception e) {
            }

      }
      System.out.println(Arrays.toString(arr));
   }
}
