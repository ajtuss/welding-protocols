package pl.coderslab.web.rest;

import org.junit.Before;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.services.BrandService;
import pl.coderslab.web.rest.assemblers.BrandResourceAssembler;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(value = BrandRestController.class)
@Import({BrandResourceAssembler.class})
public class BrandRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BrandService brandService;

    private LocalDateTime time = LocalDateTime.now();

    @Before
    public void setUpClass() {

    }

    @Test
    public void getShouldFetchAllAHalDocument() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        given(brandService.findAll()).willReturn(
                Arrays.asList(new BrandDTO(1L, "Kemppi", time, time, 1L),
                        new BrandDTO(2L, "Fronius", time, time, 1L))
        );

        mockMvc.perform(get("/api/brands")
                .with(user("user"))
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$._embedded.brands[0].id", is(1)))
               .andExpect(jsonPath("$._embedded.brands[0].name", is("Kemppi")))
               .andExpect(jsonPath("$._embedded.brands[0].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.brands[0].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.brands[0].versionId", is(1)))
               .andExpect(jsonPath("$._embedded.brands[0]._links.self.href", is("http://localhost/api/brands/1")))
               .andExpect(jsonPath("$._embedded.brands[0]._links.brands.href", is("http://localhost/api/brands")))
               .andExpect(jsonPath("$._embedded.brands[0]._links.models.href", is("http://localhost/api/brands/1/models")))
               .andExpect(jsonPath("$._embedded.brands[1].id", is(2)))
               .andExpect(jsonPath("$._embedded.brands[1].name", is("Fronius")))
               .andExpect(jsonPath("$._embedded.brands[1].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.brands[1].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.brands[1].versionId", is(1)))
               .andExpect(jsonPath("$._embedded.brands[1]._links.self.href", is("http://localhost/api/brands/2")))
               .andExpect(jsonPath("$._embedded.brands[1]._links.brands.href", is("http://localhost/api/brands")))
               .andExpect(jsonPath("$._embedded.brands[1]._links.models.href", is("http://localhost/api/brands/2/models")))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/brands")))
               .andReturn();
    }

    @Test
    public void getShouldFetchAHalDocument() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        given(brandService.findById(1L)).willReturn(new BrandDTO(1L, "Kemppi", time, time, 1L));

        mockMvc.perform(get("/api/brands/1")
                .with(user("user"))
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.name", is("Kemppi")))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(1)))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/brands/1")))
               .andExpect(jsonPath("$._links.brands.href", is("http://localhost/api/brands")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/brands/1/models")))
               .andReturn();
    }

    @Test
    public void getModelsByBrandId() {
    }

    @Test
    public void addBrand() {
    }

    @Test
    public void editBrand() {
    }

    @Test
    public void delete() {
    }
}