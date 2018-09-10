package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.services.ValidProtocolService;
import pl.coderslab.domain.services.WelderModelService;

@Controller
@RequestMapping("/validations")
public class ValidationController {

    private final ValidProtocolService validProtocolService;
    private final WelderModelService modelService;

    @Autowired
    public ValidationController(ValidProtocolService validProtocolService, WelderModelService modelService) {
        this.validProtocolService = validProtocolService;
        this.modelService = modelService;
    }

    @GetMapping
    public String showValidationsForm() {
        return "validations";
    }

    @GetMapping("/add")
    public String showAddValidationForm(@RequestParam Long machineId, Model model) {
        ValidProtocolDTO protocolDto = validProtocolService.getNewValidProtocol(machineId);
        model.addAttribute("validation", protocolDto);
        return "forms/addValidation";
    }

    @PostMapping("/add")
    public String addValidation(@ModelAttribute ValidProtocolDTO protocolDto, @RequestParam Long machineId) {
        protocolDto.setMachineId(machineId);
        Long protocolId = validProtocolService.save(protocolDto);
        return "redirect:/validations/" + protocolId;
    }

    @GetMapping("/{id:\\d+}")
    public String showEditValidationForm(@PathVariable Long id, Model model) {
        ValidProtocolDTO protocolDto = validProtocolService.findById(id);
        WelderModelDTO modelDto = modelService.findById(protocolDto.getMachineWelderModelId());

        model.addAttribute("validation", protocolDto);
        model.addAttribute("model", modelDto);
        return "forms/editValidation";
    }
}
