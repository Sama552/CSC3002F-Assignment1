package Server;

import Message.Message;

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

public class MainServer {

    private ObjectOutputStream Oout;
    private ObjectInputStream Oin;
    private Socket connection;
    private ServerSocket server;
    private int port = 12000;

    public Map<String, Socket> users = new HashMap(); // map of usernames to socket. Google documentation on how to use

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

                RunSocket rSocket = new RunSocket(socket);
                Thread t = new Thread(rSocket);
                t.start();
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

              ObjectInputStream Oin = new ObjectInputStream(in);
              ObjectOutputStream Oout = new ObjectOutputStream(out);

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

              users.put(name, socket); // add the new username to the Map of usernames and Sockets
              printUsers(); // Used for testing DELETE AFTER

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

                  // Process messages based on the flags.
                  switch (message.getMessageFlag())
                  {
                      case "F":
                      case "M":
                      case "C":
                          /* Here it's easier because we have a handle to the sender in the Message Object,
                           * do something similar below.
                           */
                          if(!Message.isValidMsg(message))
                          {
                              // create output stream to the sender and send a 'resend request'.
                              Socket senderConnection = users.get(message.getSenderName());
                              ObjectOutputStream senderOutStream =
                                      new ObjectOutputStream(senderConnection.getOutputStream());

                              senderOutStream.writeObject(new Message(
                                      "R",
                                      "Server",
                                      "Last communication was invalid. Please send again."));
                              senderOutStream.flush();

                              // house-keeping.
                              senderOutStream.close();

                              break;
                          }
                          // else continue to default processing.
                      default: // valid messages or messages that don't require validation ('R', 'D')
                          // TODO: ...
                          Socket recipientConnection = users.get(/*Please add the correct name here*/"DUMMY_NAME");
                          ObjectOutputStream recipientOutStream =
                                  new ObjectOutputStream(recipientConnection.getOutputStream());

                          recipientOutStream.writeObject(message);
                          recipientOutStream.flush();

                          // house-keeping.
                          recipientOutStream.close();

                          break;
                  }
                  /*--------------------------------------------------------------------------------------------------*/
                }
              }
          catch (Exception e) { }
        }
    }

   private void printUsers(){
     Set<String> names = users.keySet();
     for (String s : names){
       System.out.println(s);
     }
   }
}
