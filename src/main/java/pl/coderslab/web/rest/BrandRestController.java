package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandCreationDTO;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.BrandUpdateDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.services.BrandService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    private final BrandService brandService;

    @Autowired
    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public List<BrandDTO> getAllBrands() {
        return brandService.findAll();
    }

    @GetMapping("/{id:\\d+}")
    public BrandDTO getBrandByBrandId(@PathVariable Long id){
        return brandService.findById(id);
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
