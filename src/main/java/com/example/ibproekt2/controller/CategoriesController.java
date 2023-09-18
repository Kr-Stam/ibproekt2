package com.example.ibproekt2.controller;

import com.example.ibproekt2.entity.Category;
import com.example.ibproekt2.service.impl.CategoryServiceImpl;
import com.example.ibproekt2.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
//@RequestMapping("categories")
public class CategoriesController {

    private final CategoryServiceImpl categoryService;
    private final ProductServiceImpl productService;


    @GetMapping("/categories")
    public String getAllCategories(Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
        Category category = new Category();
        model.addAttribute("tmpCategory", category);
        return "categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(
            Model model,
            @PathVariable("id") long id
    ){

        //ne mi se veruva deka vaka mora da go napravam
        //mozebi ima podobar nachin, ama ispagja bojle e samo voopsto da ne se koristi jpa
        //i direktno da se koristi baza

        var result = categoryService.findById(id);
        if(result.isPresent()) {
            Category category = result.get();

            productService.getAllProducts().stream()
                    .filter(product -> product.getCategory().equals((category)))
                    .forEach(product -> productService.deleteById(product.getId()));

            categoryService.deleteCategoryById(id);
        }
        return "redirect:/categories";
    }

    @PostMapping(value = "/categories")
    public String saveCategory(@ModelAttribute("category")Category category){
        categoryService.save(category);
        return "redirect:/categories";
    }
}
