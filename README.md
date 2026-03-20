# Hệ thống Quản lý Sinh viên

## 1. Giới thiệu dự án
Đây là dự án web quản lý sinh viên được xây dựng bằng **Spring Boot**, **Spring MVC**, **Spring Data JPA**, **Thymeleaf**, **Spring Security**, **Bootstrap 5** và **MySQL**.

Mục tiêu của dự án là mô phỏng một hệ thống quản lý học vụ cơ bản với các chức năng:
- Quản lý **sinh viên**
- Quản lý **môn học**
- Quản lý **lớp**
- Tìm kiếm và phân trang dữ liệu
- Xác thực dữ liệu đầu vào khi thêm, sửa
- Đăng ký, đăng nhập, đăng xuất người dùng
- Phân quyền theo vai trò **Admin** và **Sinh viên**
- Giao diện tiếng Việt, hiện đại, dùng Thymeleaf + Bootstrap

README này không chỉ hướng dẫn chạy dự án mà còn tổng hợp **kiến thức, nguyên lý hoạt động và cách các thành phần phối hợp với nhau** để người đọc có thể nắm toàn bộ hệ thống.

---

## 2. Mục tiêu học tập của dự án
Dự án giúp luyện tập các kiến thức quan trọng trong môn J2EE / Lập trình Web Java:
- Cách tổ chức một ứng dụng theo mô hình nhiều tầng
- Cách nhận request từ trình duyệt và trả về giao diện HTML
- Cách mapping dữ liệu Java với bảng trong cơ sở dữ liệu bằng JPA
- Cách validate dữ liệu đầu vào với Jakarta Validation
- Cách viết CRUD với Spring MVC + Spring Data JPA
- Cách triển khai tìm kiếm và phân trang
- Cách dùng Thymeleaf để render dữ liệu động
- Cách tích hợp Spring Security để đăng nhập, phân quyền và logout an toàn

---

## 3. Công nghệ sử dụng
Theo `pom.xml`, dự án đang dùng các thành phần chính sau:

- **Spring Boot 4.0.3**: nền tảng khởi tạo và chạy ứng dụng
- **Spring Boot Starter Web MVC**: xây dựng ứng dụng web theo mô hình MVC
- **Spring Boot Starter Thymeleaf**: render giao diện phía server
- **Spring Boot Starter Data JPA**: thao tác CSDL bằng ORM
- **Spring Boot Starter Validation**: kiểm tra tính hợp lệ của dữ liệu đầu vào
- **Spring Boot Starter Security**: xác thực và phân quyền
- **Thymeleaf Extras Spring Security 6**: hiển thị giao diện theo trạng thái đăng nhập/quyền
- **MySQL Connector/J**: kết nối MySQL
- **Lombok**: giảm mã lặp getter/setter nhờ `@Data`
- **Bootstrap 5 + Bootstrap Icons**: giao diện hiện đại, responsive

### Ý nghĩa của từng công nghệ
- **Spring MVC** xử lý request HTTP, mapping URL tới controller.
- **Thymeleaf** nhận dữ liệu từ controller và sinh HTML cho người dùng.
- **JPA/Hibernate** ánh xạ class Java thành bảng dữ liệu.
- **Security** chặn truy cập trái phép trước khi request vào controller.
- **Validation** kiểm tra dữ liệu ở tầng model trước khi lưu.
- **Bootstrap** giúp giao diện đồng bộ, dễ dùng và responsive.

---

## 4. Cấu hình hệ thống
File `src/main/resources/application.properties` chứa cấu hình cốt lõi:

- Tên ứng dụng: `spring.application.name=nguyenthanhvan`
- Kết nối MySQL:
  - URL: `jdbc:mysql://localhost:3306/quanlysinhvien...`
  - Username: `root`
  - Password: để trống
- `spring.jpa.hibernate.ddl-auto=update`
- Thymeleaf dùng UTF-8, tắt cache để dễ phát triển
- Logging mức `DEBUG` cho package dự án

### Ý nghĩa của `ddl-auto=update`
Khi ứng dụng khởi động, Hibernate sẽ so sánh entity với schema hiện tại và tự điều chỉnh bảng nếu cần. Cách này thuận tiện cho học tập và phát triển nhanh, nhưng trong hệ thống production thường cần migration bài bản hơn.

---

## 5. Kiến trúc tổng thể của dự án
Dự án đi theo kiến trúc nhiều tầng:

```text
Trình duyệt
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Database (MySQL)
```

Kèm theo đó là tầng giao diện:

```text
Controller → Model → Thymeleaf Template → HTML trả về trình duyệt
```

### 5.1. Vai trò từng tầng
#### Controller
- Nhận request từ người dùng
- Đọc tham số URL, form, path variable
- Gọi service để xử lý nghiệp vụ
- Đưa dữ liệu vào `Model`
- Trả về tên template để render

Ví dụ:
- `SinhVienController`
- `MonHocController`
- `LopController`
- `UserController`
- `HomeController`

