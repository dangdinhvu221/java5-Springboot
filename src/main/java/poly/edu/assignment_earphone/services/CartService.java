package poly.edu.assignment_earphone.services;

import poly.edu.assignment_earphone.models.Carts;
import poly.edu.assignment_earphone.models.CartDetails;

import java.util.List;

public interface CartService {
    Carts getCart();

    void addToCart(Long earPhoneId, Integer quantity);

    void changeEarPhoneQuantityPlus(Long earPhoneId);

    void changeEarPhoneQuantityMinus(Long earPhoneId);


    void removeEarPhone(Long earPhoneId);

    void removeEarPhones();
}
