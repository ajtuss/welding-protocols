package pl.coderslab.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.service.CustomerService;
import pl.coderslab.service.dto.CustomerDTO;
import pl.coderslab.service.dto.MachineDTO;
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
 * Rest Controller for managing Customers
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CustomerRestController {

    private static final String ENTITY_NAME = "customer";

    private final CustomerService customerService;

    @Autowired
    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * POST /customers : Create a new Customer
     *
     * @param customerDTO the CustomerDTO to create
     * @return the ResponseEntity with status 201 (created) and with body the new CustomerDTO,
     * or with status 400 (BadRequest) if the customer has already an ID or is not valid
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody @Valid CustomerDTO customerDTO) throws URISyntaxException {
        if (customerDTO.getId() != null) {
            throw new BadRequestException("Id must be null", ENTITY_NAME, ErrorConstants.ERR_ID_EXIST);
        }

        CustomerDTO save = customerService.save(customerDTO);
        return ResponseEntity.created(new URI(String.format("/api/customers/%d", save.getId())))
                             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, save.getId().toString()))
                             .body(save);
    }

    /**
     * PUT /customers : Update existing Brand
     *
     * @param customerDTO the BrandDTO to update
     * @return the ResponseEntity with status 200 (ok) and with body the updated CustomerDTO,
     * or with status 400 (BadRequest) if the brandDTO is not valid,
     * or with status 500 (Internal Server Error) if the brandDTO couldn't be updated
     */
    @PutMapping("/customers")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody @Valid CustomerDTO customerDTO) throws URISyntaxException {
        if (customerDTO.getId() == null) {
            throw new BadRequestException("Id cant be null", ENTITY_NAME, ErrorConstants.ERR_ID_NULL);
        }
        CustomerDTO update = customerService.save(customerDTO);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, update.getId().toString()))
                             .body(update);
    }

    /**
     * GET /customers : Get all the Customers
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (ok) and the list of customers in body
     */
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAll(Pageable pageable) {
        Page<CustomerDTO> page = customerService.findAll(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers");
        return ResponseEntity.ok()
                             .headers(httpHeaders)
                             .body(page.getContent());
    }

    /**
     * GET /customers/:id : get the "id" customer
     *
     * @param id the id of BrandDTO to retrieve
     * @return the ResponseEntity with status 200 (ok) an with brandDTO in body
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> getOne(@PathVariable Long id) {
        Optional<CustomerDTO> customerDTO = customerService.findById(id);
        return customerDTO.map(ResponseEntity::ok)
                          .orElseThrow(() -> new NotFoundException(String.format("/customers/%d", id), ENTITY_NAME));
    }

    /**
     * DELETE /customers/:id : delete customer with id
     *
     * @param id the id of the CustomerDTO to delete
     * @return the ResponseEntity with the status 200 (ok)
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.remove(id);
        return ResponseEntity.ok()
                             .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                             .build();
    }

    /**
     * GET /models:id/machines : get all machines with welderModel id
     *
     * @param id       the id of WelderModelDTO to get all machines
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (ok) and the list of Machines in body
     */
    @GetMapping("/customers/{id}/machines")
    public ResponseEntity<List<MachineDTO>> getMachineByCustomerId(@PathVariable Long id, Pageable pageable) {
        Page<MachineDTO> page = customerService.findAllMachines(id, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/customers/%d/machines", id));
        return ResponseEntity.ok()
                             .headers(httpHeaders)
                             .body(page.getContent());
    }
}
