package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.*;

import java.util.List;

public interface WelderModelService {

    WelderModelDTO findById(Long id);
    List<WelderModelDTO> findAll();
    WelderModelDTO save(WelderModelCreationDTO model);
    WelderModelDTO update(WelderModelUpdateDTO model);

    void remove(Long id);

    BrandDTO findBrandByModelId(Long id);

    List<MachineDTO> findAllMachinesByModelId(Long id);
}
