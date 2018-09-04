package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

}