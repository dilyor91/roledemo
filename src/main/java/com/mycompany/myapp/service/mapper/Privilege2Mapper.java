package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Privilege2;
import com.mycompany.myapp.domain.Role2;
import com.mycompany.myapp.service.dto.Privilege2DTO;
import com.mycompany.myapp.service.dto.Role2DTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Privilege2} and its DTO {@link Privilege2DTO}.
 */
@Mapper(componentModel = "spring")
public interface Privilege2Mapper extends EntityMapper<Privilege2DTO, Privilege2> {
    @Mapping(target = "role2s", source = "role2s", qualifiedByName = "role2IdSet")
    Privilege2DTO toDto(Privilege2 s);

    @Mapping(target = "removeRole2", ignore = true)
    Privilege2 toEntity(Privilege2DTO privilege2DTO);

    @Named("role2Id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Role2DTO toDtoRole2Id(Role2 role2);

    @Named("role2IdSet")
    default Set<Role2DTO> toDtoRole2IdSet(Set<Role2> role2) {
        return role2.stream().map(this::toDtoRole2Id).collect(Collectors.toSet());
    }
}
