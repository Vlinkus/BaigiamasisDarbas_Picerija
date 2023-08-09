package lt.academy.javau5.pizza._security.repositories;

import java.util.List;
import java.util.Optional;

import lt.academy.javau5.pizza._security.entities.Token;
import lt.academy.javau5.pizza._security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {
  @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(Integer id);

  Optional<Token> findByToken(String token);

  @Query("SELECT t.user FROM Token t WHERE t.token = :token")
  Optional<User> getUserByToken(String token);
}