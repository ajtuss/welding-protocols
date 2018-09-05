package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.MachineDto;
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
    public List<MachineDto> getAllMachines(){
        return machineService.findAll();
    }

    @DeleteMapping("{id:\\d+}")
    public void deleteMachine(@PathVariable Long id){
        machineService.remove(id);
    }
}
