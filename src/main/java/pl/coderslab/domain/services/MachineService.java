package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.dto.CustomerDto;
import pl.coderslab.domain.dto.MachineDto;
import pl.coderslab.domain.dto.WelderModelDto;

import java.util.List;


public interface MachineService {

    void save(MachineDto machineDto);

    List<MachineDto> findAll();

    MachineDto findById(Long id);

    void update(Long id, MachineDto machineDto);

    void remove(Long id);

    List<CustomerDto> findAllCustomers();

    List<BrandDto> findAllBrands();

    List<BrandDto> findAllBrands(Long customerId);

    List<WelderModelDto> findAllMachines(Long customerId, Long brandId);
}
