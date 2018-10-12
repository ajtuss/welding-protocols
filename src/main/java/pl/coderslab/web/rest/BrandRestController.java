package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.BrandService;
import pl.coderslab.service.dto.BrandDTO;
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
 * Rest Controller for Managing Brands
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BrandRestController {

    private static final String ENTITY_NAME = "brand";

    private final BrandService brandService;

    @Autowired
    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * POST /brands : Create a new Brand
     *
     * @param brandDTO the BrandDTO to create
     * @return the ResponseEntity with status 201 (created) and with body the new BrandDTO,
     * or with status 400 (BadRequest) if the brand has already an ID or is not valid
     */
    @PostMapping("/brands")
    public ResponseEntity<BrandDTO> addBrand(@RequestBody @Valid BrandDTO brandDTO) throws URISyntaxException {
        if (brandDTO.getId() != null) {
            throw new BadRequestException("Id must be null", ENTITY_NAME, ErrorConstants.ERR_ID_EXIST);
        }
        BrandDTO result = brandService.save(brandDTO);
        return ResponseEntity.created(new URI("/api/brands/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * PUT /brands : Update existing Brand
     *
     * @param brandDTO the BrandDTO to update
     * @return the ResponseEntity with status 200 (ok) and with body the updated BrandDTO,
     * or with status 400 (Bad Request) if the brandDTO is not valid,
     * or with status 500 (Internal Server Error) if the brandDTO couldn't be updated
     */
    @PutMapping(value = "/brands")
    public ResponseEntity<BrandDTO> editBrand(@RequestBody @Valid BrandDTO brandDTO) {
        if (brandDTO.getId() == null) {
            throw new BadRequestException("Id cant be null", ENTITY_NAME, ErrorConstants.ERR_ID_NULL);
        }
        BrandDTO result = brandService.save(brandDTO);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brandDTO.getId().toString()))
                             .body(result);
    }

    /**
     * GET /brands : Get all the Brands
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (ok) and the list of brands in body
     */
    @GetMapping("/brands")
    public ResponseEntity<List<BrandDTO>> getAll(Pageable pageable) {

        Page<BrandDTO> page = brandService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brands");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /brands/:id : get the "id" brand
     *
     * @param id the id of BrandDTO to retrieve
     * @return the ResponseEntity with status 200 (ok) and with body the brandDTO,
     * or with status 404 (Not found)
     */
    @GetMapping(value = "/brands/{id}")
    public ResponseEntity<BrandDTO> getOne(@PathVariable Long id) {
        Optional<BrandDTO> brandDTO = brandService.findById(id);
        return brandDTO.map(response -> ResponseEntity.ok().body(response))
                       .orElseThrow(() -> new NotFoundException(String.format("/brands/%d", id), ENTITY_NAME));
    }

    /**
     * DELETE /brands/:id : delete brand with id
     *
     * @param id the id of the BrandDTO to delete
     * @return the ResponseEntity with the status 200 (ok)
     */
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.remove(id);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }

    /**
     * GET /brands/:id/models : get all the models with brand id
     *
     * @param id       the id of the BrandDTO
     * @param pageable the pagination information
     * @return the ResponseEntity with the status 200 (ok) and the list of WelderModelDTO in the body,
     * or with status 404 (Not found) if the BrandDTO not exist
     */
    @GetMapping(value = "/brands/{id}/models")
    public ResponseEntity<List<WelderModelDTO>> getModelsByBrandId(@PathVariable Long id, Pageable pageable) {
        Page<WelderModelDTO> page = brandService.findWelderModelsByBrandId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brands/" + id + "/models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
