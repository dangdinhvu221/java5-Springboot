package poly.edu.assignment_earphone.services.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.assignment_earphone.models.OrderStates;
import poly.edu.assignment_earphone.repositories.IOrderStatesRepository;
import poly.edu.assignment_earphone.services.OrderStatesService;
import java.util.List;

@Service
public class OrderStatesServiceImpl implements OrderStatesService {

    @Autowired
    private IOrderStatesRepository statesRepository;

    @Override
    public List<OrderStates> getAllOrderStates() {
        return this.statesRepository.findAll();
    }
}
