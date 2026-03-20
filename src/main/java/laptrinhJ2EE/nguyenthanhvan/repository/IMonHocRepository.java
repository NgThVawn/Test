package laptrinhJ2EE.nguyenthanhvan.repository;

import laptrinhJ2EE.nguyenthanhvan.entity.MonHoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IMonHocRepository extends JpaRepository<MonHoc, String> {
    @Query("SELECT m FROM MonHoc m WHERE LOWER(m.tenMonHoc) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<MonHoc> search(@Param("keyword") String keyword, Pageable pageable);
}
