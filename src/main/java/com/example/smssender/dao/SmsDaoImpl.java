package com.example.smssender.dao;

import com.example.smssender.models.Sms;
import com.example.smssender.models.Sms_;
import com.example.smssender.models.Tag_;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class SmsDaoImpl implements SmsDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean add(Sms sms) {
        try {
            entityManager.persist(sms);
            return true;
        } catch (Exception exception) {
            System.err.println("Exception while saving sms:");
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public void addAll(List<Sms> smsList) {
        for (Sms sms : smsList) {
            add(sms);
        }
    }

    @Override
    public List<Sms> getSmsFiltered(Map<String, Object> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sms> criteriaQuery = criteriaBuilder.createQuery(Sms.class);
        Root<Sms> root = criteriaQuery.from(Sms.class);

        List<Predicate> expressionList = new ArrayList<>();
        if (!specification.containsKey("date") & specification.containsKey("date_from") & specification.containsKey("date_to")) {
            expressionList.add(criteriaBuilder.between(root.get("dateTime"), (LocalDateTime) specification.get("date_from"), (LocalDateTime) specification.get("date_to")));

        } else if (specification.containsKey("date")) {
            expressionList.add(criteriaBuilder.equal(root.get("dateTime"), specification.get("date")));
        }
        if (specification.containsKey("phone")) {
            expressionList.add(criteriaBuilder.equal(root.get("phone"), specification.get("phone")));
        }
        if (specification.containsKey("tags")) {
            Expression<String> tagName = root.join(Sms_.tags).get(Tag_.name);
            List<String> tags = Arrays.asList((String[]) specification.get("tags"));
            expressionList.add(tagName.in(tags));
        }

        criteriaQuery.select(root);
        criteriaQuery.where(expressionList.toArray(new Predicate[]{}));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
