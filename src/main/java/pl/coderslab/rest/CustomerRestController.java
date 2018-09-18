package pl.coderslab.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.exceptions.InvalidRequestException;
import pl.coderslab.domain.services.CustomerService;
import pl.coderslab.rest.assemblers.CustomerResourceAssembler;
import pl.coderslab.rest.assemblers.MachineResourceAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/customers", consumes = {MediaTypes.HAL_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class CustomerRestController {

    private final CustomerService customerService;
    private final CustomerResourceAssembler assembler;
    private final MachineResourceAssembler machineAssembler;

    @Autowired
    public CustomerRestController(CustomerService customerService, CustomerResourceAssembler assembler, MachineResourceAssembler machineAssembler) {
        this.customerService = customerService;
        this.assembler = assembler;
        this.machineAssembler = machineAssembler;
    }

    @GetMapping
    public Resources<Resource<CustomerDTO>> getAll() {

        List<Resource<CustomerDTO>> resources = customerService.findAll()
                                                               .stream()
                                                               .map(assembler::toResource)
                                                               .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CustomerRestController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id:\\d+}")
    public Resource<CustomerDTO> getOne(@PathVariable Long id) {
        CustomerDTO customerDTO = customerService.findById(id);
        return assembler.toResource(customerDTO);
    }

    @PostMapping
    public ResponseEntity<Resource<CustomerDTO>> addCustomer(@RequestBody @Valid CustomerDTO customerCreationDTO) {
        CustomerDTO customerDTO = customerService.save(customerCreationDTO);
        Resource<CustomerDTO> resource = assembler.toResource(customerDTO);
        return ResponseEntity.created(linkTo(methodOn(CustomerRestController.class).getOne(customerDTO.getId())).toUri())
                             .body(resource);
    }

    @PutMapping("/{id:\\d+}")
    public Resource<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerDTO customerUpdateDTO) {
        if(!id.equals(customerUpdateDTO.getId())){
            throw new InvalidRequestException();
        }
        CustomerDTO customerDTO = customerService.update(customerUpdateDTO);
        return assembler.toResource(customerDTO);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        customerService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id:\\d+}/machines")
    public Resources<Resource<MachineDTO>> getMachineByCustomerId(@PathVariable Long id) {
        List<Resource<MachineDTO>> resources = customerService.findAllMachines(id)
                                                            .stream()
                                                            .map(machineAssembler::toResource)
                                                            .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CustomerRestController.class).getMachineByCustomerId(id)).withSelfRel());
    }
}
