public class Client_1
{

    public static void main(String[] args)
    {
        Client client=new Client("127.0.0.1", args[0]);
        //Client client=new Client("137.158.58.86");
        client.startRunning();
    }
}
