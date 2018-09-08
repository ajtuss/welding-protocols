package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.dto.WelderModelDto;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.entities.WelderModel;
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
    public void saveBrand(BrandDto brandDto) {
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brandRepository.save(brand);
    }

    @Override
    public List<BrandDto> findAll() {
//        List<Brand> brands = brandRepository.findAll();
//        Type resultType = new TypeToken<List<BrandDto>>() {
//        }.getType();
//        return modelMapper.map(brands, resultType);
        return null;
    }

    @Override
    public BrandDto findById(Long id) {
        Brand brand = brandRepository.findById(id).orElse(null);

        return modelMapper.map(brand, BrandDto.class);
    }

    @Override
    public void updateBrand(Long id, BrandDto brandDto) {
        Brand brandOld = brandRepository.findById(id).orElse(null);
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brandOld.setName(brand.getName());
    }

    @Override
    public List<WelderModelDto> findWelderModelsByBrandId(Long id) {
        List<WelderModel> welderModels = modelRepository.findAllByBrandId(id);
        Type resultType = new TypeToken<List<WelderModelDto>>() {}.getType();
        return modelMapper.map(welderModels,resultType);
    }

    @Override
    public void remove(Long id) {
        Brand brand = brandRepository.findById(id).get();
        brandRepository.delete(brand);
    }


}
