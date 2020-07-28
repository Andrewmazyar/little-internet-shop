package littleinternetshop.internetshop.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import littleinternetshop.internetshop.model.entity.Order;
import littleinternetshop.internetshop.repository.OrderRepository;
import littleinternetshop.internetshop.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(Order order) {
        order.setTimeStamp(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
