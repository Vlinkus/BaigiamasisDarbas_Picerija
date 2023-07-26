package lt.academy.javau5.pizza.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.academy.javau5.pizza.entities.Pizza;


public interface PizzaRepository extends JpaRepository<Pizza,Integer> {

}
