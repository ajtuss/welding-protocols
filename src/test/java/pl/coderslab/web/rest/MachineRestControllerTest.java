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
import pl.coderslab.domain.dto.*;
import pl.coderslab.domain.services.MachineService;
import pl.coderslab.web.rest.assemblers.CustomerResourceAssembler;
import pl.coderslab.web.rest.assemblers.MachineResourceAssembler;
import pl.coderslab.web.rest.assemblers.WelderModelResourceAssembler;

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
@WebMvcTest(value = MachineRestController.class, secure = false)
@Import({MachineResourceAssembler.class, WelderModelResourceAssembler.class, CustomerResourceAssembler.class})
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
    private static final RangeDTO RANGE_MIG = new RangeDTO(1., 100., 10., 100.);
    private static final RangeDTO RANGE_MMA = new RangeDTO(2., 200., 20., 200.);
    private static final RangeDTO RANGE_TIG = new RangeDTO(3., 300., 30., 300.);
    private static final WelderModelDTO MODEL_1 = new WelderModelDTO(1L, "Mastertig 3000", 1L,
            "Kemppi", true, true, true, true, true, false,
            RANGE_MIG, RANGE_MMA, RANGE_TIG, DATE_TIME, DATE_TIME, 1L);

    private static final MachineDTO MACHINE_1 = new MachineDTO(1L, "12345678", "484-123123",
            MODEL_1.getId(), MODEL_1.getName(), MODEL_1.getBrandId(), MODEL_1.getBrandName(),
            CUSTOMER_1.getId(), CUSTOMER_1.getShortName(), DATE_TIME, DATE_TIME, 1L);
    private static final MachineDTO MACHINE_2 = new MachineDTO(2L, "9877877123", "484-9879879",
            MODEL_1.getId(), MODEL_1.getName(), MODEL_1.getBrandId(), MODEL_1.getBrandName(),
            CUSTOMER_1.getId(), CUSTOMER_1.getShortName(), DATE_TIME, DATE_TIME, 1L);
    private static final MachineUpdateDTO MACHINE_UPDATE = new MachineUpdateDTO(1L, "12345678", "484-123123",
            MODEL_1.getId(), CUSTOMER_1.getId(), 1L);
    private static final MachineDTO MACHINE_AFTER_UPDATE = new MachineDTO(1L, "12345678", "484-123123",
            MODEL_1.getId(), MODEL_1.getName(), MODEL_1.getBrandId(), MODEL_1.getBrandName(),
            CUSTOMER_1.getId(), CUSTOMER_1.getShortName(), DATE_TIME, DATE_TIME, 2L);
    private static final MachineCreationDTO MACHINE_CREATION = new MachineCreationDTO("12345678", "484-123123",
            MODEL_1.getId(), CUSTOMER_1.getId());

    @Test
    public void getShouldFetchAllAHalDocument() throws Exception {
        given(machineService.findAll()).willReturn(Arrays.asList(MACHINE_1, MACHINE_2));

        mockMvc.perform(get("/api/machines")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$._embedded.machines[0].id", is(MACHINE_1.getId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[0].serialNumber", is(MACHINE_1.getSerialNumber())))
               .andExpect(jsonPath("$._embedded.machines[0].inwNumber", is(MACHINE_1.getInwNumber())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelBrandId", is(MACHINE_1.getWelderModelBrandId())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelBrandName", is(MACHINE_1.getWelderModelBrandName())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelId", is(MACHINE_1.getWelderModelId())))
               .andExpect(jsonPath("$._embedded.machines[0].welderModelName", is(MACHINE_1.getWelderModelName())))
               .andExpect(jsonPath("$._embedded.machines[0].customerId", is(MACHINE_1.getCustomerId())))
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
               .andExpect(jsonPath("$._embedded.machines[1].welderModelBrandId", is(MACHINE_2.getWelderModelBrandId())))
               .andExpect(jsonPath("$._embedded.machines[1].welderModelBrandName", is(MACHINE_2.getWelderModelBrandName())))
               .andExpect(jsonPath("$._embedded.machines[1].welderModelId", is(MACHINE_2.getWelderModelId())))
               .andExpect(jsonPath("$._embedded.machines[1].welderModelName", is(MACHINE_2.getWelderModelName())))
               .andExpect(jsonPath("$._embedded.machines[1].customerId", is(MACHINE_2.getCustomerId())))
               .andExpect(jsonPath("$._embedded.machines[1].customerShortName", is(MACHINE_2.getCustomerShortName())))
               .andExpect(jsonPath("$._embedded.machines[1].creationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.machines[1].modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$._embedded.machines[1].versionId", is(MACHINE_2.getVersionId().intValue())))
               .andExpect(jsonPath("$._embedded.machines[1]._links.self.href", is("http://localhost/api/machines/1")))
               .andExpect(jsonPath("$._embedded.machines[1]._links.machines.href", is("http://localhost/api/machines")))
               .andExpect(jsonPath("$._embedded.machines[1]._links.models.href", is("http://localhost/api/machines/1/models")))
               .andExpect(jsonPath("$._embedded.machines[1]._links.customers.href", is("http://localhost/api/machines/1/customers")))
               .andExpect(jsonPath("$._embedded.machines[1]._links.validations.href", is("http://localhost/api/machines/1/validations")))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/machines")))
               .andReturn();
        verify(machineService, times(1)).findAll();
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void getShouldFetchAHalDocument() throws Exception {

        given(machineService.findById(1L)).willReturn(MACHINE_1);

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
               .andExpect(jsonPath("$.welderModelBrandId", is(MACHINE_1.getWelderModelBrandId())))
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


        given(machineService.update(MACHINE_UPDATE)).willReturn(MACHINE_AFTER_UPDATE);
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
        verify(machineService, times(1)).update(MACHINE_UPDATE);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void putWithInvalidIdShouldReturnHttpStatus400() throws Exception {
        String contentBody = mapper.writeValueAsString(MACHINE_UPDATE);


        given(machineService.update(MACHINE_UPDATE)).willReturn(MACHINE_AFTER_UPDATE);
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
    public void getModelShouldFetchHalDocument() throws Exception {
        given(machineService.findModelByMachineId(1L)).willReturn(MODEL_1);

        mockMvc.perform(get("/api/machines/1/models")
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
               .andExpect(jsonPath("$.migRange.imin", is(RANGE_MIG.getIMin())))
               .andExpect(jsonPath("$.migRange.imax", is(RANGE_MIG.getIMax())))
               .andExpect(jsonPath("$.migRange.umin", is(RANGE_MIG.getUMin())))
               .andExpect(jsonPath("$.migRange.umax", is(RANGE_MIG.getUMax())))
               .andExpect(jsonPath("$.tigRange.imin", is(RANGE_TIG.getIMin())))
               .andExpect(jsonPath("$.tigRange.imax", is(RANGE_TIG.getIMax())))
               .andExpect(jsonPath("$.tigRange.umin", is(RANGE_TIG.getUMin())))
               .andExpect(jsonPath("$.tigRange.umax", is(RANGE_TIG.getUMax())))
               .andExpect(jsonPath("$.mmaRange.imin", is(RANGE_MMA.getIMin())))
               .andExpect(jsonPath("$.mmaRange.imax", is(RANGE_MMA.getIMax())))
               .andExpect(jsonPath("$.mmaRange.umin", is(RANGE_MMA.getUMin())))
               .andExpect(jsonPath("$.mmaRange.umax", is(RANGE_MMA.getUMax())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(MODEL_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/models/1")))
               .andExpect(jsonPath("$._links.models.href", is("http://localhost/api/models")))
               .andExpect(jsonPath("$._links.brands.href", is("http://localhost/api/models/1/brands")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/models/1/machines")))
               .andReturn();
        verify(machineService, times(1)).findModelByMachineId(1L);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void getCustomerShouldFetchHalDocument() throws Exception {
        given(machineService.findCustomerByMachineId(1L)).willReturn(CUSTOMER_1);

        mockMvc.perform(get("/api/machines/1/customers")
                .contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
               .andExpect(jsonPath("$.id", is(CUSTOMER_1.getId().intValue())))
               .andExpect(jsonPath("$.shortName", is(CUSTOMER_1.getShortName())))
               .andExpect(jsonPath("$.fullName", is(CUSTOMER_1.getFullName())))
               .andExpect(jsonPath("$.city", is(CUSTOMER_1.getCity())))
               .andExpect(jsonPath("$.zip", is(CUSTOMER_1.getZip())))
               .andExpect(jsonPath("$.street", is(CUSTOMER_1.getStreet())))
               .andExpect(jsonPath("$.email", is(CUSTOMER_1.getEmail())))
               .andExpect(jsonPath("$.nip", is(CUSTOMER_1.getNip())))
               .andExpect(jsonPath("$.creationDate", is(notNullValue())))
               .andExpect(jsonPath("$.modificationDate", is(notNullValue())))
               .andExpect(jsonPath("$.versionId", is(CUSTOMER_1.getVersionId().intValue())))
               .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/customers/1")))
               .andExpect(jsonPath("$._links.machines.href", is("http://localhost/api/customers/1/machines")))
               .andReturn();
        verify(machineService, times(1)).findModelByMachineId(1L);
        verifyNoMoreInteractions(machineService);
    }

    @Test
    public void getValidationsShouldFetchHalDocument() throws Exception {
        assert false;
    }
}