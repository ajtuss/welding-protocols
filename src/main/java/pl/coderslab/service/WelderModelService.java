package pl.coderslab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.WelderModelDTO;

import java.util.Optional;

public interface WelderModelService {

    Optional<WelderModelDTO> findById(Long id);

    Page<WelderModelDTO> findAll(Pageable pageable);

    WelderModelDTO save(WelderModelDTO model);

    WelderModelDTO update(WelderModelDTO model);

    void remove(Long id);

    Page<MachineDTO> findAllMachinesByModelId(Long id);
}
