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
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.RangeDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.dto.WelderModelUpdateDTO;
import pl.coderslab.domain.services.WelderModelService;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = WelderModelRestController.class, secure = false)
@Import({WelderModelResourceAssembler.class})
public class WelderModelRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WelderModelService modelService;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static final BrandDTO BRAND_1 = new BrandDTO(1L, "Kemppi", DATE_TIME, DATE_TIME, 1L);
    private static final RangeDTO RANGE_MIG = new RangeDTO(1., 100., 10., 100.);
    private static final RangeDTO RANGE_MMA = new RangeDTO(2., 200., 20., 200.);
    private static final RangeDTO RANGE_TIG = new RangeDTO(3., 300., 30., 300.);
    private static final WelderModelDTO MODEL_1 = new WelderModelDTO(1L, "Mastertig 3000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);
    private static final WelderModelDTO MODEL_2 = new WelderModelDTO(2L, "ProEvolution 4200", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);
    private static final WelderModelUpdateDTO MODEL_UPDATE_1 = new WelderModelUpdateDTO(1L, "Mastertig 3000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, 1L);
    private static final WelderModelDTO MODEL_AFTER_UPDATE_1 = new WelderModelDTO(1L, "Mastertig 4000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 2L);

    @Test
    public void getShouldFetchAllAHalDocument() throws Exception {
        given(modelService.findAll()).willReturn(Arrays.asList(MODEL_1, MODEL_2));

        mockMvc.perform(get("/api/models")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$._embedded.models[0].id", is(MODEL_1.getId().intValue())))
               .andExpect(jsonPath("$._embedded.models[0].name", is(MODEL_1.getName())))
               .andExpect(jsonPath("$._embedded.models[0].brandId", is(MODEL_1.getBrandId().intValue())))
               .andExpect(jsonPath("$._embedded.models[0].brandName", is(MODEL_1.getBrandName())))
               .andExpect(jsonPath("$._embedded.models[0].mig", is(MODEL_1.getMig())))
               .andExpect(jsonPath("$._embedded.models[0].mma", is(MODEL_1.getMma())))
               .andExpect(jsonPath("$._embedded.models[0].tig", is(MODEL_1.getTig())))
               .andExpect(jsonPath("$._embedded.models[0].currentMeter", is(MODEL_1.getCurrentMeter())))
               .andExpect(jsonPath("$._embedded.models[0].voltageMeter", is(MODEL_1.getVoltageMeter())))
               .andExpect(jsonPath("$._embedded.models[0].stepControl", is(MODEL_1.getStepControl())))
               .andExpect(jsonPath("$._embedded.models[0].stepControl", is(MODEL_1.getStepControl())))
               .andExpect(jsonPath("$._embedded.models[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.models[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.models[0].versionId", is(MODEL_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.models[0]._links.self.href", is("http://localhost/api/models/1")))
               .andExpect(jsonPath("$._embedded.models[0]._links.models.href", is("http://localhost/api/models")))
               .andExpect(jsonPath("$._embedded.models[0]._links.brands.href", is("http://localhost/api/models/1/brands")))
               .andExpect(jsonPath("$._embedded.models[0]._links.machines.href", is("http://localhost/api/models/1/machines")))
               .andExpect(jsonPath("$._embedded.models[1].id", is(MODEL_2.getId().intValue())))
               .andExpect(jsonPath("$._embedded.models[1].name", is(MODEL_2.getName())))
               .andExpect(jsonPath("$._embedded.models[1].brandId", is(MODEL_2.getBrandId().intValue())))
               .andExpect(jsonPath("$._embedded.models[1].brandName", is(MODEL_2.getBrandName())))
               .andExpect(jsonPath("$._embedded.models[1].mig", is(MODEL_2.getMig())))
               .andExpect(jsonPath("$._embedded.models[1].mma", is(MODEL_2.getMma())))
               .andExpect(jsonPath("$._embedded.models[1].tig", is(MODEL_2.getTig())))
               .andExpect(jsonPath("$._embedded.models[1].currentMeter", is(MODEL_2.getCurrentMeter())))
               .andExpect(jsonPath("$._embedded.models[1].voltageMeter", is(MODEL_2.getVoltageMeter())))
               .andExpect(jsonPath("$._embedded.models[1].stepControl", is(MODEL_2.getStepControl())))
               .andExpect(jsonPath("$._embedded.models[1].stepControl", is(MODEL_2.getStepControl())))
               .andExpect(jsonPath("$._embedded.models[1].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.models[1].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.models[1].versionId", is(MODEL_2.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.models[1]._links.self.href", is("http://localhost/api/models/2")))
               .andExpect(jsonPath("$._embedded.models[1]._links.models.href", is("http://localhost/api/models")))
               .andExpect(jsonPath("$._embedded.models[1]._links.brands.href", is("http://localhost/api/models/2/brands")))
               .andExpect(jsonPath("$._embedded.models[1]._links.machines.href", is("http://localhost/api/models/2/machines")))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/models")))
               .andReturn();
        verify(modelService, times(1)).findAll();
        verifyNoMoreInteractions(modelService);
    }

    @Test
    public void getShouldFetchAHalDocument() throws Exception {

        given(modelService.findById(1L)).willReturn(MODEL_1);

        mockMvc.perform(get("/api/models/1")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(MODEL_1.getId().intValue())))
               .andExpect(jsonPath("$.name", is(MODEL_1.getName())))
               .andExpect(jsonPath("$.brandId", is(MODEL_1.getBrandId().intValue())))
               .andExpect(jsonPath("$.brandName", is(MODEL_1.getBrandName())))
               .andExpect(jsonPath("$.mig", is(MODEL_1.getMig())))
               .andExpect(jsonPath("$.mma", is(MODEL_1.getMma())))
               .andExpect(jsonPath("$.tig", is(MODEL_1.getTig())))
               .andExpect(jsonPath("$.currentMeter", is(MODEL_1.getCurrentMeter())))
               .andExpect(jsonPath("$.voltageMeter", is(MODEL_1.getVoltageMeter())))
               .andExpect(jsonPath("$.stepControl", is(MODEL_1.getStepControl())))
               .andExpect(jsonPath("$.stepControl", is(MODEL_1.getStepControl())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MODEL_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/models/1")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/models")))
               .andExpect(jsonPath("$._links.brands.href", is("http://localhost/api/models/1/brands")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/models/1/machines")))
               .andReturn();
        verify(modelService, times(1)).findById(1L);
        verifyNoMoreInteractions(modelService);
    }


    @Test
    public void putShouldUpdateModelAndFetchHalDocument() throws Exception {
        String contentBody = mapper.writeValueAsString(MODEL_UPDATE_1);


        given(modelService.update(MODEL_UPDATE_1)).willReturn(MODEL_AFTER_UPDATE_1);
        mockMvc.perform(put("/api/brands/1")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(MODEL_AFTER_UPDATE_1.getId().intValue())))
               .andExpect(jsonPath("$.name", is(MODEL_AFTER_UPDATE_1.getName())))
               .andExpect(jsonPath("$.brandId", is(MODEL_AFTER_UPDATE_1.getBrandId().intValue())))
               .andExpect(jsonPath("$.brandName", is(MODEL_AFTER_UPDATE_1.getBrandName())))
               .andExpect(jsonPath("$.mig", is(MODEL_AFTER_UPDATE_1.getMig())))
               .andExpect(jsonPath("$.mma", is(MODEL_AFTER_UPDATE_1.getMma())))
               .andExpect(jsonPath("$.tig", is(MODEL_AFTER_UPDATE_1.getTig())))
               .andExpect(jsonPath("$.currentMeter", is(MODEL_AFTER_UPDATE_1.getCurrentMeter())))
               .andExpect(jsonPath("$.voltageMeter", is(MODEL_AFTER_UPDATE_1.getVoltageMeter())))
               .andExpect(jsonPath("$.stepControl", is(MODEL_AFTER_UPDATE_1.getStepControl())))
               .andExpect(jsonPath("$.stepControl", is(MODEL_AFTER_UPDATE_1.getStepControl())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MODEL_AFTER_UPDATE_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/models/1")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/models")))
               .andExpect(jsonPath("$._links.brands.href", is("http://localhost/api/models/1/brands")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/models/1/machines")))
               .andReturn();
        verify(modelService, times(1)).update(MODEL_UPDATE_1);
        verifyNoMoreInteractions(modelService);
    }

    @Test
    public void putWithInvalidIdShouldReturnHttpStatus400() throws Exception {
        String contentBody = mapper.writeValueAsString(MODEL_UPDATE_1);


        given(modelService.update(MODEL_UPDATE_1)).willReturn(MODEL_AFTER_UPDATE_1);
        mockMvc.perform(put("/api/models/2")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andReturn();
        verifyNoMoreInteractions(modelService);
    }

    @Test
    public void deleteShouldRemoveAndReturnNoContent() throws Exception {

        doNothing().when(modelService).remove(1L);

        mockMvc.perform(delete("/api/models/1")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isNoContent())
               .andReturn();
        verify(modelService, times(1)).remove(1L);
        verifyNoMoreInteractions(modelService);
    }
}