package ru.denisov.springboot.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import ru.denisov.springboot.models.Role;
import ru.denisov.springboot.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements ru.denisov.springboot.dao.UserDao {
    @PersistenceContext
    private EntityManager entityManager;


    public List<User> index() {

        return entityManager.createQuery("from User",User.class).getResultList();
    }
    @Transactional
    public User show (int id) {
        return entityManager.find(User.class,id);
    }

    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public void update(User updatedUser) { ;
        entityManager.merge(updatedUser);
    }
    @Transactional
    public void delete(int id) {
        entityManager.remove(getUserById(id));
        entityManager.flush();
    }

    @Override
    public User getUserByUsername(String username) {
        TypedQuery<User> user = entityManager.createQuery(
                "select user from User user WHERE user.username=:username", User.class);
        user.setParameter("username", username);
        return user.getSingleResult();
    }


//    @Override
//    public User getUserByEmail(String email) {
//        TypedQuery<User> user = entityManager.createQuery(
//                "select user from User user WHERE user.email=:email", User.class
//        );
//        return user.setParameter("email", email).getResultList().stream().findAny().orElse(null);
//    }


    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }


}
