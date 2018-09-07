package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByShortName(String shortName);
}
