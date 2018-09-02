package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.WelderModel;

public interface WelderModelRepository extends JpaRepository<WelderModel, Long> {
}