#### Service
- Chứa logic nghiệp vụ
- Là nơi trung gian giữa controller và repository
- Xử lý tìm kiếm, phân trang, lưu, sửa, xóa
- Được đánh dấu `@Service` và `@Transactional`

Ví dụ:
- `SinhVienService`
- `MonHocService`
- `LopService`
- `UserService`
- `CustomUserDetailService`

#### Repository
- Làm việc trực tiếp với database thông qua JPA
- Kế thừa `JpaRepository`
- Có thể viết query tùy chỉnh bằng `@Query`

Ví dụ:
- `ISinhVienRepository`
- `IMonHocRepository`
- `ILopRepository`
- `IUserRepository`

#### Entity
- Đại diện cho bảng dữ liệu trong database
- Gắn annotation `@Entity`, `@Table`, `@Id`, `@Column`, ...
- Đồng thời chứa các annotation validation

Ví dụ:
- `SinhVien`
- `MonHoc`
- `Lop`
- `User`

#### Template
- Nằm trong `src/main/resources/templates`
- Dùng Thymeleaf để render HTML động
- Có layout dùng chung và các trang riêng cho từng module

---

## 6. Cấu trúc thư mục và ý nghĩa

```text
src/main/java/laptrinhJ2EE/nguyenthanhvan
├── config        # Cấu hình bảo mật, dữ liệu khởi tạo
├── controller    # Nhận request và trả view
├── entity        # Mô hình dữ liệu + validation
├── repository    # Truy vấn JPA
├── services      # Xử lý nghiệp vụ
└── utils         # Tiện ích mở rộng nếu cần

src/main/resources
├── application.properties
├── static/css    # CSS tùy biến
└── templates     # Giao diện Thymeleaf
```

### Quy ước tổ chức mã nguồn
- **Tách rõ trách nhiệm** giữa các tầng
- **Controller không truy vấn DB trực tiếp**
- **Repository không xử lý giao diện**
- **Validation đặt gần dữ liệu** để tái sử dụng khi thêm/sửa
- **Template chỉ hiển thị dữ liệu**, hạn chế chứa logic phức tạp

---

## 7. Mô hình dữ liệu

### 7.1. Thực thể `SinhVien`
File: `src/main/java/laptrinhJ2EE/nguyenthanhvan/entity/SinhVien.java`

Các trường chính:
- `mssv`: mã số sinh viên, khóa chính
- `hoTen`: họ tên sinh viên
- `ngaySinh`: ngày sinh
- `email`: email
- `lop`: quan hệ nhiều-sinh-viên thuộc một-lớp
- `monHocs`: quan hệ nhiều-nhiều với môn học

### Validation áp dụng cho `SinhVien`
- `mssv`: `@Size(min = 1, max = 10)`
- `hoTen`: `@NotNull`, `@Size(max = 50)`
- `ngaySinh`: `@NotNull`, `@Past`
- `email`: `@NotNull`, `@Email`
- `lop`: `@NotNull(message = "Vui lòng chọn lớp")`

### Ý nghĩa nghiệp vụ
- Sinh viên bắt buộc phải có lớp
- Không được để trống ngày sinh
- Ngày sinh phải là ngày trong quá khứ
- Email phải đúng định dạng

### 7.2. Thực thể `Lop`
File: `src/main/java/laptrinhJ2EE/nguyenthanhvan/entity/Lop.java`

Các trường:
- `maLop`: khóa chính, tự tăng
- `tenLop`: tên lớp
- `sinhViens`: tập sinh viên thuộc lớp đó

Validation:
- `tenLop`: `@NotNull`, `@Size(min = 1, max = 7)`

### 7.3. Thực thể `MonHoc`
File: `src/main/java/laptrinhJ2EE/nguyenthanhvan/entity/MonHoc.java`

Các trường:
- `maMon`: khóa chính
- `tenMonHoc`: tên môn học
- `sinhViens`: tập sinh viên học môn đó

Validation:
- `maMon`: `@Size(min = 1, max = 10)`
- `tenMonHoc`: `@NotNull`, `@Size(min = 5, max = 50)`

### 7.4. Thực thể `User`
File: `src/main/java/laptrinhJ2EE/nguyenthanhvan/entity/User.java`

Các trường:
- `id`: khóa chính tự tăng
- `username`: tên đăng nhập, duy nhất
- `password`: mật khẩu đã mã hóa
- `email`
- `name`: tên hiển thị
- `role`: vai trò, gồm `ADMIN` hoặc `SINHVIEN`

Validation:
- `username`: `@NotBlank`, `@Size(max = 50)`
- `password`: `@NotBlank`
- `name`: `@NotBlank`, `@Size(max = 50)`
- `email`: `@Size(max = 50)`

---

## 8. Quan hệ giữa các bảng

### 8.1. `Lop` - `SinhVien`
- Một lớp có nhiều sinh viên: `@OneToMany`
- Một sinh viên thuộc một lớp: `@ManyToOne`

Điều này phản ánh đúng thực tế quản lý: mỗi sinh viên bắt buộc phải thuộc một lớp cụ thể.

