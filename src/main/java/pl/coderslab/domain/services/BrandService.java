package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.BrandCreationDTO;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.BrandUpdateDTO;
import pl.coderslab.domain.dto.WelderModelDTO;

import java.util.List;

public interface BrandService {

    BrandDTO saveBrand(BrandCreationDTO brandCreationDTO);

    List<BrandDTO> findAll();

    BrandDTO findById(Long id);

    BrandDTO updateBrand(BrandUpdateDTO brandUpdateDTO);

    List<WelderModelDTO> findWelderModelsByBrandId(Long id);

    void remove(Long id);
}
