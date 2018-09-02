package pl.coderslab.domain.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.Parameter;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
}
