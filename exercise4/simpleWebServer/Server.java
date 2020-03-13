package simpleWebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main (String[] args) throws IOException {

        //Waiting for client to connect
        ServerSocket server = new ServerSocket(80);
        System.out.println("Listening for connection on port 80 ....");

        //
        Socket clientSocket = server.accept();
        InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        PrintWriter printer = new PrintWriter(clientSocket.getOutputStream(), true);


        //add lines from terminal window to server
        String line = reader.readLine();
        ArrayList<String> header = new ArrayList<>();
        while (!line.isEmpty()) {
            header.add(line);
            System.out.println(line);
            line = reader.readLine();
        }

        printer.println("HTTP/1.0 200 OK\n");
        printer.println("Content-Type: text/html; charset=utf-8\n");
        printer.println("\n<HTML\n<BODY>\n");
        printer.println("<H1> Hilsen. Du har koblet deg opp til min enkle web-tjener </h1>\n ");

        //lists up the lines from the terminal window
        printer.println("<UL>");
        for (int i = 0; i < header.size(); i++){
            printer.println("<LI>" + header.get(i) + "<LI>");

        }
        printer.println("</UL>\n");
        
        printer.println("</BODy>\n");
        printer.println("</HTML>\n");
        clientSocket.close();

    }
}
