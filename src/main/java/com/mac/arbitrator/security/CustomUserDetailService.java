package com.mac.arbitrator.security;

import com.mac.arbitrator.entity.User;
import com.mac.arbitrator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user with given name not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),mapToAuthorities());
    }

    private Collection<? extends GrantedAuthority> mapToAuthorities(){
        Set<String> status = new HashSet<>();
        status.add("active");
        status.add("Inactive");
        return  status.stream().map(s-> new SimpleGrantedAuthority(s)).collect(Collectors.toSet());
    }
}
