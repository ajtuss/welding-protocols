package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.services.BrandService;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String showBrands() {
        return "brands";
    }

    @GetMapping("/add")
    public String showAddBrand(Model model) {
        model.addAttribute("brand",new BrandDto());
        return "forms/addBrand";
    }

    @PostMapping("/add")
    public String addBrand(@ModelAttribute BrandDto brandDto) {
        brandService.saveBrand(brandDto);
        return "redirect:/brands";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String showEditBrand(@PathVariable Long id,
                                Model model) {
        BrandDto brandDto = brandService.findById(id);
        model.addAttribute("brand", brandDto);
        return "forms/editBrand";
    }

    @PostMapping("/edit/{id:\\d+}")
    public String editBrand(@PathVariable Long id,
                            @ModelAttribute BrandDto brand) {
        brandService.updateBrand(id,brand);
        return "redirect:/brands";
    }

}
