package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.WelderModel;

import java.util.List;

public interface WelderModelRepository extends JpaRepository<WelderModel, Long> {

    Page<WelderModel> findAllByBrandId(long id, Pageable pageable);

    @Query("SELECT w FROM WelderModel w JOIN w.machines m WHERE m.id = ?1")
    WelderModel findByModelId(long id);

    @Query("SELECT w FROM WelderModel w JOIN w.machines m JOIN m.validProtocols v WHERE v.id = ?1")
    WelderModel findByValidProtocolId(long id);
}
