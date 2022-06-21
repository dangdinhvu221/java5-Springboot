package poly.edu.assignment_earphone.controllers.user;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment_earphone.models.CartDetails;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.services.*;
import poly.edu.assignment_earphone.services.Impl.PaypalServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/earPhone")
public class PaypalController {
    public static final String SUCCESS_URL = "/earPhone/success";
    public static final String CANCEL_URL = "/earPhone/cancel";

    @Autowired
    private PaypalServiceImpl service;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService detailsService;
    @Autowired
    private UserService userService;

    @PostMapping("/pay")
    public String payment() {
        try {
            double total = 0.0;
            for (CartDetails c : cartService.getCart().getCartDetails()) {
                total += c.getQuantity() * Integer.parseInt(String.valueOf(c.getPrice()));
            }
            Payment payment = service.createPayment(total, "USD", "paypal",
                    "sale", "Order Successfully!", "http://localhost:8080" + CANCEL_URL,
                    "http://localhost:8080" + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/cancel")
    public String cancelPay() {
        return "redirect:/earPhone/cart";
    }

    @GetMapping("/success")
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId,
                             Principal principal) {
        try {
            Users user = this.userService.getUsersByUsername(principal.getName());
            Payment payment = service.executePayment(paymentId, payerId);
            int total = 0;
            System.out.println(payment.toJSON());

//            Checkout
            if (payment.getState().equals("approved")) {
                for (CartDetails c : cartService.getCart().getCartDetails()) {
                    total += c.getQuantity() * Integer.parseInt(String.valueOf(c.getPrice()));
                }
                this.orderService.addOrder(user, new Date(), BigDecimal.valueOf(total * 1.1), 1L);
                for (CartDetails c : this.cartService.getCart().getCartDetails()) {
                    this.detailsService.addOrderDetails(c.getEarPhoneId(), orderService.getOrderDesc().getId(), c.getPrice(), c.getQuantity());
                }
                cartService.removeEarPhones();
                return "homePage/fragments/order-complete";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }
}
