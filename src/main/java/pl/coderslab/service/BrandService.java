package pl.coderslab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.WelderModelDTO;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    BrandDTO saveBrand(BrandDTO brandDTO);

    Page<BrandDTO> findAll(Pageable pageable);

    Page<BrandDTO> findAllByName(String query, Pageable pageable);

    Optional<BrandDTO> findById(Long id);

    BrandDTO updateBrand(BrandDTO brandDTO);

    Page<WelderModelDTO> findWelderModelsByBrandId(Long id, Pageable pageable);

    void remove(Long id);
}
