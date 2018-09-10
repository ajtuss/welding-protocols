package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
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
    public List<MachineDTO> getAllMachines() {
        return machineService.findAll();
    }

    @GetMapping("{id:\\d+}")
    public MachineDTO getMachine(@PathVariable Long id) {
        return machineService.findById(id);
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
}
