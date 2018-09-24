package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.entities.DBFile;


public interface DBFileRepository extends JpaRepository<DBFile, String> {
}
