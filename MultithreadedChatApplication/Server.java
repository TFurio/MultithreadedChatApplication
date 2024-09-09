import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    // List to store all active client handlers
    private static Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // Create a server socket
            serverSocket = new ServerSocket(1234);
            System.out.println("Server is running on port 1234...");

            // Keep listening for incoming client connections
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept incoming client connections
                System.out.println("New client connected: " + clientSocket);

                // Create a new ClientHandler for the new client and start the thread
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error in Server: " + e.getMessage());
        } finally {
            // Close the server socket in the finally block to ensure it's always closed
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                    System.out.println("Server socket closed.");
                }
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
    }
}
