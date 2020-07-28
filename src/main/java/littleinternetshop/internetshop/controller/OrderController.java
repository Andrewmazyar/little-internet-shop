package littleinternetshop.internetshop.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import littleinternetshop.internetshop.model.dto.OrderRequestDto;
import littleinternetshop.internetshop.model.dto.OrderResponseDto;
import littleinternetshop.internetshop.model.entity.Order;
import littleinternetshop.internetshop.model.mapper.OrderMapper;
import littleinternetshop.internetshop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    public OrderController(OrderMapper orderMapper,
                           OrderService orderService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    @PostMapping
    public void completeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderMapper.completeOrder(orderRequestDto);
    }

    @GetMapping
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getAll()
                .stream()
                .map(orderMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable String id) {
        Order order = orderService.getById(Long.valueOf(id));
        LocalDateTime time = LocalDateTime.now();
        if (time.isAfter(order.getTimeStamp().plusMinutes(10))) {
            orderService.deleteById(Long.valueOf(id));
            return "Order was deleted";
        } else {
            return "You can`t delete this order because its steel active";
        }
    }
}
