package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.domain.Brand;
import pl.coderslab.domain.WelderModel;
import pl.coderslab.web.exceptions.BrandNotFoundException;
import pl.coderslab.repository.BrandRepository;
import pl.coderslab.repository.WelderModelRepository;
import pl.coderslab.service.BrandService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {


    private final ModelMapper modelMapper;
    private final BrandRepository brandRepository;
    private final WelderModelRepository modelRepository;
    private final EntityManager entityManager;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper, WelderModelRepository modelRepository, EntityManager entityManager) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
        this.modelRepository = modelRepository;
        this.entityManager = entityManager;
    }

    @Override
    public BrandDTO saveBrand(BrandDTO brandDTO) {
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        Brand save = brandRepository.save(brand);
        return modelMapper.map(save, BrandDTO.class);
    }

    @Override
    public Page<BrandDTO> findAll(Pageable pageable) {
        Page<Brand> brands = brandRepository.findAll(pageable);
        return brands.map(brand -> modelMapper.map(brand, BrandDTO.class));
    }

    @Override
    public Page<BrandDTO> findAllByName(String query, Pageable pageable) {
        Page<Brand> brands = brandRepository.findByNameStartingWithIgnoreCase(query, pageable);
        return brands.map(brand -> modelMapper.map(brand, BrandDTO.class));
    }

    @Override
    public Optional<BrandDTO> findById(Long id) {
        return brandRepository.findById(id).map(brand -> modelMapper.map(brand, BrandDTO.class));
    }

    @Override
    public BrandDTO updateBrand(BrandDTO brandDTO) {
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        Brand save = brandRepository.saveAndFlush(brand);
        entityManager.refresh(save);
        return modelMapper.map(save, BrandDTO.class);
    }


    @Override
    public Page<WelderModelDTO> findWelderModelsByBrandId(Long id, Pageable pageable) {
        return modelRepository.findAllByBrandId(id, pageable)
                              .map(model -> modelMapper.map(model, WelderModelDTO.class));
    }

    @Override
    public void remove(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException(id));
        brandRepository.delete(brand);
    }


}
