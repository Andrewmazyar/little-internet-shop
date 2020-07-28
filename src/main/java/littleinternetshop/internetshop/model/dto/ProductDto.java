package littleinternetshop.internetshop.model.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Double price;
    private String productName;
    private Long quantity;
}
