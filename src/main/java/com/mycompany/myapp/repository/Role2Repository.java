package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Role2;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Role2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Role2Repository extends JpaRepository<Role2, Long> {}
