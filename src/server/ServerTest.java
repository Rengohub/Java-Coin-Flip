package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTest {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345; // Test Port for server

        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // CREATE Operation
            out.println("CREATE");
            out.println("username=user1,password=pass1");
            System.out.println("CREATE response: " + in.readLine());

            // READ Operation
            out.println("READ");
            out.println("user1");
            System.out.println("READ response: " + in.readLine());

            // UPDATE Operation
            out.println("UPDATE");
            out.println("username=user1,password=newpass");
            System.out.println("UPDATE response: " + in.readLine());

            // DELETE Operation
            out.println("DELETE");
            out.println("user1");
            System.out.println("DELETE response: " + in.readLine());

        } catch (IOException e) {
            System.out.println("Client Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
