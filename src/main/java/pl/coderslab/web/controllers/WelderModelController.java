package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String showAddModel(Model model) {
        model.addAttribute("welderModel", new WelderModelDTO());
        return "forms/addWelderModel";
    }

    @PostMapping("/add")
    public String addModel(@ModelAttribute WelderModelDTO welderModel) {
        modelService.save(welderModel);
        return "redirect:/weldmodels";
    }

    @GetMapping("/{id:\\d+}")
    public String showEditModel(@PathVariable Long id, Model model){
        WelderModelDTO welderModelDTO = modelService.findById(id);
        model.addAttribute("welderModel", welderModelDTO);
        return "forms/editWelderModel";
    }

    @PostMapping("/{id:\\d+}")
    public String editModel(@PathVariable Long id, @ModelAttribute WelderModelDTO welderModelDTO){
//        modelService.update(welderModelDTO);
        return "redirect:/weldmodels";
    }
}
