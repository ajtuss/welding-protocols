package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.DBFile;
import pl.coderslab.service.ValidProtocolService;
import pl.coderslab.service.dto.MeasureDTO;
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
 * Rest Controller for Managing Validations
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ValidationsRestController {

    private static final String ENTITY_NAME = "validation";

    private final ValidProtocolService validProtocolService;

    @Autowired
    public ValidationsRestController(ValidProtocolService validProtocolService) {
        this.validProtocolService = validProtocolService;
    }

    /**
     * POST /validations : Create a new Validation
     *
     * @param validProtocolDTO the ValidProtocolDTO to create
     * @return the ResponseEntity with status 201 (created) and with body the new ValidProtocolDTO,
     * or with status 400 (BadRequest) if the validation has already an ID or is not valid
     */
    @PostMapping("/validations")
    public ResponseEntity<ValidProtocolDTO> addProtocol(@RequestBody @Valid ValidProtocolDTO validProtocolDTO) throws URISyntaxException {
        if (validProtocolDTO.getId() != null) {
            throw new BadRequestException("Id must be null", ENTITY_NAME, ErrorConstants.ERR_ID_EXIST);
        }
        ValidProtocolDTO result = validProtocolService.save(validProtocolDTO);
        return ResponseEntity.created(new URI(String.format("/api/validations/%d", result.getId())))
                             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * PUT /validations : Update existing Validation
     *
     * @param validProtocolDTO the ValidProtocolDTO to update
     * @return the ResponseEntity with status 200 (ok) and with body the updated ValidProtocolDTO,
     * or with status 400 (Bad Request) if the ValidProtocolDTO is not valid,
     * or with status 500 (Internal Server Error) if the ValidProtocolDTO couldn't be updated
     */
    @PutMapping("/validations")
    public ResponseEntity<ValidProtocolDTO> editProtocol(@RequestBody @Valid ValidProtocolDTO validProtocolDTO) {
        if (validProtocolDTO.getId() == null) {
            throw new BadRequestException("Id cant be null", ENTITY_NAME, ErrorConstants.ERR_ID_NULL);
        }
        ValidProtocolDTO result = validProtocolService.save(validProtocolDTO);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * GET /validations : Get all the Validations
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (ok) and the list of validations in body
     */
    @GetMapping("/validations")
    public ResponseEntity<List<ValidProtocolDTO>> getAll(Pageable pageable) {
        Page<ValidProtocolDTO> page = validProtocolService.findAll(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, "/api/validations");
        return ResponseEntity.ok()
                             .headers(httpHeaders)
                             .body(page.getContent());
    }

    /**
     * GET /validations/:id : get the "id" validation
     *
     * @param id the id of ValidProtocolDTO to retrieve
     * @return the ResponseEntity with status 200 (ok) and with body the ValidProtocolDTO,
     * or with status 404 (Not found)
     */
    @GetMapping("/validations/{id}")
    public ResponseEntity<ValidProtocolDTO> getOne(@PathVariable Long id) {
        Optional<ValidProtocolDTO> result = validProtocolService.findById(id);
        return result.map(ResponseEntity::ok)
                     .orElseThrow(() -> new NotFoundException(String.format("/api/validations/%d", id), ENTITY_NAME));
    }

    /**
     * DELETE /validations/:id : delete validation with id
     *
     * @param id the id of the ValidProtocolDTO to delete
     * @return the ResponseEntity with the status 200 (ok)
     */
    @DeleteMapping("/validations/{id}")
    public ResponseEntity<?> deleteValidation(@PathVariable Long id) {
        validProtocolService.remove(id);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }

    /**
     * GET /validations/:id/measures : get all measures with validation id
     *
     * @param id       the id of the ValidProtocolDTO
     * @param pageable the pagination information
     * @return the ResponseEntity with the status 200 (ok) and the list of MeasuresDTO in the body
     */
    @GetMapping("/validations/{id}/measures")
    public ResponseEntity<List<MeasureDTO>> getMeasures(@PathVariable Long id, Pageable pageable) {
        Page<MeasureDTO> page = validProtocolService.findAllMeasures(id, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/validations/%d/measures", id));
        return ResponseEntity.ok()
                             .headers(httpHeaders)
                             .body(page.getContent());
    }


    @GetMapping("/validations/{id}/open")
    public ResponseEntity<Void> openProtocol(@PathVariable Long id) {
        validProtocolService.openProtocol(id);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/validations/{id}/close")
    public ResponseEntity<Void> closeProtocol(@PathVariable Long id) {
        validProtocolService.closeProtocol(id);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/validations/{id}/files")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable Long id) {
        DBFile dbFile = validProtocolService.getFile(id);

        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                             .body(new ByteArrayResource(dbFile.getData()));
    }
}
