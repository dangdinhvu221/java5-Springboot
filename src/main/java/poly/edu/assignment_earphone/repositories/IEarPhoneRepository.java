package poly.edu.assignment_earphone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment_earphone.models.EarPhone;
import poly.edu.assignment_earphone.models.Manufacturer;

import java.util.List;

public interface IEarPhoneRepository extends JpaRepository<EarPhone, Long> {
    List<EarPhone> findByNameLike(String name);
    List<EarPhone> findByManufacturerByManufacturerId(Manufacturer manufacturerByManufacturerId);

}
