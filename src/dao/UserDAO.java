package dao;

import model.User;

import java.util.List;

public interface UserDAO {
    User save(User user);

    User findById(int userId);
    List<User> findAll();
    User findByEmail(String email);

    void update(User user);

    void delete(int userId);
}