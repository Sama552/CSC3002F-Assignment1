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
import java.io.File;
/**
 *
 * @author nrgsam001
 */



public class Connect extends javax.swing.JFrame {

    private Socket connection;
    public String username;
    ObjectOutputStream out;
    ObjectInputStream in;
    public volatile Map<String, Client> chats = new HashMap();
    /**
     * Creates new form Connect
     */
    public Connect(Socket socket,String name, ObjectInputStream in, ObjectOutputStream out) {
        initComponents();
        this.setTitle("Connect");
        this.setVisible(true);
        this.connection = socket;
        this.username = name;
        this.in = in;
        this.out = out;
        System.out.println(new File(".").getAbsoluteFile());
        startRunning();
    }

    private void startRunning(){
      MessageListen listen = new MessageListen(this, connection, in, out);
      Thread t = new Thread(listen);
      t.start();

    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spClients = new javax.swing.JScrollPane();
        lstClients = new javax.swing.JList<>();
        btnConnect = new javax.swing.JButton();
        title = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lstClients.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        spClients.setViewportView(lstClients);

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        title.setText("Choose a client to connect to");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(22, 22, 22))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addComponent(spClients, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(title)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(title)
                .addGap(18, 18, 18)
                .addComponent(spClients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {
      if (lstClients.getSelectedIndex() == -1){
        JOptionPane.showMessageDialog(null,"You didn't select anyone...","Warning",JOptionPane.WARNING_MESSAGE);
        return;
      }
      String response = lstClients.getSelectedValue();
      if(chats.containsKey(response)){
        JOptionPane.showMessageDialog(null,"You are already connected to that guy. Stop trying to break me!","Warning",JOptionPane.WARNING_MESSAGE);
        return;
      }
      Client other = new Client(connection, username, response, out);
      Thread t = new Thread(other);
      t.start();
      chats.put(response, other);
      Message sendConnect = new Message("C", username, response, "connect me");
      try{
        out.writeObject(sendConnect);
      }catch(IOException e){
        e.printStackTrace();
      }
    }//GEN-FIRST:event_btnConnectActionPerformed


    public void updateList(String[] names){
      lstClients.setModel(new javax.swing.AbstractListModel<String>() {
          String[] strings = names;
          public int getSize() { return strings.length; }
          public String getElementAt(int i) { return strings[i]; }
      });
      lstClients.updateUI();
    }

    private javax.swing.JButton btnConnect;
    private javax.swing.JList<String> lstClients;
    private javax.swing.JScrollPane spClients;
    private javax.swing.JLabel title;

    }//GEN-LAST:event_btnConnectActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables

    // End of variables declaration//GEN-END:variables
