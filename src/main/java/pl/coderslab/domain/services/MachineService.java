package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.*;

import java.util.List;


public interface MachineService {

    MachineDTO save(MachineCreationDTO machineDTO);

    List<MachineDTO> findAll();

    MachineDTO findById(Long id);

    MachineDTO update(MachineUpdateDTO machineUpdateDTO);

    void remove(Long id);

    List<CustomerDTO> findAllCustomers();

    List<BrandDTO> findAllBrands();

    List<BrandDTO> findAllBrands(Long customerId);

    List<WelderModelDTO> findAllMachines(Long customerId, Long brandId);

    WelderModelDTO findModelByMachineId(long id);

    CustomerDTO findCustomerByMachineId(long id);
}
