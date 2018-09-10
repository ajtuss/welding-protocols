package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.services.BrandService;

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
        model.addAttribute("brand",new BrandDTO());
        return "forms/addBrand";
    }

    @PostMapping("/add")
    public String addBrand(@ModelAttribute BrandDTO brandDTO) {
        brandService.saveBrand(brandDTO);
        return "redirect:/brands";
    }

    @GetMapping("/{id:\\d+}")
    public String showEditBrand(@PathVariable Long id,
                                Model model) {
        BrandDTO brandDTO = brandService.findById(id);
        model.addAttribute("brand", brandDTO);
        return "forms/editBrand";
    }

    @PostMapping("/{id:\\d+}")
    public String editBrand(@PathVariable Long id,
                            @ModelAttribute BrandDTO brand) {
        brandService.updateBrand(id,brand);
        return "redirect:/brands";
    }


}
