package com.example.spring_security_fundamentals_005.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //Http configuration, is one of the most powerful things in Spring Security
    //I am overriding the filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic()
                .and()
                .authorizeRequests() //endpoint authorization
                // .anyRequest().authenticated() //all requests must be authenticated
                //.anyRequest().permitAll() //requests do not require authentication
                //.anyRequest().denyAll() //can be configured by request mathers to deny certain paths.
                //.anyRequest().hasAuthority("read")//users will only access my endpoints if they have the read permission
                //.anyRequest().hasAnyAuthority("create","read") // i can enumerate many authorities
                //.anyRequest().hasRole("ADMIN")
                //.anyRequest().hasAnyRole("ADMIN","MANAGER")
               // .anyRequest().access("isAuthenticated() and hasAuthority('read')")//Access give the the probability to use SPEL (Spring Expression Language)
                .requestMatchers("mvcMatchers","/demo").hasAuthority("read") //for us to call the endpoint demo,we must have the authority read
                .anyRequest().authenticated()//any other request just needs to be authenticated
                .and()
                .build();

        //for endpoint authorization,we have the matcher method and then the authorization rule
        //matcher method + authorization rule

        //What i need to know
        //1.which matcher methods should i use and why (anyRequest(),mvcMatchers(),antMatchers(),regex
        //2.How to apply different authorization rules


    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        UserDetails userOne = User.withUsername("caleb")
                .password(passwordEncoder().encode("12345"))
                //.authorities("ROLE_ADMIN")
                .authorities("read")
                //.roles("ADMIN") //equivalent with an authority named  authorities("ROLE_ADMIN")
                .build();

        UserDetails userTwo =
                User.withUsername("david").password(passwordEncoder()
                        .encode("12345"))
                        //.authorities("ROLE_MANAGER")
                        .authorities("write")
                        .build();

        //user needs to be save so the authentication provider finds it
        userDetailsManager.createUser(userOne);
        userDetailsManager.createUser(userTwo);

        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
