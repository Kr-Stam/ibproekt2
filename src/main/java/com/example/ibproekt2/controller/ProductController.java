package com.example.ibproekt2.controller;

import com.example.ibproekt2.entity.Product;
import com.example.ibproekt2.service.impl.CategoryServiceImpl;
import com.example.ibproekt2.service.impl.ManufacturerServiceImpl;
import com.example.ibproekt2.service.impl.ProductServiceImpl;
import com.example.ibproekt2.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private final CategoryServiceImpl categoryService;
    private final ManufacturerServiceImpl manufacturerService;
    private final UserService userService;

    @GetMapping(path = "/products")
    public String getAllProducts(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping(path = "/products/add")
    public String createProduct(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("manufacturers", manufacturerService.getALlManufacturers());
        return "add-new-product";
    }

    @GetMapping(path = "/products/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id") long id){
        productService.deleteById(id);
        return "redirect:/products";
    }

    @PostMapping(path = "/products")
    public String saveProduct(@ModelAttribute("product") Product product){
        productService.saveProduct(product);
        return "redirect:/products";
    }
}
