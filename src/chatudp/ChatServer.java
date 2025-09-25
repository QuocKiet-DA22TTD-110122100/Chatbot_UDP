package chatudp;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * UDP Chat Server
 * Nhận tin nhắn từ client và broadcast lại cho tất cả client khác
 * Minh họa đặc điểm connectionless của UDP
 */
public class ChatServer {
    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 1024;
    
    private DatagramSocket serverSocket;
    private List<InetSocketAddress> clients;
    private ExecutorService executor;
    private boolean running;
    
    public ChatServer() {
        clients = new ArrayList<>();
        executor = Executors.newCachedThreadPool();
    }
    
    public void start() {
        try {
            serverSocket = new DatagramSocket(PORT);
            running = true;
            
            System.out.println("=".repeat(50));
            System.out.println("🚀 UDP Chat Server started on port " + PORT);
            System.out.println("📡 Waiting for clients to connect...");
            System.out.println("=".repeat(50));
            
            // Thread chính để nhận tin nhắn
            while (running) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                
                try {
                    serverSocket.receive(packet);
                    
                    // Xử lý tin nhắn trong thread riêng
                    executor.submit(() -> handleMessage(packet));
                    
                } catch (IOException e) {
                    if (running) {
                        System.err.println("❌ Error receiving packet: " + e.getMessage());
                    }
                }
            }
            
        } catch (SocketException e) {
            System.err.println("❌ Failed to start server: " + e.getMessage());
        } finally {
            stop();
        }
    }
    
    private void handleMessage(DatagramPacket packet) {
        try {
            String message = new String(packet.getData(), 0, packet.getLength());
            InetSocketAddress clientAddress = new InetSocketAddress(
                packet.getAddress(), packet.getPort()
            );
            
            // Thêm client vào danh sách nếu chưa có
            if (!clients.contains(clientAddress)) {
                clients.add(clientAddress);
                System.out.println("👤 New client connected: " + clientAddress);
                System.out.println("📊 Total clients: " + clients.size());
            }
            
            // Log tin nhắn nhận được
            System.out.println("📨 [Server] Received from " + clientAddress + ": " + message);
            
            // Broadcast tin nhắn đến tất cả client khác
            broadcastMessage(message, clientAddress);
            
        } catch (Exception e) {
            System.err.println("❌ Error handling message: " + e.getMessage());
        }
    }
    
    private void broadcastMessage(String message, InetSocketAddress sender) {
        byte[] data = message.getBytes();
        int sentCount = 0;
        
        for (InetSocketAddress client : clients) {
            // Không gửi lại cho người gửi
            if (!client.equals(sender)) {
                try {
                    DatagramPacket packet = new DatagramPacket(
                        data, data.length, client.getAddress(), client.getPort()
                    );
                    serverSocket.send(packet);
                    sentCount++;
                } catch (IOException e) {
                    System.err.println("❌ Failed to send to " + client + ": " + e.getMessage());
                    // Loại bỏ client không thể gửi được
                    clients.remove(client);
                }
            }
        }
        
        System.out.println("📤 [Server] Broadcasted to " + sentCount + " clients");
    }
    
    public void stop() {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        if (executor != null) {
            executor.shutdown();
        }
        System.out.println("🛑 Server stopped");
    }
    
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        
        // Thêm shutdown hook để dừng server gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n🛑 Shutting down server...");
            server.stop();
        }));
        
        server.start();
    }
}


