package pl.coderslab.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.Role;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;


    private Role role1;

    @Before
    public void setUp() {
        role1 = new Role();
        role1.setRole("role name");
        entityManager.persist(role1);
        entityManager.flush();
    }

    @Test
    public void expectedTrueAfterFindByRole() {
        Role found = roleRepository.findByRole(role1.getRole());
        assertEquals(role1, found);
    }
}