package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.Measure;

public interface MeasureRepository extends JpaRepository<Measure, Long> {

    @Query("SELECT m FROM Measure m WHERE m.validProtocol.id = ?1 ORDER BY m.iAdjust ASC")
    Page<Measure> findByValidProtocolId(Long id, Pageable pageable);
}
