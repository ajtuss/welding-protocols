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
import pl.coderslab.service.BrandService;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.RangeDTO;
import pl.coderslab.service.dto.WelderModelDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(value = BrandRestController.class, secure = false)
@EnableSpringDataWebSupport
public class BrandRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static final BrandDTO BRAND = new BrandDTO(1L, "Kemppi", DATE_TIME, DATE_TIME, 1L);
    private static final BrandDTO BRAND_AFTER_UPDATE = new BrandDTO(1L, "Kemppi", DATE_TIME, DATE_TIME, 2L);
    private static final BrandDTO BRAND_UPDATE = new BrandDTO(1L, "Kemppi", 1L);


    private static final RangeDTO RANGE_MIG = new RangeDTO(BigDecimal.valueOf(1.), BigDecimal.valueOf(100.),
            BigDecimal.valueOf(10.), BigDecimal.valueOf(100.));
    private static final RangeDTO RANGE_MMA = new RangeDTO(BigDecimal.valueOf(2.), BigDecimal.valueOf(200.),
            BigDecimal.valueOf(20.), BigDecimal.valueOf(200.));
    private static final RangeDTO RANGE_TIG = new RangeDTO(BigDecimal.valueOf(3.), BigDecimal.valueOf(300.),
            BigDecimal.valueOf(30.), BigDecimal.valueOf(300.));
    private static final WelderModelDTO MODEL = new WelderModelDTO(1L, "Mastertig 3000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);

    @Test
    public void getShouldFetchAllBrands() throws Exception {
        PageImpl<BrandDTO> page = new PageImpl<>(Collections.singletonList(BRAND));
        given(brandService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/brands")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$[0].id", is(BRAND.getId().intValue())))
               .andExpect(jsonPath("$[0].name", is(BRAND.getName())))
               .andExpect(jsonPath("$[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].versionId", is(BRAND.getVersionId().intValue())))
               .andReturn();
        verify(brandService, times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(brandService);
    }

    @Test
    public void getShouldFetchABrand() throws Exception {

        given(brandService.findById(1L)).willReturn(java.util.Optional.ofNullable(BRAND));

        mockMvc.perform(get("/api/brands/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(BRAND.getId().intValue())))
               .andExpect(jsonPath("$.name", is(BRAND.getName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(BRAND.getVersionId().intValue())))
               .andReturn();
        verify(brandService, times(1)).findById(1L);
        verifyNoMoreInteractions(brandService);
    }

    @Test
    public void postShouldCreateNewBrandAndFetchSavedBrand() throws Exception {
        BrandDTO creationDTO = new BrandDTO(BRAND.getName());
        String contentBody = mapper.writeValueAsString(creationDTO);

        given(brandService.save(creationDTO)).willReturn(BRAND);
        mockMvc.perform(post("/api/brands")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(redirectedUrl("/api/brands/1"))
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(BRAND.getId().intValue())))
               .andExpect(jsonPath("$.name", is(BRAND.getName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(BRAND.getVersionId().intValue())))
               .andReturn();
        verify(brandService, times(1)).save(creationDTO);
        verifyNoMoreInteractions(brandService);
    }

    @Test
    public void putShouldUpdateBrandAndFetchSavedBrand() throws Exception {
        String contentBody = mapper.writeValueAsString(BRAND_UPDATE);


        given(brandService.save(BRAND_UPDATE)).willReturn(BRAND_AFTER_UPDATE);
        mockMvc.perform(put("/api/brands")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(contentBody))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(BRAND_AFTER_UPDATE.getId().intValue())))
               .andExpect(jsonPath("$.name", is(BRAND_AFTER_UPDATE.getName())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(BRAND_AFTER_UPDATE.getVersionId().intValue())))
               .andReturn();
        verify(brandService, times(1)).save(BRAND_UPDATE);
        verifyNoMoreInteractions(brandService);
    }

    @Test
    public void deleteShouldRemoveAndReturnNoContent() throws Exception {

        doNothing().when(brandService).remove(1L);

        mockMvc.perform(delete("/api/brands/1")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andReturn();
        verify(brandService, times(1)).remove(1L);
        verifyNoMoreInteractions(brandService);
    }


    @Test
    public void getShouldFetchAllModelsByBrandId() throws Exception {
        PageImpl<WelderModelDTO> page = new PageImpl<>(Collections.singletonList(MODEL));
        given(brandService.findWelderModelsByBrandId(eq(1L), any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/brands/1/models")
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
               .andExpect(jsonPath("$[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$[0].versionId", is(MODEL.getVersionId().intValue())))
               .andReturn();
        verify(brandService, times(1)).findWelderModelsByBrandId(eq(1L), any(Pageable.class));
        verifyNoMoreInteractions(brandService);
    }
}