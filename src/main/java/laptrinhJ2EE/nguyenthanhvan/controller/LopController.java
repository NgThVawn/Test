package laptrinhJ2EE.nguyenthanhvan.controller;

import laptrinhJ2EE.nguyenthanhvan.entity.Lop;
import laptrinhJ2EE.nguyenthanhvan.services.LopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lop")
public class LopController {
    @Autowired
    private LopService lopService;

    @GetMapping
    public String showAllLop(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {
        Page<Lop> dsLop = lopService.searchAndPaginate(keyword, page, 10);
        model.addAttribute("dsLop", dsLop.getContent());
        model.addAttribute("totalPages", dsLop.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "lop/list";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Integer id, Model model) {
        var lop = lopService.getLopById(id);
        if (lop.isEmpty()) {
            return "redirect:/lop";
        }
        model.addAttribute("lop", lop.get());
        return "lop/detail";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("lop", new Lop());
        return "lop/add";
    }

    @PostMapping("/add")
    public String addLop(@Valid @ModelAttribute("lop") Lop lop, BindingResult result) {
        if (result.hasErrors()) {
            return "lop/add";
        }
        lopService.addLop(lop);
        return "redirect:/lop";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        var lop = lopService.getLopById(id);
        if (lop.isEmpty()) {
            return "redirect:/lop";
        }
        model.addAttribute("lop", lop.get());
        return "lop/edit";
    }

    @PostMapping("/edit/{id}")
    public String editLop(
            @PathVariable Integer id,
            @Valid @ModelAttribute("lop") Lop lop,
            BindingResult result) {
        if (result.hasErrors()) {
            return "lop/edit";
        }
        lop.setMaLop(id);
        lopService.updateLop(lop);
        return "redirect:/lop";
    }

    @GetMapping("/delete/{id}")
    public String deleteLop(@PathVariable Integer id) {
        lopService.deleteLop(id);
        return "redirect:/lop";
    }
}
