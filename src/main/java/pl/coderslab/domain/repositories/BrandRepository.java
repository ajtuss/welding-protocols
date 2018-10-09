package pl.coderslab.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.entities.Brand;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Page<Brand> findByNameStartingWithIgnoreCase(String name, Pageable pageable);

    @Query("SELECT b FROM Brand b JOIN b.welderModels w WHERE w.id = ?1")
    Optional<Brand> findByModelId(Long id);
}
