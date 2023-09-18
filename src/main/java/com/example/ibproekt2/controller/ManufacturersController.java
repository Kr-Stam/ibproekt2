package com.example.ibproekt2.controller;

import com.example.ibproekt2.entity.Manufacturer;
import com.example.ibproekt2.service.impl.ManufacturerServiceImpl;
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
public class ManufacturersController {

    private final ManufacturerServiceImpl manufacturerService;
    private final ProductServiceImpl productService;


    @GetMapping("/manufacturers")
    public String getAllCategories(Model model){
        Manufacturer manufacturer = new Manufacturer();
        model.addAttribute("tmpManufacturer", manufacturer);
        model.addAttribute("manufacturers", manufacturerService.getALlManufacturers());
        return "manufacturers";
    }

    @GetMapping("/manufacturers/delete/{id}")
    public String deleteCategory(
            Model model,
            @PathVariable("id") long id
    ){

        //ne mi se veruva deka vaka mora da go napravam
        //mozebi ima podobar nachin, ama ispagja bojle e samo voopsto da ne se koristi jpa
        //i direktno da se koristi baza

        var result = manufacturerService.findById(id);
        if(result.isPresent()) {
            Manufacturer manufacturer = result.get();

            productService.getAllProducts().stream()
                    .filter(product -> product.getManufacturer().equals((manufacturer)))
                    .forEach(product -> productService.deleteById(product.getId()));

            manufacturerService.deleteById(id);
        }
        return "redirect:/manufacturers";
    }

    @PostMapping(value = "/manufacturers")
    public String saveCategory(@ModelAttribute("manufacturer") Manufacturer manufacturer){
        manufacturerService.save(manufacturer);
        return "redirect:/manufacturers";
    }
}
