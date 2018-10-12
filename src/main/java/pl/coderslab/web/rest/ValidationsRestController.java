package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.dto.*;
import pl.coderslab.domain.DBFile;
import pl.coderslab.web.errors.BadRequestException;
import pl.coderslab.service.ValidProtocolService;
import pl.coderslab.web.rest.assemblers.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/validations", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class ValidationsRestController {


    private final ValidProtocolResourceAssembler assembler;
    private final MachineResourceAssembler machineAssembler;
    private final MeasureResourceAssembler measureAssembler;
    private final CustomerResourceAssembler customerAssembler;
    private final WelderModelResourceAssembler modelAssembler;
    private final ValidProtocolService validProtocolService;

    @Autowired
    public ValidationsRestController(ValidProtocolResourceAssembler assembler, MachineResourceAssembler machineAssembler, MeasureResourceAssembler measureAssembler, CustomerResourceAssembler customerAssembler, WelderModelResourceAssembler modelAssembler, ValidProtocolService validProtocolService) {
        this.assembler = assembler;
        this.machineAssembler = machineAssembler;
        this.measureAssembler = measureAssembler;
        this.customerAssembler = customerAssembler;
        this.modelAssembler = modelAssembler;
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
            throw new BadRequestException(null, null, null);
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
        CustomerDTO customer = validProtocolService.findCustomerByValidProtocolId(id);
        return customerAssembler.toResource(customer);
    }

    @GetMapping("/{id:\\d+}/models")
    public Resource<WelderModelDTO> getModels(@PathVariable Long id) {
        WelderModelDTO welderModelDTO = validProtocolService.findWelderModelByValidProtocolId(id);
        return modelAssembler.toResource(welderModelDTO);
    }

    @GetMapping("/{id:\\d+}/open")
    public ResponseEntity<?> openProtocol(@PathVariable Long id){
        validProtocolService.openProtocol(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id:\\d+}/close")
    public ResponseEntity<?> closeProtocol(@PathVariable Long id){
        validProtocolService.closeProtocol(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id:\\d+}/files")
    public ResponseEntity<?> getFile(@PathVariable Long id){
        DBFile dbFile =  validProtocolService.getFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
}
