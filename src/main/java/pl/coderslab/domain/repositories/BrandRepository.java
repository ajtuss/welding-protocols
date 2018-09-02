package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
