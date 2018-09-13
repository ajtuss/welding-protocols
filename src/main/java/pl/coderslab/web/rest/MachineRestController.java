package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.*;
import pl.coderslab.domain.services.MachineService;
import pl.coderslab.web.rest.assemblers.MachineResourceAssembler;

import java.util.List;

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
    public List<MachineDTO> getAll() {
        return machineService.findAll();
    }

    @GetMapping("{id:\\d+}")
    public Resource<MachineDTO> getOne(@PathVariable Long id) {
        MachineDTO machineDTO = machineService.findById(id);
        return assembler.toResource(machineDTO);
    }

    @DeleteMapping("{id:\\d+}")
    public void deleteMachine(@PathVariable Long id) {
        machineService.remove(id);
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
