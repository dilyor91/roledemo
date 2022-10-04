package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Role2} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Role2DTO implements Serializable {

    private Long id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role2DTO)) {
            return false;
        }

        Role2DTO role2DTO = (Role2DTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, role2DTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role2DTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
