package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.WelderModelDTO;

import java.util.List;


public interface MachineService {

    MachineDTO save(MachineDTO machineDTO);

    List<MachineDTO> findAll();

    MachineDTO findById(Long id);

    MachineDTO update(MachineDTO machineUpdateDTO);

    void remove(Long id);

    List<CustomerDTO> findAllCustomers();

    List<BrandDTO> findAllBrands();

    List<BrandDTO> findAllBrands(Long customerId);

    List<WelderModelDTO> findAllMachines(Long customerId, Long brandId);

    WelderModelDTO findModelByMachineId(long id);

    CustomerDTO findCustomerByMachineId(long id);
}
