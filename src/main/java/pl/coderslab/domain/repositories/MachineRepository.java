package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.Machine;

public interface MachineRepository extends JpaRepository<Machine, Long> {
}
