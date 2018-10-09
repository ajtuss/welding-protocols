package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.dto.MeasureDTO;
import pl.coderslab.service.dto.ValidProtocolDTO;
import pl.coderslab.web.exceptions.InvalidRequestException;
import pl.coderslab.service.MeasureService;
import pl.coderslab.web.rest.assemblers.MeasureResourceAssembler;
import pl.coderslab.web.rest.assemblers.ValidProtocolResourceAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/measures", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class MeasureRestController {

    private final MeasureService measureService;
    private final MeasureResourceAssembler assembler;
    private final ValidProtocolResourceAssembler protocolAssembler;

    @Autowired
    public MeasureRestController(MeasureService measureService, MeasureResourceAssembler assembler, ValidProtocolResourceAssembler protocolAssembler) {
        this.measureService = measureService;
        this.assembler = assembler;
        this.protocolAssembler = protocolAssembler;
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
        ValidProtocolDTO protocol = measureService.findProtocolByMeasureId(id);
        return protocolAssembler.toResource(protocol);
    }

    @PostMapping
    public ResponseEntity<Resource<MeasureDTO>> addMeasure(@RequestBody @Valid MeasureDTO measureDTO) {
        MeasureDTO save = measureService.save(measureDTO);
        Resource<MeasureDTO> resource = assembler.toResource(save);
        return ResponseEntity.created(linkTo(methodOn(MeasureRestController.class).getOne(save.getId())).toUri())
                             .body(resource);
    }

    @PutMapping("/{id:\\d+}")
    public Resource<MeasureDTO> updateMeasure(@RequestBody @Valid MeasureDTO measureDTO, @PathVariable Long id) {
        if (!id.equals(measureDTO.getId())) {
            throw new InvalidRequestException();
        }
        MeasureDTO update = measureService.update(measureDTO);
        return assembler.toResource(update);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> deleteMeasure(@PathVariable Long id) {
        measureService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
