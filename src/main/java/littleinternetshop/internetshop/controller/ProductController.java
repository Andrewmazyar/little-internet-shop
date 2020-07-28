package littleinternetshop.internetshop.controller;

import java.util.List;
import java.util.stream.Collectors;
import littleinternetshop.internetshop.model.dto.ProductDto;
import littleinternetshop.internetshop.model.mapper.ProductMapper;
import littleinternetshop.internetshop.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;

    public ProductController(ProductMapper productMapper,
                             ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PostMapping
    public String create(@RequestBody ProductDto productDto) {
        productMapper.saveProduct(productDto);
        return "product was created successfully";
    }

    @GetMapping
    public List<ProductDto> getAll() {
        return productService.getAll()
                .stream()
                .map(productMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/all-cheaper")
    public List<ProductDto> getAllCheaper() {
        return productService.getAllByLowestPrice()
                .stream()
                .map(productMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
