package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.ValidProtocolDto;
import pl.coderslab.domain.dto.WelderModelDto;
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
    public String showAddValidationForm(@RequestParam(required = false) Long machineId, Model model) {
        ValidProtocolDto protocolDto = validProtocolService.getNewValidProtocol(machineId);
        model.addAttribute("validation", protocolDto);
        return "forms/addValidation";
    }

    @PostMapping("/add")
    public String addValidation(@ModelAttribute ValidProtocolDto protocolDto) {
        Long protocolId = validProtocolService.save(protocolDto);
        return "redirect:/validations/" + protocolId;
    }

    @GetMapping("/{id:\\d+}")
    public String showEditValidationForm(@PathVariable Long id, Model model) {
        ValidProtocolDto protocolDto = validProtocolService.findById(id);
        WelderModelDto modelDto = modelService.findById(protocolDto.getMachineWelderModelId());

        model.addAttribute("validation", protocolDto);
        model.addAttribute("model", modelDto);
        return "forms/editValidation";
    }
}
