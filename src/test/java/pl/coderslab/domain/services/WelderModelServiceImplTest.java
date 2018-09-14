package pl.coderslab.domain.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.dto.RangeDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.repositories.BrandRepository;
import pl.coderslab.domain.repositories.WelderModelRepository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({WelderModelServiceImpl.class, BrandServiceImpl.class, ModelMapper.class})
@Sql(value = "/data-test.sql")
public class WelderModelServiceImplTest {

    @Autowired
    private WelderModelService modelService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private WelderModelRepository modelRepository;

    @Autowired
    private BrandRepository brandRepository;

    private static final RangeDTO RANGE_MIG = new RangeDTO(1., 100., 10., 100.);
    private static final RangeDTO RANGE_MMA = new RangeDTO(2., 200., 20., 200.);
    private static final RangeDTO RANGE_TIG = new RangeDTO(3., 300., 30., 300.);

    private final static WelderModelDTO MODEL_CREATION_1 = new WelderModelDTO("Mastertig 3000",
            1L, true, true, true, true, true, true, RANGE_MIG, RANGE_MMA, RANGE_TIG);
    private final static WelderModelDTO MODEL_CREATION_2 = new WelderModelDTO("Mastertig 4000",
            1L, false, false, false, false, false, false, RANGE_MIG, RANGE_MMA, RANGE_TIG);

    @Test
    public void findById() {

        brandRepository.findById(1L);
    }

    @Test
    public void findAll() {
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
    public void expectedNullRangeAfterSaveModelTypeIsFalse(){
        WelderModelDTO saved = modelService.save(MODEL_CREATION_2);
        System.out.println(saved);
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

    @Test
    public void remove() {
    }

    @Test
    public void findBrandByModelId() {
    }

    @Test
    public void findAllMachinesByModelId() {
    }
}