package ru.denisov.springboot.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.denisov.springboot.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleImpl implements RoleDao{
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Role> getRoles() {

        return em.createQuery("SELECT role FROM Role role", Role.class).getResultList();
    }
    @Transactional
    @Override
    public void save(Role role) {
        em.persist(role);
    }
}
