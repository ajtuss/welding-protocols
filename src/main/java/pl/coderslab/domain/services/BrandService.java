package pl.coderslab.domain.services;

import pl.coderslab.domain.entities.Brand;

import java.util.List;

public interface BrandService {

    void saveBrand(Brand brand);

    List<Brand> findAll();

    Brand findById(Long id);

    void updateBrand(Long id, Brand brand);
}
