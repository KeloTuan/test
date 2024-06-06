package laptrinhungdungjava.DemoJPA.repository;

import laptrinhungdungjava.DemoJPA.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