### 8.2. `SinhVien` - `MonHoc`
- Quan hệ nhiều-nhiều: `@ManyToMany`
- Bảng trung gian: `SinhVien_MonHoc`

Hiện tại, cấu trúc dữ liệu đã hỗ trợ quan hệ này ở tầng entity. Tuy nhiên, giao diện và controller hiện tại chủ yếu quản lý CRUD độc lập của sinh viên, môn học, lớp; chưa có luồng UI riêng để gán môn học cho từng sinh viên.

### Vì sao dùng `@JsonIgnore`
Một số trường quan hệ được gắn `@JsonIgnore` để tránh lặp vô hạn khi serialize object có quan hệ hai chiều.

---

## 9. Repository và nguyên lý truy vấn dữ liệu

### 9.1. Repository kế thừa `JpaRepository`
Ví dụ:
- `ISinhVienRepository extends JpaRepository<SinhVien, String>`
- `ILopRepository extends JpaRepository<Lop, Integer>`
- `IMonHocRepository extends JpaRepository<MonHoc, String>`

Khi kế thừa `JpaRepository`, ta có sẵn các hàm:
- `findAll()`
- `findById()`
- `save()`
- `deleteById()`
- phân trang với `findAll(Pageable pageable)`

### 9.2. Query tùy chỉnh
#### `ISinhVienRepository`
Có các truy vấn:
- Tìm kiếm sinh viên theo **họ tên hoặc email**
- Lọc sinh viên theo **mã lớp**

#### `IMonHocRepository`
Có truy vấn:
- Tìm kiếm môn học theo **tên môn học**

#### `ILopRepository`
Có truy vấn:
- Tìm lớp theo **tên lớp**
- Tìm chính xác lớp theo `tenLop`

### Ý nghĩa của `Pageable`
`Pageable` giúp:
- lấy dữ liệu theo từng trang
- giới hạn số lượng bản ghi mỗi lần tải
- kết hợp sắp xếp

Trong dự án này, số phần tử mỗi trang thường được cố định là **10**.

---

## 10. Service và nguyên lý xử lý nghiệp vụ

### 10.1. `SinhVienService`
Chứa các chức năng:
- Lấy danh sách sinh viên theo trang
- Tìm sinh viên theo mã
- Thêm sinh viên
- Cập nhật sinh viên
- Xóa sinh viên
- Tìm kiếm + phân trang
- Lọc theo lớp

### 10.2. `MonHocService`
Chứa các chức năng:
- Lấy toàn bộ môn học
- Lấy môn học theo trang
- Lấy chi tiết theo mã môn
- Thêm, sửa, xóa môn học
- Tìm kiếm + phân trang

### 10.3. `LopService`
Chứa các chức năng:
- Lấy danh sách lớp
- Lấy lớp theo id
- Thêm, sửa, xóa lớp
- Tìm kiếm + phân trang
- Tìm lớp theo tên

### 10.4. `UserService`
Phụ trách:
- Mã hóa mật khẩu bằng `BCryptPasswordEncoder`
- Gán role mặc định `SINHVIEN` nếu chưa có
- Lưu người dùng
- Tìm người dùng theo username

### 10.5. `@Transactional`
Các service dữ liệu được đánh dấu `@Transactional` để đảm bảo các thao tác ghi DB diễn ra trong transaction thống nhất.

---

## 11. Controller và nguyên lý điều hướng request

### 11.1. `HomeController`
- URL `/`
- Trả về trang chủ `index`

### 11.2. `SinhVienController`
Base URL: `/sinhvien`

Các endpoint chính:
- `GET /sinhvien`: xem danh sách + tìm kiếm + phân trang
- `GET /sinhvien/{mssv}`: xem chi tiết
- `GET /sinhvien/add`: mở form thêm
- `POST /sinhvien/add`: lưu sinh viên mới
- `GET /sinhvien/edit/{mssv}`: mở form sửa
- `POST /sinhvien/edit/{mssv}`: cập nhật sinh viên
- `GET /sinhvien/delete/{mssv}`: xóa sinh viên

### Điểm quan trọng trong controller sinh viên
- Dùng `@Valid` để kích hoạt validation
- Dùng `BindingResult` để kiểm tra lỗi
- Nếu form lỗi, nạp lại `dsLop` rồi trả về form cũ
- Nếu thành công, redirect về danh sách

Đây là điểm rất quan trọng khi làm form có select lớp: nếu dữ liệu lỗi mà không nạp lại danh sách lớp, form sẽ render thiếu dữ liệu.

### 11.3. `MonHocController`
Base URL: `/monhoc`

Các endpoint:
- danh sách
- chi tiết
- thêm
- sửa
- xóa
- tìm kiếm
- phân trang

### 11.4. `LopController`
Base URL: `/lop`

Các endpoint:
- danh sách
- chi tiết
- thêm
- sửa
- xóa
- tìm kiếm
- phân trang

