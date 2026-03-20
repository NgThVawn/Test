package laptrinhJ2EE.nguyenthanhvan.repository;


import laptrinhJ2EE.nguyenthanhvan.entity.Lop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILopRepository extends JpaRepository<Lop, Integer> {
    @Query("SELECT l FROM Lop l WHERE LOWER(l.tenLop) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Lop> searchByTenLop(@Param("keyword") String keyword, Pageable pageable);

    Lop findByTenLop(String tenLop);
}
