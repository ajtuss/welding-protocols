package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.*;
import pl.coderslab.domain.exceptions.InvalidRequestException;
import pl.coderslab.domain.services.ValidProtocolService;
import pl.coderslab.web.rest.assemblers.MachineResourceAssembler;
import pl.coderslab.web.rest.assemblers.MeasureResourceAssembler;
import pl.coderslab.web.rest.assemblers.ValidProtocolResourceAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/validations", consumes = {MediaTypes.HAL_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class ValidationsRestController {


    private final ValidProtocolResourceAssembler assembler;
    private final MachineResourceAssembler machineAssembler;
    private final MeasureResourceAssembler measureAssembler;
    private final ValidProtocolService validProtocolService;

    @Autowired
    public ValidationsRestController(ValidProtocolResourceAssembler assembler, MachineResourceAssembler machineAssembler, MeasureResourceAssembler measureAssembler, ValidProtocolService validProtocolService) {
        this.assembler = assembler;
        this.machineAssembler = machineAssembler;
        this.measureAssembler = measureAssembler;
        this.validProtocolService = validProtocolService;
    }

    @GetMapping
    public Resources<Resource<ValidProtocolDTO>> getAll() {
        List<Resource<ValidProtocolDTO>> resources = validProtocolService.findAll()
                                                                         .stream()
                                                                         .map(assembler::toResource)
                                                                         .collect(Collectors.toList());
        return new Resources<>(resources, linkTo(methodOn(ValidationsRestController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id:\\d+}")
    public Resource<ValidProtocolDTO> getOne(@PathVariable Long id) {
        ValidProtocolDTO protocolDTO = validProtocolService.findById(id);
        return assembler.toResource(protocolDTO);
    }

    @PostMapping
    public ResponseEntity<?> addProtocol(@RequestBody @Valid ValidProtocolDTO protocolDTO) {
        ValidProtocolDTO save = validProtocolService.save(protocolDTO);
        Resource<ValidProtocolDTO> resource = assembler.toResource(save);
        return ResponseEntity.created(linkTo(methodOn(ValidationsRestController.class).getOne(save.getId())).toUri())
                             .body(resource);
    }

    @PutMapping("/{id:\\d+}")
    public Resource<ValidProtocolDTO> editProtocol(@PathVariable Long id, @RequestBody @Valid ValidProtocolDTO protocolDTO) {
        if(!id.equals(protocolDTO.getId())){
            throw new InvalidRequestException();
        }
        ValidProtocolDTO save = validProtocolService.save(protocolDTO);
        return assembler.toResource(save);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> deleteProtocol(@PathVariable Long id) {
        validProtocolService.remove(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id:\\d+}/measures")
    public Resources<Resource<MeasureDTO>> getMeasures(@PathVariable Long id) {
        List<Resource<MeasureDTO>> resources = validProtocolService.findAllMeasures(id)
                                                                 .stream()
                                                                 .map(measureAssembler::toResource)
                                                                 .collect(Collectors.toList());
        return new Resources<>(resources, linkTo(methodOn(ValidationsRestController.class).getMeasures(id)).withSelfRel());
    }

    @GetMapping("/{id:\\d+}/machines")
    public Resource<MachineDTO> getMachines(@PathVariable Long id) {
        MachineDTO machineDTO = validProtocolService.findMachineByValidProtocolId(id);
        return machineAssembler.toResource(machineDTO);
    }

    @GetMapping("/{id:\\d+}/customers")
    public Resource<CustomerDTO> getCustomers(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id:\\d+}/models")
    public Resource<WelderModelDTO> getModels(@PathVariable Long id) {
        return null;
    }
}
