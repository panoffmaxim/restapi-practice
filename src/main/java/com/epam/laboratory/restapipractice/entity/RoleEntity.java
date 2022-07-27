package com.epam.laboratory.restapipractice.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roleEntities")
    private Collection<ClientEntity> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<PrivilegeEntity> privilegeEntities;

    public RoleEntity() {
        super();
    }

    public RoleEntity(final String name) {
        super();
        this.name = name;
    }

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

    public Collection<ClientEntity> getUsers() {
        return users;
    }

    public void setUsers(Collection<ClientEntity> users) {
        this.users = users;
    }

    public Collection<PrivilegeEntity> getPrivileges() {
        return privilegeEntities;
    }

    public void setPrivileges(Collection<PrivilegeEntity> privilegeEntities) {
        this.privilegeEntities = privilegeEntities;
    }
}
