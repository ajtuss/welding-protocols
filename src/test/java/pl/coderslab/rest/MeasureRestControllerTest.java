package pl.coderslab.rest;

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
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.entities.PowerType;
import pl.coderslab.domain.services.MeasureService;
import pl.coderslab.domain.services.ValidProtocolService;
import pl.coderslab.rest.assemblers.MeasureResourceAssembler;
import pl.coderslab.rest.assemblers.ValidProtocolResourceAssembler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MeasureRestController.class, secure = false)
@Import({ValidProtocolResourceAssembler.class, MeasureResourceAssembler.class})
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
        given(measureService.findAll()).willReturn(Arrays.asList(MEASURE_1, MEASURE_2));

        mockMvc.perform(get("/api/measures")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$._embedded.measures[0].id", is(MEASURE_1.getId().intValue())))
               .andExpect(jsonPath("$._embedded.measures[0].iadjust", is(MEASURE_1.getIAdjust().intValue())))
               .andExpect(jsonPath("$._embedded.measures[0].uadjust", is(MEASURE_1.getUAdjust().doubleValue())))
               .andExpect(jsonPath("$._embedded.measures[0].validProtocolId", is(MEASURE_1.getValidProtocolId().intValue())))
               .andExpect(jsonPath("$._embedded.measures[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.measures[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.measures[0].versionId", is(MEASURE_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.measures[0]._links.self.href", is("http://localhost/api/measures/1")))
               .andExpect(jsonPath("$._embedded.measures[0]._links.measures.href", is("http://localhost/api/measures")))
               .andExpect(jsonPath("$._embedded.measures[0]._links.validations.href", is("http://localhost/api/measures/1/validations")))
               .andExpect(jsonPath("$._embedded.measures[1].id", is(MEASURE_2.getId().intValue())))
               .andExpect(jsonPath("$._embedded.measures[1].iadjust", is(MEASURE_2.getIAdjust().intValue())))
               .andExpect(jsonPath("$._embedded.measures[1].uadjust", is(MEASURE_2.getUAdjust().doubleValue())))
               .andExpect(jsonPath("$._embedded.measures[1].validProtocolId", is(MEASURE_2.getValidProtocolId().intValue())))
               .andExpect(jsonPath("$._embedded.measures[1].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.measures[1].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.measures[1].versionId", is(MEASURE_2.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.measures[1]._links.self.href", is("http://localhost/api/measures/2")))
               .andExpect(jsonPath("$._embedded.measures[1]._links.measures.href", is("http://localhost/api/measures")))
               .andExpect(jsonPath("$._embedded.measures[1]._links.validations.href", is("http://localhost/api/measures/2/validations")))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/measures")))
               .andReturn();
        verify(measureService, times(1)).findAll();
        verifyNoMoreInteractions(measureService);
    }


    @Test
    public void getShouldFetchAHalDocument() throws Exception {
        given(measureService.findById(1L)).willReturn(MEASURE_1);

        mockMvc.perform(get("/api/measures/1")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(MEASURE_1.getId().intValue())))
               .andExpect(jsonPath("$.iadjust", is(MEASURE_1.getIAdjust().intValue())))
               .andExpect(jsonPath("$.uadjust", is(MEASURE_1.getUAdjust().doubleValue())))
               .andExpect(jsonPath("$.validProtocolId", is(MEASURE_1.getValidProtocolId().intValue())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MEASURE_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/measures/1")))
               .andExpect(jsonPath("$._links.measures.href", is("http://localhost/api/measures")))
               .andExpect(jsonPath("$._links.validations.href", is("http://localhost/api/measures/1/validations")))
               .andReturn();
        verify(measureService, times(1)).findById(1L);
        verifyNoMoreInteractions(measureService);
    }

}