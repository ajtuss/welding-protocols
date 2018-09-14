package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.entities.Customer;
import pl.coderslab.domain.exceptions.CustomerNotFoundException;
import pl.coderslab.domain.repositories.CustomerRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
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
        customerRepository.save(customer);
        return null; //todo
    }

    @Override
    public CustomerDTO findById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
        return modelMapper.map(customer,CustomerDTO.class);
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
//        customer.setId(id);
//        customerRepository.save(customer);
        return null; //todo
    }

    @Override
    public void remove(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
        customerRepository.delete(customer);
    }

    @Override
    public List<MachineDTO> findAllMachines(Long id) {
        return null; //todo
    }
}
