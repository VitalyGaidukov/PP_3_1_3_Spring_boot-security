package ru.kata.spring.boot_security.demo.details;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceDetails implements UserDetailsService {

   private final UserService userService;


    public UserServiceDetails(UserService userService) {
        this.userService = userService;
    }


    //задача UserDetailsService предоставить из БД юзера по имени
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),rolesToAuthorities(user.getRoles()));

    }

    //метод возвращает коллекцию прав доступа
    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role>roles){
        return roles.stream().map(r->new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

}
