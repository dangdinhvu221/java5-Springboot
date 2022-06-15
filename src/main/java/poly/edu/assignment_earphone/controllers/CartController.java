package poly.edu.assignment_earphone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.assignment_earphone.models.CartDetails;
import poly.edu.assignment_earphone.services.CartService;
import poly.edu.assignment_earphone.services.EarPhoneService;

@Controller
@RequestMapping("/earPhone")
public class CartController {
    @Autowired
    private EarPhoneService earPhoneService;
    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String viewCart(Model model){
        int total = 0;
        int count = 0;
        for (CartDetails c : cartService.getCart().getCartDetails()) {
            total += c.getQuantity() * Integer.parseInt(String.valueOf(c.getPrice()));
            count++;
        }
        model.addAttribute("cartCount", count);
        model.addAttribute("cartDetails", cartService.getCart().getCartDetails());
        model.addAttribute("totalPrice", total);
        model.addAttribute("BUY_ALL", "/shop/buy");
        model.addAttribute("BUY_ID", "/shop/buy/{id}");
        return "homePage/fragments/cart";
    }


    @PostMapping("/addToCart")
    public String addToCart(Model model,
                            @RequestParam("idEarPhone") Long id,
                            @RequestParam("quantity") Integer quantity
    ) {
        int total = 0;
        int count = 0;
        cartService.addToCart(id, quantity);
        for (CartDetails c : cartService.getCart().getCartDetails()) {
            total += c.getQuantity() * Integer.parseInt(String.valueOf(c.getPrice()));
            count++;
        }
        model.addAttribute("cartCount", count);
        model.addAttribute("cartDetails", cartService.getCart().getCartDetails());
        model.addAttribute("totalPrice", total);
        model.addAttribute("BUY_ALL", "/shop/buy");
        model.addAttribute("BUY_ID", "/shop/buy/{id}");

        return "homePage/fragments/cart";
    }

    @GetMapping("/plus/{id}")
    public String updateQuantityPlus(@PathVariable Long id){
        this.cartService.changeEarPhoneQuantityPlus(id);
        return "redirect:/earPhone/cart";
    }

    @GetMapping("/minus/{id}")
    public String updateQuantityMinus(@PathVariable Long id){
        this.cartService.changeEarPhoneQuantityMinus(id);
        return "redirect:/earPhone/cart";
    }

    @GetMapping("/removeCart/{id}")
    public String removeToCart(Model model,
                               @PathVariable Long id) {
        int total = 0;
        int count = 0;
        this.cartService.removeEarPhone(id);
        for (CartDetails c : cartService.getCart().getCartDetails()) {
            total += c.getQuantity() * Integer.parseInt(String.valueOf(c.getPrice()));
            count++;
        }
        model.addAttribute("cartCount", count);
        model.addAttribute("cartDetails", cartService.getCart().getCartDetails());
        model.addAttribute("totalPrice", total);
        return "homePage/fragments/cart";
    }
}
