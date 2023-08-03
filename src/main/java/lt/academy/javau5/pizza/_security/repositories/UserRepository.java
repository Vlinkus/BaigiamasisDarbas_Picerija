package lt.academy.javau5.pizza._security.repositories;

import lt.academy.javau5.pizza._security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

}
