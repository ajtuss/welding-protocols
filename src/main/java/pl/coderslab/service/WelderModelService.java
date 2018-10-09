package pl.coderslab.service;

import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.WelderModelDTO;

import java.util.List;

public interface WelderModelService {

    WelderModelDTO findById(Long id);
    List<WelderModelDTO> findAll();
    WelderModelDTO save(WelderModelDTO model);
    WelderModelDTO update(WelderModelDTO model);

    void remove(Long id);

    BrandDTO findBrandByModelId(Long id);

    List<MachineDTO> findAllMachinesByModelId(Long id);
}
