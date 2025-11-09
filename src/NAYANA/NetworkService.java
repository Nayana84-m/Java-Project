package NAYANA;

import java.io.*;
import java.net.*;

public class NetworkService {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    
    public void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Banking server started on port " + port);
    }
    
    public void acceptClient() throws IOException {
        clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress());
    }
    
    public void sendResponse(String response) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(response);
    }
    
    public String receiveRequest() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return in.readLine();
    }
    
    public void closeConnections() throws IOException {
        if (clientSocket != null) clientSocket.close();
        if (serverSocket != null) serverSocket.close();
    }
}
