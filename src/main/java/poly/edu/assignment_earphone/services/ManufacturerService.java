package poly.edu.assignment_earphone.services;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.assignment_earphone.models.Manufacturer;
import poly.edu.assignment_earphone.models.TypeGender;
import poly.edu.assignment_earphone.models.TypeRole;
import poly.edu.assignment_earphone.models.Users;

import java.util.Date;
import java.util.List;

public interface ManufacturerService {
    Manufacturer addManufacturer(Manufacturer manufacturer, MultipartFile image);

    Manufacturer updateManufacturer(Manufacturer manufacturer);

    List<Manufacturer> getAllManufacturer();

    List<Manufacturer> getManufacturerByName(String name);

    Manufacturer getManufacturer(Long id);

    Manufacturer deleteManufacturer(Long id);

    void deleteAllManufacturer(Long[] id);

    Page<Manufacturer> findPaginated(int pageNo, int pageSize);
}
