package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.Machine;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    Page<Machine> findMachinesByWelderModelId(Long modelId, Pageable pageable);

    Page<Machine> findByCustomerId(Long customerId, Pageable pageable);

    @Query("SELECT m FROM Machine m JOIN m.validProtocols v WHERE v.id = ?1")
    Machine findByValidProtocolId(Long id);
}
