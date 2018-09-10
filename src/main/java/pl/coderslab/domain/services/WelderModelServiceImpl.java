package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.entities.WelderModel;
import pl.coderslab.domain.repositories.BrandRepository;
import pl.coderslab.domain.repositories.WelderModelRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class WelderModelServiceImpl implements WelderModelService {

    private final BrandRepository brandRepository;

    private final WelderModelRepository modelRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public WelderModelServiceImpl(WelderModelRepository modelRepository, ModelMapper modelMapper, BrandRepository brandRepository) {
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
    }

    @Override
    public WelderModelDTO findById(Long id) {
        WelderModel model = modelRepository.findById(id).orElse(null);
        return modelMapper.map(model, WelderModelDTO.class);
    }

    @Override
    public List<WelderModelDTO> findAll() {
        List<WelderModel> models = modelRepository.findAll();
        Type resultType = new TypeToken<List<WelderModelDTO>>() {}.getType();
        return modelMapper.map(models, resultType);
    }

    @Override
    public void save(WelderModelDTO modelDTO) {
        WelderModel welderModel = getWelderModel(modelDTO);
        modelRepository.save(welderModel);
    }

    @Override
    public void update(Long id, WelderModelDTO modelDTO) {
        WelderModel model = getWelderModel(modelDTO);
        model.setId(id);
        modelRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        WelderModel model = modelRepository.findById(id).get();
        modelRepository.delete(model);
    }

    private WelderModel getWelderModel(WelderModelDTO modelDTO) {
        Long brandId = modelDTO.getBrandId();
        WelderModel model = modelMapper.map(modelDTO, WelderModel.class);
        Brand brand = brandRepository.findById(brandId).orElse(null);
        model.setBrand(brand);
        return model;
    }
}
