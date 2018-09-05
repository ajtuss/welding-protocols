package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.domain.dto.ValidProtocolDto;
import pl.coderslab.domain.services.ValidProtocolService;

import java.util.List;

@RestController
@RequestMapping("/api/validations")
public class ValidationsRestController {

    @Autowired
    private ValidProtocolService validProtocolService;

    @GetMapping
    public List<ValidProtocolDto> getAllValidProtocols(){
        return validProtocolService.findAll();
    }
}
