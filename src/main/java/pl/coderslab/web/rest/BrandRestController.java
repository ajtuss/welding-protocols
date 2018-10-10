package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.BrandService;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.web.rest.assemblers.BrandResourceAssembler;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;
import pl.coderslab.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @GetMapping("/brands")
    public ResponseEntity<List<BrandDTO>> getAll(Pageable pageable) {

        Page<BrandDTO> page = brandService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brands");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

//    @GetMapping(value = "/brands/search/{query}")
//    public PagedResources<?> getAllByName(@PathVariable(required = false) String query, Pageable pageable) {
//        Page<BrandDTO> brands = brandService.findAllByName(query, pageable);
//        if (!brands.hasContent()) {
//            return resourcesAssembler.toEmptyResource(brands, BrandDTO.class);
//        }
//        return resourcesAssembler.toResource(brands, assembler);
//    }

    @GetMapping(value = "/brands/{id:\\d+}")
    public ResponseEntity<BrandDTO> getOne(@PathVariable Long id) {
        Optional<BrandDTO> brandDTO = brandService.findById(id);
        return brandDTO.map(response -> ResponseEntity.ok().body(response))
                       .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping(value = "/brands/{id:\\d+}/models")
    public Resources<Resource<WelderModelDTO>> getModelsByBrandId(@PathVariable Long id) {
        List<Resource<WelderModelDTO>> models = brandService.findWelderModelsByBrandId(id)
                                                            .stream()
                                                            .map(modelAssembler::toResource)
                                                            .collect(Collectors.toList());
        return new Resources<>(models, linkTo(methodOn(BrandRestController.class).getModelsByBrandId(id)).withSelfRel());
    }

    @PostMapping("/brands")
    public ResponseEntity<Resource<BrandDTO>> addBrand(@RequestBody @Valid BrandDTO brand) {
        BrandDTO brandDTO = brandService.saveBrand(brand);
        Resource<BrandDTO> resource = assembler.toResource(brandDTO);
        return ResponseEntity.created(linkTo(methodOn(BrandRestController.class).getOne(brandDTO.getId())).toUri())
                             .body(resource);
    }

    @PutMapping(value = "/brands")
    public Resource<BrandDTO> editBrand(@PathVariable Long id, @RequestBody @Valid BrandDTO brand) {
        BrandDTO brandDTO = brandService.updateBrand(brand);
        return assembler.toResource(brandDTO);
    }

    @DeleteMapping("/brands/{id:\\d+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        brandService.remove(id);
        return ResponseEntity.noContent().build();
    }


}
