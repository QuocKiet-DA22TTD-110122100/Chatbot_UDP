package chatudp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Tiện ích cho UDP Chat Application
 * Cung cấp các hàm format tin nhắn, log, và các tiện ích khác
 */
public class Utils {
    
    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("HH:mm:ss");
    
    /**
     * Format tin nhắn với timestamp
     */
    public static String formatMessage(String username, String message) {
        String timestamp = getCurrentTime();
        return String.format("[%s] %s: %s", timestamp, username, message);
    }
    
    /**
     * Format log message cho server
     */
    public static String formatServerLog(String action, String details) {
        String timestamp = getCurrentTime();
        return String.format("[%s] [Server] %s: %s", timestamp, action, details);
    }
    
    /**
     * Lấy thời gian hiện tại
     */
    public static String getCurrentTime() {
        return LocalDateTime.now().format(TIME_FORMATTER);
    }
    
    /**
     * Tạo header cho console output
     */
    public static void printHeader(String title) {
        System.out.println("=".repeat(50));
        System.out.println("🎯 " + title);
        System.out.println("=".repeat(50));
    }
    
    /**
     * Tạo separator line
     */
    public static void printSeparator() {
        System.out.println("-".repeat(50));
    }
    
    /**
     * Format thông báo kết nối
     */
    public static String formatConnectionMessage(String clientInfo) {
        return String.format("👤 Client connected: %s", clientInfo);
    }
    
    /**
     * Format thông báo broadcast
     */
    public static String formatBroadcastMessage(int clientCount) {
        return String.format("📤 Broadcasted to %d clients", clientCount);
    }
    
    /**
     * Kiểm tra xem tin nhắn có hợp lệ không
     */
    public static boolean isValidMessage(String message) {
        return message != null && !message.trim().isEmpty() && message.length() <= 1000;
    }
    
    /**
     * Làm sạch tin nhắn (loại bỏ ký tự đặc biệt có thể gây lỗi)
     */
    public static String sanitizeMessage(String message) {
        if (message == null) return "";
        return message.trim().replaceAll("[\\r\\n]", " ");
    }
    
    /**
     * Tạo thông báo demo UDP
     */
    public static void printUDPDemoInfo() {
        System.out.println("📡 UDP Chat Demo - Minh họa đặc điểm UDP:");
        System.out.println("   • Connectionless - Không cần kết nối trước");
        System.out.println("   • Fast - Nhanh, overhead thấp");
        System.out.println("   • Unreliable - Có thể mất gói, sai thứ tự");
        System.out.println("   • No ACK - Không có xác nhận nhận tin");
        printSeparator();
    }
}


