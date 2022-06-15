package poly.edu.assignment_earphone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import poly.edu.assignment_earphone.models.Manufacturer;

import javax.transaction.Transactional;
import java.util.List;

public interface IManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    List<Manufacturer> findByNameManufacturerLike(String nameManufacturer);
}
