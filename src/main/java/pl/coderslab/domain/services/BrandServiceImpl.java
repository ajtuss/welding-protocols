package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.repositories.BrandRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {


    private final ModelMapper modelMapper;

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void saveBrand(BrandDto brandDto) {
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brandRepository.save(brand);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
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


}
