package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByShortName(String shortName);

    @Query("SELECT c FROM Customer c JOIN c.machines m WHERE m.id = ?1")
    Customer findByMachineId(long machineId);
}
