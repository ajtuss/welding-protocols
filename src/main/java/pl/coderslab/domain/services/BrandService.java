package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.dto.WelderModelDto;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.entities.WelderModel;

import javax.transaction.Transactional;
import java.util.List;

public interface BrandService {

    void saveBrand(BrandDto brandDto);

    List<BrandDto> findAll();

    BrandDto findById(Long id);

    void updateBrand(Long id, BrandDto brand);

    List<WelderModelDto> findWelderModelsByBrandId(Long id);

    void remove(Long id);
}
