package littleinternetshop.internetshop.repository;

import java.util.List;
import littleinternetshop.internetshop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product AS p ORDER BY p.price ASC")
    List<Product> findAllByPrice();

    Product findByItem(String item);
}
