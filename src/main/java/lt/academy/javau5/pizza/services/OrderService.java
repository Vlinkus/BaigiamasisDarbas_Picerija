package lt.academy.javau5.pizza.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.academy.javau5.pizza.entities.Order;
import lt.academy.javau5.pizza.entities.Pizza;
import lt.academy.javau5.pizza.exceptions.NullCanNotBeSavedException;
import lt.academy.javau5.pizza.exceptions.OrderAlreadyExistsException;
import lt.academy.javau5.pizza.exceptions.OrderDoesNotExistException;
import lt.academy.javau5.pizza.exceptions.PizzaDoesNotExistException;
import lt.academy.javau5.pizza.repositories.OrderRepository;
import lt.academy.javau5.pizza.repositories.PizzaRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PizzaRepository pizzaRepository;

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Order findById(int orderId) {
		Order order = findOrderByIdOrThrowException(orderId);
		return order;
	}

	public Order save(Order theOrder){
		if (theOrder == null)
			throw new NullCanNotBeSavedException("Empty Order cannot be saved, add something");
		if (theOrder.getId() != null) {
			Order order = orderRepository.findOrderById(theOrder.getId()).orElse(null);
			if (order != null) {
				throw new OrderAlreadyExistsException("Order with ID: " + theOrder.getId() + " already exists");
			}
		}
		List<Pizza> checkedPizzas = checkIfPizzasExist(theOrder.getPizzas());
		double totalPrice = calculateOrderPrice(checkedPizzas);
		theOrder.setPizzas(checkedPizzas);
		theOrder.setPrice(Math.floor(totalPrice*100)/100);
		return orderRepository.save(theOrder);
	}

	public Order update(Order theOrder) throws OrderDoesNotExistException {
		if (theOrder == null || theOrder.getId() == null) {
			throw new OrderDoesNotExistException("Order with ID: " + theOrder.getId() + " cannot be updated");
		}
		Order existingOrder = orderRepository.findById(theOrder.getId()).orElseThrow(
				() -> new OrderDoesNotExistException("Order with ID: " + theOrder.getId() + " does not exist"));
		List<Pizza> checkedPizzas = checkIfPizzasExist(theOrder.getPizzas());
		double totalPrice = calculateOrderPrice(checkedPizzas);
		existingOrder.setPizzas(checkedPizzas);
		existingOrder.setPrice(Math.floor(totalPrice*100)/100);
		return orderRepository.save(existingOrder);
	}

	public String deleteOrder(int orderId) {
		Order order = findOrderByIdOrThrowException(orderId); 
		orderRepository.delete(order);
		return "Order Deleted Succesfully";
	}

	private double calculateOrderPrice(List<Pizza> pizzas) {
		double totalPrice = pizzas.stream().mapToDouble(Pizza::getPizzaPrice).sum();
		return totalPrice;
	}

	private List<Pizza> checkIfPizzasExist(List<Pizza> pizzas) {
		List<Pizza> pizzasFromDB = pizzaRepository.findAll();
		List<Pizza> pizzasInOrder = new ArrayList<>();
		for (Pizza p : pizzas) {
			Pizza pizzaFromDB = pizzasFromDB.stream().filter(pro -> pro.getPizzaName().equals(p.getPizzaName()))
					.findFirst().orElse(null);
			if (pizzaFromDB != null) {
				pizzasInOrder.add(pizzaFromDB);
			} else {
				throw new PizzaDoesNotExistException("Pizza with name: " + p.getPizzaName() + " not found");
			}
		}
		return pizzasInOrder;
	}
	
	private Order findOrderByIdOrThrowException(Integer orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderDoesNotExistException("Order with ID: " + orderId + " was not found"));
		return order;
	}
	
	

	    public Order addPizzaToOrder(int orderId, int pizzaId) {
	        Order order = findById(orderId);
	        Pizza pizzaToAdd = pizzaRepository.findById(pizzaId)
	                .orElseThrow(() -> new PizzaDoesNotExistException("Pizza with ID: " + pizzaId + " not found"));

	        if (!order.getPizzas().contains(pizzaToAdd)) {
	            order.getPizzas().add(pizzaToAdd);
	            updateOrderPrice(order);
	            return orderRepository.save(order);
	        } else {
	            throw new RuntimeException("Pizza is already in the order");
	        }
	    }

	    public Order removePizzaFromOrder(int orderId, int pizzaId) {
	        Order order = findById(orderId);
	        Pizza pizzaToRemove = pizzaRepository.findById(pizzaId)
	                .orElseThrow(() -> new PizzaDoesNotExistException("Pizza with ID: " + pizzaId + " not found"));

	        if (order.getPizzas().contains(pizzaToRemove)) {
	            order.getPizzas().remove(pizzaToRemove);
	            updateOrderPrice(order);
	            return orderRepository.save(order);
	        } else {
	            throw new RuntimeException("Pizza is not in the order");
	        }
	    }

	    private void updateOrderPrice(Order order) {
	        double totalPrice = calculateOrderPrice(order.getPizzas());
	        order.setPrice(Math.floor(totalPrice * 100) / 100);
	    }

	    
	}


	

