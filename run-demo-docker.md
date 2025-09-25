# 🐳 Kịch bản Demo UDP Chat với Docker

## 📋 Chuẩn bị Demo với Docker

### Thời gian: 8-12 phút
### Người thực hiện: 1-2 người
- 1 người điều khiển Docker
- 1 người tương tác với client

### Chuẩn bị:
- Máy tính có Docker và Docker Compose
- Terminal/Command Prompt
- Slide trình chiếu (tùy chọn)

---

## 🎬 Kịch bản Demo Docker Chi tiết

### **Bước 1: Giới thiệu Docker Setup (1 phút)**

**Người trình bày:**
> "Chúng ta sẽ demo UDP Chat bằng Docker để dễ triển khai và quản lý. Docker giúp đóng gói ứng dụng Java thành container, dễ chạy trên mọi môi trường."

**Slide:**
- Docker benefits: Portable, Scalable, Isolated
- Container vs VM
- Microservices architecture

---

### **Bước 2: Khởi động với Docker Compose (2 phút)**

**Thực hiện:**
```bash
# Chạy script demo tự động
chmod +x scripts/demo-docker.sh
./scripts/demo-docker.sh
```

**Hoặc chạy trực tiếp:**
```bash
# Chạy server + 3 client tự động
docker-compose up --build
```

**Kết quả mong đợi:**
```
Creating network "udpchatdemo_udp-chat-network" with driver "bridge"
Creating udp-chat-server ... done
Creating udp-chat-alice ... done
Creating udp-chat-bob ... done
Creating udp-chat-charlie ... done
```

**Giải thích:**
> "Docker Compose tạo network riêng cho các container, server và client có thể giao tiếp với nhau. Mỗi container chạy độc lập."

---

### **Bước 3: Quan sát Server Logs (2 phút)**

**Thực hiện:**
```bash
# Terminal mới để xem logs
docker-compose logs -f udp-server
```

**Kết quả mong đợi:**
```
udp-chat-server | ==================================================
udp-chat-server | 🚀 UDP Chat Server started on port 5000
udp-chat-server | 📡 Waiting for clients to connect...
udp-chat-server | ==================================================
udp-chat-server | 👤 New client connected: /172.20.0.3:12345
udp-chat-server | 📊 Total clients: 1
```

**Giải thích:**
> "Server container đang lắng nghe UDP port 5000. Khi client kết nối, server tự động thêm vào danh sách."

---

### **Bước 4: Tương tác với Client (3 phút)**

**Thực hiện:**
```bash
# Attach vào client container
docker attach udp-chat-alice
```

**Nhập username và chat:**
```
👤 Enter your username: Alice
✅ Connected as: Alice
💬 Type your messages (type 'quit' to exit):
[Alice]: Hello everyone!
```

**Tương tự với client khác:**
```bash
# Terminal khác
docker attach udp-chat-bob
```

**Giải thích:**
> "Mỗi client chạy trong container riêng. Docker network cho phép chúng giao tiếp với server qua UDP."

---

### **Bước 5: Minh họa đặc điểm UDP (3 phút)**

#### **5.1. Gửi tin nhắn liên tiếp**

**Thực hiện:**
- Gửi 5 tin nhắn nhanh liên tiếp từ Alice
- Quan sát thứ tự hiển thị ở Bob và Charlie

**Giải thích:**
> "UDP không đảm bảo thứ tự tin nhắn. Trong container, network latency thấp nhưng vẫn có thể thấy sự khác biệt."

#### **5.2. Simulate network issues**

**Thực hiện:**
```bash
# Tạm dừng một client
docker pause udp-chat-charlie

# Gửi tin nhắn từ Alice
# Charlie sẽ không nhận được

# Resume client
docker unpause udp-chat-charlie
```

**Giải thích:**
> "Docker cho phép simulate network issues dễ dàng. Đây là đặc điểm unreliable của UDP."

#### **5.3. Scale up clients**

**Thực hiện:**
```bash
# Chạy thêm client mới
docker run -it --rm \
    --network udpchatdemo_udp-chat-network \
    --name udp-client-david \
    udpchatdemo_udp-server \
    java chatudp.ChatClient udp-chat-server 5000
```

**Giải thích:**
> "Docker giúp scale ứng dụng dễ dàng. Có thể chạy nhiều client mà không ảnh hưởng server."

---

### **Bước 6: So sánh với TCP (2 phút)**

**Slide so sánh:**

| Đặc điểm | UDP | TCP |
|----------|-----|-----|
| Connection | Connectionless | Connection-oriented |
| Reliability | Unreliable | Reliable |
| Speed | Fast | Slower |
| Docker | Lightweight | More overhead |
| Use cases | Gaming, Streaming | Web, Email |

**Giải thích:**
> "UDP container nhẹ hơn TCP vì không cần maintain connection state. Phù hợp cho microservices."

---

### **Bước 7: Cleanup và Kết luận (1 phút)**

**Thực hiện:**
```bash
# Dừng tất cả containers
docker-compose down

# Hoặc cleanup hoàn toàn
docker-compose down --rmi all --volumes --remove-orphans
```

**Tóm tắt:**
- ✅ Docker giúp triển khai UDP Chat dễ dàng
- ✅ Container isolation và networking
- ✅ Scale và quản lý ứng dụng hiệu quả
- ✅ Minh họa đặc điểm UDP trong môi trường containerized

**Kết thúc:**
> "Docker + UDP = Perfect combination cho real-time applications. Dễ deploy, scale, và maintain."

---

## 🎯 Tips cho Demo Docker

### **Trước khi demo:**
- Test Docker setup trước
- Chuẩn bị slide về Docker benefits
- Đảm bảo Docker daemon đang chạy
- Chuẩn bị backup plan (Java trực tiếp)

### **Trong khi demo:**
- Giải thích Docker concepts
- Show container logs real-time
- Demonstrate scaling capabilities
- Explain networking

### **Câu hỏi thường gặp:**
- **Q:** "Tại sao dùng Docker cho UDP chat?"
- **A:** "Docker giúp isolate, scale, và deploy dễ dàng. Phù hợp cho microservices."

- **Q:** "UDP có hoạt động tốt trong container không?"
- **A:** "Có, Docker network hỗ trợ UDP tốt. Thậm chí có thể simulate network issues."

- **Q:** "Có thể chạy trên cloud không?"
- **A:** "Có, Docker container có thể deploy lên AWS, GCP, Azure dễ dàng."

---

## 🚀 Advanced Docker Demo

### **Production Setup:**
```bash
# Build production image
docker build -t udp-chat:latest .

# Run với resource limits
docker run -d --name udp-server \
  --memory=512m --cpus=0.5 \
  -p 5000:5000/udp \
  udp-chat:latest
```

### **Monitoring:**
```bash
# Xem resource usage
docker stats

# Xem network traffic
docker exec udp-chat-server netstat -u
```

### **Logging:**
```bash
# Centralized logging
docker-compose logs --tail=100 -f
```

---

**🎉 Happy Docker Chatting!**