### 11.5. `UserController`
Phụ trách:
- Hiển thị form đăng nhập
- Hiển thị form đăng ký
- Xử lý đăng ký người dùng mới
- Hiển thị trang từ chối truy cập `access-denied`

### Điểm quan trọng trong đăng ký
- Validate `User`
- Kiểm tra trùng username
- Nếu trùng, báo lỗi và ở lại form đăng ký
- Nếu hợp lệ, role mặc định là `SINHVIEN`
- Mật khẩu được mã hóa trước khi lưu

---

## 12. Thymeleaf và cơ chế render giao diện
Dự án dùng **server-side rendering**, nghĩa là:
1. Trình duyệt gửi request
2. Controller xử lý
3. Dữ liệu được đặt vào `Model`
4. Thymeleaf đọc template HTML
5. Thymeleaf thay thế biểu thức `th:*`
6. HTML hoàn chỉnh được trả về trình duyệt

### Các cú pháp Thymeleaf thường gặp trong dự án
- `th:text`: gán text
- `th:href`: tạo link động
- `th:action`: tạo action cho form
- `th:object`: bind form với object
- `th:field`: bind input với thuộc tính
- `th:if`: hiển thị có điều kiện
- `th:each`: lặp danh sách
- `th:errors`: hiển thị lỗi validation
- `th:replace`: dùng layout chung

### Layout dùng chung
File `src/main/resources/templates/layout.html`

Vai trò:
- Chứa navbar
- Chứa vùng hiển thị nội dung chính
- Chứa footer
- Dùng fragment `layout(title, content)` để các trang con kế thừa

Lợi ích:
- Đồng bộ giao diện toàn hệ thống
- Giảm lặp mã HTML
- Dễ thay đổi theme toàn bộ dự án

---

## 13. Giao diện người dùng và nguyên lý UX/UI
Dự án đã được chỉnh theo hướng giao diện sạch, hiện đại, dễ thao tác:

### Đặc điểm chính
- Toàn bộ giao diện dùng **tiếng Việt có dấu**
- Dùng **Bootstrap 5** để responsive trên màn hình nhỏ và lớn
- Có **navbar** thống nhất
- Có **card**, **panel**, **table**, **form-panel** để phân nhóm nội dung rõ ràng
- Có **search box** riêng cho danh sách
- Có **pagination** dễ dùng
- Có trạng thái hiển thị khác nhau theo quyền người dùng

### CSS tùy biến
File `src/main/resources/static/css/app.css`

Phong cách thiết kế:
- tông màu chủ đạo xanh hiện đại
- card bo góc, shadow nhẹ
- typography rõ ràng
- bảng và form được làm sạch bố cục
- có responsive cho màn hình nhỏ

### Trang chủ `index.html`
Trang chủ hiển thị khác nhau tùy người dùng:
- Nếu **chưa đăng nhập**: mời đăng nhập/đăng ký và hiển thị tài khoản demo
- Nếu **đã đăng nhập**:
  - Admin thấy lời chào và hướng quản trị
  - Sinh viên thấy lời chào và hướng xem dữ liệu

---

## 14. CRUD của từng chức năng hoạt động như thế nào

## 14.1. CRUD Sinh viên
### Thêm sinh viên
Luồng xử lý:
1. Vào `GET /sinhvien/add`
2. Controller tạo `new SinhVien()` và nạp danh sách lớp `dsLop`
3. Người dùng nhập form
4. Gửi `POST /sinhvien/add`
5. `@Valid` kiểm tra dữ liệu
6. Nếu lỗi, quay lại form và hiển thị lỗi
7. Nếu hợp lệ, service gọi repository `save()`
8. Redirect về danh sách

### Sửa sinh viên
- Lấy sinh viên theo `mssv`
- Đổ dữ liệu ra form
- Khi submit, controller giữ nguyên `mssv` bằng `sinhVien.setMssv(mssv)`
- Sau đó lưu bằng `save()`

### Xóa sinh viên
- Gọi `deleteById(mssv)`
- Sau khi xóa redirect về danh sách

### Xem chi tiết sinh viên
- Lấy theo `mssv`
- Nếu không có thì redirect về `/sinhvien`

## 14.2. CRUD Môn học
Tương tự sinh viên, nhưng dùng khóa chính `maMon`.

## 14.3. CRUD Lớp
Tương tự, nhưng dùng khóa chính `maLop` dạng số nguyên tự tăng.

### Vì sao `save()` dùng được cho cả thêm và sửa?
Trong JPA, `save()` có thể:
- thêm mới nếu entity chưa tồn tại
- cập nhật nếu entity đã tồn tại theo khóa chính

---

## 15. Validation dữ liệu đầu vào
Validation là lớp bảo vệ dữ liệu trước khi ghi vào database.

### Cơ chế hoạt động
1. Entity khai báo ràng buộc bằng annotation
2. Controller nhận object với `@Valid`
3. Spring kiểm tra object
4. Kết quả lỗi được đẩy vào `BindingResult`
5. Nếu có lỗi, trả lại form để người dùng sửa

