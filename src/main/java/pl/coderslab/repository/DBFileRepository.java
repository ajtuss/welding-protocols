package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.domain.DBFile;


public interface DBFileRepository extends JpaRepository<DBFile, String> {
}
