package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.Range;

public interface RangeRepository extends JpaRepository<Range, Long> {
}
