package poly.edu.assignment_earphone.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.edu.assignment_earphone.models.CartDetails;
import poly.edu.assignment_earphone.models.EarPhone;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.services.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/earPhone")
@PreAuthorize("isAuthenticated()")
public class CheckoutController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService detailsService;
    @Autowired
    private EarPhoneService earPhoneService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/checkout")
    public String showOrder(Model model) {
        model.addAttribute("listOrders", orderService.getAllOrders());
        model.addAttribute("listOrdersDetails", detailsService.getAllOrderDetails());
        int total = 0;
        int count = 0;
        for (CartDetails c : cartService.getCart().getCartDetails()) {
            total += (c.getQuantity() * Integer.parseInt(String.valueOf(c.getPrice())));
            count++;
        }
        model.addAttribute("cartCount", count);
        model.addAttribute("cartDetails", cartService.getCart().getCartDetails());
        model.addAttribute("totalPrice", total);
        model.addAttribute("BUY_ALL", "/shop/buy");
        model.addAttribute("BUY_ID", "/shop/buy/{id}");

        return "homePage/fragments/checkout";
    }

    @GetMapping("/orderComplete")
    public String orderComplete(Model model) {
        model.addAttribute("listOrders", orderService.getAllOrders());
        model.addAttribute("listOrdersDetails", detailsService.getAllOrderDetails());
        return "homePage/fragments/order-complete";
    }

    @GetMapping("/buy")
    public String buyEarPhone(Model model, Principal principal) {
        int total = 0;
        Users user = this.userService.getUsersByUsername(principal.getName());
        for (CartDetails c : cartService.getCart().getCartDetails()) {
            total += c.getQuantity() * Integer.parseInt(String.valueOf(c.getPrice()));
        }
        this.orderService.addOrder(user, new Date(), BigDecimal.valueOf(total * 1.1), 1L);
        for (CartDetails c : this.cartService.getCart().getCartDetails()) {
            this.detailsService.addOrderDetails(c.getEarPhoneId(), orderService.getOrderDesc().getId(), c.getPrice(), c.getQuantity());
        }
        cartService.removeEarPhones();
        return "redirect:/earPhone/orderComplete";

    }
}
