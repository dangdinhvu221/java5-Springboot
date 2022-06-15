package poly.edu.assignment_earphone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.services.EarPhoneService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/earPhone")
public class HomeAdminAndPageController {

    @Autowired
    private EarPhoneService earPhoneService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/dashAdmin/home")
    private String homeAdmin(Model model) {

        return "dashAdmin/layouts/indexAdmin";
    }

    @GetMapping("/logout")
    public String logOut(){
        request.getSession().removeAttribute("user");
        return "redirect:/earPhone/homePage";
    }

    @GetMapping("/homePage")
    private String homePage(Model model) {
        model.addAttribute("EDIT", "/earPhone/earPhoneDetails/");
        model.addAttribute("listEarPhone", this.earPhoneService.getAllEarPhones());
        return "homePage/layouts/indexHomePage";
    }

    @GetMapping("/logInForm")
    private String logIn(Model model) {
        return "LogIn/index";
    }

    @GetMapping("/earPhoneDetails/{id}")
    private String earPhoneDetail(Model model,
                                  @PathVariable Long id
    ) {
        model.addAttribute("ADD_TO_CART", "/earPhone/addToCart");
        model.addAttribute("earPhone", this.earPhoneService.getEarPhone(id));
        return "homePage/fragments/product-detail";
    }

    @GetMapping("/charts")
    private String chart() {
        return "dashAdmin/fragments/chart";
    }

}
