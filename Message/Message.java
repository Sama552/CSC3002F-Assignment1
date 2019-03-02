package Message;

import Client.Client;

import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This is a class to represent a chat message sent by Clients to the Server.
 */
public class Message {
    private String message;
    private String uid = UUID.randomUUID().toString();
    private HashMap<String, Boolean> recipientInfo = new HashMap<>();
    private String senderName;

    public static void main(String[] args)
    {
//        System.out.println(Message.getMd5("Hello World"));
//        List<String> list = Arrays.asList("a", "b", "c");
//        System.out.println(list);
        Message msg = new Message("M", " how now brown cow!", Arrays.asList("P", "LongJohn"));
        System.out.println(msg);
//        System.out.println(Arrays.asList("a,b,c".split("[,]")));
    }

    public Message(String senderName, String message, List<String> recipients) {
        this.senderName = senderName;
        this.message = message;
        for (String recipientName : recipients) {
            recipientInfo.put(recipientName, false);
        }
    }

    public String getMsgToSend() {
        return uid + ":" + message;
    }

    public String getMessage() {
        return message;
    }

    public String getUid() {
        return uid;
    }

    public HashMap<String, Boolean> getRecipientInfo() {
        return recipientInfo;
    }

    /**
     * Validates message content against the preceding MD5.
     *
     * @param msg the MD5-text combination.
     * @return whether or not the message was sent correct.
     */
    public static boolean validMsg(String msg)
    {
        String md5In = msg.substring(0, 32);
        String md5Expected =  Message.getMd5(msg.substring(32));

        return md5Expected.equals(md5In);
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer(uid);
        buffer.append('[');
        buffer.append(senderName);
        buffer.append(']');
        buffer.append(recipientInfo.keySet());
        buffer.append(Message.getMd5(message));
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
    public static String getMd5(String input)
    {
        try
        {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

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
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
}