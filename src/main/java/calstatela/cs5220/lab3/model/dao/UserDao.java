package calstatela.cs5220.lab3.model.dao;

import java.util.List;

import calstatela.cs5220.lab3.model.User;

public interface UserDao {
	User getUser(Integer id);

    List<User> getUsers();

    User saveUser(User user);
}

