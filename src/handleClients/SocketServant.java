package handleClients;

import java.io.*;
import java.net.*;

public class SocketServant {
    public static void main(String[] args) throws IOException {
        final int PORTNR = 1250;
        ServerSocket servant = new ServerSocket(PORTNR);
        System.out.println("The servent is running. Waiting for instructions...");
        while (true){
            Socket connection = servant.accept();
            Thread clientThread = new ThreadClient(connection);
            clientThread.start();
        }
    }
}

