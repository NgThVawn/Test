package laptrinhJ2EE.nguyenthanhvan.config;

import laptrinhJ2EE.nguyenthanhvan.entity.User;
import laptrinhJ2EE.nguyenthanhvan.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra nếu chưa có admin thì tạo tài khoản admin mặc định
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setName("Administrator");
            admin.setEmail("admin@example.com");
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("Đã tạo tài khoản admin mặc định: admin/admin123");
        }

        // Tạo một tài khoản sinh viên mẫu
        if (userRepository.findByUsername("sinhvien") == null) {
            User sinhvien = new User();
            sinhvien.setUsername("sinhvien");
            sinhvien.setPassword(passwordEncoder.encode("123456"));
            sinhvien.setName("Sinh Viên Mẫu");
            sinhvien.setEmail("sinhvien@example.com");
            sinhvien.setRole("SINHVIEN");
            userRepository.save(sinhvien);
            System.out.println("Đã tạo tài khoản sinh viên mẫu: sinhvien/123456");
        }
    }
}

