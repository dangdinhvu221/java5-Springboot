package poly.edu.assignment_earphone.services;

import org.springframework.data.domain.Page;
import poly.edu.assignment_earphone.models.Manufacturer;
import poly.edu.assignment_earphone.models.Orders;
import poly.edu.assignment_earphone.models.Users;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {
    Orders addOrder(Users users, Date created, BigDecimal totalPrice, Long orderStates);

    Orders getOrderDesc();

    Orders addOrders(Orders orders);

    Orders updateOrders(Long id, Long idState);

    List<Orders> getAllOrders();

    Orders getOrders(Long id);

    Orders deleteOrders(Long id);

    void deleteAllOrders(Long[] id);

    Page<Orders> findPaginated(int pageNo, int pageSize);
}
