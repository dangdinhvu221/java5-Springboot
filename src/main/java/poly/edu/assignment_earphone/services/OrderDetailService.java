package poly.edu.assignment_earphone.services;

import org.springframework.data.domain.Page;
import poly.edu.assignment_earphone.models.Manufacturer;
import poly.edu.assignment_earphone.models.OrderDetails;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDetailService {
    OrderDetails addOrderDetails(OrderDetails orderDetails);

    OrderDetails updateOrderDetails(OrderDetails orderDetails);

    List<OrderDetails> getAllOrderDetails();

    OrderDetails getOrderDetails(Long id);

    OrderDetails deleteOrderDetails(Long id);

    void deleteAllOrderDetails(Long[] id);

    Page<OrderDetails> findPaginated(int pageNo, int pageSize);

    OrderDetails addOrderDetails(Long earPhoneId, Long orderId, BigDecimal price, Integer quantity);

}
