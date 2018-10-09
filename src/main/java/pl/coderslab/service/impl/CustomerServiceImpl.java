package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.service.dto.CustomerDTO;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.domain.Customer;
import pl.coderslab.domain.Machine;
import pl.coderslab.web.exceptions.CustomerNotFoundException;
import pl.coderslab.repository.CustomerRepository;
import pl.coderslab.repository.MachineRepository;
import pl.coderslab.service.CustomerService;

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
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
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
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(customer);
    }

    @Override
    public List<MachineDTO> findAllMachines(Long id) {
        List<Machine> machines = machineRepository.findByCustomerId(id);
        Type resultType = new TypeToken<List<MachineDTO>>() {}.getType();
        return modelMapper.map(machines, resultType);
    }
}
