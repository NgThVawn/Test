package laptrinhJ2EE.nguyenthanhvan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity(name = "SinhVien")
@Table(name = "SinhVien")
public class SinhVien {
    @Id
    @Column(name = "MSSV")
    @Size(min = 1, max = 10, message = "MSSV phải có 10 chữ số")
    private String mssv;

    @Size(min = 1, max = 50, message = "Họ tên chỉ có tối đa 50 ký tự")
    @NotNull(message = "Họ tên không được để trống")
    @Column(name = "HoTen", length = 50)
    private String hoTen;

    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    @NotNull(message = "Ngày sinh không được để trống")
    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Email(message = "Email không hợp lệ")
    @NotNull(message = "Email không được để trống")
    @Column(name = "Email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "MaLop",
            referencedColumnName = "MaLop",
            foreignKey = @ForeignKey(name = "FK_SINHVIEN_LOP")
    )
    @NotNull(message = "Vui lòng chọn lớp")
    private Lop lop;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "SinhVien_MonHoc",
            joinColumns = {@JoinColumn(name = "MSSV")},
            inverseJoinColumns = {@JoinColumn(name = "MaMon")}
    )
    @JsonIgnore
    private Set<MonHoc> monHocs;
}
