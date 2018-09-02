package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.domain.entities.WelderModel;
import pl.coderslab.domain.repositories.WelderModelRepository;
import pl.coderslab.domain.services.WelderModelService;

import java.util.List;

@RestController
    @RequestMapping("/api/weldmodels")
public class WelderModelRestController {

    private final WelderModelService modelService;

    @Autowired
    public WelderModelRestController(WelderModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public List<WelderModel> getAllModels(){
        return modelService.findAll();
    }
}
