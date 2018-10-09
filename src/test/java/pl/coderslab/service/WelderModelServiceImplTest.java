package pl.coderslab.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.dto.RangeDTO;
import pl.coderslab.service.dto.WelderModelDTO;
import pl.coderslab.domain.WelderModel;
import pl.coderslab.web.exceptions.InvalidRequestException;
import pl.coderslab.web.exceptions.WelderModelNotFoundException;
import pl.coderslab.repository.WelderModelRepository;
import pl.coderslab.service.impl.BrandServiceImpl;
import pl.coderslab.service.impl.WelderModelServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({WelderModelServiceImpl.class, BrandServiceImpl.class, ModelMapper.class})
@Sql(value = "/data-brands.sql")
public class WelderModelServiceImplTest {

    @Autowired
    private WelderModelService modelService;

    @Autowired
    private WelderModelRepository modelRepository;

    private static final RangeDTO RANGE_MIG = new RangeDTO(BigDecimal.valueOf(1.0), BigDecimal.valueOf(100.), BigDecimal.valueOf(10.), BigDecimal.valueOf(100.));
    private static final RangeDTO RANGE_MMA = new RangeDTO(BigDecimal.valueOf(2.), BigDecimal.valueOf(200.), BigDecimal.valueOf(20.), BigDecimal.valueOf(200.));
    private static final RangeDTO RANGE_TIG = new RangeDTO(BigDecimal.valueOf(3.), BigDecimal.valueOf(300.), BigDecimal.valueOf(30.), BigDecimal.valueOf(300.));

    private final static WelderModelDTO MODEL_CREATION_1 = new WelderModelDTO("Mastertig 3000",
            1L, true, true, true, true, true, true, RANGE_MIG, RANGE_MMA, RANGE_TIG);
    private final static WelderModelDTO MODEL_CREATION_2 = new WelderModelDTO("Mastertig 4000",
            1L, false, false, false, false, false, false, RANGE_MIG, RANGE_MMA, RANGE_TIG);

    @Test
    public void expectedTrueAfterFindById() {
        WelderModelDTO saved = modelService.save(MODEL_CREATION_1);
        WelderModelDTO found = modelService.findById(saved.getId());
        assertEquals(saved, found);
    }

    @Test(expected = WelderModelNotFoundException.class)
    public void expectedExceptionAfterTryFindNotExistingModel(){
        modelService.findById(Long.MAX_VALUE);
    }

    @Test
    public void expectedTrueAfterFindAll() {
        WelderModelDTO model1 = modelService.save(MODEL_CREATION_1);
        WelderModelDTO model2 = modelService.save(MODEL_CREATION_2);

        List<WelderModelDTO> found = modelService.findAll();

        assertThat(found, containsInAnyOrder(model1, model2));
        assertThat(found, hasSize(2));
    }

    @Test
    public void expectedTrueAfterSave() {
        WelderModelDTO saved = modelService.save(MODEL_CREATION_1);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertThat(saved.getBrandId(), is(MODEL_CREATION_1.getBrandId()));
        assertThat(saved.getMig(), is(MODEL_CREATION_1.getMig()));
        assertThat(saved.getMma(), is(MODEL_CREATION_1.getMma()));
        assertThat(saved.getTig(), is(MODEL_CREATION_1.getTig()));
        assertThat(saved.getStepControl(), is(MODEL_CREATION_1.getStepControl()));
        assertThat(saved.getCurrentMeter(), is(MODEL_CREATION_1.getCurrentMeter()));
        assertThat(saved.getVoltageMeter(), is(MODEL_CREATION_1.getVoltageMeter()));
        assertThat(saved.getMigRange(), is(MODEL_CREATION_1.getMigRange()));
        assertThat(saved.getMmaRange(), is(MODEL_CREATION_1.getMmaRange()));
        assertThat(saved.getTigRange(), is(MODEL_CREATION_1.getTigRange()));
        assertNotNull(saved.getCreationDate());
        assertNotNull(saved.getModificationDate());
        assertNotNull(saved.getVersionId());
    }

