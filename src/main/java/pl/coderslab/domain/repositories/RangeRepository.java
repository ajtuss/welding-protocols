package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.Range;

public interface RangeRepository extends JpaRepository<Range, Long> {
}
