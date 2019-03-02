package Client;

import Message.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

public class Client extends javax.swing.JFrame{

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message="";
    private String serverIP;
    private Socket connection;
    private int port = 6789;
    private boolean signedOn = false;

    public Client(String serverIp, String clientName){
        
        initComponents();

        this.clientName = clientName;
        this.setTitle(clientName);
        this.setVisible(true);
        status.setVisible(true);
        this.serverIP = serverIp;
    }

    public void signOff() throws IOException
    {
        status.setText("Signing off...");

        // TODO: Send sign-off message to server.
        Message signOffMsg = new Message(clientName, "SignOff", Arrays.asList("Server"));
        output.writeObject(signOffMsg.toString());
        output.flush();

        // house-keeping.
        output.close();
        input.close();
        connection.close();
    }

    public void signOn() throws IOException {
        // Fetch the connection.
        status.setText("Attempting Connection ...");
        try{
            connection = new Socket(InetAddress.getByName(serverIP),port);
        }catch(IOException ioException){
            JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);
        }
        status.setText("Connected to: " + connection.getInetAddress().getHostName());

        // Instantiate the output Object.
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        // Send sign-on message to the server till connected/timed-out.
        while(!signedOn)
        {
            // send message.
            output.writeObject(Message.getMd5(clientName) + clientName);
            output.flush();

            // process server response.
            String acknowledge = null;

            try
            {
                acknowledge = (String) input.readObject();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            signedOn = "Successful".equals(acknowledge);
        }
        status.setText("Signed-on to: " + connection.getInetAddress().getHostName());
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        recipientTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(null);

        // TODO: ADD LISTENER TO RECIPIENT TEXT FIELD.
        //     : ADD THE FIELD TO THE PANEL AND SET THE CORRECT BOUNDS.

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

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(30, 110, 360, 270);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Write your text here");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 30, 150, 20);

        status.setText("...");
        jPanel1.add(status);
        status.setBounds(30, 80, 300, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bg7.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 420, 410);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(414, 428));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_jTextField1ActionPerformed

        sendMessage(jTextField1.getText());
	jTextField1.setText("");
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_jButton1ActionPerformed

       sendMessage(jTextField1.getText());
	jTextField1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public void startRunning(){
       try{
           signOn();
            input = new ObjectInputStream(connection.getInputStream());

            whileChatting();
       }
       catch(IOException ioException){
            ioException.printStackTrace();
       }
    }
    
    private void whileChatting() throws IOException{
      jTextField1.setEditable(true);
      do{
              try{
                  /* TODO: ADD LOGIC HERE TO PROCESS THE MESSAGE (STRIP THE UID AND SEND AN ACKNOWLEDGEMENT) OR TO
                   *       PROCESS AN ACKNOWLEDGEMENT.
                   */

                  message = (String) input.readObject();
                  if(message.charAt(0) == '~') {
                      // Process the acknowledgement. TO BE CHANGED.
                      processAcknowledgement(message);

                  }
                  else{
                      // Post the message and send the acknowledgement.
                      chatArea.append("\n" + message.substring(message.indexOf(':') + 1));
                      sendAcknowledgement(message);
                  }
              }
              catch(ClassNotFoundException classNotFoundException){
              }
      }while(!message.equals("Client - END")); // TODO: MODIFY THIS TO MATCH CURRENT LOGIC.
    }

    // TODO:
    private void sendFiles(String filesToSend)
    {
        for(String fileName : filesToSend.split("[;]"))
        {
            // TODO: call compression logic here...
        }
    }
    
    private void sendMessage(String message){
        try{
            // Generate the message object.
            // TODO: EXTRACT INFO FROM RECIPIENT TEXTBOX AND PLACE IT BEFORE THE SPLIT.
            Message toSend = new Message(clientName, message, Arrays.asList("".split("[;]")));

            // Send the message.
            output.writeObject(toSend.toString());
            output.flush();
            chatArea.append("\n" + clientName + " - " + message);

            // Add the sent message to the Client's message list.
            messages.add(toSend);
        }
        catch(IOException ioException){
            chatArea.append("\n Unable to Send Message: <" + message + ">");
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    /**
     *
     * @param ackString The acknowledgement message sent by the recipient.
     */
    public void processAcknowledgement(String ackString){
        String[] splitAck = ackString.substring(1).split("[:]");
        Message msgToAcknowledge = getMessageByUid(splitAck[0]);
        msgToAcknowledge.getRecipientInfo().put(splitAck[1], true);
    }

    /**
     * Sends an acknowledgement message for a received message.
     *
     * Format: ~UniqueID:ClientName
     *
     * @param message The received message for which to generate and send an acknowledgement.
     */
    public void sendAcknowledgement(String message){
        String uid = message.substring(0, message.indexOf(':'));

        try{
            output.writeObject("~" + uid + clientName);
            output.flush();
        }
        catch(IOException ioException){
            chatArea.append("\n Unable to Send Acknowledgement Message");
        }
    }

    public Message getMessageByUid(String uid){
        for(Message msg : messages){
            if(uid.equals(msg.getUid())){
                return msg;
            }
        }

        return null;
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    /* Extra variables for features.
    /*-----------------------------*/
    private String clientName = "";
    List<Message> messages = new ArrayList<>();

    /*----------------------------------------------------------------------------------------------------------------*/
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // TODO: ADD TEXT FIELD FOR ;-DELIMITED LIST OF FILE PATHS.
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField recipientTextField;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}
