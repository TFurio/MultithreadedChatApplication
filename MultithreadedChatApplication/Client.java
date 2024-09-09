import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader input = null;
        PrintWriter output = null;

        try {
            // Connect to the server
            socket = new Socket("127.0.0.1", 1234);
            System.out.println("Connected to the server.");

            // Set up input and output streams
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            // Scanner to read from the console
            Scanner scanner = new Scanner(System.in);
            String userInput;

            // Keep sending messages to the server
            while (true) {
                System.out.println("Enter a message to send to the server: ");
                userInput = scanner.nextLine();
                output.println(userInput);

                // Exit the loop if the user types "exit"
                if (userInput.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the chat...");
                    break;
                }

                // Print the server's response
                String response = input.readLine();
                System.out.println("Server: " + response);
            }
        } catch (IOException e) {
            System.err.println("Error in Client: " + e.getMessage());
        } finally {
            // Close the socket and streams properly
            try {
                if (socket != null) {
                    socket.close();
                    System.out.println("Client socket closed.");
                }
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
