package poly.edu.assignment_earphone.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.services.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/earPhone")
public class HistoryOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService detailsService;
    @Autowired
    private UserService userService;

    @GetMapping("/history")
    public String historyOrder(Model model, Principal principal) {
        Users user = userService.getUsersByUsername(principal.getName());
        if (user != null) {
            model.addAttribute("userSession", user);
            model.addAttribute("listOrders", this.orderService.getAllOrders());
            model.addAttribute("listOrderDetails", this.detailsService.getAllOrderDetails());
        } else {
            return "redirect:/earPhone/homePage";
        }

        return "homePage/fragments/historyOrder";

    }

}
