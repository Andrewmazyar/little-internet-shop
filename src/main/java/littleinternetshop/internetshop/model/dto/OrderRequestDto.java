package littleinternetshop.internetshop.model.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
    private String productName;
    private Long quantity;
}
