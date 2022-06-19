package poly.edu.assignment_earphone.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/earPhone/dashAdmin")
@PreAuthorize("isAuthenticated() and hasAuthority('ADMIN')")
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


    @GetMapping("/Orders/updateOrders")
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

    @GetMapping("/manageOrders")
    public String manageOrder(ModelMap model) {
        return findPaginated(1, model);
    }


    @GetMapping("/Orders/searchOrders")
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

    @GetMapping("/Orders/pageOrders/{pageNo}")
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
