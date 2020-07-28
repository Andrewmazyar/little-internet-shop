package littleinternetshop.internetshop.model.mapper;

import littleinternetshop.internetshop.model.dto.ProductDto;
import littleinternetshop.internetshop.model.entity.Product;
import littleinternetshop.internetshop.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final ProductService productService;

    public ProductMapper(ProductService productService) {
        this.productService = productService;
    }

    public void saveProduct(ProductDto dto) {
        Product product = new Product();
        product.setQuantity(dto.getQuantity());
        product.setItem(dto.getProductName());
        product.setPrice(dto.getPrice());
        productService.create(product);
    }

    public ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setQuantity(product.getQuantity());
        dto.setProductName(product.getItem());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
