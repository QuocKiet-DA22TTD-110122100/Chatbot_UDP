#!/bin/bash

# Script demo UDP Chat với Docker
# Tác giả: UDP Chat Demo Team

echo "🚀 UDP Chat Demo với Docker"
echo "================================"

# Kiểm tra Docker
if ! command -v docker &> /dev/null; then
    echo "❌ Docker chưa được cài đặt. Vui lòng cài Docker trước."
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose chưa được cài đặt. Vui lòng cài Docker Compose trước."
    exit 1
fi

echo "✅ Docker và Docker Compose đã sẵn sàng"

# Menu lựa chọn
echo ""
echo "Chọn chế độ demo:"
echo "1. Chạy Server + 3 Client tự động"
echo "2. Chỉ chạy Server (chạy client thủ công)"
echo "3. Dừng tất cả containers"
echo "4. Xem logs"
echo "5. Cleanup (xóa containers và images)"

read -p "Nhập lựa chọn (1-5): " choice

case $choice in
    1)
        echo "🎬 Bắt đầu demo với Server + 3 Client..."
        docker-compose up --build
        ;;
    2)
        echo "🖥️  Chạy chỉ Server..."
        docker-compose -f docker-compose.server-only.yml up --build
        ;;
    3)
        echo "🛑 Dừng tất cả containers..."
        docker-compose down
        docker-compose -f docker-compose.server-only.yml down
        ;;
    4)
        echo "📋 Logs của containers:"
        docker-compose logs -f
        ;;
    5)
        echo "🧹 Cleanup containers và images..."
        docker-compose down --rmi all --volumes --remove-orphans
        docker system prune -f
        echo "✅ Cleanup hoàn tất"
        ;;
    *)
        echo "❌ Lựa chọn không hợp lệ"
        exit 1
        ;;
esac


