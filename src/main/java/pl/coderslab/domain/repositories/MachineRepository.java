package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.entities.Customer;
import pl.coderslab.domain.entities.Machine;
import pl.coderslab.domain.entities.WelderModel;

import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    List<Machine> findMachinesByWelderModelId(Long modelId);

    List<Machine> findByCustomerId(Long customerId);

    @Query("SELECT m FROM Machine m JOIN m.validProtocols v WHERE v.id = ?1")
    Machine findByValidProtocolId(Long id);
}
