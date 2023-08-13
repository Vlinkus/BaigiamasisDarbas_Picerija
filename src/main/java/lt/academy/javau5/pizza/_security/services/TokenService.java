package lt.academy.javau5.pizza._security.services;

import lt.academy.javau5.pizza._security.entities.Token;
import lt.academy.javau5.pizza._security.entities.User;
import lt.academy.javau5.pizza._security.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    private TokenRepository repo;

    public Optional<User> getUserByToken(String token) {
        return repo.getUserByToken(token);
    }

    public Optional<Token> findByToken(String token){ return repo.findByToken(token); }

    public Token save(Token storedToken) { return repo.save(storedToken); }
}
