package pl.coderslab.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.service.dto.MeasureDTO;
import pl.coderslab.repository.ValidProtocolRepository;
import pl.coderslab.service.impl.MeasureServiceImpl;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({MeasureServiceImpl.class, ModelMapper.class})
@Sql(value = "/data-valid.sql")
public class MeasureServiceImplTest {


    @Autowired
    private MeasureService measureService;

    @Autowired
    private ValidProtocolRepository protocolRepository;


    //Range for validation id 1 = 10-250A, 20.5-30V)
    private static final MeasureDTO MEASURE_1 = MeasureDTO.builder().iAdjust(BigDecimal.valueOf(10))
                                                          .validProtocolId(1L).build();
    private static final MeasureDTO MEASURE_2 = MeasureDTO.builder().iAdjust(BigDecimal.valueOf(100))
                                                          .validProtocolId(1L).build();
    private static final MeasureDTO MEASURE_3 = MeasureDTO.builder().iAdjust(BigDecimal.valueOf(250))
                                                          .validProtocolId(1L).build();

    private static final MeasureDTO MEASURE_FAIL_1 = MeasureDTO.builder().iAdjust(BigDecimal.valueOf(9))
                                                               .validProtocolId(1L).build();
    private static final MeasureDTO MEASURE_FAIL_2 = MeasureDTO.builder().iAdjust(BigDecimal.valueOf(251))
                                                               .validProtocolId(1L).build();

    @Test
    public void expectedTrueAfterUpdateMeasure() {
        MeasureDTO save = measureService.save(MEASURE_1);
    }

}