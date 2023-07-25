package lt.academy.javau5.pizza.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.academy.javau5.pizza.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
