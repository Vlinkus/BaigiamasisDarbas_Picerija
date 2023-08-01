package lt.academy.javau5.pizza.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.academy.javau5.pizza.entities.Pizza;
import lt.academy.javau5.pizza.entities.Product;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

	List<Product> findProductsById(int id);

}
