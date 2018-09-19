package pl.coderslab.rest;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;

@RestController
@RequestMapping(value = "/api/measures", consumes = {MediaTypes.HAL_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class MeasureRestController {

    @GetMapping
    public Resources<Resource<MeasureDTO>> getAll() {
        return null;
    }

    @GetMapping("/{id:\\d+}")
    public Resource<MeasureDTO> getOne(@PathVariable Long id){
        return null;
    }

    @GetMapping("/{id:\\d+}/protocols")
    public Resource<ValidProtocolDTO> getValidProtocol(@PathVariable Long id) {
        return null;
    }
}
