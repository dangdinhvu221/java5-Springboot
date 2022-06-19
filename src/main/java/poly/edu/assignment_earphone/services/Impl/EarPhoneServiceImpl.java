package poly.edu.assignment_earphone.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.assignment_earphone.models.EarPhone;
import poly.edu.assignment_earphone.models.Manufacturer;
import poly.edu.assignment_earphone.models.typeEnum.TypeCondition;
import poly.edu.assignment_earphone.models.typeEnum.TypeEarPhone;
import poly.edu.assignment_earphone.repositories.IEarPhoneRepository;
import poly.edu.assignment_earphone.repositories.IManufacturerRepository;
import poly.edu.assignment_earphone.services.EarPhoneService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class EarPhoneServiceImpl implements EarPhoneService {

    @Autowired
    private IEarPhoneRepository earPhoneRepository;
    @Autowired
    private IManufacturerRepository manufacturerRepository;

    @Override
    public EarPhone addEarPhone(EarPhone earPhone) {
        return this.earPhoneRepository.save(earPhone);
    }

    @Override
    public EarPhone updateEarPhone(EarPhone earPhone) {
        Long id = earPhone.getId();
        try {
            if (id != null) {
                Optional<EarPhone> e = this.earPhoneRepository.findById(id);
                if (e.isPresent()) {
                    earPhone.setId(id);
                    this.earPhoneRepository.save(earPhone);
                }
            }
            return earPhone;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<EarPhone> getAllEarPhones() {
        return this.earPhoneRepository.findAll();
    }

    @Override
    public EarPhone getEarPhone(Long id) {
        return this.earPhoneRepository.findById(id).get();
    }

    @Override
    public List<EarPhone> getTypeEarPhone(Integer typeEarPhone) {
        return null;
    }

    @Override
    public List<EarPhone> findByNameEarPhone(String name) {
        return this.earPhoneRepository.findByNameLike("%" + name + "%");
    }

    @Override
    public EarPhone deleteEarPhone(Long id) {
        if (id != null) {
            Optional<EarPhone> e = this.earPhoneRepository.findById(id);
            if (e.isPresent()) {
                this.earPhoneRepository.deleteById(id);
                return e.get();
            }
        }
        return null;
    }

    @Override
    public void deleteAllEarPhone(Long[] id) {
        this.earPhoneRepository.deleteAllByIdInBatch(Arrays.asList(id));
    }

    @Override
    public Page<EarPhone> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.earPhoneRepository.findAll(pageable);
    }

    @Override
    public void saveEarPhoneToDb(String name, String title, String warranty, Integer frequency, String color, BigDecimal price, String impedance, MultipartFile file, String description, Date created, Integer quantity, TypeEarPhone typeEarPhone, TypeCondition typeCondition, Manufacturer manufacturerByManufacturerId) {
        EarPhone earPhone = new EarPhone();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            earPhone.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerByManufacturerId.getId()).get();
        earPhone.setName(name);
        earPhone.setTitle(title);
        earPhone.setColor(color);
        earPhone.setPrice(price);
        earPhone.setQuantity(quantity);
        earPhone.setWarranty(warranty);
        earPhone.setFrequency(frequency);
        earPhone.setImpedance(impedance);
        earPhone.setDescription(description);
        earPhone.setDescription(description);
        earPhone.setTypeEarPhone(typeEarPhone);
        earPhone.setTypeCondition(typeCondition);
        earPhone.setManufacturerByManufacturerId(manufacturer);
        earPhone.setCreated(new Date());
        earPhoneRepository.save(earPhone);
    }

    @Override
    public void updateEarPhoneToDb(Long id, String name, String title, String warranty, Integer frequency, String color, BigDecimal price, String impedance, MultipartFile file, String description, Date created, Integer quantity, TypeEarPhone typeEarPhone, TypeCondition typeCondition, Manufacturer manufacturerByManufacturerId) {
        EarPhone earPhone = new EarPhone();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.isEmpty()) {
            earPhone.setImage(this.earPhoneRepository.findById(id).get().getImage());
        }else{
            try {
                earPhone.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerByManufacturerId.getId()).get();
        earPhone.setId(id);
        earPhone.setName(name);
        earPhone.setTitle(title);
        earPhone.setColor(color);
        earPhone.setPrice(price);
        earPhone.setQuantity(quantity);
        earPhone.setWarranty(warranty);
        earPhone.setFrequency(frequency);
        earPhone.setImpedance(impedance);
        earPhone.setDescription(description);
        earPhone.setDescription(description);
        earPhone.setTypeEarPhone(typeEarPhone);
        earPhone.setTypeCondition(typeCondition);
        earPhone.setManufacturerByManufacturerId(manufacturer);
        earPhone.setCreated(created);
        earPhoneRepository.save(earPhone);
    }


}
