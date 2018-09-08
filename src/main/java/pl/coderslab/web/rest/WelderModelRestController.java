package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.WelderModelDto;
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
    public List<WelderModelDto> getAllModels() {
        return modelService.findAll();
    }

    @GetMapping("{id:\\d+}")
    public WelderModelDto getModel(@PathVariable Long id) {
        return modelService.findById(id);
    }

    @DeleteMapping("{id:\\d+}")
    public void deleteModel(@PathVariable Long id) {
        modelService.remove(id);
    }
}
