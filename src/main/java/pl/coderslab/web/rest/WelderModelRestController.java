package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.web.errors.BadRequestException;
import pl.coderslab.service.WelderModelService;
import pl.coderslab.web.errors.ErrorConstants;
import pl.coderslab.web.rest.assemblers.BrandResourceAssembler;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;
import pl.coderslab.web.rest.assemblers.MachineResourceAssembler;
import pl.coderslab.web.rest.util.HeaderUtil;
import pl.coderslab.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Rest Controller for Managing WelderModels
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WelderModelRestController {

    private static final String ENTITY_NAME = "model";

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

    /**
     * POST /brands : Create a new WelderModel*
     *
     * @param welderModelDTO the WelderModelDTO to create
     * @return the ResponseEntity with status 201 (created) and with body the new WelderModelDTO,
     * or with status 400 (Bad request) if the brand has already ID or is not valid
     */
    @PostMapping("/models")
    public ResponseEntity<WelderModelDTO> addModel(@RequestBody @Valid WelderModelDTO welderModelDTO) throws URISyntaxException {
        if (welderModelDTO.getId() != null) {
            throw new BadRequestException("Id must be null", ENTITY_NAME, ErrorConstants.ERR_ID_EXIST);
        }
        WelderModelDTO result = modelService.save(welderModelDTO);
        return ResponseEntity.created(new URI(String.format("/models/%d", result.getId())))
                             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                             .build();
    }

    /**
     * PUT /models : Update existing WelderModel
     *
     * @param welderModelDTO the WelderModelDTO to update
     * @return the ResponseEntity with status 200 (ok) and with body the updated BrandDTO,
     * or with status 400 (Bad request) if the brandDTO is not valid,
     * or with status 500 (Internal Server Error) if the branDTO couldn't be updated
     */
    @PutMapping("/models")
    public ResponseEntity<WelderModelDTO> editModel(@RequestBody @Valid WelderModelDTO welderModelDTO) {
        if (welderModelDTO.getId() == null) {
            throw new BadRequestException("Id cant be null", ENTITY_NAME, ErrorConstants.ERR_ID_NULL);
        }
        WelderModelDTO result = modelService.update(welderModelDTO);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * GET /models : Get all the WelderModels
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (ok) and the list of welderModels in body
     */
    @GetMapping("/models")
    public ResponseEntity<List<WelderModelDTO>> getAll(Pageable pageable) {
        Page<WelderModelDTO> page = modelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/models");
        return ResponseEntity.ok()
                             .headers(headers)
                             .body(page.getContent());
    }

    @GetMapping("/models/{id}")
    public Resource<WelderModelDTO> getOne(@PathVariable Long id) {
        WelderModelDTO modelDTO = modelService.findById(id);
        return assembler.toResource(modelDTO);
    }

    @DeleteMapping("/models/{id}")
    public ResponseEntity<?> deleteModel(@PathVariable Long id) {
        modelService.remove(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/models/{id}/brands")
    public Resource<BrandDTO> getBrandByModelId(@PathVariable Long id) {
        BrandDTO brandDTO = modelService.findBrandByModelId(id);
        return brandAssembler.toResource(brandDTO);
    }

    @GetMapping("/models{id}/machines")
    public Resources<Resource<MachineDTO>> getMachinesByModelId(@PathVariable Long id) {
        List<Resource<MachineDTO>> resources = modelService.findAllMachinesByModelId(id)
                                                           .stream()
                                                           .map(machineAssembler::toResource)
                                                           .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(WelderModelRestController.class).getMachinesByModelId(id)).withSelfRel());
    }

}
