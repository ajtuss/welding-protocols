package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.Measure;

import java.util.List;

public interface MeasureRepository extends JpaRepository<Measure, Long> {

    @Query("SELECT m FROM Measure m WHERE m.validProtocol.id = ?1 ORDER BY m.iAdjust ASC")
    List<Measure> findByValidProtocolId(Long id);
}