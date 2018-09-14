package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.entities.Machine;
import pl.coderslab.domain.entities.WelderModel;
import pl.coderslab.domain.exceptions.BrandNotFoundException;
import pl.coderslab.domain.exceptions.WelderModelNotFoundException;
import pl.coderslab.domain.repositories.BrandRepository;
import pl.coderslab.domain.repositories.MachineRepository;
import pl.coderslab.domain.repositories.WelderModelRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class WelderModelServiceImpl implements WelderModelService {

    private final BrandRepository brandRepository;

    private final WelderModelRepository modelRepository;

    private final MachineRepository machineRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public WelderModelServiceImpl(WelderModelRepository modelRepository, ModelMapper modelMapper, BrandRepository brandRepository, MachineRepository machineRepository) {
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
        this.machineRepository = machineRepository;
    }

    @Override
    public WelderModelDTO findById(Long id) {
        WelderModel model = modelRepository.findById(id).orElse(null);
        return modelMapper.map(model, WelderModelDTO.class);
    }

    @Override
    public List<WelderModelDTO> findAll() {
        List<WelderModel> models = modelRepository.findAll();
        Type resultType = new TypeToken<List<WelderModelDTO>>() {
        }.getType();
        return modelMapper.map(models, resultType);
    }

    @Override
    public WelderModelDTO save(WelderModelDTO modelCreationDTO) {
        WelderModel model = modelMapper.map(modelCreationDTO, WelderModel.class);
//        if(modelCreationDTO.getMig() != null && mo)
        Long brandId = model.getBrand().getId();
        model.setBrand(brandRepository.findById(brandId).orElseThrow(() -> new BrandNotFoundException(brandId)));
        WelderModel save = modelRepository.save(model);
        return modelMapper.map(save, WelderModelDTO.class);
    }

    @Override
    public WelderModelDTO update(WelderModelDTO modelUpdateDTO) {
        WelderModel welderModel = modelMapper.map(modelUpdateDTO, WelderModel.class);
        WelderModel save = modelRepository.save(welderModel);
        return modelMapper.map(save, WelderModelDTO.class);
    }

    @Override
    public void remove(Long id) {
        WelderModel model = modelRepository.findById(id).orElseThrow(() -> new WelderModelNotFoundException(id));
        modelRepository.delete(model);
    }

    @Override
    public BrandDTO findBrandByModelId(Long id) {
        Brand brand = brandRepository.findByModelId(id).orElseThrow(() -> new WelderModelNotFoundException(id));
        return modelMapper.map(brand, BrandDTO.class);
    }

    @Override
    public List<MachineDTO> findAllMachinesByModelId(Long id) {
        List<Machine> machines = machineRepository.findMachinesByWelderModelId(id);
        Type resultType = new TypeToken<List<MachineDTO>>() {
        }.getType();
        return modelMapper.map(machines, resultType);
    }
}
