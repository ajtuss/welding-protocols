package pl.coderslab.rest.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.rest.MeasureRestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class MeasureResourceAssembler implements ResourceAssembler<MeasureDTO, Resource<MeasureDTO>> {
    @Override
    public Resource<MeasureDTO> toResource(MeasureDTO measureDTO) {
        return new Resource<>(measureDTO,
                linkTo(methodOn(MeasureRestController.class).getOne(measureDTO.getId())).withSelfRel(),
                linkTo(methodOn(MeasureRestController.class).getAll()).withRel("measures"),
                linkTo(methodOn(MeasureRestController.class).getValidProtocol(measureDTO.getId())).withRel("validations"));
    }
}
