package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.WelderModelService;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.web.errors.BadRequestException;
import pl.coderslab.web.errors.ErrorConstants;
import pl.coderslab.web.errors.NotFoundException;
import pl.coderslab.web.rest.util.HeaderUtil;
import pl.coderslab.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Rest Controller for Managing WelderModels
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WelderModelRestController {

    private static final String ENTITY_NAME = "model";

    private final WelderModelService modelService;

    @Autowired
    public WelderModelRestController(WelderModelService modelService) {
        this.modelService = modelService;
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
        return ResponseEntity.created(new URI(String.format("/api/models/%d", result.getId())))
                             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
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

    /**
     * GET /models/:id : Get the WelderModelsDTO with id
     *
     * @param id the id of WelderModelDTO to retrieve
     * @return the ResponseEntity with status 200 (ok) and body with the WelderModelDTO,
     * or status 400 (Not Found)
     */
    @GetMapping("/models/{id}")
    public ResponseEntity<WelderModelDTO> getModel(@PathVariable Long id) {
        Optional<WelderModelDTO> welderModelDTO = modelService.findById(id);
        return welderModelDTO.map(response -> ResponseEntity.ok().body(response))
                             .orElseThrow(() -> new NotFoundException(String.format("/models/%d", id), ENTITY_NAME));
    }

    /**
     * DELETE /models/:id : delete welderModel with id
     *
     * @param id the id of WelderModelDTO to delete
     * @return the ResponseEntity with status 200 (ok)
     */
    @DeleteMapping("/models/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        modelService.remove(id);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }

    /**
     * GET /models:id/machines : get all machines with welderModel id
     *
     * @param id the id of WelderModelDTO to get all machines
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (ok) and the list of Machines in body
     */
    @GetMapping("/models/{id}/machines")
    public ResponseEntity<List<MachineDTO>> getMachinesByModelId(@PathVariable Long id, Pageable pageable) {
        Page<MachineDTO> page = modelService.findAllMachinesByModelId(id, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/models/%d/machines", id));
        return ResponseEntity.ok()
                             .headers(httpHeaders)
                             .body(page.getContent());
    }

}
