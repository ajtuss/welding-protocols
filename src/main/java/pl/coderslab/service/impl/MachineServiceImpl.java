package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.service.dto.CustomerDTO;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.ValidProtocolDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.domain.Customer;
import pl.coderslab.domain.Machine;
import pl.coderslab.domain.ValidProtocol;
import pl.coderslab.domain.WelderModel;
import pl.coderslab.web.errors.CustomerNotFoundException;
import pl.coderslab.web.errors.MachineNotFoundException;
import pl.coderslab.web.errors.WelderModelNotFoundException;
import pl.coderslab.repository.CustomerRepository;
import pl.coderslab.repository.MachineRepository;
import pl.coderslab.repository.ValidProtocolRepository;
import pl.coderslab.repository.WelderModelRepository;
import pl.coderslab.service.MachineService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;
    private final WelderModelRepository modelRepository;
    private final CustomerRepository customerRepository;
    private final ValidProtocolRepository protocolRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, ModelMapper modelMapper, WelderModelRepository modelRepository, CustomerRepository customerRepository, ValidProtocolRepository protocolRepository, EntityManager entityManager) {
        this.machineRepository = machineRepository;
        this.modelMapper = modelMapper;
        this.modelRepository = modelRepository;
        this.customerRepository = customerRepository;
        this.protocolRepository = protocolRepository;
        this.entityManager = entityManager;
    }


    @Override
    public MachineDTO save(MachineDTO machineDTO) {
        Machine machine = getMachine(machineDTO);

        Machine saved = machineRepository.save(machine);
        return modelMapper.map(saved, MachineDTO.class);
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
        Machine machine = machineRepository.findById(id).orElseThrow(() -> new MachineNotFoundException(id));
        return modelMapper.map(machine, MachineDTO.class);
    }

    @Override
    public MachineDTO update(MachineDTO machineDTO) {
        Machine machine = getMachine(machineDTO);
        Machine save = machineRepository.saveAndFlush(machine);
        entityManager.refresh(save);
        return modelMapper.map(save, MachineDTO.class);
    }

    @Override
    public void remove(Long id) {
        Machine machine = machineRepository.findById(id).get();
        machineRepository.delete(machine);
    }


    @Override
    public WelderModelDTO findModelByMachineId(long id) {
        WelderModel model = modelRepository.findByModelId(id);
        return modelMapper.map(model, WelderModelDTO.class);
    }

    @Override
    public CustomerDTO findCustomerByMachineId(long id) {
        Customer customer = customerRepository.findByMachineId(id);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public List<ValidProtocolDTO> findValidationsByMachineId(long id) {
        List<ValidProtocol> protocols = protocolRepository.findByMachineId(id);
        Type resultType = new TypeToken<List<ValidProtocolDTO>>() {
        }.getType();
        return modelMapper.map(protocols, resultType);
    }

    private Machine getMachine(MachineDTO machineDTO) {
        Long modelId = machineDTO.getWelderModelId();
        Long customerId = machineDTO.getCustomerId();
        Machine machine = modelMapper.map(machineDTO, Machine.class);
        WelderModel welderModel = modelRepository.findById(modelId).orElseThrow(() -> new WelderModelNotFoundException(modelId));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        machine.setWelderModel(welderModel);
        machine.setCustomer(customer);
        return machine;
    }
}
