package Server;

import Message.Message;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MainServer extends javax.swing.JFrame{

//    private ObjectOutputStream output; // to be moved.
//    private ObjectInputStream input;
//    private Socket connection; // to be removed.
    private ServerSocket server;
    private int totalClients = 100;
    private int port = 6789;

    public HashMap<String, Socket> getConnectionTable()
    {
        return connectionTable;
    }

    public void closeConnection(String clientName) throws IOException
    {
        Socket toClose = connectionTable.get(clientName);
        toClose.close();
        connectionTable.put(clientName, null);
    }

    // Matching table. Client names will be matched to their connection.
    private HashMap<String, Socket> connectionTable = new HashMap<>();
  
    public MainServer(){
        
        initComponents();
        this.setTitle("Server");
        this.setVisible(true);
        status.setVisible(true);
    }

    /**
     * This method continuously listens for client connections, and upon establishment, processes the sign-on message
     * from the client and adds them to the connection table.
     */
    public void listenForClientConnections()
    {
        try
        {
            server = new ServerSocket(port, totalClients);

            // Continuous loop to listen for connections from clients.
            while(true)
            {
                Socket newConnection = server.accept();
                status.setText(newConnection.getInetAddress().getHostName() + " has connected.");

                // TODO: PROCESS SIGN-ON MESSAGE AND ADD THE CONNECTION TO THE TABLE.
                ObjectInputStream signOnStream = new ObjectInputStream(newConnection.getInputStream());
                ObjectOutputStream clientStream = new ObjectOutputStream(newConnection.getOutputStream());
                boolean signedOn = false;
                while(!signedOn)
                {
                    String signOnMsg = (String) signOnStream.readObject();

                    // validate the received message.
                    if(Message.validMsg(signOnMsg))
                    {
                        // register the client and update sign-on status.
                        connectionTable.put(signOnMsg.substring(32), newConnection);
                        clientStream.writeObject("Successful");
                        signedOn = true;
                    }
                    else
                    {
                        clientStream.writeObject("Unsuccessful");
                    }

                    clientStream.flush();
                }

                // close the stream.
                signOnStream.close();
                clientStream.close();

                // Set the display field to editable if not.
                if(!jTextField1.isEditable())
                {
                    jTextField1.setEditable(true);
                }

                // Start new thread for the client.
                new ClientServerThread(newConnection, this).start();
            }
        }
        catch (IOException e)
        {
        }
        catch (ClassNotFoundException c)
        {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(30, 110, 360, 270);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(30, 50, 270, 30);

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(310, 50, 80, 30);

        status.setText("...");
        jPanel1.add(status);
        status.setBounds(30, 80, 300, 40);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Write your text here");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 30, 150, 20);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bg7.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 420, 405);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(417, 425));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_jButton1ActionPerformed
        
//        sendMessage(jTextField1.getText());
	jTextField1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

//        sendMessage(jTextField1.getText());
	jTextField1.setText("");
    }//GEN-LAST:event_jTextField1ActionPerformed

//    private void sendMessage(String message){
//        try{
//            output.writeObject("Server - " + message);
//            output.flush();
//            chatArea.append("\nServer - "+message);
//        }
//        catch(IOException ioException){
//            chatArea.append("\n Unable to Send Message");
//        }
//    }

    public JTextArea getChatArea()
    {
        return chatArea;
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}
