
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * This is a class to represent a chat message sent by Clients to the Server.
 */
public class Message implements Serializable {
    // The message flags define the type of content: "F" - file; "M" - message.
    private String messageFlag;
    private Object message;
    private String uid = UUID.randomUUID().toString();
    private ArrayList<String> recipientInfo = new ArrayList<>();
    private String md5;

    private String senderName;
    private String recName;

    public String getSenderName() {
        return senderName;
    }

    public String getMd5() {
        return md5;
    }

    public String getMessageFlag() {
        return messageFlag;
    }

    public String getRecName(){
        return recName;
    }

//     public static void main(String[] args) throws Exception
//     {
// //        System.out.println(Message.generateMd5("Hello World"));
// //        List<String> list = Arrays.asList("a", "b", "c");
// //        System.out.println(list);
// //        System.out.println(Message.generateMd5(new File("C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\10mb.txt")));
//         Message msg = new Message("M", "M", new File("C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\test.txt"));//" how now brown cow!");
//         System.out.println(msg);
//
//         //Saving of object in a file
//         FileOutputStream file = new FileOutputStream("C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\shit");
//         ObjectOutputStream out = new ObjectOutputStream(file);
//
//         // Method for serialization of object
//         out.writeObject(msg);
//
//         out.close();
//         file.close();
//
//         // Try deserialize:
//         Message msgIn = null;
//
//
//         // Reading the object from a file
//         FileInputStream file2 = new FileInputStream("C:\\Users\\maminimini\\Desktop\\2019\\CSC3002F\\shit");
//         ObjectInputStream in = new ObjectInputStream(file2);
//
//         // Method for deserialization of object
//         msgIn = (Message) in.readObject();
//
//         in.close();
//         file.close();
//
//         System.out.println("Object has been deserialized ");
//         System.out.println("flag = " + msgIn.messageFlag);
//         System.out.println("messageClass = " + msgIn.getClass().getName());
//         System.out.println("message is File = " + (msgIn.message instanceof File));
//         System.out.println("message = " + new String(Files.readAllBytes(((File) msgIn.message).toPath())));
//
// //        System.out.println(Arrays.asList("a,b,c".split("[,]")));
//     }

    public Message(String messageFlag, String senderName, String r, Object message) {
        this.messageFlag = messageFlag;
        this.senderName = senderName;
        this.message = message;
        this.md5 = Message.generateMd5(message);
        this.recName = r;
    }

//    public String getMsgToSend() {
//        return uid + ":" + message;
//    }

    public Object getMessage() {
        return message;
    }

    public String getUid() {
        return uid;
    }

//    public ArrayList<String> getRecipientInfo() {
//        return recipientInfo;
//    }

    /**
     * Validates message content using md5.
     *
     * @param msg the Message to validate.
     * @return whether or not the message was sent correct.
     */
    public static boolean isValidMsg(Message msg)
    {
        String md5In = msg.getMd5();
        String md5Expected =  Message.generateMd5(msg.getMessage());

        return md5Expected.equals(md5In);
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer(messageFlag);
        buffer.append(':');
        buffer.append(uid);
        buffer.append('[');
        buffer.append(senderName);
        buffer.append(']');
        buffer.append(recipientInfo);
        buffer.append(Message.generateMd5(message));
        buffer.append(message);

        return buffer.toString();
    }

    /**
     * A Utility method to get the MD5 Hash Value for a given String.
     *
     * @param input the String for which to calculate an MD5 Hash.
     * @return the MD5 Hash Value for a given String.
     *
     * Reference:     *
     * @see <a href="https://www.geeksforgeeks.org/md5-hash-in-java/">MD5 implementation source</a>
     */
    public static String generateMd5(Object input)
    {
        try
        {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest;
            if(input instanceof  String)
            {
                messageDigest = md.digest(((String) input).getBytes());
            }
            else
            {
                messageDigest = md.digest(Files.readAllBytes(((File) input).toPath()));
                //messageDigest = md.digest(((File) input).getBytes());
            }

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32)
            {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // a) For specifying wrong message digest algorithms.
        // b) When the file path cannot be found (for files).
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
