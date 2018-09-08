package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.CustomerDto;
import pl.coderslab.domain.services.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    @Autowired
    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers(){
        return customerService.findAll();
    }

    @GetMapping("{id:\\d+}")
    public CustomerDto getCustomerById(@PathVariable Long id){
        return customerService.findById(id);
    }

    @DeleteMapping("{id:\\d+}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.remove(id);
    }
}
