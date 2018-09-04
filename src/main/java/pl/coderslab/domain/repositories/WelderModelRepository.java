package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.WelderModel;

import java.util.List;

public interface WelderModelRepository extends JpaRepository<WelderModel, Long> {

    List<WelderModel> findAllByBrandId(Long id);
}
