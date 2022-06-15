package poly.edu.assignment_earphone.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.assignment_earphone.models.CartDetails;
import poly.edu.assignment_earphone.models.Carts;
import poly.edu.assignment_earphone.models.EarPhone;
import poly.edu.assignment_earphone.services.CartService;
import poly.edu.assignment_earphone.services.EarPhoneService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private EarPhoneService earPhoneService;
    private static final String ATT_CART_NAME = "myCart";


    @Override
    public Carts getCart() {
        Carts cart = (Carts) request.getSession().getAttribute(ATT_CART_NAME);
        if (cart == null) {
            cart = new Carts();
            cart.setCartDetails(new ArrayList<>());
            request.getSession().setAttribute(ATT_CART_NAME, cart);
        }
        return cart;
    }

    @Override
    public void addToCart(Long earPhoneId, Integer quantity) {
        Carts cart = getCart();
        EarPhone earPhone = earPhoneService.getEarPhone(earPhoneId);
        boolean flag = true;
        if (quantity > earPhone.getQuantity()) {
            flag = false;
        }else{
            for (CartDetails cD : cart.getCartDetails()) {
                if (earPhoneId.equals(cD.getId())) {
                    if (cD.getQuantity().equals(earPhone.getQuantity()) || (cD.getQuantity() + quantity) > earPhone.getQuantity()) {
                        flag = false;
                    } else {
                        flag = false;
                        cD.setQuantity(cD.getQuantity() + quantity);
                    }
                }
            }
        }
        if (flag) {
            cart.getCartDetails().add(new CartDetails(earPhone.getId(), earPhone.getName(), quantity, earPhone.getPrice(), earPhone.getImage(), earPhone.getId(), 1L));
        }
    }

    @Override
    public void changeEarPhoneQuantityPlus(Long earPhoneId) {
        Carts cart = getCart();
        EarPhone earPhone = earPhoneService.getEarPhone(earPhoneId);
        for (CartDetails cD : cart.getCartDetails()) {
            if (earPhoneId.equals(cD.getId())) {
                if (cD.getQuantity() == earPhone.getQuantity()) {

                } else {
                    cD.setQuantity(cD.getQuantity() + 1);
                }
            }
        }
    }

    @Override
    public void changeEarPhoneQuantityMinus(Long earPhoneId) {
        Carts cart = getCart();
        List<CartDetails> list = cart.getCartDetails();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(earPhoneId)) {
                if(list.get(i).getQuantity() < 2){
                    list.remove(i);
                }else{
                    list.get(i).setQuantity(list.get(i).getQuantity() - 1);
                }
            }
        }
    }

    @Override
    public void removeEarPhone(Long earPhoneId) {
        Carts cart = getCart();
        List<CartDetails> list = cart.getCartDetails();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(earPhoneId)) {
                list.remove(i);
            }
        }
    }

    @Override
    public void removeEarPhones() {
        request.getSession().removeAttribute(ATT_CART_NAME);
    }
}
