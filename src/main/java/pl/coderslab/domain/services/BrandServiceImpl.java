package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.entities.WelderModel;
import pl.coderslab.domain.exceptions.BrandNotFoundException;
import pl.coderslab.domain.repositories.BrandRepository;
import pl.coderslab.domain.repositories.WelderModelRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

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
    public List<BrandDTO> findAll() {
        List<Brand> brands = brandRepository.findAll();
        Type resultType = new TypeToken<List<BrandDTO>>() {
        }.getType();
        return modelMapper.map(brands, resultType);
    }

    @Override
    public BrandDTO findById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException(id));
        return modelMapper.map(brand, BrandDTO.class);
    }

    @Override
    public BrandDTO updateBrand(BrandDTO brandDTO) {
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        Brand save = brandRepository.saveAndFlush(brand);
        entityManager.refresh(save);
        return modelMapper.map(save,BrandDTO.class);
    }


    @Override
    public List<WelderModelDTO> findWelderModelsByBrandId(Long id) {
        List<WelderModel> welderModels = modelRepository.findAllByBrandId(id);
        Type resultType = new TypeToken<List<WelderModelDTO>>() {}.getType();
        return modelMapper.map(welderModels,resultType);
    }

    @Override
    public void remove(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException(id));
        brandRepository.delete(brand);
    }


}
