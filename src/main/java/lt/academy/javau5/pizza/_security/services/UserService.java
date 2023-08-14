package lt.academy.javau5.pizza._security.services;

import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.entities.User;
import lt.academy.javau5.pizza._security.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;

    public Optional<User> getById(Long id) { return repo.findById(id); }

    public Optional<User> getByEmail(String email) { return repo.findByEmail(email); }

    public Optional<User> getByUsername(String email) { return repo.findByEmail(email); }

    public List<String> getAllUsernames() { return repo.getAllUsernames(); }

    public List<User> getAll() { return repo.findAll(); }

    public User saveUser(User user) { return repo.save(user); }

    public void deleteUser(User user) {
        repo.delete(user);
    }

    public boolean hasEmail(String email) { return repo.existsByEmail(email); }

    public boolean hasUsername(String username) { return repo.existsByUsername(username); }

    public Optional<User> findByUsername(String username) { return repo.findByUsername(username);}
}
