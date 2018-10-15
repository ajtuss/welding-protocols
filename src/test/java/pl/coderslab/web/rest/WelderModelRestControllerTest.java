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
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.RangeDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.service.WelderModelService;

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
@WebMvcTest(value = WelderModelRestController.class, secure = false)
@EnableSpringDataWebSupport
public class WelderModelRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WelderModelService modelService;

    @Autowired
    private ObjectMapper mapper;

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();

    private static final BrandDTO BRAND = new BrandDTO(1L, "Kemppi", DATE_TIME, DATE_TIME, 1L);

    private static final RangeDTO RANGE_MIG = new RangeDTO(BigDecimal.valueOf(1.),BigDecimal.valueOf( 100.), BigDecimal.valueOf(10.), BigDecimal.valueOf(11.));
    private static final RangeDTO RANGE_MMA = new RangeDTO(BigDecimal.valueOf(2.), BigDecimal.valueOf(200.), BigDecimal.valueOf(20.), BigDecimal.valueOf(22.));
    private static final RangeDTO RANGE_TIG = new RangeDTO(BigDecimal.valueOf(3.), BigDecimal.valueOf(300.), BigDecimal.valueOf(30.), BigDecimal.valueOf(33.));

    private static final WelderModelDTO MODEL = new WelderModelDTO(1L, "Mastertig 3000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);

    private static final WelderModelDTO MODEL_AFTER_UPDATE = new WelderModelDTO(1L, "Mastertig 4000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 2L);
    private static final WelderModelDTO MODEL_CREATION = new WelderModelDTO("Mastertig 3000", 1L,
            true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG);

    private static final MachineDTO MACHINE = new MachineDTO(1L, "12345678", "484-123123",
            MODEL.getId(), MODEL.getName(), MODEL.getBrandId(), MODEL.getBrandName(),
            1L, "COMPANY", DATE_TIME, DATE_TIME, 1L);


    @Test
    public void getShouldFetchAllWelderModels() throws Exception {
        PageImpl<WelderModelDTO> page = new PageImpl<>(Collections.singletonList(MODEL));
        given(modelService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/models")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$[0].id", is(MODEL.getId().intValue())))
               .andExpect(jsonPath("$[0].name", is(MODEL.getName())))
               .andExpect(jsonPath("$[0].brandId", is(MODEL.getBrandId().intValue())))
               .andExpect(jsonPath("$[0].brandName", is(MODEL.getBrandName())))
               .andExpect(jsonPath("$[0].mig", is(MODEL.getMig())))
               .andExpect(jsonPath("$[0].mma", is(MODEL.getMma())))
               .andExpect(jsonPath("$[0].tig", is(MODEL.getTig())))
               .andExpect(jsonPath("$[0].currentMeter", is(MODEL.getCurrentMeter())))
               .andExpect(jsonPath("$[0].voltageMeter", is(MODEL.getVoltageMeter())))
               .andExpect(jsonPath("$[0].stepControl", is(MODEL.getStepControl())))
               .andExpect(jsonPath("$[0].stepControl", is(MODEL.getStepControl())))
               .andExpect(jsonPath("$[0].migRange.imin", is(RANGE_MIG.getIMin().doubleValue())))
               .andExpect(jsonPath("$[0].migRange.imax", is(RANGE_MIG.getIMax().doubleValue())))
               .andExpect(jsonPath("$[0].migRange.umin", is(RANGE_MIG.getUMin().doubleValue())))
               .andExpect(jsonPath("$[0].migRange.umax", is(RANGE_MIG.getUMax().doubleValue())))
               .andExpect(jsonPath("$[0].tigRange.imin", is(RANGE_TIG.getIMin().doubleValue())))
               .andExpect(jsonPath("$[0].tigRange.imax", is(RANGE_TIG.getIMax().doubleValue())))
               .andExpect(jsonPath("$[0].tigRange.umin", is(RANGE_TIG.getUMin().doubleValue())))
               .andExpect(jsonPath("$[0].tigRange.umax", is(RANGE_TIG.getUMax().doubleValue())))
               .andExpect(jsonPath("$[0].mmaRange.imin", is(RANGE_MMA.getIMin().doubleValue())))
               .andExpect(jsonPath("$[0].mmaRange.imax", is(RANGE_MMA.getIMax().doubleValue())))
               .andExpect(jsonPath("$[0].mmaRange.umin", is(RANGE_MMA.getUMin().doubleValue())))
               .andExpect(jsonPath("$[0].mmaRange.umax", is(RANGE_MMA.getUMax().doubleValue())))
               .andExpect(jsonPath("$[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].versionId", is(MODEL.getVersionId().intValue())))
               .andReturn();
        verify(modelService, times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(modelService);
    }

    @Test
    public void getShouldFetchAWelderModel() throws Exception {

        given(modelService.findById(1L)).willReturn(Optional.of(MODEL));

        mockMvc.perform(get("/api/models/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(MODEL.getId().intValue())))
               .andExpect(jsonPath("$.name", is(MODEL.getName())))
               .andExpect(jsonPath("$.brandId", is(MODEL.getBrandId().intValue())))
               .andExpect(jsonPath("$.brandName", is(MODEL.getBrandName())))
               .andExpect(jsonPath("$.mig", is(MODEL.getMig())))
               .andExpect(jsonPath("$.mma", is(MODEL.getMma())))
               .andExpect(jsonPath("$.tig", is(MODEL.getTig())))
               .andExpect(jsonPath("$.currentMeter", is(MODEL.getCurrentMeter())))
               .andExpect(jsonPath("$.voltageMeter", is(MODEL.getVoltageMeter())))
               .andExpect(jsonPath("$.stepControl", is(MODEL.getStepControl())))
               .andExpect(jsonPath("$.stepControl", is(MODEL.getStepControl())))
               .andExpect(jsonPath("$.migRange.imin", is(RANGE_MIG.getIMin().doubleValue())))
               .andExpect(jsonPath("$.migRange.imax", is(RANGE_MIG.getIMax().doubleValue())))
               .andExpect(jsonPath("$.migRange.umin", is(RANGE_MIG.getUMin().doubleValue())))
               .andExpect(jsonPath("$.migRange.umax", is(RANGE_MIG.getUMax().doubleValue())))
               .andExpect(jsonPath("$.tigRange.imin", is(RANGE_TIG.getIMin().doubleValue())))
               .andExpect(jsonPath("$.tigRange.imax", is(RANGE_TIG.getIMax().doubleValue())))
               .andExpect(jsonPath("$.tigRange.umin", is(RANGE_TIG.getUMin().doubleValue())))
               .andExpect(jsonPath("$.tigRange.umax", is(RANGE_TIG.getUMax().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.imin", is(RANGE_MMA.getIMin().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.imax", is(RANGE_MMA.getIMax().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.umin", is(RANGE_MMA.getUMin().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.umax", is(RANGE_MMA.getUMax().doubleValue())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MODEL.getVersionId().intValue())))
               .andReturn();
        verify(modelService, times(1)).findById(1L);
        verifyNoMoreInteractions(modelService);
    }

    @Test
    public void postShouldAddModelAndFetchSavedModel() throws Exception {
        String contentBody = mapper.writeValueAsString(MODEL_CREATION);


        given(modelService.save(MODEL_CREATION)).willReturn(MODEL);
        mockMvc.perform(post("/api/models")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(redirectedUrl("/api/models/1"))
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(MODEL.getId().intValue())))
               .andExpect(jsonPath("$.name", is(MODEL.getName())))
               .andExpect(jsonPath("$.brandId", is(MODEL.getBrandId().intValue())))
               .andExpect(jsonPath("$.brandName", is(MODEL.getBrandName())))
               .andExpect(jsonPath("$.mig", is(MODEL.getMig())))
               .andExpect(jsonPath("$.mma", is(MODEL.getMma())))
               .andExpect(jsonPath("$.tig", is(MODEL.getTig())))
               .andExpect(jsonPath("$.currentMeter", is(MODEL.getCurrentMeter())))
               .andExpect(jsonPath("$.voltageMeter", is(MODEL.getVoltageMeter())))
               .andExpect(jsonPath("$.stepControl", is(MODEL.getStepControl())))
               .andExpect(jsonPath("$.stepControl", is(MODEL.getStepControl())))
               .andExpect(jsonPath("$.migRange.imin", is(RANGE_MIG.getIMin().doubleValue())))
               .andExpect(jsonPath("$.migRange.imax", is(RANGE_MIG.getIMax().doubleValue())))
               .andExpect(jsonPath("$.migRange.umin", is(RANGE_MIG.getUMin().doubleValue())))
               .andExpect(jsonPath("$.migRange.umax", is(RANGE_MIG.getUMax().doubleValue())))
               .andExpect(jsonPath("$.tigRange.imin", is(RANGE_TIG.getIMin().doubleValue())))
               .andExpect(jsonPath("$.tigRange.imax", is(RANGE_TIG.getIMax().doubleValue())))
               .andExpect(jsonPath("$.tigRange.umin", is(RANGE_TIG.getUMin().doubleValue())))
               .andExpect(jsonPath("$.tigRange.umax", is(RANGE_TIG.getUMax().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.imin", is(RANGE_MMA.getIMin().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.imax", is(RANGE_MMA.getIMax().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.umin", is(RANGE_MMA.getUMin().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.umax", is(RANGE_MMA.getUMax().doubleValue())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MODEL.getVersionId().intValue())))
               .andReturn();
        verify(modelService, times(1)).save(MODEL_CREATION);
        verifyNoMoreInteractions(modelService);
    }

    @Test
    public void putShouldUpdateModelAndFetchSavedModel() throws Exception {
        String contentBody = mapper.writeValueAsString(MODEL);
        System.out.println(contentBody);


        given(modelService.update(MODEL)).willReturn(MODEL_AFTER_UPDATE);
        mockMvc.perform(put("/api/models")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(MODEL_AFTER_UPDATE.getId().intValue())))
               .andExpect(jsonPath("$.name", is(MODEL_AFTER_UPDATE.getName())))
               .andExpect(jsonPath("$.brandId", is(MODEL_AFTER_UPDATE.getBrandId().intValue())))
               .andExpect(jsonPath("$.brandName", is(MODEL_AFTER_UPDATE.getBrandName())))
               .andExpect(jsonPath("$.mig", is(MODEL_AFTER_UPDATE.getMig())))
               .andExpect(jsonPath("$.mma", is(MODEL_AFTER_UPDATE.getMma())))
               .andExpect(jsonPath("$.tig", is(MODEL_AFTER_UPDATE.getTig())))
               .andExpect(jsonPath("$.currentMeter", is(MODEL_AFTER_UPDATE.getCurrentMeter())))
               .andExpect(jsonPath("$.voltageMeter", is(MODEL_AFTER_UPDATE.getVoltageMeter())))
               .andExpect(jsonPath("$.stepControl", is(MODEL_AFTER_UPDATE.getStepControl())))
               .andExpect(jsonPath("$.stepControl", is(MODEL_AFTER_UPDATE.getStepControl())))
               .andExpect(jsonPath("$.migRange.imin", is(RANGE_MIG.getIMin().doubleValue())))
               .andExpect(jsonPath("$.migRange.imax", is(RANGE_MIG.getIMax().doubleValue())))
               .andExpect(jsonPath("$.migRange.umin", is(RANGE_MIG.getUMin().doubleValue())))
               .andExpect(jsonPath("$.migRange.umax", is(RANGE_MIG.getUMax().doubleValue())))
               .andExpect(jsonPath("$.tigRange.imin", is(RANGE_TIG.getIMin().doubleValue())))
               .andExpect(jsonPath("$.tigRange.imax", is(RANGE_TIG.getIMax().doubleValue())))
               .andExpect(jsonPath("$.tigRange.umin", is(RANGE_TIG.getUMin().doubleValue())))
               .andExpect(jsonPath("$.tigRange.umax", is(RANGE_TIG.getUMax().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.imin", is(RANGE_MMA.getIMin().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.imax", is(RANGE_MMA.getIMax().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.umin", is(RANGE_MMA.getUMin().doubleValue())))
               .andExpect(jsonPath("$.mmaRange.umax", is(RANGE_MMA.getUMax().doubleValue())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MODEL_AFTER_UPDATE.getVersionId().intValue())))
               .andReturn();
        verify(modelService, times(1)).update(MODEL);
        verifyNoMoreInteractions(modelService);
    }

    @Test
    public void deleteShouldRemoveAndReturnStatusOk() throws Exception {

        doNothing().when(modelService).remove(1L);

        mockMvc.perform(delete("/api/models/1")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andReturn();
        verify(modelService, times(1)).remove(1L);
        verifyNoMoreInteractions(modelService);
    }


    @Test
    public void getMachinesShouldFetchAHalDocument() throws Exception {
        PageImpl<MachineDTO> page = new PageImpl<>(Collections.singletonList(MACHINE));
        given(modelService.findAllMachinesByModelId(eq(1L), any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/models/1/machines")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$[0].id", is(MACHINE.getId().intValue())))
               .andExpect(jsonPath("$[0].serialNumber", is(MACHINE.getSerialNumber())))
               .andExpect(jsonPath("$[0].inwNumber", is(MACHINE.getInwNumber())))
               .andExpect(jsonPath("$[0].welderModelBrandId", is(MACHINE.getWelderModelBrandId().intValue())))
               .andExpect(jsonPath("$[0].welderModelBrandName", is(MACHINE.getWelderModelBrandName())))
               .andExpect(jsonPath("$[0].welderModelId", is(MACHINE.getWelderModelId().intValue())))
               .andExpect(jsonPath("$[0].welderModelName", is(MACHINE.getWelderModelName())))
               .andExpect(jsonPath("$[0].customerId", is(MACHINE.getCustomerId().intValue())))
               .andExpect(jsonPath("$[0].customerShortName", is(MACHINE.getCustomerShortName())))
               .andExpect(jsonPath("$[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].versionId", is(MACHINE.getVersionId().intValue())))
               .andReturn();
        verify(modelService, times(1)).findAllMachinesByModelId(eq(1L), any(Pageable.class));
        verifyNoMoreInteractions(modelService);
    }
}