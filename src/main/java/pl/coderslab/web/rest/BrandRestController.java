package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandCreationDTO;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.BrandUpdateDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.services.BrandService;
import pl.coderslab.web.rest.assemblers.BrandResourceAssembler;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/brands")
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

    @GetMapping("/{id:\\d+}")
    public Resource<BrandDTO> getOne(@PathVariable Long id){
        BrandDTO brandDTO = brandService.findById(id);
        return assembler.toResource(brandDTO);
    }


    @GetMapping("/{id:\\d+}/models")
    public List<WelderModelDTO> getModelsByBrandId(@PathVariable Long id){
        return brandService.findWelderModelsByBrandId(id);
    }

    @PostMapping
    public ResponseEntity<Resource<BrandDTO>> addBrand(@RequestBody @Valid BrandCreationDTO brandCreationDTO) throws URISyntaxException {
        BrandDTO brandDTO = brandService.saveBrand(brandCreationDTO);
        Resource<BrandDTO> resource = assembler.toResource(brandDTO);
        return ResponseEntity.created(linkTo(methodOn(BrandRestController.class).getOne(brandDTO.getId())).toUri()).body(resource);
    }

    @PutMapping("/{id:\\d+}")
    public BrandDTO editBrand(@PathVariable Long id, @RequestBody @Valid BrandUpdateDTO brandUpdateDTO){
        return brandService.updateBrand(brandUpdateDTO);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        brandService.remove(id);
        return ResponseEntity.noContent().build();
    }


}
