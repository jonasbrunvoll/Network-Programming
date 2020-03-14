import java.net.*;
import java.util.Scanner;

class UDPClient {
    public static void main(String args[]) throws Exception {
        DatagramPacket sendingPacket;
        DatagramPacket receivingPacket;

        DatagramSocket ds = new DatagramSocket();  //Socket for connection
        Scanner reader = new Scanner(System.in);
        InetAddress ipAdress = InetAddress.getLocalHost(); //Finds ip-adress to ocal host
        System.out.println("Client is running");


        System.out.println("Choose operation");
        String line = reader.nextLine();

        while (line != null){

            System.out.println("Number 1");
            String num1 = reader.nextLine();


            System.out.println("Number 2");
            String num2 = reader.nextLine();


            String sendingData = line +" "+num1 +" "+ num2;
            byte[] sendingDataBytes = sendingData.getBytes();

            // sends data to the server. Needs to be sent as bytes
            sendingPacket = new DatagramPacket(sendingDataBytes, sendingDataBytes.length, ipAdress, 1250);
            ds.send(sendingPacket);

            //receiving data from server
            byte[] receivingBytes = new byte[1024];
            receivingPacket = new DatagramPacket(receivingBytes,receivingBytes.length);
            ds.receive(receivingPacket);

            //Print out resualt from server
            String str = new String(receivingPacket.getData());
            System.out.println(str.trim());

            //Prepare client for new operation
            System.out.println("Choose operation");
            line = reader.nextLine();

        }
    }
}