package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tacos.data.UserRepository;
import tacos.security.NoEncodingPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .authorizeExchange()
                .anyExchange().permitAll().and()
                .build();
//                .pathMatchers("/design","/orders").hasAnyAuthority("USER")
//                .anyExchange().permitAll()
//                .and()
//                .build();
    }

//    @Service
//    public ReactiveUserDetailsService userDetailsService(UserRepository userRepo){
//        return new ReactiveUserDetailsService() {
//            @Override
//            public Mono<UserDetails> findByUsername(String username) {
//                return userRepo.findByUsername(username)
//                        .map(user -> {
//                            return user.toUswerDetails();
//                        });
//            }
//        };
//    }


}