### Các lỗi tiêu biểu mà dự án đang bắt
- Không nhập họ tên
- Không nhập ngày sinh
- Ngày sinh không hợp lệ hoặc không ở quá khứ
- Email sai định dạng
- Không chọn lớp khi thêm/sửa sinh viên
- Không nhập tên môn học
- Không nhập tên lớp
- Không nhập tên đăng nhập, mật khẩu, tên người dùng khi đăng ký

### Trường hợp đáng chú ý: không chọn lớp
Đây là lỗi rất quan trọng ở form sinh viên.

Tầng entity đã bắt bằng:
- `@NotNull(message = "Vui lòng chọn lớp")`

Tầng form dùng:
- `th:field="*{lop.maLop}"`
- và hiển thị lỗi bằng `th:errors="*{lop}"`

Điều này giúp người dùng thấy lỗi ngay trên giao diện thay vì để ứng dụng lỗi ngầm khi lưu.

### Trường hợp đáng chú ý: không được để trống ngày sinh
Ở `SinhVien`, trường `ngaySinh` có:
- `@NotNull(message = "Ngày sinh không được để trống")`
- `@Past(message = "Ngày sinh phải là ngày trong quá khứ")`

Nghĩa là:
- để trống sẽ báo lỗi
- nhập ngày tương lai cũng báo lỗi

---

## 16. Tìm kiếm và phân trang

## 16.1. Tìm kiếm
### Sinh viên
Repository tìm theo:
- `hoTen`
- `email`

### Môn học
Tìm theo:
- `tenMonHoc`

### Lớp
Tìm theo:
- `tenLop`

### Nguyên lý
- Người dùng nhập từ khóa vào ô tìm kiếm
- Form gửi request GET cùng tham số `keyword`
- Controller kiểm tra:
  - nếu rỗng → lấy toàn bộ theo trang
  - nếu có nội dung → gọi hàm `searchAndPaginate(...)`
- Repository dùng JPQL `LIKE %keyword%`
- Kết quả được trả về theo `Page<T>`

## 16.2. Phân trang
### Cơ chế
- Controller nhận `page`
- Service tạo `PageRequest.of(page, pageSize, Sort...)`
- Repository trả `Page<T>`
- Controller đưa vào model:
  - dữ liệu trang hiện tại
  - tổng số trang
  - trang hiện tại
  - từ khóa đang tìm
- Template render thanh phân trang

### Lợi ích
- Tránh tải toàn bộ dữ liệu một lần
- Tăng hiệu năng
- Cải thiện UX với danh sách dài

---

## 17. Bảo mật, đăng nhập và phân quyền
Đây là phần rất quan trọng của dự án.

## 17.1. `SecurityConfig`
File: `src/main/java/laptrinhJ2EE/nguyenthanhvan/config/SecurityConfig.java`

### Thành phần chính
- `BCryptPasswordEncoder`: mã hóa mật khẩu
- `DaoAuthenticationProvider`: xác thực qua database
- `AuthenticationManager`: quản lý xác thực
- `SecurityFilterChain`: cấu hình toàn bộ luật bảo mật HTTP

### Luật truy cập hiện tại
#### Truy cập công khai
Các URL được phép vào không cần đăng nhập:
- `/`
- `/login`
- `/register`
- `/css/**`
- `/js/**`
- `/images/**`

#### Chỉ `ADMIN` mới có quyền CRUD
- `/sinhvien/add`, `/sinhvien/edit/**`, `/sinhvien/delete/**`
- `/monhoc/add`, `/monhoc/edit/**`, `/monhoc/delete/**`
- `/lop/add`, `/lop/edit/**`, `/lop/delete/**`

#### Người đã đăng nhập mới được xem dữ liệu
- `/sinhvien/**`
- `/monhoc/**`
- `/lop/**`

### Ý nghĩa phân quyền
- **Admin**: có quyền thêm, sửa, xóa và xem
- **Sinh viên**: chỉ xem danh sách/chi tiết

Đây là mô hình rất phù hợp với bài toán quản trị học vụ cơ bản.

## 17.2. `CustomUserDetailService`
Spring Security cần một `UserDetailsService` để nạp người dùng từ DB.

Luồng:
1. Người dùng submit form login
2. Security gọi `loadUserByUsername(username)`
3. Service tìm `User` qua `IUserRepository`
4. Nếu không có → ném `UsernameNotFoundException`
5. Nếu có → bọc thành `CustomUserDetail`

## 17.3. `CustomUserDetail`
Class này triển khai `UserDetails`.

Nhiệm vụ:
- Trả về username
- Trả về password đã mã hóa
- Trả về quyền của người dùng

Phần quan trọng nhất là:
- `ROLE_` + `user.getRole()`

Ví dụ:
- `ADMIN` → `ROLE_ADMIN`
- `SINHVIEN` → `ROLE_SINHVIEN`

Điều này giúp `hasRole('ADMIN')` và `hasRole('SINHVIEN')` hoạt động đúng.

---

