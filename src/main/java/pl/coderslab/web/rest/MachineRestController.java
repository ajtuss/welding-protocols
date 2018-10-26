package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.MachineService;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.ValidProtocolDTO;
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
 * Rest Controller for Managing Machines
 */
@RestController
@RequestMapping(value = "/api", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class MachineRestController {

    private static final String ENTITY_NAME = "machine";

    private final MachineService machineService;

    @Autowired
    public MachineRestController(MachineService machineService) {
        this.machineService = machineService;
    }

    /**
     * POST /machines : Create a new Machine
     *
     * @param machineDTO the MachineDTO to create
     * @return the ResponseEntity with status 201 (created) and with body the new MachineDTO,
     * or with status 400 (BadRequest) if the machine has already an ID or is not valid
     */
    @PostMapping("/machines")
    public ResponseEntity<MachineDTO> addMachine(@RequestBody @Valid MachineDTO machineDTO) throws URISyntaxException {
        if (machineDTO.getId() != null) {
            throw new BadRequestException("Id must be null", ENTITY_NAME, ErrorConstants.ERR_ID_EXIST);
        }
        MachineDTO result = machineService.save(machineDTO);
        return ResponseEntity.created(new URI(String.format("/api/machines/%d", result.getId())))
                             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * PUT /machines : Update existing Machine
     *
     * @param machineDTO the MachineDTO to update
     * @return the ResponseEntity with status 200 (ok) and with body the updated machineDTO,
     * or with status 400 (Bad Request) if the machineDTO is not valid,
     * or with status 500 (Internal Server Error) if the machineDTO couldn't be updated
     */
    @PutMapping("/machines")
    public ResponseEntity<MachineDTO> updateMachine(@RequestBody @Valid MachineDTO machineDTO) {
        if (machineDTO.getId() == null) {
            throw new BadRequestException("Id cant be null", ENTITY_NAME, ErrorConstants.ERR_ID_NULL);
        }
        MachineDTO result = machineService.save(machineDTO);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * GET /machines : Get all the Machines
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (ok) and the list of machines in body
     */
    @GetMapping("/machines")
    public ResponseEntity<List<MachineDTO>> getAll(Pageable pageable) {

        Page<MachineDTO> page = machineService.findAll(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, "/api/machines");
        return ResponseEntity.ok()
                             .headers(httpHeaders)
                             .body(page.getContent());
    }

    /**
     * GET /machines/:id : get the "id" machine
     *
     * @param id the id of machineDTO to retrieve
     * @return the ResponseEntity with status 200 (ok) and with body the machineDTO,
     * or with status 404 (Not found)
     */
    @GetMapping("/machines/{id}")
    public ResponseEntity<MachineDTO> getOne(@PathVariable Long id) {
        Optional<MachineDTO> machineDTO = machineService.findById(id);
        return machineDTO.map(ResponseEntity::ok)
                         .orElseThrow(() -> new NotFoundException(String.format("/api/machines/%d", id), ENTITY_NAME));
    }

    /**
     * DELETE /machines/:id : delete machine with id
     *
     * @param id the id of the machineDTO to delete
     * @return the ResponseEntity with the status 200 (ok)
     */
    @DeleteMapping("/machines/{id}")
    public ResponseEntity<Void> deleteMachine(@PathVariable Long id) {
        machineService.remove(id);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }

    /**
     * GET /machines/:id/validations : get all validations with machine id
     *
     * @param id       the id of the MachineDTO
     * @param pageable the pagination information
     * @return the ResponseEntity with the status 200 (ok) and the list of ValidationDTO in the body
     */
    @GetMapping("/machines/{id}/validations")
    public ResponseEntity<List<ValidProtocolDTO>> getValidationsByMachineId(@PathVariable Long id, Pageable pageable) {
        Page<ValidProtocolDTO> page = machineService.findValidationsByMachineId(id, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/machines/%d/validations", id));
        return ResponseEntity.ok()
                             .headers(httpHeaders)
                             .body(page.getContent());
    }
}
