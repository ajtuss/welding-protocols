package pl.coderslab.web.rest.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.web.rest.WelderModelRestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class WelderModelResourceAssembler implements ResourceAssembler<WelderModelDTO, Resource<WelderModelDTO>> {
    @Override
    public Resource<WelderModelDTO> toResource(WelderModelDTO modelDTO) {
        return new Resource<>(modelDTO,
                linkTo(methodOn(WelderModelRestController.class).getModel(modelDTO.getId())).withSelfRel(),
                linkTo(methodOn(WelderModelRestController.class).getAll(null)).withRel("models"),
                linkTo(methodOn(WelderModelRestController.class).getMachinesByModelId(modelDTO.getId())).withRel("machines"));
    }
}
