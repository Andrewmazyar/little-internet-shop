package littleinternetshop.internetshop.service;

import java.util.List;
import littleinternetshop.internetshop.model.entity.Order;

public interface OrderService {
    Order create(Order order);

    List<Order> getAll();

    Order getById(Long id);

    void deleteById(Long id);
}
