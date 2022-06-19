package poly.edu.assignment_earphone.services;


import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.assignment_earphone.models.EarPhone;
import poly.edu.assignment_earphone.models.Manufacturer;
import poly.edu.assignment_earphone.models.typeEnum.TypeCondition;
import poly.edu.assignment_earphone.models.typeEnum.TypeEarPhone;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface EarPhoneService {
    EarPhone addEarPhone(EarPhone earPhone);

    EarPhone updateEarPhone(EarPhone earPhone);

    List<EarPhone> getAllEarPhones();

    EarPhone getEarPhone(Long id);

    List<EarPhone> getTypeEarPhone(Integer typeEarPhone);

    List<EarPhone> findByNameEarPhone(String name);

    EarPhone deleteEarPhone(Long id);

    void deleteAllEarPhone(Long[] id);

    Page<EarPhone> findPaginated(int pageNo, int pageSize);

    void saveEarPhoneToDb(String name, String title, String warranty, Integer frequency, String color, BigDecimal price, String impedance, MultipartFile image, String description, Date created, Integer quantity, TypeEarPhone typeEarPhone, TypeCondition typeCondition, Manufacturer manufacturerByManufacturerId);

    void updateEarPhoneToDb(Long id, String name, String title, String warranty, Integer frequency, String color, BigDecimal price, String impedance, MultipartFile image, String description, Date created, Integer quantity, TypeEarPhone typeEarPhone, TypeCondition typeCondition, Manufacturer manufacturerByManufacturerId);
}
