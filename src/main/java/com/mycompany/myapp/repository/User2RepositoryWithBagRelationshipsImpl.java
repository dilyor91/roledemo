package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.User2;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class User2RepositoryWithBagRelationshipsImpl implements User2RepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User2> fetchBagRelationships(Optional<User2> user2) {
        return user2.map(this::fetchRole2s);
    }

    @Override
    public Page<User2> fetchBagRelationships(Page<User2> user2s) {
        return new PageImpl<>(fetchBagRelationships(user2s.getContent()), user2s.getPageable(), user2s.getTotalElements());
    }

    @Override
    public List<User2> fetchBagRelationships(List<User2> user2s) {
        return Optional.of(user2s).map(this::fetchRole2s).orElse(Collections.emptyList());
    }

    User2 fetchRole2s(User2 result) {
        return entityManager
            .createQuery("select user2 from User2 user2 left join fetch user2.role2s where user2 is :user2", User2.class)
            .setParameter("user2", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<User2> fetchRole2s(List<User2> user2s) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, user2s.size()).forEach(index -> order.put(user2s.get(index).getId(), index));
        List<User2> result = entityManager
            .createQuery("select distinct user2 from User2 user2 left join fetch user2.role2s where user2 in :user2s", User2.class)
            .setParameter("user2s", user2s)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
