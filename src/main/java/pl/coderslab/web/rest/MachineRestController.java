package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.exceptions.InvalidRequestException;
import pl.coderslab.domain.services.MachineService;
import pl.coderslab.web.rest.assemblers.CustomerResourceAssembler;
import pl.coderslab.web.rest.assemblers.MachineResourceAssembler;
import pl.coderslab.web.rest.assemblers.ValidProtocolResourceAssembler;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/machines", consumes = {MediaTypes.HAL_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class MachineRestController {

    private final MachineService machineService;
    private final MachineResourceAssembler assembler;
    private final WelderModelResourceAssembler modelAssembler;
    private final CustomerResourceAssembler customerAssembler;
    private final ValidProtocolResourceAssembler protocolAssembler;

    @Autowired
    public MachineRestController(MachineService machineService, MachineResourceAssembler assembler, WelderModelResourceAssembler modelAssembler, CustomerResourceAssembler customerAssembler, ValidProtocolResourceAssembler protocolAssembler) {
        this.machineService = machineService;
        this.assembler = assembler;
        this.modelAssembler = modelAssembler;
        this.customerAssembler = customerAssembler;
        this.protocolAssembler = protocolAssembler;
    }

    @GetMapping
    public Resources<Resource<MachineDTO>> getAll() {
        List<Resource<MachineDTO>> resources = machineService.findAll()
                                                             .stream()
                                                             .map(assembler::toResource)
                                                             .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(MachineRestController.class).getAll()).withSelfRel());
    }

    @GetMapping("{id:\\d+}")
    public Resource<MachineDTO> getOne(@PathVariable Long id) {
        MachineDTO machineDTO = machineService.findById(id);
        return assembler.toResource(machineDTO);
    }

    @PostMapping
    public ResponseEntity<?> addMachine(@RequestBody @Valid MachineDTO machineCreationDTO) {
        MachineDTO machineDTO = machineService.save(machineCreationDTO);
        Resource<MachineDTO> resource = assembler.toResource(machineDTO);
        return ResponseEntity.created(linkTo(methodOn(MachineRestController.class).getOne(machineDTO.getId())).toUri())
                             .body(resource);
    }

    @PutMapping("/{id:\\d+}")
    public Resource<MachineDTO> updateMachine(@PathVariable Long id, @RequestBody @Valid MachineDTO machineUpdateDTO){
        if(!id.equals(machineUpdateDTO.getId())){
            throw new InvalidRequestException();
        }
        MachineDTO machineDTO = machineService.update(machineUpdateDTO);
        return assembler.toResource(machineDTO);
    }

    @DeleteMapping("{id:\\d+}")
    public ResponseEntity<?> deleteMachine(@PathVariable Long id) {
        machineService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id:\\d+}/models")
    public Resource<WelderModelDTO> getModelByMachineId(@PathVariable Long id) {
        WelderModelDTO modelDTO = machineService.findModelByMachineId(id);
        return modelAssembler.toResource(modelDTO);
    }

    @GetMapping("/{id:\\d+}/customers")
    public Resource<CustomerDTO> getCustomerByMachineId(@PathVariable Long id) {
        CustomerDTO customerDTO = machineService.findCustomerByMachineId(id);
        return customerAssembler.toResource(customerDTO);
    }

    @GetMapping("/{id:\\d+}/validations")
    public Resources<Resource<ValidProtocolDTO>> getValidationsByMachineId(@PathVariable Long id) {
        List<Resource<ValidProtocolDTO>> resources = machineService.findValidationsByMachineId(id)
                                                                 .stream()
                                                                 .map(protocolAssembler::toResource)
                                                                 .collect(Collectors.toList());
        return new Resources<>(resources, linkTo(methodOn(MachineRestController.class).getValidationsByMachineId(id)).withSelfRel());
    }
}
