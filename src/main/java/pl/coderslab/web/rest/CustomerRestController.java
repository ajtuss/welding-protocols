package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.services.CustomerService;
import pl.coderslab.web.rest.assemblers.CustomerResourceAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customers", consumes = MediaTypes.HAL_JSON_UTF8_VALUE, produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class CustomerRestController {

    private final CustomerService customerService;
    private final CustomerResourceAssembler assembler;

    @Autowired
    public CustomerRestController(CustomerService customerService, CustomerResourceAssembler assembler) {
        this.customerService = customerService;
        this.assembler = assembler;
    }

    @GetMapping
    public List<CustomerDTO> getAll(){
        return customerService.findAll();
    }

    @GetMapping("/{id:\\d+}")
    public Resource<CustomerDTO> getOne(@PathVariable Long id){
        CustomerDTO customerDTO = customerService.findById(id);
        return assembler.toResource(customerDTO);
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable Long id){
        customerService.remove(id);
    }

    @GetMapping("/{id:\\d+}/machines")
    public Resource<MachineDTO> getMachineByCustomerId(@PathVariable Long id) {
        return null; //todo
    }
}