## 18. Đăng ký người dùng
`UserController` xử lý đăng ký như sau:

1. Mở form `GET /register`
2. Người dùng nhập thông tin
3. Gửi `POST /register`
4. Validate object `User`
5. Kiểm tra username đã tồn tại chưa
6. Nếu hợp lệ:
   - gán role mặc định `SINHVIEN`
   - mã hóa mật khẩu bằng BCrypt
   - lưu xuống DB
7. Redirect sang trang đăng nhập với `?success=true`

### Vì sao user đăng ký mặc định là `SINHVIEN`?
Đây là cơ chế an toàn.
- Người dùng tự đăng ký không được tự chọn quyền admin
- Admin nên do hệ thống hoặc người quản trị tạo

---

## 19. Đăng nhập người dùng
Form đăng nhập nằm tại `templates/user/login.html`.

### Luồng đăng nhập
1. Người dùng nhập username + password
2. Form gửi `POST /login`
3. Spring Security xử lý xác thực
4. Nếu sai → quay lại `/login?error=true`
5. Nếu đúng → chuyển tới `/`

### Sau đăng nhập
- Navbar hiển thị tên người dùng đang đăng nhập
- Hiển thị badge vai trò:
  - Admin
  - Sinh viên
- Giao diện menu và các nút hành động thay đổi theo quyền

---

## 20. Đăng xuất và lý do phải dùng POST
Dự án dùng logout chuẩn của Spring Security.

### Cấu hình
- `logoutUrl("/logout")`
- `logoutSuccessUrl("/login?logout=true")`

### Nguyên lý đúng
Nút đăng xuất trong `layout.html` được triển khai bằng **form POST** có kèm **CSRF token**.

### Vì sao không nên dùng link `GET /logout`?
Với Spring Security hiện đại, logout nên đi qua request POST để tránh hành vi ngoài ý muốn và đảm bảo an toàn CSRF.

Nếu dùng sai kiểu request, trình duyệt có thể báo lỗi 404 hoặc request không được security filter xử lý.

### Luồng logout
1. Người dùng bấm nút đăng xuất
2. Form gửi `POST /logout`
3. Spring Security hủy phiên đăng nhập
4. Redirect tới `/login?logout=true`
5. Trang login hiển thị thông báo đăng xuất thành công

---

## 21. Dữ liệu mẫu được khởi tạo tự động
File: `src/main/java/laptrinhJ2EE/nguyenthanhvan/config/DataInitializer.java`

Khi ứng dụng chạy, nếu chưa có dữ liệu thì hệ thống tự tạo:

### Tài khoản admin mặc định
- Username: `admin`
- Password: `admin123`
- Role: `ADMIN`

### Tài khoản sinh viên mẫu
- Username: `sinhvien`
- Password: `123456`
- Role: `SINHVIEN`

### Ý nghĩa
- Giúp kiểm thử nhanh mà không cần tạo tay ngay từ đầu
- Dễ demo và so sánh khác biệt giữa hai vai trò

---

## 22. Hiển thị giao diện theo quyền với `sec:authorize`
Dự án sử dụng namespace bảo mật của Thymeleaf:

```html
xmlns:sec="http://www.thymeleaf.org/extras/spring-security"
```

Ví dụ sử dụng:
- `sec:authorize="isAuthenticated()"`
- `sec:authorize="!isAuthenticated()"`
- `sec:authorize="hasRole('ADMIN')"`
- `sec:authorize="hasRole('SINHVIEN')"`

### Lợi ích
- Ẩn nút CRUD khỏi sinh viên thường
- Hiển thị menu phù hợp theo trạng thái đăng nhập
- Trải nghiệm rõ ràng và đúng quyền

Lưu ý: ẩn nút trên giao diện chỉ là lớp UX. Bảo vệ thực sự vẫn nằm ở `SecurityConfig`.

---

## 23. Bảng chức năng theo vai trò

| Chức năng | Khách | Sinh viên | Admin |
|---|---:|---:|---:|
| Xem trang chủ | Có | Có | Có |
| Đăng ký tài khoản | Có | Không cần | Không cần |
| Đăng nhập | Có | Có | Có |
| Xem danh sách sinh viên | Không | Có | Có |
| Xem chi tiết sinh viên | Không | Có | Có |
| Thêm/Sửa/Xóa sinh viên | Không | Không | Có |
| Xem danh sách môn học | Không | Có | Có |
| Thêm/Sửa/Xóa môn học | Không | Không | Có |
| Xem danh sách lớp | Không | Có | Có |
| Thêm/Sửa/Xóa lớp | Không | Không | Có |
| Đăng xuất | Không | Có | Có |

---

## 24. Luồng xử lý mẫu: thêm một sinh viên mới
Đây là luồng rất điển hình của toàn bộ hệ thống.

