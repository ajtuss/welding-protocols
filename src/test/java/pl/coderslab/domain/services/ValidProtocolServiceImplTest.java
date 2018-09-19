package pl.coderslab.domain.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.MeasureDTO;
import pl.coderslab.domain.dto.ValidProtocolDTO;
import pl.coderslab.domain.entities.PowerType;
import pl.coderslab.domain.exceptions.ValidProtocolNotFoundException;
import pl.coderslab.domain.repositories.ValidProtocolRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@Import({ValidProtocolServiceImpl.class, ModelMapper.class})
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


    @Test
    public void expectedTrueAfterSaveProtocol() {

    }

    @Test
    public void expectedTrueAfterFindById() {
        ValidProtocolDTO found = protocolService.findById(1L);

        assertThat(found, is(PROTOCOL_1_DB));
    }

    @Test
    public void findAll() {
        List<ValidProtocolDTO> found = protocolService.findAll();

        assertThat(found, containsInAnyOrder(PROTOCOL_1_DB, PROTOCOL_2_DB));
    }


    @Test
    public void update() {
    }

    @Test
    public void expectedTrueAfterRemove() {
        protocolService.remove(PROTOCOL_1_DB.getId());
    }

    @Test(expected = ValidProtocolNotFoundException.class)
    public void expectedExceptionAfterRemoveAndFind() {
        protocolService.remove(PROTOCOL_1_DB.getId());
        protocolService.findById(PROTOCOL_1_DB.getId());
    }

    @Test
    public void findAllMeasures() {
        List<MeasureDTO> found = protocolService.findAllMeasures(PROTOCOL_1_DB.getId());

        assertThat(found, contains(MEASURE_1_DB, MEASURE_2_DB, MEASURE_3_DB, MEASURE_4_DB, MEASURE_5_DB));
    }

    @Test
    public void findMachineByValidProtocolId() {
    }
}