package poly.edu.assignment_earphone.controllers.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment_earphone.services.AuthenService;
import poly.edu.assignment_earphone.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/earPhone")
public class LogInController {

    @GetMapping("/logoutAccount")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/earPhone/homePage";
    }

    @PostMapping("/logIn")
    public String logIn(ModelMap modelMap,
            @RequestParam(name = "login", defaultValue = "true") boolean login
    ) {
        if(!login){
            modelMap.addAttribute("login",login);
            modelMap.addAttribute("error", "Some of your info isn't correct. Please try again.");
            return "LogIn/index";
        }else{
            return "redirect:/earPhone/homePage";
        }
    }

}
