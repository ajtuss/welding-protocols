package pl.coderslab.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.domain.PowerType;
import pl.coderslab.service.MachineService;
import pl.coderslab.service.dto.*;

import java.math.BigDecimal;
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
@WebMvcTest(value = MachineRestController.class, secure = false)
@EnableSpringDataWebSupport
public class MachineRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MachineService machineService;

    @Autowired
    private ObjectMapper mapper;

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static final CustomerDTO CUSTOMER_1 = new CustomerDTO(1L, "FIRMA",
            "FIRMA SP. z O.O.", "CITY", "00-000", "Street 14",
            "firma@firma.pl", "123-456-32-18", DATE_TIME, DATE_TIME, 1L);
    private static final BrandDTO BRAND_1 = new BrandDTO(1L, "Kemppi", DATE_TIME, DATE_TIME, 1L);
    private static final RangeDTO RANGE_MIG = new RangeDTO(BigDecimal.valueOf(1.), BigDecimal.valueOf(100.), BigDecimal.valueOf(10.), BigDecimal
            .valueOf(100.));
    private static final RangeDTO RANGE_MMA = new RangeDTO(BigDecimal.valueOf(2.), BigDecimal.valueOf(200.), BigDecimal.valueOf(20.), BigDecimal
            .valueOf(200.));
    private static final RangeDTO RANGE_TIG = new RangeDTO(BigDecimal.valueOf(3.), BigDecimal.valueOf(300.), BigDecimal.valueOf(30.), BigDecimal
            .valueOf(300.));
    private static final WelderModelDTO MODEL_1 = new WelderModelDTO(1L, "Mastertig 3000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);

    private static final MachineDTO MACHINE = new MachineDTO(1L, "12345678", "484-123123",
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

    @Test
    public void getShouldFetchAllMAchines() throws Exception {
        PageImpl<MachineDTO> page = new PageImpl<>(Collections.singletonList(MACHINE));
        given(machineService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/machines")
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
               .andExpect(jsonPath("$[0].welderModelId", is(MACHINE.getWelderModelId()
                                                                   .intValue())))
               .andExpect(jsonPath("$[0].welderModelName", is(MACHINE.getWelderModelName())))
               .andExpect(jsonPath("$[0].customerId", is(MACHINE.getCustomerId().intValue())))
               .andExpect(jsonPath("$[0].customerShortName", is(MACHINE.getCustomerShortName())))
               .andExpect(jsonPath("$[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].versionId", is(MACHINE.getVersionId().intValue())))
               .andReturn();
        verify(machineService, times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void getShouldFetchAMachine() throws Exception {

        given(machineService.findById(1L)).willReturn(Optional.of(MACHINE));

        mockMvc.perform(get("/api/machines/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(MACHINE.getId().intValue())))
               .andExpect(jsonPath("$.serialNumber", is(MACHINE.getSerialNumber())))
               .andExpect(jsonPath("$.inwNumber", is(MACHINE.getInwNumber())))
               .andExpect(jsonPath("$.welderModelBrandId", is(MACHINE.getWelderModelBrandId().intValue())))
               .andExpect(jsonPath("$.welderModelBrandName", is(MACHINE.getWelderModelBrandName())))
               .andExpect(jsonPath("$.welderModelId", is(MACHINE.getWelderModelId().intValue())))
               .andExpect(jsonPath("$.welderModelName", is(MACHINE.getWelderModelName())))
               .andExpect(jsonPath("$.customerId", is(MACHINE.getCustomerId().intValue())))
               .andExpect(jsonPath("$.customerShortName", is(MACHINE.getCustomerShortName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MACHINE.getVersionId().intValue())))
               .andReturn();
        verify(machineService, times(1)).findById(1L);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void postShouldAddMachineAndFetchSavedMachine() throws Exception {
        String contentBody = mapper.writeValueAsString(MACHINE_CREATION);

        given(machineService.save(MACHINE_CREATION)).willReturn(MACHINE);
        mockMvc.perform(post("/api/machines")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id", is(MACHINE.getId().intValue())))
               .andExpect(jsonPath("$.serialNumber", is(MACHINE.getSerialNumber())))
               .andExpect(jsonPath("$.inwNumber", is(MACHINE.getInwNumber())))
               .andExpect(jsonPath("$.welderModelBrandId", is(MACHINE.getWelderModelBrandId().intValue())))
               .andExpect(jsonPath("$.welderModelBrandName", is(MACHINE.getWelderModelBrandName())))
               .andExpect(jsonPath("$.welderModelId", is(MACHINE.getWelderModelId().intValue())))
               .andExpect(jsonPath("$.welderModelName", is(MACHINE.getWelderModelName())))
               .andExpect(jsonPath("$.customerId", is(MACHINE.getCustomerId().intValue())))
               .andExpect(jsonPath("$.customerShortName", is(MACHINE.getCustomerShortName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MACHINE.getVersionId().intValue())))
               .andReturn();
        verify(machineService, times(1)).save(MACHINE_CREATION);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void putShouldUpdateMachineAndFetchUpdatedMachine() throws Exception {
        String contentBody = mapper.writeValueAsString(MACHINE_UPDATE);


        given(machineService.save(MACHINE_UPDATE)).willReturn(MACHINE_AFTER_UPDATE);
        mockMvc.perform(put("/api/machines")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
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
               .andReturn();
        verify(machineService, times(1)).save(MACHINE_UPDATE);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void deleteShouldRemoveAndReturnNoContent() throws Exception {

        doNothing().when(machineService).remove(1L);

        mockMvc.perform(delete("/api/machines/1")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andReturn();
        verify(machineService, times(1)).remove(1L);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void getValidationsShouldFetchAllValidations() throws Exception {

        Page<ValidProtocolDTO> page = new PageImpl<>(Collections.singletonList(VALID_PROTOCOL_1));
        given(machineService.findValidationsByMachineId(eq(1L), any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/machines/1/validations")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$[0].id", is(VALID_PROTOCOL_1.getId().intValue())))
               .andExpect(jsonPath("$[0].machineId", is(VALID_PROTOCOL_1.getMachineId()
                                                                        .intValue())))
               .andExpect(jsonPath("$[0].machineSerialNumber", is(VALID_PROTOCOL_1.getMachineSerialNumber())))
               .andExpect(jsonPath("$[0].machineInwNumber", is(VALID_PROTOCOL_1.getMachineInwNumber())))
               .andExpect(jsonPath("$[0].machineWelderModelBrandId", is(VALID_PROTOCOL_1.getMachineWelderModelBrandId()
                                                                                        .intValue())))
               .andExpect(jsonPath("$[0].machineWelderModelBrandName", is(VALID_PROTOCOL_1.getMachineWelderModelBrandName())))
               .andExpect(jsonPath("$[0].machineWelderModelId", is(VALID_PROTOCOL_1.getMachineWelderModelId()
                                                                                   .intValue())))
               .andExpect(jsonPath("$[0].machineWelderModelName", is(VALID_PROTOCOL_1.getMachineWelderModelName())))
               .andExpect(jsonPath("$[0].machineCustomerId", is(VALID_PROTOCOL_1.getMachineCustomerId()
                                                                                .intValue())))
               .andExpect(jsonPath("$[0].machineCustomerShortName", is(VALID_PROTOCOL_1.getMachineCustomerShortName())))
               .andExpect(jsonPath("$[0].type", is(VALID_PROTOCOL_1.getType().name())))
               .andExpect(jsonPath("$[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].versionId", is(VALID_PROTOCOL_1.getVersionId()
                                                                        .intValue())))
               .andReturn();
        verify(machineService, times(1)).findValidationsByMachineId(eq(1L), any(Pageable.class));
        verifyNoMoreInteractions(machineService);
    }
}