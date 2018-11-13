package pl.coderslab.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.service.dto.MachineDTO;
import pl.coderslab.service.dto.MeasureDTO;
import pl.coderslab.service.dto.ValidProtocolDTO;
import pl.coderslab.domain.PowerType;
import pl.coderslab.web.errors.ValidProtocolNotFoundException;
import pl.coderslab.repository.ValidProtocolRepository;
import pl.coderslab.service.impl.MeasureServiceImpl;
import pl.coderslab.service.impl.ValidProtocolServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@Import({ValidProtocolServiceImpl.class, ModelMapper.class, MeasureServiceImpl.class})
@Sql(value = "/data-valid.sql")
public class ValidProtocolServiceImplTest {


    @Autowired
    private ValidProtocolService protocolService;

    @Autowired
    private ValidProtocolRepository protocolRepository;


    private static final ValidProtocolDTO PROTOCOL_1 = ValidProtocolDTO.builder()
                                                                       .machineId(1L)
                                                                       .type(PowerType.MMA)
                                                                       .build();

    private static final ValidProtocolDTO PROTOCOL_2 = ValidProtocolDTO.builder()
                                                                       .machineId(1L)
                                                                       .type(PowerType.TIG)
                                                                       .build();

    private static final ValidProtocolDTO PROTOCOL_1_DB = ValidProtocolDTO.builder()
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

    private static final ValidProtocolDTO PROTOCOL_2_DB = ValidProtocolDTO.builder()
                                                                          .id(2L)
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
                                                                          .type(PowerType.TIG)
                                                                          .machineId(1L)
                                                                          .build();

    private static final MeasureDTO MEASURE_1_DB = MeasureDTO.builder()
                                                             .id(1L)
                                                             .creationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                             .modificationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                             .versionId(1L)
                                                             .iAdjust(BigDecimal.valueOf(5))
                                                             .uAdjust(BigDecimal.valueOf(10.2))
                                                             .validProtocolId(1L)
                                                             .build();
    private static final MeasureDTO MEASURE_2_DB = MeasureDTO.builder()
                                                             .id(2L)
                                                             .creationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                             .modificationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                             .versionId(1L)
                                                             .iAdjust(BigDecimal.valueOf(79))
                                                             .uAdjust(BigDecimal.valueOf(13.2))
                                                             .validProtocolId(1L)
                                                             .build();
    private static final MeasureDTO MEASURE_3_DB = MeasureDTO.builder()
                                                             .id(3L)
                                                             .creationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                             .modificationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                             .versionId(1L)
                                                             .iAdjust(BigDecimal.valueOf(153))
                                                             .uAdjust(BigDecimal.valueOf(16.1))
                                                             .validProtocolId(1L)
                                                             .build();
    private static final MeasureDTO MEASURE_4_DB = MeasureDTO.builder()
                                                             .id(4L)
                                                             .creationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                             .modificationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                             .versionId(1L)
                                                             .iAdjust(BigDecimal.valueOf(226))
                                                             .uAdjust(BigDecimal.valueOf(19.1))
                                                             .validProtocolId(1L)
                                                             .build();
    private static final MeasureDTO MEASURE_5_DB = MeasureDTO.builder()
                                                             .id(5L)
                                                             .creationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                             .modificationDate(LocalDateTime.parse("2018-09-19T09:24:58"))
                                                             .versionId(1L)
                                                             .iAdjust(BigDecimal.valueOf(300))
                                                             .uAdjust(BigDecimal.valueOf(22.0))
                                                             .validProtocolId(1L)
                                                             .build();

    private static final MachineDTO MACHINE_1_DB = MachineDTO.builder()
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
    public void expectedTrueAfterFindById() {
        Optional<ValidProtocolDTO> found = protocolService.findById(1L);

        assertTrue(found.isPresent());
        assertThat(found.get(), is(PROTOCOL_1_DB));
    }

    @Test
    public void findAll() {
        Page<ValidProtocolDTO> found = protocolService.findAll(new PageRequest(0, 20));

        assertThat(found, hasItems(PROTOCOL_1_DB, PROTOCOL_2_DB));
    }


    @Test
    public void update() {
    }

    @Test
    public void expectedTrueAfterRemove() {
        protocolService.remove(PROTOCOL_1_DB.getId());
    }

    @Test
    public void expectedTrueAfterFindAllMeasures() {
        Page<MeasureDTO> found = protocolService.findAllMeasures(PROTOCOL_1_DB.getId(), new PageRequest(0, 20));

        assertThat(found, hasItems(MEASURE_1_DB, MEASURE_2_DB, MEASURE_3_DB, MEASURE_4_DB, MEASURE_5_DB));
    }

}