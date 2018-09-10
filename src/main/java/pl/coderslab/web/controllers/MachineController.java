package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.MachineDTO;
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
        model.addAttribute("machine", new MachineDTO());
        return "forms/addMachine";
    }

    @PostMapping("/add")
    public String addMachine(@ModelAttribute MachineDTO machineDTO){
        machineService.save(machineDTO);
        return "redirect:/machines";
    }

    @GetMapping("{id:\\d+}")
    public String showEditMachineForm(@PathVariable Long id, Model model){
        MachineDTO machineDTO = machineService.findById(id);
        model.addAttribute("machine", machineDTO);
        return "forms/editMachine";
    }

    @PostMapping("{id:\\d+}")
    public String editMachine(@PathVariable Long id, @ModelAttribute MachineDTO machineDTO){
        machineService.update(id, machineDTO);
        return "redirect:/machines";
    }
}
