 import java.io.IOException;
 import java.io.PrintWriter;
 import java.net.ServerSocket;
 import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
 public class Main {
    static ServerSocket server;
    static Socket socket;
    public static void main(String[] args) {
        try {
            server = new ServerSocket(8080);
            if (server.isClosed()) {
                System.out.println("No server.");
            } else {
                System.out.println("Server online.");
                boolean listening = true;
                while (listening) {
                    new ServerThread(server.accept()).start();
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port");
            System.out.println(e.getMessage());
        }
    }
    
    public static class ServerThread extends Thread {
        public ServerThread(Socket client) {
            super("ServerThread");
            socket = client;
            System.out.println(String.valueOf(socket.getPort()).concat(" connected"));
        }
 
        public void run() {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner in = new Scanner(socket.getInputStream());
                String inputLine;
                ServerProtocol serverProtocol = new ServerProtocol();
                while ((inputLine = in.nextLine()) != null) {
                    String outputLine = serverProtocol.processInput(inputLine);
                    out.println(outputLine);
                }
                socket.close();
            } catch (NoSuchElementException nosuchElementException) {
                System.out.println(socket.getPort() + " disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    static class ServerProtocol {
        private static final int WAITING = 0;
        private static final int WORKING = 1;
        private int state = WAITING;
        public String processInput(String message) {
            String theOutput = null;
            if (state == WAITING) {
                theOutput = "Waiting...";
                state = WORKING;
            } else if (state == WORKING) {
                theOutput = "Working...";
                state = WAITING;
            }
            return theOutput;
        }
    }
}