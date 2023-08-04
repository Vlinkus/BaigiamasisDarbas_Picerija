package lt.academy.javau5.pizza._security.services;

import lt.academy.javau5.pizza._security.entities.User;
import lt.academy.javau5.pizza._security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public Optional<User> getByEmail(String email) { return repo.findByEmail(email); }

    public Optional<User> getByUsername(String email) { return repo.findByEmail(email); }

    public List<User> getAll() { return repo.findAll(); }

    public User saveUser(User user) { return repo.save(user); }

    public void deleteUser(User user) {
        repo.delete(user);
    }

    public boolean hasEmail(String email) { return repo.existsByEmail(email); }

    public boolean hasUsername(String username) { return repo.existsByUsername(username); }

    
}
