package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.entities.Customer;
import pl.coderslab.domain.entities.Machine;
import pl.coderslab.domain.exceptions.CustomerNotFoundException;
import pl.coderslab.domain.repositories.CustomerRepository;
import pl.coderslab.domain.repositories.MachineRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final MachineRepository machineRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, MachineRepository machineRepository, EntityManager entityManager, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.machineRepository = machineRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        Type resultType = new TypeToken<List<CustomerDTO>>() {}.getType();
        return modelMapper.map(customers, resultType);
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Customer save = customerRepository.save(customer);
        return modelMapper.map(save, CustomerDTO.class);
    }

    @Override
    public CustomerDTO findById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
        return modelMapper.map(customer,CustomerDTO.class);
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Customer updated = customerRepository.saveAndFlush(customer);
        entityManager.refresh(updated);
        return modelMapper.map(updated, CustomerDTO.class);
    }

    @Override
    public void remove(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
        customerRepository.delete(customer);
    }

    @Override
    public List<MachineDTO> findAllMachines(Long id) {
        List<Machine> machines = machineRepository.findByCustomerId(id);
        Type resultType = new TypeToken<List<MachineDTO>>() {}.getType();
        return modelMapper.map(machines, resultType);
    }
}
