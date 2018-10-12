package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.BrandService;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.web.errors.BadRequestException;
import pl.coderslab.web.errors.ErrorConstants;
import pl.coderslab.web.rest.assemblers.BrandResourceAssembler;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;
import pl.coderslab.web.rest.util.HeaderUtil;
import pl.coderslab.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BrandRestController {

    private static final String ENTITY_NAME = "brand";

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
    public ResponseEntity<List<WelderModelDTO>> getModelsByBrandId(@PathVariable Long id, Pageable pageable) {
        Page<WelderModelDTO> page = brandService.findWelderModelsByBrandId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brands/" + id + "/models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/brands")
    public ResponseEntity<BrandDTO> addBrand(@RequestBody @Valid BrandDTO brandDTO) throws URISyntaxException {
        if (brandDTO.getId() != null) {
            throw new BadRequestException("Id must be null", ENTITY_NAME, ErrorConstants.ERR_IDNULL);
        }
        BrandDTO result = brandService.save(brandDTO);
        return ResponseEntity.created(new URI("/api/brands/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
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
