package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.Customer;
import pl.coderslab.repository.CustomerRepository;
import pl.coderslab.repository.MachineRepository;
import pl.coderslab.service.CustomerService;
import pl.coderslab.service.dto.CustomerDTO;
import pl.coderslab.service.dto.MachineDTO;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

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
    public Page<CustomerDTO> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                                 .map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Customer save = customerRepository.save(customer);
        return modelMapper.map(save, CustomerDTO.class);
    }

    @Override
    public Optional<CustomerDTO> findById(Long id) {
        return customerRepository.findById(id)
                                 .map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

    @Override
    public void remove(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        customer.ifPresent(customerRepository::delete);
    }

    @Override
    public Page<MachineDTO> findAllMachines(Long id, Pageable pageable) {
        return machineRepository.findByCustomerId(id, pageable)
                                .map(machine -> modelMapper.map(machine, MachineDTO.class));
    }
}
