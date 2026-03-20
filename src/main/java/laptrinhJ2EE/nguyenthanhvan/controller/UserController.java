package laptrinhJ2EE.nguyenthanhvan.controller;

import laptrinhJ2EE.nguyenthanhvan.entity.User;
import laptrinhJ2EE.nguyenthanhvan.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
        if (logout != null) {
            model.addAttribute("message", "Đăng xuất thành công!");
        }
        return "user/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {

        // Kiểm tra validation errors
        if (result.hasErrors()) {
            return "user/register";
        }

        // Kiểm tra username đã tồn tại
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "user/register";
        }

        // Lưu user với role mặc định là SINHVIEN
        user.setRole("SINHVIEN");
        userService.save(user);

        model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/login?success=true";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "user/access-denied";
    }
}

