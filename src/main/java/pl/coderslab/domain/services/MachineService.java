package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.*;

import java.util.List;


public interface MachineService {

    MachineDTO save(MachineDTO machineDTO);

    List<MachineDTO> findAll();

    MachineDTO findById(Long id);

    MachineDTO update(MachineDTO machineUpdateDTO);

    void remove(Long id);

    WelderModelDTO findModelByMachineId(long id);

    CustomerDTO findCustomerByMachineId(long id);

    List<ValidProtocolDTO> findValidationsByMachineId(long id);
}
