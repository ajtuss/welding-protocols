package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.Customer;
import pl.coderslab.domain.Machine;
import pl.coderslab.domain.WelderModel;
import pl.coderslab.repository.CustomerRepository;
import pl.coderslab.repository.MachineRepository;
import pl.coderslab.repository.ValidProtocolRepository;
import pl.coderslab.repository.WelderModelRepository;
import pl.coderslab.service.MachineService;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.ValidProtocolDTO;
import pl.coderslab.web.errors.CustomerNotFoundException;
import pl.coderslab.web.errors.WelderModelNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;
    private final ValidProtocolRepository validProtocolRepository;
    private final WelderModelRepository modelRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, ValidProtocolRepository validProtocolRepository, ModelMapper modelMapper, WelderModelRepository modelRepository, CustomerRepository customerRepository) {
        this.machineRepository = machineRepository;
        this.validProtocolRepository = validProtocolRepository;
        this.modelMapper = modelMapper;
        this.modelRepository = modelRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public MachineDTO save(MachineDTO machineDTO) {
        Machine machine = getMachine(machineDTO);
        Machine saved = machineRepository.save(machine);
        return modelMapper.map(saved, MachineDTO.class);
    }

    @Override
    public Page<MachineDTO> findAll(Pageable pageable) {
        Page<Machine> machines = machineRepository.findAll(pageable);
        return machines.map(machine -> modelMapper.map(machine, MachineDTO.class));
    }

    @Override
    public Optional<MachineDTO> findById(Long id) {
        return machineRepository.findById(id)
                                .map(machine -> modelMapper.map(machine, MachineDTO.class));
    }

    @Override
    public void remove(Long id) {
        machineRepository.findById(id)
                         .ifPresent(machineRepository::delete);
    }

    @Override
    public Page<ValidProtocolDTO> findValidationsByMachineId(long id, Pageable pageable) {
        return validProtocolRepository.findByMachineId(id, pageable)
                                      .map(vp -> modelMapper.map(vp, ValidProtocolDTO.class));
    }

    private Machine getMachine(MachineDTO machineDTO) {
        //todo add validation for model and customer to check existing in db
        Long modelId = machineDTO.getWelderModelId();
        Long customerId = machineDTO.getCustomerId();
        Machine machine = modelMapper.map(machineDTO, Machine.class);
        WelderModel welderModel = modelRepository.findById(modelId)
                                                 .orElseThrow(() -> new WelderModelNotFoundException(modelId));
        Customer customer = customerRepository.findById(customerId)
                                              .orElseThrow(() -> new CustomerNotFoundException(customerId));
        machine.setWelderModel(welderModel);
        machine.setCustomer(customer);
        return machine;
    }
}
