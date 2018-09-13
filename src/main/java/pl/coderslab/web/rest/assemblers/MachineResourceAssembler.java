package pl.coderslab.web.rest.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.web.rest.MachineRestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class MachineResourceAssembler implements ResourceAssembler<MachineDTO, Resource<MachineDTO>> {
    @Override
    public Resource<MachineDTO> toResource(MachineDTO machineDTO) {
        return new Resource<>(machineDTO,
                linkTo(methodOn(MachineRestController.class).getOne(machineDTO.getId())).withSelfRel(),
                linkTo(methodOn(MachineRestController.class).getAll()).withRel("machines"),
                linkTo(methodOn(MachineRestController.class).getModelByMachineId(machineDTO.getId())).withRel("models"),
                linkTo(methodOn(MachineRestController.class).getCustomerByMachineId(machineDTO.getId())).withRel("customers"),
                linkTo(methodOn(MachineRestController.class).getValidationsByMachineId(machineDTO.getId())).withRel("validations"));
    }
}
