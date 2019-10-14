package calstatela.cs5220.lab3.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import calstatela.cs5220.lab3.model.User;
import calstatela.cs5220.lab3.model.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUser(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("from User", User.class)
                .getResultList();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return entityManager.merge(user);
    }
}
