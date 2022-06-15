package poly.edu.assignment_earphone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment_earphone.models.OrderDetails;

public interface IOrderDetailRepository extends JpaRepository<OrderDetails, Long> {
}
