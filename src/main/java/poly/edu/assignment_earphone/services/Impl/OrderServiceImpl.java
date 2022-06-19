package poly.edu.assignment_earphone.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import poly.edu.assignment_earphone.models.OrderStates;
import poly.edu.assignment_earphone.models.Orders;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.repositories.IOrderRepository;
import poly.edu.assignment_earphone.repositories.IOrderStatesRepository;
import poly.edu.assignment_earphone.repositories.IUserRepository;
import poly.edu.assignment_earphone.services.OrderService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderStatesRepository orderStatesRepository;


    @Override
    public Orders addOrder(Users user, Date created, BigDecimal totalPrice, Long orderStatesId) {
        Orders orders = new Orders();
        OrderStates orderStates = this.orderStatesRepository.findById(orderStatesId).get();
        orders.setId(null);
        orders.setUsersByUserId(user);
        orders.setCreated(new Date());
        orders.setTotalPrice(totalPrice);
        orders.setOderStatesByOderStatesId(orderStates);
        return this.orderRepository.save(orders);
    }

    @Override
    public Orders getOrderDesc() {
        return this.orderRepository.findTop1ByOrderByIdDesc();
    }

    @Override
    public Orders addOrders(Orders orders) {
        return this.orderRepository.save(orders);
    }

    @Override
    public Orders updateOrders(Long id, Long idState) {
        if(id != null){
            Orders o = this.orderRepository.findById(id).get();
            o.setId(id);
            OrderStates orderStates = this.orderStatesRepository.findById(idState).get();
            o.setOderStatesByOderStatesId(orderStates);
            this.orderRepository.save(o);
        }
        return null;
    }

    @Override
    public List<Orders> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Orders getOrders(Long id) {
        return this.orderRepository.findById(id).get();
    }

    @Override
    public Orders deleteOrders(Long id) {
        if (id != null) {
            Optional<Orders> o = this.orderRepository.findById(id);
            if (o.isPresent()) {
                this.userRepository.deleteById(id);
                return o.get();
            }
        }
        return null;
    }

    @Override
    public void deleteAllOrders(Long[] id) {
        this.orderRepository.deleteAllByIdInBatch(Arrays.asList(id));
    }

    @Override
    public Page<Orders> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.orderRepository.findAll(pageable);
    }
}
