package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.entities.Measure;

import java.util.List;

public interface MeasureRepository extends JpaRepository<Measure, Long> {

    @Query("SELECT m FROM Measure m WHERE m.validProtocol.id = ?1")
    List<Measure> findByValidProtocolId(Long id);
}
