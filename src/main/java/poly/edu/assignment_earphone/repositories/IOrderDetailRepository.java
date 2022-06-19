package poly.edu.assignment_earphone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment_earphone.models.OrderDetails;
import poly.edu.assignment_earphone.models.Orders;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByOrdersByOrdersId(Orders ordersByOrdersId);
}