1. Admin vào `/sinhvien/add`
2. `SinhVienController.showAddForm()` tạo object rỗng và nạp danh sách lớp
3. Template `sinhvien/add.html` hiển thị form
4. Admin nhập dữ liệu rồi submit
5. `SinhVienController.addSinhVien()` nhận object qua `@ModelAttribute`
6. `@Valid` kiểm tra validation trong entity `SinhVien`
7. Nếu lỗi:
   - `BindingResult` chứa lỗi
   - nạp lại `dsLop`
   - trả về form với thông báo lỗi
8. Nếu hợp lệ:
   - gọi `SinhVienService.addSinhVien()`
   - service gọi `ISinhVienRepository.save()`
   - dữ liệu lưu xuống MySQL
9. Controller redirect tới `/sinhvien`
10. Trang danh sách hiển thị dữ liệu mới

Từ luồng này có thể suy ra cách hoạt động của hầu hết form khác trong dự án.

---

## 25. Luồng xử lý mẫu: tìm kiếm sinh viên
1. Người dùng nhập từ khóa vào ô tìm kiếm ở `sinhvien/list.html`
2. Form gửi `GET /sinhvien?keyword=...`
3. Controller đọc `keyword`
4. Gọi `sinhVienService.searchAndPaginate(keyword, page, 10)`
5. Repository thực thi query JPQL dùng `LIKE`
6. Kết quả trả về `Page<SinhVien>`
7. Template render bảng và thanh phân trang

---

## 26. Luồng xử lý mẫu: đăng nhập và phân quyền
1. Người dùng mở `/login`
2. Nhập tài khoản và mật khẩu
3. Form gửi `POST /login`
4. `CustomUserDetailService` tải user từ database
5. Security so khớp mật khẩu bằng BCrypt
6. Nếu hợp lệ, security tạo session xác thực
7. Khi truy cập URL cần quyền cao hơn:
   - nếu là Admin → được truy cập
   - nếu là SinhVien → bị từ chối và chuyển tới `/access-denied`
8. Trên giao diện, Thymeleaf kiểm tra role để hiện/ẩn nút phù hợp

---

## 27. Những nguyên lý cốt lõi cần nắm khi học dự án này

### 27.1. MVC không chỉ là chia file, mà là chia trách nhiệm
- Model: dữ liệu và ràng buộc
- View: giao diện hiển thị
- Controller: trung gian điều hướng

### 27.2. Validation nên đặt gần dữ liệu
Đặt trên entity giúp cùng một luật được dùng cho cả thêm và sửa.

### 27.3. Phân quyền phải làm ở cả backend lẫn frontend
- Backend: chặn thật bằng Spring Security
- Frontend: ẩn các nút không phù hợp để UX rõ ràng

### 27.4. Giao diện server-side vẫn rất mạnh
Dù không dùng SPA hay JavaScript phức tạp, Thymeleaf vẫn đáp ứng tốt các bài toán CRUD, validation, tìm kiếm, phân trang và bảo mật.

### 27.5. JPA giúp giảm rất nhiều mã SQL thủ công
Ta tập trung vào object và nghiệp vụ hơn là viết SQL thuần cho mọi thao tác cơ bản.

---

## 28. Những điểm mạnh của dự án hiện tại
- Kiến trúc rõ ràng, dễ học
- CRUD đầy đủ cho 3 module chính
- Có validation thực tế
- Có tìm kiếm và phân trang
- Có đăng ký, đăng nhập, logout
- Có phân quyền 2 vai trò rõ ràng
- Giao diện Bootstrap hiện đại, tiếng Việt đồng bộ
- Có dữ liệu demo sẵn để kiểm thử nhanh

---

## 29. Giới hạn hiện tại và hướng mở rộng
### Giới hạn hiện tại
- Chưa có giao diện gán môn học cho từng sinh viên dù entity đã hỗ trợ quan hệ nhiều-nhiều
- Xóa đang dùng `GET`, phù hợp học tập nhưng chưa phải cách RESTful tối ưu
- Chưa có test nghiệp vụ chi tiết cho từng controller/service
- Chưa có migration database riêng biệt

### Hướng mở rộng nên làm tiếp
- Thêm chức năng đăng ký môn học cho sinh viên
- Chuyển thao tác xóa sang `POST` hoặc `DELETE`
- Thêm xác nhận xóa tốt hơn
- Thêm unit test / integration test cho CRUD và security
- Thêm dashboard thống kê số lượng sinh viên, lớp, môn học
- Thêm upload ảnh đại diện sinh viên
- Thêm phân quyền chi tiết hơn theo chức năng

---

## 30. Hướng dẫn chạy dự án
### Yêu cầu môi trường
- Java 17 trở lên
- Maven Wrapper có sẵn (`mvnw`, `mvnw.cmd`)
- MySQL đang chạy
- Tạo database tên `quanlysinhvien`

### Các bước chạy
1. Tạo database MySQL:
   - `quanlysinhvien`
2. Kiểm tra lại thông tin kết nối trong `application.properties`
3. Chạy ứng dụng bằng Maven Wrapper
4. Mở trình duyệt truy cập trang chủ

### Lệnh chạy trên Windows PowerShell
```powershell
Set-Location 'D:\22DTHD5\J2EE\Buoi4\nguyenthanhvan\nguyenthanhvan'
.\mvnw.cmd spring-boot:run
```

