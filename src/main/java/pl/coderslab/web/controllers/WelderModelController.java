package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.WelderModelDto;
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
        model.addAttribute("welderModel", new WelderModelDto());
        return "forms/addWelderModel";
    }

    @PostMapping("/add")
    public String addModel(@ModelAttribute WelderModelDto welderModel) {
        modelService.save(welderModel);
        return "redirect:/weldmodels";
    }

    @GetMapping("/{id:\\d+}")
    public String showEditModel(@PathVariable Long id, Model model){
        WelderModelDto welderModelDto = modelService.findById(id);
//        System.out.println(welderModelDTO);
        model.addAttribute("welderModel", welderModelDto);
        return "forms/editWelderModel";
    }

    @PostMapping("/{id:\\d+}")
    public String editModel(@PathVariable Long id, @ModelAttribute WelderModelDto welderModelDTO){
        modelService.update(id,welderModelDTO);
        return "redirect:/weldmodels";
    }
}
