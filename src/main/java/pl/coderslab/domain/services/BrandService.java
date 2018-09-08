package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.dto.WelderModelDto;

import java.util.List;

public interface BrandService {

    void saveBrand(BrandDto brandDto);

    List<BrandDto> findAll();

    BrandDto findById(Long id);

    void updateBrand(Long id, BrandDto brand);

    List<WelderModelDto> findWelderModelsByBrandId(Long id);

    void remove(Long id);
}
