package pl.coderslab.domain.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.exceptions.CustomerNotFoundException;
import pl.coderslab.domain.repositories.CustomerRepository;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({CustomerServiceImpl.class, ModelMapper.class})
@Sql(value = "/data-brands.sql")
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    private final static CustomerDTO CUSTOMER_1 = CustomerDTO.builder()
                                                             .shortName("COMPANY")
                                                             .fullName("COMPANY sp. z O.O.")
                                                             .city("Katowice")
                                                             .zip("40-584")
                                                             .street("Kościuszki 12")
                                                             .email("email@email.com")
                                                             .nip("1250249764")
                                                             .build();
    private final static CustomerDTO CUSTOMER_2 = CustomerDTO.builder()
                                                             .shortName("COMPANY 2")
                                                             .fullName("COMPANY 2 sp. z O.O.")
                                                             .city("Kraków")
                                                             .zip("40-000")
                                                             .street("Kościuszki 15")
                                                             .email("email@company2.com")
                                                             .nip("1251649566")
                                                             .build();

    @Test
    public void expectedTrueAfterSave(){
        CustomerDTO saved = customerService.save(CUSTOMER_1);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertThat(saved.getShortName(), is(CUSTOMER_1.getShortName()));
        assertThat(saved.getFullName(), is(CUSTOMER_1.getFullName()));
        assertThat(saved.getCity(), is(CUSTOMER_1.getCity()));
        assertThat(saved.getZip(), is(CUSTOMER_1.getZip()));
        assertThat(saved.getStreet(), is(CUSTOMER_1.getStreet()));
        assertThat(saved.getEmail(), is(CUSTOMER_1.getEmail()));
        assertThat(saved.getNip(), is(CUSTOMER_1.getNip()));
        assertNotNull(saved.getModificationDate());
        assertNotNull(saved.getCreationDate());
        assertNotNull(saved.getVersionId());
    }

    @Test
    public void expectedTrueAfterFindById(){
        CustomerDTO saved = customerService.save(CUSTOMER_1);

        CustomerDTO found = customerService.findById(saved.getId());

        assertThat(found, is(saved));
    }

    @Test(expected = CustomerNotFoundException.class)
    public void expectedExceptionAfterFindNotExistingCustomer(){
        customerService.findById(Long.MAX_VALUE);
    }

    @Test
    public void expectedTrueAfterFindAll(){
        CustomerDTO customer1 = customerService.save(CUSTOMER_1);
        CustomerDTO customer2 = customerService.save(CUSTOMER_2);

        List<CustomerDTO> customers = customerService.findAll();

        assertThat(customers, containsInAnyOrder(customer1, customer2));
        assertThat(customers, hasSize(2));
    }

    @Test
    public void expectedTrueAfterUpdate(){
        CustomerDTO saved = customerService.save(CUSTOMER_1);
        saved.setShortName("NEW CUST");

        CustomerDTO update = customerService.update(saved);

        assertNotNull(update);
        assertNotNull(update.getId());
        assertThat(update.getShortName(), is(saved.getShortName()));
        assertThat(update.getFullName(), is(saved.getFullName()));
        assertThat(update.getCity(), is(saved.getCity()));
        assertThat(update.getZip(), is(saved.getZip()));
        assertThat(update.getStreet(), is(saved.getStreet()));
        assertThat(update.getEmail(), is(saved.getEmail()));
        assertThat(update.getNip(), is(saved.getNip()));
        assertTrue(update.getModificationDate().isAfter(saved.getModificationDate()));
        assertThat(update.getCreationDate(), is(saved.getCreationDate()));
        assertTrue(update.getVersionId()> saved.getVersionId());
    }

    @Test
    public void expectedTrueAfterRemoveCustomer(){
        CustomerDTO save = customerService.save(CUSTOMER_1);
        customerService.remove(save.getId());
    }

    @Test(expected = CustomerNotFoundException.class)
    public void expectedExceptionAfterRemoveAndFind(){
        CustomerDTO save = customerService.save(CUSTOMER_1);
        customerService.remove(save.getId());
        customerService.findById(save.getId());
    }



}