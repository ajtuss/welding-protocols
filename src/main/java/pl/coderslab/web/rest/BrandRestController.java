package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandCreationDTO;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.BrandUpdateDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.services.BrandService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    private final BrandService brandService;

    @Autowired
    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public Resources<Resource<BrandDTO>> getAll() {
        List<Resource<BrandDTO>> brands = brandService.findAll().stream()
                                                      .map(this::getBrandDTOResource)
                                                      .collect(Collectors.toList());
        return new Resources<>(brands,
                linkTo(methodOn(BrandRestController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id:\\d+}")
    public Resource<BrandDTO> getOne(@PathVariable Long id){
        BrandDTO brandDTO = brandService.findById(id);
        return getBrandDTOResource(brandDTO);
    }

    private Resource<BrandDTO> getBrandDTOResource(BrandDTO brandDTO) {
        return new Resource<>(brandDTO,
                linkTo(methodOn(BrandRestController.class).getOne(brandDTO.getId())).withSelfRel(),
                linkTo(methodOn(BrandRestController.class).getAll()).withRel("brands"),
                linkTo(methodOn(BrandRestController.class).getModelsByBrandId(brandDTO.getId())).withRel("models"));
    }


    @GetMapping("/{id:\\d+}/models")
    public List<WelderModelDTO> getModelsByBrandId(@PathVariable Long id){
        return brandService.findWelderModelsByBrandId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandDTO addBrand(@RequestBody @Valid BrandCreationDTO brandCreationDTO){
        return brandService.saveBrand(brandCreationDTO);
    }

    @PutMapping("/{id:\\d+}")
    public BrandDTO editBrand(@PathVariable Long id, @RequestBody @Valid BrandUpdateDTO brandUpdateDTO){
        return brandService.updateBrand(brandUpdateDTO);
    }

    @DeleteMapping("/{id:\\d}")
    public void deleteBrand(@PathVariable Long id){
        brandService.remove(id);
    }


}
