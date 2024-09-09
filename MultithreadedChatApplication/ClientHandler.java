import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    // Constructor that accepts a Socket parameter
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error in ClientHandler: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            // Handle client interaction (read and write messages)
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                // Respond to the client or process the message here
                out.println("Echo: " + message); // Simple echo server for now
            }
        } catch (IOException e) {
            System.err.println("Error in ClientHandler: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
