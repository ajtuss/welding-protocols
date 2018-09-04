package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.ValidProtocol;

public interface ValidProtocolRepository extends JpaRepository<ValidProtocol, Long> {
}