### Chạy test nhanh
```powershell
Set-Location 'D:\22DTHD5\J2EE\Buoi4\nguyenthanhvan\nguyenthanhvan'
.\mvnw.cmd test
```

---

## 31. Tài khoản mẫu để thử nhanh

### Quản trị viên
- Tên đăng nhập: `admin`
- Mật khẩu: `admin123`

### Sinh viên
- Tên đăng nhập: `sinhvien`
- Mật khẩu: `123456`

---

## 32. Các URL quan trọng
- `/` : trang chủ
- `/login` : đăng nhập
- `/register` : đăng ký
- `/sinhvien` : danh sách sinh viên
- `/monhoc` : danh sách môn học
- `/lop` : danh sách lớp
- `/access-denied` : trang từ chối truy cập

---

## 33. Cách đọc source code để nắm dự án nhanh nhất
Nếu bạn mới tiếp cận dự án, nên đọc theo thứ tự này:

1. `pom.xml`
   - biết dự án dùng công nghệ gì
2. `application.properties`
   - biết cấu hình kết nối và môi trường
3. `SecurityConfig.java`
   - biết luật truy cập và phân quyền
4. Các entity
   - biết cấu trúc dữ liệu và validation
5. Các repository
   - biết cách truy vấn dữ liệu
6. Các service
   - biết xử lý nghiệp vụ
7. Các controller
   - biết luồng URL và điều hướng
8. `layout.html`, `index.html`, các template CRUD
   - biết cách render giao diện

Đây là cách học nhanh nhất để hiểu một dự án Spring Boot theo chiều từ tổng quan tới chi tiết.

---

## 34. Kết luận
Dự án này là một ví dụ khá đầy đủ của một hệ thống quản lý dữ liệu cơ bản với Java Web hiện đại. Giá trị lớn nhất của dự án không chỉ nằm ở việc có CRUD, mà ở chỗ nó thể hiện được cách các thành phần trong Spring Boot phối hợp với nhau:

- **Entity** mô tả dữ liệu và ràng buộc
- **Repository** truy vấn dữ liệu
- **Service** xử lý nghiệp vụ
- **Controller** điều hướng request/response
- **Thymeleaf** render giao diện
- **Security** bảo vệ hệ thống

Nếu nắm chắc dự án này, bạn sẽ hiểu được nền tảng của rất nhiều ứng dụng quản lý viết bằng Spring Boot sau này.

---

## 35. Tóm tắt nhanh trong 10 ý
1. Đây là dự án web quản lý sinh viên dùng Spring Boot + Thymeleaf + MySQL.
2. Kiến trúc chính là `Controller → Service → Repository → Database`.
3. Có 4 entity chính: `SinhVien`, `MonHoc`, `Lop`, `User`.
4. `SinhVien` bắt buộc có lớp, email hợp lệ và ngày sinh không được trống.
5. CRUD cho sinh viên, môn học, lớp được xây theo cùng một mẫu.
6. Tìm kiếm dùng JPQL + `LIKE`, phân trang dùng `Pageable`.
7. Giao diện dùng Bootstrap 5, layout chung và tiếng Việt đồng bộ.
8. Người dùng tự đăng ký sẽ có role `SINHVIEN`.
9. `ADMIN` có quyền CRUD, `SINHVIEN` chỉ có quyền xem.
10. Logout chuẩn phải dùng `POST /logout` kèm CSRF token.

---

## 36. Demo Travis CI: fail trước, pass sau

### File đã thêm/cập nhật cho CI
- `.travis.yml`: pipeline Travis chạy `./mvnw -B test`
- `src/test/resources/application-test.properties`: cấu hình H2 cho test
- `pom.xml`: thêm dependency `com.h2database:h2` (scope `test`)
- `NguyenthanhvanApplicationTests`: thêm test `ciIntentionalFailDemo`

### Cách demo trên Travis
1. Push code hiện tại lên GitHub và bật Travis CI cho repository.
2. Lần chạy đầu sẽ **fail** vì biến `TRAVIS_DEMO_FORCE_FAIL=true` trong `.travis.yml`.
3. Sửa `.travis.yml`: đổi `TRAVIS_DEMO_FORCE_FAIL=true` thành `TRAVIS_DEMO_FORCE_FAIL=false`.
4. Commit + push lại, pipeline sẽ **pass**.

### Kiểm tra nhanh ở local (mô phỏng Travis)
- Mô phỏng fail:
  - PowerShell: `$env:TRAVIS_DEMO_FORCE_FAIL='true'; .\\mvnw.cmd -q test`
- Mô phỏng pass:
  - PowerShell: `$env:TRAVIS_DEMO_FORCE_FAIL='false'; .\\mvnw.cmd -q test`

> Sau khi demo xong, có thể xóa test `ciIntentionalFailDemo` hoặc giữ nhưng luôn để `TRAVIS_DEMO_FORCE_FAIL=false`.
