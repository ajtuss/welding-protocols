package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.web.exceptions.InvalidRequestException;
import pl.coderslab.service.BrandService;
import pl.coderslab.web.rest.assemblers.BrandResourceAssembler;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;

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
    private final PagedResourcesAssembler<BrandDTO> resourcesAssembler;

    @Autowired
    public BrandRestController(BrandService brandService, BrandResourceAssembler assembler, WelderModelResourceAssembler modelAssembler, PagedResourcesAssembler<BrandDTO> resourcesAssembler) {
        this.brandService = brandService;
        this.assembler = assembler;
        this.modelAssembler = modelAssembler;
        this.resourcesAssembler = resourcesAssembler;
    }

    @GetMapping
    public PagedResources<?> getAll(@RequestParam(required = false, value = "search") String query, Pageable pageable) {
        Page<BrandDTO> brands;
        if (query == null) {
            brands = brandService.findAll(pageable);
        } else {
            brands = brandService.findAllByName(query, pageable);
        }
        if (!brands.hasContent()) {
            return resourcesAssembler.toEmptyResource(brands, BrandDTO.class);
        }
        return resourcesAssembler.toResource(brands, assembler);
    }

    @GetMapping(value = "/search/{query}")
    public PagedResources<?> getAllByName(@PathVariable(required = false) String query, Pageable pageable) {
        Page<BrandDTO> brands = brandService.findAllByName(query, pageable);
        if (!brands.hasContent()) {
            return resourcesAssembler.toEmptyResource(brands, BrandDTO.class);
        }
        return resourcesAssembler.toResource(brands, assembler);
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
