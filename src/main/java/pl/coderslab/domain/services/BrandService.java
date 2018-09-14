package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.WelderModelDTO;

import java.util.List;

public interface BrandService {

    BrandDTO saveBrand(BrandDTO brandDTO);

    List<BrandDTO> findAll();

    BrandDTO findById(Long id);

    BrandDTO updateBrand(BrandDTO brandDTO);

    List<WelderModelDTO> findWelderModelsByBrandId(Long id);

    void remove(Long id);
}
