package laptrinhJ2EE.nguyenthanhvan.controller;

import laptrinhJ2EE.nguyenthanhvan.entity.MonHoc;
import laptrinhJ2EE.nguyenthanhvan.services.MonHocService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/monhoc")
public class MonHocController {
    @Autowired
    private MonHocService monHocService;

    @GetMapping
    public String showAllMonHoc(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {
        Page<MonHoc> dsMonHoc;
        if (keyword == null || keyword.isEmpty()) {
            dsMonHoc = monHocService.getAllMonHocPaginated(page, 10);
        } else {
            dsMonHoc = monHocService.searchAndPaginate(keyword, page, 10);
        }
        model.addAttribute("dsMonHoc", dsMonHoc.getContent());
        model.addAttribute("totalPages", dsMonHoc.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "monhoc/list";
    }

    @GetMapping("/{maMon}")
    public String showDetail(@PathVariable String maMon, Model model) {
        var monHoc = monHocService.getMonHocById(maMon);
        if (monHoc.isEmpty()) {
            return "redirect:/monhoc";
        }
        model.addAttribute("monHoc", monHoc.get());
        return "monhoc/detail";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("monHoc", new MonHoc());
        return "monhoc/add";
    }

    @PostMapping("/add")
    public String addMonHoc(
            @Valid @ModelAttribute("monHoc") MonHoc monHoc,
            BindingResult result) {
        if (result.hasErrors()) {
            return "monhoc/add";
        }
        monHocService.addMonHoc(monHoc);
        return "redirect:/monhoc";
    }

    @GetMapping("/edit/{maMon}")
    public String showEditForm(@PathVariable String maMon, Model model) {
        var monHoc = monHocService.getMonHocById(maMon);
        if (monHoc.isEmpty()) {
            return "redirect:/monhoc";
        }
        model.addAttribute("monHoc", monHoc.get());
        return "monhoc/edit";
    }

    @PostMapping("/edit/{maMon}")
    public String editMonHoc(
            @PathVariable String maMon,
            @Valid @ModelAttribute("monHoc") MonHoc monHoc,
            BindingResult result) {
        if (result.hasErrors()) {
            return "monhoc/edit";
        }
        monHoc.setMaMon(maMon);
        monHocService.updateMonHoc(monHoc);
        return "redirect:/monhoc";
    }

    @GetMapping("/delete/{maMon}")
    public String deleteMonHoc(@PathVariable String maMon) {
        monHocService.deleteMonHoc(maMon);
        return "redirect:/monhoc";
    }
}

