package poly.edu.assignment_earphone.controllers.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.models.typeEnum.TypeRole;
import poly.edu.assignment_earphone.services.AuthenService;
import poly.edu.assignment_earphone.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("")
public class LogInController {
    @Autowired
    private AuthenService authenService;
    @Autowired
    private UserService userService;

    @PostMapping("/earPhone/logIn")
    public String logIn(ModelMap modelMap,
            @RequestParam(name = "login") boolean login
    ) {
        if(login == false){
            modelMap.addAttribute("login",login);
            modelMap.addAttribute("error", "Some of your info isn't correct. Please try again.");
            return "LogIn/index";
        }else{
            return "redirect:/earPhone/homePage";
        }
    }

//    @PostMapping("/logIn")
//    public String signUp(ModelMap model, @ModelAttribute("user") @Valid Users user) throws Exception {
//        user.setTypeRole(TypeRole.USER);
//        userService.a(user);
//        return "redirect:/Shop/Login?signUp=true";
//    }
//
//    @GetMapping("/earPhone/logout")
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        if (authentication != null) {
//            new SecurityContextLogoutHandler().logout(request, response, authentication);
//        }
//        return "redirect:/earPhone/homePage";
//    }
}
