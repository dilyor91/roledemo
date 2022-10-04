package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Privilege2.
 */
@Entity
@Table(name = "privilege_2")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Privilege2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
        name = "rel_privilege_2__role2",
        joinColumns = @JoinColumn(name = "privilege_2_id"),
        inverseJoinColumns = @JoinColumn(name = "role2_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "privileges", "users" }, allowSetters = true)
    private Set<Role2> role2s = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Privilege2 id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Privilege2 name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role2> getRole2s() {
        return this.role2s;
    }

    public void setRole2s(Set<Role2> role2s) {
        this.role2s = role2s;
    }

    public Privilege2 role2s(Set<Role2> role2s) {
        this.setRole2s(role2s);
        return this;
    }

    public Privilege2 addRole2(Role2 role2) {
        this.role2s.add(role2);
        role2.getPrivileges().add(this);
        return this;
    }

    public Privilege2 removeRole2(Role2 role2) {
        this.role2s.remove(role2);
        role2.getPrivileges().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Privilege2)) {
            return false;
        }
        return id != null && id.equals(((Privilege2) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Privilege2{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
