package pl.coderslab.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.services.MeasureService;
import pl.coderslab.rest.assemblers.MeasureResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/measures", consumes = {MediaTypes.HAL_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class MeasureRestController {

    private final MeasureService measureService;
    private final MeasureResourceAssembler assembler;

    @Autowired
    public MeasureRestController(MeasureService measureService, MeasureResourceAssembler assembler) {
        this.measureService = measureService;
        this.assembler = assembler;
    }


    @GetMapping
    public Resources<Resource<MeasureDTO>> getAll() {
        List<Resource<MeasureDTO>> resources = measureService.findAll()
                                                           .stream()
                                                           .map(assembler::toResource)
                                                           .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(MeasureRestController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id:\\d+}")
    public Resource<MeasureDTO> getOne(@PathVariable Long id) {
        MeasureDTO measureDTO = measureService.findById(id);
        return assembler.toResource(measureDTO);
    }

    @GetMapping("/{id:\\d+}/validations")
    public Resource<ValidProtocolDTO> getValidProtocol(@PathVariable Long id) {
        return null;
    }
}
