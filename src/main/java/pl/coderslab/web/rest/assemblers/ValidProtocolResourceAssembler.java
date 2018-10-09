package pl.coderslab.web.rest.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import pl.coderslab.service.dto.ValidProtocolDTO;
import pl.coderslab.web.rest.ValidationsRestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class ValidProtocolResourceAssembler implements ResourceAssembler<ValidProtocolDTO, Resource<ValidProtocolDTO>> {
    @Override
    public Resource<ValidProtocolDTO> toResource(ValidProtocolDTO protocolDTO) {
        return new Resource<>(protocolDTO,
                ControllerLinkBuilder.linkTo(methodOn(ValidationsRestController.class).getOne(protocolDTO.getId())).withSelfRel(),
                linkTo(methodOn(ValidationsRestController.class).getAll()).withRel("validations"),
                linkTo(methodOn(ValidationsRestController.class).getMeasures(protocolDTO.getId())).withRel("measures"),
                linkTo(methodOn(ValidationsRestController.class).getMachines(protocolDTO.getId())).withRel("machines"),
                linkTo(methodOn(ValidationsRestController.class).getCustomers(protocolDTO.getId())).withRel("customers"),
                linkTo(methodOn(ValidationsRestController.class).getModels(protocolDTO.getId())).withRel("models"),
                linkTo(methodOn(ValidationsRestController.class).openProtocol(protocolDTO.getId())).withRel("open"),
                linkTo(methodOn(ValidationsRestController.class).closeProtocol(protocolDTO.getId())).withRel("close"),
                linkTo(methodOn(ValidationsRestController.class).getFile(protocolDTO.getId())).withRel("files"));
    }
}
