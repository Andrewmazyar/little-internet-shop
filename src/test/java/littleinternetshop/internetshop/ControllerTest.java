package littleinternetshop.internetshop;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import littleinternetshop.internetshop.model.dto.OrderRequestDto;
import littleinternetshop.internetshop.model.dto.ProductDto;
import littleinternetshop.internetshop.model.entity.Order;
import littleinternetshop.internetshop.model.entity.Product;
import littleinternetshop.internetshop.repository.OrderRepository;
import littleinternetshop.internetshop.repository.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void add_product_input_ok() throws Exception {
        ProductDto dto = new ProductDto();
        dto.setPrice(100.0);
        dto.setProductName("Product");
        dto.setQuantity(100L);
        mockMvc.perform(post("/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void add_order_input_ok() throws Exception {
        OrderRequestDto dto = new OrderRequestDto();
        dto.setProductName("Product");
        dto.setQuantity(10L);
        mockMvc.perform(post("/orders")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void get_product_ok() throws Exception {
        List<ProductDto> dtos = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            ProductDto dto = new ProductDto();
            dto.setPrice(product.getPrice());
            dto.setProductName(product.getItem());
            dto.setQuantity(product.getQuantity());
            dtos.add(dto);
        }
        String url = "/products";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, objectMapper.writeValueAsString(dtos));
    }

    @Test
    public void get_all_from_cheaper() throws Exception {
        generateProduct();
        List<ProductDto> dtos = new ArrayList<>();
        for (Product product : productRepository.findAllByPrice()) {
            ProductDto dto = new ProductDto();
            dto.setPrice(product.getPrice());
            dto.setProductName(product.getItem());
            dto.setQuantity(product.getQuantity());
            dtos.add(dto);
        }
        String url = "/products/all-cheaper";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, objectMapper.writeValueAsString(dtos));
    }

    @Test
    public void delete_order_ok() throws Exception {
        generatedOrder();
        String url = "/orders/delete/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, "Order was deleted");
    }

    @Test
    public void delete_order_fail() throws Exception {
        generatedOrder();
        String url = "/orders/delete/2";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, "You can`t delete this order because its steel active");
    }

    private List<Order> generatedOrder() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setTimeStamp(LocalDateTime.now().minusMinutes(11));
        order.setItem("product");
        order.setPrice(12.2);
        order.setQuantity(10L);
        orders.add(order);
        Order order1 = new Order();
        order1.setTimeStamp(LocalDateTime.now());
        order1.setItem("product1");
        order1.setPrice(121.2);
        order1.setQuantity(11L);
        orders.add(order1);
        Order order2 = new Order();
        order2.setTimeStamp(LocalDateTime.now());
        order2.setItem("product1");
        order2.setPrice(121.2);
        order2.setQuantity(11L);
        orders.add(order2);
        orderRepository.saveAll(orders);
        return orderRepository.saveAll(orders);
    }

    private List<Product> generateProduct() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setPrice(20.0);
        product.setItem("Product1");
        product.setQuantity(100L);
        productRepository.save(product);
        products.add(product);

        Product product1 = new Product();
        product1.setPrice(5.0);
        product1.setItem("Product1");
        product1.setQuantity(100L);
        productRepository.saveAll(products);
        products.add(product1);
        return products;
    }
}
