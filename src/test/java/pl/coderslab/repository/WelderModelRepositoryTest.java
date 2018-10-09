package pl.coderslab.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.Range;
import pl.coderslab.domain.WelderModel;

import javax.persistence.EntityManager;

import java.math.BigDecimal;

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

        migRange = new Range(null, BigDecimal.valueOf(1.0), BigDecimal.valueOf(100.0), BigDecimal.valueOf(10.0), BigDecimal.valueOf(100.0));
        mmaRange = new Range(null, BigDecimal.valueOf(2.0), BigDecimal.valueOf(200.0), BigDecimal.valueOf(20.0), BigDecimal.valueOf(200.0));
        tigRange = new Range(null, BigDecimal.valueOf(3.0), BigDecimal.valueOf(300.0), BigDecimal.valueOf(30.0), BigDecimal.valueOf(300.0));

        model.setMigRange(migRange);
        model.setMmaRange(mmaRange);
        model.setTigRange(tigRange);
        entityManager.persist(model);
        entityManager.flush();
    }

    @Test
    public void expectedTrueAfterGetModel() {
        WelderModel found = modelRepository.getOne(model.getId());
        assertEquals(model, found);
        assertEquals(migRange, found.getMigRange());
        assertEquals(mmaRange, found.getMmaRange());
        assertEquals(tigRange, found.getTigRange());
    }

    @Test
    public void expectedTrueAfterRemoveRange() {
        WelderModel welderModel = modelRepository.getOne(model.getId());
        Long migRangeId = welderModel.getMigRange().getId();
        welderModel.setMigRange(null);
        modelRepository.saveAndFlush(welderModel);

        Range found = rangeRepository.findById(migRangeId).orElse(null);

        assertNull(found);
    }

}