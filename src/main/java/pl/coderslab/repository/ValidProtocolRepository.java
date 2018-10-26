package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.ValidProtocol;

public interface ValidProtocolRepository extends JpaRepository<ValidProtocol, Long> {

    @Query("SELECT v FROM ValidProtocol v JOIN v.machine m WHERE m.id = ?1")
    Page<ValidProtocol> findByMachineId(long id, Pageable pageable);
}
