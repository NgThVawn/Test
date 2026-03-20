package laptrinhJ2EE.nguyenthanhvan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "MonHoc")
@Table(name = "MonHoc")
public class MonHoc {
    @Id
    @Size(min = 1, max = 10, message = "Mã môn phải có độ dài từ 1 đến 10 ký tự")
    @Column(name = "MaMon", length = 10)
    private String maMon;

    @Size(min = 5, max = 50, message = "Tên môn phải có độ dài từ 5 đến 50 ký tự")
    @Column(name = "TenMonHoc", length = 50)
    @NotNull(message = "Tên môn học không được để trống")
    private String tenMonHoc;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "SinhVien_MonHoc",
            joinColumns = {@JoinColumn(name = "MaMon")},
            inverseJoinColumns = {@JoinColumn(name = "MSSV")}
    )
    @JsonIgnore
    private Set<SinhVien> sinhViens;
}
