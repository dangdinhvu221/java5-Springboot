package poly.edu.assignment_earphone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/earPhone")
public class LogInController {
    @Autowired
    private UserService userService;

    @PostMapping("/logIn")
    public String logIn(ModelMap modelMap, HttpSession session,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password
    ) {
        if(this.userService.LogIn(username, password)){
            session.setAttribute("user", this.userService.getUsersByUsername(username));
            return "redirect:/earPhone/homePage";
        }else{
            modelMap.addAttribute("error", "Some of your info isn't correct. Please try again.");
            return "LogIn/index";
        }
    }
}
