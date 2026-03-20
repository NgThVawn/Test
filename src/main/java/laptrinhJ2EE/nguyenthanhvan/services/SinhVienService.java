package laptrinhJ2EE.nguyenthanhvan.services;

import laptrinhJ2EE.nguyenthanhvan.entity.SinhVien;
import laptrinhJ2EE.nguyenthanhvan.repository.ISinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SinhVienService {
    @Autowired
    private ISinhVienRepository sinhVienRepository;

    public Page<SinhVien> getAllSinhVien(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("mssv").ascending());
        return sinhVienRepository.findAll(pageable);
    }

    public Optional<SinhVien> getSinhVienById(String mssv) {
        return sinhVienRepository.findById(mssv);
    }

    public SinhVien addSinhVien(SinhVien sinhVien) {
        return sinhVienRepository.save(sinhVien);
    }

    public SinhVien updateSinhVien(SinhVien sinhVien) {
        return sinhVienRepository.save(sinhVien);
    }

    public void deleteSinhVien(String mssv) {
        sinhVienRepository.deleteById(mssv);
    }

    public Page<SinhVien> searchAndPaginate(String keyword, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("mssv").ascending());
        if (keyword == null || keyword.isEmpty()) {
            return sinhVienRepository.findAll(pageable);
        }
        return sinhVienRepository.search(keyword, pageable);
    }

    public Page<SinhVien> getSinhVienByLop(Integer maLop, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("mssv").ascending());
        return sinhVienRepository.findByLop(maLop, pageable);
    }
}

