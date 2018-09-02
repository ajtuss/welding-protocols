package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.entities.Brand;

import javax.transaction.Transactional;
import java.util.List;

public interface BrandService {

    void saveBrand(BrandDto brandDto);

    List<Brand> findAll();

    BrandDto findById(Long id);

    void updateBrand(Long id, BrandDto brand);
}
