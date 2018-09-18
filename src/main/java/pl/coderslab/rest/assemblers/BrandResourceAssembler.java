package pl.coderslab.rest.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.rest.BrandRestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BrandResourceAssembler implements ResourceAssembler<BrandDTO, Resource<BrandDTO>> {
    @Override
    public Resource<BrandDTO> toResource(BrandDTO brandDTO) {
        return new Resource<>(brandDTO,
                linkTo(methodOn(BrandRestController.class).getOne(brandDTO.getId())).withSelfRel(),
                linkTo(methodOn(BrandRestController.class).getAll()).withRel("brands"),
                linkTo(methodOn(BrandRestController.class).getModelsByBrandId(brandDTO.getId())).withRel("models"));
    }
}
