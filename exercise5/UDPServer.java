import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPServer extends Thread {
    public static void main (String[] args) throws Exception{
        String feedback;
        DatagramPacket sendingPacket;
        DatagramPacket receivingPacket;

        DatagramSocket ds = new DatagramSocket(1250);
        System.out.println("The server is running...");

        while (true){

            //Receives packet from client
            byte[] receivingBytes = new byte[1024];
            receivingPacket = new DatagramPacket(receivingBytes, receivingBytes.length);
            ds.receive(receivingPacket);
            String str = new String(receivingPacket.getData());

            //Finds operation
            String[] res = str.split(" ");
            String op = res[0].toLowerCase();

            //If operation does not exist.
            if (!(op.equals("add") || op.equals("sub") || op.equals("multi") || op.equals("div"))){
                feedback = "Illegal operation. Try again";

            } else {

                double sum;
                double num1 = Double.parseDouble(res[1]);
                double num2 = Double.parseDouble(res[2]);

                switch (op) {

                    case "add":
                        sum = num1 + num2;
                        break;

                    case "sub":
                        sum = num1 - num2;
                        break;

                    case "multi":
                        sum = num1 * num2;
                        break;

                    case "div":
                        sum = num1 / num2;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + op);
                }
                feedback = "Your result: " + sum;
            }

            //Sends result back to client.
            byte[] b2 = feedback.getBytes();
            InetAddress ipAdress = InetAddress.getLocalHost();
            sendingPacket = new DatagramPacket(b2, b2.length, ipAdress, receivingPacket.getPort());
            ds.send(sendingPacket);
            System.out.println("To client: "+ feedback);

        }


    }
}
