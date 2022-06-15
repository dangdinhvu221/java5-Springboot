package poly.edu.assignment_earphone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment_earphone.models.*;
import poly.edu.assignment_earphone.services.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/earPhone")
public class OderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService detailsService;
    @Autowired
    private EarPhoneService earPhoneService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderStatesService statesService;

    @GetMapping("/history")
    public String historyOrder(Model model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user != null) {
            model.addAttribute("userSession", user);
            model.addAttribute("listOrders", this.orderService.getAllOrders());
            model.addAttribute("listOrderDetails", this.detailsService.getAllOrderDetails());
        } else {
            return "redirect:/earPhone/homePage";
        }

        return "homePage/fragments/historyOrder";

    }

    @GetMapping("/dashAdmin/Orders/updateOrders")
    public String updateOrders(
            @RequestParam("checkedID") Long[] id,
            @RequestParam("orderStates") Long idStates
    ){
        List<Orders> list = this.orderService.getAllOrders();
        for (Orders o : list){
            for (Long idO : id){
                if(o.getId().equals(idO)){
                    this.orderService.updateOrders(o.getId(), idStates);
                }
            }
        }
        return "redirect:/earPhone/dashAdmin/manageOrders";
    }

    @GetMapping("/dashAdmin/manageOrders")
    public String manageOrder(ModelMap model) {
        return findPaginated(1, model);
    }


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
    public String buyEarPhone(Model model, HttpServletRequest request) {
        int total = 0;
        Users user = (Users) request.getSession().getAttribute("user");
        if (user != null) {
            List<CartDetails> cartDetails = this.cartService.getCart().getCartDetails();
            for (CartDetails c : cartService.getCart().getCartDetails()) {
                total += c.getQuantity() * Integer.parseInt(String.valueOf(c.getPrice()));
            }
            this.orderService.addOrder(user.getId(), new Date(), BigDecimal.valueOf(total * 1.1), 1L);
            for (CartDetails c : cartDetails) {
                EarPhone earPhone = this.earPhoneService.getEarPhone(c.getEarPhoneId());
                if (c.getEarPhoneId().equals(earPhone.getId())) {
                    earPhone.setQuantity(earPhone.getQuantity() - c.getQuantity());
                    this.earPhoneService.addEarPhone(earPhone);
                }

                System.out.println(earPhone.getQuantity());
                this.detailsService.addOrderDetails(c.getEarPhoneId(), orderService.getOrderDesc().getId(), c.getPrice(), c.getQuantity());
            }
            cartService.removeEarPhones();
            model.addAttribute("listOrders", orderService.getAllOrders());
            model.addAttribute("listOrdersDetails", detailsService.getAllOrderDetails());
            return "redirect:/earPhone/orderComplete";
        } else {
            return "redirect:/earPhone/logInForm";
        }
    }

    @GetMapping("/dashAdmin/Orders/searchOrders")
    public String searchManufacturers(@RequestParam("search") Long id,
                                      ModelMap model
    ) {
        model.addAttribute("ListOrders", this.orderService.getOrders(id));
        Page<Orders> pageOrders = orderService.findPaginated(1, 5);
        model.addAttribute("page", pageOrders);
        model.addAttribute("currentPage", 1);
        this.actions(model);
        return "dashAdmin/fragments/manage-Orders";
    }

    @GetMapping("/dashAdmin/Orders/pageOrders/{pageNo}")
    public String findPaginated(@PathVariable("pageNo") int pageNo, ModelMap model) {
        int pageSize = 5;
        Page<Orders> pageOrders = orderService.findPaginated(pageNo, pageSize);

        model.addAttribute("ListOrders", pageOrders.getContent());
        model.addAttribute("page", pageOrders);
        model.addAttribute("currentPage", pageNo);
        this.actions(model);
        return "dashAdmin/fragments/manage-Orders";
    }

    public void actions(ModelMap model) {
        model.addAttribute("listOrderDetails", this.detailsService.getAllOrderDetails());
        model.addAttribute("listOrderStates", this.statesService.getAllOrderStates());
        model.addAttribute("SEARCH", "/searchOrders");
        model.addAttribute("BASEURL", "/earPhone/dashAdmin/Orders");
        model.addAttribute("UPDATE", "/updateOrders");
        model.addAttribute("DELETE_ALL", "/earPhone/dashAdmin/Orders/deleteOrders");
        model.addAttribute("PAGE", "/pageOrders/");
    }

}
