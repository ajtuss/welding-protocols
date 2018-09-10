package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.WelderModelDTO;

import java.util.List;

public interface BrandService {

    void saveBrand(BrandDTO brandDTO);

    List<BrandDTO> findAll();

    BrandDTO findById(Long id);

    void updateBrand(Long id, BrandDTO brand);

    List<WelderModelDTO> findWelderModelsByBrandId(Long id);

    void remove(Long id);
}
