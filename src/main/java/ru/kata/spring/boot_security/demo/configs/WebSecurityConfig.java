package ru.kata.spring.boot_security.demo.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.details.UserServiceDetails;


@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;

    private final PasswordEncoder passwordEncoder;

    private final UserServiceDetails userServiceDetails;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, PasswordEncoder passwordEncoder, UserServiceDetails userServiceDetails) {
        this.successUserHandler = successUserHandler;
        this.passwordEncoder = passwordEncoder;
        this.userServiceDetails = userServiceDetails;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                //.antMatchers("/", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/homePage").successHandler(successUserHandler)//обработка после успешной аутентификации
                .and()
                .formLogin().loginProcessingUrl("/login")
                .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/homePage")
                .permitAll();
    }


    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("admin")
//                        .roles("ADMIN")
//                        .build();
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    //возвращает юзера в SecurityContext
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userServiceDetails);
        return authenticationProvider;
    }

}