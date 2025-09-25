package chatudp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * UDP Chat Client
 * Kết nối đến server và cho phép gửi/nhận tin nhắn
 * Minh họa đặc điểm connectionless của UDP
 */
public class ChatClient {
    private static final int BUFFER_SIZE = 1024;
    private static final int TIMEOUT = 5000; // 5 seconds
    
    private DatagramSocket clientSocket;
    private InetAddress serverAddress;
    private int serverPort;
    private String username;
    private ExecutorService executor;
    private boolean running;
    
    public ChatClient(String serverHost, int serverPort) {
        this.serverPort = serverPort;
        this.executor = Executors.newCachedThreadPool();
        
        try {
            this.serverAddress = InetAddress.getByName(serverHost);
            this.clientSocket = new DatagramSocket();
            this.clientSocket.setSoTimeout(TIMEOUT);
        } catch (Exception e) {
            System.err.println("❌ Failed to initialize client: " + e.getMessage());
            System.exit(1);
        }
    }
    
    public void start() {
        Scanner scanner = new Scanner(System.in);
        
        // Nhập username
        System.out.println("=".repeat(50));
        System.out.println("🎯 UDP Chat Client");
        System.out.println("=".repeat(50));
        System.out.print("👤 Enter your username: ");
        this.username = scanner.nextLine().trim();
        
        if (username.isEmpty()) {
            username = "Anonymous";
        }
        
        System.out.println("✅ Connected as: " + username);
        System.out.println("💬 Type your messages (type 'quit' to exit):");
        System.out.println("=".repeat(50));
        
        running = true;
        
        // Thread để nhận tin nhắn
        executor.submit(this::receiveMessages);
        
        // Thread chính để gửi tin nhắn
        sendMessages(scanner);
    }
    
    private void sendMessages(Scanner scanner) {
        while (running) {
            System.out.print("[" + username + "]: ");
            String message = scanner.nextLine();
            
            if ("quit".equalsIgnoreCase(message.trim())) {
                running = false;
                break;
            }
            
            if (!message.trim().isEmpty()) {
                sendMessage(message);
            }
        }
        
        stop();
    }
    
    private void sendMessage(String message) {
        try {
            String fullMessage = "[" + username + "]: " + message;
            byte[] data = fullMessage.getBytes();
            
            DatagramPacket packet = new DatagramPacket(
                data, data.length, serverAddress, serverPort
            );
            
            clientSocket.send(packet);
            
            // Hiển thị tin nhắn của mình
            System.out.println("📤 You: " + message);
            
        } catch (IOException e) {
            System.err.println("❌ Failed to send message: " + e.getMessage());
        }
    }
    
    private void receiveMessages() {
        byte[] buffer = new byte[BUFFER_SIZE];
        
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(packet);
                
                String message = new String(packet.getData(), 0, packet.getLength());
                
                // Chỉ hiển thị tin nhắn từ người khác
                if (!message.startsWith("[" + username + "]:")) {
                    System.out.println("📨 " + message);
                }
                
            } catch (SocketTimeoutException e) {
                // Timeout là bình thường, tiếp tục lắng nghe
                continue;
            } catch (IOException e) {
                if (running) {
                    System.err.println("❌ Error receiving message: " + e.getMessage());
                }
            }
        }
    }
    
    public void stop() {
        running = false;
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
        }
        if (executor != null) {
            executor.shutdown();
        }
        System.out.println("👋 Goodbye!");
    }
    
    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 5000;
        
        // Cho phép override server host/port qua command line
        if (args.length >= 1) {
            serverHost = args[0];
        }
        if (args.length >= 2) {
            try {
                serverPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("❌ Invalid port number: " + args[1]);
                System.exit(1);
            }
        }
        
        System.out.println("🔗 Connecting to server: " + serverHost + ":" + serverPort);
        
        ChatClient client = new ChatClient(serverHost, serverPort);
        
        // Thêm shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n🛑 Disconnecting...");
            client.stop();
        }));
        
        client.start();
    }
}


