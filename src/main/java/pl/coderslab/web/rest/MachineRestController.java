package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandDto;
import pl.coderslab.domain.dto.CustomerDto;
import pl.coderslab.domain.dto.MachineDto;
import pl.coderslab.domain.dto.WelderModelDto;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.entities.WelderModel;
import pl.coderslab.domain.services.MachineService;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
public class MachineRestController {

    private final MachineService machineService;

    @Autowired
    public MachineRestController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public List<MachineDto> getAllMachines() {
        return machineService.findAll();
    }

    @GetMapping("{id:\\d+}")
    public MachineDto getMachine(@PathVariable Long id) {
        return machineService.findById(id);
    }

    @DeleteMapping("{id:\\d+}")
    public void deleteMachine(@PathVariable Long id) {
        machineService.remove(id);
    }

    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomersWithMachines() {
        return machineService.findAllCustomers();
    }

    @GetMapping("/weldmodels")
    public List<WelderModelDto> getAllModels(@RequestParam Long customerId,
                                             @RequestParam Long brandId) {

            return machineService.findAllMachines(customerId, brandId);
    }

    @GetMapping("/brands")
    public List<BrandDto> getAllBrands(@RequestParam(required = false) Long customerId) {
        if (customerId != null) {
            return machineService.findAllBrands(customerId);
        } else {
            return machineService.findAllBrands();
        }
    }
}
