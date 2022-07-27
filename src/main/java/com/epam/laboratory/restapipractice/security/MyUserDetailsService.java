package com.epam.laboratory.restapipractice.security;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.entity.PrivilegeEntity;
import com.epam.laboratory.restapipractice.entity.RoleEntity;
import com.epam.laboratory.restapipractice.repository.ClientRepo;
import com.epam.laboratory.restapipractice.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String clientName)
            throws UsernameNotFoundException {

        ClientEntity clientEntity = clientRepo.findByClientName(clientName);
        if (clientEntity == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(Arrays.asList(
                            roleRepo.findByName("ROLE_USER"))));
        }

        return new org.springframework.security.core.userdetails.User(
                clientEntity.getClientName(), clientEntity.getPassword(), clientEntity.isEnabled(), true, true,
                true, getAuthorities(clientEntity.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<RoleEntity> roleEntities) {

        return getGrantedAuthorities(getPrivileges(roleEntities));
    }

    private List<String> getPrivileges(Collection<RoleEntity> roleEntities) {
        List<String> privileges = new ArrayList<>();
        List<PrivilegeEntity> collection = new ArrayList<>();

        roleEntities.stream().forEach(role -> {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        });
        collection.stream().forEach(privilegeEntity -> {
            privileges.add(privilegeEntity.getName());
        });
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
