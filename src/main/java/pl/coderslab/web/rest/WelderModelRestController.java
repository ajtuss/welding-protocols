package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.services.WelderModelService;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class WelderModelRestController {

    private final WelderModelService modelService;

    @Autowired
    public WelderModelRestController(WelderModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public List<WelderModelDTO> getAllModels() {
        return modelService.findAll();
    }

    @GetMapping("{id:\\d+}")
    public WelderModelDTO getModel(@PathVariable Long id) {
        return modelService.findById(id);
    }

    @DeleteMapping("{id:\\d+}")
    public void deleteModel(@PathVariable Long id) {
        modelService.remove(id);
    }
}
