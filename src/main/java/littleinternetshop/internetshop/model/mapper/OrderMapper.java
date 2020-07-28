package littleinternetshop.internetshop.model.mapper;

import littleinternetshop.internetshop.model.dto.OrderRequestDto;
import littleinternetshop.internetshop.model.dto.OrderResponseDto;
import littleinternetshop.internetshop.model.entity.Order;
import littleinternetshop.internetshop.model.entity.Product;
import littleinternetshop.internetshop.service.OrderService;
import littleinternetshop.internetshop.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final ProductService productService;
    private final OrderService orderService;

    public OrderMapper(ProductService productService,
                       OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    public OrderResponseDto completeOrder(OrderRequestDto orderRequestDto) {
        Product product = productService.getByItem(orderRequestDto.getProductName());
        OrderResponseDto dto = new OrderResponseDto();
        if (checkAmount(product.getQuantity(), orderRequestDto.getQuantity())) {
            dto.setProductName(product.getItem());
            dto.setQuantity(product.getQuantity());
            dto.setPrice(product.getPrice() * dto.getPrice());
            product.setQuantity(0L);
            productService.updateById(product);
            saveOrder(dto);
            return dto;
        }
        dto.setProductName(product.getItem());
        dto.setQuantity(orderRequestDto.getQuantity());
        dto.setPrice(product.getPrice() * dto.getQuantity());
        product.setQuantity(product.getQuantity() - orderRequestDto.getQuantity());
        productService.updateById(product);
        saveOrder(dto);
        return dto;
    }

    public OrderResponseDto convertToDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setPrice(order.getPrice());
        dto.setProductName(order.getItem());
        dto.setQuantity(order.getQuantity());
        return dto;
    }

    private void saveOrder(OrderResponseDto dto) {
        Order order = new Order();
        order.setItem(dto.getProductName());
        order.setQuantity(dto.getQuantity());
        order.setPrice(dto.getPrice());
        orderService.create(order);
    }

    private boolean checkAmount(Long first, Long second) {
        return first < second;
    }
}
