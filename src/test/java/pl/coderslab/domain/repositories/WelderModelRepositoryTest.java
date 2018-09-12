package pl.coderslab.domain.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.entities.Range;
import pl.coderslab.domain.entities.WelderModel;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WelderModelRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private WelderModelRepository modelRepository;

    @Autowired
    private RangeRepository rangeRepository;

    private WelderModel model;
    private Range migRange;
    private Range mmaRange;
    private Range tigRange;

    @Before
    public void setUp() {
        model = new WelderModel();
        model.setName("Test model");

        migRange = new Range(null, 1.0, 100.0, 10.0, 100.0);
        mmaRange = new Range(null, 2.0, 200.0, 20.0, 200.0);
        tigRange = new Range(null, 3.0, 300.0, 30.0, 300.0);

        model.setMigRange(migRange);
        model.setMmaRange(mmaRange);
        model.setTigRange(tigRange);
        entityManager.persist(model);
        entityManager.flush();
    }

    @Test
    public void expectedTrueAfterGetModel() {
        WelderModel found = modelRepository.getOne(1L);
        assertEquals(model, found);
        assertEquals(migRange, found.getMigRange());
        assertEquals(mmaRange, found.getMmaRange());
        assertEquals(tigRange, found.getTigRange());
    }

    @Test
    public void expectedTrueAfterRemoveRange() {
        WelderModel model = modelRepository.getOne(1L);
        Long migRangeId = model.getMigRange().getId();
        model.setMigRange(null);
        modelRepository.saveAndFlush(model);

        Range found = rangeRepository.findById(migRangeId).orElse(null);

        assertNull(found);
    }

}