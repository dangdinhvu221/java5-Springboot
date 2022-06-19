package poly.edu.assignment_earphone.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.edu.assignment_earphone.services.EarPhoneService;

@Controller
@PreAuthorize("isAuthenticated() and hasAuthority('ADMIN')")
@RequestMapping("/earPhone/dashAdmin/")
public class dashAdminController {
    @Autowired
    private EarPhoneService earPhoneService;

    @GetMapping("/home")
    private String homeAdmin() {
        return "dashAdmin/layouts/indexAdmin";
    }

    @GetMapping("/charts")
    private String chart() {
        return "dashAdmin/fragments/chart";
    }



}
