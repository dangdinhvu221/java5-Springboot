package poly.edu.assignment_earphone.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.assignment_earphone.models.Manufacturer;
import poly.edu.assignment_earphone.repositories.IManufacturerRepository;
import poly.edu.assignment_earphone.services.ManufacturerService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired
    private IManufacturerRepository manufacturerRepository;

    @Override
    public Manufacturer addManufacturer(Manufacturer manufacturer, MultipartFile file) {
        manufacturer.setId(null);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            manufacturer.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer manufacturer, MultipartFile file) {
        Long id = manufacturer.getId();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (id != null) {
            if (fileName.isEmpty()) {
                manufacturer.setImage(this.manufacturerRepository.findById(id).get().getImage());
            }
            try {
                manufacturer.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Optional<Manufacturer> updatedManufacturer = this.manufacturerRepository.findById(id);
            if (updatedManufacturer.isPresent()) {
                manufacturer.setId(id);
                return this.manufacturerRepository.save(manufacturer);
            }
        }
        return null;
    }

    @Override
    public List<Manufacturer> getAllManufacturer() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public List<Manufacturer> getManufacturerByName(String name) {
        return this.manufacturerRepository.findByNameManufacturerLike(name);
    }

    @Override
    public Manufacturer getManufacturer(Long id) {
        return this.manufacturerRepository.findById(id).get();
    }

    @Override
    public Manufacturer deleteManufacturer(Long id) {
        if (id != null) {
            Optional<Manufacturer> manufacturer = this.manufacturerRepository.findById(id);
            if (manufacturer.isPresent()) {
                this.manufacturerRepository.deleteById(id);
                return manufacturer.get();
            }
        }
        return null;
    }

    @Override
    public void deleteAllManufacturer(Long[] id) {
        this.manufacturerRepository.deleteAllByIdInBatch(Arrays.asList(id));
    }

    @Override
    public Page<Manufacturer> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.manufacturerRepository.findAll(pageable);
    }
}
