package pl.coderslab.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.service.dto.CustomerDTO;
import pl.coderslab.web.errors.CustomerNotFoundException;
import pl.coderslab.repository.CustomerRepository;
import pl.coderslab.service.impl.CustomerServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
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

        Optional<CustomerDTO> found = customerService.findById(saved.getId());

        assertTrue(found.isPresent());
        assertThat(found.get(), is(saved));
    }

    @Test
    public void expectedTrueAfterFindAll(){

        CustomerDTO customer1 = customerService.save(CUSTOMER_1);
        CustomerDTO customer2 = customerService.save(CUSTOMER_2);

        Page<CustomerDTO> customers = customerService.findAll(new PageRequest(0, 20));

        assertThat(customers, hasItems(customer1, customer2));
        assertThat(customers.getContent(), hasSize(2));
    }

    @Test
    public void expectedTrueAfterRemoveCustomer(){
        CustomerDTO save = customerService.save(CUSTOMER_1);
        customerService.remove(save.getId());
    }

}