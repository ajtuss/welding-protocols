package pl.coderslab.rest.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.rest.WelderModelRestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class WelderModelResourceAssembler implements ResourceAssembler<WelderModelDTO, Resource<WelderModelDTO>> {
    @Override
    public Resource<WelderModelDTO> toResource(WelderModelDTO modelDTO) {
        return new Resource<>(modelDTO,
                linkTo(methodOn(WelderModelRestController.class).getOne(modelDTO.getId())).withSelfRel(),
                linkTo(methodOn(WelderModelRestController.class).getAll()).withRel("models"),
                linkTo(methodOn(WelderModelRestController.class).getBrandByModelId(modelDTO.getId())).withRel("brands"),
                linkTo(methodOn(WelderModelRestController.class).getMachinesByModelId(modelDTO.getId())).withRel("machines"));
    }
}
