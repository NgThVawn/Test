package laptrinhJ2EE.nguyenthanhvan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(max = 50, message = "Tên đăng nhập phải ít hơn 50 ký tự")
    private String username;

    @Column(name = "password", length = 250, nullable = false)
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @Column(name = "email", length = 50)
    @Size(max = 50, message = "Email phải ít hơn 50 ký tự")
    private String email;

    @Column(name = "name", length = 50, nullable = false)
    @Size(max = 50, message = "Tên của bạn phải ít hơn 50 ký tự")
    @NotBlank(message = "Tên của bạn không được trống")
    private String name;

    @Column(name = "role", length = 20, nullable = false)
    private String role = "SINHVIEN"; // Mặc định là SINHVIEN
}
