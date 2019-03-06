//package Client;

//import Compress.CompressionUtils;
//import Message.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;

public class Client extends javax.swing.JFrame implements Runnable{
    private final String downloadMsg = "Do you want to download file: %s; sent by %s of size: %d? [Y/N]";
    private String fileToDownload;
    private String defaultDownloadLocation = System.getProperty("user.home") + "\\Desktop";

    private ObjectOutputStream output;
    private Message message = null;
    private String serverIP;
    private Socket connection;
    private boolean waitingForDownloadResponse = false;

    private String otherClient = "";
    private String clientName = "";
    List<Message> messages = new ArrayList<>();

    // Group-merge stuff
//    private InputStream in;
//    private OutputStream out;

//    private DataInputStream dIn;
//    private DataOutputStream dOut;

    public Client(Socket socket, String thisClient, String other , ObjectOutputStream out){

        initComponents();
        this.connection = socket;
        this.clientName = thisClient;
        this.otherClient = other;
        this.setTitle(otherClient);
        this.output = out;
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void run() {

    }

//    public void signOff() throws IOException
//    {
//        status.setText("Signing off...");
//
//        // TODO: Send sign-off message to server.
//        Message signOffMsg = new Message("M", clientName, "SignOff", Arrays.asList("Server"));
//        output.writeObject(signOffMsg.toString());
//        output.flush();
//
//        // house-keeping.
//        output.close();
//        input.close();
//        connection.close();
//    }
//
//    public void signOn() throws IOException {
//        // Fetch the connection.
//        status.setText("Attempting Connection ...");
//        try{
//            connection = new Socket(InetAddress.getByName(serverIP),port);
//        }catch(IOException ioException){
//            JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);
//        }
//        status.setText("Connected to: " + connection.getInetAddress().getHostName());
//
//        // Instantiate the output Object.
//        output = new ObjectOutputStream(connection.getOutputStream());
//        output.flush();
//
//        // Send sign-on message to the server till connected/timed-out.
//        while(!signedOn)
//        {
//            // send message.
//            output.writeObject(Message.generateMd5(clientName) + clientName);
//            output.flush();
//
//            // process server response.
//            String acknowledge = null;
//
//            try
//            {
//                acknowledge = (String) input.readObject();
//            }
//            catch (ClassNotFoundException e)
//            {
//                e.printStackTrace();
//            }
//            signedOn = "Successful".equals(acknowledge);
//        }
//        status.setText("Signed-on to: " + connection.getInetAddress().getHostName());
//    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtFileName = new javax.swing.JTextField();
        lblFileTitle = new javax.swing.JLabel();
        lblFileName = new javax.swing.JLabel();
        btnSendFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(null);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(30, 390, 390, 30);

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(430, 390, 80, 30);

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(30, 40, 480, 350);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Write your text here");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 10, 150, 20);

        status.setText("...");
        jPanel1.add(status);
        status.setBounds(40, 10, 300, 40);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 420, 410);
        jPanel1.add(txtFileName);
        txtFileName.setBounds(110, 460, 270, 27);

        lblFileTitle.setText("To send a file. Make sure it is in the Client folder.");
        jPanel1.add(lblFileTitle);
        lblFileTitle.setBounds(30, 430, 340, 30);

        lblFileName.setText("File name :");
        jPanel1.add(lblFileName);
        lblFileName.setBounds(30, 470, 90, 17);

