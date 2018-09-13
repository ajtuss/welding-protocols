package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.domain.dto.*;
import pl.coderslab.domain.services.WelderModelService;
import pl.coderslab.web.rest.assemblers.BrandResourceAssembler;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/models", consumes = MediaTypes.HAL_JSON_UTF8_VALUE, produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class WelderModelRestController {

    private final WelderModelService modelService;

    private final WelderModelResourceAssembler assembler;

    private final BrandResourceAssembler brandAssembler;

    @Autowired
    public WelderModelRestController(WelderModelService modelService, WelderModelResourceAssembler assembler, BrandResourceAssembler brandAssembler) {
        this.modelService = modelService;
        this.assembler = assembler;
        this.brandAssembler = brandAssembler;
    }

    @GetMapping
    public Resources<Resource<WelderModelDTO>> getAll() {
        List<Resource<WelderModelDTO>> models = modelService.findAll()
                                                            .stream()
                                                            .map(assembler::toResource)
                                                            .collect(Collectors.toList());
        return new Resources<>(models, linkTo(methodOn(WelderModelRestController.class).getAll()).withSelfRel());
    }

    @GetMapping("{id:\\d+}")
    public Resource<WelderModelDTO> getOne(@PathVariable Long id) {
        WelderModelDTO modelDTO = modelService.findById(id);
        return assembler.toResource(modelDTO);
    }

    @DeleteMapping("{id:\\d+}")
    public ResponseEntity<?> deleteModel(@PathVariable Long id) {
        modelService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Resource<WelderModelDTO>> addModel(@RequestBody @Valid WelderModelCreationDTO modelCreationDTO) {
        WelderModelDTO welderModelDTO = modelService.save(modelCreationDTO);
        Resource<WelderModelDTO> resource = assembler.toResource(welderModelDTO);
        return ResponseEntity.created(linkTo(methodOn(WelderModelRestController.class).getOne(welderModelDTO.getId())).toUri())
                             .body(resource);
    }

    @PutMapping("{id:\\d+}")
    public Resource<WelderModelDTO> editModel(@PathVariable Long id, @RequestBody @Valid WelderModelUpdateDTO modelUpdateDTO) {
        if (!id.equals(modelUpdateDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id from URL and field must be the same");
        }
        WelderModelDTO modelDTO = modelService.update(modelUpdateDTO);
        return assembler.toResource(modelDTO);
    }

    @GetMapping("{id:\\d+}/brands")
    public Resource<BrandDTO> getBrandByModelId(@PathVariable Long id) {
        BrandDTO brandDTO = modelService.findBrandByModelId(id);
        return brandAssembler.toResource(brandDTO);
    }

    @GetMapping("{id:\\d+}/machines")
    public Resources<Resource<MachineDTO>> getMachinesByModelId(@PathVariable Long id) {
        return null;
    }
}
