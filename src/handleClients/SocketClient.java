package handleClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main (String[] args) throws IOException {

        //Objectvariable
        final int PORTNR = 1250;
        String ip = "localhost";

        Scanner readerConnection = new Scanner(System.in);
        Socket connection = new Socket(ip, PORTNR);

        InputStreamReader readConnection = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(readConnection);
        PrintWriter printer = new PrintWriter(connection.getOutputStream(), true);

        String line = reader.readLine();

        while(line != null){
            System.out.println(line);

            System.out.println("Decide operation");
            String operation = readerConnection.nextLine();

            System.out.println("enter number1");
            String num1 = readerConnection.nextLine();

            System.out.println("enter number2");
            String num2 = readerConnection.nextLine();

            printer.println(operation + " " + num1 + " " + num2);

            line = reader.readLine();

        }
    }

}


