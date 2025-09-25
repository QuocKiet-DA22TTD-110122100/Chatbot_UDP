# Dockerfile cho UDP Chat Application
FROM openjdk:11-jdk-slim

# Tạo thư mục làm việc
WORKDIR /app

# Copy source code
COPY src/chatudp/ ./src/chatudp/

# Biên dịch Java code
RUN javac -d . src/chatudp/*.java

# Expose port cho UDP
EXPOSE 5000/udp

# Default command (có thể override)
CMD ["java", "chatudp.ChatServer"]

