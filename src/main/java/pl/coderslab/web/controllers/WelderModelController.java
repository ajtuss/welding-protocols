package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.services.BrandService;
import pl.coderslab.domain.services.WelderModelService;

@Controller
@RequestMapping("/weldmodels")
public class WelderModelController {

    private final WelderModelService modelService;

    private final BrandService brandService;

    @Autowired
    public WelderModelController(WelderModelService modelService, BrandService brandService) {
        this.modelService = modelService;
        this.brandService = brandService;
    }


    @GetMapping
    public String showModels() {
        return "weldModels";
    }

    @GetMapping("/add")
    public String showAddModels(Model model) {
        model.addAttribute("welderModel", new WelderModelDTO());
        return "forms/addWelderModel";
    }

    @PostMapping("/add")
    public String addModel(@ModelAttribute WelderModelDTO welderModel) {
        modelService.save(welderModel);
        return "redirect:/weldmodels";
    }

}
