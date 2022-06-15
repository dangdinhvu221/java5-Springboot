package poly.edu.assignment_earphone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.assignment_earphone.models.OrderStates;

public interface IOrderStatesRepository extends JpaRepository<OrderStates, Long> {
}
