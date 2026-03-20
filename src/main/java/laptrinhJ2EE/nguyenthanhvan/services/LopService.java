package laptrinhJ2EE.nguyenthanhvan.services;

import laptrinhJ2EE.nguyenthanhvan.entity.Lop;
import laptrinhJ2EE.nguyenthanhvan.repository.ILopRepository;
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
public class LopService {
    @Autowired
    private ILopRepository lopRepository;

    public List<Lop> getAllLop() {
        return lopRepository.findAll();
    }

    public Optional<Lop> getLopById(Integer id) {
        return lopRepository.findById(id);
    }

    public Lop addLop(Lop lop) {
        return lopRepository.save(lop);
    }

    public Lop updateLop(Lop lop) {
        return lopRepository.save(lop);
    }

    public void deleteLop(Integer id) {
        lopRepository.deleteById(id);
    }

    public Page<Lop> searchAndPaginate(String keyword, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("maLop").ascending());
        if (keyword == null || keyword.isEmpty()) {
            return lopRepository.findAll(pageable);
        }
        return lopRepository.searchByTenLop(keyword, pageable);
    }

    public Lop findByTenLop(String tenLop) {
        return lopRepository.findByTenLop(tenLop);
    }
}
