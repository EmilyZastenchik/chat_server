/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newPackage;

/**
 *
 * @author ZASTY09
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.in;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;

public class ChatServer {
    private static final int PORT = 8888;
    private static final HashSet<String> names = new HashSet<>();
    private static final HashSet<String> userNames = new HashSet<>();
    private static final HashSet<PrintWriter> writers = new HashSet<>();
    private static int usersConnected = 0;

    public static void main(String[] args) {
        System.out.println(new Date() + "\nChat Server online.\n");

        try (ServerSocket chatServer = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = chatServer.accept();
          //      new ClientHandler(socket).start();
            }
        } catch (IOException ioe) {}
    }

    private static String names() {
        StringBuilder nameList = new StringBuilder();

        for (String name : userNames) {
            nameList.append(", ").append(name);
        }

        return "Connected : " + nameList.substring(2);
    }
    private Object socket;
 private static class ClientHandler extends Thread {
        private String name;
        private String serverSideName;
        new Thread( () -> {
        try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8888);
      
  
      //Listen for a connection request
      Socket socket = serverSocket.accept();

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
         }  catch(IOException ex) {
        ex.printStackTrace();
      }
    });
  }
      //  public ClientHandler(Socket socket) {
      //      this.socket = socket;
      //  }

        public void run() {
            try {
               

                System.out.println("SUBMIT_NAME");
                name = in.readLine();
                serverSideName = name.toLowerCase();

                synchronized (names) {
                    while (names.contains(serverSideName) || name == null || name.trim().isEmpty()) {
                        System.out.println("RESUBMIT_NAME");
                        name = in.readLine();
                        serverSideName = name.toLowerCase();
                    }
                }

                System.out.println("NAME_ACCEPTED");
                System.out.println(name + " connected. IP: " + socket.getInetAddress().getHostAddress());

                messageAll("CONNECT" + name);
                userNames.add(name);
                names.add(serverSideName);
                writers.add(out);
                System.out.println("INFO" + ++usersConnected + names());


                while (true) {
                    String input = in.readLine();

                    if (input == null || input.isEmpty()) {
                        continue;
                    }

                    messageAll("MESSAGE " + name + ": " + input);
                }
            } catch (IOException e) {
                if (name != null) {
                    System.out.println(name + " disconnected.");
                    userNames.remove(name);
                    names.remove(serverSideName);
                    writers.remove(out);
                    messageAll("DISCONNECT" + name);
                    usersConnected--;
                }   
            } finally {     
                socket.close();
            }
        }
    

    private static void messageAll(String... messages) {
        if (!writers.isEmpty()){
            for (String message : messages) {
                for (PrintWriter writer : writers) {
                    writer.println(message);
                }
            }
        }
    }
    }
