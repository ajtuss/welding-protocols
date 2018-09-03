package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.WelderModelDto;
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
    public WelderModelDto findById(Long id) {
        WelderModel model = modelRepository.findById(id).orElse(null);
        return modelMapper.map(model, WelderModelDto.class);
    }

    @Override
    public List<WelderModelDto> findAll() {
        List<WelderModel> models = modelRepository.findAll();
        Type resultType = new TypeToken<List<WelderModelDto>>() {}.getType();
        return modelMapper.map(models, resultType);
    }

    @Override
    public void save(WelderModelDto modelDTO) {
        WelderModel welderModel = modelMapper.map(modelDTO, WelderModel.class);
        modelRepository.save(welderModel);
    }

    @Override
    public void update(Long id, WelderModelDto modelDTO) {
        WelderModel model = modelMapper.map(modelDTO, WelderModel.class);
        model.setId(id);
        System.out.println(model);
        modelRepository.save(model);
        //todo
    }
}
