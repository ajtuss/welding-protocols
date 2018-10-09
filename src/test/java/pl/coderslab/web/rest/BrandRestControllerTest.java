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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.RangeDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.service.BrandService;
import pl.coderslab.web.rest.assemblers.BrandResourceAssembler;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;

import java.math.BigDecimal;
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
@WebMvcTest(value = BrandRestController.class, secure = false)
@Import({BrandResourceAssembler.class, WelderModelResourceAssembler.class, PagedResourcesAssembler.class})
public class BrandRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static final BrandDTO BRAND_1 = new BrandDTO(1L, "Kemppi", DATE_TIME, DATE_TIME, 1L);
    private static final BrandDTO BRAND_2 = new BrandDTO(2L, "Fronius", DATE_TIME, DATE_TIME, 1L);
    private static final BrandDTO BRAND_1_AFTER_UPDATE = new BrandDTO(1L, "Kemppi", DATE_TIME, DATE_TIME, 2L);
    private static final BrandDTO BRAND_UPDATE_1 = new BrandDTO(1L, "Kemppi", 1L);


    private static final RangeDTO RANGE_MIG = new RangeDTO(BigDecimal.valueOf(1.), BigDecimal.valueOf(100.),
            BigDecimal.valueOf(10.), BigDecimal.valueOf(100.));
    private static final RangeDTO RANGE_MMA = new RangeDTO(BigDecimal.valueOf(2.), BigDecimal.valueOf(200.),
            BigDecimal.valueOf(20.), BigDecimal.valueOf(200.));
    private static final RangeDTO RANGE_TIG = new RangeDTO(BigDecimal.valueOf(3.), BigDecimal.valueOf(300.),
            BigDecimal.valueOf(30.), BigDecimal.valueOf(300.));
    private static final WelderModelDTO MODEL_1 = new WelderModelDTO(1L, "Mastertig 3000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);
    private static final WelderModelDTO MODEL_2 = new WelderModelDTO(2L, "ProEvolution 4200", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);

    @Test
    public void getShouldFetchAllAHalDocument() throws Exception {

        given(brandService.findAll(Pageable.unpaged())).willReturn(new PageImpl<>(Arrays.asList(BRAND_1, BRAND_2)));

        mockMvc.perform(get("/api/brands")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$._embedded.brands[0].id", is(BRAND_1.getId().intValue())))
               .andExpect(jsonPath("$._embedded.brands[0].name", is(BRAND_1.getName())))
               .andExpect(jsonPath("$._embedded.brands[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.brands[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.brands[0].versionId", is(BRAND_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.brands[0]._links.self.href", is("http://localhost/api/brands/1")))
               .andExpect(jsonPath("$._embedded.brands[0]._links.brands.href", is("http://localhost/api/brands")))
               .andExpect(jsonPath("$._embedded.brands[0]._links.models.href", is("http://localhost/api/brands/1/models")))
               .andExpect(jsonPath("$._embedded.brands[1].id", is(BRAND_2.getId().intValue())))
               .andExpect(jsonPath("$._embedded.brands[1].name", is(BRAND_2.getName())))
               .andExpect(jsonPath("$._embedded.brands[1].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.brands[1].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.brands[1].versionId", is(BRAND_2.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.brands[1]._links.self.href", is("http://localhost/api/brands/2")))
               .andExpect(jsonPath("$._embedded.brands[1]._links.brands.href", is("http://localhost/api/brands")))
               .andExpect(jsonPath("$._embedded.brands[1]._links.models.href", is("http://localhost/api/brands/2/models")))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/brands")))
               .andReturn();
        verify(brandService, times(1)).findAll(Pageable.unpaged());
        verifyNoMoreInteractions(brandService);
    }

    @Test
    public void getShouldFetchAHalDocument() throws Exception {

        given(brandService.findById(1L)).willReturn(java.util.Optional.ofNullable(BRAND_1));

        mockMvc.perform(get("/api/brands/1")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(BRAND_1.getId().intValue())))
               .andExpect(jsonPath("$.name", is(BRAND_1.getName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(BRAND_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/brands/1")))
               .andExpect(jsonPath("$._links.brands.href", is("http://localhost/api/brands")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/brands/1/models")))
               .andReturn();
        verify(brandService, times(1)).findById(1L);
        verifyNoMoreInteractions(brandService);
    }

    @Test
    public void postShouldCreateNewBrandAndFetchAHalDocument() throws Exception {
        BrandDTO creationDTO = new BrandDTO(BRAND_1.getName());
        String contentBody = mapper.writeValueAsString(creationDTO);

        given(brandService.saveBrand(creationDTO)).willReturn(BRAND_1);
        mockMvc.perform(post("/api/brands")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(redirectedUrl("http://localhost/api/brands/1"))
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(BRAND_1.getId().intValue())))
               .andExpect(jsonPath("$.name", is(BRAND_1.getName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(BRAND_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/brands/1")))
               .andExpect(jsonPath("$._links.brands.href", is("http://localhost/api/brands")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/brands/1/models")))
               .andReturn();
        verify(brandService, times(1)).saveBrand(creationDTO);
        verifyNoMoreInteractions(brandService);
    }

    @Test
    public void putShouldUpdateBrandAndFetchHalDocument() throws Exception {
        String contentBody = mapper.writeValueAsString(BRAND_UPDATE_1);


        given(brandService.updateBrand(BRAND_UPDATE_1)).willReturn(BRAND_1_AFTER_UPDATE);
        mockMvc.perform(put("/api/brands/1")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(BRAND_1_AFTER_UPDATE.getId().intValue())))
               .andExpect(jsonPath("$.name", is(BRAND_1_AFTER_UPDATE.getName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(BRAND_1_AFTER_UPDATE.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/brands/1")))
               .andExpect(jsonPath("$._links.brands.href", is("http://localhost/api/brands")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/brands/1/models")))
               .andReturn();
        verify(brandService, times(1)).updateBrand(BRAND_UPDATE_1);
        verifyNoMoreInteractions(brandService);
    }

    @Test
    public void putWithInvalidIdShouldReturnHttpStatus400() throws Exception {
        String contentBody = mapper.writeValueAsString(BRAND_UPDATE_1);


        given(brandService.updateBrand(BRAND_UPDATE_1)).willReturn(BRAND_1_AFTER_UPDATE);
        mockMvc.perform(put("/api/brands/2")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andReturn();
        verifyNoMoreInteractions(brandService);
    }

    @Test
    public void deleteShouldRemoveAndReturnNoContent() throws Exception {

        doNothing().when(brandService).remove(1L);

        mockMvc.perform(delete("/api/brands/1")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isNoContent())
               .andReturn();
        verify(brandService, times(1)).remove(1L);
        verifyNoMoreInteractions(brandService);
    }


    @Test
    public void getShouldFetchAllModelsAHalDocument() throws Exception {
        given(brandService.findWelderModelsByBrandId(1L)).willReturn(Arrays.asList(MODEL_1, MODEL_2));

        mockMvc.perform(get("/api/brands/1/models")
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
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/brands/1/models")))
               .andReturn();
        verify(brandService, times(1)).findWelderModelsByBrandId(1L);
        verifyNoMoreInteractions(brandService);
    }
}