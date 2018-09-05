package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.ValidProtocolDto;
import pl.coderslab.domain.services.ValidProtocolService;

@Controller
@RequestMapping("/validations")
public class ValidationController {

    private final ValidProtocolService validProtocolService;

    @Autowired
    public ValidationController(ValidProtocolService validProtocolService) {
        this.validProtocolService = validProtocolService;
    }

    @GetMapping
    public String showValidationsForm(){
        return "validations";
    }

    @GetMapping("/add")
    public String showAddValidationForm(@RequestParam Long machineId, Model model){
        ValidProtocolDto protocolDto = validProtocolService.getNewValidProtocol(machineId);
        model.addAttribute("validation", protocolDto);
        return "forms/addValidation";
    }

    @PostMapping("/add")
    public String addValidation(@ModelAttribute ValidProtocolDto protocolDto){
        validProtocolService.save(protocolDto);
        return "redirect:/validations";
    }
}
