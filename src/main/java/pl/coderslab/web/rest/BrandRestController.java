package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.domain.dto.BrandCreationDTO;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.BrandUpdateDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.services.BrandService;
import pl.coderslab.web.rest.assemblers.BrandResourceAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/brands", consumes = MediaTypes.HAL_JSON_UTF8_VALUE, produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class BrandRestController {

    private final BrandService brandService;
    private final BrandResourceAssembler assembler;

    @Autowired
    public BrandRestController(BrandService brandService, BrandResourceAssembler assembler) {
        this.brandService = brandService;
        this.assembler = assembler;
    }

    @GetMapping
    public Resources<Resource<BrandDTO>> getAll() {
        List<Resource<BrandDTO>> brands = brandService.findAll().stream()
                                                      .map(assembler::toResource)
                                                      .collect(Collectors.toList());
        return new Resources<>(brands,
                linkTo(methodOn(BrandRestController.class).getAll()).withSelfRel());
    }

    @GetMapping(value = "/{id:\\d+}")
    public Resource<BrandDTO> getOne(@PathVariable Long id) {
        BrandDTO brandDTO = brandService.findById(id);
        return assembler.toResource(brandDTO);
    }


    @GetMapping(value = "/{id:\\d+}/models")
    public List<WelderModelDTO> getModelsByBrandId(@PathVariable Long id) {
        return brandService.findWelderModelsByBrandId(id); //todo add Response Entity
    }

    @PostMapping
    public ResponseEntity<Resource<BrandDTO>> addBrand(@RequestBody @Valid BrandCreationDTO brandCreationDTO) {
        BrandDTO brandDTO = brandService.saveBrand(brandCreationDTO);
        Resource<BrandDTO> resource = assembler.toResource(brandDTO);
        return ResponseEntity.created(linkTo(methodOn(BrandRestController.class).getOne(brandDTO.getId())).toUri())
                             .body(resource);
    }

    @PutMapping(value = "/{id:\\d+}")
    public Resource<BrandDTO> editBrand(@PathVariable Long id, @RequestBody @Valid BrandUpdateDTO brandUpdateDTO) {
        if (!id.equals(brandUpdateDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id from URL and field must be the same");
        }
        BrandDTO brandDTO = brandService.updateBrand(brandUpdateDTO);
        return assembler.toResource(brandDTO);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        brandService.remove(id);
        return ResponseEntity.noContent().build();
    }


}
