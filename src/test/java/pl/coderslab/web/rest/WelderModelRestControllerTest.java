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
import pl.coderslab.domain.services.WelderModelService;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private static final RangeDTO RANGE_MIG = new RangeDTO(1L, 1.,
            100., 10., 100., DATE_TIME, DATE_TIME, 1L);
    private static final RangeDTO RANGE_MMA = new RangeDTO(2L, 2.,
            200., 20., 200., DATE_TIME, DATE_TIME, 1L);
    private static final RangeDTO RANGE_TIG = new RangeDTO(3L, 3.,
            300., 30., 300., DATE_TIME, DATE_TIME, 1L);
    private static final WelderModelDTO MODEL_1 = new WelderModelDTO(1L, "Mastertig 3000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);

    @Test
    public void getAllModels() {
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
    public void deleteModel() {
    }
}