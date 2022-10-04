package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.User2;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface User2RepositoryWithBagRelationships {
    Optional<User2> fetchBagRelationships(Optional<User2> user2);

    List<User2> fetchBagRelationships(List<User2> user2s);

    Page<User2> fetchBagRelationships(Page<User2> user2s);
}
