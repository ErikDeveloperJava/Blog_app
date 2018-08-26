package myblog.controller;

import myblog.model.Category;
import myblog.pages.Pages;
import myblog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController implements Pages {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public @ResponseBody
    List<Category> categories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/admin/category/add")
    public String addGet(Model model) {
        model.addAttribute("category", new Category());
        return CATEGORY_ADD;
    }

    @PostMapping("/admin/category/add")
    public String addPost(@Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return CATEGORY_ADD;
        } else {
            categoryRepository.save(category);
            return "redirect:/admin";
        }
    }
}
