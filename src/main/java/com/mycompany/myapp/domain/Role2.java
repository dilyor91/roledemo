package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Role2.
 */
@Entity
@Table(name = "role_2")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Role2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "role2s")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "role2s" }, allowSetters = true)
    private Set<Privilege2> privileges = new HashSet<>();

    @ManyToMany(mappedBy = "role2s")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "role2s" }, allowSetters = true)
    private Set<User2> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Role2 id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Role2 name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Privilege2> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Set<Privilege2> privilege2s) {
        if (this.privileges != null) {
            this.privileges.forEach(i -> i.removeRole2(this));
        }
        if (privilege2s != null) {
            privilege2s.forEach(i -> i.addRole2(this));
        }
        this.privileges = privilege2s;
    }

    public Role2 privileges(Set<Privilege2> privilege2s) {
        this.setPrivileges(privilege2s);
        return this;
    }

    public Role2 addPrivilege(Privilege2 privilege2) {
        this.privileges.add(privilege2);
        privilege2.getRole2s().add(this);
        return this;
    }

    public Role2 removePrivilege(Privilege2 privilege2) {
        this.privileges.remove(privilege2);
        privilege2.getRole2s().remove(this);
        return this;
    }

    public Set<User2> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User2> user2s) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeRole2(this));
        }
        if (user2s != null) {
            user2s.forEach(i -> i.addRole2(this));
        }
        this.users = user2s;
    }

    public Role2 users(Set<User2> user2s) {
        this.setUsers(user2s);
        return this;
    }

    public Role2 addUser(User2 user2) {
        this.users.add(user2);
        user2.getRole2s().add(this);
        return this;
    }

    public Role2 removeUser(User2 user2) {
        this.users.remove(user2);
        user2.getRole2s().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role2)) {
            return false;
        }
        return id != null && id.equals(((Role2) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role2{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