    @Test
    public void expectedNullRangeAfterSaveModelTypeIsFalse() {
        WelderModelDTO saved = modelService.save(MODEL_CREATION_2);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertThat(saved.getBrandId(), is(MODEL_CREATION_2.getBrandId()));
        assertThat(saved.getMig(), is(false));
        assertThat(saved.getMma(), is(false));
        assertThat(saved.getTig(), is(false));
        assertThat(saved.getStepControl(), is(MODEL_CREATION_2.getStepControl()));
        assertThat(saved.getCurrentMeter(), is(MODEL_CREATION_2.getCurrentMeter()));
        assertThat(saved.getVoltageMeter(), is(MODEL_CREATION_2.getVoltageMeter()));
        assertNull(saved.getMigRange());
        assertNull(saved.getMmaRange());
        assertNull(saved.getTigRange());
        assertNotNull(saved.getCreationDate());
        assertNotNull(saved.getModificationDate());
        assertNotNull(saved.getVersionId());
    }

    @Test
    public void expectedTrueAfterUpdate() {
        WelderModelDTO saved = modelService.save(MODEL_CREATION_1);
        saved.setName("Mastertig 5000");
        saved.setMig(false);
        saved.setMma(false);
        saved.setTig(false);

        WelderModelDTO updated = modelService.update(saved);

        assertNotNull(updated);
        assertThat(saved.getId(), is(updated.getId()));
        assertThat(saved.getBrandId(), is(updated.getBrandId()));
        assertThat(saved.getMig(), is(updated.getMig()));
        assertThat(saved.getMma(), is(updated.getMma()));
        assertThat(saved.getTig(), is(updated.getTig()));
        assertThat(saved.getStepControl(), is(updated.getStepControl()));
        assertThat(saved.getCurrentMeter(), is(updated.getCurrentMeter()));
        assertThat(saved.getVoltageMeter(), is(updated.getVoltageMeter()));
        assertNull(updated.getMigRange());
        assertNull(updated.getMmaRange());
        assertNull(updated.getTigRange());
        assertThat(saved.getCreationDate(), is(updated.getCreationDate()));
        assertTrue(updated.getModificationDate().isAfter(saved.getModificationDate()));
        assertTrue(updated.getVersionId() > saved.getVersionId());
    }

    @Test(expected = InvalidRequestException.class)
    public void expectedExceptionWhenUpdateWithIdIsNull() {
        WelderModelDTO saved = modelService.save(MODEL_CREATION_1);
        saved.setId(null);
        modelService.update(saved);
    }

    @Test(expected = InvalidRequestException.class)
    public void expectedExceptionWhenUpdateWithVersionIdIsNull() {
        WelderModelDTO saved = modelService.save(MODEL_CREATION_1);
        saved.setVersionId(null);
        modelService.update(saved);
    }

    @Test(expected = InvalidRequestException.class)
    public void expectedTrueAfterUpdateAndCompare() {
        WelderModelDTO saved = modelService.save(MODEL_CREATION_1);
        saved.setVersionId(null);
        modelService.update(saved);
    }


    @Test
    public void expectedTrueAfetrRemove() {
        WelderModelDTO saved = modelService.save(MODEL_CREATION_1);

        modelService.remove(saved.getId());
    }

    @Test
    public void expectedNullAfterRemoveAndFind() {
        WelderModelDTO saved = modelService.save(MODEL_CREATION_1);
        modelService.remove(saved.getId());

        WelderModel model = modelRepository.findById(saved.getId()).orElse(null);
        assertNull(model);
    }


    @Test
    public void expectedTrueAfterFindBrandByModelId() {
        WelderModelDTO saved = modelService.save(MODEL_CREATION_1);
        BrandDTO brandDTO = modelService.findBrandByModelId(saved.getId());
        assertNotNull(brandDTO);
    }


    @Test
    public void findAllMachinesByModelId() {
        //todo
    }
}