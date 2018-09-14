package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.entities.Brand;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByName(String name);

    @Query("SELECT b FROM Brand b JOIN b.welderModels w WHERE w.id = ?1")
    Optional<Brand> findByModelId(Long id);
}
