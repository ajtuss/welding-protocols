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
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.domain.PowerType;
import pl.coderslab.service.MeasureService;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.MeasureDTO;
import pl.coderslab.service.dto.ValidProtocolDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MeasureRestController.class, secure = false)
@EnableSpringDataWebSupport
public class MeasureRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeasureService measureService;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final ValidProtocolDTO PROTOCOL_1 = ValidProtocolDTO.builder()
                                                                       .id(1L)
                                                                       .creationDate(LocalDateTime.parse("2018-09-18T13:27:26"))
                                                                       .modificationDate(LocalDateTime.parse("2018-09-18T13:27:26"))
                                                                       .machineCustomerId(1L)
                                                                       .machineCustomerShortName("Firma")
                                                                       .machineSerialNumber("1234567")
                                                                       .machineWelderModelId(2L)
                                                                       .machineWelderModelName("MasterTig 3000")
                                                                       .machineWelderModelBrandId(2L)
                                                                       .machineWelderModelBrandName("EWM")
                                                                       .versionId(1L)
                                                                       .type(PowerType.MMA)
                                                                       .machineId(1L)
                                                                       .build();


    private static final MeasureDTO MEASURE_1 = MeasureDTO.builder()
                                                          .id(1L)
                                                          .creationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                          .modificationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                          .versionId(1L)
                                                          .iAdjust(BigDecimal.valueOf(5))
                                                          .uAdjust(BigDecimal.valueOf(10.2))
                                                          .validProtocolId(1L)
                                                          .build();
    private static final MeasureDTO MEASURE_2 = MeasureDTO.builder()
                                                          .id(2L)
                                                          .creationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                          .modificationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                          .versionId(1L)
                                                          .iAdjust(BigDecimal.valueOf(79))
                                                          .uAdjust(BigDecimal.valueOf(13.2))
                                                          .validProtocolId(1L)
                                                          .build();
    private static final MeasureDTO MEASURE_3 = MeasureDTO.builder()
                                                          .id(3L)
                                                          .creationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                          .modificationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                          .versionId(1L)
                                                          .iAdjust(BigDecimal.valueOf(153))
                                                          .uAdjust(BigDecimal.valueOf(16.1))
                                                          .validProtocolId(1L)
                                                          .build();
    private static final MeasureDTO MEASURE_4 = MeasureDTO.builder()
                                                          .id(4L)
                                                          .creationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                          .modificationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                          .versionId(1L)
                                                          .iAdjust(BigDecimal.valueOf(226))
                                                          .uAdjust(BigDecimal.valueOf(19.1))
                                                          .validProtocolId(1L)
                                                          .build();
    private static final MeasureDTO MEASURE_5 = MeasureDTO.builder()
                                                          .id(5L)
                                                          .creationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                          .modificationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                          .versionId(1L)
                                                          .iAdjust(BigDecimal.valueOf(300))
                                                          .uAdjust(BigDecimal.valueOf(22.0))
                                                          .validProtocolId(1L)
                                                          .build();

    private static final MachineDTO MACHINE_1 = MachineDTO.builder()
                                                          .id(1L)
                                                          .creationDate(LocalDateTime.parse("2018-09-17T13:52:50"))
                                                          .modificationDate(LocalDateTime.parse("2018-09-17T13:52:50"))
                                                          .versionId(1L)
                                                          .welderModelId(2L)
                                                          .customerId(1L)
                                                          .serialNumber("1234567")
                                                          .welderModelName("MasterTig 3000")
                                                          .welderModelId(2L)
                                                          .welderModelBrandId(2L)
                                                          .welderModelBrandName("EWM")
                                                          .customerId(1L)
                                                          .customerShortName("Firma")
                                                          .build();

    @Test
    public void getShouldFetchAllAHalDocument() throws Exception {
        Page<MeasureDTO> page = new PageImpl<>(Collections.singletonList(MEASURE_1));
        given(measureService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/measures")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$[0].id", is(MEASURE_1.getId().intValue())))
               .andExpect(jsonPath("$[0].iadjust", is(MEASURE_1.getIAdjust().intValue())))
               .andExpect(jsonPath("$[0].uadjust", is(MEASURE_1.getUAdjust().doubleValue())))
               .andExpect(jsonPath("$[0].validProtocolId", is(MEASURE_1.getValidProtocolId()
                                                                       .intValue())))
               .andExpect(jsonPath("$[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].versionId", is(MEASURE_1.getVersionId().intValue())))
               .andReturn();
        verify(measureService, times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(measureService);
    }


    @Test
    public void getShouldFetchAHalDocument() throws Exception {
        given(measureService.findById(1L)).willReturn(Optional.of(MEASURE_1));

        mockMvc.perform(get("/api/measures/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(MEASURE_1.getId().intValue())))
               .andExpect(jsonPath("$.iadjust", is(MEASURE_1.getIAdjust().intValue())))
               .andExpect(jsonPath("$.uadjust", is(MEASURE_1.getUAdjust().doubleValue())))
               .andExpect(jsonPath("$.validProtocolId", is(MEASURE_1.getValidProtocolId().intValue())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MEASURE_1.getVersionId().intValue())))
               .andReturn();
        verify(measureService, times(1)).findById(1L);
        verifyNoMoreInteractions(measureService);
    }

}