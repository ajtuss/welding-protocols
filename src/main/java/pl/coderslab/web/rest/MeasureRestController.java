package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.MeasureService;
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

@RestController
@RequestMapping(value = "/api/measures", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MeasureRestController {

    private static final String ENTITY_NAME = "measure";

    private final MeasureService measureService;

    @Autowired
    public MeasureRestController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @PostMapping
    public ResponseEntity<MeasureDTO> addMeasure(@RequestBody @Valid MeasureDTO measureDTO) throws URISyntaxException {
        if (measureDTO.getId() != null) {
            throw new BadRequestException("Id must be null", ENTITY_NAME, ErrorConstants.ERR_ID_EXIST);
        }
        MeasureDTO result = measureService.save(measureDTO);
        return ResponseEntity.created(new URI(String.format("/api/measures/%d", result.getId())))
                             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    @PutMapping
    public ResponseEntity<MeasureDTO> updateMeasure(@RequestBody @Valid MeasureDTO measureDTO) {
        if (measureDTO.getId() == null) {
            throw new BadRequestException("Id cant be null", ENTITY_NAME, ErrorConstants.ERR_ID_NULL);
        }
        MeasureDTO result = measureService.update(measureDTO);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    @GetMapping
    public ResponseEntity<List<MeasureDTO>> getAll(Pageable pageable) {
        Page<MeasureDTO> page = measureService.findAll(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, "/api/measures");
        return ResponseEntity.ok()
                             .headers(httpHeaders)
                             .body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasureDTO> getOne(@PathVariable Long id) {
        Optional<MeasureDTO> result = measureService.findById(id);
        return result.map(ResponseEntity::ok)
                     .orElseThrow(() -> new NotFoundException(String.format("/api/measures/%d", id), ENTITY_NAME));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeasure(@PathVariable Long id) {
        measureService.remove(id);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }

    @GetMapping("/{id}/validations")
    public ResponseEntity<List<ValidProtocolDTO>> getValidProtocol(@PathVariable Long id, Pageable pageable) {
        Page<ValidProtocolDTO> page = measureService.findProtocolByMeasureId(id, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/measures/%d/validations", id));
        return ResponseEntity.ok()
                             .headers(httpHeaders)
                             .body(page.getContent());
    }
}
