package com.example.smssender.dao;

import com.example.smssender.models.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class TagDaoImpl implements TagDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag findTagByName(String name) {
        TypedQuery<Tag> typedQuery = entityManager.createQuery("from Tag where name =: name", Tag.class);
        typedQuery.setParameter("name", name);
        Tag neededTag = null;
        try {
            neededTag = typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            System.err.println("No such Tag in DB, it will be added");
        }
        return neededTag;
    }
}
