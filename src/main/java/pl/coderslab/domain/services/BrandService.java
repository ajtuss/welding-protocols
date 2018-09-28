package pl.coderslab.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.WelderModelDTO;

import java.util.List;

public interface BrandService {

    BrandDTO saveBrand(BrandDTO brandDTO);

    Page<BrandDTO> findAll(Pageable pageable);

    BrandDTO findById(Long id);

    BrandDTO updateBrand(BrandDTO brandDTO);

    List<WelderModelDTO> findWelderModelsByBrandId(Long id);

    void remove(Long id);
}
