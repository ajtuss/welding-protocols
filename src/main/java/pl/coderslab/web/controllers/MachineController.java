package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.domain.dto.MachineDto;
import pl.coderslab.domain.services.MachineService;

@Controller
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;

    @Autowired
    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public String showAllMachines(){
        return "machines";
    }

    @GetMapping("/add")
    public String showAddMachineForm(Model model){
        model.addAttribute("machine", new MachineDto());
        return "forms/addMachine";
    }
}
