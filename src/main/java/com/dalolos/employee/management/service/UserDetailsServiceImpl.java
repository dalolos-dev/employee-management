package com.dalolos.employee.management.service;

import com.dalolos.employee.management.dto.AuthLoginRequestDTO;
import com.dalolos.employee.management.dto.AuthResponseDTO;
import com.dalolos.employee.management.model.Employee;
import com.dalolos.employee.management.repository.EmployeeRepository;
import com.dalolos.employee.management.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);

        List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

        employee.getRolesList().forEach(role -> grantedAuthoritiesList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName()))));

        employee.getRolesList().stream().flatMap(role -> role.getPermissionsList().stream()).forEach(permission -> grantedAuthoritiesList.add(new SimpleGrantedAuthority(permission.getPermissionName())));

        return new User(employee.getUsername(), employee.getPassword(), employee.isEnabled(), employee.isAccountNonExpired(), employee.isCredentialsNonExpired(), employee.isAccountNonLocked(), grantedAuthoritiesList);
    }

    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequestDTO) {
        String username = authLoginRequestDTO.username();
        String password = authLoginRequestDTO.password();

        Authentication authentication = this.authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.generateToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "login ok", accessToken, true);

        return authResponseDTO;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
