#!/bin/bash

# Script chạy UDP Client với Docker
# Sử dụng khi server đã chạy

echo "👤 UDP Chat Client"
echo "=================="

# Kiểm tra server có đang chạy không
if ! docker ps | grep -q "udp-chat-server"; then
    echo "❌ Server chưa chạy. Vui lòng chạy server trước:"
    echo "   docker-compose -f docker-compose.server-only.yml up"
    exit 1
fi

echo "✅ Server đang chạy"

# Nhập username
read -p "👤 Nhập username của bạn: " username

if [ -z "$username" ]; then
    username="Anonymous"
fi

echo "🔗 Kết nối với server..."
echo "💬 Gõ tin nhắn (Ctrl+C để thoát)"
echo ""

# Chạy client container
docker run -it --rm \
    --network udpchatdemo_udp-chat-network \
    --name "udp-client-$username" \
    udpchatdemo_udp-server \
    java chatudp.ChatClient udp-chat-server 5000


