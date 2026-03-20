package laptrinhJ2EE.nguyenthanhvan.repository;

import laptrinhJ2EE.nguyenthanhvan.entity.SinhVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISinhVienRepository extends JpaRepository<SinhVien, String> {
    @Query("SELECT sv FROM SinhVien sv WHERE LOWER(sv.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(sv.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<SinhVien> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT sv FROM SinhVien sv WHERE sv.lop.maLop = :maLop")
    Page<SinhVien> findByLop(@Param("maLop") Integer maLop, Pageable pageable);
}
