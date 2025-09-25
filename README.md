# 🚀 UDP Chat Application Demo

Một ứng dụng chat đơn giản sử dụng UDP protocol để minh họa các đặc điểm của UDP: **connectionless**, **fast**, **unreliable**.

## 📋 Mục tiêu

- Minh họa cơ chế hoạt động của User Datagram Protocol (UDP)
- Thể hiện đặc điểm: không kết nối, nhanh, không đảm bảo độ tin cậy
- Tạo demo trực quan cho việc trình bày về UDP vs TCP

## 🏗️ Kiến trúc

```
Client A ----->|
               |-----> Server -----> Client B
Client C ----->|                     (broadcast)
```

- **Server**: Nhận tin nhắn từ client và broadcast lại cho tất cả client khác
- **Client**: Gửi tin nhắn đến server và nhận tin nhắn từ server
- **UDP**: Mỗi tin nhắn là một datagram độc lập, không có kết nối

## 📂 Cấu trúc dự án

```
UDPChatDemo/
├── src/chatudp/
│   ├── ChatServer.java    # UDP Server
│   ├── ChatClient.java    # UDP Client  
│   └── Utils.java         # Tiện ích
├── README.md              # Hướng dẫn này
└── run-demo.md           # Kịch bản demo
```

## 🚀 Cách chạy

### 🐳 Phương pháp 1: Docker (Khuyến nghị)

#### Yêu cầu:
- Docker và Docker Compose đã cài đặt

#### Chạy nhanh:
```bash
# Chạy server + 3 client tự động
docker-compose up --build

# Hoặc chỉ chạy server
docker-compose -f docker-compose.server-only.yml up --build
```

#### Script demo tự động:
```bash
# Chạy script demo
chmod +x scripts/demo-docker.sh
./scripts/demo-docker.sh
```

#### Chạy client thủ công:
```bash
# Khi server đã chạy, chạy client mới
chmod +x scripts/run-client.sh
./scripts/run-client.sh
```

### ☕ Phương pháp 2: Java trực tiếp

#### 1. Biên dịch
```bash
# Biên dịch tất cả file Java
javac -d . src/chatudp/*.java
```

#### 2. Chạy Server
```bash
# Terminal 1: Chạy server
java chatudp.ChatServer
```

Bạn sẽ thấy:
```
==================================================
🚀 UDP Chat Server started on port 5000
📡 Waiting for clients to connect...
==================================================
```

#### 3. Chạy Client
```bash
# Terminal 2: Client 1 (Alice)
java chatudp.ChatClient

# Terminal 3: Client 2 (Bob)  
java chatudp.ChatClient

# Terminal 4: Client 3 (Charlie)
java chatudp.ChatClient
```

Mỗi client sẽ yêu cầu nhập username:
```
==================================================
🎯 UDP Chat Client
==================================================
👤 Enter your username: Alice
✅ Connected as: Alice
💬 Type your messages (type 'quit' to exit):
==================================================
[Alice]: 
```

## 🎭 Demo Script

### Bước 1: Khởi động Server
- Chạy `java chatudp.ChatServer`
- Server sẽ hiển thị "Server started on port 5000"

### Bước 2: Kết nối Clients
- Mở 2-3 terminal khác
- Chạy `java chatudp.ChatClient` ở mỗi terminal
- Nhập username khác nhau (Alice, Bob, Charlie)

### Bước 3: Chat Demo
- Alice gửi: "Hello everyone!"
- Bob gửi: "Hi Alice!"
- Charlie gửi: "Hello from Charlie!"

### Bước 4: Minh họa đặc điểm UDP
- Gửi nhiều tin nhắn liên tiếp để thấy thứ tự có thể bị đảo
- Giải thích: UDP không đảm bảo thứ tự tin nhắn
- Có thể thấy một số tin nhắn bị mất (đặc điểm unreliable)

## 🔧 Tính năng

### Server
- ✅ Lắng nghe trên port 5000
- ✅ Nhận tin nhắn từ bất kỳ client nào
- ✅ Broadcast tin nhắn đến tất cả client khác
- ✅ Log hoạt động real-time
- ✅ Quản lý danh sách client tự động

### Client  
- ✅ Nhập username khi kết nối
- ✅ Gửi tin nhắn đến server
- ✅ Nhận tin nhắn từ server (thread riêng)
- ✅ Hiển thị tin nhắn real-time
- ✅ Thoát bằng lệnh 'quit'

## 📊 Minh họa đặc điểm UDP

### ✅ Connectionless
- Không cần thiết lập kết nối trước khi gửi tin
- Mỗi tin nhắn là datagram độc lập

### ✅ Fast & Lightweight  
- Overhead thấp, gửi tin nhanh
- Không có ACK, không có flow control

### ✅ Unreliable
- Tin nhắn có thể bị mất
- Thứ tự tin nhắn có thể bị đảo
- Không có đảm bảo delivery

## 🎯 Ứng dụng thực tế

UDP phù hợp cho:
- **Streaming video/audio** (tốc độ quan trọng hơn độ tin cậy)
- **Online gaming** (latency thấp)
- **DNS queries** (nhanh, đơn giản)
- **IoT sensors** (dữ liệu real-time)

## 🛠️ Yêu cầu hệ thống

### 🐳 Với Docker (Khuyến nghị):
- Docker và Docker Compose
- Terminal/Command Prompt
- Không cần cài Java

### ☕ Với Java trực tiếp:
- Java 8 hoặc cao hơn
- Terminal/Command Prompt
- Không cần thư viện bên ngoài

## 📝 Lưu ý

- Server phải chạy trước khi client kết nối
- Có thể chạy nhiều client cùng lúc
- Để dừng: nhấn Ctrl+C hoặc gõ 'quit' ở client
- Port mặc định: 5000 (có thể thay đổi trong code)

## 🚀 Mở rộng

- Thêm GUI với Swing/JavaFX
- Gửi file qua UDP
- Mã hóa tin nhắn (AES)
- Thông báo join/leave
- Simulate packet loss

---

**Happy Chatting! 🎉**
