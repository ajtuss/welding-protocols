package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.*;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.entities.Customer;
import pl.coderslab.domain.entities.Machine;
import pl.coderslab.domain.entities.WelderModel;
import pl.coderslab.domain.repositories.CustomerRepository;
import pl.coderslab.domain.repositories.MachineRepository;
import pl.coderslab.domain.repositories.WelderModelRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;
    private final WelderModelRepository modelRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, ModelMapper modelMapper, WelderModelRepository modelRepository, CustomerRepository customerRepository) {
        this.machineRepository = machineRepository;
        this.modelMapper = modelMapper;
        this.modelRepository = modelRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public MachineDTO save(MachineCreationDTO machineDTO) {
//        Machine machine = getMachine(machineDTO);
//        machineRepository.save(machine);
        return null; //todo
    }

    @Override
    public List<MachineDTO> findAll() {
        List<Machine> machines = machineRepository.findAll();
        Type resultType = new TypeToken<List<MachineDTO>>() {
        }.getType();
        return modelMapper.map(machines, resultType);
    }

    @Override
    public MachineDTO findById(Long id) {
        Machine machine = machineRepository.findById(id).orElse(null);
        return modelMapper.map(machine, MachineDTO.class);
    }

    @Override
    public MachineDTO update(MachineUpdateDTO machineUpdateDTO) {
//        Machine machine = getMachine(machineDTO);
//        machine.setId(id);
//        machineRepository.save(machine);
        return null; //todo
    }

    @Override
    public void remove(Long id) {
        Machine machine = machineRepository.findById(id).get();
        machineRepository.delete(machine);
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        List<Customer> customers = machineRepository.findAllCustomers();
        Type resultType = new TypeToken<List<CustomerDTO>>() {
        }.getType();
        return modelMapper.map(customers, resultType);
    }

    @Override
    public List<BrandDTO> findAllBrands() {
        List<Brand> brands = machineRepository.findAllBrands();
        Type resultType = new TypeToken<List<BrandDTO>>() {
        }.getType();
        return modelMapper.map(brands, resultType);
    }

    @Override
    public List<BrandDTO> findAllBrands(Long customerId) {
        List<Brand> brands = machineRepository.findAllBrandsWhereCustomer(customerId);
        Type resultType = new TypeToken<List<BrandDTO>>() {
        }.getType();
        return modelMapper.map(brands, resultType);    }

    @Override
    public List<WelderModelDTO> findAllMachines(Long customerId, Long brandId) {
        List<WelderModel> models = machineRepository.findAllModels(customerId, brandId);
        Type resultType = new TypeToken<List<WelderModelDTO>>() {
        }.getType();
        return modelMapper.map(models, resultType);    }

    @Override
    public WelderModelDTO findModelByMachineId(long id) {
        return null; //todo
    }

    @Override
    public CustomerDTO findCustomerByMachineId(long id) {
        return null; //todo
    }

    private Machine getMachine(MachineDTO machineDTO) {
        Long modelId = machineDTO.getWelderModelId();
        Long customerId = machineDTO.getCustomerId();
        Machine machine = modelMapper.map(machineDTO, Machine.class);
        WelderModel welderModel = modelRepository.findById(modelId).orElse(null);
        Customer customer = customerRepository.findById(customerId).orElse(null);
        machine.setWelderModel(welderModel);
        machine.setCustomer(customer);
        return machine;
    }
}
