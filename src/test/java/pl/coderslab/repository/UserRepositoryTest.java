package pl.coderslab.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.Role;
import pl.coderslab.domain.User;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final String PASSWORD = "password";
    private User user1;
    private Role role1;
    private Role role2;

    @Before
    public void setUp() {
        user1 = new User();
        user1.setUsername("User Name");
        user1.setEmail("user@user.com");
        user1.setPassword(passwordEncoder.encode(PASSWORD));
        user1.setActive(true);

        role1 = new Role();
        role1.setRole("role1");
        role2 = new Role();
        role2.setRole("role2");

        user1.addRole(role1);
        user1.addRole(role2);

        entityManager.persist(user1);
        entityManager.persist(role1);
        entityManager.persist(role2);
        entityManager.flush();
    }

    @Test
    public void expectedTrueAfterFindByEmailIgnoreCase() {
        User found = userRepository.findByEmailIgnoreCase(user1.getEmail().toUpperCase());
        assertEquals(user1, found);
    }

    @Test
    public void expectedTrueAfterFindByUsernameIgnoreCase() {
        User found = userRepository.findByUsernameIgnoreCase(user1.getUsername().toUpperCase());
        assertEquals(user1, found);
    }

    @Test
    public void expectedTrueAfterMatchPassword() {
        User found = userRepository.findByUsernameIgnoreCase(user1.getUsername());
        assertTrue(passwordEncoder.matches(PASSWORD, found.getPassword()));
    }
}