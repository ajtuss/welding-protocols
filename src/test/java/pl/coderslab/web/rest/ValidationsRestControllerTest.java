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
import pl.coderslab.domain.dto.*;
import pl.coderslab.domain.entities.PowerType;
import pl.coderslab.domain.services.ValidProtocolService;
import pl.coderslab.web.rest.assemblers.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ValidationsRestController.class, secure = false)
@Import({ValidProtocolResourceAssembler.class, MeasureResourceAssembler.class, MachineResourceAssembler.class})
public class ValidationsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValidProtocolService protocolService;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static final CustomerDTO CUSTOMER = new CustomerDTO(1L, "FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18", DATE_TIME, DATE_TIME, 1L);
    private static final BrandDTO BRAND = new BrandDTO(1L, "Kemppi", DATE_TIME, DATE_TIME, 1L);
    private static final RangeDTO RANGE_MIG = new RangeDTO(1., 100., 10., 100.);
    private static final RangeDTO RANGE_MMA = new RangeDTO(2., 200., 20., 200.);
    private static final RangeDTO RANGE_TIG = new RangeDTO(3., 300., 30., 300.);
    private static final WelderModelDTO MODEL_1 = new WelderModelDTO(1L, "Mastertig 3000", BRAND.getId(),
            BRAND.getName(), true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);

    private static final MachineDTO MACHINE = new MachineDTO(1L, "12345678", "484-123123",
            MODEL_1.getId(), MODEL_1.getName(), MODEL_1.getBrandId(), MODEL_1.getBrandName(),
            CUSTOMER.getId(), CUSTOMER.getShortName(), DATE_TIME, DATE_TIME, 1L);

    private static final ValidProtocolDTO VALID_PROTOCOL_1 = ValidProtocolDTO.builder()
                                                                             .id(1L)
                                                                             .machineCustomerId(MACHINE.getCustomerId())
                                                                             .machineCustomerShortName(MACHINE.getCustomerShortName())
                                                                             .machineSerialNumber(MACHINE.getSerialNumber())
                                                                             .machineInwNumber(MACHINE.getInwNumber())
                                                                             .machineWelderModelBrandId(MACHINE.getWelderModelBrandId())
                                                                             .machineWelderModelBrandName(MACHINE.getWelderModelBrandName())
                                                                             .machineWelderModelId(MACHINE.getWelderModelId())
                                                                             .machineWelderModelName(MACHINE.getWelderModelName())
                                                                             .machineId(MACHINE.getId())
                                                                             .type(PowerType.MIG)
                                                                             .creationDate(DATE_TIME)
                                                                             .modificationDate(DATE_TIME)
                                                                             .versionId(1L)
                                                                             .build();

    private static final ValidProtocolDTO VALID_PROTOCOL_2 = ValidProtocolDTO.builder()
                                                                             .id(2L)
                                                                             .machineCustomerId(MACHINE.getCustomerId())
                                                                             .machineCustomerShortName(MACHINE.getCustomerShortName())
                                                                             .machineSerialNumber(MACHINE.getSerialNumber())
                                                                             .machineInwNumber(MACHINE.getInwNumber())
                                                                             .machineWelderModelBrandId(MACHINE.getWelderModelBrandId())
                                                                             .machineWelderModelBrandName(MACHINE.getWelderModelBrandName())
                                                                             .machineWelderModelId(MACHINE.getWelderModelId())
                                                                             .machineWelderModelName(MACHINE.getWelderModelName())
                                                                             .machineId(MACHINE.getId())
                                                                             .type(PowerType.MMA)
                                                                             .creationDate(DATE_TIME)
                                                                             .modificationDate(DATE_TIME).versionId(1L)
                                                                             .versionId(1L)
                                                                             .build();

    private static final ValidProtocolDTO VALID_PROTOCOL_ADD = ValidProtocolDTO.builder()
                                                                               .machineId(MACHINE.getId())
                                                                               .type(PowerType.MMA)
                                                                               .build();

    @Test
    public void getShouldFetchAHalDocument() throws Exception {

        given(protocolService.findById(1L)).willReturn(VALID_PROTOCOL_1);

        mockMvc.perform(get("/api/validations/1")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(VALID_PROTOCOL_1.getId().intValue())))
               .andExpect(jsonPath("$.machineId", is(VALID_PROTOCOL_1.getMachineId().intValue())))
               .andExpect(jsonPath("$.machineSerialNumber", is(VALID_PROTOCOL_1.getMachineSerialNumber())))
               .andExpect(jsonPath("$.machineInwNumber", is(VALID_PROTOCOL_1.getMachineInwNumber())))
               .andExpect(jsonPath("$.machineWelderModelBrandId", is(VALID_PROTOCOL_1.getMachineWelderModelBrandId()
                                                                                     .intValue())))
               .andExpect(jsonPath("$.machineWelderModelBrandName", is(VALID_PROTOCOL_1.getMachineWelderModelBrandName())))
               .andExpect(jsonPath("$.machineWelderModelId", is(VALID_PROTOCOL_1.getMachineWelderModelId().intValue())))
               .andExpect(jsonPath("$.machineWelderModelName", is(VALID_PROTOCOL_1.getMachineWelderModelName())))
               .andExpect(jsonPath("$.machineCustomerId", is(VALID_PROTOCOL_1.getMachineCustomerId().intValue())))
               .andExpect(jsonPath("$.machineCustomerShortName", is(VALID_PROTOCOL_1.getMachineCustomerShortName())))
               .andExpect(jsonPath("$.type", is(VALID_PROTOCOL_1.getType().name())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(VALID_PROTOCOL_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/validations/1")))
               .andExpect(jsonPath("$._links.validations.href", is("http://localhost/api/validations")))
               .andExpect(jsonPath("$._links.measures.href", is("http://localhost/api/validations/1/measures")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/validations/1/machines")))
               .andExpect(jsonPath("$._links.customers.href", is("http://localhost/api/validations/1/customers")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/validations/1/models")))
               .andReturn();
        verify(protocolService, times(1)).findById(1L);
        verifyNoMoreInteractions(protocolService);
    }

    @Test
    public void getShouldFetchAllHalDocument() throws Exception {

        given(protocolService.findAll()).willReturn(Arrays.asList(VALID_PROTOCOL_1, VALID_PROTOCOL_2));

        mockMvc.perform(get("/api/validations")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$._embedded.validations[0].id", is(VALID_PROTOCOL_1.getId().intValue())))
               .andExpect(jsonPath("$._embedded.validations[0].machineId", is(VALID_PROTOCOL_1.getMachineId()
                                                                                              .intValue())))
               .andExpect(jsonPath("$._embedded.validations[0].machineSerialNumber", is(VALID_PROTOCOL_1.getMachineSerialNumber())))
               .andExpect(jsonPath("$._embedded.validations[0].machineInwNumber", is(VALID_PROTOCOL_1.getMachineInwNumber())))
               .andExpect(jsonPath("$._embedded.validations[0].machineWelderModelBrandId", is(VALID_PROTOCOL_1.getMachineWelderModelBrandId()
                                                                                                              .intValue())))
               .andExpect(jsonPath("$._embedded.validations[0].machineWelderModelBrandName", is(VALID_PROTOCOL_1.getMachineWelderModelBrandName())))
               .andExpect(jsonPath("$._embedded.validations[0].machineWelderModelId", is(VALID_PROTOCOL_1.getMachineWelderModelId()
                                                                                                         .intValue())))
               .andExpect(jsonPath("$._embedded.validations[0].machineWelderModelName", is(VALID_PROTOCOL_1.getMachineWelderModelName())))
               .andExpect(jsonPath("$._embedded.validations[0].machineCustomerId", is(VALID_PROTOCOL_1.getMachineCustomerId()
                                                                                                      .intValue())))
               .andExpect(jsonPath("$._embedded.validations[0].machineCustomerShortName", is(VALID_PROTOCOL_1.getMachineCustomerShortName())))
               .andExpect(jsonPath("$._embedded.validations[0].type", is(VALID_PROTOCOL_1.getType().name())))
               .andExpect(jsonPath("$._embedded.validations[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.validations[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.validations[0].versionId", is(VALID_PROTOCOL_1.getVersionId()
                                                                                              .intValue())))
               .andExpect(jsonPath("$._embedded.validations[0]._links.self.href", is("http://localhost/api/validations/1")))
               .andExpect(jsonPath("$._embedded.validations[0]._links.validations.href", is("http://localhost/api/validations")))
               .andExpect(jsonPath("$._embedded.validations[0]._links.measures.href", is("http://localhost/api/validations/1/measures")))
               .andExpect(jsonPath("$._embedded.validations[0]._links.machines.href", is("http://localhost/api/validations/1/machines")))
               .andExpect(jsonPath("$._embedded.validations[0]._links.customers.href", is("http://localhost/api/validations/1/customers")))
               .andExpect(jsonPath("$._embedded.validations[0]._links.models.href", is("http://localhost/api/validations/1/models")))
               .andExpect(jsonPath("$._embedded.validations[1].id", is(VALID_PROTOCOL_2.getId().intValue())))
               .andExpect(jsonPath("$._embedded.validations[1].machineId", is(VALID_PROTOCOL_2.getMachineId()
                                                                                              .intValue())))
               .andExpect(jsonPath("$._embedded.validations[1].machineSerialNumber", is(VALID_PROTOCOL_2.getMachineSerialNumber())))
               .andExpect(jsonPath("$._embedded.validations[1].machineInwNumber", is(VALID_PROTOCOL_2.getMachineInwNumber())))
               .andExpect(jsonPath("$._embedded.validations[1].machineWelderModelBrandId", is(VALID_PROTOCOL_2.getMachineWelderModelBrandId()
                                                                                                              .intValue())))
               .andExpect(jsonPath("$._embedded.validations[1].machineWelderModelBrandName", is(VALID_PROTOCOL_2.getMachineWelderModelBrandName())))
               .andExpect(jsonPath("$._embedded.validations[1].machineWelderModelId", is(VALID_PROTOCOL_2.getMachineWelderModelId()
                                                                                                         .intValue())))
               .andExpect(jsonPath("$._embedded.validations[1].machineWelderModelName", is(VALID_PROTOCOL_2.getMachineWelderModelName())))
               .andExpect(jsonPath("$._embedded.validations[1].machineCustomerId", is(VALID_PROTOCOL_2.getMachineCustomerId()
                                                                                                      .intValue())))
               .andExpect(jsonPath("$._embedded.validations[1].machineCustomerShortName", is(VALID_PROTOCOL_2.getMachineCustomerShortName())))
               .andExpect(jsonPath("$._embedded.validations[1].type", is(VALID_PROTOCOL_2.getType().name())))
               .andExpect(jsonPath("$._embedded.validations[1].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.validations[1].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.validations[1].versionId", is(VALID_PROTOCOL_2.getVersionId()
                                                                                              .intValue())))
               .andExpect(jsonPath("$._embedded.validations[1]._links.self.href", is("http://localhost/api/validations/2")))
               .andExpect(jsonPath("$._embedded.validations[1]._links.validations.href", is("http://localhost/api/validations")))
               .andExpect(jsonPath("$._embedded.validations[1]._links.measures.href", is("http://localhost/api/validations/2/measures")))
               .andExpect(jsonPath("$._embedded.validations[1]._links.machines.href", is("http://localhost/api/validations/2/machines")))
               .andExpect(jsonPath("$._embedded.validations[1]._links.customers.href", is("http://localhost/api/validations/2/customers")))
               .andExpect(jsonPath("$._embedded.validations[1]._links.models.href", is("http://localhost/api/validations/2/models")))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/validations")))
               .andReturn();
        verify(protocolService, times(1)).findAll();
        verifyNoMoreInteractions(protocolService);
    }

    @Test
    public void postShouldAddMachineAndFetchHalDocument() throws Exception {
        String contentBody = mapper.writeValueAsString(VALID_PROTOCOL_ADD);

        given(protocolService.save(VALID_PROTOCOL_ADD)).willReturn(VALID_PROTOCOL_1);
        mockMvc.perform(post("/api/validations")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id", is(VALID_PROTOCOL_1.getId().intValue())))
               .andExpect(jsonPath("$.machineId", is(VALID_PROTOCOL_1.getMachineId().intValue())))
               .andExpect(jsonPath("$.machineSerialNumber", is(VALID_PROTOCOL_1.getMachineSerialNumber())))
               .andExpect(jsonPath("$.machineInwNumber", is(VALID_PROTOCOL_1.getMachineInwNumber())))
               .andExpect(jsonPath("$.machineWelderModelBrandId", is(VALID_PROTOCOL_1.getMachineWelderModelBrandId()
                                                                                     .intValue())))
               .andExpect(jsonPath("$.machineWelderModelBrandName", is(VALID_PROTOCOL_1.getMachineWelderModelBrandName())))
               .andExpect(jsonPath("$.machineWelderModelId", is(VALID_PROTOCOL_1.getMachineWelderModelId().intValue())))
               .andExpect(jsonPath("$.machineWelderModelName", is(VALID_PROTOCOL_1.getMachineWelderModelName())))
               .andExpect(jsonPath("$.machineCustomerId", is(VALID_PROTOCOL_1.getMachineCustomerId().intValue())))
               .andExpect(jsonPath("$.machineCustomerShortName", is(VALID_PROTOCOL_1.getMachineCustomerShortName())))
               .andExpect(jsonPath("$.type", is(VALID_PROTOCOL_1.getType().name())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(VALID_PROTOCOL_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/validations/1")))
               .andExpect(jsonPath("$._links.validations.href", is("http://localhost/api/validations")))
               .andExpect(jsonPath("$._links.measures.href", is("http://localhost/api/validations/1/measures")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/validations/1/machines")))
               .andExpect(jsonPath("$._links.customers.href", is("http://localhost/api/validations/1/customers")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/validations/1/models")))
               .andReturn();
        verify(protocolService, times(1)).save(VALID_PROTOCOL_ADD);
        verifyNoMoreInteractions(protocolService);
    }

    @Test
    public void deleteShouldRemoveAndReturnNoContent() throws Exception {

        doNothing().when(protocolService).remove(1L);

        mockMvc.perform(delete("/api/validations/1")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isNoContent())
               .andReturn();
        verify(protocolService, times(1)).remove(1L);
        verifyNoMoreInteractions(protocolService);
    }


}