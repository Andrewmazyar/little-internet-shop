package littleinternetshop.internetshop;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import littleinternetshop.internetshop.model.entity.Order;
import littleinternetshop.internetshop.model.entity.Product;
import littleinternetshop.internetshop.service.OrderService;
import littleinternetshop.internetshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class DataSaverToDbTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    public void save_product_to_db_is_ok() {
        Product product = new Product();
        product.setQuantity(100L);
        product.setPrice(100.4);
        product.setItem("Product");
        productService.create(product);
        Product actual = productService.getByItem("Product");
        Assert.assertEquals(product.getItem(), actual.getItem());
        Assert.assertEquals(product.getPrice(), actual.getPrice());
    }

    @Test
    public void save_order_to_db_is_ok() {
        Order order = new Order();
        order.setQuantity(10L);
        order.setPrice(10.0);
        order.setItem("product");
        order.setTimeStamp(LocalDateTime.now());
        orderService.create(order);
        Order actual = orderService.getById(1l);
        Assert.assertEquals(order.getItem(), actual.getItem());
        Assert.assertEquals(order.getPrice(), actual.getPrice());
    }
}