        btnSendFile.setText("Send file");
        btnSendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendFileActionPerformed(evt);
            }
        });
        jPanel1.add(btnSendFile);
        btnSendFile.setBounds(390, 460, 110, 29);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(558, 542));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_jTextField1ActionPerformed
        jButton1ActionPerformed(evt);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_jButton1ActionPerformed
      /* If the server and other user are waiting for a download response, this should be flagged as a download
      * response.
      */
     if(waitingForDownloadResponse)
     {
         sendMessage(jTextField1.getText() + "|" + fileToDownload, "D");
         jTextField1.setText("");

         // change the flag.
         waitingForDownloadResponse = false;

         return;
     }else{
        sendMessage(jTextField1.getText(), "M");
        jTextField1.setText("");
     }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendFileActionPerformed
        // If uploads field is populated, it sends a file instead.
        // The logic is such that the sender sends the upload request, and once atleast one person confirms, the program
        // zips the file and uploads it in the background to the server which passes it on.
        String uploadFileName = txtFileName.getText();
        if(!txtFileName.getText().isEmpty())
        {
            sendMessage(
                    String.format(downloadMsg, uploadFileName, clientName, CompressionUtils.getFileSize(uploadFileName)),
                    "F");
        }
        txtFileName.setText("");
    }//GEN-LAST:event_btnSendFileActionPerformed

    public void receivedMessage(Message in){
        message = in;
        if("R".equals(message.getMessageFlag()))
        {
            chatArea.append("\n" + message.getSenderName() + " - " + (String)message.getMessage());
            return;
        }
        else if("F".equals(message.getMessageFlag())) // 2 - Handle request to upload a file.
        {
            String msgText = (String)message.getMessage();
            chatArea.append("\n" + msgText);

            // Store the name of the file we want to download:
            fileToDownload = msgText.substring(msgText.indexOf(':') + 2, msgText.indexOf(';'));

            // Raise the flag for download responses:
            waitingForDownloadResponse = true;

            return;
        }
        else if("D".equals(message.getMessageFlag())) // 3 - Auto-compress and send the file in the background.
        {
            String[] downloadResponse = ((String) message.getMessage()).split("[|]");
            System.out.println("sending file");
            System.out.println("name: " + downloadResponse[1]);

            // Any response besides y/Y is taken as a rejection for downloads.
            if(!"Y".equalsIgnoreCase(downloadResponse[0]))
            {
                chatArea.append("\n" + message.getSenderName() + " refused to download the file.");
            }
            else
            {
                // Compress the file, send and delete the compressed file:
                if(CompressionUtils.compressFile(downloadResponse[1]))
                {
                    int endIndex = downloadResponse[1].lastIndexOf('.');
                    endIndex = endIndex > -1 ? endIndex : downloadResponse[1].length();

                    String fileName = downloadResponse[1].substring(0, endIndex) + ".zip";
                    File fileToSend = new File(fileName);
                    sendMessage(fileToSend, "S");

                    // CLEAN UP THE ZIPPED FILES.
                    if(fileToSend.delete())
                    {
                        System.out.println("System cleaned up file : " + fileName);
                    }
                    else
                    {
                        System.out.println("System failed to cleanup: " + fileName);
                    }
                }
                else
                {
                    chatArea.append("\nError - Failed to compress the file: " + downloadResponse[1]);
                }
            }
        }

        // 3 - Notify the user and immediately skip if message fails the MD5 validation.
        if(!Message.isValidMsg(message))
        {
            chatArea.append(String.format(
                    "\nReceived an invalid message from <%s> with unique id <%s>",
                    message.getSenderName(),
                    message.getUid()));

            // inform the user
            sendMessage("Last communication was invalid. Please send again.", "R");
            //continue;
        }

        // 3 - If text, display.
        Object myMessageContent = message.getMessage();
        if(myMessageContent instanceof String)
        {
            chatArea.append("\n" + message.getSenderName() + " - " + (String)myMessageContent);
        }
        else // 4 - If file content, download and inform the user.
        {
            File myFile = (File) myMessageContent;

            if(CompressionUtils.decompress(myFile, defaultDownloadLocation))
            {
                chatArea.append("\n"
                        + String.format(
                        "Downloaded file <%s> from %s",
                        myFile.getName(),
                        message.getSenderName()));
            }
            else
            {
                chatArea.append("\n"
                        + String.format(
                        "Failed to download file <%s> from %s",
                        myFile.getName(),
                        message.getSenderName()));
            }
        }
    }



    private void sendMessage(Object message, String flag){
        try{
            // Generate the message object.
            Message toSend = new Message(flag, clientName, otherClient, message);

            // Send the message.
            output.writeObject(toSend);
            output.flush();
            if (!flag.equals("F")){
                chatArea.append("\n" + "Me" + " - " + message);
            }

            // Add the sent message to the Client's message list.
            messages.add(toSend);
        }
        catch(IOException ioException){
            chatArea.append("\n Unable to Send Message: <" + (message instanceof File ? ((File) message).getName() : message) + ">");
        }
    }

//    /*----------------------------------------------------------------------------------------------------------------*/
//    /**
//     *
//     * @param ackString The acknowledgement message sent by the recipient.
//     */
//    public void processAcknowledgement(String ackString){
//        String[] splitAck = ackString.substring(1).split("[:]");
//        Message msgToAcknowledge = getMessageByUid(splitAck[0]);
////        msgToAcknowledge.getRecipientInfo().put(splitAck[1], true);
//    }
//
//    /**
//     * Sends an acknowledgement message for a received message.
//     *
//     * Format: ~UniqueID:ClientName
//     *
//     * @param message The received message for which to generate and send an acknowledgement.
//     */
//    public void sendAcknowledgement(String message){
//        String uid = message.substring(0, message.indexOf(':'));
//
//        try{
//            output.writeObject("~" + uid + clientName);
//            output.flush();
//        }
//        catch(IOException ioException){
//            chatArea.append("\n Unable to Send Acknowledgement Message");
//        }
//    }

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

    /*----------------------------------------------------------------------------------------------------------------*/


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSendFile;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblFileName;
    private javax.swing.JLabel lblFileTitle;
    private javax.swing.JLabel status;
    private javax.swing.JTextField txtFileName;
    // End of variables declaration//GEN-END:variables
}
