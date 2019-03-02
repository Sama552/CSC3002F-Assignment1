package Server;

import Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class handles the client-server chat logic on the server side.
 */
public class ClientServerThread extends Thread{
    Socket socket;
    MainServer mainServer;

    private ObjectOutputStream output; // to be moved.
    private ObjectInputStream input;
    private Socket connection; // to be removed.

    public ClientServerThread(Socket socket, MainServer mainServer)
    {
        this.socket = socket;
        this.mainServer = mainServer;
    }

    @Override
    public void run()
    {
        while(true)
        {
            String msg = "";
            try
            {
                msg = (String) input.readObject();
                processMsg(msg);
            }
            catch(ClassNotFoundException e)
            {
                continue;
            }
            catch (IOException e)
            {
                continue;
            }
        }
    }

    public void processMsg(String msg) throws IOException
    {
        // Air the message.
        mainServer.getChatArea().append(msg);

        String clientName = msg.substring(msg.indexOf('[') + 1, msg.indexOf(']'));
        String recipientStr = msg.substring(msg.indexOf(']') + 2, msg.lastIndexOf(']'));
        int md5Start = msg.lastIndexOf(']') + 1;
        String md5AndMsg = msg.substring(md5Start);

        // Forward this to the recipients if the message is valid.
        if(Message.validMsg(md5AndMsg)) {
            String msgText = md5AndMsg.substring(32);

            // Process messages meant for the server here.
            if ("Server".equals(recipientStr)) {

                switch (msgText) {
                    case "SignOff":
                        mainServer.closeConnection(clientName);

                        break;
                    default:
                        break;
                }
            } else {
                for (String recipientName : recipientStr.split("[,]")) {
                    if (mainServer.getConnectionTable().containsKey(recipientName)) {
                        Socket recipientConnection = mainServer.getConnectionTable().get(recipientName);
                        if (recipientConnection != null) {
                            // fetch the output stream for the recipient.
                            ObjectOutputStream outputStream = new ObjectOutputStream(recipientConnection.getOutputStream());
                            String msgOut = clientName + " - " + msgText;
                            outputStream.writeObject(Message.getMd5(msgOut) + msgOut);
                            outputStream.flush();

                            // close the stream.
                            outputStream.close();
                        } else {
                            // TODO: Notify the sender that the recipient is offline.
                        }
                    } else {
                        // TODO: The given user was never on this server, notify the sender.
                    }
                }
            }
        }
    }
}
