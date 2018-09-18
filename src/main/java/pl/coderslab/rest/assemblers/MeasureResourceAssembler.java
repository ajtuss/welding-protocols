package pl.coderslab.rest.assemblers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.coderslab.domain.dto.MeasureDTO;

@Component
public class MeasureResourceAssembler implements ResourceAssembler<MeasureDTO, Resource<MeasureDTO>> {
    @Override
    public Resource<MeasureDTO> toResource(MeasureDTO entity) {
        return null; //todo
    }
}
