package littleinternetshop.internetshop.service.impl;

import java.util.List;
import littleinternetshop.internetshop.model.entity.Product;
import littleinternetshop.internetshop.repository.ProductRepository;
import littleinternetshop.internetshop.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getAllByLowestPrice() {
        return productRepository.findAllByPrice();
    }

    @Override
    public Product updateById(Product product) {
        Product updateProduct = productRepository.findById(product.getId()).get();
        updateProduct.setQuantity(product.getQuantity());
        return productRepository.save(product);
    }

    @Override
    public Product getByItem(String item) {
        return productRepository.findByItem(item);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }
}
