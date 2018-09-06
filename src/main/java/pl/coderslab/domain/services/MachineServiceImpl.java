package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.dto.CustomerDto;
import pl.coderslab.domain.dto.MachineDto;
import pl.coderslab.domain.dto.WelderModelDto;
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
    public void save(MachineDto machineDto) {
        Machine machine = getMachine(machineDto);
        machineRepository.save(machine);
    }

    @Override
    public List<MachineDto> findAll() {
        List<Machine> machines = machineRepository.findAll();
        Type resultType = new TypeToken<List<MachineDto>>() {
        }.getType();
        return modelMapper.map(machines, resultType);
    }

    @Override
    public MachineDto findById(Long id) {
        Machine machine = machineRepository.findById(id).orElse(null);
        return modelMapper.map(machine, MachineDto.class);
    }

    @Override
    public void update(Long id, MachineDto machineDto) {
        Machine machine = getMachine(machineDto);
        machine.setId(id);
        machineRepository.save(machine);
    }

    @Override
    public void remove(Long id) {
        Machine machine = machineRepository.findById(id).get();
        machineRepository.delete(machine);
    }

    @Override
    public List<CustomerDto> findAllCustomers() {
        List<Customer> customers = machineRepository.findAllCustomers();
        Type resultType = new TypeToken<List<CustomerDto>>() {
        }.getType();
        return modelMapper.map(customers, resultType);
    }

    @Override
    public List<BrandDto> findAllBrands() {
        List<Brand> brands = machineRepository.findAllBrands();
        Type resultType = new TypeToken<List<BrandDto>>() {
        }.getType();
        return modelMapper.map(brands, resultType);
    }

    @Override
    public List<BrandDto> findAllBrands(Long customerId) {
        List<Brand> brands = machineRepository.findAllBrandsWhereCustomer(customerId);
        Type resultType = new TypeToken<List<BrandDto>>() {
        }.getType();
        return modelMapper.map(brands, resultType);    }

    @Override
    public List<WelderModelDto> findAllMachines(Long customerId, Long brandId) {
        List<WelderModel> models = machineRepository.findAllModels(customerId, brandId);
        Type resultType = new TypeToken<List<WelderModelDto>>() {
        }.getType();
        return modelMapper.map(models, resultType);    }

    private Machine getMachine(MachineDto machineDto) {
        Long modelId = machineDto.getWelderModelId();
        Long customerId = machineDto.getCustomerId();
        Machine machine = modelMapper.map(machineDto, Machine.class);
        WelderModel welderModel = modelRepository.findById(modelId).orElse(null);
        Customer customer = customerRepository.findById(customerId).orElse(null);
        machine.setWelderModel(welderModel);
        machine.setCustomer(customer);
        return machine;
    }
}
