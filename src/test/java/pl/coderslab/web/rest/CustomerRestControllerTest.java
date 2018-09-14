package pl.coderslab.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.services.CustomerService;
import pl.coderslab.web.rest.assemblers.CustomerResourceAssembler;
import pl.coderslab.web.rest.assemblers.MachineResourceAssembler;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerRestController.class, secure = false)
@Import({CustomerResourceAssembler.class, MachineResourceAssembler.class})
public class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static final CustomerDTO CUSTOMER_1 = new CustomerDTO(1L, "FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18", DATE_TIME, DATE_TIME, 1L);
    private static final CustomerDTO CUSTOMER_2 = new CustomerDTO(2L, "FIRMA 2",
            "FIRMA 2 SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma2@firma2.pl", "725-18-01-126", DATE_TIME, DATE_TIME, 1L);
    private static final CustomerDTO CUSTOMER_CREATION_1 = new CustomerDTO("FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18");
    private static final CustomerDTO CUSTOMER_UPDATE_1 = new CustomerDTO(1L, "FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18", 1L);
    private static final CustomerDTO CUSTOMER_AFTER_UPDATE_1 = new CustomerDTO(1L, "FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18", DATE_TIME, DATE_TIME, 2L);

    private static final MachineDTO MACHINE_1 = new MachineDTO(1L, "12345678", "484-123123",
            1L, "Mastertig 3000", 1L, "Kemppi",
            CUSTOMER_1.getId(), CUSTOMER_1.getShortName(), DATE_TIME, DATE_TIME, 1L);

    private static final MachineDTO MACHINE_2 = new MachineDTO(2L, "9877877123", "484-9879879",
            2L, "Mastertig 4000", 1L, "Kemppi",
            CUSTOMER_1.getId(), CUSTOMER_1.getShortName(), DATE_TIME, DATE_TIME, 1L);

    @Test
    public void getShouldFetchAllAHalDocument() throws Exception {

        given(customerService.findAll()).willReturn(
                Arrays.asList(CUSTOMER_1, CUSTOMER_2)
        );

        mockMvc.perform(get("/api/customers")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$._embedded.customers[0].id", is(CUSTOMER_1.getId().intValue())))
               .andExpect(jsonPath("$._embedded.customers[0].shortName", is(CUSTOMER_1.getShortName())))
               .andExpect(jsonPath("$._embedded.customers[0].fullName", is(CUSTOMER_1.getFullName())))
               .andExpect(jsonPath("$._embedded.customers[0].city", is(CUSTOMER_1.getCity())))
               .andExpect(jsonPath("$._embedded.customers[0].zip", is(CUSTOMER_1.getZip())))
               .andExpect(jsonPath("$._embedded.customers[0].street", is(CUSTOMER_1.getStreet())))
               .andExpect(jsonPath("$._embedded.customers[0].email", is(CUSTOMER_1.getEmail())))
               .andExpect(jsonPath("$._embedded.customers[0].nip", is(CUSTOMER_1.getNip())))
               .andExpect(jsonPath("$._embedded.customers[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.customers[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.customers[0].versionId", is(CUSTOMER_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.customers[0]._links.self.href", is("http://localhost/api/customers/1")))
               .andExpect(jsonPath("$._embedded.customers[0]._links.machines.href", is("http://localhost/api/customers/1/machines")))
               .andExpect(jsonPath("$._embedded.customers[1].id", is(CUSTOMER_2.getId().intValue())))
               .andExpect(jsonPath("$._embedded.customers[1].shortName", is(CUSTOMER_2.getShortName())))
               .andExpect(jsonPath("$._embedded.customers[1].fullName", is(CUSTOMER_2.getFullName())))
               .andExpect(jsonPath("$._embedded.customers[1].city", is(CUSTOMER_2.getCity())))
               .andExpect(jsonPath("$._embedded.customers[1].zip", is(CUSTOMER_2.getZip())))
               .andExpect(jsonPath("$._embedded.customers[1].street", is(CUSTOMER_2.getStreet())))
               .andExpect(jsonPath("$._embedded.customers[1].email", is(CUSTOMER_2.getEmail())))
               .andExpect(jsonPath("$._embedded.customers[1].nip", is(CUSTOMER_2.getNip())))
               .andExpect(jsonPath("$._embedded.customers[1].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.customers[1].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.customers[1].versionId", is(CUSTOMER_2.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.customers[1]._links.self.href", is("http://localhost/api/customers/2")))
               .andExpect(jsonPath("$._embedded.customers[1]._links.machines.href", is("http://localhost/api/customers/2/machines")))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/customers")))
               .andReturn();
        verify(customerService, times(1)).findAll();
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void getShouldFetchAHalDocument() throws Exception {

        given(customerService.findById(1L)).willReturn(CUSTOMER_1);

        mockMvc.perform(get("/api/customers/1")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(CUSTOMER_1.getId().intValue())))
               .andExpect(jsonPath("$.shortName", is(CUSTOMER_1.getShortName())))
               .andExpect(jsonPath("$.fullName", is(CUSTOMER_1.getFullName())))
               .andExpect(jsonPath("$.city", is(CUSTOMER_1.getCity())))
               .andExpect(jsonPath("$.zip", is(CUSTOMER_1.getZip())))
               .andExpect(jsonPath("$.street", is(CUSTOMER_1.getStreet())))
               .andExpect(jsonPath("$.email", is(CUSTOMER_1.getEmail())))
               .andExpect(jsonPath("$.nip", is(CUSTOMER_1.getNip())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(CUSTOMER_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/customers/1")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/customers/1/machines")))
               .andReturn();
        verify(customerService, times(1)).findById(1L);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void postShouldCreateNewCustomerAndFetchAHalDocument() throws Exception {
        String contentBody = mapper.writeValueAsString(CUSTOMER_CREATION_1);

        given(customerService.save(CUSTOMER_CREATION_1)).willReturn(CUSTOMER_1);
        mockMvc.perform(post("/api/customers")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(redirectedUrl("http://localhost/api/customers/1"))
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(CUSTOMER_1.getId().intValue())))
               .andExpect(jsonPath("$.shortName", is(CUSTOMER_1.getShortName())))
               .andExpect(jsonPath("$.fullName", is(CUSTOMER_1.getFullName())))
               .andExpect(jsonPath("$.city", is(CUSTOMER_1.getCity())))
               .andExpect(jsonPath("$.zip", is(CUSTOMER_1.getZip())))
               .andExpect(jsonPath("$.street", is(CUSTOMER_1.getStreet())))
               .andExpect(jsonPath("$.email", is(CUSTOMER_1.getEmail())))
               .andExpect(jsonPath("$.nip", is(CUSTOMER_1.getNip())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(CUSTOMER_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/customers/1")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/customers/1/machines")))
               .andReturn();
        verify(customerService, times(1)).save(CUSTOMER_CREATION_1);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void putShouldUpdateCustomerAndFetchHalDocument() throws Exception {
        String contentBody = mapper.writeValueAsString(CUSTOMER_UPDATE_1);


        given(customerService.update(CUSTOMER_UPDATE_1)).willReturn(CUSTOMER_AFTER_UPDATE_1);
        mockMvc.perform(put("/api/customers/1")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(CUSTOMER_AFTER_UPDATE_1.getId().intValue())))
               .andExpect(jsonPath("$.shortName", is(CUSTOMER_AFTER_UPDATE_1.getShortName())))
               .andExpect(jsonPath("$.fullName", is(CUSTOMER_AFTER_UPDATE_1.getFullName())))
               .andExpect(jsonPath("$.city", is(CUSTOMER_AFTER_UPDATE_1.getCity())))
               .andExpect(jsonPath("$.zip", is(CUSTOMER_AFTER_UPDATE_1.getZip())))
               .andExpect(jsonPath("$.street", is(CUSTOMER_AFTER_UPDATE_1.getStreet())))
               .andExpect(jsonPath("$.email", is(CUSTOMER_AFTER_UPDATE_1.getEmail())))
               .andExpect(jsonPath("$.nip", is(CUSTOMER_AFTER_UPDATE_1.getNip())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(CUSTOMER_AFTER_UPDATE_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/customers/1")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/customers/1/machines")))
               .andReturn();
        verify(customerService, times(1)).update(CUSTOMER_UPDATE_1);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void putWithInvalidIdShouldReturnHttpStatus400() throws Exception {
        String contentBody = mapper.writeValueAsString(CUSTOMER_UPDATE_1);


        given(customerService.update(CUSTOMER_UPDATE_1)).willReturn(CUSTOMER_AFTER_UPDATE_1);
        mockMvc.perform(put("/api/customers/2")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andReturn();
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void deleteShouldRemoveAndReturnNoContent() throws Exception {

        doNothing().when(customerService).remove(1L);

        mockMvc.perform(delete("/api/customers/1")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isNoContent())
               .andReturn();
        verify(customerService, times(1)).remove(1L);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void getMachinesShouldFetchHalDocument() throws Exception {
        given(customerService.findAllMachines(1L)).willReturn(Arrays.asList(MACHINE_1, MACHINE_2));

        mockMvc.perform(get("/api/customers/1/machines")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$._embedded.machines[0].id", is(MACHINE_1.getId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[0].serialNumber", is(MACHINE_1.getSerialNumber())))
               .andExpect(jsonPath("$._embedded.machines[0].inwNumber", is(MACHINE_1.getInwNumber())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelBrandId", is(MACHINE_1.getWelderModelBrandId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelBrandName", is(MACHINE_1.getWelderModelBrandName())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelId", is(MACHINE_1.getWelderModelId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelName", is(MACHINE_1.getWelderModelName())))
               .andExpect(jsonPath("$._embedded.machines[0].customerId", is(MACHINE_1.getCustomerId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[0].customerShortName", is(MACHINE_1.getCustomerShortName())))
               .andExpect(jsonPath("$._embedded.machines[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.machines[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.machines[0].versionId", is(MACHINE_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[0]._links.self.href", is("http://localhost/api/machines/1")))
               .andExpect(jsonPath("$._embedded.machines[0]._links.machines.href", is("http://localhost/api/machines")))
               .andExpect(jsonPath("$._embedded.machines[0]._links.models.href", is("http://localhost/api/machines/1/models")))
               .andExpect(jsonPath("$._embedded.machines[0]._links.customers.href", is("http://localhost/api/machines/1/customers")))
               .andExpect(jsonPath("$._embedded.machines[0]._links.validations.href", is("http://localhost/api/machines/1/validations")))
               .andExpect(jsonPath("$._embedded.machines[1].id", is(MACHINE_2.getId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[1].serialNumber", is(MACHINE_2.getSerialNumber())))
               .andExpect(jsonPath("$._embedded.machines[1].inwNumber", is(MACHINE_2.getInwNumber())))
               .andExpect(jsonPath("$._embedded.machines[1].welderModelBrandId", is(MACHINE_2.getWelderModelBrandId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[1].welderModelBrandName", is(MACHINE_2.getWelderModelBrandName())))
               .andExpect(jsonPath("$._embedded.machines[1].welderModelId", is(MACHINE_2.getWelderModelId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[1].welderModelName", is(MACHINE_2.getWelderModelName())))
               .andExpect(jsonPath("$._embedded.machines[1].customerId", is(MACHINE_2.getCustomerId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[1].customerShortName", is(MACHINE_2.getCustomerShortName())))
               .andExpect(jsonPath("$._embedded.machines[1].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.machines[1].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.machines[1].versionId", is(MACHINE_2.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[1]._links.self.href", is("http://localhost/api/machines/2")))
               .andExpect(jsonPath("$._embedded.machines[1]._links.machines.href", is("http://localhost/api/machines")))
               .andExpect(jsonPath("$._embedded.machines[1]._links.models.href", is("http://localhost/api/machines/2/models")))
               .andExpect(jsonPath("$._embedded.machines[1]._links.customers.href", is("http://localhost/api/machines/2/customers")))
               .andExpect(jsonPath("$._embedded.machines[1]._links.validations.href", is("http://localhost/api/machines/2/validations")))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/customers/1/machines")))
               .andReturn();
        verify(customerService, times(1)).findAllMachines(1L);
        verifyNoMoreInteractions(customerService);
    }
}