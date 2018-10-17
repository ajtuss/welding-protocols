package pl.coderslab.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.service.dto.CustomerDTO;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.CustomerService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerRestController.class, secure = false)
@EnableSpringDataWebSupport
public class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper mapper;

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static final CustomerDTO CUSTOMER = new CustomerDTO(1L, "FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18", DATE_TIME, DATE_TIME, 1L);

    private static final CustomerDTO CUSTOMER_CREATION = new CustomerDTO("FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18");
    private static final CustomerDTO CUSTOMER_UPDATE = new CustomerDTO(1L, "FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18", 1L);
    private static final CustomerDTO CUSTOMER_AFTER_UPDATE = new CustomerDTO(1L, "FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18", DATE_TIME, DATE_TIME, 2L);

    private static final MachineDTO MACHINE = new MachineDTO(1L, "12345678", "484-123123",
            1L, "Mastertig 3000", 1L, "Kemppi",
            CUSTOMER.getId(), CUSTOMER.getShortName(), DATE_TIME, DATE_TIME, 1L);


    @Test
    public void getShouldFetchAllCustomers() throws Exception {
        PageImpl<CustomerDTO> page = new PageImpl<>(Collections.singletonList(CUSTOMER));
        given(customerService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/customers")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$[0].id", is(CUSTOMER.getId().intValue())))
               .andExpect(jsonPath("$[0].shortName", is(CUSTOMER.getShortName())))
               .andExpect(jsonPath("$[0].fullName", is(CUSTOMER.getFullName())))
               .andExpect(jsonPath("$[0].city", is(CUSTOMER.getCity())))
               .andExpect(jsonPath("$[0].zip", is(CUSTOMER.getZip())))
               .andExpect(jsonPath("$[0].street", is(CUSTOMER.getStreet())))
               .andExpect(jsonPath("$[0].email", is(CUSTOMER.getEmail())))
               .andExpect(jsonPath("$[0].nip", is(CUSTOMER.getNip())))
               .andExpect(jsonPath("$[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].versionId", is(CUSTOMER.getVersionId().intValue())))
               .andReturn();
        verify(customerService, times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void getShouldFetchACustomer() throws Exception {

        given(customerService.findById(1L)).willReturn(Optional.of(CUSTOMER));

        mockMvc.perform(get("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(CUSTOMER.getId().intValue())))
               .andExpect(jsonPath("$.shortName", is(CUSTOMER.getShortName())))
               .andExpect(jsonPath("$.fullName", is(CUSTOMER.getFullName())))
               .andExpect(jsonPath("$.city", is(CUSTOMER.getCity())))
               .andExpect(jsonPath("$.zip", is(CUSTOMER.getZip())))
               .andExpect(jsonPath("$.street", is(CUSTOMER.getStreet())))
               .andExpect(jsonPath("$.email", is(CUSTOMER.getEmail())))
               .andExpect(jsonPath("$.nip", is(CUSTOMER.getNip())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(CUSTOMER.getVersionId().intValue())))
               .andReturn();
        verify(customerService, times(1)).findById(1L);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void postShouldCreateNewCustomerAndSavedCustomer() throws Exception {
        String contentBody = mapper.writeValueAsString(CUSTOMER_CREATION);

        given(customerService.save(CUSTOMER_CREATION)).willReturn(CUSTOMER);
        mockMvc.perform(post("/api/customers")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(redirectedUrl("/api/customers/1"))
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(CUSTOMER.getId().intValue())))
               .andExpect(jsonPath("$.shortName", is(CUSTOMER.getShortName())))
               .andExpect(jsonPath("$.fullName", is(CUSTOMER.getFullName())))
               .andExpect(jsonPath("$.city", is(CUSTOMER.getCity())))
               .andExpect(jsonPath("$.zip", is(CUSTOMER.getZip())))
               .andExpect(jsonPath("$.street", is(CUSTOMER.getStreet())))
               .andExpect(jsonPath("$.email", is(CUSTOMER.getEmail())))
               .andExpect(jsonPath("$.nip", is(CUSTOMER.getNip())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(CUSTOMER.getVersionId().intValue())))
               .andReturn();
        verify(customerService, times(1)).save(CUSTOMER_CREATION);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void putShouldUpdateCustomerAndFetchASavedCustomer() throws Exception {
        String contentBody = mapper.writeValueAsString(CUSTOMER_UPDATE);

        given(customerService.save(CUSTOMER_UPDATE)).willReturn(CUSTOMER_AFTER_UPDATE);

        mockMvc.perform(put("/api/customers")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(CUSTOMER_AFTER_UPDATE.getId().intValue())))
               .andExpect(jsonPath("$.shortName", is(CUSTOMER_AFTER_UPDATE.getShortName())))
               .andExpect(jsonPath("$.fullName", is(CUSTOMER_AFTER_UPDATE.getFullName())))
               .andExpect(jsonPath("$.city", is(CUSTOMER_AFTER_UPDATE.getCity())))
               .andExpect(jsonPath("$.zip", is(CUSTOMER_AFTER_UPDATE.getZip())))
               .andExpect(jsonPath("$.street", is(CUSTOMER_AFTER_UPDATE.getStreet())))
               .andExpect(jsonPath("$.email", is(CUSTOMER_AFTER_UPDATE.getEmail())))
               .andExpect(jsonPath("$.nip", is(CUSTOMER_AFTER_UPDATE.getNip())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(CUSTOMER_AFTER_UPDATE.getVersionId().intValue())))
               .andReturn();
        verify(customerService, times(1)).save(CUSTOMER_UPDATE);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void deleteShouldRemoveAndReturnOk() throws Exception {

        doNothing().when(customerService).remove(1L);

        mockMvc.perform(delete("/api/customers/1")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andReturn();
        verify(customerService, times(1)).remove(1L);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void getMachinesShouldFetchHalDocument() throws Exception {
        PageImpl<MachineDTO> page = new PageImpl<>(Collections.singletonList(MACHINE));
        given(customerService.findAllMachines(eq(1L), any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/customers/1/machines")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$[0].id", is(MACHINE.getId().intValue())))
               .andExpect(jsonPath("$[0].serialNumber", is(MACHINE.getSerialNumber())))
               .andExpect(jsonPath("$[0].inwNumber", is(MACHINE.getInwNumber())))
               .andExpect(jsonPath("$[0].welderModelBrandId", is(MACHINE.getWelderModelBrandId()
                                                                        .intValue())))
               .andExpect(jsonPath("$[0].welderModelBrandName", is(MACHINE.getWelderModelBrandName())))
               .andExpect(jsonPath("$[0].welderModelId", is(MACHINE.getWelderModelId().intValue())))
               .andExpect(jsonPath("$[0].welderModelName", is(MACHINE.getWelderModelName())))
               .andExpect(jsonPath("$[0].customerId", is(MACHINE.getCustomerId().intValue())))
               .andExpect(jsonPath("$[0].customerShortName", is(MACHINE.getCustomerShortName())))
               .andExpect(jsonPath("$[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].versionId", is(MACHINE.getVersionId().intValue())))
               .andReturn();
        verify(customerService, times(1)).findAllMachines(eq(1L), any(Pageable.class));
        verifyNoMoreInteractions(customerService);
    }
}