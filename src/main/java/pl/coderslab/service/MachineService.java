package pl.coderslab.service;

import pl.coderslab.service.dto.*;

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
