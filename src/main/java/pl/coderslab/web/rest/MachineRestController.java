package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.*;
import pl.coderslab.domain.services.MachineService;
import pl.coderslab.web.rest.assemblers.MachineResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/machines", consumes = MediaTypes.HAL_JSON_UTF8_VALUE, produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class MachineRestController {

    private final MachineService machineService;
    private final MachineResourceAssembler assembler;

    @Autowired
    public MachineRestController(MachineService machineService, MachineResourceAssembler assembler) {
        this.machineService = machineService;
        this.assembler = assembler;
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

    @DeleteMapping("{id:\\d+}")
    public ResponseEntity<?> deleteMachine(@PathVariable Long id) {
        machineService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomersWithMachines() {
        return machineService.findAllCustomers();
    }

    @GetMapping("/weldmodels")
    public List<WelderModelDTO> getAllModels(@RequestParam Long customerId,
                                             @RequestParam Long brandId) {

            return machineService.findAllMachines(customerId, brandId);
    }

    @GetMapping("/brands")
    public List<BrandDTO> getAllBrands(@RequestParam(required = false) Long customerId) {
        if (customerId != null) {
            return machineService.findAllBrands(customerId);
        } else {
            return machineService.findAllBrands();
        }
    }

    @GetMapping("/{id:\\d+}/models")
    public Resource<WelderModelDTO> getModelByMachineId(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id:\\d+}/customers")
    public Resource<CustomerDTO> getCustomerByMachineId(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id:\\d+}/validations")
    public Resources<Resource<ValidationDTO>> getValidationsByMachineId(@PathVariable Long id) {
        return null;
    }
}
