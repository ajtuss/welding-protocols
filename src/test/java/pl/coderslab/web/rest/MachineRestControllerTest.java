package pl.coderslab.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.service.dto.*;
import pl.coderslab.domain.PowerType;
import pl.coderslab.service.MachineService;
import pl.coderslab.web.rest.assemblers.CustomerResourceAssembler;
import pl.coderslab.web.rest.assemblers.ValidProtocolResourceAssembler;
import pl.coderslab.web.rest.assemblers.MachineResourceAssembler;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MachineRestController.class, secure = false)
@Import({MachineResourceAssembler.class, WelderModelResourceAssembler.class, CustomerResourceAssembler.class, ValidProtocolResourceAssembler.class})
public class MachineRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MachineService machineService;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static final CustomerDTO CUSTOMER_1 = new CustomerDTO(1L, "FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18", DATE_TIME, DATE_TIME, 1L);
    private static final BrandDTO BRAND_1 = new BrandDTO(1L, "Kemppi", DATE_TIME, DATE_TIME, 1L);
    private static final RangeDTO RANGE_MIG = new RangeDTO(BigDecimal.valueOf(1.),BigDecimal.valueOf( 100.), BigDecimal.valueOf(10.), BigDecimal.valueOf(100.));
    private static final RangeDTO RANGE_MMA = new RangeDTO(BigDecimal.valueOf(2.), BigDecimal.valueOf(200.), BigDecimal.valueOf(20.), BigDecimal.valueOf(200.));
    private static final RangeDTO RANGE_TIG = new RangeDTO(BigDecimal.valueOf(3.), BigDecimal.valueOf(300.), BigDecimal.valueOf(30.), BigDecimal.valueOf(300.));
    private static final WelderModelDTO MODEL_1 = new WelderModelDTO(1L, "Mastertig 3000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);

    private static final MachineDTO MACHINE_1 = new MachineDTO(1L, "12345678", "484-123123",
            MODEL_1.getId(), MODEL_1.getName(), MODEL_1.getBrandId(), MODEL_1.getBrandName(),
            CUSTOMER_1.getId(), CUSTOMER_1.getShortName(), DATE_TIME, DATE_TIME, 1L);
    private static final MachineDTO MACHINE_2 = new MachineDTO(2L, "9877877123", "484-9879879",
            MODEL_1.getId(), MODEL_1.getName(), MODEL_1.getBrandId(), MODEL_1.getBrandName(),
            CUSTOMER_1.getId(), CUSTOMER_1.getShortName(), DATE_TIME, DATE_TIME, 1L);
    private static final MachineDTO MACHINE_UPDATE = new MachineDTO(1L, "12345678", "484-123123",
            MODEL_1.getId(), CUSTOMER_1.getId(), 1L);
    private static final MachineDTO MACHINE_AFTER_UPDATE = new MachineDTO(1L, "12345678", "484-123123",
            MODEL_1.getId(), MODEL_1.getName(), MODEL_1.getBrandId(), MODEL_1.getBrandName(),
            CUSTOMER_1.getId(), CUSTOMER_1.getShortName(), DATE_TIME, DATE_TIME, 2L);
    private static final MachineDTO MACHINE_CREATION = new MachineDTO("12345678", "484-123123",
            MODEL_1.getId(), CUSTOMER_1.getId());


    private static final ValidProtocolDTO VALID_PROTOCOL_1 = ValidProtocolDTO.builder()
                                                                             .id(1L)
                                                                             .machineCustomerId(MACHINE_1.getCustomerId())
                                                                             .machineCustomerShortName(MACHINE_1.getCustomerShortName())
                                                                             .machineSerialNumber(MACHINE_1.getSerialNumber())
                                                                             .machineInwNumber(MACHINE_1.getInwNumber())
                                                                             .machineWelderModelBrandId(MACHINE_1.getWelderModelBrandId())
                                                                             .machineWelderModelBrandName(MACHINE_1.getWelderModelBrandName())
                                                                             .machineWelderModelId(MACHINE_1.getWelderModelId())
                                                                             .machineWelderModelName(MACHINE_1.getWelderModelName())
                                                                             .machineId(MACHINE_1.getId())
                                                                             .type(PowerType.MIG)
                                                                             .creationDate(DATE_TIME)
                                                                             .modificationDate(DATE_TIME)
                                                                             .versionId(1L)
                                                                             .build();

    private static final ValidProtocolDTO VALID_PROTOCOL_2 = ValidProtocolDTO.builder()
                                                                             .id(2L)
                                                                             .machineCustomerId(MACHINE_1.getCustomerId())
                                                                             .machineCustomerShortName(MACHINE_1.getCustomerShortName())
                                                                             .machineSerialNumber(MACHINE_1.getSerialNumber())
                                                                             .machineInwNumber(MACHINE_1.getInwNumber())
                                                                             .machineWelderModelBrandId(MACHINE_1.getWelderModelBrandId())
                                                                             .machineWelderModelBrandName(MACHINE_1.getWelderModelBrandName())
                                                                             .machineWelderModelId(MACHINE_1.getWelderModelId())
                                                                             .machineWelderModelName(MACHINE_1.getWelderModelName())
                                                                             .machineId(MACHINE_1.getId())
                                                                             .type(PowerType.MMA)
                                                                             .creationDate(DATE_TIME)
                                                                             .modificationDate(DATE_TIME).versionId(1L)
                                                                             .versionId(1L)
                                                                             .build();

    @Test
    public void getShouldFetchAllAHalDocument() throws Exception {
        given(machineService.findAll(any(Pageable.class))).willReturn(new PageImpl<>(Arrays.asList(MACHINE_1, MACHINE_2)));

        mockMvc.perform(get("/api/machines")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$._embedded.machines[0].id", is(MACHINE_1.getId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[0].serialNumber", is(MACHINE_1.getSerialNumber())))
               .andExpect(jsonPath("$._embedded.machines[0].inwNumber", is(MACHINE_1.getInwNumber())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelBrandId", is(MACHINE_1.getWelderModelBrandId()
                                                                                             .intValue())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelBrandName", is(MACHINE_1.getWelderModelBrandName())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelId", is(MACHINE_1.getWelderModelId()
                                                                                        .intValue())))
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
               .andExpect(jsonPath("$._embedded.machines[1].welderModelBrandId", is(MACHINE_2.getWelderModelBrandId()
                                                                                             .intValue())))
               .andExpect(jsonPath("$._embedded.machines[1].welderModelBrandName", is(MACHINE_2.getWelderModelBrandName())))
               .andExpect(jsonPath("$._embedded.machines[1].welderModelId", is(MACHINE_2.getWelderModelId()
                                                                                        .intValue())))
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
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/machines")))
               .andReturn();
        verify(machineService, times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void getShouldFetchAHalDocument() throws Exception {

        given(machineService.findById(1L)).willReturn(Optional.of(MACHINE_1));

        mockMvc.perform(get("/api/machines/1")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(MACHINE_1.getId().intValue())))
               .andExpect(jsonPath("$.serialNumber", is(MACHINE_1.getSerialNumber())))
               .andExpect(jsonPath("$.inwNumber", is(MACHINE_1.getInwNumber())))
               .andExpect(jsonPath("$.welderModelBrandId", is(MACHINE_1.getWelderModelBrandId().intValue())))
               .andExpect(jsonPath("$.welderModelBrandName", is(MACHINE_1.getWelderModelBrandName())))
               .andExpect(jsonPath("$.welderModelId", is(MACHINE_1.getWelderModelId().intValue())))
               .andExpect(jsonPath("$.welderModelName", is(MACHINE_1.getWelderModelName())))
               .andExpect(jsonPath("$.customerId", is(MACHINE_1.getCustomerId().intValue())))
               .andExpect(jsonPath("$.customerShortName", is(MACHINE_1.getCustomerShortName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MACHINE_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/machines/1")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/machines")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/machines/1/models")))
               .andExpect(jsonPath("$._links.customers.href", is("http://localhost/api/machines/1/customers")))
               .andExpect(jsonPath("$._links.validations.href", is("http://localhost/api/machines/1/validations")))
               .andReturn();
        verify(machineService, times(1)).findById(1L);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void postShouldAddMachineAndFetchHalDocument() throws Exception {
        String contentBody = mapper.writeValueAsString(MACHINE_CREATION);

        given(machineService.save(MACHINE_CREATION)).willReturn(MACHINE_1);
        mockMvc.perform(post("/api/machines")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id", is(MACHINE_1.getId().intValue())))
               .andExpect(jsonPath("$.serialNumber", is(MACHINE_1.getSerialNumber())))
               .andExpect(jsonPath("$.inwNumber", is(MACHINE_1.getInwNumber())))
               .andExpect(jsonPath("$.welderModelBrandId", is(MACHINE_1.getWelderModelBrandId().intValue())))
               .andExpect(jsonPath("$.welderModelBrandName", is(MACHINE_1.getWelderModelBrandName())))
               .andExpect(jsonPath("$.welderModelId", is(MACHINE_1.getWelderModelId().intValue())))
               .andExpect(jsonPath("$.welderModelName", is(MACHINE_1.getWelderModelName())))
               .andExpect(jsonPath("$.customerId", is(MACHINE_1.getCustomerId().intValue())))
               .andExpect(jsonPath("$.customerShortName", is(MACHINE_1.getCustomerShortName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MACHINE_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/machines/1")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/machines")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/machines/1/models")))
               .andExpect(jsonPath("$._links.customers.href", is("http://localhost/api/machines/1/customers")))
               .andExpect(jsonPath("$._links.validations.href", is("http://localhost/api/machines/1/validations")))
               .andReturn();
        verify(machineService, times(1)).save(MACHINE_CREATION);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void putShouldUpdateMachineAndFetchHalDocument() throws Exception {
        String contentBody = mapper.writeValueAsString(MACHINE_UPDATE);


        given(machineService.save(MACHINE_UPDATE)).willReturn(MACHINE_AFTER_UPDATE);
        mockMvc.perform(put("/api/machines/1")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(MACHINE_AFTER_UPDATE.getId().intValue())))
               .andExpect(jsonPath("$.serialNumber", is(MACHINE_AFTER_UPDATE.getSerialNumber())))
               .andExpect(jsonPath("$.inwNumber", is(MACHINE_AFTER_UPDATE.getInwNumber())))
               .andExpect(jsonPath("$.welderModelBrandId", is(MACHINE_AFTER_UPDATE.getWelderModelBrandId().intValue())))
               .andExpect(jsonPath("$.welderModelBrandName", is(MACHINE_AFTER_UPDATE.getWelderModelBrandName())))
               .andExpect(jsonPath("$.welderModelId", is(MACHINE_AFTER_UPDATE.getWelderModelId().intValue())))
               .andExpect(jsonPath("$.welderModelName", is(MACHINE_AFTER_UPDATE.getWelderModelName())))
               .andExpect(jsonPath("$.customerId", is(MACHINE_AFTER_UPDATE.getCustomerId().intValue())))
               .andExpect(jsonPath("$.customerShortName", is(MACHINE_AFTER_UPDATE.getCustomerShortName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MACHINE_AFTER_UPDATE.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/machines/1")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/machines")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/machines/1/models")))
               .andExpect(jsonPath("$._links.customers.href", is("http://localhost/api/machines/1/customers")))
               .andExpect(jsonPath("$._links.validations.href", is("http://localhost/api/machines/1/validations")))
               .andReturn();
        verify(machineService, times(1)).save(MACHINE_UPDATE);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void putWithInvalidIdShouldReturnHttpStatus400() throws Exception {
        String contentBody = mapper.writeValueAsString(MACHINE_UPDATE);


        given(machineService.save(MACHINE_UPDATE)).willReturn(MACHINE_AFTER_UPDATE);
        mockMvc.perform(put("/api/machines/2")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andReturn();
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void deleteShouldRemoveAndReturnNoContent() throws Exception {

        doNothing().when(machineService).remove(1L);

        mockMvc.perform(delete("/api/machines/1")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isNoContent())
               .andReturn();
        verify(machineService, times(1)).remove(1L);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void getValidationsShouldFetchHalDocument() throws Exception {

        given(machineService.findValidationsByMachineId(1L, any(Pageable.class))).willReturn(new PageImpl<>(Arrays.asList(VALID_PROTOCOL_1, VALID_PROTOCOL_2)));

        mockMvc.perform(get("/api/machines/1/validations")
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
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/machines/1/validations")))
               .andReturn();
        verify(machineService, times(1)).findValidationsByMachineId(1L, any(Pageable.class));
        verifyNoMoreInteractions(machineService);
    }
}