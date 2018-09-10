package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.services.ValidProtocolService;

import java.util.List;

@RestController
@RequestMapping("/api/validations")
public class ValidationsRestController {

    @Autowired
    private ValidProtocolService validProtocolService;

    @GetMapping
    public List<ValidProtocolDTO> getAllValidProtocols() {
        return validProtocolService.findAll();
    }

    @GetMapping("/{id:\\d+}")
    public ValidProtocolDTO getValidProtocol(@PathVariable Long id){
        return validProtocolService.findById(id);
    }

    @GetMapping("/{id:\\d+}/measures")
    public List<MeasureDTO> getAllMeasures(@PathVariable Long id){
        return validProtocolService.findAllMeasures(id);
    }


    @DeleteMapping("{id:\\d+}")
    public void deleteModel(@PathVariable Long id) {
        validProtocolService.remove(id);
    }
}
