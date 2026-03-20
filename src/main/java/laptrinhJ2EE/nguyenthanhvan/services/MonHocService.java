package laptrinhJ2EE.nguyenthanhvan.services;

import laptrinhJ2EE.nguyenthanhvan.entity.MonHoc;
import laptrinhJ2EE.nguyenthanhvan.repository.IMonHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MonHocService {
    @Autowired
    private IMonHocRepository monHocRepository;

    public List<MonHoc> getAllMonHoc() {
        return monHocRepository.findAll();
    }

    public Page<MonHoc> getAllMonHocPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("maMon").ascending());
        return monHocRepository.findAll(pageable);
    }

    public Optional<MonHoc> getMonHocById(String maMon) {
        return monHocRepository.findById(maMon);
    }

    public MonHoc addMonHoc(MonHoc monHoc) {
        return monHocRepository.save(monHoc);
    }

    public MonHoc updateMonHoc(MonHoc monHoc) {
        return monHocRepository.save(monHoc);
    }

    public void deleteMonHoc(String maMon) {
        monHocRepository.deleteById(maMon);
    }

    public Page<MonHoc> searchAndPaginate(String keyword, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("maMon").ascending());
        if (keyword == null || keyword.isEmpty()) {
            return monHocRepository.findAll(pageable);
        }
        return monHocRepository.search(keyword, pageable);
    }
}

