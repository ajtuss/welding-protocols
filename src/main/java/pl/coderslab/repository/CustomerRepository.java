package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByShortName(String shortName);

    @Query("SELECT c FROM Customer c JOIN c.machines m WHERE m.id = ?1")
    Customer findByMachineId(long machineId);

    @Query("SELECT c FROM Customer c JOIN c.machines m JOIN m.validProtocols v WHERE v.id = ?1")
    Customer findByValidProtocolId(Long id);
}
