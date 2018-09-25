package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.entities.ValidProtocol;

import java.util.List;

public interface ValidProtocolRepository extends JpaRepository<ValidProtocol, Long> {

    @Query("SELECT v FROM ValidProtocol v JOIN v.machine m WHERE m.id = ?1")
    List<ValidProtocol> findByMachineId(long id);
}
