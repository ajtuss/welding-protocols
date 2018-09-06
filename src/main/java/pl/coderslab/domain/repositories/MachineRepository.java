package pl.coderslab.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.entities.Customer;
import pl.coderslab.domain.entities.Machine;
import pl.coderslab.domain.entities.WelderModel;

import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {
    @Query("SELECT DISTINCT c FROM Machine m JOIN m.customer c ORDER BY c.shortName ASC")
    List<Customer> findAllCustomers();

    @Query("SELECT DISTINCT w FROM Machine m JOIN m.welderModel w WHERE m.customer.id = ?1 AND w.brand.id = ?2 ORDER BY w.name ASC")
    List<WelderModel> findAllModels(Long customerId, Long brandId);

    @Query("SELECT DISTINCT b FROM Machine m JOIN m.welderModel.brand b ORDER BY b.name ASC")
    List<Brand> findAllBrands();

    @Query("SELECT DISTINCT b FROM Machine m JOIN m.welderModel.brand b WHERE m.customer.id = ?1 ORDER BY b.name ASC")
    List<Brand> findAllBrandsWhereCustomer(Long customerId);
}
