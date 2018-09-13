package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.services.CustomerService;
import pl.coderslab.web.rest.assemblers.CustomerResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
    public Resources<Resource<CustomerDTO>> getAll(){

        List<Resource<CustomerDTO>> resources = customerService.findAll()
                                                             .stream()
                                                             .map(assembler::toResource)
                                                             .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CustomerRestController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id:\\d+}")
    public Resource<CustomerDTO> getOne(@PathVariable Long id){
        CustomerDTO customerDTO = customerService.findById(id);
        return assembler.toResource(customerDTO);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        customerService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id:\\d+}/machines")
    public Resource<MachineDTO> getMachineByCustomerId(@PathVariable Long id) {
        return null; //todo
    }
}
