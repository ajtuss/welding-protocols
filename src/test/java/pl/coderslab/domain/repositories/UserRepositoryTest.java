package pl.coderslab.domain.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.config.Application;
import pl.coderslab.domain.entities.User;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User user1;
    private final String PASSWORD = "password";

    @Before
    public void setUp() {
        user1 = new User();
        user1.setUsername("User Name");
        user1.setEmail("user@user.com");
        user1.setPassword(passwordEncoder.encode(PASSWORD));
        user1.setActive(true);
        entityManager.persist(user1);
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
    public void expectedTrueAfterMatchPassword(){
        User found = userRepository.findByUsernameIgnoreCase(user1.getUsername());
        assertTrue(passwordEncoder.matches(PASSWORD, found.getPassword()));
    }
}