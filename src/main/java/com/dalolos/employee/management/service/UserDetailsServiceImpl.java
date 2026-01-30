package com.dalolos.employee.management.service;

import com.dalolos.employee.management.model.Employee;
import com.dalolos.employee.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);

        List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

        employee.getRolesList().forEach(role -> grantedAuthoritiesList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName()))));

        employee.getRolesList().stream().flatMap(role -> role.getPermissionsList().stream()).forEach(permission -> grantedAuthoritiesList.add(new SimpleGrantedAuthority(permission.getPermissionName())));

        return new User(employee.getUsername(), employee.getPassword(), employee.isEnabled(), employee.isAccountNonExpired(), employee.isCredentialsNonExpired(), employee.isAccountNonLocked(), grantedAuthoritiesList);
    }
}
