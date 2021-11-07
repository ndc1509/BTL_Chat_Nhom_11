# BTL_Chat_Nhom_11
 1) Các phần đã hoàn thành
 - CSDL với bảng tài khoản và bạn bè
 - Login/logout/đăng kí
 - Mỗi client có thể biết những ai onl trên server, danh sách tự động cập nhật
 - Tính năng kết bạn

 2) Các phần cần thực hiện
 - Chat 1-1
 - Chat nhóm
 - Truyền gửi file
 - Gửi icon/ảnh
 - Video chat

 3) Ý tưởng thêm
 - Thiết lập avatar
 - Mã hóa mật khẩu
 - Khóa đăng nhập nếu nhập sai mật khẩu quá 5 lần ?
    + Khóa dựa trên IP và khóa trong bao lâu ?
 - Chat log
 - Còn gì nữa k nhỉ?

# Giải thích Protocol như sau:
1) Client -> Server: một string chứa dữ liệu    
- Ví dụ: ",string1,string2,string3"
+ string1 là mã lệnh để server thực thi (1 login, 2 đăng kí, 3 logout)
+ string2, string3 là dữ liệu cần gửi
+ Server xử lý chuỗi bằng cách tách chuỗi dựa vào dấu "," và cho ra mảng string params[], params[0] là mã lệnh
+ Chuỗi dữ liệu có thể dài tùy thích thành string4,5,6,...
2) Server -> Client: gửi lần lượt các gói dữ liệu
như client

# ThreadPool
Một dạng đa Thread để xử lý nhiều client

# Tham khảo:
https://github.com/kevinzakka/game-room/blob/master/src/server.java
...

# LƯU Ý:
- Tạo SQL dump kèm luôn data nhé
