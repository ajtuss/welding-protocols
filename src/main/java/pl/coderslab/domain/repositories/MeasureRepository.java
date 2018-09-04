package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.Measure;

public interface MeasureRepository extends JpaRepository<Measure, Long> {
}
