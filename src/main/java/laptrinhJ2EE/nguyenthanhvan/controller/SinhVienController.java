package laptrinhJ2EE.nguyenthanhvan.controller;

import laptrinhJ2EE.nguyenthanhvan.entity.SinhVien;
import laptrinhJ2EE.nguyenthanhvan.services.SinhVienService;
import laptrinhJ2EE.nguyenthanhvan.services.LopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {
    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private LopService lopService;

    @GetMapping
    public String showAllSinhVien(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {
        Page<SinhVien> dsSinhVien;
        if (keyword == null || keyword.isEmpty()) {
            dsSinhVien = sinhVienService.getAllSinhVien(page, 10);
        } else {
            dsSinhVien = sinhVienService.searchAndPaginate(keyword, page, 10);
        }
        model.addAttribute("dsSinhVien", dsSinhVien.getContent());
        model.addAttribute("totalPages", dsSinhVien.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "sinhvien/list";
    }

    @GetMapping("/{mssv}")
    public String showDetail(@PathVariable String mssv, Model model) {
        var sinhVien = sinhVienService.getSinhVienById(mssv);
        if (sinhVien.isEmpty()) {
            return "redirect:/sinhvien";
        }
        model.addAttribute("sinhVien", sinhVien.get());
        return "sinhvien/detail";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        model.addAttribute("dsLop", lopService.getAllLop());
        return "sinhvien/add";
    }

    @PostMapping("/add")
    public String addSinhVien(
            @Valid @ModelAttribute("sinhVien") SinhVien sinhVien,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("dsLop", lopService.getAllLop());
            return "sinhvien/add";
        }
        sinhVienService.addSinhVien(sinhVien);
        return "redirect:/sinhvien";
    }

    @GetMapping("/edit/{mssv}")
    public String showEditForm(@PathVariable String mssv, Model model) {
        var sinhVien = sinhVienService.getSinhVienById(mssv);
        if (sinhVien.isEmpty()) {
            return "redirect:/sinhvien";
        }
        model.addAttribute("sinhVien", sinhVien.get());
        model.addAttribute("dsLop", lopService.getAllLop());
        return "sinhvien/edit";
    }

    @PostMapping("/edit/{mssv}")
    public String editSinhVien(
            @PathVariable String mssv,
            @Valid @ModelAttribute("sinhVien") SinhVien sinhVien,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("dsLop", lopService.getAllLop());
            return "sinhvien/edit";
        }
        sinhVien.setMssv(mssv);
        sinhVienService.updateSinhVien(sinhVien);
        return "redirect:/sinhvien";
    }

    @GetMapping("/delete/{mssv}")
    public String deleteSinhVien(@PathVariable String mssv) {
        sinhVienService.deleteSinhVien(mssv);
        return "redirect:/sinhvien";
    }
}

