package poly.edu.assignment_earphone.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.assignment_earphone.models.*;
import poly.edu.assignment_earphone.models.typeEnum.TypeCondition;
import poly.edu.assignment_earphone.models.typeEnum.TypeEarPhone;
import poly.edu.assignment_earphone.services.EarPhoneService;
import poly.edu.assignment_earphone.services.ManufacturerService;

import java.math.BigDecimal;
import java.util.Date;

@Controller
@RequestMapping("/earPhone/dashAdmin")
@PreAuthorize("isAuthenticated() and hasAuthority('ADMIN')")
public class EarPhoneController {

    @Autowired
    private EarPhoneService earPhoneService;

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("/formEarPhone")
    private String showFormEarPhone(ModelMap model) {
        model.addAttribute("BASEURL", "/earPhone/dashAdmin");
        model.addAttribute("CREATE", "/addEarPhones");
        model.addAttribute("UPDATE", "/updateEarPhone");
        model.addAttribute("listManufacturer", this.manufacturerService.getAllManufacturer());
        return "dashAdmin/fragments/form-CreatedEarPhone";
    }

    @GetMapping("/manageEarPhones")
    public String getAllEarPhone(ModelMap model) {
        return findPaginated(1, model);
    }

    @PostMapping("/addEarPhones")
    public String addEarPhone(@RequestParam("name") String name,
                              @RequestParam("title") String title,
                              @RequestParam("warranty") String warranty,
                              @RequestParam("frequency") Integer frequency,
                              @RequestParam("color") String color,
                              @RequestParam("impedance") String impedance,
                              @RequestParam("typeEarPhone") TypeEarPhone typeEarPhone,
                              @RequestParam("typeCondition") TypeCondition typeCondition,
                              @RequestParam("quantity") Integer quantity,
                              @RequestParam("price") BigDecimal price,
                              @RequestParam("image") MultipartFile image,
                              @RequestParam("description") String description,
                              @RequestParam("manufacturers") Manufacturer manufacturers
    ) {
        this.earPhoneService.saveEarPhoneToDb(name, title, warranty, frequency, color, price, impedance, image, description, new Date(), quantity, typeEarPhone, typeCondition, manufacturers);
        return "redirect:/earPhone/dashAdmin/formEarPhone";
    }

    @GetMapping("/editEarPhone/{ids}")
    private String editUser(ModelMap model,
                            @PathVariable() Long ids
    ) {
        EarPhone earPhone = this.earPhoneService.getEarPhone(ids);
        model.addAttribute("earPhone", earPhone);
        model.addAttribute("UPDATE", "/updateEarPhone");
        model.addAttribute("BASEURL", "/earPhone/dashAdmin");
        model.addAttribute("listManufacturer", this.manufacturerService.getAllManufacturer());
        return "dashAdmin/fragments/form-EditEarPhone";
    }

    @PostMapping("/updateEarPhone")
    public String updateUser(ModelMap model,
                             @RequestParam("idEarPhone") Long id,
                             @RequestParam("name") String name,
                             @RequestParam("title") String title,
                             @RequestParam("warranty") String warranty,
                             @RequestParam("frequency") Integer frequency,
                             @RequestParam("color") String color,
                             @RequestParam("impedance") String impedance,
                             @RequestParam("typeEarPhone") TypeEarPhone typeEarPhone,
                             @RequestParam("typeCondition") TypeCondition typeCondition,
                             @RequestParam("quantity") Integer quantity,
                             @RequestParam("price") BigDecimal price,
                             @RequestParam("image") MultipartFile image,
                             @RequestParam("description") String description,
                             @RequestParam("manufacturers") Manufacturer manufacturers

    ) {
        EarPhone earPhone = this.earPhoneService.getEarPhone(id);
        this.earPhoneService.updateEarPhoneToDb(id, name, title, warranty, frequency, color, price, impedance, image, description, earPhone.getCreated(), quantity, typeEarPhone, typeCondition, manufacturers);
        model.addAttribute("UPDATE", "/updateEarPhone");
        model.addAttribute("BASEURL", "/earPhone/dashAdmin");
        return "redirect:/earPhone/dashAdmin/manageEarPhones";
    }

    @GetMapping("/deleteEarPhone/{id}")
    public String deleteEarPhone(@PathVariable Long id
    ) {
        this.earPhoneService.deleteEarPhone(id);
        return "redirect:/earPhone/dashAdmin/formEarPhone";
    }

    @GetMapping("/deleteEarPhone")
    public String deleteUser(@RequestParam("checkedID") Long[] ids) {
        this.earPhoneService.deleteAllEarPhone(ids);
        return "redirect:/earPhone/dashAdmin/formEarPhone";
    }

    @GetMapping("/searchEarPhone")
    public String searchUsers(ModelMap model,
                              @RequestParam("search") String name
    ) {
        System.out.println(name);
        for (EarPhone e: this.earPhoneService.findByNameEarPhone(name)
             ) {
            System.out.println(e.getId());
        }
        model.addAttribute("ListEarPhone", this.earPhoneService.findByNameEarPhone(name));
        Page<EarPhone> pageEarPhone = this.earPhoneService.findPaginated(1, 5);
        model.addAttribute("page", pageEarPhone);
        model.addAttribute("currentPage", 1);
        this.actions(model);
        return "dashAdmin/fragments/manage-EarPhones";
    }

    @GetMapping("/pageEarPhone/{pageNo}")
    public String findPaginated(@PathVariable("pageNo") int pageNo, ModelMap model) {
        int pageSize = 5;
        Page<EarPhone> pageEarPhone = this.earPhoneService.findPaginated(pageNo, pageSize);
        model.addAttribute("ListEarPhone", pageEarPhone.getContent());
        model.addAttribute("page", pageEarPhone);
        model.addAttribute("currentPage", pageNo);
        this.actions(model);
        return "dashAdmin/fragments/manage-EarPhones";
    }

    public void actions(ModelMap model) {
        model.addAttribute("ADD", "/formEarPhone");
        model.addAttribute("BASEURL", "/earPhone/dashAdmin");
        model.addAttribute("BLOCK", "/deleteEarPhone/");
        model.addAttribute("SEARCH", "/searchEarPhone");
        model.addAttribute("EDIT", "/editEarPhone/");
        model.addAttribute("DELETE_ALL", "/earPhone/dashAdmin/deleteEarPhone");
        model.addAttribute("PAGE", "/pageEarPhone/");
    }
}
