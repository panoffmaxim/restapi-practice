package com.epam.laboratory.restapipractice.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Privilege")
public class PrivilegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privilegeEntities")
    private Collection<RoleEntity> roleEntities;

    public PrivilegeEntity() {
        super();
    }

    public PrivilegeEntity(final String name) {
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

    public Collection<RoleEntity> getRoles() {
        return roleEntities;
    }

    public void setRoles(Collection<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }
}
