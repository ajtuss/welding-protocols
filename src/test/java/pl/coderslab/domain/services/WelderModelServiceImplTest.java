package pl.coderslab.domain.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.repositories.BrandRepository;
import pl.coderslab.domain.repositories.WelderModelRepository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

    private final static WelderModelDTO MODEL_CREATION_1 = new WelderModelDTO("Mastertig 3000",
            1L, false, false, false, false, false, false, null, null, null);
    private final static WelderModelDTO MODEL_CREATION_2 = new WelderModelDTO("Mastertig 4000",
            1L, false, false, false, false, false, false, null, null, null);

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
    public void update() {
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