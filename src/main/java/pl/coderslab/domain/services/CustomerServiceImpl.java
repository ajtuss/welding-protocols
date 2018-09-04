package pl.coderslab.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.domain.dto.CustomerDto;
import pl.coderslab.domain.entities.Customer;
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
    public List<CustomerDto> findAll() {
        List<Customer> customers = customerRepository.findAll();
        Type resultType = new TypeToken<List<CustomerDto>>() {}.getType();
        return modelMapper.map(customers, resultType);
    }

    @Override
    public void save(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customerRepository.save(customer);
    }

    @Override
    public CustomerDto findById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return modelMapper.map(customer,CustomerDto.class);
    }

    @Override
    public void update(Long id, CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customer.setId(id);
        customerRepository.save(customer);
    }
}
