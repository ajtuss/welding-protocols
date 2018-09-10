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

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {


    private final ModelMapper modelMapper;
    private final BrandRepository brandRepository;
    private final WelderModelRepository modelRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper, WelderModelRepository modelRepository) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
        this.modelRepository = modelRepository;
    }


    @Override
    public void saveBrand(BrandDTO brandDTO) {
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        brandRepository.save(brand);
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
        Brand brand = brandRepository.findById(id).orElseThrow(BrandNotFoundException::new);
        return modelMapper.map(brand, BrandDTO.class);
    }

    @Override
    public void updateBrand(Long id, BrandDTO brandDTO) {
        Brand brandOld = brandRepository.findById(id).orElseThrow(BrandNotFoundException::new);
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        brandOld.setName(brand.getName());
    }

    @Override
    public List<WelderModelDTO> findWelderModelsByBrandId(Long id) {
        List<WelderModel> welderModels = modelRepository.findAllByBrandId(id);
        Type resultType = new TypeToken<List<WelderModelDTO>>() {}.getType();
        return modelMapper.map(welderModels,resultType);
    }

    @Override
    public void remove(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(BrandNotFoundException::new);
        brandRepository.delete(brand);
    }


}
