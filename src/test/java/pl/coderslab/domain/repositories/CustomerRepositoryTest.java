package pl.coderslab.domain.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.entities.Customer;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;


    private Customer customer1;

    @Before
    public void setUp(){
        customer1 = new Customer();
        customer1.setShortName("short name");
        customer1.setFullName("full name");
        customer1.setZip("00-000");
        customer1.setCity("city");
        customer1.setStreet("street 23");
        customer1.setEmail("email@email.com");
        customer1.setNip("1250249764");
        entityManager.persist(customer1);
        entityManager.flush();
    }

    @Test
    public void expectedTrueAfterFindByShortName(){
        Customer found = customerRepository.findByShortName(customer1.getShortName());
        assertEquals(customer1, found);
    }

}