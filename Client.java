import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static Socket client;

    public static void main(String[] args) {
        String userInput;
        PrintWriter message;
        
        try (Scanner input = new Scanner(System.in)) {
            client = new Socket(InetAddress.getLocalHost(), 8080);
            message = new PrintWriter(client.getOutputStream(), true);
            Scanner response = new Scanner(client.getInputStream());
            while ((userInput = input.nextLine()) != null) {
                if (userInput == "quit") {
                    client.close();
                }
                message.println(userInput);
                System.out.println(response.nextLine());
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }
    }
}