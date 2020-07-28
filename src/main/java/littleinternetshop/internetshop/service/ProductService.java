package littleinternetshop.internetshop.service;

import java.util.List;
import littleinternetshop.internetshop.model.entity.Product;

public interface ProductService {
    List<Product> getAll();

    Product getById(Long id);

    List<Product> getAllByLowestPrice();

    Product updateById(Product product);

    Product getByItem(String item);

    Product create(Product product);
}
