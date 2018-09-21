package pl.coderslab.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.exceptions.InvalidRequestException;
import pl.coderslab.domain.services.WelderModelService;
import pl.coderslab.rest.assemblers.BrandResourceAssembler;
import pl.coderslab.rest.assemblers.WelderModelResourceAssembler;
import pl.coderslab.rest.assemblers.MachineResourceAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/models", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class WelderModelRestController {

    private final WelderModelService modelService;
    private final WelderModelResourceAssembler assembler;
    private final BrandResourceAssembler brandAssembler;
    private final MachineResourceAssembler machineAssembler;

    @Autowired
    public WelderModelRestController(WelderModelService modelService, WelderModelResourceAssembler assembler, BrandResourceAssembler brandAssembler, MachineResourceAssembler machineAssembler) {
        this.modelService = modelService;
        this.assembler = assembler;
        this.brandAssembler = brandAssembler;
        this.machineAssembler = machineAssembler;
    }

    @GetMapping
    public Resources<Resource<WelderModelDTO>> getAll() {
        List<Resource<WelderModelDTO>> models = modelService.findAll()
                                                            .stream()
                                                            .map(assembler::toResource)
                                                            .collect(Collectors.toList());
        return new Resources<>(models, linkTo(methodOn(WelderModelRestController.class).getAll()).withSelfRel());
    }

    @GetMapping("{id:\\d+}")
    public Resource<WelderModelDTO> getOne(@PathVariable Long id) {
        WelderModelDTO modelDTO = modelService.findById(id);
        return assembler.toResource(modelDTO);
    }

    @DeleteMapping("{id:\\d+}")
    public ResponseEntity<?> deleteModel(@PathVariable Long id) {
        modelService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Resource<WelderModelDTO>> addModel(@RequestBody @Valid WelderModelDTO modelDTO) {
        WelderModelDTO savedModel = modelService.save(modelDTO);
        Resource<WelderModelDTO> resource = assembler.toResource(savedModel);
        return ResponseEntity.created(linkTo(methodOn(WelderModelRestController.class).getOne(savedModel.getId())).toUri())
                             .body(resource);
    }

    @PutMapping("{id:\\d+}")
    public Resource<WelderModelDTO> editModel(@PathVariable Long id, @RequestBody @Valid WelderModelDTO modelUpdateDTO) {
        if (!id.equals(modelUpdateDTO.getId())) {
            throw new InvalidRequestException();
        }
        WelderModelDTO modelDTO = modelService.update(modelUpdateDTO);
        return assembler.toResource(modelDTO);
    }

    @GetMapping("{id:\\d+}/brands")
    public Resource<BrandDTO> getBrandByModelId(@PathVariable Long id) {
        BrandDTO brandDTO = modelService.findBrandByModelId(id);
        return brandAssembler.toResource(brandDTO);
    }

    @GetMapping("{id:\\d+}/machines")
    public Resources<Resource<MachineDTO>> getMachinesByModelId(@PathVariable Long id) {
        List<Resource<MachineDTO>> resources = modelService.findAllMachinesByModelId(id)
                                                         .stream()
                                                         .map(machineAssembler::toResource)
                                                         .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(WelderModelRestController.class).getMachinesByModelId(id)).withSelfRel());
    }


//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleException(MethodArgumentNotValidException exception) {
//
//        String errorMsg = exception.getBindingResult().getFieldErrors().stream()
//                                   .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                                   .findFirst()
//                                   .orElse(exception.getMessage());
//
//        return ErrorResponse.builder().message(errorMsg).build();
//    }
}
