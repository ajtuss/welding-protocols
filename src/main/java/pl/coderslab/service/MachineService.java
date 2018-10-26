package pl.coderslab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.coderslab.service.dto.*;

import java.util.Optional;


public interface MachineService {

    MachineDTO save(MachineDTO machineDTO);

    Page<MachineDTO> findAll(Pageable pageable);

    Optional<MachineDTO> findById(Long id);

    void remove(Long id);

    Page<ValidProtocolDTO> findValidationsByMachineId(long id, Pageable pageable);
}
