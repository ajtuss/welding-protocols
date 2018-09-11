package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.services.WelderModelService;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/models", consumes = MediaTypes.HAL_JSON_UTF8_VALUE, produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class WelderModelRestController {

    private final WelderModelService modelService;

    private final WelderModelResourceAssembler assembler;

    @Autowired
    public WelderModelRestController(WelderModelService modelService, WelderModelResourceAssembler assembler) {
        this.modelService = modelService;
        this.assembler = assembler;
    }

    @GetMapping
    public List<WelderModelDTO> getAll() {
        return modelService.findAll();
    }

    @GetMapping("{id:\\d+}")
    public Resource<WelderModelDTO> getOne(@PathVariable Long id) {
        WelderModelDTO modelDTO = modelService.findById(id);
        return assembler.toResource(modelDTO);
    }

    @DeleteMapping("{id:\\d+}")
    public void deleteModel(@PathVariable Long id) {
        modelService.remove(id);
    }

    @GetMapping("{id:\\d+}/brands")
    public Resource<BrandDTO> getBrandByModelId(@PathVariable Long id){
        return null;
    }

    @GetMapping("{id:\\d+}/machines")
    public Resources<Resource<MachineDTO>> getMachinesByModelId(@PathVariable Long id){
        return null;
    }
}
