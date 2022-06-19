package poly.edu.assignment_earphone.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.assignment_earphone.models.Manufacturer;
import poly.edu.assignment_earphone.services.ManufacturerService;
import java.util.Arrays;

@Controller
@RequestMapping("/earPhone/dashAdmin/manageManufacturers")
@PreAuthorize("isAuthenticated() and hasAuthority('ADMIN')")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("")
    public String getAllManufacturers(ModelMap model) {

        return findPaginated(1, model);
    }

    @PostMapping("/addManufacturer")
    public String addManufacturer(@RequestParam("manufacturer") String name,
                                  @RequestParam("image") MultipartFile image
                                  ) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setNameManufacturer(name);
        this.manufacturerService.addManufacturer(manufacturer, image);
        return "redirect:/earPhone/dashAdmin/manageManufacturers";
    }

    @PostMapping("/updateManufacturer")
    public String updateManufacturer(@RequestParam("manufacturer") String name,
                                     @RequestParam("image") MultipartFile image,
                                     @RequestParam("manufacturerId") Long id) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setNameManufacturer(name);
        this.manufacturerService.updateManufacturer(manufacturer, image);
        return "redirect:/earPhone/dashAdmin/manageManufacturers";
    }

    @GetMapping("/searchManufacturers")
    public String searchManufacturers(@RequestParam("search") String nameManufacturer,
                                      ModelMap model
    ) {
        model.addAttribute("ListManufacturer", this.manufacturerService.getManufacturerByName("%" + nameManufacturer + "%"));
        Page<Manufacturer> pageManufacturer = manufacturerService.findPaginated(1, 5);
        model.addAttribute("page", pageManufacturer);
        model.addAttribute("currentPage", 1);
        this.actions(model);
        return "dashAdmin/fragments/manage-Manufacturers";
    }

    @GetMapping("/deleteManufacturer")
    public String deleteManufacturers(@RequestParam("checkedID") Long[] id) {
        System.out.println(Arrays.toString(id));
        manufacturerService.deleteAllManufacturer(id);
        return "redirect:/earPhone/dashAdmin/manageManufacturers";
    }

    @GetMapping("/pageManufacturers/{pageNo}")
    public String findPaginated(@PathVariable("pageNo") int pageNo, ModelMap model) {
        int pageSize = 5;
        Page<Manufacturer> pageManufacturer = manufacturerService.findPaginated(pageNo, pageSize);
        model.addAttribute("ListManufacturer", pageManufacturer.getContent());
        model.addAttribute("page", pageManufacturer);
        model.addAttribute("currentPage", pageNo);
        this.actions(model);
        return "dashAdmin/fragments/manage-Manufacturers";
    }

    public void actions(ModelMap model) {
        model.addAttribute("SEARCH", "/searchManufacturers");
        model.addAttribute("BASEURL", "/earPhone/dashAdmin/manageManufacturers");
        model.addAttribute("CREATE", "/addManufacturer");
        model.addAttribute("UPDATE", "/updateManufacturer");
        model.addAttribute("DELETE_ALL", "/earPhone/dashAdmin/manageManufacturers/deleteManufacturer");
        model.addAttribute("PAGE", "/pageManufacturers/");
    }
}
