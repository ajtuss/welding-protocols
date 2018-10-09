package pl.coderslab.web.rest.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.coderslab.service.dto.CustomerDTO;
import pl.coderslab.web.rest.CustomerRestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CustomerResourceAssembler implements ResourceAssembler<CustomerDTO, Resource<CustomerDTO>> {
    @Override
    public Resource<CustomerDTO> toResource(CustomerDTO customerDTO) {
        return new Resource<>(customerDTO,
                linkTo(methodOn(CustomerRestController.class).getOne(customerDTO.getId())).withSelfRel(),
                linkTo(methodOn(CustomerRestController.class).getMachineByCustomerId(customerDTO.getId())).withRel("machines"));
    }
}
