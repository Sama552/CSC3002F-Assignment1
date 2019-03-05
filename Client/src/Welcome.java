import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nrgsam001
 */
public class Welcome extends javax.swing.JFrame {

    private String username;
    private String serverIP;
    private Socket connection;
    private int port = 12000;

    private InputStream in;
    private OutputStream out;

    private ObjectInputStream Oin;
    private ObjectOutputStream Oout;
    /**
     * Creates new form Welcome
     */
    public Welcome() {
        initComponents();
        this.setVisible(true);
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        txtIP = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        lblIP = new javax.swing.JLabel();
        lblname = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ActionListener listener = new ActionListener(){
		        public void actionPerformed(ActionEvent evt){
                btnStartActionPerformed(evt);
		        }
	      };
        btnStart.addActionListener(listener);

        lblTitle.setText("Please Enter the Server's IP address and choose a username.");

        btnStart.setText("Start");

        lblIP.setText("Server IP:");

        lblname.setText("username");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIP)
                            .addComponent(lblname))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtIP)
                                .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIP))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblname))
                .addGap(36, 36, 36)
                .addComponent(btnStart)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt){
      serverIP = txtIP.getText();
      username = txtName.getText();
      try{
          try{
            connection = new Socket(InetAddress.getByName(serverIP),port);
          }catch(IOException ioEception){
            JOptionPane.showMessageDialog(null,"Could not connect to server at " + serverIP + ". Please check if the server is running and try again","Warning",JOptionPane.WARNING_MESSAGE);
          }

        Oout = new ObjectOutputStream(connection.getOutputStream());
        Oin = new ObjectInputStream(connection.getInputStream());

        String confirm = "";

        Oout.writeObject(username);
        Oout.flush();
        try{
          confirm = (String) Oin.readObject();
        }catch(ClassNotFoundException c){
          //TODO
        }

        if (confirm.equals("OK")){
            this.setVisible(false);
            Connect c = new Connect(connection, username, Oin, Oout);
            JOptionPane.showMessageDialog(null,"CONNECTED","Warning",JOptionPane.WARNING_MESSAGE);
        }else{
          JOptionPane.showMessageDialog(null,"Username already in use. Please choose a different username and try again","Warning",JOptionPane.WARNING_MESSAGE);
          connection.close();
          txtName.setText("");
        }
      }catch(IOException e){
          e.printStackTrace();
      }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblname;
    // End of variables declaration//GEN-END:variables
}
