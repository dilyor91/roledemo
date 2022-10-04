package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Privilege2} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Privilege2DTO implements Serializable {

    private Long id;

    private String name;

    private Set<Role2DTO> role2s = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role2DTO> getRole2s() {
        return role2s;
    }

    public void setRole2s(Set<Role2DTO> role2s) {
        this.role2s = role2s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Privilege2DTO)) {
            return false;
        }

        Privilege2DTO privilege2DTO = (Privilege2DTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, privilege2DTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Privilege2DTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", role2s=" + getRole2s() +
            "}";
    }
}
