package pl.coderslab.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.exceptions.InvalidRequestException;
import pl.coderslab.domain.services.BrandService;
import pl.coderslab.rest.assemblers.BrandResourceAssembler;
import pl.coderslab.rest.assemblers.WelderModelResourceAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/brands", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class BrandRestController {

    private final BrandService brandService;
    private final BrandResourceAssembler assembler;
    private final WelderModelResourceAssembler modelAssembler;

    @Autowired
    public BrandRestController(BrandService brandService, BrandResourceAssembler assembler, WelderModelResourceAssembler modelAssembler) {
        this.brandService = brandService;
        this.assembler = assembler;
        this.modelAssembler = modelAssembler;
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
    public Resources<Resource<WelderModelDTO>> getModelsByBrandId(@PathVariable Long id) {
        List<Resource<WelderModelDTO>> models = brandService.findWelderModelsByBrandId(id)
                .stream()
                .map(modelAssembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(models, linkTo(methodOn(BrandRestController.class).getModelsByBrandId(id)).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<Resource<BrandDTO>> addBrand(@RequestBody @Valid BrandDTO brand) {
        BrandDTO brandDTO = brandService.saveBrand(brand);
        Resource<BrandDTO> resource = assembler.toResource(brandDTO);
        return ResponseEntity.created(linkTo(methodOn(BrandRestController.class).getOne(brandDTO.getId())).toUri())
                             .body(resource);
    }

    @PutMapping(value = "/{id:\\d+}")
    public Resource<BrandDTO> editBrand(@PathVariable Long id, @RequestBody @Valid BrandDTO brand) {
        if (!id.equals(brand.getId())) {
            throw new InvalidRequestException();
        }
        BrandDTO brandDTO = brandService.updateBrand(brand);
        return assembler.toResource(brandDTO);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        brandService.remove(id);
        return ResponseEntity.noContent().build();
    }


}
