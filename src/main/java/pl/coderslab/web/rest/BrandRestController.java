package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.dto.WelderModelDto;
import pl.coderslab.domain.services.BrandService;

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
    public List<BrandDto> getAllBrands() {
        return brandService.findAll();
    }

    @GetMapping("{id:\\d}")
    public BrandDto getBrandByBrandId(@PathVariable Long id){
        return brandService.findById(id);
    }


    @GetMapping("{id:\\d}/models")
    public List<WelderModelDto> getModelsByBrandId(@PathVariable Long id){
        return brandService.findWelderModelsByBrandId(id);
    }

    @DeleteMapping("{id:\\d}")
    public void deleteBrand(@PathVariable Long id){
        brandService.remove(id);
    }


}
