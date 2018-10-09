package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	 public abstract User findByEmailIgnoreCase(String email);
	 public abstract User findByUsernameIgnoreCase(String username);

}