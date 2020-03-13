package handleClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ThreadClient extends Thread {
    private Socket connection;
    public static int clients = 0;
    double sum;
    String symbol;
    String feedback;

    public ThreadClient(Socket connection) {
        this.connection = connection;

    }

    private boolean isOperation(String op){
        boolean result = false;
        if ((op.equals("add") || op.equals("sub") || op.equals("multi") || op.equals("div"))){
            result = true;
        }
        return result;
    }

    public void run() {
        clients++;
        int client = clients;
        try {

            System.out.println("Client"+clients+ " connected.");

            InputStreamReader readConnection = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(readConnection);
            PrintWriter printer = new PrintWriter(connection.getOutputStream(), true);

            printer.println("You are connected to the servent");

            String line = reader.readLine();

            while (line != null){
                String[] res = line.split(" ");
                String operation = res[0].toLowerCase();

                if (!isOperation(operation)){
                    feedback = "Illegal operation. Try again";
                    System.out.println("To client" + client + ": "+feedback);
                    printer.println(feedback);

                } else {

                    double num1 = Double.parseDouble(res[1]);
                    double num2 = Double.parseDouble(res[2]);

                    switch (operation) {

                        case "add":
                            sum = num1 + num2;
                            symbol = "+";
                            break;

                        case "sub":
                            sum = num1 - num2;
                            symbol = "-";
                            break;

                        case "multi":
                            sum = num1 * num2;
                            symbol = "*";
                            break;

                        case "div":
                            sum = num1 / num2;
                            symbol = "/";
                            break;
                    }
                    printer.println(res[1] + " " + symbol + " " + res[2] + " = " + sum);
                    feedback = "To client" + client + ": " + res[1] + " " + symbol + " " + res[2] + " = " + sum;
                    System.out.println(feedback);

                }

                line = reader.readLine();
            }

        } catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
