package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.WelderModelDTO;

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
