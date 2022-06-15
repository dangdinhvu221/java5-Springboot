package poly.edu.assignment_earphone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment_earphone.models.Orders;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Orders, Long> {
    Orders findTop1ByOrderByIdDesc();
}
