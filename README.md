# **Calculator - Java**
**Ứng dụng tính toán cơ bản sử dụng`javax.swing`, `java.awt` và `javax.script`**

## Cài đặt:
1. Tải reposotory này về
2. Tạo project java mới (khuyên dùng netbean)
3. Đưa file `Calculator.java` vào project
4. Chạy file `Calculator.java` (hàm `main` để chạy đã có trong file)

## Sử dụng:
Ban đầu mở app, sẽ có giao diện giống 1 chiếc máy tính bỏ túi:
![](https://i.imgur.com/TBizBpR.png)

### Hướng dẫn sơ lược:
1. Có thể `dùng chuột` để ấn các nút trên ứng dụng, hoặc sử dụng `bàn phím` của bạn để nhập biểu thức. 
2. Mũi tên lên **↑** xuống **↓**. dùng để `xem lại lịch sử`.
3. Mũi tên trái **←** phải **→** dùng để di chuyển con trỏ qua lại.

### Chế độ nâng cao:
Nhấp chọn `Mode` và chọn `Science mode` để vào chế độ máy tính nâng cao.
![](https://i.imgur.com/eEkz2VX.png)

**Giao diện chế độ nâng cao:**
* Ở giao diện này sẽ có thêm các nút chức năng tính toán nâng cao.
* Kết quả sẽ chỉ ở dạng `số thực`.
* `Hex` `Bin` `Oct` dùng để chuyển số hệ thập phân (`Dec`) mà bạn nhập vào sang hệ số 16, 2, 8 tương ứng.
* `Deg` `Rad` dùng để chuyển 1 số sang hệ số góc tương ứng, ví dụ 
    * `Rad(180)` : chuyển góc 180 độ về góc radians và = π = 3.1415... 
    * `Deg(2xπ)` : chuyển góc radians 2π về góc deg và = 360 độ
* `Rand` dùng để tạo 1 số ngẫu nhiên trong khoảng 0-1
* `X`,`Y`,`Z` dùng để lưu và sử dụng lại các số đã lưu, ví dụ:
```javascript=
// Nhập vào các biểu thức sau đây, và ấn = mỗi lần nhập xong 1 dòng
X=10 // lưu giá trị 10 vào X
X+15 // sẽ lấy giá trị vừa lưu của X thay thế vào biểu thức và tính ra kết quả = 20
Y=12 // Lưu 12 vào Y
X+Y // trả về 22
```

![](https://i.imgur.com/fZrQNPi.png)

> Chúc các bạn có nhiều niềm vui khi lập trình.
