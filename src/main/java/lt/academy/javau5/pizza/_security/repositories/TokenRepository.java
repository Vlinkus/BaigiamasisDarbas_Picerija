package lt.academy.javau5.pizza._security.repositories;

import java.util.List;
import java.util.Optional;

import lt.academy.javau5.pizza._security.entities.Token;
import lt.academy.javau5.pizza._security.entities.User;
import lt.academy.javau5.pizza._security.services.TokenService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for {@link Token} entity
 * @see TokenService
 * @version 1.0, 15 Aug 2023
 * @since 1.0, 3 Aug 2023
 * @author Maksim Pavlenko
 */

public interface TokenRepository extends JpaRepository<Token, Integer> {
  @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(Integer id);

  Optional<Token> findByToken(String token);

  @Query("SELECT t.user FROM Token t JOIN t.user u WHERE t.token = :token")
  Optional<User> getUserByToken(String token);
}