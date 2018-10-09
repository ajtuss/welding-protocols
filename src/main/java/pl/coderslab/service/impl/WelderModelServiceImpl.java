package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.domain.Brand;
import pl.coderslab.domain.Machine;
import pl.coderslab.domain.WelderModel;
import pl.coderslab.web.exceptions.BrandNotFoundException;
import pl.coderslab.web.exceptions.InvalidRequestException;
import pl.coderslab.web.exceptions.WelderModelNotFoundException;
import pl.coderslab.repository.BrandRepository;
import pl.coderslab.repository.MachineRepository;
import pl.coderslab.repository.WelderModelRepository;
import pl.coderslab.service.WelderModelService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class WelderModelServiceImpl implements WelderModelService {

    private final EntityManager entityManager;
    private final BrandRepository brandRepository;
    private final WelderModelRepository modelRepository;
    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public WelderModelServiceImpl(EntityManager entityManager, WelderModelRepository modelRepository, ModelMapper modelMapper, BrandRepository brandRepository, MachineRepository machineRepository) {
        this.entityManager = entityManager;
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
        this.machineRepository = machineRepository;
    }

    @Override
    public WelderModelDTO findById(Long id) {
        WelderModel model = modelRepository.findById(id).orElseThrow(() -> new WelderModelNotFoundException((id)));
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
    public WelderModelDTO save(WelderModelDTO modelDTO) {
        checkRange(modelDTO);
        WelderModel model = modelMapper.map(modelDTO, WelderModel.class);
        Long brandId = model.getBrand().getId();
        model.setBrand(brandRepository.findById(brandId).orElseThrow(() -> new BrandNotFoundException(brandId)));
        WelderModel save = modelRepository.save(model);
        return modelMapper.map(save, WelderModelDTO.class);
    }

    @Override
    public WelderModelDTO update(WelderModelDTO modelDTO) {
        checkRange(modelDTO);
        if (modelDTO.getId() == null || modelDTO.getVersionId() == null) {
            throw new InvalidRequestException("To update id and versionId can`t be null");
        }
        WelderModel model = modelMapper.map(modelDTO, WelderModel.class);

        setIdInRanges(model);
        Long brandId = model.getBrand().getId();
        model.setBrand(brandRepository.findById(brandId).orElseThrow(() -> new BrandNotFoundException(brandId)));
        WelderModel save = modelRepository.saveAndFlush(model);
        entityManager.refresh(save);
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

    private void checkRange(WelderModelDTO modelDTO) {
        if (modelDTO.getMig()) {
            if (modelDTO.getMigRange() == null)
                throw new InvalidRequestException("Mig Range can`t be null if Mig is checked");
        } else {
            modelDTO.setMigRange(null);
        }
        if (modelDTO.getMma()) {
            if (modelDTO.getMmaRange() == null)
                throw new InvalidRequestException("Mma Range can`t be null if Mma is checked");
        } else {
            modelDTO.setMmaRange(null);
        }
        if (modelDTO.getTig()) {
            if (modelDTO.getTigRange() == null)
                throw new InvalidRequestException("Tig Range can`t be null if Tig is checked");
        } else {
            modelDTO.setTigRange(null);
        }
    }

    private void setIdInRanges(WelderModel model) {
        WelderModel oldModel = modelRepository.findById(model.getId())
                                              .orElseThrow(() -> new WelderModelNotFoundException(model.getId()));
        if(oldModel.getMigRange()!= null && model.getMig()){
            model.getMigRange().setId(oldModel.getMigRange().getId());
        }
        if(oldModel.getMmaRange()!= null && model.getMma()){
            model.getMmaRange().setId(oldModel.getMmaRange().getId());
        }
        if(oldModel.getTigRange()!= null && model.getTig()){
            model.getTigRange().setId(oldModel.getTigRange().getId());
        }
    }
}
