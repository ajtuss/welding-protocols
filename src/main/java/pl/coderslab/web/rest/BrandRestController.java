package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
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
    public List<BrandDTO> getAllBrands() {
        return brandService.findAll();
    }

    @GetMapping("{id:\\d}")
    public BrandDTO getBrandByBrandId(@PathVariable Long id){
        return brandService.findById(id);
    }


    @GetMapping("{id:\\d}/models")
    public List<WelderModelDTO> getModelsByBrandId(@PathVariable Long id){
        return brandService.findWelderModelsByBrandId(id);
    }

    @DeleteMapping("{id:\\d}")
    public void deleteBrand(@PathVariable Long id){
        brandService.remove(id);
    }


}
