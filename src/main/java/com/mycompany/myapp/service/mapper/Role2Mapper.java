package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Role2;
import com.mycompany.myapp.service.dto.Role2DTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Role2} and its DTO {@link Role2DTO}.
 */
@Mapper(componentModel = "spring")
public interface Role2Mapper extends EntityMapper<Role2DTO, Role2> {}
