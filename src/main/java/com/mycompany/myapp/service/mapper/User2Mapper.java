package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Role2;
import com.mycompany.myapp.domain.User2;
import com.mycompany.myapp.service.dto.Role2DTO;
import com.mycompany.myapp.service.dto.User2DTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link User2} and its DTO {@link User2DTO}.
 */
@Mapper(componentModel = "spring")
public interface User2Mapper extends EntityMapper<User2DTO, User2> {
    @Mapping(target = "role2s", source = "role2s", qualifiedByName = "role2IdSet")
    User2DTO toDto(User2 s);

    @Mapping(target = "removeRole2", ignore = true)
    User2 toEntity(User2DTO user2DTO);

    @Named("role2Id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Role2DTO toDtoRole2Id(Role2 role2);

    @Named("role2IdSet")
    default Set<Role2DTO> toDtoRole2IdSet(Set<Role2> role2) {
        return role2.stream().map(this::toDtoRole2Id).collect(Collectors.toSet());
    }
}
