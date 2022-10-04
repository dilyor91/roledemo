package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Privilege2;
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
public class Privilege2RepositoryWithBagRelationshipsImpl implements Privilege2RepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Privilege2> fetchBagRelationships(Optional<Privilege2> privilege2) {
        return privilege2.map(this::fetchRole2s);
    }

    @Override
    public Page<Privilege2> fetchBagRelationships(Page<Privilege2> privilege2s) {
        return new PageImpl<>(fetchBagRelationships(privilege2s.getContent()), privilege2s.getPageable(), privilege2s.getTotalElements());
    }

    @Override
    public List<Privilege2> fetchBagRelationships(List<Privilege2> privilege2s) {
        return Optional.of(privilege2s).map(this::fetchRole2s).orElse(Collections.emptyList());
    }

    Privilege2 fetchRole2s(Privilege2 result) {
        return entityManager
            .createQuery(
                "select privilege2 from Privilege2 privilege2 left join fetch privilege2.role2s where privilege2 is :privilege2",
                Privilege2.class
            )
            .setParameter("privilege2", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Privilege2> fetchRole2s(List<Privilege2> privilege2s) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, privilege2s.size()).forEach(index -> order.put(privilege2s.get(index).getId(), index));
        List<Privilege2> result = entityManager
            .createQuery(
                "select distinct privilege2 from Privilege2 privilege2 left join fetch privilege2.role2s where privilege2 in :privilege2s",
                Privilege2.class
            )
            .setParameter("privilege2s", privilege2s)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
