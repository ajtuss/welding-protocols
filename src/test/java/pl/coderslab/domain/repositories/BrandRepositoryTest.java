package pl.coderslab.domain.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.entities.Brand;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BrandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BrandRepository brandRepository;

    private Brand brand1;

    @Before
    public void setUp(){
        brand1 = new Brand();
        brand1.setName("Brand name");
        entityManager.persist(brand1);
        entityManager.flush();
    }

    @Test
    public void expectedTrueAfterFindByName(){

//        Brand found = brandRepository.findByName(brand1.getName());
//        assertEquals(found.getName(), brand1.getName() );
    }

}