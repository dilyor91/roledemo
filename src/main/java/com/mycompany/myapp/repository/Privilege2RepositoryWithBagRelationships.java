package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Privilege2;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface Privilege2RepositoryWithBagRelationships {
    Optional<Privilege2> fetchBagRelationships(Optional<Privilege2> privilege2);

    List<Privilege2> fetchBagRelationships(List<Privilege2> privilege2s);

    Page<Privilege2> fetchBagRelationships(Page<Privilege2> privilege2s);
}
